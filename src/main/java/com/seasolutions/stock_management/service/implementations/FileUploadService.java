package com.seasolutions.stock_management.service.implementations;


import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.ResampleFilters;
import com.mortennobel.imagescaling.ResampleOp;
import com.seasolutions.stock_management.model.entity.FileUpload;
import com.seasolutions.stock_management.model.support.enumerable.ImageSize;
import com.seasolutions.stock_management.model.view_model.FileUploadViewModel;
import com.seasolutions.stock_management.repository.interfaces.IFileUploadRepository;
import com.seasolutions.stock_management.service.base.BaseService;
import com.seasolutions.stock_management.service.interfaces.IFileUploadService;
import com.seasolutions.stock_management.util.MappingUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

@Log4j2
@Transactional
@Service
public class FileUploadService extends BaseService<FileUpload, FileUploadViewModel> implements IFileUploadService {

    private static final Long DEFAULT_FILE_CACHE_TIME = 1000 * 60 * 5L;
    private static Cache<String, FileUpload> imageCache = null;
    public static final int CACHE_SIZE = 1000000;
    public static final int CONCURRENCY_LEVEL = 5;
    private static final CacheBuilder DEFAULT_CACHE_BUILDER = CacheBuilder.newBuilder()
            .concurrencyLevel(CONCURRENCY_LEVEL)
            .maximumSize(CACHE_SIZE)
            .expireAfterWrite(DEFAULT_FILE_CACHE_TIME, TimeUnit.MILLISECONDS);


    @Autowired
    IFileUploadRepository fileUploadRepository;


    public FileUploadService(){
        imageCache = DEFAULT_CACHE_BUILDER.build();
    }



    @Override
    public void saveImage(MultipartFile file) throws IOException {

        final byte[] scaledImageData = scaleImage(ImageSize.big, file.getBytes());
        FileUpload fileUpload = FileUpload.builder()
                .fileName(file.getOriginalFilename())
                .fileData(scaledImageData)
                .build();
        fileUploadRepository.add(fileUpload);
    }


    @Override
    public FileUploadViewModel getImage(long id) {
        FileUpload fileUpload = imageCache.getIfPresent("Image_"+id);
        if(fileUpload==null){
            fileUpload = fileUploadRepository.findById(id);
            imageCache.put("Image_"+id,fileUpload);
        }
        FileUploadViewModel fileUploadViewModel = MappingUtils.map(fileUpload,FileUploadViewModel.class);
        return fileUploadViewModel;
    }




    private byte[] scaleImage(final ImageSize imageSize, final byte[] imageData) {
        try {
            final BufferedImage originalImage = getBufferedImageFromBytes(imageData);
            final boolean hasAlpha = originalImage.getColorModel().hasAlpha();

            final int origHeight = originalImage.getHeight();
            final int origWidth = originalImage.getWidth();

            final int newWidth = imageSize.width;
            final double aspectRatio = (1.0 * origWidth) / (1.0 * newWidth);
            final int newHeight = Math.round(origHeight / new Float(aspectRatio));

            final ResampleOp resampleOp = new ResampleOp(newWidth, newHeight);
            resampleOp.setFilter(ResampleFilters.getLanczos3Filter());
            resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Normal);
            final BufferedImage scaledImage = resampleOp.filter(originalImage, null);

            return getBytesFromBufferedImage(scaledImage, imageSize.compressionQuality, hasAlpha);

        } catch (final IOException e) {
            log.error("Couldn't scale image", e);
        }
        return null;
    }

    private BufferedImage getBufferedImageFromBytes(final byte[] imageData) throws IOException {
        final InputStream in = new ByteArrayInputStream(imageData);
        return ImageIO.read(in);
    }

    private byte[] getBytesFromBufferedImage(final BufferedImage bufferedImage, final float compressionQuality,
                                             final boolean hasAlpha)
            throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (hasAlpha) {
            ImageIO.write(bufferedImage, "png", baos);
        } else {
            compressJpeg(bufferedImage, compressionQuality, baos);
        }
        baos.flush();
        final byte[] scaledImageData = baos.toByteArray();
        baos.close();
        return scaledImageData;
    }

    private void compressJpeg(final BufferedImage bufferedImage, final float compressionQuality,
                              final ByteArrayOutputStream baos) throws IOException {
        final Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        final ImageWriter writer = (ImageWriter) writers.next();

        final ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
        writer.setOutput(ios);

        final ImageWriteParam param = writer.getDefaultWriteParam();
        if(param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(compressionQuality);
        }
        writer.write(null, new IIOImage(bufferedImage, null, null), param);
    }





}


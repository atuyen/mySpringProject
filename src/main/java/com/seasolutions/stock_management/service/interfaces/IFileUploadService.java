package com.seasolutions.stock_management.service.interfaces;

import com.seasolutions.stock_management.model.entity.FileUpload;
import com.seasolutions.stock_management.model.view_model.FileUploadViewModel;
import com.seasolutions.stock_management.service.base.IBaseService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileUploadService extends IBaseService<FileUpload, FileUploadViewModel> {

    void saveImage(MultipartFile file) throws IOException;



    FileUploadViewModel getImage(long id);


}

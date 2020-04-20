package com.seasolutions.stock_management.controller;


import com.seasolutions.stock_management.model.view_model.FileUploadViewModel;
import com.seasolutions.stock_management.security.annotation.Unauthenticated;
import com.seasolutions.stock_management.service.interfaces.IFileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class FileUploadController {

    @Autowired
    IFileUploadService fileUploadService;


    @Unauthenticated
    @PostMapping(path = "/upload")
    public String uploadFile(MultipartFile file) throws IOException {
        fileUploadService.saveImage(file);
        return "Success";
    }



    @Unauthenticated
    @GetMapping(path = "/images/{id}")
    public void getImage(@PathVariable long id,
                         final HttpServletResponse response) throws IOException {
        FileUploadViewModel fileUploadViewModel = fileUploadService.getImage(id);
        response.addHeader("Content-Type", "image/jpeg");
        response.addHeader("Cache-Control", "max-age=60");
        response.getOutputStream().write(fileUploadViewModel.getFileData());
    }



    @Unauthenticated
    @GetMapping(path = "/download-template/{fileType}")
    public void downloadFileEmployee(HttpServletRequest request, @PathVariable String fileType,
                                     HttpServletResponse response) {
        String fileName = "";
        switch (fileType) {
            case "D":
                fileName = "DepartmentTemplate.xlsm";
                break;
            case "E":
                fileName = "EmployeeTemplate.xlsm";
                break;
            case "U":
                fileName = "UserTemplate.xlsm";
                break;
            default:
                break;
        }
        try {
            InputStream input = new ClassPathResource("template/" + fileName).getInputStream();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            ServletOutputStream out = response.getOutputStream();
            byte[] outputByte = new byte[4096];
            // copy binary contect to output stream
            int length = 0;
            while ((length = input.read(outputByte, 0, 4096)) != -1) {
                out.write(outputByte, 0, length);
            }
            input.close();
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }






}

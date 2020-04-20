package com.seasolutions.stock_management.model.view_model;


import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadViewModel extends BaseViewModel {

    private byte[] fileData;
    private String fileName;

}

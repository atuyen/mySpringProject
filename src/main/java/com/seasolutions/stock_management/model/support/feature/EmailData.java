package com.seasolutions.stock_management.model.support.feature;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EmailData {
    String toEmail;

    @Builder.Default
    List<MultipartFile> files = new ArrayList<>();
}

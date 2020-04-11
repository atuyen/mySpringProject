package com.seasolutions.stock_management.model.support;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationOptions {
    private int offset = 0;
    private int pagSize = 10;
}

package com.seasolutions.stock_management.model.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedResponse<T> {
    private int offset;
    private int pagSize;
    private long total;
    @JsonIgnore
    private List<T> data;



    public PaginatedResponse(final PaginationOptions option, final long total, final List<T> data) {
        this.offset=option.getOffset();
        this.pagSize=option.getPagSize();
        this.total=total;
        this.data = data;
    }

}

package com.seasolutions.stock_management.model.view_model;

import com.seasolutions.stock_management.validation.ananotation.ProductIDExisting;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class TestValidateModel {

    @ProductIDExisting
    private Long id;

    @NotNull(message = "{NotNull.name}")
    private String name;

    @Size(max = 100)
    private String description;

    @Min(1)
    private BigDecimal price;

    public TestValidateModel() {
    }
}

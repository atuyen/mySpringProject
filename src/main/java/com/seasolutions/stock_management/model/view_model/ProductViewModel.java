package com.seasolutions.stock_management.model.view_model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductViewModel extends BaseViewModel {
    private String name;
    private BigDecimal cost;
    private int number;

    private long categoryId;
    private String categoryName;

    private long companyId;
    private String companyName;
    private String companyTransactionName;
    private int totalNumber;


    @JsonIgnore
    List<InvoiceDetailViewModel> invoiceDetails;

    public int getTotalNumber() {
        int sellNumber = invoiceDetails.stream().mapToInt(i->i.getNumber()).sum();
        return sellNumber+number;
    }
}

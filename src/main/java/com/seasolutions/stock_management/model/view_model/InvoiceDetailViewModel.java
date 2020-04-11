package com.seasolutions.stock_management.model.view_model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class InvoiceDetailViewModel extends BaseViewModel {

    private int cost;

    private int number;

    private int promotion;

    private long invoiceId;

    private String productName;
    
    private int money;

    public double getMoney() {
        return number*cost - number*cost*promotion*0.01;
    }
}

package com.seasolutions.stock_management.model.view_model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Formula;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class InvoiceViewModel extends BaseViewModel {

    private Date deliverDate;
    private Date orderDate;
    private Date shippingDate;
    private String receivePlace;
    private String empName;

    private String customerTransactionName;

    private double totalMoney;


    @JsonIgnore
    private String employeeFirstName;

    @JsonIgnore
    private String employeeLastName;

    @JsonIgnore
    List<InvoiceDetailViewModel> invoiceDetails;

    public String getEmpName() {
        return employeeFirstName.concat(" ").concat(employeeLastName);
    }

    public double getTotalMoney() {
        return invoiceDetails.stream().mapToDouble(i -> i.getMoney()).sum();
    }
}

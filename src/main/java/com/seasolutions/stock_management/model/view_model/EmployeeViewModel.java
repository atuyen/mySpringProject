package com.seasolutions.stock_management.model.view_model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.internal.jline.internal.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EmployeeViewModel extends BaseViewModel{


    private String firstName;
    private String lastName;
    private String phone;
    private int salary;
    private @Nullable int support;
    private Date birthday;
    private Date workDay;

    private int totalSalary;
    private int totalInvoices;


    @JsonIgnore
    List<InvoiceViewModel> invoices;


    public int getTotalSalary() {
        return salary +support;
    }

    public int getTotalInvoices() {
        return invoices.size();
    }
}

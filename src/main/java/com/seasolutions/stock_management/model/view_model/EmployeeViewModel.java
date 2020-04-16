package com.seasolutions.stock_management.model.view_model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.internal.jline.internal.Nullable;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    private String email;

    @JsonIgnore
    private String password;

    @JsonIgnore
    List<InvoiceViewModel> invoices;


    @Builder.Default
    List<InviteViewModel> invites=new ArrayList<>();


    public int getTotalSalary() {
        return salary +support;
    }

    public int getTotalInvoices() {
        return invoices.size();
    }

    public List<InviteViewModel> getInvites() {
        return invites.stream().filter(i->i.getIsActive()).collect(Collectors.toList());
    }
}

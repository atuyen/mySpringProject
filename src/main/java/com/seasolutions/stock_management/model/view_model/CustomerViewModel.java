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
public class CustomerViewModel extends BaseViewModel{
    private String address;

    private String email;

    private String fax;

    private String name;

    private String phone;

    private String transactionName;





}

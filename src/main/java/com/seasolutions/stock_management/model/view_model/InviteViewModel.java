package com.seasolutions.stock_management.model.view_model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class InviteViewModel extends BaseViewModel {

    private Boolean isActive;
    private Boolean isManager;
    private Boolean isPrimary;
    private Boolean isSuperUser;
    private String companyName;
    private long companyId;





}

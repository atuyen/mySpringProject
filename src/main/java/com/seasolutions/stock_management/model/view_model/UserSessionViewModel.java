package com.seasolutions.stock_management.model.view_model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
public class UserSessionViewModel {

    private String ipAddress;

    private String authToken;


    private Long employeeId;


    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expires;

}

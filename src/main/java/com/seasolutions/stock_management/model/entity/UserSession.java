package com.seasolutions.stock_management.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="user_session")
@NamedQuery(name="UserSession.findAll", query="SELECT u FROM UserSession u")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserSession extends BaseModel {

    @Column(name="ip_address")
    private String ipAddress;

    @Column(name="auth_token")
    private String authToken;

    @Column(name="employee_id")
    private Long employeeId;


    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expires;



}

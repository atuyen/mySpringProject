package com.seasolutions.stock_management.model.entity;

import com.seasolutions.stock_management.model.view_model.BaseViewModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the invite database table.
 * 
 */
@Entity
@NamedQuery(name="Invite.findAll", query="SELECT i FROM Invite i")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Invite extends BaseModel implements Serializable  {
	private static final long serialVersionUID = 1L;


	@Builder.Default
	@Column(name="is_active")
	private Boolean isActive=true;



	@Builder.Default
	@Column(name="is_manager")
	private Boolean isManager=false;

	@Builder.Default
	@Column(name="is_primary")
	private Boolean isPrimary=true;


	@Builder.Default
	@Column(name="is_super_user")
	private Boolean isSuperUser=false;

	//bi-directional many-to-one association to Company
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="com_id")
	private Company company;

	//bi-directional many-to-one association to Employee
	@ManyToOne(fetch=FetchType.LAZY)
	private Employee employee;


}
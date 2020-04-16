package com.seasolutions.stock_management.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.internal.jline.internal.Nullable;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the employee database table.
 * 
 */
@Entity
@NamedQuery(name="Employee.findAll", query="SELECT e FROM Employee e")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Employee extends BaseModel {



	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;

	private String phone;

	private int salary;

	private @Nullable Integer support;


	private String email;

	@JsonIgnore
	private String password;


	@Column(name="work_day")
	@Temporal(TemporalType.TIMESTAMP)
	private Date workDay;

	@Temporal(TemporalType.TIMESTAMP)
	private Date birthday;

	//bi-directional many-to-one association to Order
	@Builder.Default
	@OneToMany(mappedBy="employee",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<Invoice> invoices = new ArrayList<>();


	//bi-directional many-to-one association to Invite
	@Builder.Default
	@OneToMany(mappedBy="employee",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<Invite> invites = new ArrayList<>();


	public Invoice addOrder(Invoice invoice) {
		getInvoices().add(invoice);
		invoice.setEmployee(this);
		return invoice;
	}

	public Invoice removeOrder(Invoice invoice) {
		getInvoices().remove(invoice);
		invoice.setEmployee(null);
		return invoice;
	}

	public Invite addInvite(Invite invite) {
		getInvites().add(invite);
		invite.setEmployee(this);

		return invite;
	}

	public Invite removeInvite(Invite invite) {
		getInvites().remove(invite);
		invite.setEmployee(null);

		return invite;
	}
}
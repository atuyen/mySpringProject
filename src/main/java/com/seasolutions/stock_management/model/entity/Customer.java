package com.seasolutions.stock_management.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the custormer database table.
 * 
 */
@Entity
@NamedQuery(name="Customer.findAll", query="SELECT c FROM Customer c")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Customer extends BaseModel {

	private String address;

	private String email;

	private String fax;

	private String name;

	private String phone;

	@Column(name="transaction_name")
	private String transactionName;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="customer")
	private List<Invoice> invoices;

	public Invoice addInvoice(Invoice invoice) {
		getInvoices().add(invoice);
		invoice.setCustomer(this);

		return invoice;
	}

	public Invoice removeInvoice(Invoice invoice) {
		getInvoices().remove(invoice);
		invoice.setCustomer(null);

		return invoice;
	}

}
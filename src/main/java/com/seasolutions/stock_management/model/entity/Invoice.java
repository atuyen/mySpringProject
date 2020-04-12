package com.seasolutions.stock_management.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the order database table.
 * 
 */
@Entity
@NamedQuery(name="Invoice.findAll", query="SELECT o FROM Invoice o")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Invoice extends BaseModel {

	@Column(name="deliver_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date deliverDate;

	@Column(name="order_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate;

	@Column(name="receive_place")
	private String receivePlace;

	@Column(name="shipping_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date shippingDate;

	//bi-directional many-to-one association to Custormer
	@ManyToOne(fetch=FetchType.LAZY)
	private Customer customer;

	//bi-directional many-to-one association to Employee
	@ManyToOne(fetch=FetchType.LAZY)
	private Employee employee;

	//bi-directional many-to-one association to OrderDetail
	@OneToMany(mappedBy="invoice")
	private List<InvoiceDetail> invoiceDetails;



	public InvoiceDetail addInvoiceDetail(InvoiceDetail invoiceDetail) {
		getInvoiceDetails().add(invoiceDetail);
		invoiceDetail.setInvoice(this);

		return invoiceDetail;
	}

	public InvoiceDetail removeInvoiceDetail(InvoiceDetail invoiceDetail) {
		getInvoiceDetails().remove(invoiceDetail);
		invoiceDetail.setInvoice(null);

		return invoiceDetail;
	}

}
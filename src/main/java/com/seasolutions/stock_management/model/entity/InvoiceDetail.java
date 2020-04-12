package com.seasolutions.stock_management.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the order_detail database table.
 * 
 */
@Entity
@Table(name="invoice_detail")
@NamedQuery(name="InvoiceDetail.findAll", query="SELECT o FROM InvoiceDetail o")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class InvoiceDetail extends BaseModel {


	private int cost;

	private int number;

	private Integer promotion;

	//bi-directional many-to-one association to Order
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="invoice_id")
	private Invoice invoice;

	//bi-directional many-to-one association to Product
	@ManyToOne(fetch=FetchType.LAZY)
	private Product product;


}
package com.seasolutions.stock_management.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the product database table.
 */
@Entity
@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Product extends BaseModel {


    private BigDecimal cost;
    private String name;
    private int number;


    //bi-directional many-to-one association to OrderDetail
    @OneToMany(mappedBy = "product")
    private List<InvoiceDetail> invoiceDetails;

    //bi-directional many-to-one association to Category
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cate_id")
    private Category category;

    //bi-directional many-to-one association to Company
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "com_id")
    private Company company;


    public InvoiceDetail addOrderDetail(InvoiceDetail invoiceDetail) {
        getInvoiceDetails().add(invoiceDetail);
        invoiceDetail.setProduct(this);

        return invoiceDetail;
    }

    public InvoiceDetail removeOrderDetail(InvoiceDetail invoiceDetail) {
        getInvoiceDetails().remove(invoiceDetail);
        invoiceDetail.setProduct(null);

        return invoiceDetail;
    }

}
package com.seasolutions.stock_management.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the company database table.
 */
@Entity
@NamedQuery(name = "Company.findAll", query = "SELECT c FROM Company c")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Company extends BaseModel {
    private String adress;

    private String email;

    private String fax;

    private String name;

    private String phone;

    @Column(name = "transaction_name")
    private String transactionName;

    //bi-directional many-to-one association to Product
    @OneToMany(mappedBy = "company")
    private List<Product> products;


    public Product addProduct(Product product) {
        getProducts().add(product);
        product.setCompany(this);

        return product;
    }

    public Product removeProduct(Product product) {
        getProducts().remove(product);
        product.setCompany(null);

        return product;
    }

}
package com.seasolutions.stock_management.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Category extends BaseModel {

	private String name;


	//bi-directional many-to-one association to Product
	@OneToMany(mappedBy="category",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
	@Builder.Default
	private List<Product> products = new ArrayList<>();

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setCategory(this);
		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setCategory(null);
		return product;
	}

}
package com.seasolutions.stock_management.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the email_file database table.
 * 
 */
@Entity
@Table(name="email_file")
@NamedQuery(name="EmailFile.findAll", query="SELECT e FROM EmailFile e")
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailFile extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;


	@Column(name="file_content")
	private byte[] fileContent;

	private String name;

	//bi-directional many-to-one association to SentEmail
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="email_id")
	private SentEmail sentEmail;


}
package com.seasolutions.stock_management.model.entity;

import com.seasolutions.stock_management.model.view_model.BaseViewModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the file_upload database table.
 *
 */
@Entity
@Table(name="file_upload")
@NamedQuery(name="FileUpload.findAll", query="SELECT f FROM FileUpload f")

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileUpload extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="file_data")
	private byte[] fileData;

	@Column(name="file_name")
	private String fileName;
}
package com.seasolutions.stock_management.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the sent_email database table.
 * 
 */
@Entity
@Table(name="sent_email")
@NamedQuery(name="SentEmail.findAll", query="SELECT s FROM SentEmail s")
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SentEmail extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="created_at")
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name="date_sent")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateSent;

	@Column(name="failed_count")
	@Builder.Default
	private Integer failedCount=0;

	@Column(name="invalid_address")
	@Builder.Default
	private Boolean invalidAddress=false;

	@Column(name="keep_history")
	@Builder.Default
	private Boolean keepHistory=true;

	@Column(name="last_try")
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastTry;

	private String message;

	@Column(name="recipient_address")
	private String recipientAddress;

	@Builder.Default
	private Boolean sent=false;

	private String subject;

	//bi-directional many-to-one association to EmailFile
	@OneToMany(mappedBy="sentEmail",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@Builder.Default
	private List<EmailFile> emailFiles=new ArrayList<>();




	public EmailFile addEmailFile(EmailFile emailFile) {
		getEmailFiles().add(emailFile);
		emailFile.setSentEmail(this);

		return emailFile;
	}

	public EmailFile removeEmailFile(EmailFile emailFile) {
		getEmailFiles().remove(emailFile);
		emailFile.setSentEmail(null);

		return emailFile;
	}

}
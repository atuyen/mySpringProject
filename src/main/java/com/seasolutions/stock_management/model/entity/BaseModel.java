package com.seasolutions.stock_management.model.entity;


import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@DynamicUpdate
public class BaseModel {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected Long id;


//    @Column(name="active_flag")
//    @Builder.Default
//    protected Boolean activeFlag=true;
//
//
//    @Column(name="create_date")
//    @CreationTimestamp
//    protected Timestamp createDate;
//
//
//    @Column(name="update_date")
//    @UpdateTimestamp
//    protected Timestamp updateDate;

}

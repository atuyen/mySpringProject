package com.seasolutions.stock_management.model.view_model;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseViewModel {
    protected long id;
//    protected Boolean activeFlag=true;
//    protected Timestamp createDate;
//    protected Timestamp updateDate;

}

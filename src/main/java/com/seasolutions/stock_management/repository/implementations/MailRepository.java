package com.seasolutions.stock_management.repository.implementations;

import com.seasolutions.stock_management.model.entity.SentEmail;
import com.seasolutions.stock_management.repository.base.BaseRepository;
import com.seasolutions.stock_management.repository.interfaces.IMailRepository;
import com.seasolutions.stock_management.repository.support.IDataManagerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Transactional
@Repository
public class MailRepository extends BaseRepository<SentEmail> implements IMailRepository {

    @Autowired
    IDataManagerTemplate dataManagerTemplate;



    @Override
    public List<SentEmail> getUnsentEmails( int maxFailedCount, int limit) {
        dataManagerTemplate.setLogQuery(false);

        String query = "Select e from SentEmail e "
                + " where e.sent = false AND e.failedCount < :maxFailedCount AND e.invalidAddress = false ";

        Map<String,Object> params = new HashMap();
        params.put("maxFailedCount",maxFailedCount);

        List<SentEmail> emails = findDataByQuery(query,params,0,limit);
        dataManagerTemplate.setLogQuery(true);

        return  emails;
    }
}

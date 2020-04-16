package com.seasolutions.stock_management.service.implementations;

import com.seasolutions.stock_management.model.entity.Company;
import com.seasolutions.stock_management.model.entity.Employee;
import com.seasolutions.stock_management.model.entity.Invite;
import com.seasolutions.stock_management.model.exception.BadArgumentException;
import com.seasolutions.stock_management.model.security.RegisterInfo;
import com.seasolutions.stock_management.repository.interfaces.ICompanyRepository;
import com.seasolutions.stock_management.repository.interfaces.IEmployeeRepository;
import com.seasolutions.stock_management.service.interfaces.IEmployeeService;
import com.seasolutions.stock_management.service.interfaces.IRegisterService;
import com.seasolutions.stock_management.util.EncodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RegisterService implements IRegisterService {
    @Autowired
    ICompanyRepository companyRepository;

    @Autowired
    IEmployeeRepository employeeRepository;

    @Autowired
    IEmployeeService employeeService;


    @Override
    public void register(RegisterInfo registerInfo) {
        //Check email ton tai chua,neu ton tai thi throw exception
        if(existedEmployee(registerInfo.getEmail())){
            throw new BadArgumentException(registerInfo.getEmail().concat(" exited"));
        }


        //Tao 1 company
        Company company = Company.builder()
                .name(registerInfo.getCompanyName())
                .build();
        company = companyRepository.add(company);

        //Tao 1 employee gan voi company do va voi role mac dinh la superuser, manager, primary

        Invite invite = Invite.builder()
                .company(company)
                .isManager(true)
                .isSuperUser(true)
                .build();

        Employee employee = Employee.builder()
                .email(registerInfo.getEmail())
                .password(EncodeUtils.encode(registerInfo.getPassword()))
                .build();

        employee.addInvite(invite);


        employeeRepository.add(employee);



    }


    private  boolean existedEmployee(String email){
        return  employeeService.findByEmail(email) !=null;
    }





}

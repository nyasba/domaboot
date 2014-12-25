package com.example.api;

import com.example.domain.CustomerEntity;
import com.example.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 顧客管理のRestAPI
 */
@RestController
public class CustomerRestController {

    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "/api/customers", method = RequestMethod.GET)
    List<CustomerEntity> getCustomers(){
        return customerService.findAll();
    }
}

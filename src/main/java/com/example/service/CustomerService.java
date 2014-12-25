package com.example.service;

import com.example.datasource.CustomerRepository;
import com.example.domain.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 顧客管理のService層
 */
@Service
@Transactional
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public List<CustomerEntity> findAll(){
        return customerRepository.findAllOrderByName();
    }
}

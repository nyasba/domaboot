package com.example.service;

import com.example.datasource.CustomerRepository;
import com.example.domain.CustomerEntity;
import org.seasar.doma.jdbc.SelectOptions;
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

    /**
     * ID順に顧客全件取得する
     * @return 顧客リスト
     */
    public List<CustomerEntity> findAll(){
        return customerRepository.findAllOrderById();
    }

    /**
     * ID順に顧客全件取得する。ページング処理付
     * @return 顧客リスト
     */
    public List<CustomerEntity> findAllWithPaging(SelectOptions selectOptions){
        return customerRepository.findAllOrderById(selectOptions);
    }

    /**
     * 名前順に顧客全件取得する
     * @return 顧客リスト
     */
    public List<CustomerEntity> findAllOrderByName(){
        return customerRepository.findAllOrderByName();
    }

    /**
     * ID指定で顧客情報を取得する
     * @param id ID
     * @return 顧客情報
     */
    public CustomerEntity findById(Integer id){
        return customerRepository.findById(id);
    }

    /**
     * 顧客情報を登録する
     * @param customerEntity 顧客情報
     */
    public void register(CustomerEntity customerEntity){
        customerRepository.insert(customerEntity);
    }

    /**
     * 顧客情報を修正する
     * @param customerEntity 顧客情報
     */
    public void modify(CustomerEntity customerEntity){
        customerRepository.update(customerEntity);
    }

    /**
     * 顧客情報を削除する
     * @param id 削除するID
     */
    public void delete(Integer id){
        CustomerEntity customerEntity = customerRepository.findById(id);
        customerRepository.delete(customerEntity);
    }
}

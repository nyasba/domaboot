package com.example.datasource;

import com.example.domain.CustomerEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;

import java.util.List;

@DomaRepository
@Dao
public interface CustomerRepository {

    @Select
    public List<CustomerEntity> findAllOrderByName();
}

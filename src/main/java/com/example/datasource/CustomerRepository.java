package com.example.datasource;

import com.example.domain.CustomerEntity;
import org.seasar.doma.*;
import org.seasar.doma.jdbc.Result;

import java.util.List;

@DomaRepository
@Dao
public interface CustomerRepository {

    @Select
    public List<CustomerEntity> findAllOrderByName();

    @Select
    public CustomerEntity findById(Integer id);

    @Insert
    public Result<CustomerEntity> insert(CustomerEntity customerEntity);

    @Update
    public Result<CustomerEntity> update(CustomerEntity customerEntity);

    @Delete
    public Result<CustomerEntity> delete(CustomerEntity customerEntity);
}

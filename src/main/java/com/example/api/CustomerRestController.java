package com.example.api;

import com.example.domain.CustomerEntity;
import com.example.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 顧客管理のRestAPI
 */
@RestController
public class CustomerRestController {

    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "/api/customers", method = RequestMethod.GET)
    List<CustomerEntity> getCustomers() {
        return customerService.findAll();
    }

    @RequestMapping(value = "/api/customers/{id}", method = RequestMethod.GET)
    CustomerEntity getCustomer(@PathVariable Integer id) {
        return customerService.findById(id);
    }

    @RequestMapping(value = "/api/customers", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    void registerCustomer(@Valid @RequestBody CustomerRequest request) {
        CustomerEntity customerEntity = new CustomerEntity(request.getLastName(), request.getFirstName());
        customerService.register(customerEntity);
    }

    @RequestMapping(value = "/api/customers/{id}", method = RequestMethod.PUT)
    void modifyCustomer(@PathVariable Integer id, @Valid @RequestBody CustomerRequest request) {
        CustomerEntity customerEntity = new CustomerEntity(id, request.getLastName(), request.getFirstName());
        customerService.modify(customerEntity);
    }

    @RequestMapping(value = "/api/customers/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCustomer(@PathVariable Integer id) {
        customerService.delete(id);
    }

}

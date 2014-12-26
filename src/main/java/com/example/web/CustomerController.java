package com.example.web;

import com.example.api.CustomerRequest;
import com.example.domain.CustomerEntity;
import com.example.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 顧客管理のController
 */
@Controller
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @ModelAttribute
    CustomerRequest setupRequest(){
        // @RequestMappingの処理より先にオブジェクトを生成しておき
        // それに対して値をセットするのでRequestオブジェクトにSetterが必要
        return new CustomerRequest();
    }

    @RequestMapping(value = "customers", method = RequestMethod.GET)
    String list(Model model){
        List<CustomerEntity> customerEntityList = customerService.findAll();
        model.addAttribute("customers",customerEntityList);
        return "customers/list";
    }

    @RequestMapping(value="customers/create", method = RequestMethod.POST)
    String create(@Validated CustomerRequest request, BindingResult result, Model model){
        if(result.hasErrors()){
            return list(model);
        }
        CustomerEntity customerEntity = new CustomerEntity(
                request.getLastName(),
                request.getFirstName()
        );

        customerService.register(customerEntity);
        return "redirect:/customers";
    }

    // submitボタンのname要素で指定した内容がparamsと一致しているものが呼び出される（request=list.htmlの編集ボタン）
    @RequestMapping(value = "customers/edit", params = "request", method = RequestMethod.GET)
    String editForm(@RequestParam Integer id, CustomerRequest request){
        CustomerEntity customerEntity = customerService.findById(id);
        BeanUtils.copyProperties(customerEntity, request);
        return "customers/edit";
    }

    @RequestMapping(value = "customers/edit", method=RequestMethod.POST)
    String edit(@RequestParam Integer id, @Validated CustomerRequest request, BindingResult result){
        if(result.hasErrors()){
            return editForm(id, request);
        }
        CustomerEntity customerEntity = new CustomerEntity(id, request.getLastName(),request.getFirstName());
        customerService.modify(customerEntity);

        return "redirect:/customers";
    }

    // submitボタンのname要素で指定した内容がparamsと一致しているものが呼び出される（request=edit.htmlの戻るボタン）
    @RequestMapping(value="customers/edit", params="goToTop")
    String goToTop(){
        return "redirect:/customers";
    }

    @RequestMapping(value="customers/delete", method = RequestMethod.POST)
    String delete(@RequestParam Integer id){
        customerService.delete(id);
        return "redirect:/customers";
    }
}

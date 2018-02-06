package com.company.product.featurematrix.customer;

import com.company.product.featurematrix.data.DataSource;

import java.util.ArrayList;
import java.util.List;


public class CustomerList {

    private List<Customer> customerList = new ArrayList<>();

    CustomerList(){

        customerList = DataSource.getCustomerList();

    }

    CustomerList(String customerName){

        customerList = DataSource.getSelectCustomerList(customerName);
    }

    public  List<Customer> getInstance(){
        return customerList;
    }
}

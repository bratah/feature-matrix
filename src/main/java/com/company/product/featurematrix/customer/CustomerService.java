package com.company.product.featurematrix.customer;

import com.company.product.featurematrix.data.DataSource;
import com.company.product.featurematrix.utility.Utility;

import java.util.ArrayList;
import java.util.List;


public class CustomerService {

    List<Customer> customerList = new ArrayList<>();

    CustomerService(){

        CustomerList cList = new CustomerList();

        this.customerList = cList.getInstance();
    }

    CustomerService(String custoemrName){

        CustomerList cList = new CustomerList(custoemrName);

        this.customerList = cList.getInstance();
    }

    public List<Customer> getCustomerList(){

        return customerList;
    }

    public String[][] getFeatureMatrix(){

        List<String>  globalFeatures = new ArrayList<>( DataSource.getFeatureMap().keySet());

        String[][] featureMatrix = Utility.loadFeatureMatrix(DataSource.getFeatureMap(), customerList);

        return featureMatrix;

    }
}

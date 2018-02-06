package com.company.product.featurematrix.customer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "CustomerServlet",
        urlPatterns = {"/customer"}
)

public class CustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerName = req.getParameter("name");

        CustomerService CustServ;

        if(customerName == null){

        CustServ = new CustomerService();}else {
            CustServ = new CustomerService(customerName);
        }
        forwardListCustomers(req, resp, CustServ.getFeatureMatrix());
    }

    private void forwardListCustomers(HttpServletRequest req, HttpServletResponse resp, String[][] featureMatrix)
            throws ServletException, IOException {
        String nextJSP = "/jsp/list-customers.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        req.setAttribute("featureMatrix", featureMatrix);
        dispatcher.forward(req, resp);
    }
}
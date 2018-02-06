package com.company.product.featurematrix.testcase;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(
        name = "TestcaseServlet",
        urlPatterns = {"/testcase"}
)

public class TestcaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String customer = req.getParameter("customer");
        String version = req.getParameter("version");
        String feature = req.getParameter("feature");
        String pfeature = req.getParameter("pfeature");

        List<Testcase> TESTCASE_LIST = new ArrayList<>();
        String fileName = System.getProperty("data") + "/customers/" + customer+"/"+version;
        String line = null;

        try {

            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {

                String[] vals = line.split(";");
                String groupList = vals[1].substring(1, vals[1].length() - 1);
                ArrayList<String> grps = new ArrayList<String>(Arrays.asList(groupList.split(",")));
                grps.replaceAll(String::trim);

                if (grps.contains(feature)) {

                    Testcase testcase = new Testcase(vals[0],vals[2]);
                    TESTCASE_LIST.add(testcase);
                }
            }

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
            ex.printStackTrace();
        }

            forwardListTestcases(req, resp, TESTCASE_LIST);
    }

    private void forwardListTestcases(HttpServletRequest req, HttpServletResponse resp, List<Testcase> testcaseList)
            throws ServletException, IOException {
        String nextJSP = "/jsp/list-testcases.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        req.setAttribute("testcaseList", testcaseList);
        dispatcher.forward(req, resp);
    }   

}

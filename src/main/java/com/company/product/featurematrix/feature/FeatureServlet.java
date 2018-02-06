package com.company.product.featurematrix.feature;

import com.company.product.featurematrix.data.DataSource;

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
import java.util.*;

@WebServlet(
        name = "FeatureServlet",
        urlPatterns = {"/feature"}
)

public class FeatureServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String customer = req.getParameter("customer");
        String version = req.getParameter("version");
        String pfeature = req.getParameter("pfeature");

        List<Feature> FEATURE_LIST = new ArrayList<>();
        String fileName = System.getProperty("data") + "/customers/" + customer+"/"+version;
        String line = null;
        HashMap<String, List<String>>  featureMap = DataSource.getFeatureMap();

        try {

            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {

                String[] vals = line.split(";");
                String groupList = vals[1].substring(1, vals[1].length() - 1);
                ArrayList<String> grps = new ArrayList<String>(Arrays.asList(groupList.split(",")));


                for(String g : grps){

                    try {
                        if (!featureMap.get(pfeature).contains(g.trim())) {
                            continue;
                        }
                    }catch (NullPointerException e){
                        // Suppress no key found
                    }

                    Optional<Feature> match
                            = FEATURE_LIST.stream()
                            .filter(e -> e.getName().trim().equals(g.trim()))
                            .findFirst();

                    if (match.isPresent()) {
                        match.get().addTestcase(vals[0],vals[2]);

                    } else {
                        Feature f = new Feature(g.trim());
                        f.addTestcase(vals[0],vals[2]);
                        FEATURE_LIST.add(f);
                    }
                }
            }

            bufferedReader.close();


            try{

                for (String f1 : featureMap.get(pfeature)) {

                    boolean found = false;

                    for (int j = 0; j < FEATURE_LIST.size(); j++) {
                        if (FEATURE_LIST.get(j).getName().equalsIgnoreCase(f1)) {
                            found = true;
                            break;
                        }
                    }

                    if (found == false) {
                        Feature f = new Feature(f1);
                        FEATURE_LIST.add(f);
                    }
                }
            }catch (NullPointerException e){
                // No featureMap addition for empty pfeature
            }


        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
            ex.printStackTrace();
        }

        forwardListFeatures(req, resp, FEATURE_LIST);
    }

    private void forwardListFeatures(HttpServletRequest req, HttpServletResponse resp, List<Feature> featureList)
            throws ServletException, IOException {
        String nextJSP = "/jsp/list-features.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        req.setAttribute("featureList", featureList);
        dispatcher.forward(req, resp);
    }   

}

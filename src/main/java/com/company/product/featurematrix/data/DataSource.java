package com.company.product.featurematrix.data;


import com.company.product.featurematrix.customer.Customer;
import com.company.product.featurematrix.feature.Feature;
import com.company.product.featurematrix.utility.Utility;

import java.io.*;
import java.util.*;

public class DataSource {

    public static HashMap<String, List<String>> getFeatureMap() {

        HashMap<String, List<String>> standardFeatureList = new HashMap<>();

        String featuresListFile = System.getProperty("data")+"/features.txt";
        String featureTree = null;

        try {

            FileReader fileReader = new FileReader(featuresListFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((featureTree = bufferedReader.readLine()) != null) {

                if (featureTree.contains("=")) {

                    String[] kv = featureTree.split("=");
                    List<String> subfeatures = new ArrayList<>();
                    if(kv.length>1) {
                        subfeatures = Arrays.asList(kv[1].split("\\s*,\\s*"));
                    }
                    standardFeatureList.put(kv[0], subfeatures);
                }

            }
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + featuresListFile + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + featuresListFile + "'");
            ex.printStackTrace();
        }

        return standardFeatureList;
    }

    public static HashMap<String,String> getCustomerVersionsMap(){

        HashMap<String,String> customerVersions = new HashMap<>();
        String cList = System.getProperty("data")+"/versions.txt";
        String singleCustomer = null;

        try {

            FileReader fileReader = new FileReader(cList);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((singleCustomer = bufferedReader.readLine()) != null) {

                String[] custVer = singleCustomer.trim().split("=");
                customerVersions.put(custVer[0], custVer[1]);
            }
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + cList + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + cList + "'");
            ex.printStackTrace();
        }

        return customerVersions;
    }


    public static List<Customer> getCustomerList(){

        List<Customer> customerList = new ArrayList<>();
        String fileName = System.getProperty("data") + "/customers/";
        String line = null;

        File folder = new File(fileName);
        File[] dirArray = folder.listFiles();

        for(File directory : dirArray){

            File latestVersion = Utility.getLatestVersion(directory.listFiles());

            HashMap<String,String> cv = DataSource.getCustomerVersionsMap();
            String cVersion;

            if (cv.containsKey(directory.getName())){
                cVersion = cv.get(directory.getName());
            }else {
                cVersion = latestVersion.getName();
            }

            Customer c = new Customer(directory.getName(), cVersion);

            try {

                FileReader fileReader = new FileReader(latestVersion.getAbsolutePath());
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while((line = bufferedReader.readLine()) != null) {

                    String[] vals = line.split(";");
                    String groupList = vals[1].substring(1, vals[1].length() - 1);
                    ArrayList<String> grps = new ArrayList<String>(Arrays.asList(groupList.split(",")));

                    for(String g : grps){

                        Optional<Feature> match
                                = c.getFeatures().stream()
                                .filter(e -> e.getName().trim().equals(g.trim()))
                                .findFirst();

                        if (match.isPresent()) {
                            match.get().addTestcase(vals[0],vals[2]);

                        } else {
                            Feature f = new Feature(g.trim());
                            f.addTestcase(vals[0],vals[2]);
                            c.addFeature(f);
                        }
                    }
                }

                customerList.add(c);
                bufferedReader.close();
            }
            catch(FileNotFoundException ex) {
                System.out.println("Unable to open file '" + fileName + "'");
            }
            catch(IOException ex) {
                System.out.println("Error reading file '" + fileName + "'");
                ex.printStackTrace();
            }
        }
        return customerList;
    }


    public static List<Customer> getSelectCustomerList(String customerName) {

        List<Customer> customerList = new ArrayList<>();
        String fileName = System.getProperty("data") + "/customers/";
        String line = null;

        File folder = new File(fileName);
        File[] dirArray = folder.listFiles();

        for(File directory : dirArray) {

            if (directory.getName().startsWith(customerName.replace("-latest","")) || directory.getName().startsWith("ers-std")) {

                File latestVersion = Utility.getLatestVersion(directory.listFiles());

                HashMap<String, String> cv = DataSource.getCustomerVersionsMap();
                String cVersion;

                if (cv.containsKey(directory.getName())) {
                    cVersion = cv.get(directory.getName());
                } else {
                    cVersion = latestVersion.getName();
                }

                Customer c = new Customer(directory.getName(), cVersion);

                try {

                    FileReader fileReader = new FileReader(latestVersion.getAbsolutePath());
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    while ((line = bufferedReader.readLine()) != null) {

                        String[] vals = line.split(";");
                        String groupList = vals[1].substring(1, vals[1].length() - 1);
                        ArrayList<String> grps = new ArrayList<String>(Arrays.asList(groupList.split(",")));

                        for (String g : grps) {

                            Optional<Feature> match
                                    = c.getFeatures().stream()
                                    .filter(e -> e.getName().trim().equals(g.trim()))
                                    .findFirst();

                            if (match.isPresent()) {
                                match.get().addTestcase(vals[0], vals[2]);

                            } else {
                                Feature f = new Feature(g.trim());
                                f.addTestcase(vals[0], vals[2]);
                                c.addFeature(f);
                            }
                        }
                    }

                    customerList.add(c);
                    bufferedReader.close();
                } catch (FileNotFoundException ex) {
                    System.out.println("Unable to open file '" + fileName + "'");
                } catch (IOException ex) {
                    System.out.println("Error reading file '" + fileName + "'");
                    ex.printStackTrace();
                }
            }
        }
        return customerList;

    }
}

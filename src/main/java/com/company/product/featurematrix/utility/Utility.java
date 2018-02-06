package com.company.product.featurematrix.utility;

import com.company.product.featurematrix.customer.Customer;

import java.io.*;
import java.util.*;

public class Utility {



    public static String[][] loadFeatureMatrix(HashMap<String,List<String>> featureSet, List<Customer> customerList){

        int rows = featureSet.size();
        int cols = customerList.size()+1;

        String[][] featureMatrix = new String[rows][cols];
        List<String> kset = new ArrayList<String>(featureSet.keySet());

        for(int i=0;i<rows; i++) {

            String featureName = kset.get(i);
            if(i>0) featureMatrix[i][0] = featureName;

            for (int j = 1; j < cols; j++) {

                if (i == 0) {
                    Customer cust = customerList.get(j-1);
                    featureMatrix[i][j] = cust.getName()+"\n"+cust.getVersion();
                } else {
                    featureMatrix[i][j] = Integer.toString(customerList.get(j-1).getSubFeaturesStatus(featureSet.get(featureName)));
                }
            }
        }

        return featureMatrix;

    }

    public static File getLatestVersion(File[] versions){

        File[] backendVersions = versions;

        Arrays.sort(backendVersions, new Comparator<File>() {
            public int compare(File f1, File f2) {

                String str1 = f1.getName().replace("-",".");
                String str2  = f1.getName().replace("-",".");

                //System.out.println("v1="+str1+" v2="+str2);

                String[] vals1 = str1.split("\\.");
                String[] vals2 = str2.split("\\.");

                int i = 0;

                while (i < vals1.length && i < vals2.length && vals1[i].equals(vals2[i])) {
                    i++;
                }
                if (i < vals1.length && i < vals2.length) {
                    int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
                    return Integer.signum(diff);
                }
                return Integer.signum(vals1.length - vals2.length);
            }
        });

        return backendVersions[versions.length-1];
    }

}


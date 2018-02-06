package com.company.product.featurematrix.customer;

import com.company.product.featurematrix.feature.Feature;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Customer {

    private long id;
    private String name;
    private String version;
    private List<Feature> features;

    public Customer(String name, String version) {
        this.name = name;
        this.version = version;
        this.features = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() { return version; }

    public void setVersion(String version) { this.version = version; }

    public List<Feature> getFeatures() {
        return features;
    }

    public void addFeature(Feature feature) {
        this.features.add(feature);
    }

    public int getFeatureStatus(String featureName) {

        for(Feature f : features) {

            if (f.getName().equalsIgnoreCase(featureName)) {
                try {
                    if(f.getStatus()) {
                        return 0;
                    } else {
                        return 1;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return 2;
    }

    public int getSubFeaturesStatus(List<String> featureNames) {

        int count = 0;

        for(String s : featureNames) {

            for (Feature f : features) {

                if (f.getName().equalsIgnoreCase(s.trim())) {
                    try {
                        if (f.getStatus()) {
                            count++;
                            break;
                        } else {
                            count--;
                            break;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if(count == 0) {
            return 2;
        }else if( count == featureNames.size()) {
            return 0;
        }else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return "Customer{" + ", name=" + name + ", version=" + version + "}";
    }

    public long getId() {
        return id;
    }
}

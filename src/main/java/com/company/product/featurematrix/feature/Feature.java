package com.company.product.featurematrix.feature;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Feature {

    private long id;
    private String name;
    private HashMap<String,String> testcases = new HashMap<>();

    public Feature(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTestcaseSize(){
        return testcases.size();
    }

    public boolean getStatus() throws ParseException {
        Iterator it = testcases.entrySet().iterator();
        double fraction = 0.0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            fraction += fractionToDouble(pair.getValue().toString());
            it.remove(); // avoids a ConcurrentModificationException
        }
        if (fraction == Math.floor(fraction) && fraction > 0.0) {return true;} else {return  false;}
    }

    public void addTestcase(String name, String status){
        testcases.put(name,status);
    }

    public static Double fractionToDouble(String fraction)
            throws ParseException {
        Double d = null;
        if (fraction != null) {
            if (fraction.contains("/")) {
                String[] numbers = fraction.split("/");
                if (numbers.length == 2) {
                    BigDecimal d1 = BigDecimal.valueOf(Double.valueOf(numbers[0]));
                    BigDecimal d2 = BigDecimal.valueOf(Double.valueOf(numbers[1]));
                    BigDecimal response = d1.divide(d2, MathContext.DECIMAL128);
                    d = response.doubleValue();
                }
            }
            else {
                d = Double.valueOf(fraction);
            }
        }
        if (d == null) {
            throw new ParseException(fraction, 0);
        }
        return d;
    }

    @Override
    public String toString() {
        return "Customer{" + ", name=" + name + ", testcases=" + testcases.toString() + "}";
    }

    public long getId() {
        return id;
    }
}

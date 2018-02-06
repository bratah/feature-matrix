package com.company.product.featurematrix.testcase;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;

public class Testcase {

    private long id;
    private String name;
    private String count;


    public Testcase(String name, String count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() throws ParseException { return count; }

    public boolean getStatus() throws ParseException {

        double fraction = fractionToDouble(count);
        if (fraction == new Double(1)) {return true;} else {return  false;}
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
        return "TestCase {" + ", name=" + name + ", status=" + count + "}";
    }

    public long getId() {
        return id;
    }
}

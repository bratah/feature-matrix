package com.company.product.featurematrix;

import org.apache.catalina.startup.Tomcat;

import java.util.Optional;

import static java.lang.System.exit;

public class Main {
    
    public static final Optional<String> PORT = Optional.ofNullable(System.getenv("PORT"));
    public static final Optional<String> HOSTNAME = Optional.ofNullable(System.getenv("HOSTNAME"));
    
    public static void main(String[] args) throws Exception {

        String DataPath = System.getProperty("data");
        if(DataPath == null){
            System.out.println("Usage: java -jar -Ddata=path-to-dir <jar file>");
            exit(1);
        }

        String contextPath = "/" ;
        String appBase = ".";
        Tomcat tomcat = new Tomcat();   
        tomcat.setPort(Integer.valueOf(PORT.orElse("8080") ));
        tomcat.setHostname(HOSTNAME.orElse("localhost"));
        tomcat.getHost().setAppBase(appBase);
        tomcat.addWebapp(contextPath, appBase);
        tomcat.start();
        tomcat.getServer().await();
    }
}
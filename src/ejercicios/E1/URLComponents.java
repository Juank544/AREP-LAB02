package src.ejercicios.E1;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class URLComponents {
    public static void main(String[] args){
        try {
            URL googleURL = new URL("http://www.google.com:8080/docs/index.html?parameter1=Juan#Camilo");
            System.out.println("Protocol: " + googleURL.getProtocol());
            System.out.println("Host: " + googleURL.getHost());
            System.out.println("Port: " + googleURL.getPort());
            System.out.println("Authority: " + googleURL.getAuthority());
            System.out.println("Path: " + googleURL.getPath());
            System.out.println("Query: " + googleURL.getQuery());
            System.out.println("File: " + googleURL.getFile());
            System.out.println("Ref.: " + googleURL.getRef());
        } catch (MalformedURLException e) {
            Logger.getLogger(URLComponents.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
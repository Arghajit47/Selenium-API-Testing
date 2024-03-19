package org.example;

import io.restassured.response.Response;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class CommonMethods {
    public void writeFile(Response response, String fileName) {
        if (response.statusCode() == 404) {
            System.out.println("API Response Status Code is : " + response.statusCode());
            System.out.println(response.getBody().asString());
        }
        else {
            System.out.println("API Response Status Code is : " + response.statusCode());
            System.out.println(response.getBody().asString());
            try (FileWriter fileWriter = new FileWriter(fileName)) {
                fileWriter.write(response.getBody().asString());
                System.out.println("API response successfully saved to " + fileName);
            } catch (IOException e) {
                System.out.println("Error occurred while writing to JSON file: " + e.getMessage());
            }
        }
    }

    public String readPropertiesFile(String propName) {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/data.properties")) {
            properties.load(fis);
            return properties.getProperty(propName);
        } catch (IOException e) {
            System.out.println("Error occurred while loading data.properties file: " + e.getMessage());
            return null;
        }
    }

}
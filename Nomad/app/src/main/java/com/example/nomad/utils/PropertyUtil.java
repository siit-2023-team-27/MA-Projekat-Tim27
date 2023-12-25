package com.example.nomad.utils;

import com.example.nomad.activities.HomeActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {
    private static Properties properties;
    public static String AUTH_PATH;

    private static final String configFilePath= "GradleScripts/local.properties";


    public static void ConfigFileReader() {


        File ConfigFile=new File(configFilePath);
        try {

            FileInputStream configFileReader=new FileInputStream(ConfigFile);
            properties = new Properties();

            try {
                properties.load(configFileReader);
                configFileReader.close();
            } catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
        }  catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException( configFilePath);
        }
        AUTH_PATH = properties.getProperty("AUTH_PATH");
    }
}

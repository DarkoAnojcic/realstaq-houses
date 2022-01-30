package com.realstaq.interview.houses.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Load {

    public static String aProperty(final String property) {

        try (InputStream input = Load.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();

            prop.load(input);
            return prop.getProperty(property);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public static String aFile(final URI uri) {
        try {
            return new String(Files.readAllBytes(Paths.get(Load.class.getClassLoader().getResource(uri.toString()).toURI())));
        } catch (IOException | URISyntaxException e) {
            throw new IllegalArgumentException("Unable to load a file!", e);
        }
    }
}

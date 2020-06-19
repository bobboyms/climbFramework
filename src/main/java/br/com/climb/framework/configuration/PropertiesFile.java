package br.com.climb.framework.configuration;

import br.com.climb.framework.execptions.ConfigFileException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFile extends ConfigFileBean implements ConfigFile {

    protected InputStream getInputStream(final String fileName) throws ConfigFileException {

        InputStream inputStream = PropertiesFile.class.getClassLoader().getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new ConfigFileException("configuration file not found in resource");
        }

        return inputStream;
    }

    protected Properties loadProperties(InputStream inputStream) throws IOException {
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }

    protected void generateConfigData(Properties properties) {
        super.setPackge(properties.getProperty("framework.package.url"));
        super.setSecurityUrl(properties.getProperty("framework.security.url"));
    }

    public PropertiesFile(String fileName) throws ConfigFileException, IOException {
        generateConfigData(loadProperties(getInputStream(fileName)));
    }
}
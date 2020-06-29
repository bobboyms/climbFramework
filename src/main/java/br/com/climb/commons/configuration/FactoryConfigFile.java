package br.com.climb.commons.configuration;

import br.com.climb.framework.execptions.ConfigFileException;
import com.google.common.base.Strings;

import java.io.IOException;

public class FactoryConfigFile {

    private static final String FILE_PROPERTIES = "properties";

    protected String getTypeFile(final String fileName) throws ConfigFileException {

        if (Strings.isNullOrEmpty(fileName)) {
            throw new ConfigFileException("the configuration file cannot be null or empty");
        }

        String[] values =  fileName.split("\\.");
        if (values.length == 1) {
            throw new ConfigFileException("unsupported configuration file");
        }

        return values[1].toLowerCase();
    }

    public ConfigFile getConfigFile(final String fileName) throws ConfigFileException, IOException {

        final String fileType = getTypeFile(fileName);

        if (fileType.equals(FILE_PROPERTIES)) {
            return new PropertiesFile(fileName);
        }

        throw new ConfigFileException("unsupported configuration file");

    }

}

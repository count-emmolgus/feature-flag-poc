package change.me.to.something.SomeKindOfDemo.featurereader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class PropertyFileReader implements IFeatureReader {

    private final String pathToPropertyFile = "";
    private Properties properties;
    private static Logger logger = LoggerFactory.getLogger(PropertyFileReader.class);

    public PropertyFileReader() {
        try (InputStream stream = new FileInputStream(pathToPropertyFile)) {
            this.properties = new Properties();
            this.properties.load(stream);
        } catch (IOException e) {
            logger.error("Unable to initiate the feature property reader", e);
        }
    }

    @Override
    public boolean isActive(String featureKey) {
        if (properties == null) {
            return false;
        }
        String value = (String) properties.getOrDefault(featureKey, "false");
        return "true".equalsIgnoreCase(value);
    }

    @Override
    public void updateFeatureValue(String featureKey, boolean newValue) {
        try (OutputStream stream = new FileOutputStream(pathToPropertyFile)) {
            properties.setProperty(featureKey, Boolean.toString(newValue));
            properties.store(stream, null);
        } catch (IOException e) {
            logger.error(String.format("Unable to update the file at %s with the new values <%s - %s>", pathToPropertyFile, featureKey, newValue), e);
        }
    }
}

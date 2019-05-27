package change.me.to.something.SomeKindOfDemo;

import change.me.to.something.SomeKindOfDemo.featurereader.IFeatureReader;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Service implements IService {

    /**
     * Injected via dependency injection. That way we don't need to define things Static....
     * Also since we want to store the feature-setting in the database we could hard-code it that
     * way.
     * HOWEVER, since we might want to be able to toggle the feature on the Tomcat aswell, and that one
     * doesn't have a database. It could make sense have some implementation of reading from a property-file.
     * <p>
     * It should be pointed out however, that since the Tomcat has access to the spider, via a spider-client
     * it should be able to use the same logic as an actual client to read the feature-flag.
     * Meaning that one doesn't have to use a FUCKING property-file to toggle the features.
     * Since it is stupid, and forces the users/admins to ensure consistency between servers...
     * (Counter-argument is that maybe one only want the feature to be active at a sub-set of Tomcats.
     * E.g. the implantation done to try and guarantee that not all Tomcats allow password-changes)
     */
    private IFeatureReader featureReader;

    private static Logger logger = LoggerFactory.getLogger(Service.class);

    private Cache<String, Boolean> cache;

    public Service(IFeatureReader featureReader) {
        this.featureReader = featureReader;
        this.cache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();
    }

    @Override
    public boolean isActive(String featureKey) {
        try {
            return cache.get(featureKey, () ->
                    featureReader.isActive(featureKey)
            );
        } catch (ExecutionException e) {
            logger.error("Unable to read the value from the cache and it's loader implementation", e);
            return false;
        }
    }

    @Override
    public void updateFeatureValue(String featureKey, boolean newValue) {
        this.featureReader.updateFeatureValue(featureKey, newValue);
        this.cache.invalidate(featureKey);
    }
}

package change.me.to.something.SomeKindOfDemo.clientaccessor;

import change.me.to.something.SomeKindOfDemo.IService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class FeatureAccessor implements IFeatureAccessor {

    private static Logger logger = LoggerFactory.getLogger(FeatureAccessor.class);

    /**
     * The reason that we use a cache here aswell as on the server is that we don't expect
     * teh value to actually change that often/at-all.
     * But it would be unnecessary to make a bunch of network calls just to get the same value all over again.
     * This can lead the the annoying edge-case of a client lagging behind by what should be at the most:
     * (This cache duration-time + Server cache duration-time.
     * Which in this case would be 10 minutes + 10 minutes = 20 minutes)
     * Which is sad, but truly an edge-case, and shouldn't lead to anything more than at the most little annoyance
     * for one user every year...
     */
    private Cache<String, Boolean> cache;

    private IService serverConnection;

    public FeatureAccessor() {
//        this.serverConnection = new Service()
        this.cache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();
    }

    @Override
    public boolean isActive(String featureKey) {
        try {
            return cache.get(featureKey, () ->
                    serverConnection.isActive(featureKey)
            );
        } catch (ExecutionException e) {
            logger.error("Unable to read the value from the cache and it's loader implementation", e);
            return false;
        }
    }

    @Override
    public void updateFeatureValue(String featureKey, boolean newValue) {
        serverConnection.updateFeatureValue(featureKey, newValue);
        cache.invalidate(featureKey);
    }
}

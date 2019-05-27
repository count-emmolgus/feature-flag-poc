package change.me.to.something.SomeKindOfDemo;

/**
 * Interface for the service that is used by both clients, aswell as other server-modules
 * to read whether the feature is toggled on or not.
 */
public interface IService {

    boolean isActive(String featureKey);

    void updateFeatureValue(String featureKey, boolean newValue);
}

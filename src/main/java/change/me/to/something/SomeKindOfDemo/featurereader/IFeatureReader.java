package change.me.to.something.SomeKindOfDemo.featurereader;

/**
 * Interface to allow different implementations of checking if the feature is active or not
 */
public interface IFeatureReader {

    boolean isActive(String featureKey);

    void updateFeatureValue(String featureKey, boolean newValue);
}

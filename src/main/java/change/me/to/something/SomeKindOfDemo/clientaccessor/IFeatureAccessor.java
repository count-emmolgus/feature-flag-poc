package change.me.to.something.SomeKindOfDemo.clientaccessor;

public interface IFeatureAccessor {

    boolean isActive(String featureKey);

    void updateFeatureValue(String featureKey, boolean newValue);
}

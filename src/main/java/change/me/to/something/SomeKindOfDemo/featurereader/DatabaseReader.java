package change.me.to.something.SomeKindOfDemo.featurereader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseReader implements IFeatureReader {

    private static Logger logger = LoggerFactory.getLogger(DatabaseReader.class);

    @Override
    public boolean isActive(String featureKey) {
        /*
        Simple logic for checking if the column for the row with the featureKey is true or false.
        Should be something along the lines of:
        SELECT feature_active FROM MySuperAwesomeDatabase WHERE feature_key = :?

        Notice that something that should be defined is to have the featureKey have the
        constraint of being unique. Which both makes sense, and can/will lead to strange issues
        if someone tries to add multiple rows...
        ####################################################
        # uid #        feature_key        # feature_active #
        #--------------------------------------------------#
        #  0  # se.cambio.mads.ConnectHSA #     TRUE       #
        #  1  # se.cambio.asdd.Yello      #     FALSE      #
        ####################################################
         */
        return false;
    }

    @Override
    public void updateFeatureValue(String featureKey, boolean newValue) {
        /*
        TODO Implement logic for updating the value of the database row, by the same logic as above
         */
    }
}

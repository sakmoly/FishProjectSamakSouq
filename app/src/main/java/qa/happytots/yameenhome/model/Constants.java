package qa.happytots.yameenhome.model;

import qa.happytots.yameenhome.BuildConfig;

public interface Constants {
    int SUCCESS_RESPONSE = 1;

    int SUCCESS_RESULT = 0;
    int FAILURE_RESULT = 1;
    String PACKAGE_NAME = BuildConfig.APPLICATION_ID;
    String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";
    String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";
    String RESULT_CODE = "Result code";
}

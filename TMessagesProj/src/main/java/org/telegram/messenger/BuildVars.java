/*
 * This is the source code of Telegram for Android v. 2.x.x.
 * It is licensed under GNU GPL v. 2 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright Nikolai Kudashov, 2013-2015.
 */

package org.telegram.messenger;

public class BuildVars {
    public static boolean DEBUG_VERSION = true;
    public static int BUILD_VERSION = BuildConfig.VERSION_CODE;
    public static int APP_ID = BuildConfig.APP_ID; //obtain your own APP_ID at https://core.telegram.org/api/obtaining_api_id
    public static String APP_HASH = BuildConfig.APP_HASH; //obtain your own APP_HASH at https://core.telegram.org/api/obtaining_api_id
    public static String HOCKEY_APP_HASH = BuildConfig.HOCKEYAPP_HASH;
    public static String GCM_SENDER_ID = "760348033672";
    public static String SEND_LOGS_EMAIL = BuildConfig.LOG_EMAIL;
    public static String BING_SEARCH_KEY = ""; //obtain your own KEY at https://www.bing.com/dev/en-us/dev-center
    public static String FOURSQUARE_API_KEY = ""; //obtain your own KEY at https://developer.foursquare.com/
    public static String FOURSQUARE_API_ID = ""; //obtain your own API_ID at https://developer.foursquare.com/
    public static String FOURSQUARE_API_VERSION = "20150326";
}

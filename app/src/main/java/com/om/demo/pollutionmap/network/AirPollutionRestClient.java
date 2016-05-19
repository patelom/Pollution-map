package com.om.demo.pollutionmap.network;

/**
 * Created by om on 19/05/16.
 */
import com.loopj.android.http.*;

public class AirPollutionRestClient {

        private static final String BASE_URL = "http://api.airpollution.online/";

        private static AsyncHttpClient client = new AsyncHttpClient();

        public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
            client.get(getAbsoluteUrl(url), params, responseHandler);
        }

        public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
            client.post(getAbsoluteUrl(url), params, responseHandler);
        }

        private static String getAbsoluteUrl(String relativeUrl) {
            return BASE_URL + relativeUrl;
        }
}

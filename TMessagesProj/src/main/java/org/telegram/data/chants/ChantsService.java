package org.telegram.data.chants;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.messenger.volley.Request;
import org.telegram.messenger.volley.RequestQueue;
import org.telegram.messenger.volley.Response;
import org.telegram.messenger.volley.VolleyError;
import org.telegram.messenger.volley.toolbox.JsonObjectRequest;
import org.telegram.messenger.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class ChantsService {

    private static final String URL = "http://fenegram.herokuapp.com/chants";

    private RequestQueue mRequestQueue;
    private JsonObjectRequest mLastRequest;

    public interface ChantsCallback {
        void onSuccess(List<Chant> botItems);

        void onFailure();
    }

    public ChantsService(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public boolean cancel() {
        if (mLastRequest != null) {
            mLastRequest.cancel();
            return true;
        }
        return false;
    }

    public boolean isInProgress() {
        return mLastRequest != null;
    }

    public boolean load(final ChantsCallback chantsCallback) {
        if (mLastRequest != null) {
            return false;
        }

        mLastRequest = new JsonObjectRequest(
                Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mLastRequest = null;
                        Log.d("FEN", response.toString());
                        final ArrayList<Chant> chants = new ArrayList<>();

                        try {
                            final JSONArray items = response.getJSONArray(
                                    "items");
                            final int length = items.length();
                            for (int i = 0; i < length; i++) {
                                final JSONObject item = items.getJSONObject(
                                        i);
                                String url = item.getString("url");
                                String lyrics = item.getString("lyrics");
                                String title = item.getString(
                                        "title");
                                final Chant botItem = new Chant(
                                        url,
                                        lyrics,
                                        title);
                                chants.add(botItem);
                            }
                            chantsCallback.onSuccess(chants);
                        } catch (JSONException e) {
                            chantsCallback.onFailure();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mLastRequest = null;
                chantsCallback.onFailure();
            }
        });
        mRequestQueue.add(mLastRequest);
        return true;
    }

}

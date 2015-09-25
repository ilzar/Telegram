// Copyright (c) 2015 Yandex LLC. All rights reserved.
// Author: Oleg Isupov <cypt4@yandex-team.ru>

package org.telegram.BotShop;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.messenger.volley.Network;
import org.telegram.messenger.volley.Request;
import org.telegram.messenger.volley.RequestQueue;
import org.telegram.messenger.volley.Response;
import org.telegram.messenger.volley.VolleyError;
import org.telegram.messenger.volley.toolbox.BasicNetwork;
import org.telegram.messenger.volley.toolbox.HurlStack;
import org.telegram.messenger.volley.toolbox.JsonObjectRequest;
import org.telegram.messenger.volley.toolbox.NoCache;

import java.util.ArrayList;

public class BotShopService {

    private static final String url = "http://127.0.0.1:9999";

    private RequestQueue mRequestQueue;
    private JsonObjectRequest mLastRequest;

    public interface BotShopCallback {
        void onSuccess(BotItems botItems);
        void onFailure();
    }

    public BotShopService(final Context context) {
        final Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(new NoCache(), network);
    }

    public boolean load(final BotShopCallback botShopCallback) {
        if (mLastRequest != null) {
            return false;
        }

        mLastRequest = new JsonObjectRequest(Request.Method.GET,
                                                             url, null,
                                                             new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                mLastRequest = null;
                Log.d("FEN", response.toString());
                final ArrayList<BotItem> botItems = new ArrayList<>();

                try {
                    final JSONArray items = response.getJSONArray("items");
                    final int length = items.length();
                    for (int i = 0; i < length; i++) {
                        String name = response.getString("name");
                        String image = response.getString("image");
                        String description = response.getString("description");
                        final BotItem botItem = new BotItem(name, image, description);
                        botItems.add(botItem);
                    }
                    botShopCallback.onSuccess(new BotItems(botItems));
                    return;
                } catch (JSONException e) {
                    botShopCallback.onFailure();
                    return;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mLastRequest = null;
                botShopCallback.onFailure();
            }
        });
        mRequestQueue.add(mLastRequest);
        return true;
    }
}

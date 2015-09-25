// Copyright (c) 2015 Yandex LLC. All rights reserved.
// Author: Oleg Isupov <cypt4@yandex-team.ru>

package org.telegram.BotShop;

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

public class BotShopService {

    private static final String URL = "http://fenegram-bot-store.herokuapp.com/list";

    private RequestQueue mRequestQueue;
    private JsonObjectRequest mLastRequest;

    public interface BotShopCallback {
        void onSuccess(BotItems botItems);
        void onFailure();
    }

    public BotShopService(final Context context) {
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

    public boolean load(final BotShopCallback botShopCallback) {
        if (mLastRequest != null) {
            return false;
        }

        mLastRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
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
                        final JSONObject item = items.getJSONObject(i);
                        String name = item.getString("name");
                        String image = item.getString("image");
                        String description = item.getString("description");
                        final BotItem botItem = new BotItem(name, description, image);
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

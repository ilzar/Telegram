// Copyright (c) 2015 Yandex LLC. All rights reserved.
// Author: Oleg Isupov <cypt4@yandex-team.ru>

package org.telegram.Ytils;

import android.content.Context;
import android.text.TextUtils;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

import java.util.ArrayList;
import java.util.List;

public class BotSubscriber2 {

    private static boolean initStarted = false;
    public static final BotSubscriber2 INSTANCE = new BotSubscriber2();

    private String mainBot = "fenerbahce_bot";

    private List<String> botNames = new ArrayList<>(); {
        botNames.add("tahminetbot");
        botNames.add("fenerbahce_bot");
        botNames.add("fenerbahce_vote_bot");
        botNames.add("fenetestbot");
        botNames.add("fenerbahcebetsbot");
    }

    public void init() {
        if (initStarted) {
            return;
        }

        for (String botName : botNames) {
            if (isBotSubscribed(botName)) {
                continue;
            }
            final TLRPC.User user = MessagesController.getInstance().getUser(botName);
            if (user == null) {
                TLRPC.TL_contacts_search req = new TLRPC.TL_contacts_search();
                req.q = botName;
                req.limit = 1;
                queryBots(req);
            } else {
                subscribe(user);
            }
        }
    }

    private void queryBots(final TLRPC.TL_contacts_search req) {
        ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
            @Override
            public void run(final TLObject response, final TLRPC.TL_error error) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        if (error == null) {
                            TLRPC.TL_contacts_found res = (TLRPC.TL_contacts_found) response;
                            if (res.users != null && res.users.size() > 0) {
                                final TLRPC.User user = res.users.get(0);
                                MessagesController.getInstance().putUser(user, false);
                                subscribe(user);
                            }
                        }
                    }
                });
            }
        }, ConnectionsManager.RequestFlagFailOnServerErrors);
    }

    private void checkBot(long userID, final Runnable successAction) {
        TLRPC.TL_messages_getHistory request = new TLRPC.TL_messages_getHistory();
        request.peer = MessagesController.getInputPeer((int)userID);
        request.limit = 1;
        ConnectionsManager.getInstance().sendRequest(request, new RequestDelegate() {
            @Override
            public void run(TLObject response, TLRPC.TL_error error) {
                if (error == null) {
                    final TLRPC.messages_Messages res = (TLRPC.messages_Messages) response;
                    if (res.messages.size() == 0) {
                        AndroidUtilities.runOnUIThread(successAction);
                    }
                }
            }
        });

    }

    private void subscribe(final TLRPC.User user) {
        if (TextUtils.equals(user.username, mainBot)) {
            DialogSorter.INSTANCE.setTopBotId(user.id);
        }

        checkBot(user.id, new Runnable() {
            @Override
            public void run() {
                SendMessagesHelper.getInstance()
                                  .sendMessage("/start", user.id, null, null, false, true);
                markBotSubscribed(user.username);
            }
        });
      }

    private boolean isBotSubscribed(String botName) {
        return ApplicationLoader.applicationContext
            .getSharedPreferences("YandexPreferences", Context.MODE_PRIVATE)
            .getBoolean("subscribed_" + botName.toLowerCase(), false);
    }

    private void markBotSubscribed(String botName) {
        ApplicationLoader.applicationContext
            .getSharedPreferences("YandexPreferences", Context.MODE_PRIVATE).edit()
            .putBoolean("subscribed_" + botName.toLowerCase(), true).apply();
    }
}

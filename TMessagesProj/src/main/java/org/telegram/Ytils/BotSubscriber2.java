// Copyright (c) 2015 Yandex LLC. All rights reserved.
// Author: Oleg Isupov <cypt4@yandex-team.ru>

package org.telegram.Ytils;

import android.text.TextUtils;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

import java.util.ArrayList;
import java.util.List;

public class BotSubscriber2 {

    public static final BotSubscriber2 INSTANCE = new BotSubscriber2();
    static boolean inited = false;

    private String mainBot = "fenerbahce_bot";

    private List<String> botNames = new ArrayList<>(); {
        botNames.add("tahminetbot");
        botNames.add("fenerbahce_bot");
        botNames.add("fenerbahce_vote_bot");
        botNames.add("fenetestbot");
        botNames.add("fenerbahcebetsbot");
    }

    public void init() {
        if (!inited) {
            inited = true;
        } else {
            return;
        }
        for (String botName : botNames) {
            final TLRPC.User user = MessagesController.getInstance().getUser(botName);
            if (user == null) {
                TLRPC.TL_contacts_search req = new TLRPC.TL_contacts_search();
                req.q = botName;
                req.limit = 1;
                queryBots(req);
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
                            final TLRPC.User user = res.users.get(0);
                            MessagesController.getInstance().putUser(user, false);
                            SendMessagesHelper.getInstance()
                                              .sendMessage("/start", user.id, null, null, false,
                                                           true);
                            if (TextUtils.equals(user.username, mainBot)) {
                                DialogSorter.INSTANCE.setTopBotId(user.id);
                            }
                        }
                    }
                });
            }
        }, ConnectionsManager.RequestFlagFailOnServerErrors);
    }
}

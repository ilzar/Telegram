// Copyright (c) 2015 Yandex LLC. All rights reserved.
// Author: Oleg Isupov <cypt4@yandex-team.ru>

package org.telegram.Ytils;

import android.content.Context;
import android.os.Handler;

import org.telegram.messenger.ChatObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.tgnet.TLRPC;

import java.util.ArrayList;
import java.util.List;

public class BotSubscriber {

    /**
     *
     *
     * username = {java.lang.String@830066175608} "AlertBot"
     status = null
     first_name = {java.lang.String@830066175440} "Alert Bot"
     photo = {org.telegram.tgnet.TLRPC$TL_userProfilePhoto@830066175744}
     phone = null
     last_name = null
     inactive = false
     access_hash = 4135671800012392206
     flags = 16427
     bot_info_version = 4
     id = 123238669
     disableFree = false
     */

    public static BotSubscriber INSTANCE = new BotSubscriber();

    private final List<Integer> defaultBots = new ArrayList<>(); {
        defaultBots.add(123238669);
    }

    private void addUser(final MessagesController messagesController, final int id) {
        final TLRPC.TL_userForeign_old2 user = new

            TLRPC.TL_userForeign_old2();
        user.phone = "333";
        user.id = id;
        user.inactive = false;
        user.access_hash = 4135671800012392206l;
        user.flags = 16427;
        user.bot_info_version = 4;
        user.disableFree = false;

        user.first_name = "AlertBot";
        user.last_name = "";
        user.status = null;
        user.photo = new TLRPC.TL_userProfilePhotoEmpty();

        messagesController.putUser(user, true);
    }

    public void putDefaultBots(final MessagesController messagesController) {
        for (final int id : defaultBots) {
            addUser(messagesController, id);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //                final SharedPreferences yandexPreferences =
                //                    context.getSharedPreferences("YandexPreferences", Context
                // .MODE_PRIVATE);

                final boolean inited = false;//yandexPreferences.getBoolean("inited", false);
                if (!inited) {
                    for (final int id : defaultBots) {
                        MessagesController.getInstance().unblockUser(id);
                        SendMessagesHelper.getInstance()
                                          .sendMessage("/start", id, null, null, false, true);
                    }
                    //                    yandexPreferences.edit().putBoolean("inited", true)
                    // .apply();
                }
                //            }
            }
        }, 10000);
    }

    public void sendStart(Context context) {
        //        final SharedPreferences yandexPreferences =
        //            context.getSharedPreferences("YandexPreferences", Context.MODE_PRIVATE);
        //        final boolean inited = false;//yandexPreferences.getBoolean("inited", false);
        //        if (!inited) {
        //            for (final int id : defaultBots) {
        //                SendMessagesHelper.getInstance()
        //                                  .sendMessage("/start", id, null, null, false, true);
        //            }
        //            yandexPreferences.edit().putBoolean("inited", true).apply();
        //        }
    }

}

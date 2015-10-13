// Copyright (c) 2015 Yandex LLC. All rights reserved.
// Author: Oleg Isupov <cypt4@yandex-team.ru>

package org.telegram.Ytils;

import android.content.Context;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

public class StickersManager {

    private static final String[] STICKER_SETS = new String[]{"fngrm_1", "fngrm_2"};

    public static final StickersManager INSTANCE = new StickersManager();

    private boolean started;
    private int counter = STICKER_SETS.length;

    public void installStickers(final Context context) {
        if (started) {
            return;
        }
        started = true;

        if (context.getSharedPreferences("YandexPreferences", Context.MODE_PRIVATE)
                   .getBoolean("StickersInstalled", false)) {
            return;
        }

        for (int i = 0; i < STICKER_SETS.length; i++) {
            TLRPC.TL_inputStickerSetShortName stickerset = new TLRPC.TL_inputStickerSetShortName();
            stickerset.short_name = STICKER_SETS[i];
            TLRPC.TL_messages_installStickerSet req = new TLRPC.TL_messages_installStickerSet();
            req.stickerset = stickerset;
            ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                @Override
                public void run(final TLObject response, final TLRPC.TL_error error) {
                    if (error == null) {
                        checkAllStickersInstalled(context);
                    }
                }
            });
        }
    }

    private void checkAllStickersInstalled(final Context context) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                counter--;
                if (counter == 0) {
                    StickersQuery.loadStickers(false, true);
//                    context.getSharedPreferences("YandexPreferences", Context.MODE_PRIVATE).edit()
//                           .putBoolean("StickersInstalled", true);
                }
            }
        });
    }

}

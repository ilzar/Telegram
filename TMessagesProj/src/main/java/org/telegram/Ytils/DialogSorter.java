// Copyright (c) 2015 Yandex LLC. All rights reserved.
// Author: Oleg Isupov <cypt4@yandex-team.ru>

package org.telegram.Ytils;

import org.telegram.tgnet.TLRPC;

import java.util.List;

public class DialogSorter {

    public static final DialogSorter INSTANCE = new DialogSorter();

    private int topBotId = 123238669;

    public void promoteBot(List<TLRPC.Dialog> dialogList) {
        if (dialogList == null) {
            return;
        }
        final int size = dialogList.size();
        for (int i = 0; i < size; i++) {
            final TLRPC.Dialog dialog = dialogList.get(i);
            if (dialog.id == topBotId) {
                dialogList.remove(i);
                dialogList.add(0, dialog);
                break;
            }
        }
    }

    public void setTopBotId(final int topBotId) {
        this.topBotId = topBotId;
    }
}

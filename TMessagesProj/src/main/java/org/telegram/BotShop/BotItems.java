// Copyright (c) 2015 Yandex LLC. All rights reserved.
// Author: Oleg Isupov <cypt4@yandex-team.ru>

package org.telegram.BotShop;

import java.util.Collections;
import java.util.List;

public class BotItems {
    public final List<BotItem> mBotItemList;

    public BotItems(final List<BotItem> botItemList) {
        mBotItemList = Collections.unmodifiableList(botItemList);
    }
}

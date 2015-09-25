// Copyright (c) 2015 Yandex LLC. All rights reserved.
// Author: Oleg Isupov <cypt4@yandex-team.ru>

package org.telegram.BotShop;

public class BotItem {
    public final String name;
    public final String description;
    public final String imageUrl;

    public BotItem(final String name, final String description, final String imageUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}

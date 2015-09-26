// Copyright (c) 2015 Yandex LLC. All rights reserved.
// Author: Oleg Isupov <cypt4@yandex-team.ru>

package org.telegram.BotShop;

public class BotItem {
    public final String name;
    public final String description;
    public final String imageUrl;
    private final String mId;

    public BotItem(final String name, final String description, final String imageUrl, String id) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        mId = id;
    }

    @Override
    public String toString() {
        return name;
    }
}

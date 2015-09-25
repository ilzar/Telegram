package org.telegram.messenger;

public class BotDescription {
    private final String name;
    private final String url;

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    private final String description;

    public BotDescription(String name, String url, String description) {
        this.name = name;
        this.url = url;
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }
}

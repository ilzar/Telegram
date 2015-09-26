package org.telegram.data.chants;

public class Chant {

    public final String url;
    public final String lyrics;
    public final String title;

    public Chant(String url, String lyrics, String title) {
        this.url = url;
        this.lyrics = lyrics;
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}

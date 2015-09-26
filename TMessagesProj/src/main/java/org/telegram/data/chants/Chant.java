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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chant chant = (Chant) o;

        if (url != null ? !url.equals(chant.url) : chant.url != null) return false;
        if (lyrics != null ? !lyrics.equals(chant.lyrics) : chant.lyrics != null) return false;
        return !(title != null ? !title.equals(chant.title) : chant.title != null);

    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (lyrics != null ? lyrics.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}

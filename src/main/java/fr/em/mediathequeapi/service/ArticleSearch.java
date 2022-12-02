package fr.em.mediathequeapi.service;


import fr.em.mediathequeapi.metier.Reference;

public class ArticleSearch {

    private long EAN13;
    private String string;
    private Reference format;
    private Reference genre;
    private int page;
    private int lgPage = 50;
    private Reference mediatheque;

    public ArticleSearch() {
        //Default no-argument constructor
    }

    public long getEAN13() {
        return EAN13;
    }

    public void setEAN13(long EAN13) {
        this.EAN13 = EAN13;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public void setFormat(Reference format) {
        this.format = format;
    }

    public Reference getFormat() {
        return format;
    }

    public void setGenre(Reference genre) {
        this.genre = genre;
    }

    public Reference getGenre() {
        return genre;
    }

    public void setMediatheque(Reference mediatheque) {
        this.mediatheque = mediatheque;
    }

    public Reference getMediatheque() {
        return mediatheque;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLgPage() {
        return lgPage;
    }

    public void setLgPage(int lgPage) {
        this.lgPage = lgPage;
    }
}

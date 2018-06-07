package com.sectordefectuoso.animecha;

public class Anime {
    private String Title;
    private String Description;
    private String Genre;
    private Integer Episodes;
    private Float EpisodeDuration;
    private String Studio;
    private String Poster;
    private Integer Year;

    public Anime(){

    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public Integer getEpisodes() {
        return Episodes;
    }

    public void setEpisodes(Integer episodes) {
        Episodes = episodes;
    }

    public Float getEpisodeDuration() {
        return EpisodeDuration;
    }

    public void setEpisodeDuration(Float episodeDuration) {
        EpisodeDuration = episodeDuration;
    }

    public String getStudio() {
        return Studio;
    }

    public void setStudio(String studio) {
        Studio = studio;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public Integer getYear() {
        return Year;
    }

    public void setYear(Integer year) {
        Year = year;
    }

    public Anime(String title, String description, String genre, Integer episodes, Float episodeDuration, String studio, String poster, Integer year) {
        Title = title;
        Description = description;
        Genre = genre;
        Episodes = episodes;
        EpisodeDuration = episodeDuration;
        Studio = studio;
        Poster = poster;
        Year = year;
    }
}

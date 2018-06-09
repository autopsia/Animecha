package com.sectordefectuoso.animecha;

public class Anime{
    private String Id;
    private String Title;
    private String Description;
    private String Genre;
    private String Episodes;
    private String EpisodeDuration;
    private String Studio;
    private String Poster;
    private String Year;

    public Anime(){

    }

    public Anime(String id, String title, String description, String genre, String episodes, String episodeDuration, String studio, String poster, String year) {
        Id = id;
        Title = title;
        Description = description;
        Genre = genre;
        Episodes = episodes;
        EpisodeDuration = episodeDuration;
        Studio = studio;
        Poster = poster;
        Year = year;
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

    public String getEpisodes() {
        return Episodes;
    }

    public void setEpisodes(String episodes) {
        Episodes = episodes;
    }

    public String getEpisodeDuration() {
        return EpisodeDuration;
    }

    public void setEpisodeDuration(String episodeDuration) {
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

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
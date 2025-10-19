package cineflix;

public class Movie {
    private int movieID; // PK.
    private String title, genre, synopsis, imagePath, createdAt;
    private int releaseYear, duration, copies;
    private double pricePerWeek;

    // Constructors
    public Movie() {}

    public Movie(String title, String genre, String synopsis, int releaseYear,
                 int duration, int copies, double pricePerWeek, String imagePath) {
        this.title = title;
        this.genre = genre;
        this.synopsis = synopsis;
        this.releaseYear = releaseYear;
        this.duration = duration;
        this.copies = copies;
        this.pricePerWeek = pricePerWeek;
        this.imagePath = imagePath;
    }

    // Getter method
    public int getMovieID() {
        return movieID;
    }
    public String getTitle() {
        return title;
    }
    public String getGenre() {
        return genre;
    }
    public String getSynopsis() {
        return synopsis;
    }
    public String getImagePath() {
        return imagePath;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public int getReleaseYear() {
        return releaseYear;
    }
    public int getDuration() {
        return duration;
    }
    public int getCopies() {
        return copies;
    }
    public double getPricePerWeek() {
        return pricePerWeek;
    }

    // Setter method
    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public void setCopies(int copies) {
        this.copies = copies;
    }
    public void setPricePerWeek(double pricePerWeek) {
        this.pricePerWeek = pricePerWeek;
    }
}
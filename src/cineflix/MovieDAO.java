package cineflix;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {
    private Connection conn;
    public static final String TABLE_NAME = "tblMovies";
    public static final String COL_ID = "movieID"; // PRIMARY KEY.
    public static final String COL_TITLE = "title";
    public static final String COL_GENRE = "genre";
    public static final String COL_SYNOPSIS = "synopsis";
    public static final String COL_YEAR = "releaseYear";
    public static final String COL_DURATION = "duration";
    public static final String COL_COPIES = "copies";
    public static final String COL_PRICE = "pricePerWeek";
    public static final String COL_IMAGEPATH = "imagePath";
    public static final String COL_CREATEDAT = "createdAt"; // DEFAULT CURRENT_TIMESTAMP.
    
    public MovieDAO() {};
    
    // Receives a DBConnection to enable CRUD operations on tblMovies.
    public MovieDAO(Connection conn) {
        this.conn = conn;
    }
    
    // Inserts the movie infor to the DB; exclude PK, createdAt;
    public void insertMovie(Movie movie) {
        String sql = 
                "INSERT INTO " + TABLE_NAME + " (" +
                COL_TITLE + ", " + COL_GENRE + ", " + COL_SYNOPSIS + ", " + COL_YEAR + 
                ", " + COL_DURATION + ", " + COL_COPIES + ", " + COL_PRICE + ", " + COL_IMAGEPATH + 
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getGenre());
            stmt.setString(3, movie.getSynopsis());
            stmt.setInt(4, movie.getReleaseYear());
            stmt.setInt(5, movie.getDuration());
            stmt.setInt(6, movie.getCopies());
            stmt.setDouble(7, movie.getPricePerWeek());
            stmt.setString(8, movie.getImagePath());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            Message.error("Database error: " + e.getMessage());
        }
    }
    
    // Gets all the movies and store returns a list.
    public List<Movie> getAllMovies() {
        List<Movie> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Movie m = new Movie();
                m.setMovieID(rs.getInt(COL_ID));
                m.setTitle(rs.getString(COL_TITLE));
                m.setGenre(rs.getString(COL_GENRE));
                m.setSynopsis(rs.getString(COL_SYNOPSIS));
                m.setReleaseYear(rs.getInt(COL_YEAR));
                m.setDuration(rs.getInt(COL_DURATION));
                m.setCopies(rs.getInt(COL_COPIES));
                m.setPricePerWeek(rs.getDouble(COL_PRICE));
                m.setImagePath(rs.getString(COL_IMAGEPATH));
                m.setCreatedAt(rs.getString(COL_CREATEDAT));

                list.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Message.error("Database error: " + e.getMessage());
        }
        return list;
    }

}
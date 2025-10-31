package cineflix;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {
    private Connection conn;
    
    public static final String TABLE_MOVIES = "tblMovies";
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
    
    // Inserts the movie infos to the DB; exclude PK, createdAt;
    public void insertMovie(Movie movie) {
        String sql = 
                "INSERT INTO " + TABLE_MOVIES + " (" +
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
            Message.error("Movie insertion failed:\n" + e.getMessage());
        }
    }
    
    // Gets all the movies and store returns a list.
    public List<Movie> getAllMovies() {
        List<Movie> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_MOVIES;

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
            Message.error("Error retrieving all movies:\n" + e.getMessage());
        }
        return list;
    }
    
    public List<Movie> getAllMoviesForUsers() {
        List<Movie> movies = new ArrayList<>();

        String sql = 
                "SELECT " + COL_ID + ", " + COL_TITLE + ", " + COL_GENRE + ", " + 
                COL_YEAR + ", " + COL_DURATION + ", " + COL_PRICE + ", " + COL_COPIES + 
                " FROM " + TABLE_MOVIES +
                " ORDER BY " + COL_TITLE + " ASC";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Movie m = new Movie();
                m.setMovieID(rs.getInt(COL_ID));
                m.setTitle(rs.getString(COL_TITLE));
                m.setGenre(rs.getString(COL_GENRE));
                m.setReleaseYear(rs.getInt(COL_YEAR));
                m.setDuration(rs.getInt(COL_DURATION));
                m.setPricePerWeek(rs.getDouble(COL_PRICE));
                m.setCopies(rs.getInt(COL_COPIES));
                movies.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Message.error("Error retrieving all Movies for Users:\n" + e.getMessage());
        }

        return movies;
    }

    // Retrieves imagepath directory from local folder by referencing its movie ID.
    public String getImagePathByMovieID(int movieID) {
        String imagePath = null;
        String sql = 
                "SELECT " + COL_IMAGEPATH + 
                " FROM " + TABLE_MOVIES + 
                " WHERE " + COL_ID + " = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, movieID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                imagePath = rs.getString(COL_IMAGEPATH);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Message.error("Error retrieving imagepath:\n" + e.getMessage());
        }

        return imagePath;
    }

    // Retrieve movie's synopsis inide the database by referencing its movie ID.
    public String getSynopsisByMovieID(int movieID) {
        String synopsis = null;
        String sql = 
                "SELECT " + COL_SYNOPSIS + 
                " FROM " + TABLE_MOVIES + 
                " WHERE " + COL_ID + " = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, movieID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                synopsis = rs.getString(COL_SYNOPSIS);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Message.error("Error retrieving synopsis:\n" + e.getMessage());
        }

        return synopsis;
    }

    // Updates existing movie information via a new movie model.
    public void updateMovie(Movie movie)  {
        String sql = 
                "UPDATE " + TABLE_MOVIES + 
                " SET " + COL_TITLE + " = ?, " + COL_GENRE  + " = ?, " + COL_SYNOPSIS + 
                " = ?, " + COL_YEAR + " = ?, " + COL_DURATION + " = ?, " + COL_COPIES + 
                " = ?, " + COL_PRICE + " = ?, " + COL_IMAGEPATH + " = ? " +
                "WHERE " + COL_ID + " = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getGenre());
            stmt.setString(3, movie.getSynopsis());
            stmt.setInt(4, movie.getReleaseYear());
            stmt.setInt(5, movie.getDuration());
            stmt.setInt(6, movie.getCopies());
            stmt.setDouble(7, movie.getPricePerWeek());
            stmt.setString(8, movie.getImagePath());
            stmt.setInt(9, movie.getMovieID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            Message.error("Movie update failed:\n" + e.getMessage());
        }
    }
    
    // Deletes an exising movie data from the database by referencing its primary key (movieID).
    public void deleteMovie(int movieID) {
        String sql = 
                "DELETE FROM " + TABLE_MOVIES + 
                " WHERE " + COL_ID + " = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, movieID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            Message.error("Movie deletion failed:\n" + e.getMessage());
        }
    }
    
    // Decrement copies by referencing its movieID.
    public boolean decrementMovieCopies(int movieID) {
        String sql = 
                "UPDATE " + TABLE_MOVIES + 
                " SET " + COL_COPIES + " = " + COL_COPIES + " - 1 "
                + "WHERE " + COL_ID + " = ? AND " + COL_COPIES + " > 0";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, movieID);
            int affected = pst.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            Message.error("Movie copy decremental failed:\n" + e.getMessage());
        }
        return false;
    }
    
    // Increment copies by referencing its movieID.
    public boolean incrementMovieCopies(int movieID) {
        String sql = 
                "UPDATE " + TABLE_MOVIES + 
                " SET " + COL_COPIES + " = " + COL_COPIES + " + 1 " +
                "WHERE " + COL_ID + " = ?";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, movieID);
            int affected = pst.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            Message.error("Movie copy incremental failed:\n" + e.getMessage());
        }
        return false;
    }
}
package cineflix;

import java.util.ArrayList;
import java.util.List;

public class SearchUtils {
    
    // Linear Search: accepts a list of movies and the keyword
    public static List<Movie> searchMoviesByTitle(List<Movie> movieList, String keyword) {
        keyword = keyword.toLowerCase();
        List<Movie> results = new ArrayList<>();

        for (Movie m : movieList) { // Loop thru movie list one by one and search for the searched keyword.
            if (m.getTitle().toLowerCase().contains(keyword)) {
                results.add(m);
            }
        }
        return results;
    }
    
    public static List<AdminRentalEntry> searchRentals(List<AdminRentalEntry> rentalList, String keyword) {
     keyword = keyword.toLowerCase();
     List<AdminRentalEntry> results = new ArrayList<>();

    for (AdminRentalEntry r : rentalList) { 
        // Loop thru rental list one by one and search for the searched keyword.
        if (String.valueOf(r.getRentalID()).contains(keyword) ||
            r.getFullName().toLowerCase().contains(keyword) ||
            r.getMovieTitle().toLowerCase().contains(keyword)) {
            results.add(r);
        }
    }
     return results;
    }
    
    public static List<AdminPaymentEntry> searchPayments(List<AdminPaymentEntry> paymentList, String keyword) {
        keyword = keyword.toLowerCase();
        List<AdminPaymentEntry> results = new ArrayList<>();

         for (AdminPaymentEntry p : paymentList) {
             // Loop thru payment list one by one and search for the searched keyword.
             if (String.valueOf(p.getRentalID()).contains(keyword) ||
                 (p.getAccountName() != null && p.getAccountName().toLowerCase().contains(keyword)) ||
                 (p.getMovieTitle() != null && p.getMovieTitle().toLowerCase().contains(keyword)) ||
                 (p.getPaymentStatus() != null && p.getPaymentStatus().toLowerCase().contains(keyword)) ||
                 (p.getRentalStatus() != null && p.getRentalStatus().toLowerCase().contains(keyword))) {
                 results.add(p);
             }
         }
         return results;
    }
}
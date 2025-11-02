package cineflix;

import java.util.ArrayList;
import java.util.List;

public class SearchUtils {
    
    // Linear Search: accepts a list of movies and the keyword.
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
    
    // Linear Search: accepts a list of rentals and the keyword.
    public static List<Rental> searchUserRentalHistory(List<Rental> rentalList, String keyword) {
        keyword = keyword.toLowerCase();
        List<Rental> results = new ArrayList<>();

        for (Rental r : rentalList) {
            // Loop thru rental list one by one and search for the searched keyword.
            if (String.valueOf(r.getRentalID()).contains(keyword) ||
                (r.getMovieTitle() != null && r.getMovieTitle().toLowerCase().contains(keyword)) ||
                (r.getRentalStage() != null && r.getRentalStage().toLowerCase().contains(keyword)) ||
                (r.getRentalStatus() != null && r.getRentalStatus().toLowerCase().contains(keyword))) {
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
    
    public static List<Payment> searchUserPayment(List<Payment> paymentList, String keyword) {
        keyword = keyword.toLowerCase();
        List<Payment> results = new ArrayList<>();

        for (Payment p : paymentList) {
            // Loop thru payment list one by one and search for the searched keyword.
            if (String.valueOf(p.getRentalID()).contains(keyword) ||
                p.getMovieTitle().toLowerCase().contains(keyword) ||
                p.getRentalStatus().toLowerCase().contains(keyword) ||
                p.getPaymentStatus().toLowerCase().contains(keyword)) {
                results.add(p);
            }
        }
        return results;
    }
    
    public static List<PersonalInfo> searchUserProfiles(List<PersonalInfo> users, String keyword) {
        keyword = keyword.toLowerCase();
        List<PersonalInfo> results = new ArrayList<>();

        for (PersonalInfo u : users) {
            if (String.valueOf(u.getAccountID()).contains(keyword) ||
                (u.getFullName() != null && u.getFullName().toLowerCase().contains(keyword)) ||
                (u.getUsername() != null && u.getUsername().toLowerCase().contains(keyword)) ||
                (u.getEmail() != null && u.getEmail().toLowerCase().contains(keyword))) {
                results.add(u);
            }
        }
        return results;
    }
}
package cineflix;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class SortUtils {
    
    // Sort by "Date Added" using Selection Sort, but compare by movieID.
    public static void sortMovieByDateAdded(List<Movie> movies) {
        for (int i = 0; i < movies.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < movies.size(); j++) {
                if (movies.get(j).getMovieID() < movies.get(minIndex).getMovieID()) {
                    minIndex = j;
                }
            }
            Movie temp = movies.get(minIndex);
            movies.set(minIndex, movies.get(i));
            movies.set(i, temp);
        }
    }

    // Sort by movie title using Selection Sort.
    public static void sortMovieByTitle(List<Movie> movies) {
        // Outer loop: move the boundary of the unsorted part one by one
        for (int i = 0; i < movies.size() - 1; i++) {
            // Assume the current index has the smallest element
            int minIndex = i;

            // Inner loop: find the actual smallest element in the unsorted part.
            for (int j = i + 1; j < movies.size(); j++) {
                // Compare titles ignoring case.
                if (movies.get(j).getTitle()
                        .compareToIgnoreCase(movies.get(minIndex).getTitle()) < 0) {
                    minIndex = j; // Update minIndex if a smaller title is found.
                }
            }
            // Swap the found minimum element with the first element of the unsorted part.
            Movie temp = movies.get(minIndex);
            movies.set(minIndex, movies.get(i));
            movies.set(i, temp);
        }
    }

    // Sort by genre using Selection Sort.
    public static void sortMovieByGenre(List<Movie> movies) {
        for (int i = 0; i < movies.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < movies.size(); j++) {
                if (movies.get(j).getGenre()
                        .compareToIgnoreCase(movies.get(minIndex).getGenre()) < 0) {
                    minIndex = j;
                }
            }
            Movie temp = movies.get(minIndex);
            movies.set(minIndex, movies.get(i));
            movies.set(i, temp);
        }
    }

    // Sort by release year using Selection Sort.
    public static void sortMovieByYear(List<Movie> movies) {
        for (int i = 0; i < movies.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < movies.size(); j++) {
                if (movies.get(j).getReleaseYear() < movies.get(minIndex).getReleaseYear()) {
                    minIndex = j;
                }
            }
            Movie temp = movies.get(minIndex);
            movies.set(minIndex, movies.get(i));
            movies.set(i, temp);
        }
    }
    
    // Sort by rental date (ascending) using Selection Sort
    public static void sortByRentalDate(List<AdminRentalEntry> rentals) {
        for (int i = 0; i < rentals.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < rentals.size(); j++) {
                Timestamp a = rentals.get(j).getRentalDate();
                Timestamp b = rentals.get(minIndex).getRentalDate();

                if (a != null && b != null && a.before(b)) {
                    minIndex = j;
                } else if (a != null && b == null) {
                    // Treat nulls as "last"
                    minIndex = j;
                }
            }
            AdminRentalEntry temp = rentals.get(minIndex);
            rentals.set(minIndex, rentals.get(i));
            rentals.set(i, temp);
        }
    }

    // Sort by return date (ascending) using Selection Sort.
    public static void sortByReturnDate(List<AdminRentalEntry> rentals) {
        for (int i = 0; i < rentals.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < rentals.size(); j++) {
                Timestamp a = rentals.get(j).getReturnDate();
                Timestamp b = rentals.get(minIndex).getReturnDate();

                if (a != null && b != null && a.before(b)) {
                    minIndex = j;
                } else if (a != null && b == null) {
                    minIndex = j;
                }
            }
            AdminRentalEntry temp = rentals.get(minIndex);
            rentals.set(minIndex, rentals.get(i));
            rentals.set(i, temp);
        }
    }

    // Sort by stage (alphabetical).
    public static void sortByStage(List<AdminRentalEntry> rentals) {
        for (int i = 0; i < rentals.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < rentals.size(); j++) {
                if (rentals.get(j).getRentalStage()
                        .compareToIgnoreCase(rentals.get(minIndex).getRentalStage()) < 0) {
                    minIndex = j;
                }
            }
            AdminRentalEntry temp = rentals.get(minIndex);
            rentals.set(minIndex, rentals.get(i));
            rentals.set(i, temp);
        }
    }

    // Sort by status (alphabetical).
    public static void sortByStatus(List<AdminRentalEntry> rentals) {
        for (int i = 0; i < rentals.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < rentals.size(); j++) {
                if (rentals.get(j).getRentalStatus()
                        .compareToIgnoreCase(rentals.get(minIndex).getRentalStatus()) < 0) {
                    minIndex = j;
                }
            }
            AdminRentalEntry temp = rentals.get(minIndex);
            rentals.set(minIndex, rentals.get(i));
            rentals.set(i, temp);
        }
    }
    
    public static void sortUserRentalByRentalDate(List<Rental> rentals) {
        for (int i = 0; i < rentals.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < rentals.size(); j++) {
                Timestamp a = rentals.get(j).getRentalDate();
                Timestamp b = rentals.get(minIndex).getRentalDate();

                if (a == null && b == null) continue;
                else if (a == null) continue; // null = last
                else if (b == null || a.before(b)) minIndex = j;
            }
            Rental temp = rentals.get(minIndex);
            rentals.set(minIndex, rentals.get(i));
            rentals.set(i, temp);
        }
    }

    public static void sortUserRentalByReturnDate(List<Rental> rentals) {
        for (int i = 0; i < rentals.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < rentals.size(); j++) {
                Timestamp a = rentals.get(j).getReturnDate();
                Timestamp b = rentals.get(minIndex).getReturnDate();

                if (a == null && b == null) continue;
                else if (a == null) continue;
                else if (b == null || a.before(b)) minIndex = j;
            }
            Rental temp = rentals.get(minIndex);
            rentals.set(minIndex, rentals.get(i));
            rentals.set(i, temp);
        }
    }

    public static void sortUserRentalByStage(List<Rental> rentals) {
        for (int i = 0; i < rentals.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < rentals.size(); j++) {
                String stageJ = rentals.get(j).getRentalStage();
                String stageMin = rentals.get(minIndex).getRentalStage();

                if (stageJ != null && stageMin != null &&
                    stageJ.compareToIgnoreCase(stageMin) < 0) {
                    minIndex = j;
                }
            }
            Rental temp = rentals.get(minIndex);
            rentals.set(minIndex, rentals.get(i));
            rentals.set(i, temp);
        }
    }

    public static void sortUserRentalByStatus(List<Rental> rentals) {
        for (int i = 0; i < rentals.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < rentals.size(); j++) {
                String statusJ = rentals.get(j).getRentalStatus();
                String statusMin = rentals.get(minIndex).getRentalStatus();

                if (statusJ != null && statusMin != null &&
                    statusJ.compareToIgnoreCase(statusMin) < 0) {
                    minIndex = j;
                }
            }
            Rental temp = rentals.get(minIndex);
            rentals.set(minIndex, rentals.get(i));
            rentals.set(i, temp);
        }
    }
    
   // Payment: sort by return date.
    public static void sortPaymentByReturnDate(List<AdminPaymentEntry> payments) {
        for (int i = 0; i < payments.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < payments.size(); j++) {
                if (payments.get(j).getReturnDate() != null &&
                    payments.get(minIndex).getReturnDate() != null &&
                    payments.get(j).getReturnDate().isBefore(payments.get(minIndex).getReturnDate())) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                AdminPaymentEntry temp = payments.get(minIndex);
                payments.set(minIndex, payments.get(i));
                payments.set(i, temp);
            }
        }
    }

    // Payment: sort by payment date.
    public static void sortPaymentByPaymentDate(List<AdminPaymentEntry> payments) {
        for (int i = 0; i < payments.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < payments.size(); j++) {
                LocalDateTime a = payments.get(j).getPaymentDate();
                LocalDateTime b = payments.get(minIndex).getPaymentDate();

                if (a == null && b == null) {
                    continue; // both null, no change
                } else if (a == null) {
                    continue; // null is "last", so don't update minIndex
                } else if (b == null) {
                    minIndex = j; // non-null beats null
                } else if (a.isBefore(b)) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                AdminPaymentEntry temp = payments.get(minIndex);
                payments.set(minIndex, payments.get(i));
                payments.set(i, temp);
            }
        }
    }

    // Payment: sort by payment status.
    public static void sortPaymentByPaymentStatus(List<AdminPaymentEntry> payments) {
        for (int i = 0; i < payments.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < payments.size(); j++) {
                String statusJ = payments.get(j).getPaymentStatus();
                String statusMin = payments.get(minIndex).getPaymentStatus();
                if (statusJ != null && statusMin != null &&
                    statusJ.compareToIgnoreCase(statusMin) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                AdminPaymentEntry temp = payments.get(minIndex);
                payments.set(minIndex, payments.get(i));
                payments.set(i, temp);
            }
        }
    }
    
    public static void sortUserPaymentsByPaymentDate(List<Payment> payments) {
        for (int i = 0; i < payments.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < payments.size(); j++) {
                Timestamp a = payments.get(j).getPaymentDate();
                Timestamp b = payments.get(minIndex).getPaymentDate();

                if (a == null && b == null) continue;
                else if (a == null) continue; // null = last
                else if (b == null || a.before(b)) minIndex = j;
            }
            Payment temp = payments.get(minIndex);
            payments.set(minIndex, payments.get(i));
            payments.set(i, temp);
        }
    }
    
    public static void sortUserPaymentsByPaidAmount(List<Payment> payments) {
        for (int i = 0; i < payments.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < payments.size(); j++) {
                double amountJ = payments.get(j).getAmount();
                double amountMin = payments.get(minIndex).getAmount();

                if (amountJ < amountMin) {
                    minIndex = j;
                }
            }
            Payment temp = payments.get(minIndex);
            payments.set(minIndex, payments.get(i));
            payments.set(i, temp);
        }
    }
    
    public static void sortUserPaymentsByRemainingBalance(List<Payment> payments) {
        for (int i = 0; i < payments.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < payments.size(); j++) {
                double balanceJ = payments.get(j).getRentalCost() + payments.get(j).getOverdueAmount() - payments.get(j).getAmount();
                double balanceMin = payments.get(minIndex).getRentalCost() + payments.get(minIndex).getOverdueAmount() - payments.get(minIndex).getAmount();

                if (balanceJ < balanceMin) {
                    minIndex = j;
                }
            }
            Payment temp = payments.get(minIndex);
            payments.set(minIndex, payments.get(i));
            payments.set(i, temp);
        }
    }

    public static void sortUserPaymentsByRentalStatus(List<Payment> payments) {
        for (int i = 0; i < payments.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < payments.size(); j++) {
                String statusJ = payments.get(j).getRentalStatus();
                String statusMin = payments.get(minIndex).getRentalStatus();

                if (statusJ != null && statusMin != null &&
                    statusJ.compareToIgnoreCase(statusMin) < 0) {
                    minIndex = j;
                }
            }
            Payment temp = payments.get(minIndex);
            payments.set(minIndex, payments.get(i));
            payments.set(i, temp);
        }
    }
    
    public static void sortUserPaymentsByRentalCost(List<Payment> payments) {
        for (int i = 0; i < payments.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < payments.size(); j++) {
                double costJ = payments.get(j).getRentalCost();
                double costMin = payments.get(minIndex).getRentalCost();

                if (costJ < costMin) {
                    minIndex = j;
                }
            }
            Payment temp = payments.get(minIndex);
            payments.set(minIndex, payments.get(i));
            payments.set(i, temp);
        }
    }
}
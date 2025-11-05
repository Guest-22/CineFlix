package cineflix;

import java.awt.Image;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class AdminDashboard extends javax.swing.JFrame {
    Connection conn;
    MovieDAO movieDAO;
    AccountDAO accountDAO;
    RentalDAO rentalDAO;
    PaymentDAO paymentDAO;
    
    public AdminDashboard() {
        initComponents();
        this.setSize(1315, 675);
        this.setLocationRelativeTo(null); // Center the frame
        lblHeader4.setText("Welcome, " + ActiveSession.loggedInUsername); // Welcome message to admin.
        
        conn = DBConnection.getConnection();
        if (conn == null) return;
        movieDAO = new MovieDAO(conn);
        accountDAO = new AccountDAO(conn);
        rentalDAO = new RentalDAO(conn);
        paymentDAO = new PaymentDAO(conn);
        
        populateSummaryDetails();
        populateTopMovies();
        populateTodaysHighlight();
    }
    
    private void populateSummaryDetails(){
        try{
            if (conn == null) return;
            movieDAO = new MovieDAO(conn);
            accountDAO = new AccountDAO(conn);
            rentalDAO = new RentalDAO(conn);
            paymentDAO = new PaymentDAO(conn);

            // Movies
            int totalMovies = movieDAO.getMovieTotalCount();
            int lowestMovieCopy = movieDAO.getLowestStockCount();
            int highestMovieCopy = movieDAO.getHighestStockCount();
            Timestamp latestCreatedAt = movieDAO.getLatestMovieCreatedAt();
            SimpleDateFormat fmt = new SimpleDateFormat("MMM dd, yyyy"); // Formats the date (e.g., Nov 01, 2025).
            String formattedDate = fmt.format(latestCreatedAt);
            lblLastAdded.setText(formattedDate);
            lblTotalMovies.setText(String.valueOf(totalMovies));
            lblLowestStock.setText(String.valueOf(lowestMovieCopy));
            lblHighestStock.setText(String.valueOf(highestMovieCopy));


           // Rentals
            int totalRentals = rentalDAO.getRentalTotalCount();
            int requested = rentalDAO.getStageCount("Requested");
            int ongoing = rentalDAO.getStatusCount("Ongoing");
            int returned = rentalDAO.getStatusCount("Returned");
            int late = rentalDAO.getStatusCount("Late");
            int cancelled = rentalDAO.getStageCount("Cancelled"); // If "Cancelled" is stored in stage

            lblTotalRentals.setText(String.valueOf(totalRentals));
            lblRequest.setText(String.valueOf(requested));
            lblOngoing.setText(String.valueOf(ongoing));
            lblReturned.setText(String.valueOf(returned));
            lblLate.setText(String.valueOf(late));
            lblCancelled.setText(String.valueOf(cancelled));

            // Users
            int totalUsers = accountDAO.getAccountTotalCount("User");
            lblTotalUsers.setText(String.valueOf(totalUsers)); 

            // Payments
            int totalPayments = paymentDAO.getPaymentTotalCount();
            double totalRevenue = paymentDAO.getSumAmount();
            lblTotalPayments.setText(String.valueOf(totalPayments));
            lblTotalRevenue.setText("₱" + String.format("%,.2f", totalRevenue));
        } catch(Exception e){
            Message.error("Error populating summary details:\n" + e.getMessage());
        }
    }
    
    // Display top rented movies.
    public void populateTopMovies() {
        try{
            if (conn == null) return;
            MovieDAO movieDAO = new MovieDAO(conn);
            List<Movie> topMovies = movieDAO.getTopRentedMovieSummaries(5);

            for (int i = 0; i < topMovies.size(); i++) {
                Movie movie = topMovies.get(i);
                String title = movie.getTitle();
                String imagePath = movie.getImagePath();
                ImageIcon scaledIcon = null;

                // Dynamically resize image to match JLabel dimensions
                if (imagePath != null && !imagePath.isEmpty()) {
                    ImageIcon originalIcon = new ImageIcon(imagePath);
                    JLabel targetLabel = switch (i) {
                        case 0 -> lblImage1;
                        case 1 -> lblImage2;
                        case 2 -> lblImage3;
                        case 3 -> lblImage4;
                        case 4 -> lblImage5;
                        default -> null;
                    };

                    if (targetLabel != null) {
                        int width = targetLabel.getWidth();
                        int height = targetLabel.getHeight();
                        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                        scaledIcon = new ImageIcon(scaledImage);
                    }
                }

                // Assign title and image to corresponding components
                switch (i) {
                    case 0:
                        txtaTitle1.setText(title);
                        lblImage1.setIcon(scaledIcon);
                        break;
                    case 1:
                        txtaTitle2.setText(title);
                        lblImage2.setIcon(scaledIcon);
                        break;
                    case 2:
                        txtaTitle3.setText(title);
                        lblImage3.setIcon(scaledIcon);
                        break;
                    case 3:
                        txtaTitle4.setText(title);
                        lblImage4.setIcon(scaledIcon);
                        break;
                    case 4:
                        txtaTitle5.setText(title);
                        lblImage5.setIcon(scaledIcon);
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e){
            Message.error("Error populating top rented movies:\n" + e.getMessage());
        }
    }
    
    // Retrieve all new entries today (CURDATE()).
    private void populateTodaysHighlight (){
        try {
            if(conn == null)return;
            movieDAO = new MovieDAO(conn);
            accountDAO = new AccountDAO(conn);
            rentalDAO = new RentalDAO(conn);
            paymentDAO = new PaymentDAO(conn);
            
            int todayMovieCount = movieDAO.getTodayMovieCount();
            int todayAccountCount = accountDAO.getTodayUserCount();
            int todayRentalCount = rentalDAO.getTodayRentalCount();
            int todayPaymentCount = paymentDAO.getTodayPaymentCount();
            
            lblMovieEntriesAdded.setText("- " + String.valueOf(todayMovieCount));
            lblAccountsCreated.setText("- " + String.valueOf(todayAccountCount));
            lblRentalRequestsReceived.setText("- " + String.valueOf(todayRentalCount));
            lblPaymentsCompleted.setText("- " + String.valueOf(todayPaymentCount));
        } catch (Exception e){
            Message.error("Error retrieving today's highlight:\n" + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        pnlSideNav = new javax.swing.JPanel();
        lblHeader1 = new javax.swing.JLabel();
        lblHeader3 = new javax.swing.JLabel();
        lblHeader2 = new javax.swing.JLabel();
        btnHome = new javax.swing.JButton();
        btnMovieInventory = new javax.swing.JButton();
        btnUserProfiles = new javax.swing.JButton();
        btnRentalLogs = new javax.swing.JButton();
        btnPaymentReview = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        scrlHeader4 = new javax.swing.JScrollPane();
        lblHeader4 = new javax.swing.JTextArea();
        pnlSummaryDetails = new javax.swing.JPanel();
        pnlMovie = new javax.swing.JPanel();
        lblMovie = new javax.swing.JLabel();
        lblTotalMoviesTitle = new javax.swing.JLabel();
        lblTotalMovies = new javax.swing.JLabel();
        lblLowestStockTitle = new javax.swing.JLabel();
        lblHighestStockTitle = new javax.swing.JLabel();
        lblLowestStock = new javax.swing.JLabel();
        lblHighestStock = new javax.swing.JLabel();
        lblLastAddedTitle = new javax.swing.JLabel();
        lblLastAdded = new javax.swing.JLabel();
        lblSummaryDetails = new javax.swing.JLabel();
        pnlRentals = new javax.swing.JPanel();
        lblRentals = new javax.swing.JLabel();
        lblOngoingTitle = new javax.swing.JLabel();
        lblReturnedTitle = new javax.swing.JLabel();
        lblLateTitle = new javax.swing.JLabel();
        lblCancelledTitle = new javax.swing.JLabel();
        lblRequestTitle = new javax.swing.JLabel();
        lblTotalRentalsTitle = new javax.swing.JLabel();
        lblTotalRentals = new javax.swing.JLabel();
        lblRequest = new javax.swing.JLabel();
        lblOngoing = new javax.swing.JLabel();
        lblReturned = new javax.swing.JLabel();
        lblLate = new javax.swing.JLabel();
        lblCancelled = new javax.swing.JLabel();
        pnlPayments = new javax.swing.JPanel();
        lblTotalRevenueTitle = new javax.swing.JLabel();
        lblPayments = new javax.swing.JLabel();
        lblTotalPaymentsTitle = new javax.swing.JLabel();
        lblTotalPayments = new javax.swing.JLabel();
        lblTotalRevenue = new javax.swing.JLabel();
        pnlUsers = new javax.swing.JPanel();
        lblTotalUsersTitle = new javax.swing.JLabel();
        lblUsers = new javax.swing.JLabel();
        lblTotalUsers = new javax.swing.JLabel();
        pnlTopRentedMovies = new javax.swing.JPanel();
        lblTopRentedMovies = new javax.swing.JLabel();
        lblImage1 = new javax.swing.JLabel();
        scrlImage1 = new javax.swing.JScrollPane();
        txtaTitle1 = new javax.swing.JTextArea();
        lblImage2 = new javax.swing.JLabel();
        scrlImage2 = new javax.swing.JScrollPane();
        txtaTitle2 = new javax.swing.JTextArea();
        lblImage3 = new javax.swing.JLabel();
        scrlImage3 = new javax.swing.JScrollPane();
        txtaTitle3 = new javax.swing.JTextArea();
        lblImage4 = new javax.swing.JLabel();
        scrlImage4 = new javax.swing.JScrollPane();
        txtaTitle4 = new javax.swing.JTextArea();
        lblImage5 = new javax.swing.JLabel();
        scrlImage5 = new javax.swing.JScrollPane();
        txtaTitle5 = new javax.swing.JTextArea();
        pnlTodaysHighlight = new javax.swing.JPanel();
        lblTodaysHighlight = new javax.swing.JLabel();
        lblMovieEntriesAddedTitle = new javax.swing.JLabel();
        lblAccountsCreatedTitle = new javax.swing.JLabel();
        lblRentalRequestsReceivedTitle = new javax.swing.JLabel();
        lblPaymentsCompletedTitle = new javax.swing.JLabel();
        lblMovieEntriesAdded = new javax.swing.JLabel();
        lblAccountsCreated = new javax.swing.JLabel();
        lblRentalRequestsReceived = new javax.swing.JLabel();
        lblPaymentsCompleted = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CineFlix: Home");
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(1315, 675));
        setResizable(false);
        setSize(new java.awt.Dimension(1315, 675));

        pnlMain.setBackground(new java.awt.Color(255, 255, 255));
        pnlMain.setMaximumSize(new java.awt.Dimension(1315, 675));
        pnlMain.setMinimumSize(new java.awt.Dimension(1315, 675));
        pnlMain.setPreferredSize(new java.awt.Dimension(1315, 675));

        pnlSideNav.setBackground(new java.awt.Color(0, 0, 0));

        lblHeader1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblHeader1.setForeground(new java.awt.Color(255, 255, 255));
        lblHeader1.setText("CineFlix");
        lblHeader1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblHeader3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblHeader3.setForeground(new java.awt.Color(255, 255, 255));
        lblHeader3.setText("Dashboard");
        lblHeader3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblHeader2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblHeader2.setForeground(new java.awt.Color(255, 255, 255));
        lblHeader2.setText("Admin");
        lblHeader2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btnHome.setBackground(new java.awt.Color(255, 255, 255));
        btnHome.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnHome.setForeground(new java.awt.Color(0, 0, 0));
        btnHome.setText("Home");
        btnHome.setFocusable(false);
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        btnMovieInventory.setBackground(new java.awt.Color(0, 0, 0));
        btnMovieInventory.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnMovieInventory.setForeground(new java.awt.Color(255, 255, 255));
        btnMovieInventory.setText("Movie Inventory");
        btnMovieInventory.setFocusable(false);
        btnMovieInventory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMovieInventoryActionPerformed(evt);
            }
        });

        btnUserProfiles.setBackground(new java.awt.Color(0, 0, 0));
        btnUserProfiles.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnUserProfiles.setForeground(new java.awt.Color(255, 255, 255));
        btnUserProfiles.setText("User Profiles");
        btnUserProfiles.setFocusable(false);
        btnUserProfiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserProfilesActionPerformed(evt);
            }
        });

        btnRentalLogs.setBackground(new java.awt.Color(0, 0, 0));
        btnRentalLogs.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnRentalLogs.setForeground(new java.awt.Color(255, 255, 255));
        btnRentalLogs.setText("Rental Logs");
        btnRentalLogs.setFocusable(false);
        btnRentalLogs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRentalLogsActionPerformed(evt);
            }
        });

        btnPaymentReview.setBackground(new java.awt.Color(0, 0, 0));
        btnPaymentReview.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnPaymentReview.setForeground(new java.awt.Color(255, 255, 255));
        btnPaymentReview.setText("Payment Review");
        btnPaymentReview.setFocusable(false);
        btnPaymentReview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaymentReviewActionPerformed(evt);
            }
        });

        btnLogout.setBackground(new java.awt.Color(0, 0, 0));
        btnLogout.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setText("Logout");
        btnLogout.setFocusable(false);
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        lblHeader4.setEditable(false);
        lblHeader4.setBackground(new java.awt.Color(0, 0, 0));
        lblHeader4.setColumns(20);
        lblHeader4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblHeader4.setForeground(new java.awt.Color(255, 255, 255));
        lblHeader4.setLineWrap(true);
        lblHeader4.setRows(3);
        lblHeader4.setWrapStyleWord(true);
        lblHeader4.setBorder(null);
        lblHeader4.setFocusable(false);
        scrlHeader4.setViewportView(lblHeader4);

        javax.swing.GroupLayout pnlSideNavLayout = new javax.swing.GroupLayout(pnlSideNav);
        pnlSideNav.setLayout(pnlSideNavLayout);
        pnlSideNavLayout.setHorizontalGroup(
            pnlSideNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnMovieInventory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnUserProfiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnRentalLogs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnPaymentReview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnLogout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlSideNavLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlSideNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrlHeader4, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlSideNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblHeader3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblHeader2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblHeader1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        pnlSideNavLayout.setVerticalGroup(
            pnlSideNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSideNavLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lblHeader1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHeader2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHeader3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrlHeader4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMovieInventory, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUserProfiles, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRentalLogs, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPaymentReview, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlSummaryDetails.setBackground(new java.awt.Color(0, 0, 0));

        pnlMovie.setBackground(new java.awt.Color(255, 255, 255));
        pnlMovie.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlMovieMouseClicked(evt);
            }
        });

        lblMovie.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblMovie.setForeground(new java.awt.Color(0, 0, 0));
        lblMovie.setText("Movie Details");

        lblTotalMoviesTitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTotalMoviesTitle.setForeground(new java.awt.Color(0, 0, 0));
        lblTotalMoviesTitle.setText("Total Movies:");

        lblTotalMovies.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblTotalMovies.setForeground(new java.awt.Color(0, 0, 0));
        lblTotalMovies.setText("N/A");

        lblLowestStockTitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblLowestStockTitle.setForeground(new java.awt.Color(0, 0, 0));
        lblLowestStockTitle.setText("Lowest Stock:");

        lblHighestStockTitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblHighestStockTitle.setForeground(new java.awt.Color(0, 0, 0));
        lblHighestStockTitle.setText("Highest Stock:");

        lblLowestStock.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblLowestStock.setForeground(new java.awt.Color(0, 0, 0));
        lblLowestStock.setText("N/A");

        lblHighestStock.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblHighestStock.setForeground(new java.awt.Color(0, 0, 0));
        lblHighestStock.setText("N/A");

        lblLastAddedTitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblLastAddedTitle.setForeground(new java.awt.Color(0, 0, 0));
        lblLastAddedTitle.setText("Last Added:");

        lblLastAdded.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblLastAdded.setForeground(new java.awt.Color(0, 0, 0));
        lblLastAdded.setText("N/A");

        javax.swing.GroupLayout pnlMovieLayout = new javax.swing.GroupLayout(pnlMovie);
        pnlMovie.setLayout(pnlMovieLayout);
        pnlMovieLayout.setHorizontalGroup(
            pnlMovieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMovieLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMovieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMovieLayout.createSequentialGroup()
                        .addComponent(lblTotalMoviesTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalMovies, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlMovieLayout.createSequentialGroup()
                        .addComponent(lblLowestStockTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblLowestStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlMovieLayout.createSequentialGroup()
                        .addComponent(lblHighestStockTitle)
                        .addGap(3, 3, 3)
                        .addComponent(lblHighestStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlMovieLayout.createSequentialGroup()
                        .addComponent(lblMovie)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlMovieLayout.createSequentialGroup()
                        .addComponent(lblLastAddedTitle)
                        .addGap(3, 3, 3)
                        .addComponent(lblLastAdded, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlMovieLayout.setVerticalGroup(
            pnlMovieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMovieLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMovie, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMovieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalMoviesTitle)
                    .addComponent(lblTotalMovies))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMovieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLowestStockTitle)
                    .addComponent(lblLowestStock, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMovieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHighestStockTitle)
                    .addComponent(lblHighestStock, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMovieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLastAddedTitle)
                    .addComponent(lblLastAdded, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblSummaryDetails.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblSummaryDetails.setForeground(new java.awt.Color(255, 255, 255));
        lblSummaryDetails.setText("Summary Details");
        lblSummaryDetails.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        pnlRentals.setBackground(new java.awt.Color(255, 255, 255));
        pnlRentals.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlRentalsMouseClicked(evt);
            }
        });

        lblRentals.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblRentals.setForeground(new java.awt.Color(0, 0, 0));
        lblRentals.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRentals.setText("Rentals Details");

        lblOngoingTitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblOngoingTitle.setForeground(new java.awt.Color(0, 0, 0));
        lblOngoingTitle.setText("Ongoing:");

        lblReturnedTitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblReturnedTitle.setForeground(new java.awt.Color(0, 0, 0));
        lblReturnedTitle.setText("Returned:");

        lblLateTitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblLateTitle.setForeground(new java.awt.Color(0, 0, 0));
        lblLateTitle.setText("Late:");

        lblCancelledTitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCancelledTitle.setForeground(new java.awt.Color(0, 0, 0));
        lblCancelledTitle.setText("Cancelled:");

        lblRequestTitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblRequestTitle.setForeground(new java.awt.Color(0, 0, 0));
        lblRequestTitle.setText("Request:");

        lblTotalRentalsTitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTotalRentalsTitle.setForeground(new java.awt.Color(0, 0, 0));
        lblTotalRentalsTitle.setText("Total Rentals:");

        lblTotalRentals.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblTotalRentals.setForeground(new java.awt.Color(0, 0, 0));
        lblTotalRentals.setText("N/A");

        lblRequest.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblRequest.setForeground(new java.awt.Color(0, 0, 0));
        lblRequest.setText("N/A");

        lblOngoing.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblOngoing.setForeground(new java.awt.Color(0, 0, 0));
        lblOngoing.setText("N/A");

        lblReturned.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblReturned.setForeground(new java.awt.Color(0, 0, 0));
        lblReturned.setText("N/A");

        lblLate.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblLate.setForeground(new java.awt.Color(0, 0, 0));
        lblLate.setText("N/A");

        lblCancelled.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblCancelled.setForeground(new java.awt.Color(0, 0, 0));
        lblCancelled.setText("N/A");

        javax.swing.GroupLayout pnlRentalsLayout = new javax.swing.GroupLayout(pnlRentals);
        pnlRentals.setLayout(pnlRentalsLayout);
        pnlRentalsLayout.setHorizontalGroup(
            pnlRentalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRentalsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRentalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlRentalsLayout.createSequentialGroup()
                        .addComponent(lblReturnedTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblReturned, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblRentals)
                    .addGroup(pnlRentalsLayout.createSequentialGroup()
                        .addComponent(lblTotalRentalsTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalRentals, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlRentalsLayout.createSequentialGroup()
                        .addComponent(lblRequestTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblRequest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlRentalsLayout.createSequentialGroup()
                        .addComponent(lblOngoingTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblOngoing, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(pnlRentalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRentalsLayout.createSequentialGroup()
                        .addComponent(lblCancelledTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCancelled, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                    .addGroup(pnlRentalsLayout.createSequentialGroup()
                        .addComponent(lblLateTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblLate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlRentalsLayout.setVerticalGroup(
            pnlRentalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRentalsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRentals, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRentalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRentalsLayout.createSequentialGroup()
                        .addGroup(pnlRentalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTotalRentalsTitle)
                            .addComponent(lblTotalRentals))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlRentalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblRequestTitle)
                            .addComponent(lblRequest)))
                    .addGroup(pnlRentalsLayout.createSequentialGroup()
                        .addGroup(pnlRentalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblLateTitle)
                            .addComponent(lblLate))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlRentalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCancelledTitle)
                            .addComponent(lblCancelled))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRentalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOngoingTitle)
                    .addComponent(lblOngoing))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRentalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReturnedTitle)
                    .addComponent(lblReturned))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlPayments.setBackground(new java.awt.Color(255, 255, 255));
        pnlPayments.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlPaymentsMouseClicked(evt);
            }
        });

        lblTotalRevenueTitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTotalRevenueTitle.setForeground(new java.awt.Color(0, 0, 0));
        lblTotalRevenueTitle.setText("Total Revenue:");

        lblPayments.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblPayments.setForeground(new java.awt.Color(0, 0, 0));
        lblPayments.setText("Payments Details");

        lblTotalPaymentsTitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTotalPaymentsTitle.setForeground(new java.awt.Color(0, 0, 0));
        lblTotalPaymentsTitle.setText("Total Payments:");

        lblTotalPayments.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblTotalPayments.setForeground(new java.awt.Color(0, 0, 0));
        lblTotalPayments.setText("N/A");

        lblTotalRevenue.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblTotalRevenue.setForeground(new java.awt.Color(0, 0, 0));
        lblTotalRevenue.setText("N/A");

        javax.swing.GroupLayout pnlPaymentsLayout = new javax.swing.GroupLayout(pnlPayments);
        pnlPayments.setLayout(pnlPaymentsLayout);
        pnlPaymentsLayout.setHorizontalGroup(
            pnlPaymentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPaymentsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPaymentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPaymentsLayout.createSequentialGroup()
                        .addComponent(lblTotalPaymentsTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalPayments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlPaymentsLayout.createSequentialGroup()
                        .addComponent(lblPayments)
                        .addGap(0, 115, Short.MAX_VALUE))
                    .addGroup(pnlPaymentsLayout.createSequentialGroup()
                        .addComponent(lblTotalRevenueTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalRevenue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(6, 6, 6))
        );
        pnlPaymentsLayout.setVerticalGroup(
            pnlPaymentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPaymentsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPayments, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPaymentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalPaymentsTitle)
                    .addComponent(lblTotalPayments))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPaymentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalRevenueTitle)
                    .addComponent(lblTotalRevenue))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlUsers.setBackground(new java.awt.Color(255, 255, 255));
        pnlUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlUsersMouseClicked(evt);
            }
        });

        lblTotalUsersTitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTotalUsersTitle.setForeground(new java.awt.Color(0, 0, 0));
        lblTotalUsersTitle.setText("Total Users:");

        lblUsers.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblUsers.setForeground(new java.awt.Color(0, 0, 0));
        lblUsers.setText("Users Details");

        lblTotalUsers.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblTotalUsers.setForeground(new java.awt.Color(0, 0, 0));
        lblTotalUsers.setText("N/A");

        javax.swing.GroupLayout pnlUsersLayout = new javax.swing.GroupLayout(pnlUsers);
        pnlUsers.setLayout(pnlUsersLayout);
        pnlUsersLayout.setHorizontalGroup(
            pnlUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUsersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUsersLayout.createSequentialGroup()
                        .addComponent(lblUsers)
                        .addGap(0, 59, Short.MAX_VALUE))
                    .addGroup(pnlUsersLayout.createSequentialGroup()
                        .addComponent(lblTotalUsersTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalUsers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlUsersLayout.setVerticalGroup(
            pnlUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUsersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalUsersTitle)
                    .addComponent(lblTotalUsers))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlSummaryDetailsLayout = new javax.swing.GroupLayout(pnlSummaryDetails);
        pnlSummaryDetails.setLayout(pnlSummaryDetailsLayout);
        pnlSummaryDetailsLayout.setHorizontalGroup(
            pnlSummaryDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSummaryDetailsLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlSummaryDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblSummaryDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlMovie, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(pnlUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlRentals, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlPayments, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        pnlSummaryDetailsLayout.setVerticalGroup(
            pnlSummaryDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSummaryDetailsLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblSummaryDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlSummaryDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlPayments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlMovie, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlUsers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlRentals, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pnlTopRentedMovies.setBackground(new java.awt.Color(0, 0, 0));

        lblTopRentedMovies.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblTopRentedMovies.setForeground(new java.awt.Color(255, 255, 255));
        lblTopRentedMovies.setText("Top Rented Movies of All Time");
        lblTopRentedMovies.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblImage1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblImage1.setForeground(new java.awt.Color(255, 255, 255));
        lblImage1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtaTitle1.setEditable(false);
        txtaTitle1.setBackground(new java.awt.Color(0, 0, 0));
        txtaTitle1.setColumns(20);
        txtaTitle1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtaTitle1.setForeground(new java.awt.Color(255, 255, 255));
        txtaTitle1.setLineWrap(true);
        txtaTitle1.setRows(4);
        txtaTitle1.setWrapStyleWord(true);
        txtaTitle1.setBorder(null);
        txtaTitle1.setFocusable(false);
        scrlImage1.setViewportView(txtaTitle1);

        lblImage2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblImage2.setForeground(new java.awt.Color(255, 255, 255));
        lblImage2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtaTitle2.setEditable(false);
        txtaTitle2.setBackground(new java.awt.Color(0, 0, 0));
        txtaTitle2.setColumns(20);
        txtaTitle2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtaTitle2.setForeground(new java.awt.Color(255, 255, 255));
        txtaTitle2.setLineWrap(true);
        txtaTitle2.setRows(4);
        txtaTitle2.setWrapStyleWord(true);
        txtaTitle2.setBorder(null);
        txtaTitle2.setFocusable(false);
        scrlImage2.setViewportView(txtaTitle2);

        lblImage3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblImage3.setForeground(new java.awt.Color(255, 255, 255));
        lblImage3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtaTitle3.setEditable(false);
        txtaTitle3.setBackground(new java.awt.Color(0, 0, 0));
        txtaTitle3.setColumns(20);
        txtaTitle3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtaTitle3.setForeground(new java.awt.Color(255, 255, 255));
        txtaTitle3.setLineWrap(true);
        txtaTitle3.setRows(4);
        txtaTitle3.setWrapStyleWord(true);
        txtaTitle3.setBorder(null);
        txtaTitle3.setFocusable(false);
        scrlImage3.setViewportView(txtaTitle3);

        lblImage4.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblImage4.setForeground(new java.awt.Color(255, 255, 255));
        lblImage4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtaTitle4.setEditable(false);
        txtaTitle4.setBackground(new java.awt.Color(0, 0, 0));
        txtaTitle4.setColumns(20);
        txtaTitle4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtaTitle4.setForeground(new java.awt.Color(255, 255, 255));
        txtaTitle4.setLineWrap(true);
        txtaTitle4.setRows(4);
        txtaTitle4.setWrapStyleWord(true);
        txtaTitle4.setBorder(null);
        txtaTitle4.setFocusable(false);
        scrlImage4.setViewportView(txtaTitle4);

        lblImage5.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblImage5.setForeground(new java.awt.Color(255, 255, 255));
        lblImage5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtaTitle5.setEditable(false);
        txtaTitle5.setBackground(new java.awt.Color(0, 0, 0));
        txtaTitle5.setColumns(20);
        txtaTitle5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtaTitle5.setForeground(new java.awt.Color(255, 255, 255));
        txtaTitle5.setLineWrap(true);
        txtaTitle5.setRows(4);
        txtaTitle5.setWrapStyleWord(true);
        txtaTitle5.setBorder(null);
        txtaTitle5.setFocusable(false);
        scrlImage5.setViewportView(txtaTitle5);

        javax.swing.GroupLayout pnlTopRentedMoviesLayout = new javax.swing.GroupLayout(pnlTopRentedMovies);
        pnlTopRentedMovies.setLayout(pnlTopRentedMoviesLayout);
        pnlTopRentedMoviesLayout.setHorizontalGroup(
            pnlTopRentedMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTopRentedMoviesLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlTopRentedMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTopRentedMovies, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlTopRentedMoviesLayout.createSequentialGroup()
                        .addGroup(pnlTopRentedMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblImage1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(scrlImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlTopRentedMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblImage2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(scrlImage2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlTopRentedMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblImage3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(scrlImage3, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlTopRentedMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblImage4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(scrlImage4, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlTopRentedMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblImage5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(scrlImage5, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(16, Short.MAX_VALUE))))
        );
        pnlTopRentedMoviesLayout.setVerticalGroup(
            pnlTopRentedMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTopRentedMoviesLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblTopRentedMovies)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTopRentedMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTopRentedMoviesLayout.createSequentialGroup()
                        .addGroup(pnlTopRentedMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblImage4, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlTopRentedMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblImage3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblImage2, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlTopRentedMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrlImage2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(scrlImage3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(scrlImage4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTopRentedMoviesLayout.createSequentialGroup()
                        .addComponent(lblImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(scrlImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlTopRentedMoviesLayout.createSequentialGroup()
                        .addComponent(lblImage5, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(scrlImage5, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18))
        );

        pnlTodaysHighlight.setBackground(new java.awt.Color(0, 0, 0));

        lblTodaysHighlight.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblTodaysHighlight.setForeground(new java.awt.Color(255, 255, 255));
        lblTodaysHighlight.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTodaysHighlight.setText("Today's Highlight");
        lblTodaysHighlight.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblMovieEntriesAddedTitle.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMovieEntriesAddedTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblMovieEntriesAddedTitle.setText("Movie Entries Added:");
        lblMovieEntriesAddedTitle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblAccountsCreatedTitle.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblAccountsCreatedTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblAccountsCreatedTitle.setText("Accounts Created:");
        lblAccountsCreatedTitle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblRentalRequestsReceivedTitle.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblRentalRequestsReceivedTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblRentalRequestsReceivedTitle.setText("Rental Requests Received:");
        lblRentalRequestsReceivedTitle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblPaymentsCompletedTitle.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPaymentsCompletedTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblPaymentsCompletedTitle.setText("Payments Completed:");
        lblPaymentsCompletedTitle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblMovieEntriesAdded.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMovieEntriesAdded.setForeground(new java.awt.Color(255, 255, 255));
        lblMovieEntriesAdded.setText("N/A");
        lblMovieEntriesAdded.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblAccountsCreated.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblAccountsCreated.setForeground(new java.awt.Color(255, 255, 255));
        lblAccountsCreated.setText("N/A");
        lblAccountsCreated.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblRentalRequestsReceived.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblRentalRequestsReceived.setForeground(new java.awt.Color(255, 255, 255));
        lblRentalRequestsReceived.setText("N/A");
        lblRentalRequestsReceived.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblPaymentsCompleted.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPaymentsCompleted.setForeground(new java.awt.Color(255, 255, 255));
        lblPaymentsCompleted.setText("N/A");
        lblPaymentsCompleted.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout pnlTodaysHighlightLayout = new javax.swing.GroupLayout(pnlTodaysHighlight);
        pnlTodaysHighlight.setLayout(pnlTodaysHighlightLayout);
        pnlTodaysHighlightLayout.setHorizontalGroup(
            pnlTodaysHighlightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTodaysHighlight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlTodaysHighlightLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTodaysHighlightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMovieEntriesAdded, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPaymentsCompleted, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblRentalRequestsReceived, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblAccountsCreated, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlTodaysHighlightLayout.createSequentialGroup()
                        .addGroup(pnlTodaysHighlightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMovieEntriesAddedTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAccountsCreatedTitle)
                            .addComponent(lblRentalRequestsReceivedTitle)
                            .addComponent(lblPaymentsCompletedTitle))
                        .addGap(0, 27, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlTodaysHighlightLayout.setVerticalGroup(
            pnlTodaysHighlightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTodaysHighlightLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblTodaysHighlight)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMovieEntriesAddedTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMovieEntriesAdded)
                .addGap(18, 18, 18)
                .addComponent(lblAccountsCreatedTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblAccountsCreated)
                .addGap(18, 18, 18)
                .addComponent(lblRentalRequestsReceivedTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRentalRequestsReceived, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblPaymentsCompletedTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPaymentsCompleted)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(pnlSideNav, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addComponent(pnlTopRentedMovies, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlTodaysHighlight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlSummaryDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSideNav, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(pnlSummaryDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlTopRentedMovies, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlTodaysHighlight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHomeActionPerformed

    private void btnMovieInventoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMovieInventoryActionPerformed
        new AdminMovieInventory().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnMovieInventoryActionPerformed

    private void btnUserProfilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserProfilesActionPerformed
        new AdminUserProfiles().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnUserProfilesActionPerformed

    private void btnRentalLogsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRentalLogsActionPerformed
        new AdminRentalLogs().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRentalLogsActionPerformed

    private void btnPaymentReviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaymentReviewActionPerformed
        new AdminPaymentReview().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnPaymentReviewActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        ActiveSession.clearSession(); // Clears active session.
        new Login().setVisible(true); // Returns to login frame.
        this.dispose();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void pnlMovieMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlMovieMouseClicked
        new AdminMovieInventory().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_pnlMovieMouseClicked

    private void pnlUsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlUsersMouseClicked
        new AdminUserProfiles().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_pnlUsersMouseClicked

    private void pnlRentalsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlRentalsMouseClicked
        new AdminRentalLogs().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_pnlRentalsMouseClicked

    private void pnlPaymentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlPaymentsMouseClicked
        new AdminPaymentReview().setVisible(true);
    }//GEN-LAST:event_pnlPaymentsMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMovieInventory;
    private javax.swing.JButton btnPaymentReview;
    private javax.swing.JButton btnRentalLogs;
    private javax.swing.JButton btnUserProfiles;
    private javax.swing.JLabel lblAccountsCreated;
    private javax.swing.JLabel lblAccountsCreatedTitle;
    private javax.swing.JLabel lblCancelled;
    private javax.swing.JLabel lblCancelledTitle;
    private javax.swing.JLabel lblHeader1;
    private javax.swing.JLabel lblHeader2;
    private javax.swing.JLabel lblHeader3;
    private javax.swing.JTextArea lblHeader4;
    private javax.swing.JLabel lblHighestStock;
    private javax.swing.JLabel lblHighestStockTitle;
    private javax.swing.JLabel lblImage1;
    private javax.swing.JLabel lblImage2;
    private javax.swing.JLabel lblImage3;
    private javax.swing.JLabel lblImage4;
    private javax.swing.JLabel lblImage5;
    private javax.swing.JLabel lblLastAdded;
    private javax.swing.JLabel lblLastAddedTitle;
    private javax.swing.JLabel lblLate;
    private javax.swing.JLabel lblLateTitle;
    private javax.swing.JLabel lblLowestStock;
    private javax.swing.JLabel lblLowestStockTitle;
    private javax.swing.JLabel lblMovie;
    private javax.swing.JLabel lblMovieEntriesAdded;
    private javax.swing.JLabel lblMovieEntriesAddedTitle;
    private javax.swing.JLabel lblOngoing;
    private javax.swing.JLabel lblOngoingTitle;
    private javax.swing.JLabel lblPayments;
    private javax.swing.JLabel lblPaymentsCompleted;
    private javax.swing.JLabel lblPaymentsCompletedTitle;
    private javax.swing.JLabel lblRentalRequestsReceived;
    private javax.swing.JLabel lblRentalRequestsReceivedTitle;
    private javax.swing.JLabel lblRentals;
    private javax.swing.JLabel lblRequest;
    private javax.swing.JLabel lblRequestTitle;
    private javax.swing.JLabel lblReturned;
    private javax.swing.JLabel lblReturnedTitle;
    private javax.swing.JLabel lblSummaryDetails;
    private javax.swing.JLabel lblTodaysHighlight;
    private javax.swing.JLabel lblTopRentedMovies;
    private javax.swing.JLabel lblTotalMovies;
    private javax.swing.JLabel lblTotalMoviesTitle;
    private javax.swing.JLabel lblTotalPayments;
    private javax.swing.JLabel lblTotalPaymentsTitle;
    private javax.swing.JLabel lblTotalRentals;
    private javax.swing.JLabel lblTotalRentalsTitle;
    private javax.swing.JLabel lblTotalRevenue;
    private javax.swing.JLabel lblTotalRevenueTitle;
    private javax.swing.JLabel lblTotalUsers;
    private javax.swing.JLabel lblTotalUsersTitle;
    private javax.swing.JLabel lblUsers;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlMovie;
    private javax.swing.JPanel pnlPayments;
    private javax.swing.JPanel pnlRentals;
    private javax.swing.JPanel pnlSideNav;
    private javax.swing.JPanel pnlSummaryDetails;
    private javax.swing.JPanel pnlTodaysHighlight;
    private javax.swing.JPanel pnlTopRentedMovies;
    private javax.swing.JPanel pnlUsers;
    private javax.swing.JScrollPane scrlHeader4;
    private javax.swing.JScrollPane scrlImage1;
    private javax.swing.JScrollPane scrlImage2;
    private javax.swing.JScrollPane scrlImage3;
    private javax.swing.JScrollPane scrlImage4;
    private javax.swing.JScrollPane scrlImage5;
    private javax.swing.JTextArea txtaTitle1;
    private javax.swing.JTextArea txtaTitle2;
    private javax.swing.JTextArea txtaTitle3;
    private javax.swing.JTextArea txtaTitle4;
    private javax.swing.JTextArea txtaTitle5;
    // End of variables declaration//GEN-END:variables
}
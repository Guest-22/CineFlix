package cineflix;

import java.sql.Connection;

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
    }
    
    private void populateSummaryDetails(){
        if (conn == null) return;
        movieDAO = new MovieDAO(conn);
        accountDAO = new AccountDAO(conn);
        rentalDAO = new RentalDAO(conn);
        paymentDAO = new PaymentDAO(conn);
        
        // Movies
        int totalMovies = movieDAO.getMovieTotalCount();
        lblEntriesAdded.setText(String.valueOf(totalMovies));
        
       // Rentals
        int totalRentals = rentalDAO.getRentalTotalCount();
        int requested = rentalDAO.getStageCount("Requested");
        int ongoing = rentalDAO.getStatusCount("Ongoing");
        int returned = rentalDAO.getStatusCount("Returned");
        int late = rentalDAO.getStatusCount("Late");
        int cancelled = rentalDAO.getStageCount("Cancelled"); // If "Cancelled" is stored in stage

        lblRentalsAdded.setText(String.valueOf(totalRentals));
        lblRequest.setText(String.valueOf(requested));
        lblOngoing.setText(String.valueOf(ongoing));
        lblReturned.setText(String.valueOf(returned));
        lblLate.setText(String.valueOf(late));
        lblCancelled.setText(String.valueOf(cancelled));

        // Users
        int totalUsers = accountDAO.getAccountTotalCount("User");
        lblAccountsCreated.setText(String.valueOf(totalUsers)); 

        // Payments
        int totalPayments = paymentDAO.getPaymentTotalCount();
        double totalRevenue = paymentDAO.getSumAmount();

        lblRecordsAdded.setText(String.valueOf(totalPayments));
        lblTotalRevenue.setText("₱" + String.format("%,.2f", totalRevenue));
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
        lblHeader4 = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        pnlSummary = new javax.swing.JPanel();
        pnlMovie = new javax.swing.JPanel();
        lblMovie = new javax.swing.JLabel();
        lblEntriesAddedTitle = new javax.swing.JLabel();
        lblEntriesAdded = new javax.swing.JLabel();
        lblSummaryDetails = new javax.swing.JLabel();
        pnlRentals = new javax.swing.JPanel();
        lblRentals = new javax.swing.JLabel();
        lblOngoingTitle = new javax.swing.JLabel();
        lblReturnedTitle = new javax.swing.JLabel();
        lblLateTitle = new javax.swing.JLabel();
        lblCancelledTitle = new javax.swing.JLabel();
        lblRequestTitle = new javax.swing.JLabel();
        lblRentalsAddedTitle = new javax.swing.JLabel();
        lblRentalsAdded = new javax.swing.JLabel();
        lblRequest = new javax.swing.JLabel();
        lblOngoing = new javax.swing.JLabel();
        lblReturned = new javax.swing.JLabel();
        lblLate = new javax.swing.JLabel();
        lblCancelled = new javax.swing.JLabel();
        pnlPayments = new javax.swing.JPanel();
        lblTotalRevenueTitle = new javax.swing.JLabel();
        lblPayments = new javax.swing.JLabel();
        lblRecordsAddedTitle = new javax.swing.JLabel();
        lblRecordsAdded = new javax.swing.JLabel();
        lblTotalRevenue = new javax.swing.JLabel();
        pnlUsers = new javax.swing.JPanel();
        lblAccountsCreatedTitle = new javax.swing.JLabel();
        lblUsers = new javax.swing.JLabel();
        lblAccountsCreated = new javax.swing.JLabel();
        btnRefresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CineFlix: Admin Dashboard");
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

        lblHeader4.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblHeader4.setForeground(new java.awt.Color(255, 255, 255));
        lblHeader4.setText("Welcome, Admin");
        lblHeader4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

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

        javax.swing.GroupLayout pnlSideNavLayout = new javax.swing.GroupLayout(pnlSideNav);
        pnlSideNav.setLayout(pnlSideNavLayout);
        pnlSideNavLayout.setHorizontalGroup(
            pnlSideNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSideNavLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlSideNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblHeader3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHeader2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHeader1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHeader4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(18, Short.MAX_VALUE))
            .addComponent(btnHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnMovieInventory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnUserProfiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnRentalLogs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnPaymentReview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnLogout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(lblHeader4)
                .addGap(44, 44, 44)
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
                .addContainerGap(206, Short.MAX_VALUE))
        );

        pnlSummary.setBackground(new java.awt.Color(0, 0, 0));

        pnlMovie.setBackground(new java.awt.Color(255, 255, 255));
        pnlMovie.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlMovieMouseClicked(evt);
            }
        });

        lblMovie.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblMovie.setForeground(new java.awt.Color(0, 0, 0));
        lblMovie.setText("Movies");

        lblEntriesAddedTitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblEntriesAddedTitle.setForeground(new java.awt.Color(0, 0, 0));
        lblEntriesAddedTitle.setText("Entries Added:");

        lblEntriesAdded.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblEntriesAdded.setForeground(new java.awt.Color(0, 0, 0));
        lblEntriesAdded.setText("N/A");

        javax.swing.GroupLayout pnlMovieLayout = new javax.swing.GroupLayout(pnlMovie);
        pnlMovie.setLayout(pnlMovieLayout);
        pnlMovieLayout.setHorizontalGroup(
            pnlMovieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMovieLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMovieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMovieLayout.createSequentialGroup()
                        .addComponent(lblMovie)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlMovieLayout.createSequentialGroup()
                        .addComponent(lblEntriesAddedTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEntriesAdded, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlMovieLayout.setVerticalGroup(
            pnlMovieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMovieLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMovie)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMovieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEntriesAddedTitle)
                    .addComponent(lblEntriesAdded))
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
        lblRentals.setText("Rentals");

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

        lblRentalsAddedTitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblRentalsAddedTitle.setForeground(new java.awt.Color(0, 0, 0));
        lblRentalsAddedTitle.setText("Rentals Added:");

        lblRentalsAdded.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblRentalsAdded.setForeground(new java.awt.Color(0, 0, 0));
        lblRentalsAdded.setText("N/A");

        lblRequest.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblRequest.setForeground(new java.awt.Color(0, 0, 0));
        lblRequest.setText("N/A");

        lblOngoing.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblOngoing.setForeground(new java.awt.Color(0, 0, 0));
        lblOngoing.setText("N/A");

        lblReturned.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblReturned.setForeground(new java.awt.Color(0, 0, 0));
        lblReturned.setText("N/A");

        lblLate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblLate.setForeground(new java.awt.Color(0, 0, 0));
        lblLate.setText("N/A");

        lblCancelled.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblCancelled.setForeground(new java.awt.Color(0, 0, 0));
        lblCancelled.setText("N/A");

        javax.swing.GroupLayout pnlRentalsLayout = new javax.swing.GroupLayout(pnlRentals);
        pnlRentals.setLayout(pnlRentalsLayout);
        pnlRentalsLayout.setHorizontalGroup(
            pnlRentalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRentalsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRentalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRentalsLayout.createSequentialGroup()
                        .addComponent(lblRentalsAddedTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblRentalsAdded, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE))
                    .addGroup(pnlRentalsLayout.createSequentialGroup()
                        .addComponent(lblRentals)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlRentalsLayout.createSequentialGroup()
                        .addComponent(lblRequestTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblRequest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlRentalsLayout.createSequentialGroup()
                        .addComponent(lblOngoingTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblOngoing, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlRentalsLayout.createSequentialGroup()
                        .addComponent(lblReturnedTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblReturned, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlRentalsLayout.createSequentialGroup()
                        .addComponent(lblLateTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblLate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlRentalsLayout.createSequentialGroup()
                        .addComponent(lblCancelledTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCancelled, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(6, 6, 6))
        );
        pnlRentalsLayout.setVerticalGroup(
            pnlRentalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRentalsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRentals)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRentalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRentalsAddedTitle)
                    .addComponent(lblRentalsAdded))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRentalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRequestTitle)
                    .addComponent(lblRequest))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRentalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOngoingTitle)
                    .addComponent(lblOngoing))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRentalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReturnedTitle)
                    .addComponent(lblReturned))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRentalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLateTitle)
                    .addComponent(lblLate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRentalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCancelledTitle)
                    .addComponent(lblCancelled))
                .addContainerGap(14, Short.MAX_VALUE))
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
        lblPayments.setText("Payments");

        lblRecordsAddedTitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblRecordsAddedTitle.setForeground(new java.awt.Color(0, 0, 0));
        lblRecordsAddedTitle.setText("Records Added:");

        lblRecordsAdded.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblRecordsAdded.setForeground(new java.awt.Color(0, 0, 0));
        lblRecordsAdded.setText("N/A");

        lblTotalRevenue.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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
                        .addComponent(lblRecordsAddedTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblRecordsAdded, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
                    .addGroup(pnlPaymentsLayout.createSequentialGroup()
                        .addComponent(lblPayments)
                        .addGap(0, 0, Short.MAX_VALUE))
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
                .addComponent(lblPayments)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPaymentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRecordsAddedTitle)
                    .addComponent(lblRecordsAdded))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPaymentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalRevenueTitle)
                    .addComponent(lblTotalRevenue))
                .addContainerGap(106, Short.MAX_VALUE))
        );

        pnlUsers.setBackground(new java.awt.Color(255, 255, 255));
        pnlUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlUsersMouseClicked(evt);
            }
        });

        lblAccountsCreatedTitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAccountsCreatedTitle.setForeground(new java.awt.Color(0, 0, 0));
        lblAccountsCreatedTitle.setText("Accounts Created:");

        lblUsers.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblUsers.setForeground(new java.awt.Color(0, 0, 0));
        lblUsers.setText("Users");

        lblAccountsCreated.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblAccountsCreated.setForeground(new java.awt.Color(0, 0, 0));
        lblAccountsCreated.setText("N/A");

        javax.swing.GroupLayout pnlUsersLayout = new javax.swing.GroupLayout(pnlUsers);
        pnlUsers.setLayout(pnlUsersLayout);
        pnlUsersLayout.setHorizontalGroup(
            pnlUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUsersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUsersLayout.createSequentialGroup()
                        .addComponent(lblUsers)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlUsersLayout.createSequentialGroup()
                        .addComponent(lblAccountsCreatedTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAccountsCreated, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlUsersLayout.setVerticalGroup(
            pnlUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUsersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblUsers)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAccountsCreatedTitle)
                    .addComponent(lblAccountsCreated))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlSummaryLayout = new javax.swing.GroupLayout(pnlSummary);
        pnlSummary.setLayout(pnlSummaryLayout);
        pnlSummaryLayout.setHorizontalGroup(
            pnlSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSummaryLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblSummaryDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlMovie, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(pnlUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlRentals, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(pnlPayments, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        pnlSummaryLayout.setVerticalGroup(
            pnlSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSummaryLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblSummaryDetails)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlPayments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlRentals, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlMovie, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlUsers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        btnRefresh.setBackground(new java.awt.Color(0, 0, 0));
        btnRefresh.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("Refresh");
        btnRefresh.setFocusable(false);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(pnlSideNav, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlSummary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSideNav, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(pnlSummary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 12, Short.MAX_VALUE)
                .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        populateSummaryDetails();
    }//GEN-LAST:event_btnRefreshActionPerformed

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
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRentalLogs;
    private javax.swing.JButton btnUserProfiles;
    private javax.swing.JLabel lblAccountsCreated;
    private javax.swing.JLabel lblAccountsCreatedTitle;
    private javax.swing.JLabel lblCancelled;
    private javax.swing.JLabel lblCancelledTitle;
    private javax.swing.JLabel lblEntriesAdded;
    private javax.swing.JLabel lblEntriesAddedTitle;
    private javax.swing.JLabel lblHeader1;
    private javax.swing.JLabel lblHeader2;
    private javax.swing.JLabel lblHeader3;
    private javax.swing.JLabel lblHeader4;
    private javax.swing.JLabel lblLate;
    private javax.swing.JLabel lblLateTitle;
    private javax.swing.JLabel lblMovie;
    private javax.swing.JLabel lblOngoing;
    private javax.swing.JLabel lblOngoingTitle;
    private javax.swing.JLabel lblPayments;
    private javax.swing.JLabel lblRecordsAdded;
    private javax.swing.JLabel lblRecordsAddedTitle;
    private javax.swing.JLabel lblRentals;
    private javax.swing.JLabel lblRentalsAdded;
    private javax.swing.JLabel lblRentalsAddedTitle;
    private javax.swing.JLabel lblRequest;
    private javax.swing.JLabel lblRequestTitle;
    private javax.swing.JLabel lblReturned;
    private javax.swing.JLabel lblReturnedTitle;
    private javax.swing.JLabel lblSummaryDetails;
    private javax.swing.JLabel lblTotalRevenue;
    private javax.swing.JLabel lblTotalRevenueTitle;
    private javax.swing.JLabel lblUsers;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlMovie;
    private javax.swing.JPanel pnlPayments;
    private javax.swing.JPanel pnlRentals;
    private javax.swing.JPanel pnlSideNav;
    private javax.swing.JPanel pnlSummary;
    private javax.swing.JPanel pnlUsers;
    // End of variables declaration//GEN-END:variables
}
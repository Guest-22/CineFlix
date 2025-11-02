package cineflix;

import java.sql.Connection;

public class UserDashboard extends javax.swing.JFrame {
    Connection conn;
    RentalDAO rentalDAO;
    PaymentDAO paymentDAO;
    
    public UserDashboard() {
        initComponents();
        this.setSize(1315, 675);
        this.setLocationRelativeTo(null); // Center the JFrame
        lblHeader4.setText("Welcome, " + ActiveSession.loggedInUsername); // Welcome message.
        
        conn = DBConnection.getConnection();
        if (conn == null) return;
        rentalDAO = new RentalDAO(conn);
        paymentDAO = new PaymentDAO(conn);
        
        populateRentalSummary();
    }
    
    // Populate rental summary.
    private void populateRentalSummary(){
        if (conn == null) return;
        rentalDAO = new RentalDAO(conn);
        paymentDAO = new PaymentDAO(conn);
        
        // Get all counts based-off current loggedInUserID.
        int pendingRequest = rentalDAO.getRentalStageByAccountID(ActiveSession.loggedInAccountID, "Requested");
        int ongoingRentals = rentalDAO.getRentalStatusByAccountID(ActiveSession.loggedInAccountID, "Ongoing");
        int returnedRentals = rentalDAO.getRentalStatusByAccountID(ActiveSession.loggedInAccountID, "Returned");
        int paidFullCount = paymentDAO.getPaymentStatusCountByAccountID(ActiveSession.loggedInAccountID, "Paid Full");
        int totalRentals = rentalDAO.getRentalCountByAccountID(ActiveSession.loggedInAccountID);
        
        // Diplay counts.
        lblPendingRequest.setText(String.valueOf(pendingRequest));   
        lblOngoingRentals.setText(String.valueOf(ongoingRentals));
        lblReturnedRentals.setText(String.valueOf(returnedRentals));
        lblPaidRentals.setText(String.valueOf(paidFullCount));
        lblTotalRentals.setText(String.valueOf(totalRentals)); 
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
        btnBrowseMovies = new javax.swing.JButton();
        btnRentalHistory = new javax.swing.JButton();
        btnMyPayments = new javax.swing.JButton();
        lblHeader4 = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        pnlRentalSummary = new javax.swing.JPanel();
        lblRentalSummary = new javax.swing.JLabel();
        lblReturnedRentalsTitle = new javax.swing.JLabel();
        lblTotalRentalsTitle = new javax.swing.JLabel();
        lblOngoingRentalsTitle = new javax.swing.JLabel();
        lblTotalRentals = new javax.swing.JLabel();
        lblOngoingRentals = new javax.swing.JLabel();
        lblReturnedRentals = new javax.swing.JLabel();
        lblPaidRentalsTitle = new javax.swing.JLabel();
        lblPaidRentals = new javax.swing.JLabel();
        lblPendingRequestTitle = new javax.swing.JLabel();
        lblPendingRequest = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CineFlix: User Dashboard");
        setMinimumSize(new java.awt.Dimension(1315, 675));
        setResizable(false);

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
        lblHeader2.setText("User");
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

        btnBrowseMovies.setBackground(new java.awt.Color(0, 0, 0));
        btnBrowseMovies.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnBrowseMovies.setForeground(new java.awt.Color(255, 255, 255));
        btnBrowseMovies.setText("Browse Movies");
        btnBrowseMovies.setFocusable(false);
        btnBrowseMovies.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseMoviesActionPerformed(evt);
            }
        });

        btnRentalHistory.setBackground(new java.awt.Color(0, 0, 0));
        btnRentalHistory.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnRentalHistory.setForeground(new java.awt.Color(255, 255, 255));
        btnRentalHistory.setText("Rental History");
        btnRentalHistory.setFocusable(false);
        btnRentalHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRentalHistoryActionPerformed(evt);
            }
        });

        btnMyPayments.setBackground(new java.awt.Color(0, 0, 0));
        btnMyPayments.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnMyPayments.setForeground(new java.awt.Color(255, 255, 255));
        btnMyPayments.setText("My Payments");
        btnMyPayments.setFocusable(false);
        btnMyPayments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMyPaymentsActionPerformed(evt);
            }
        });

        lblHeader4.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblHeader4.setForeground(new java.awt.Color(255, 255, 255));
        lblHeader4.setText("Welcome, User");
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btnHome, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
            .addComponent(btnBrowseMovies, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnRentalHistory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnMyPayments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(btnBrowseMovies, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRentalHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMyPayments, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(281, Short.MAX_VALUE))
        );

        pnlRentalSummary.setBackground(new java.awt.Color(0, 0, 0));

        lblRentalSummary.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblRentalSummary.setForeground(new java.awt.Color(255, 255, 255));
        lblRentalSummary.setText("Rental Summary");
        lblRentalSummary.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblReturnedRentalsTitle.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblReturnedRentalsTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblReturnedRentalsTitle.setText("Returned Rentals:");
        lblReturnedRentalsTitle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblTotalRentalsTitle.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblTotalRentalsTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalRentalsTitle.setText("Total Rentals:");
        lblTotalRentalsTitle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblOngoingRentalsTitle.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblOngoingRentalsTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblOngoingRentalsTitle.setText("Ongoing Rentals:");
        lblOngoingRentalsTitle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblTotalRentals.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblTotalRentals.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalRentals.setText("N/A");
        lblTotalRentals.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblOngoingRentals.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblOngoingRentals.setForeground(new java.awt.Color(255, 255, 255));
        lblOngoingRentals.setText("N/A");
        lblOngoingRentals.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblReturnedRentals.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblReturnedRentals.setForeground(new java.awt.Color(255, 255, 255));
        lblReturnedRentals.setText("N/A");
        lblReturnedRentals.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblPaidRentalsTitle.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblPaidRentalsTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblPaidRentalsTitle.setText("Paid Rentals");
        lblPaidRentalsTitle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblPaidRentals.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblPaidRentals.setForeground(new java.awt.Color(255, 255, 255));
        lblPaidRentals.setText("N/A");
        lblPaidRentals.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblPendingRequestTitle.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblPendingRequestTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblPendingRequestTitle.setText("Pending Request:");
        lblPendingRequestTitle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblPendingRequest.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblPendingRequest.setForeground(new java.awt.Color(255, 255, 255));
        lblPendingRequest.setText("N/A");
        lblPendingRequest.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout pnlRentalSummaryLayout = new javax.swing.GroupLayout(pnlRentalSummary);
        pnlRentalSummary.setLayout(pnlRentalSummaryLayout);
        pnlRentalSummaryLayout.setHorizontalGroup(
            pnlRentalSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRentalSummaryLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(pnlRentalSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRentalSummaryLayout.createSequentialGroup()
                        .addComponent(lblOngoingRentalsTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblOngoingRentals, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlRentalSummaryLayout.createSequentialGroup()
                        .addComponent(lblReturnedRentalsTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblReturnedRentals, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlRentalSummaryLayout.createSequentialGroup()
                        .addComponent(lblPaidRentalsTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPaidRentals, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlRentalSummaryLayout.createSequentialGroup()
                        .addComponent(lblRentalSummary)
                        .addGap(0, 39, Short.MAX_VALUE))
                    .addGroup(pnlRentalSummaryLayout.createSequentialGroup()
                        .addComponent(lblPendingRequestTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPendingRequest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlRentalSummaryLayout.createSequentialGroup()
                        .addComponent(lblTotalRentalsTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalRentals, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlRentalSummaryLayout.setVerticalGroup(
            pnlRentalSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRentalSummaryLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(lblRentalSummary)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRentalSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPendingRequestTitle)
                    .addComponent(lblPendingRequest))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRentalSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOngoingRentalsTitle)
                    .addComponent(lblOngoingRentals, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRentalSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReturnedRentalsTitle)
                    .addComponent(lblReturnedRentals, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRentalSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPaidRentalsTitle)
                    .addComponent(lblPaidRentals))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRentalSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTotalRentalsTitle)
                    .addComponent(lblTotalRentals, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(pnlSideNav, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlRentalSummary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(972, Short.MAX_VALUE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSideNav, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(pnlRentalSummary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHomeActionPerformed

    private void btnBrowseMoviesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseMoviesActionPerformed
        new UserBrowseMovies().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBrowseMoviesActionPerformed

    private void btnRentalHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRentalHistoryActionPerformed
        new UserRentalHistory().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRentalHistoryActionPerformed

    private void btnMyPaymentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMyPaymentsActionPerformed
        new UserMyPayments().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnMyPaymentsActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        ActiveSession.clearSession(); // Clears active session.
        new Login().setVisible(true); // Returns to login frame.
        this.dispose();
    }//GEN-LAST:event_btnLogoutActionPerformed

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
            java.util.logging.Logger.getLogger(UserDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowseMovies;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMyPayments;
    private javax.swing.JButton btnRentalHistory;
    private javax.swing.JLabel lblHeader1;
    private javax.swing.JLabel lblHeader2;
    private javax.swing.JLabel lblHeader3;
    private javax.swing.JLabel lblHeader4;
    private javax.swing.JLabel lblOngoingRentals;
    private javax.swing.JLabel lblOngoingRentalsTitle;
    private javax.swing.JLabel lblPaidRentals;
    private javax.swing.JLabel lblPaidRentalsTitle;
    private javax.swing.JLabel lblPendingRequest;
    private javax.swing.JLabel lblPendingRequestTitle;
    private javax.swing.JLabel lblRentalSummary;
    private javax.swing.JLabel lblReturnedRentals;
    private javax.swing.JLabel lblReturnedRentalsTitle;
    private javax.swing.JLabel lblTotalRentals;
    private javax.swing.JLabel lblTotalRentalsTitle;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlRentalSummary;
    private javax.swing.JPanel pnlSideNav;
    // End of variables declaration//GEN-END:variables
}
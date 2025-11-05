package cineflix;

import java.awt.Image;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

public class UserDashboard extends javax.swing.JFrame {
    Connection conn;
    RentalDAO rentalDAO;
    PaymentDAO paymentDAO;
    MovieDAO movieDAO;
    
    public UserDashboard() {
        initComponents();
        this.setSize(1315, 675);
        this.setLocationRelativeTo(null); // Center the JFrame
        lblHeader4.setText("Welcome, " + ActiveSession.loggedInUsername); // Welcome message.
        
        conn = DBConnection.getConnection();
        if (conn == null) return;
        rentalDAO = new RentalDAO(conn);
        paymentDAO = new PaymentDAO(conn);
        movieDAO = new MovieDAO(conn);
        
        populateSummary();
        populateTopMovies();
        populateRentalTable();
    }
    
    // Populate rental summary.
    private void populateSummary(){
        // Rental Summary.
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
        
        // Payment Summary.
        // Get all payment-related metrics for the current user.
        double totalPaid = paymentDAO.getTotalPaidByAccountID(ActiveSession.loggedInAccountID);
        double unpaidBalance = paymentDAO.getTotalUnpaidBalanceByAccountID(ActiveSession.loggedInAccountID);
        double overdueCharges = paymentDAO.getTotalOverdueChargesByAccountID(ActiveSession.loggedInAccountID);
        int confirmedTransactions = paymentDAO.getPaymentStatusCountByAccountID(ActiveSession.loggedInAccountID, "Paid Full");
        LocalDateTime lastPaymentDate = paymentDAO.getLastPaymentDateByAccountID(ActiveSession.loggedInAccountID);

        // Inline formatter for "November 11, 2025" style.
        DateTimeFormatter longDateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");

        // Format values for display.
        lblTotalAmountPaid.setText("₱" + String.format("%.2f", totalPaid));
        lblTotalUnpaidBalance.setText("₱" + String.format("%.2f", unpaidBalance));
        lblTotalOverdueCharges.setText("₱" + String.format("%.2f", overdueCharges));
        lblConfirmedTransactions.setText(String.valueOf(confirmedTransactions));
        lblLastPaymentDate.setText(
            lastPaymentDate != null ? longDateFormatter.format(lastPaymentDate) : "—"
        );
    }
    
    // Display top rented movies.
    public void populateTopMovies() {
        try{
            if (conn == null) return;
            MovieDAO movieDAO = new MovieDAO(conn);
            List<Movie> topMovies = movieDAO.getTopRentedMovieSummaries(4);

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
                        default -> null;
                    };

                    if (targetLabel != null) {
                        int width = targetLabel.getWidth();
                        int height = targetLabel.getHeight();
                        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                        scaledIcon = new ImageIcon(scaledImage);
                    }
                }

                // Assign title and image to corresponding components.
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
                    default:
                        break;
                }
            }
        } catch (Exception e){
            Message.error("Error populating top rented movies:\n" + e.getMessage());
        }
    }

    // Populate recent rental record made by the user.
    private void populateRentalTable(){
        try{
            if (conn == null) return;

            RentalDAO rentalDAO = new RentalDAO(conn);
            List<Rental> recentRentals = rentalDAO.getRecentRentalsByAccountID(ActiveSession.loggedInAccountID, 10);

            DefaultTableModel model = (DefaultTableModel) tblRentalRecord.getModel();
            model.setRowCount(0); // Clear existing rows

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");

            for (Rental r : recentRentals) {
                Object[] row = {
                    r.getMovieTitle(),
                    formatter.format(r.getRentalDate().toLocalDateTime()),
                    r.getReturnDate() != null ? formatter.format(r.getReturnDate().toLocalDateTime()) : "—",
                    r.getRentalStatus(),
                    "₱" + String.format("%.2f", r.getRentalCost())
                };
                model.addRow(row);
            }
        } catch (Exception e){
            Message.error("Error populating rental record:\n" + e.getMessage());
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
        btnBrowseMovies = new javax.swing.JButton();
        btnRentalHistory = new javax.swing.JButton();
        btnMyPayments = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        scrlHeader4 = new javax.swing.JScrollPane();
        lblHeader4 = new javax.swing.JTextArea();
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
        pnlPaymentSummary = new javax.swing.JPanel();
        lblPaymentSummary = new javax.swing.JLabel();
        lblTotalUnpaidBalanceTitle = new javax.swing.JLabel();
        lblConfirmedTransactionsTitle = new javax.swing.JLabel();
        lblTotalAmountPaidTitle = new javax.swing.JLabel();
        lblConfirmedTransactions = new javax.swing.JLabel();
        lblTotalAmountPaid = new javax.swing.JLabel();
        lblTotalUnpaidBalance = new javax.swing.JLabel();
        lblTotalOverdueChargesTitle = new javax.swing.JLabel();
        lblTotalOverdueCharges = new javax.swing.JLabel();
        lblLastPaymentDateTitle = new javax.swing.JLabel();
        lblLastPaymentDate = new javax.swing.JLabel();
        pblTopRentedMovies = new javax.swing.JPanel();
        lblTopRentedMovies = new javax.swing.JLabel();
        lblImage1 = new javax.swing.JLabel();
        scrlTitle1 = new javax.swing.JScrollPane();
        txtaTitle1 = new javax.swing.JTextArea();
        lblImage2 = new javax.swing.JLabel();
        scrlTitle2 = new javax.swing.JScrollPane();
        txtaTitle2 = new javax.swing.JTextArea();
        lblImage3 = new javax.swing.JLabel();
        scrlTitle3 = new javax.swing.JScrollPane();
        txtaTitle3 = new javax.swing.JTextArea();
        lblImage4 = new javax.swing.JLabel();
        scrlTitle4 = new javax.swing.JScrollPane();
        txtaTitle4 = new javax.swing.JTextArea();
        scrlRentalRecord = new javax.swing.JScrollPane();
        tblRentalRecord = new javax.swing.JTable();
        lblRecentRentalHistory = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CineFlix: Home");
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
            .addComponent(btnBrowseMovies, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnRentalHistory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnMyPayments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnLogout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlSideNavLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlSideNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrlHeader4, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlSideNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblHeader3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblHeader2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblHeader1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(10, Short.MAX_VALUE))
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
                .addComponent(btnBrowseMovies, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRentalHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMyPayments, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addComponent(lblPaidRentalsTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPaidRentals, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlRentalSummaryLayout.createSequentialGroup()
                        .addComponent(lblReturnedRentalsTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblReturnedRentals, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlRentalSummaryLayout.createSequentialGroup()
                        .addComponent(lblOngoingRentalsTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblOngoingRentals, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlRentalSummaryLayout.createSequentialGroup()
                        .addComponent(lblRentalSummary)
                        .addGap(0, 175, Short.MAX_VALUE))
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
                .addGroup(pnlRentalSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOngoingRentalsTitle)
                    .addComponent(lblOngoingRentals, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRentalSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReturnedRentalsTitle)
                    .addComponent(lblReturnedRentals))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRentalSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPaidRentalsTitle)
                    .addComponent(lblPaidRentals))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRentalSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPendingRequestTitle)
                    .addComponent(lblPendingRequest))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRentalSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTotalRentalsTitle)
                    .addComponent(lblTotalRentals, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pnlPaymentSummary.setBackground(new java.awt.Color(0, 0, 0));

        lblPaymentSummary.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblPaymentSummary.setForeground(new java.awt.Color(255, 255, 255));
        lblPaymentSummary.setText("Payment Summary");
        lblPaymentSummary.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblTotalUnpaidBalanceTitle.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblTotalUnpaidBalanceTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalUnpaidBalanceTitle.setText("Total Unpaid Balance:");
        lblTotalUnpaidBalanceTitle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblConfirmedTransactionsTitle.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblConfirmedTransactionsTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblConfirmedTransactionsTitle.setText("Confirmed Transactions:");
        lblConfirmedTransactionsTitle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblTotalAmountPaidTitle.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblTotalAmountPaidTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalAmountPaidTitle.setText("Total Amount Paid:");
        lblTotalAmountPaidTitle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblConfirmedTransactions.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblConfirmedTransactions.setForeground(new java.awt.Color(255, 255, 255));
        lblConfirmedTransactions.setText("N/A");
        lblConfirmedTransactions.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblTotalAmountPaid.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblTotalAmountPaid.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalAmountPaid.setText("N/A");
        lblTotalAmountPaid.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblTotalUnpaidBalance.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblTotalUnpaidBalance.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalUnpaidBalance.setText("N/A");
        lblTotalUnpaidBalance.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblTotalOverdueChargesTitle.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblTotalOverdueChargesTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalOverdueChargesTitle.setText("Total Overdue Charges:");
        lblTotalOverdueChargesTitle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblTotalOverdueCharges.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblTotalOverdueCharges.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalOverdueCharges.setText("N/A");
        lblTotalOverdueCharges.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblLastPaymentDateTitle.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblLastPaymentDateTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblLastPaymentDateTitle.setText("Last Payment Date:");
        lblLastPaymentDateTitle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblLastPaymentDate.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblLastPaymentDate.setForeground(new java.awt.Color(255, 255, 255));
        lblLastPaymentDate.setText("N/A");
        lblLastPaymentDate.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout pnlPaymentSummaryLayout = new javax.swing.GroupLayout(pnlPaymentSummary);
        pnlPaymentSummary.setLayout(pnlPaymentSummaryLayout);
        pnlPaymentSummaryLayout.setHorizontalGroup(
            pnlPaymentSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPaymentSummaryLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(pnlPaymentSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPaymentSummaryLayout.createSequentialGroup()
                        .addComponent(lblTotalOverdueChargesTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalOverdueCharges, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlPaymentSummaryLayout.createSequentialGroup()
                        .addComponent(lblPaymentSummary)
                        .addGap(0, 217, Short.MAX_VALUE))
                    .addGroup(pnlPaymentSummaryLayout.createSequentialGroup()
                        .addComponent(lblConfirmedTransactionsTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblConfirmedTransactions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlPaymentSummaryLayout.createSequentialGroup()
                        .addComponent(lblLastPaymentDateTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblLastPaymentDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlPaymentSummaryLayout.createSequentialGroup()
                        .addComponent(lblTotalUnpaidBalanceTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalUnpaidBalance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlPaymentSummaryLayout.createSequentialGroup()
                        .addComponent(lblTotalAmountPaidTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalAmountPaid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlPaymentSummaryLayout.setVerticalGroup(
            pnlPaymentSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPaymentSummaryLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(lblPaymentSummary)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPaymentSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalAmountPaidTitle)
                    .addComponent(lblTotalAmountPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPaymentSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalUnpaidBalanceTitle)
                    .addComponent(lblTotalUnpaidBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPaymentSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalOverdueChargesTitle)
                    .addComponent(lblTotalOverdueCharges))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPaymentSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLastPaymentDateTitle)
                    .addComponent(lblLastPaymentDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPaymentSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblConfirmedTransactionsTitle)
                    .addComponent(lblConfirmedTransactions, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pblTopRentedMovies.setBackground(new java.awt.Color(0, 0, 0));
        pblTopRentedMovies.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pblTopRentedMoviesMouseClicked(evt);
            }
        });

        lblTopRentedMovies.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblTopRentedMovies.setForeground(new java.awt.Color(255, 255, 255));
        lblTopRentedMovies.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTopRentedMovies.setText("Top Rented Movies");
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
        txtaTitle1.setRows(3);
        txtaTitle1.setWrapStyleWord(true);
        txtaTitle1.setBorder(null);
        txtaTitle1.setFocusable(false);
        scrlTitle1.setViewportView(txtaTitle1);

        lblImage2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblImage2.setForeground(new java.awt.Color(255, 255, 255));
        lblImage2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtaTitle2.setEditable(false);
        txtaTitle2.setBackground(new java.awt.Color(0, 0, 0));
        txtaTitle2.setColumns(20);
        txtaTitle2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtaTitle2.setForeground(new java.awt.Color(255, 255, 255));
        txtaTitle2.setLineWrap(true);
        txtaTitle2.setRows(3);
        txtaTitle2.setWrapStyleWord(true);
        txtaTitle2.setBorder(null);
        txtaTitle2.setFocusable(false);
        scrlTitle2.setViewportView(txtaTitle2);

        lblImage3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblImage3.setForeground(new java.awt.Color(255, 255, 255));
        lblImage3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtaTitle3.setEditable(false);
        txtaTitle3.setBackground(new java.awt.Color(0, 0, 0));
        txtaTitle3.setColumns(20);
        txtaTitle3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtaTitle3.setForeground(new java.awt.Color(255, 255, 255));
        txtaTitle3.setLineWrap(true);
        txtaTitle3.setRows(3);
        txtaTitle3.setWrapStyleWord(true);
        txtaTitle3.setBorder(null);
        txtaTitle3.setFocusable(false);
        scrlTitle3.setViewportView(txtaTitle3);

        lblImage4.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblImage4.setForeground(new java.awt.Color(255, 255, 255));
        lblImage4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtaTitle4.setEditable(false);
        txtaTitle4.setBackground(new java.awt.Color(0, 0, 0));
        txtaTitle4.setColumns(20);
        txtaTitle4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtaTitle4.setForeground(new java.awt.Color(255, 255, 255));
        txtaTitle4.setLineWrap(true);
        txtaTitle4.setRows(3);
        txtaTitle4.setWrapStyleWord(true);
        txtaTitle4.setBorder(null);
        txtaTitle4.setFocusable(false);
        scrlTitle4.setViewportView(txtaTitle4);

        javax.swing.GroupLayout pblTopRentedMoviesLayout = new javax.swing.GroupLayout(pblTopRentedMovies);
        pblTopRentedMovies.setLayout(pblTopRentedMoviesLayout);
        pblTopRentedMoviesLayout.setHorizontalGroup(
            pblTopRentedMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pblTopRentedMoviesLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pblTopRentedMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pblTopRentedMoviesLayout.createSequentialGroup()
                        .addGroup(pblTopRentedMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(scrlTitle3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(lblImage3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pblTopRentedMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pblTopRentedMoviesLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(scrlTitle4, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblImage4, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pblTopRentedMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lblTopRentedMovies, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pblTopRentedMoviesLayout.createSequentialGroup()
                            .addGroup(pblTopRentedMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblImage1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(scrlTitle1, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE))
                            .addGap(18, 18, 18)
                            .addGroup(pblTopRentedMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(scrlTitle2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(lblImage2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        pblTopRentedMoviesLayout.setVerticalGroup(
            pblTopRentedMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pblTopRentedMoviesLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(lblTopRentedMovies)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pblTopRentedMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pblTopRentedMoviesLayout.createSequentialGroup()
                        .addComponent(lblImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(scrlTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pblTopRentedMoviesLayout.createSequentialGroup()
                        .addComponent(lblImage2, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrlTitle2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pblTopRentedMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblImage4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblImage3, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pblTopRentedMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrlTitle3)
                    .addComponent(scrlTitle4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(71, Short.MAX_VALUE))
        );

        tblRentalRecord.setBackground(new java.awt.Color(0, 0, 0));
        tblRentalRecord.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblRentalRecord.setForeground(new java.awt.Color(255, 255, 255));
        tblRentalRecord.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Movie Title", "Rental Date", "Return Date", "Rental Status", "Rental Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRentalRecord.setSelectionBackground(new java.awt.Color(74, 144, 226));
        tblRentalRecord.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tblRentalRecord.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRentalRecordMouseClicked(evt);
            }
        });
        scrlRentalRecord.setViewportView(tblRentalRecord);

        lblRecentRentalHistory.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblRecentRentalHistory.setForeground(new java.awt.Color(0, 0, 0));
        lblRecentRentalHistory.setText("Recent Rental History");
        lblRecentRentalHistory.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(pnlSideNav, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrlRentalRecord)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addComponent(lblRecentRentalHistory)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addComponent(pnlRentalSummary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlPaymentSummary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(pblTopRentedMovies, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pblTopRentedMovies, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlRentalSummary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlPaymentSummary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addComponent(lblRecentRentalHistory)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrlRentalRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(pnlSideNav, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
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

    private void pblTopRentedMoviesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pblTopRentedMoviesMouseClicked
        new UserBrowseMovies().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_pblTopRentedMoviesMouseClicked

    private void tblRentalRecordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRentalRecordMouseClicked
        
    }//GEN-LAST:event_tblRentalRecordMouseClicked

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
    private javax.swing.JLabel lblConfirmedTransactions;
    private javax.swing.JLabel lblConfirmedTransactionsTitle;
    private javax.swing.JLabel lblHeader1;
    private javax.swing.JLabel lblHeader2;
    private javax.swing.JLabel lblHeader3;
    private javax.swing.JTextArea lblHeader4;
    private javax.swing.JLabel lblImage1;
    private javax.swing.JLabel lblImage2;
    private javax.swing.JLabel lblImage3;
    private javax.swing.JLabel lblImage4;
    private javax.swing.JLabel lblLastPaymentDate;
    private javax.swing.JLabel lblLastPaymentDateTitle;
    private javax.swing.JLabel lblOngoingRentals;
    private javax.swing.JLabel lblOngoingRentalsTitle;
    private javax.swing.JLabel lblPaidRentals;
    private javax.swing.JLabel lblPaidRentalsTitle;
    private javax.swing.JLabel lblPaymentSummary;
    private javax.swing.JLabel lblPendingRequest;
    private javax.swing.JLabel lblPendingRequestTitle;
    private javax.swing.JLabel lblRecentRentalHistory;
    private javax.swing.JLabel lblRentalSummary;
    private javax.swing.JLabel lblReturnedRentals;
    private javax.swing.JLabel lblReturnedRentalsTitle;
    private javax.swing.JLabel lblTopRentedMovies;
    private javax.swing.JLabel lblTotalAmountPaid;
    private javax.swing.JLabel lblTotalAmountPaidTitle;
    private javax.swing.JLabel lblTotalOverdueCharges;
    private javax.swing.JLabel lblTotalOverdueChargesTitle;
    private javax.swing.JLabel lblTotalRentals;
    private javax.swing.JLabel lblTotalRentalsTitle;
    private javax.swing.JLabel lblTotalUnpaidBalance;
    private javax.swing.JLabel lblTotalUnpaidBalanceTitle;
    private javax.swing.JPanel pblTopRentedMovies;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlPaymentSummary;
    private javax.swing.JPanel pnlRentalSummary;
    private javax.swing.JPanel pnlSideNav;
    private javax.swing.JScrollPane scrlHeader4;
    private javax.swing.JScrollPane scrlRentalRecord;
    private javax.swing.JScrollPane scrlTitle1;
    private javax.swing.JScrollPane scrlTitle2;
    private javax.swing.JScrollPane scrlTitle3;
    private javax.swing.JScrollPane scrlTitle4;
    private javax.swing.JTable tblRentalRecord;
    private javax.swing.JTextArea txtaTitle1;
    private javax.swing.JTextArea txtaTitle2;
    private javax.swing.JTextArea txtaTitle3;
    private javax.swing.JTextArea txtaTitle4;
    // End of variables declaration//GEN-END:variables
}
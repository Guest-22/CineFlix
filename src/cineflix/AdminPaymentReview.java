package cineflix;

import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

public class AdminPaymentReview extends javax.swing.JFrame {
    private Connection conn;
    private PaymentDAO paymentDAO;
    
    private int selectedPaymentID = -1;
    
    public AdminPaymentReview() {
        initComponents();
        this.setSize(1315, 675);
        this.setLocationRelativeTo(null); // Center the JFrame.
        lblHeader4.setText("Welcome, " + ActiveSession.loggedInUsername); // Welcome message.
        setDefaultTglSort();
        
        conn = DBConnection.getConnection();
        if (conn == null) return;
        paymentDAO = new PaymentDAO(conn);
        
        populatePaymentTable(""); // Populates payment table.
    }
    
    // Sets the default toggle button style.
    private void setDefaultTglSort(){
        tglSort.setFocusPainted(false);
        tglSort.setContentAreaFilled(false);
        tglSort.setBorderPainted(false);
        tglSort.setOpaque(true);
        tglSort.setBackground(Color.BLACK);
        tglSort.setForeground(Color.WHITE);
    }
    
    // Clear all fields and set selectedID back to -1.
    private void clearForm() {
        txtPaymentID.setText("");
        txtRentalID.setText("");
        txtAccountName.setText("");
        txtMovieTitle.setText("");
        txtRentalDate.setText("");
        txtReturnDate.setText("");
        lblWeeks.setText("N/A");
        cmbRentalStage.setSelectedIndex(0); 
        cmbRentalStatus.setSelectedIndex(0);
        txtPaymentStatus.setText("");
        txtTotalCost.setText("");
        txtOverdue.setText("");
        txtPaidAmount.setText("");
        lblRemainingBalance.setText("N/A");
        lblPaymentDate.setText("N/A");
        selectedPaymentID = -1;
        tblPaymentRecord.clearSelection();
        
        // Clear filtered search.
        txtSearch.setText(""); 
        populatePaymentTable(""); //
    }
    
    // Populates payment table.
    private void populatePaymentTable(String keyword) {
        DefaultTableModel model = (DefaultTableModel) tblPaymentRecord.getModel();
        model.setRowCount(0); // Clear existing rows

        try{
            conn = DBConnection.getConnection();
            if (conn == null) return;

            paymentDAO = new PaymentDAO(conn);
            List<AdminPaymentEntry> payments = paymentDAO.getAdminPaymentLogs();
            payments = SearchUtils.searchPayments(payments, keyword); // Filters table by keyword.

            String selectedSort = cmbSort.getSelectedItem().toString(); 
            String selectedOrder = tglSort.isSelected() ? "DESC" : "ASC";
            
             switch (selectedSort) {
                case "Sort by Return Date":
                    SortUtils.sortPaymentByReturnDate(payments);
                    break;
                case "Sort by Payment Date":
                    SortUtils.sortPaymentByPaymentDate(payments);
                    break;
                case "Sort by Payment Status":
                    SortUtils.sortPaymentByPaymentStatus(payments);
                    break;
                default:
                    break;
            }
             
            // Sort by order (ASC or DESC).
            if (selectedOrder.equals("DESC")) {
                Collections.reverse(payments);
            }
  
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy : h:mm a");
            
            for (AdminPaymentEntry p : payments) {
                model.addRow(new Object[] {
                    p.getPaymentID(),
                    p.getRentalID(),
                    p.getAccountName(),
                    p.getMovieTitle(),
                    p.getRentalDate().format(formatter),
                    p.getReturnDate().format(formatter),
                    p.getRentalStage(),
                    p.getRentalStatus(),
                    p.getPaymentStatus(),
                    String.format("₱%.2f", p.getTotalCost()),
                    String.format("₱%.2f", p.getAmountPaid()),
                    String.format("₱%.2f", p.getOverdueAmount()),
                    p.getPaymentDate() != null ? p.getPaymentDate().format(formatter) : ""
                });
            }
        } catch (Exception e){
            Message.error("Error loading payment table:\n" + e.getMessage());
        }
        // Hides payment ID.
        tblPaymentRecord.getColumnModel().getColumn(0).setMinWidth(0);
        tblPaymentRecord.getColumnModel().getColumn(0).setMaxWidth(0);
        tblPaymentRecord.getColumnModel().getColumn(0).setWidth(0);

        // Hides rental stage.
        tblPaymentRecord.getColumnModel().getColumn(6).setMinWidth(0);
        tblPaymentRecord.getColumnModel().getColumn(6).setMaxWidth(0);
        tblPaymentRecord.getColumnModel().getColumn(6).setWidth(0);
        
        // Hides rental status.
        tblPaymentRecord.getColumnModel().getColumn(7).setMinWidth(0);
        tblPaymentRecord.getColumnModel().getColumn(7).setMaxWidth(0);
        tblPaymentRecord.getColumnModel().getColumn(7).setWidth(0);
        
        // Hides payment status.
        tblPaymentRecord.getColumnModel().getColumn(8).setMinWidth(0);
        tblPaymentRecord.getColumnModel().getColumn(8).setMaxWidth(0);
        tblPaymentRecord.getColumnModel().getColumn(8).setWidth(0);
        
        // Hides overdue amount.
        tblPaymentRecord.getColumnModel().getColumn(11).setMinWidth(0);
        tblPaymentRecord.getColumnModel().getColumn(11).setMaxWidth(0);
        tblPaymentRecord.getColumnModel().getColumn(11).setWidth(0);

        // Hides payment date.
        tblPaymentRecord.getColumnModel().getColumn(12).setMinWidth(0);
        tblPaymentRecord.getColumnModel().getColumn(12).setMaxWidth(0);
        tblPaymentRecord.getColumnModel().getColumn(12).setWidth(0);
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
        lblPaymentReview = new javax.swing.JLabel();
        scrlPayment = new javax.swing.JScrollPane();
        tblPaymentRecord = new javax.swing.JTable();
        pnlForm = new javax.swing.JPanel();
        lblManageRentalDetails = new javax.swing.JLabel();
        txtTotalCost = new javax.swing.JTextField();
        txtReturnDate = new javax.swing.JTextField();
        lblTotalCost = new javax.swing.JLabel();
        lblRentalID = new javax.swing.JLabel();
        lblOverdue = new javax.swing.JLabel();
        lblAccountName = new javax.swing.JLabel();
        lblWeeks = new javax.swing.JLabel();
        lblMovieTitle = new javax.swing.JLabel();
        cmbRentalStage = new javax.swing.JComboBox<>();
        lblRentalDate = new javax.swing.JLabel();
        cmbRentalStatus = new javax.swing.JComboBox<>();
        lblReturnDate = new javax.swing.JLabel();
        lblRentalStage = new javax.swing.JLabel();
        lblRentalStatus = new javax.swing.JLabel();
        txtRentalID = new javax.swing.JTextField();
        txtAccountName = new javax.swing.JTextField();
        txtMovieTitle = new javax.swing.JTextField();
        txtRentalDate = new javax.swing.JTextField();
        txtPaymentID = new javax.swing.JTextField();
        lblPaymentID = new javax.swing.JLabel();
        lblPaymentStatus = new javax.swing.JLabel();
        txtPaymentStatus = new javax.swing.JTextField();
        txtOverdue = new javax.swing.JTextField();
        lblPaidAmount = new javax.swing.JLabel();
        txtPaidAmount = new javax.swing.JTextField();
        lblPaymentDate = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnConfirmTransaction = new javax.swing.JButton();
        lblRemainingBalance1 = new javax.swing.JLabel();
        lblSettleBalance = new javax.swing.JLabel();
        txtSettleBalance = new javax.swing.JTextField();
        lblRemainingBalance = new javax.swing.JLabel();
        lblWeekDuration = new javax.swing.JLabel();
        lblPaymentDate1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        cmbSort = new javax.swing.JComboBox<>();
        tglSort = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CineFlix: Payment Review");
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

        btnHome.setBackground(new java.awt.Color(0, 0, 0));
        btnHome.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnHome.setForeground(new java.awt.Color(255, 255, 255));
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

        btnPaymentReview.setBackground(new java.awt.Color(255, 255, 255));
        btnPaymentReview.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPaymentReview.setForeground(new java.awt.Color(0, 0, 0));
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblPaymentReview.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblPaymentReview.setForeground(new java.awt.Color(0, 0, 0));
        lblPaymentReview.setText("Payment Review");
        lblPaymentReview.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        tblPaymentRecord.setBackground(new java.awt.Color(0, 0, 0));
        tblPaymentRecord.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblPaymentRecord.setForeground(new java.awt.Color(255, 255, 255));
        tblPaymentRecord.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Payment ID", "Rental ID", "Account Name", "Movie Title", "Rental Date", "Return Date", "Rental Stage", "Rental Status", "Payment Status", "Total Cost", "Paid Amount", "Overdue Amount", "Payment Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPaymentRecord.setSelectionBackground(new java.awt.Color(74, 144, 226));
        tblPaymentRecord.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tblPaymentRecord.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPaymentRecordMouseClicked(evt);
            }
        });
        scrlPayment.setViewportView(tblPaymentRecord);

        pnlForm.setBackground(new java.awt.Color(0, 0, 0));

        lblManageRentalDetails.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblManageRentalDetails.setForeground(new java.awt.Color(255, 255, 255));
        lblManageRentalDetails.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblManageRentalDetails.setText("Manage Payment Details");
        lblManageRentalDetails.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtTotalCost.setEditable(false);
        txtTotalCost.setBackground(new java.awt.Color(204, 204, 204));
        txtTotalCost.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtTotalCost.setForeground(new java.awt.Color(0, 0, 0));
        txtTotalCost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalCostActionPerformed(evt);
            }
        });

        txtReturnDate.setEditable(false);
        txtReturnDate.setBackground(new java.awt.Color(204, 204, 204));
        txtReturnDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtReturnDate.setForeground(new java.awt.Color(0, 0, 0));
        txtReturnDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReturnDateActionPerformed(evt);
            }
        });

        lblTotalCost.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTotalCost.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalCost.setText("Total Cost:");

        lblRentalID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblRentalID.setForeground(new java.awt.Color(255, 255, 255));
        lblRentalID.setText("Rental ID:");

        lblOverdue.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblOverdue.setForeground(new java.awt.Color(255, 255, 255));
        lblOverdue.setText("Overdue:");

        lblAccountName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAccountName.setForeground(new java.awt.Color(255, 255, 255));
        lblAccountName.setText("Account Name:");

        lblWeeks.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        lblWeeks.setForeground(new java.awt.Color(204, 204, 204));
        lblWeeks.setText("N/A");

        lblMovieTitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMovieTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblMovieTitle.setText("Movie Title:");

        cmbRentalStage.setBackground(new java.awt.Color(255, 255, 255));
        cmbRentalStage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbRentalStage.setForeground(new java.awt.Color(0, 0, 0));
        cmbRentalStage.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Requested", "Approved", "PickedUp", "Rejected" }));

        lblRentalDate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblRentalDate.setForeground(new java.awt.Color(255, 255, 255));
        lblRentalDate.setText("Rental Date:");

        cmbRentalStatus.setBackground(new java.awt.Color(255, 255, 255));
        cmbRentalStatus.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbRentalStatus.setForeground(new java.awt.Color(0, 0, 0));
        cmbRentalStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pending", "Ongoing", "Returned", "Late", "Cancelled" }));

        lblReturnDate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblReturnDate.setForeground(new java.awt.Color(255, 255, 255));
        lblReturnDate.setText("Return Date:");

        lblRentalStage.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblRentalStage.setForeground(new java.awt.Color(255, 255, 255));
        lblRentalStage.setText("Rental Stage:");

        lblRentalStatus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblRentalStatus.setForeground(new java.awt.Color(255, 255, 255));
        lblRentalStatus.setText("Rental Status:");

        txtRentalID.setEditable(false);
        txtRentalID.setBackground(new java.awt.Color(204, 204, 204));
        txtRentalID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtRentalID.setForeground(new java.awt.Color(0, 0, 0));
        txtRentalID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRentalIDActionPerformed(evt);
            }
        });

        txtAccountName.setEditable(false);
        txtAccountName.setBackground(new java.awt.Color(204, 204, 204));
        txtAccountName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtAccountName.setForeground(new java.awt.Color(0, 0, 0));
        txtAccountName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAccountNameActionPerformed(evt);
            }
        });

        txtMovieTitle.setEditable(false);
        txtMovieTitle.setBackground(new java.awt.Color(204, 204, 204));
        txtMovieTitle.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtMovieTitle.setForeground(new java.awt.Color(0, 0, 0));
        txtMovieTitle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMovieTitleActionPerformed(evt);
            }
        });

        txtRentalDate.setEditable(false);
        txtRentalDate.setBackground(new java.awt.Color(204, 204, 204));
        txtRentalDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtRentalDate.setForeground(new java.awt.Color(0, 0, 0));
        txtRentalDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRentalDateActionPerformed(evt);
            }
        });

        txtPaymentID.setEditable(false);
        txtPaymentID.setBackground(new java.awt.Color(204, 204, 204));
        txtPaymentID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPaymentID.setForeground(new java.awt.Color(0, 0, 0));
        txtPaymentID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPaymentIDActionPerformed(evt);
            }
        });

        lblPaymentID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblPaymentID.setForeground(new java.awt.Color(255, 255, 255));
        lblPaymentID.setText("Payment ID:");

        lblPaymentStatus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblPaymentStatus.setForeground(new java.awt.Color(255, 255, 255));
        lblPaymentStatus.setText("Payment Status:");

        txtPaymentStatus.setEditable(false);
        txtPaymentStatus.setBackground(new java.awt.Color(204, 204, 204));
        txtPaymentStatus.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPaymentStatus.setForeground(new java.awt.Color(0, 0, 0));
        txtPaymentStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPaymentStatusActionPerformed(evt);
            }
        });

        txtOverdue.setBackground(new java.awt.Color(204, 204, 204));
        txtOverdue.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtOverdue.setForeground(new java.awt.Color(0, 0, 0));
        txtOverdue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOverdueActionPerformed(evt);
            }
        });

        lblPaidAmount.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblPaidAmount.setForeground(new java.awt.Color(255, 255, 255));
        lblPaidAmount.setText("Paid Amount:");

        txtPaidAmount.setBackground(new java.awt.Color(204, 204, 204));
        txtPaidAmount.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPaidAmount.setForeground(new java.awt.Color(0, 0, 0));
        txtPaidAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPaidAmountActionPerformed(evt);
            }
        });

        lblPaymentDate.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblPaymentDate.setForeground(new java.awt.Color(204, 204, 204));
        lblPaymentDate.setText("N/A");

        btnUpdate.setBackground(new java.awt.Color(0, 153, 204));
        btnUpdate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Update");
        btnUpdate.setFocusable(false);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(255, 51, 0));
        btnDelete.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Delete");
        btnDelete.setFocusable(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(153, 153, 255));
        btnClear.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setText("Clear");
        btnClear.setFocusable(false);
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnConfirmTransaction.setBackground(new java.awt.Color(0, 204, 153));
        btnConfirmTransaction.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnConfirmTransaction.setForeground(new java.awt.Color(255, 255, 255));
        btnConfirmTransaction.setText("Confirm Transaction");
        btnConfirmTransaction.setFocusable(false);
        btnConfirmTransaction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmTransactionActionPerformed(evt);
            }
        });

        lblRemainingBalance1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblRemainingBalance1.setForeground(new java.awt.Color(0, 255, 51));
        lblRemainingBalance1.setText("Remaining Balance:");

        lblSettleBalance.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblSettleBalance.setForeground(new java.awt.Color(255, 255, 255));
        lblSettleBalance.setText("Settle Balance:");

        txtSettleBalance.setBackground(new java.awt.Color(255, 255, 255));
        txtSettleBalance.setForeground(new java.awt.Color(0, 0, 0));

        lblRemainingBalance.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblRemainingBalance.setForeground(new java.awt.Color(0, 255, 51));
        lblRemainingBalance.setText("N/A");

        lblWeekDuration.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        lblWeekDuration.setForeground(new java.awt.Color(204, 204, 204));
        lblWeekDuration.setText("Week Duration:");

        lblPaymentDate1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblPaymentDate1.setForeground(new java.awt.Color(204, 204, 204));
        lblPaymentDate1.setText("Last Payment Date:");

        javax.swing.GroupLayout pnlFormLayout = new javax.swing.GroupLayout(pnlForm);
        pnlForm.setLayout(pnlFormLayout);
        pnlFormLayout.setHorizontalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblManageRentalDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlFormLayout.createSequentialGroup()
                                .addComponent(lblSettleBalance)
                                .addGap(18, 18, 18)
                                .addComponent(txtSettleBalance))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlFormLayout.createSequentialGroup()
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlFormLayout.createSequentialGroup()
                                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPaymentStatus)
                                    .addComponent(lblTotalCost)
                                    .addComponent(lblPaidAmount))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlFormLayout.createSequentialGroup()
                                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtTotalCost, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtPaidAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(pnlFormLayout.createSequentialGroup()
                                                .addComponent(lblRemainingBalance1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblRemainingBalance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(pnlFormLayout.createSequentialGroup()
                                                .addComponent(lblOverdue)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(txtOverdue, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(txtPaymentStatus)))
                            .addComponent(btnConfirmTransaction, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(6, 6, 6))
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(pnlFormLayout.createSequentialGroup()
                                    .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblPaymentID)
                                        .addComponent(lblAccountName)
                                        .addComponent(lblMovieTitle)
                                        .addComponent(lblRentalDate))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlFormLayout.createSequentialGroup()
                                            .addComponent(txtPaymentID, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblRentalID)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtRentalID, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(pnlFormLayout.createSequentialGroup()
                                            .addGap(0, 0, Short.MAX_VALUE)
                                            .addComponent(txtAccountName, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(pnlFormLayout.createSequentialGroup()
                                    .addComponent(lblRentalStatus)
                                    .addGap(18, 18, 18)
                                    .addComponent(cmbRentalStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(pnlFormLayout.createSequentialGroup()
                                    .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblRentalStage)
                                        .addComponent(lblReturnDate))
                                    .addGap(22, 22, 22)
                                    .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtReturnDate, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(cmbRentalStage, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtRentalDate)
                                        .addComponent(txtMovieTitle)
                                        .addGroup(pnlFormLayout.createSequentialGroup()
                                            .addComponent(lblWeekDuration)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(lblWeeks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                            .addGroup(pnlFormLayout.createSequentialGroup()
                                .addComponent(lblPaymentDate1)
                                .addGap(18, 18, 18)
                                .addComponent(lblPaymentDate, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(24, 24, 24))
        );
        pnlFormLayout.setVerticalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addComponent(lblManageRentalDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRentalID, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRentalID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPaymentID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPaymentID, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAccountName, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAccountName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMovieTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMovieTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRentalDate, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRentalDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblReturnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtReturnDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblWeeks, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblWeekDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cmbRentalStage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRentalStage, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRentalStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbRentalStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPaymentStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPaymentStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalCost, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblOverdue, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOverdue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPaidAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPaidAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRemainingBalance)
                    .addComponent(lblRemainingBalance1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPaymentDate, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPaymentDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSettleBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSettleBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnConfirmTransaction, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtSearch.setBackground(new java.awt.Color(0, 0, 0));
        txtSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSearch.setForeground(new java.awt.Color(255, 255, 255));
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        btnSearch.setBackground(new java.awt.Color(0, 0, 0));
        btnSearch.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("Search");
        btnSearch.setFocusable(false);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        cmbSort.setBackground(new java.awt.Color(0, 0, 0));
        cmbSort.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmbSort.setForeground(new java.awt.Color(255, 255, 255));
        cmbSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sort by Return Date", "Sort by Payment Date", "Sort by Payment Status" }));
        cmbSort.setFocusable(false);
        cmbSort.setRequestFocusEnabled(false);
        cmbSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSortActionPerformed(evt);
            }
        });

        tglSort.setBackground(new java.awt.Color(0, 0, 0));
        tglSort.setFont(new java.awt.Font("SansSerif", 1, 10)); // NOI18N
        tglSort.setForeground(new java.awt.Color(255, 255, 255));
        tglSort.setText("ASC");
        tglSort.setBorderPainted(false);
        tglSort.setFocusPainted(false);
        tglSort.setFocusable(false);
        tglSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tglSortActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(pnlSideNav, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrlPayment, javax.swing.GroupLayout.DEFAULT_SIZE, 734, Short.MAX_VALUE)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addComponent(lblPaymentReview)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addComponent(txtSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tglSort, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(pnlForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSideNav, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblPaymentReview)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch)
                    .addComponent(cmbSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tglSort))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrlPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(152, 152, 152))
            .addComponent(pnlForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        new AdminDashboard().setVisible(true);
        this.dispose();
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
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPaymentReviewActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        ActiveSession.clearSession(); // Clears active session.
        new Login().setVisible(true); // Returns to login frame.
        this.dispose();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void tblPaymentRecordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPaymentRecordMouseClicked
        int row = tblPaymentRecord.getSelectedRow();
        if (row >= 0) {
            // Stores selected payment ID globally for reference.
            selectedPaymentID = Integer.parseInt(tblPaymentRecord.getValueAt(row, 0).toString());

            // Populate form fields with selected row data.
            txtPaymentID.setText(tblPaymentRecord.getValueAt(row, 0).toString()); 
            txtRentalID.setText(tblPaymentRecord.getValueAt(row, 1).toString()); 
            txtAccountName.setText(tblPaymentRecord.getValueAt(row, 2).toString()); 
            txtMovieTitle.setText(tblPaymentRecord.getValueAt(row, 3).toString()); 
            txtRentalDate.setText(tblPaymentRecord.getValueAt(row, 4).toString()); 
            txtReturnDate.setText(tblPaymentRecord.getValueAt(row, 5).toString()); 
            cmbRentalStage.setSelectedItem(tblPaymentRecord.getValueAt(row, 6).toString());
            cmbRentalStatus.setSelectedItem(tblPaymentRecord.getValueAt(row, 7).toString()); 
            txtPaymentStatus.setText(tblPaymentRecord.getValueAt(row, 8).toString());
            txtTotalCost.setText(tblPaymentRecord.getValueAt(row, 9).toString());
            txtPaidAmount.setText(tblPaymentRecord.getValueAt(row, 10).toString());
            txtOverdue.setText(tblPaymentRecord.getValueAt(row, 11).toString());
            cmbRentalStatus.setSelectedItem(tblPaymentRecord.getValueAt(row, 7).toString());
            // Null-safe payment date display.
            Object paymentDateObj = tblPaymentRecord.getValueAt(row, 12);
            String paymentDateStr = (paymentDateObj != null) ? paymentDateObj.toString() : "";
            lblPaymentDate.setText(paymentDateStr);

            // Calculate weeks between rental & return date.
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy : h:mm a");
                LocalDateTime rentalDateTime = LocalDateTime.parse(txtRentalDate.getText().trim(), formatter);
                LocalDateTime returnDateTime = LocalDateTime.parse(txtReturnDate.getText().trim(), formatter);

                long days = ChronoUnit.DAYS.between(rentalDateTime.toLocalDate(), returnDateTime.toLocalDate());
                long weeks = days / 7;
                
                lblWeeks.setText(weeks + " Week/s");
            } catch (Exception e2) {
                lblWeeks.setText("Invalid date format");
            }
             // ?Calculate Remaining Balance: (totalCost - amountPaid) + overdueAmount
            try {
                double totalCost = Double.parseDouble(txtTotalCost.getText().replace("₱", "").trim());
                double amountPaid = Double.parseDouble(txtPaidAmount.getText().replace("₱", "").trim());
                double overdue = Double.parseDouble(txtOverdue.getText().replace("₱", "").trim());

                double remainingBalanceBalance = (totalCost - amountPaid) + overdue;
                lblRemainingBalance.setText(String.format("₱%.2f", remainingBalanceBalance));
            } catch (Exception e3) {
                lblRemainingBalance.setText("");
            }
        }
    }//GEN-LAST:event_tblPaymentRecordMouseClicked

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty()){
             Message.error("Please enter a movie title to search.");
             return;
        }
        populatePaymentTable(keyword);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void cmbSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSortActionPerformed
        String sortQuery = txtSearch.getText().trim();
        populatePaymentTable(sortQuery);
    }//GEN-LAST:event_cmbSortActionPerformed

    private void tglSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tglSortActionPerformed
        if (tglSort.isSelected()) {
        tglSort.setText("DESC");
        } else {
            tglSort.setText("ASC");
        }
        populatePaymentTable(txtSearch.getText().trim());
    }//GEN-LAST:event_tglSortActionPerformed

    private void txtTotalCostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalCostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalCostActionPerformed

    private void txtReturnDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtReturnDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtReturnDateActionPerformed

    private void txtRentalIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRentalIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRentalIDActionPerformed

    private void txtAccountNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAccountNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAccountNameActionPerformed

    private void txtMovieTitleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMovieTitleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMovieTitleActionPerformed

    private void txtRentalDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRentalDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRentalDateActionPerformed

    private void txtPaymentIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPaymentIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaymentIDActionPerformed

    private void txtPaymentStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPaymentStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaymentStatusActionPerformed

    private void txtOverdueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOverdueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOverdueActionPerformed

    private void txtPaidAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPaidAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaidAmountActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (selectedPaymentID <= 0) { // Handles no row selection
            Message.error("No payment selected.");
            return;
        }
        
        // Declare and assign selected row.
        int row = tblPaymentRecord.getSelectedRow();
        if (row < 0) {
            Message.error("No row selected in the table.");
            return;
        }
        // Extract rentalID from hidden column (index 1)
        int rentalID = Integer.parseInt(tblPaymentRecord.getValueAt(row, 1).toString());

        // Get selected rental stage & status from ComboBox
        String selectedStage = cmbRentalStage.getSelectedItem().toString();
        String selectedStatus = cmbRentalStatus.getSelectedItem().toString();

        if (conn == null) return; // Validates connection.
        RentalDAO rentalDAO = new RentalDAO(conn); // Use rentalDAO to update.

        boolean isStageUpdated = rentalDAO.updateRentalStage(rentalID, selectedStage);
        boolean isStatusUpdated = rentalDAO.updateRentalStatus(rentalID, selectedStatus);

        if (isStageUpdated && isStatusUpdated) {
            Message.show("Payment record updated: Stage = " + selectedStage + ", Status = " + selectedStatus);
            populatePaymentTable(""); // Refresh table
            // clearForm(); // Optional: reset right panel
        } else {
            Message.error("Failed to update payment record.");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (selectedPaymentID == -1) {
            Message.error("No payment selected.");
            return;
        }

        boolean confirm = Message.confirm("Are you sure you want to delete this transaction?", "Confirm Delete");
        if (!confirm) return;

        try {
            conn = DBConnection.getConnection();
            if (conn == null) return;

            paymentDAO = new PaymentDAO(conn);
            boolean success = paymentDAO.deletePaymentTransaction(selectedPaymentID);

            if (success) {
                Message.show("Transaction deleted successfully.");
                populatePaymentTable("");
                clearForm();
            } else {
                Message.error("Failed to delete transaction.");
            }

        } catch (Exception e) {
            Message.error("Error deleting transaction: " + e.getMessage());
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearForm();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnConfirmTransactionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmTransactionActionPerformed
            if (selectedPaymentID == -1) {
            Message.error("No payment selected."); // Handles no row selected.
            return;
        }

        try {
            double remainingBalance = Double.parseDouble(lblRemainingBalance.getText().replace("₱", "").trim());
            double settleAmount = Double.parseDouble(txtSettleBalance.getText().replace("₱", "").trim());
            double currentPaidAmount = Double.parseDouble(txtPaidAmount.getText().replace("₱", "").trim());
            double newTotalPaid = currentPaidAmount + settleAmount;
            
            // Payment must be full/equivalent to remaining balance.
            if (settleAmount != remainingBalance) {
                Message.show("Settle amount must match the remaining balance to confirm.", "Mismatch");
                return;
            }

            LocalDateTime now = LocalDateTime.now();
            String status = "Paid Full";

            conn = DBConnection.getConnection();
            if (conn == null) return;

            paymentDAO = new PaymentDAO(conn);
            //Inserts the full payment.
            boolean success = paymentDAO.confirmPaymentTransaction(selectedPaymentID, status, newTotalPaid, now);

            if (!success) {
                Message.error("Failed to confirm transaction.");
                return;
            }

            // If Transaction is confirmed, proceed with copy increment.
            int movieID = paymentDAO.getMovieIDByPaymentID(selectedPaymentID);
            if (movieID == -1) { // Movie copy increment failed.
                Message.error("Movie ID not found for this payment.");
                return;
            }

            MovieDAO movieDAO = new MovieDAO(conn);
            boolean updated = movieDAO.incrementMovieCopies(movieID);
            if (!updated) {
                Message.error("Failed to increment movie copy.");
                return;
            }

            Message.show("Transaction confirmed successfully.");
            populatePaymentTable("");
            // clearForm();
        } catch (Exception e) {
            Message.error("Error confirming transaction: " + e.getMessage());
        }
    }//GEN-LAST:event_btnConfirmTransactionActionPerformed

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
            java.util.logging.Logger.getLogger(AdminPaymentReview.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminPaymentReview.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminPaymentReview.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminPaymentReview.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminPaymentReview().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnConfirmTransaction;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMovieInventory;
    private javax.swing.JButton btnPaymentReview;
    private javax.swing.JButton btnRentalLogs;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnUserProfiles;
    private javax.swing.JComboBox<String> cmbRentalStage;
    private javax.swing.JComboBox<String> cmbRentalStatus;
    private javax.swing.JComboBox<String> cmbSort;
    private javax.swing.JLabel lblAccountName;
    private javax.swing.JLabel lblHeader1;
    private javax.swing.JLabel lblHeader2;
    private javax.swing.JLabel lblHeader3;
    private javax.swing.JLabel lblHeader4;
    private javax.swing.JLabel lblManageRentalDetails;
    private javax.swing.JLabel lblMovieTitle;
    private javax.swing.JLabel lblOverdue;
    private javax.swing.JLabel lblPaidAmount;
    private javax.swing.JLabel lblPaymentDate;
    private javax.swing.JLabel lblPaymentDate1;
    private javax.swing.JLabel lblPaymentID;
    private javax.swing.JLabel lblPaymentReview;
    private javax.swing.JLabel lblPaymentStatus;
    private javax.swing.JLabel lblRemainingBalance;
    private javax.swing.JLabel lblRemainingBalance1;
    private javax.swing.JLabel lblRentalDate;
    private javax.swing.JLabel lblRentalID;
    private javax.swing.JLabel lblRentalStage;
    private javax.swing.JLabel lblRentalStatus;
    private javax.swing.JLabel lblReturnDate;
    private javax.swing.JLabel lblSettleBalance;
    private javax.swing.JLabel lblTotalCost;
    private javax.swing.JLabel lblWeekDuration;
    private javax.swing.JLabel lblWeeks;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlSideNav;
    private javax.swing.JScrollPane scrlPayment;
    private javax.swing.JTable tblPaymentRecord;
    private javax.swing.JToggleButton tglSort;
    private javax.swing.JTextField txtAccountName;
    private javax.swing.JTextField txtMovieTitle;
    private javax.swing.JTextField txtOverdue;
    private javax.swing.JTextField txtPaidAmount;
    private javax.swing.JTextField txtPaymentID;
    private javax.swing.JTextField txtPaymentStatus;
    private javax.swing.JTextField txtRentalDate;
    private javax.swing.JTextField txtRentalID;
    private javax.swing.JTextField txtReturnDate;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSettleBalance;
    private javax.swing.JTextField txtTotalCost;
    // End of variables declaration//GEN-END:variables
}
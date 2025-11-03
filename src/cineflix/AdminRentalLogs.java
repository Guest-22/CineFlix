package cineflix;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class AdminRentalLogs extends javax.swing.JFrame {
    private Connection conn;
    private RentalDAO rentalDAO;
    private PaymentDAO paymentDAO;
    
    private int selectedRentalID = -1;
    
    public AdminRentalLogs() {
        initComponents();
        this.setSize(1315, 675);
        this.setLocationRelativeTo(null); // Center the JFrame.
        lblHeader4.setText("Welcome, " + ActiveSession.loggedInUsername); // Welcome message.
        setDefaultTglSort();
        
        conn = DBConnection.getConnection(); // Attemps to get a DB Connection.
        if (conn == null) return; // Validates the connection before continuing.
        rentalDAO = new RentalDAO (conn);
        paymentDAO = new PaymentDAO (conn);
        populateRentalRecord(""); // Populates rental table.
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
    
    // Clear all inputs.
    private void clearForm() {
        txtRentalID.setText("");
        txtAccountName.setText("");
        txtMovieTitle.setText("");
        txtRentalDate.setText("");
        txtReturnDate.setText("");
        lblWeeks.setText("N/A");
        cmbRentalStage.setSelectedIndex(0);
        cmbRentalStage.setSelectedIndex(0);
        txtTotalCost.setText("");
        txtUpfrontFee.setText("");
        txtPaidAmount.setText("");
        tblRentalTable.clearSelection();
        selectedRentalID = -1; // Resets selected row rental id.
        
        // Clear filtered search.
        txtSearch.setText(""); // lear the search field.
        populateRentalRecord(""); // Reset table to show all movies.
    }
    
    // Populates the rental record.
    private void populateRentalRecord(String keyword) {
        DefaultTableModel rentalModel = (DefaultTableModel) tblRentalTable.getModel();
        rentalModel.setRowCount(0); // Clear existing rows.

        try{
            Connection conn = DBConnection.getConnection(); // Attempts to get the DB connection.
            if (conn == null) return; // Validates the connection.
            rentalDAO = new RentalDAO(conn); // Pass the connection to query joins.
            
            // Use the AdminRentalEntry as model for this customized cols(REASON: JOINS).
            List<AdminRentalEntry> rentals = rentalDAO.getAdminRentalLogs(); 
            rentals = SearchUtils.searchRentals(rentals, keyword);
            
            String selectedSort = cmbSort.getSelectedItem().toString();
            String selectedOrder = tglSort.isSelected() ? "DESC" : "ASC";
            
            switch (selectedSort) {
                case "Sort by Rental Date":
                    SortUtils.sortByRentalDate(rentals);
                    break;
                case "Sort by Return Date":
                    SortUtils.sortByReturnDate(rentals);
                    break;
                case "Sort by Stage":
                    SortUtils.sortByStage(rentals);
                    break;
                case "Sort by Status":
                    SortUtils.sortByStatus(rentals);
                    break;
                default:
                    break;
            }
            
            if ("DESC".equals(selectedOrder)) {
                Collections.reverse(rentals);
            }
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy : h:mm a");
            for (AdminRentalEntry r : rentals) { // Loops through a List of AdminRentalEntry objects and adds the record to the rental table.
                // Convert Timestamp to LocalDateTime and format.
                String rentalDateStr = r.getRentalDate().toLocalDateTime().format(formatter);
                String returnDateStr = r.getReturnDate().toLocalDateTime().format(formatter);

                rentalModel.addRow(new Object[] {
                    r.getRentalID(),
                    r.getFullName(),
                    r.getMovieTitle(),
                    rentalDateStr,
                    returnDateStr,
                    r.getRentalStage(), 
                    r.getRentalStatus(),
                    r.getPaymentStatus(),
                    String.format("₱%.2f", r.getTotalCost())
                });
            }
            // Hide paymentStatus.
            tblRentalTable.getColumnModel().getColumn(7).setMinWidth(0);
            tblRentalTable.getColumnModel().getColumn(7).setMaxWidth(0);
            tblRentalTable.getColumnModel().getColumn(7).setWidth(0);
            
        } catch (Exception e){
            Message.error("Error loading rental table: " + e.getMessage());
        }
        applyRentalTableColorRenderers(tblRentalTable); // Apply color indicator that varies on stage & status.
    }
        
    // Applies color indicator based on the rental stage & rental status.
    private void applyRentalTableColorRenderers(JTable tblRentalRecord) {
        // Colors indicators.
        Color colGray   = new Color(128, 128, 128); // Neutral.
        Color colGreen  = new Color(88, 199, 138); // Active.
        Color colPurple = new Color(153, 102, 204); // Completed.
        Color colRed    = new Color(226, 88, 88); // Problematic.

        // Apply row-wide color renderer based on rentalStage col (index 5).
        tblRentalRecord.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (isSelected) {
                    cell.setBackground(table.getSelectionBackground());
                    cell.setForeground(table.getSelectionForeground());
                } else {
                    Object stageValue = table.getValueAt(row, 5); // rentalStage column.
                    if (column != 6 && stageValue != null) { // Skip rentalStatus column.
                        String rentalStageCol = stageValue.toString();
                        switch (rentalStageCol) {
                            case "Requested":  cell.setBackground(colGray);   break;
                            case "Approved":   cell.setBackground(colGreen);  break;
                            case "PickedUp":   cell.setBackground(colPurple); break;
                            case "Rejected":   cell.setBackground(colRed);    break;
                            default:           cell.setBackground(table.getBackground());
                        }
                    } else {
                        cell.setBackground(table.getBackground());
                    }
                    cell.setForeground(table.getForeground());
                }
                return cell;
            }
        });

        // Apply color renderer to rentalStatus col (index 6).
        tblRentalRecord.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (isSelected) {
                    cell.setBackground(table.getSelectionBackground());
                    cell.setForeground(table.getSelectionForeground());
                } else {
                    String rentalStatusCol = value.toString();
                    switch (rentalStatusCol) {
                        case "Pending":    cell.setBackground(colGray);   break;
                        case "Ongoing":    cell.setBackground(colGreen);  break;
                        case "Returned":   cell.setBackground(colPurple); break;
                        case "Late":
                        case "Cancelled":  cell.setBackground(colRed);    break;
                        default:           cell.setBackground(table.getBackground());
                    }
                    cell.setForeground(table.getForeground());
                }
                return cell;
            }
        });
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
        lblRentalLogs = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        cmbSort = new javax.swing.JComboBox<>();
        tglSort = new javax.swing.JToggleButton();
        txtSearch = new javax.swing.JTextField();
        scrlRental = new javax.swing.JScrollPane();
        tblRentalTable = new javax.swing.JTable();
        pnlForm = new javax.swing.JPanel();
        lblManageRentalDetails = new javax.swing.JLabel();
        lblRentalID = new javax.swing.JLabel();
        lblAccountName = new javax.swing.JLabel();
        lblMovieTitle = new javax.swing.JLabel();
        lblRentalDate = new javax.swing.JLabel();
        lblReturnDate = new javax.swing.JLabel();
        lblRentalStage = new javax.swing.JLabel();
        lblRentalStatus = new javax.swing.JLabel();
        txtRentalID = new javax.swing.JTextField();
        txtAccountName = new javax.swing.JTextField();
        txtMovieTitle = new javax.swing.JTextField();
        txtRentalDate = new javax.swing.JTextField();
        txtTotalCost = new javax.swing.JTextField();
        txtReturnDate = new javax.swing.JTextField();
        btnUpdate = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnConfirmTransaction = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        lblTotalCost = new javax.swing.JLabel();
        lblUpfrontFee = new javax.swing.JLabel();
        txtUpfrontFee = new javax.swing.JTextField();
        lblUpfrontFee1 = new javax.swing.JLabel();
        lblAmountPaid = new javax.swing.JLabel();
        txtPaidAmount = new javax.swing.JTextField();
        lblWeeks = new javax.swing.JLabel();
        cmbRentalStage = new javax.swing.JComboBox<>();
        cmbRentalStatus = new javax.swing.JComboBox<>();
        lblWeekDuration = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CineFlix: Rental Logs");
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

        btnRentalLogs.setBackground(new java.awt.Color(255, 255, 255));
        btnRentalLogs.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnRentalLogs.setForeground(new java.awt.Color(0, 0, 0));
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btnHome, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
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

        lblRentalLogs.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblRentalLogs.setForeground(new java.awt.Color(0, 0, 0));
        lblRentalLogs.setText("Rental Logs");
        lblRentalLogs.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

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
        cmbSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sort by Rental Date", "Sort by Return Date", "Sort by Stage", "Sort by Status" }));
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

        txtSearch.setBackground(new java.awt.Color(0, 0, 0));
        txtSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSearch.setForeground(new java.awt.Color(255, 255, 255));
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        tblRentalTable.setBackground(new java.awt.Color(0, 0, 0));
        tblRentalTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblRentalTable.setForeground(new java.awt.Color(255, 255, 255));
        tblRentalTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Rental ID", "Account Name", "Movie Title", "Rental Date", "Return Date", "Rental Stage", "Rental Status", "Payment Status", "Total Cost"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRentalTable.setSelectionBackground(new java.awt.Color(74, 144, 226));
        tblRentalTable.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tblRentalTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRentalTableMouseClicked(evt);
            }
        });
        scrlRental.setViewportView(tblRentalTable);

        pnlForm.setBackground(new java.awt.Color(0, 0, 0));

        lblManageRentalDetails.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblManageRentalDetails.setForeground(new java.awt.Color(255, 255, 255));
        lblManageRentalDetails.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblManageRentalDetails.setText("Manage Rental Details");
        lblManageRentalDetails.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblRentalID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblRentalID.setForeground(new java.awt.Color(255, 255, 255));
        lblRentalID.setText("Rental ID:");

        lblAccountName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAccountName.setForeground(new java.awt.Color(255, 255, 255));
        lblAccountName.setText("Account Name:");

        lblMovieTitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMovieTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblMovieTitle.setText("Movie Title:");

        lblRentalDate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblRentalDate.setForeground(new java.awt.Color(255, 255, 255));
        lblRentalDate.setText("Rental Date:");

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

        lblTotalCost.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTotalCost.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalCost.setText("Total Cost:");

        lblUpfrontFee.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblUpfrontFee.setForeground(new java.awt.Color(255, 255, 255));
        lblUpfrontFee.setText("Upfront Fee:");

        txtUpfrontFee.setEditable(false);
        txtUpfrontFee.setBackground(new java.awt.Color(204, 204, 204));
        txtUpfrontFee.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtUpfrontFee.setForeground(new java.awt.Color(0, 0, 0));
        txtUpfrontFee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUpfrontFeeActionPerformed(evt);
            }
        });

        lblUpfrontFee1.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        lblUpfrontFee1.setForeground(new java.awt.Color(204, 204, 204));
        lblUpfrontFee1.setText("25% Upfront Fee");

        lblAmountPaid.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAmountPaid.setForeground(new java.awt.Color(255, 255, 255));
        lblAmountPaid.setText("Paid Amount:");

        txtPaidAmount.setBackground(new java.awt.Color(255, 255, 255));
        txtPaidAmount.setForeground(new java.awt.Color(0, 0, 0));

        lblWeeks.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        lblWeeks.setForeground(new java.awt.Color(204, 204, 204));
        lblWeeks.setText("N/A");

        cmbRentalStage.setBackground(new java.awt.Color(255, 255, 255));
        cmbRentalStage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbRentalStage.setForeground(new java.awt.Color(0, 0, 0));
        cmbRentalStage.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Requested", "Approved", "PickedUp", "Rejected" }));

        cmbRentalStatus.setBackground(new java.awt.Color(255, 255, 255));
        cmbRentalStatus.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbRentalStatus.setForeground(new java.awt.Color(0, 0, 0));
        cmbRentalStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pending", "Ongoing", "Returned", "Late", "Cancelled" }));

        lblWeekDuration.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        lblWeekDuration.setForeground(new java.awt.Color(204, 204, 204));
        lblWeekDuration.setText("Week Duration:");

        javax.swing.GroupLayout pnlFormLayout = new javax.swing.GroupLayout(pnlForm);
        pnlForm.setLayout(pnlFormLayout);
        pnlFormLayout.setHorizontalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblManageRentalDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormLayout.createSequentialGroup()
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblRentalID)
                            .addComponent(lblRentalStage)
                            .addComponent(lblReturnDate)
                            .addComponent(lblRentalDate)
                            .addComponent(lblMovieTitle)
                            .addComponent(lblAccountName))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormLayout.createSequentialGroup()
                                .addComponent(lblTotalCost)
                                .addGap(30, 30, 30))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormLayout.createSequentialGroup()
                                .addComponent(lblRentalStatus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))))
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlFormLayout.createSequentialGroup()
                        .addComponent(lblWeekDuration)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWeeks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblUpfrontFee1, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addComponent(txtTotalCost, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addComponent(txtRentalID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addComponent(txtAccountName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addComponent(txtMovieTitle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addComponent(txtRentalDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addComponent(txtReturnDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addComponent(cmbRentalStage, javax.swing.GroupLayout.Alignment.LEADING, 0, 248, Short.MAX_VALUE)
                    .addComponent(cmbRentalStatus, javax.swing.GroupLayout.Alignment.LEADING, 0, 248, Short.MAX_VALUE)
                    .addComponent(txtUpfrontFee, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
                .addGap(31, 31, 31))
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnConfirmTransaction, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUpfrontFee)
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addComponent(lblAmountPaid)
                        .addGap(18, 18, 18)
                        .addComponent(txtPaidAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 24, Short.MAX_VALUE))
        );
        pnlFormLayout.setVerticalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addComponent(lblManageRentalDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRentalID, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRentalID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReturnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtReturnDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblWeeks, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblWeekDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRentalStage, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbRentalStage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRentalStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbRentalStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalCost, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUpfrontFee, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUpfrontFee, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUpfrontFee1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAmountPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPaidAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnConfirmTransaction, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(pnlSideNav, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tglSort, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblRentalLogs)
                    .addComponent(scrlRental))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(pnlForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSideNav, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblRentalLogs)
                .addGap(4, 4, 4)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch)
                    .addComponent(cmbSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tglSort))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrlRental, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92))
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
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRentalLogsActionPerformed

    private void btnPaymentReviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaymentReviewActionPerformed
        new AdminPaymentReview().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnPaymentReviewActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty()){
             Message.error("Please enter a keyword to search.");
             return;
        }
        populateRentalRecord(keyword);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void cmbSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSortActionPerformed
        String sortQuery = txtSearch.getText().trim();
        populateRentalRecord(sortQuery); 
    }//GEN-LAST:event_cmbSortActionPerformed

    private void tglSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tglSortActionPerformed
        if (tglSort.isSelected()) {
        tglSort.setText("DESC");
        } else {
            tglSort.setText("ASC");
        }
        populateRentalRecord(txtSearch.getText().trim());
    }//GEN-LAST:event_tglSortActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void tblRentalTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRentalTableMouseClicked
       tblRentalTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // Handles tblRentalRecord click.
                int row = tblRentalTable.getSelectedRow();
                if (row >= 0) {
                    // Stores selected rental ID globally for reference.
                    selectedRentalID = Integer.parseInt(tblRentalTable.getValueAt(row, 0).toString());
                    
                    // Populate form fields with selected row data.
                    txtRentalID.setText(tblRentalTable.getValueAt(row, 0).toString()); 
                    txtAccountName.setText(tblRentalTable.getValueAt(row, 1).toString()); 
                    txtMovieTitle.setText(tblRentalTable.getValueAt(row, 2).toString()); 
                    txtRentalDate.setText(tblRentalTable.getValueAt(row, 3).toString()); 
                    txtReturnDate.setText(tblRentalTable.getValueAt(row, 4).toString()); 
                    cmbRentalStage.setSelectedItem(tblRentalTable.getValueAt(row, 5).toString());
                    cmbRentalStatus.setSelectedItem(tblRentalTable.getValueAt(row, 6).toString()); 
                    txtTotalCost.setText(tblRentalTable.getValueAt(row, 8).toString());
                    
                    // Extract total cost from col 8 and calculate the upfront fee.
                    String totalCostStr = tblRentalTable.getValueAt(row, 8).toString().replace("₱", "");
                    double totalCost = Double.parseDouble(totalCostStr);
                    double upfrontFee = totalCost * 0.25; // 25% downpayment/upfrontfee or ignore to pay in full.
                    txtUpfrontFee.setText(String.format("₱%.2f", upfrontFee));
                    
                    // Calcuate weeks based-off rentaldate & returndate.
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime rentalDateTime = LocalDateTime.parse(txtRentalDate.getText().trim(), formatter);
                        LocalDateTime returnDateTime = LocalDateTime.parse(txtReturnDate.getText().trim(), formatter);

                        long days = ChronoUnit.DAYS.between(rentalDateTime.toLocalDate(), returnDateTime.toLocalDate());
                        long weeks = days / 7;
                        lblWeeks.setText(weeks + " Week/s");
                    } catch (Exception e2) {
                        lblWeeks.setText("Invalid date format");
                    }
                }
            }
        }); 
    }//GEN-LAST:event_tblRentalTableMouseClicked

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        ActiveSession.clearSession(); // Clears active session.
        new Login().setVisible(true); // Returns to login frame.
        this.dispose();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
       if (selectedRentalID <= 0) { // Handles no row selection.
            Message.error("No rental selected.");
            return;
        }

        // Get selected rental stage & status from ComboBox.
        String selectedStage = cmbRentalStage.getSelectedItem().toString();
        String selectedStatus = cmbRentalStatus.getSelectedItem().toString();

        if (conn == null) return; // Validates connection
        rentalDAO = new RentalDAO(conn); // Use DAO to update

        // Update both stage and status
        boolean stageUpdated = rentalDAO.updateRentalStage(selectedRentalID, selectedStage);
        boolean statusUpdated = rentalDAO.updateRentalStatus(selectedRentalID, selectedStatus);

        if (stageUpdated && statusUpdated) {
            Message.show("Rental updated: Stage = " + selectedStage + ", Status = " + selectedStatus);
            populateRentalRecord(""); // Refresh table
            // clearForm(); // Reset form
        } else {
            Message.error("Failed to update rental.");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearForm();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnConfirmTransactionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmTransactionActionPerformed
        if (selectedRentalID <= 0) { // Handles no row being selected.
            Message.error("No rental selected.");
            return;
        }

        // Validates the rentalStatus if its approve proceed, else show this error.
        String currentStage = cmbRentalStage.getSelectedItem().toString();
        if (!currentStage.equalsIgnoreCase("Approved")) {
            Message.error("Only rentals in 'Approved' stage can be confirmed.");
            return;
        }

        String totalStr = txtTotalCost.getText().trim().replace("₱", "").replace(",", "").trim(); // Remove peso sign and commas.
        String paidStr = txtPaidAmount.getText().trim();
        double amountPaid, totalCost;

        // Validates empty inputs.
        if (paidStr.isEmpty()) {
            Message.error("Amount paid cannot be empty.");
            return;
        }

        // Validates non-int/double input.
        try {
            amountPaid = Double.parseDouble(paidStr);
            totalCost = Double.parseDouble(totalStr);
        } catch (NumberFormatException e) {
            Message.error("Invalid number format. Please enter valid numeric values.");
            return;
        }

        // Compare the amount whether the user decide to pay the upfront or full.
        String paymentStatus = (amountPaid == totalCost) ? "Paid Full" : "Paid Upfront";

        if (conn == null) return; // Validates the connection before proceeding.
        // Pass the connection to perform col updates.
        rentalDAO = new RentalDAO(conn);
        paymentDAO = new PaymentDAO(conn); 

        // Update existing payment record in tblPayments with new updated values.
        boolean updatedPayment = paymentDAO.updatePaymentStatusAndAmount(selectedRentalID, amountPaid, paymentStatus);
        
        // Verify if the update went smoothly or an error occured.
        boolean stageUpdated = false;
        boolean statusUpdated = false;

        // Only proceed to update stage/status if payment was successful.
        if (updatedPayment == true) {
            stageUpdated = rentalDAO.updateRentalStage(selectedRentalID, "Pickedup");
            statusUpdated = rentalDAO.updateRentalStatus(selectedRentalID, "Ongoing");
        }

        // If all went smoothly, we're done.
        if (updatedPayment == true && stageUpdated == true && statusUpdated == true) {
            Message.show("Transaction confirmed. Rental picked up and payment updated.");
            populateRentalRecord("");
            // clearForm();
        } else {
            Message.error("Failed to confirm transaction.");
        }
    }//GEN-LAST:event_btnConfirmTransactionActionPerformed

    private void txtTotalCostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalCostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalCostActionPerformed

    private void txtReturnDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtReturnDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtReturnDateActionPerformed

    private void txtRentalDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRentalDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRentalDateActionPerformed

    private void txtMovieTitleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMovieTitleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMovieTitleActionPerformed

    private void txtAccountNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAccountNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAccountNameActionPerformed

    private void txtRentalIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRentalIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRentalIDActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
       if (selectedRentalID <= 0) { // Handles no row being selected.
            Message.error("No rental selected.");
            return;
        }

        // Get selected rentalStage from ComboBox.
        String stage = cmbRentalStage.getSelectedItem().toString();

        // Allow deletion only if stage is 'Rejected' or 'Requested'
        if (!stage.equalsIgnoreCase("Rejected") && !stage.equalsIgnoreCase("Requested")) {
            Message.show("Only rentals in 'Rejected' or 'Requested' stage can be deleted.", "Delete Not Allowed");
            return;
        }

        boolean confirmed = Message.confirm("Are you sure you want to delete this rental?", "Confirm Delete");
        if (!confirmed) return;

        if (conn == null) return; // Validates connection.
        rentalDAO = new RentalDAO(conn); // Use DAO to delete rental
        boolean success = rentalDAO.deleteRental(selectedRentalID); // Deletes rental and payment record

        if (success) {
            Message.show("Rental deleted successfully.", "Delete Successful");
            populateRentalRecord(""); // Refresh table
            clearForm(); // Reset form
        } else {
            Message.error("Failed to delete rental.");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtUpfrontFeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUpfrontFeeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUpfrontFeeActionPerformed

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
            java.util.logging.Logger.getLogger(AdminRentalLogs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminRentalLogs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminRentalLogs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminRentalLogs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminRentalLogs().setVisible(true);
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
    private javax.swing.JLabel lblAmountPaid;
    private javax.swing.JLabel lblHeader1;
    private javax.swing.JLabel lblHeader2;
    private javax.swing.JLabel lblHeader3;
    private javax.swing.JLabel lblHeader4;
    private javax.swing.JLabel lblManageRentalDetails;
    private javax.swing.JLabel lblMovieTitle;
    private javax.swing.JLabel lblRentalDate;
    private javax.swing.JLabel lblRentalID;
    private javax.swing.JLabel lblRentalLogs;
    private javax.swing.JLabel lblRentalStage;
    private javax.swing.JLabel lblRentalStatus;
    private javax.swing.JLabel lblReturnDate;
    private javax.swing.JLabel lblTotalCost;
    private javax.swing.JLabel lblUpfrontFee;
    private javax.swing.JLabel lblUpfrontFee1;
    private javax.swing.JLabel lblWeekDuration;
    private javax.swing.JLabel lblWeeks;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlSideNav;
    private javax.swing.JScrollPane scrlRental;
    private javax.swing.JTable tblRentalTable;
    private javax.swing.JToggleButton tglSort;
    private javax.swing.JTextField txtAccountName;
    private javax.swing.JTextField txtMovieTitle;
    private javax.swing.JTextField txtPaidAmount;
    private javax.swing.JTextField txtRentalDate;
    private javax.swing.JTextField txtRentalID;
    private javax.swing.JTextField txtReturnDate;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTotalCost;
    private javax.swing.JTextField txtUpfrontFee;
    // End of variables declaration//GEN-END:variables
}

package cineflix;

import java.awt.Color;
import java.awt.Component;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class UserMyPayments extends javax.swing.JFrame {
    private List<Payment> payments; // Used in generate receipts and populate payment table.
    
    public UserMyPayments() {
        initComponents();
        this.setSize(1315, 675);
        this.setLocationRelativeTo(null); // Center the JFrame
        lblHeader4.setText("Welcome, " + ActiveSession.loggedInUsername); // Welcome message.
        setDefaultReceiptTxta();
        setDefaultTglSort();
        
        populatePaymentTable(""); // Populates tblPaymentRecord.
    }

    // Wraps the synopsys; proceed to new br when it exceed the length space.
    private void setDefaultReceiptTxta(){
        txtaReceipt.setLineWrap(true); 
        txtaReceipt.setWrapStyleWord(true);
        txtaReceipt.setEditable(false);
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
    
    private void populatePaymentTable(String keyword) {
        DefaultTableModel model = (DefaultTableModel) tblPaymentRecord.getModel();
        model.setRowCount(0); // Clear existing rows

        try {
            Connection conn = DBConnection.getConnection(); // Get database connection.
            if (conn == null) return; // DBConnection already shows error message
            
            PaymentDAO paymentDAO = new PaymentDAO(conn); // Pass the DB connection.
            // Retrieve payment records for the current user.
            payments = paymentDAO.getPaymentRecordsByAccountID(ActiveSession.loggedInAccountID); 
            validateOverdueChargesAndStatus(payments);
            payments = SearchUtils.searchUserPayment(payments, keyword);
            
            String selectedSort = cmbSort.getSelectedItem().toString();
            String selectedOrder = tglSort.isSelected() ? "DESC" : "ASC";
            
            switch (selectedSort) {
            case "Sort by Payment Date":
                SortUtils.sortUserPaymentsByPaymentDate(payments);
                break;
            case "Sort by Return Date":
                SortUtils.sortUserPaymentsByReturnDate(payments);
                break;
            case "Sort by Paid Amount":
                SortUtils.sortUserPaymentsByPaidAmount(payments);
                break;
            case "Sort by Balance":
                SortUtils.sortUserPaymentsByRemainingBalance(payments);
                break;
            case "Sort by Rental Cost":
                SortUtils.sortUserPaymentsByRentalCost(payments);
                break;
            case "Sort by Rental Status":
                SortUtils.sortUserPaymentsByRentalStatus(payments);
                break;
            default:
                break;
        }
            
        if ("DESC".equals(selectedOrder)) {
            Collections.reverse(payments);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy : h:mm a"); // Formats date (e.g., Nov 01, 2025 : 11:00 PM).

        for (Payment p : payments) {
            double rentalCost = p.getRentalCost();
            double overdue = p.getOverdueAmount();
            double amountPaid = p.getAmount();
            double remainingBalance = rentalCost + overdue - amountPaid;        

             String rentalDate = p.getRentalDate() != null
                ? p.getRentalDate().atStartOfDay().format(formatter)
                : "—";

            String returnDate = p.getReturnDate() != null
                ? p.getReturnDate().atStartOfDay().format(formatter)
                : "—";

            Object[] row = {
                p.getRentalID(),
                p.getMovieTitle(),
                rentalDate,
                returnDate,
                p.getRentalStatus(),
                p.getPaymentStatus(),
                "₱" + formatAmount(rentalCost),
                "₱" + formatAmount(overdue),
                "₱" + formatAmount(amountPaid),
                "₱" + formatAmount(remainingBalance)
            };
            model.addRow(row); // Add row to table
        }
        applyUserPaymentTableColorRenderers(tblPaymentRecord);
        
        // Hide rentalID column (index 0).
        tblPaymentRecord.getColumnModel().getColumn(0).setMinWidth(0);
        tblPaymentRecord.getColumnModel().getColumn(0).setMaxWidth(0);
        tblPaymentRecord.getColumnModel().getColumn(0).setWidth(0);
        
        } catch (Exception e) {
            
            Message.error("Error loading payment table: " + e.getMessage());
        }
    }

     // Formats the amount with two decimals.
    private String formatAmount(double amount) {
        return String.format("%.2f", amount);
    }
    
    // Validates returned date if late charge 10Php as additional payment.
    private void validateOverdueChargesAndStatus(List<Payment> payments) {
        LocalDateTime now = LocalDateTime.now();

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) return;

            PaymentDAO paymentDAO = new PaymentDAO(conn);
            RentalDAO rentalDAO = new RentalDAO(conn);

            for (Payment p : payments) {
                LocalDate returnDate = p.getReturnDate();
                boolean isPastDue = returnDate != null && returnDate.isBefore(now.toLocalDate());
                boolean isOngoing = "Ongoing".equalsIgnoreCase(p.getRentalStatus());

                double overdueAmount = 0.00;
                String rentalStatus = p.getRentalStatus();

                if (isPastDue) {
                    long daysLate = java.time.temporal.ChronoUnit.DAYS.between(returnDate, now.toLocalDate());
                    overdueAmount = daysLate * 10.00;

                    if (isOngoing) { // Only change status if it's still marked as 'Ongoing'
                        rentalStatus = "Late";
                    }
                }

                // Update DB values directly.
                paymentDAO.updateOverdueAmount(p.getPaymentID(), overdueAmount);
                rentalDAO.updateRentalStatus(p.getRentalID(), rentalStatus);
            }
        } catch (Exception e) {
            Message.error("Error validating overdue charges: " + e.getMessage());
        }
    }
    
    private void applyUserPaymentTableColorRenderers(JTable tblPaymentTable) {
        // Finals-defensible color palette
        Color colGray   = new Color(96, 96, 96); // Pending.
        Color colGreen  = new Color(44, 160, 110); // Paid Upfront.
        Color colPurple = new Color(102, 51, 153); // Paid Full.
        Color colRed    = new Color(180, 40, 40); // Overdue / Cancelled.
        Color colOrange = new Color(255, 140, 0); // Late.
        Color colBlue   = new Color(70, 130, 180); // Ongoing.
        Color colTeal   = new Color(0, 128, 128); // Returned.

        // Apply row-wide renderer based on paymentStatus (column 5), rentalStatus (column 4), and overdueAmount (column 7).
        tblPaymentTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                Object paymentStatusObj = table.getValueAt(row, 5); // paymentStatus.
                Object rentalStatusObj  = table.getValueAt(row, 4); // rentalStatus.
                Object overdueObj       = table.getValueAt(row, 7); // overdueAmount.

                if (isSelected) {
                    cell.setBackground(table.getSelectionBackground());
                    cell.setForeground(table.getSelectionForeground());
                } else {
                    Color baseColor = table.getBackground();

                    // Row-wide base color from paymentStatus.
                    if (paymentStatusObj != null) {
                        String status = paymentStatusObj.toString();
                        switch (status) {
                            case "Pending":      baseColor = colGray; break;
                            case "Paid Upfront": baseColor = colGreen; break;
                            case "Paid Full":    baseColor = colPurple; break;
                            default:             baseColor = table.getBackground();
                        }
                    }

                    // Column-specific override for rentalStatus (column 4).
                    if (column == 4 && rentalStatusObj != null) {
                        String rentalStatus = rentalStatusObj.toString();
                        switch (rentalStatus) {
                            case "Ongoing":
                                baseColor = colBlue;
                                break;
                            case "Returned":
                                baseColor = colTeal;
                                break;
                            case "Late":
                                baseColor = colOrange;
                                break;
                            case "Cancelled": 
                                baseColor = colRed; 
                                break;
                            default:
                        }
                    }

                    // Column-specific override for overdueAmount (column 7).
                    if (column == 7 && overdueObj != null) {
                        try {
                            String valStr = overdueObj.toString().replace("₱", "").replace(",", "").trim();
                            double overdue = Double.parseDouble(valStr);
                            if (overdue > 0.0) {
                                cell.setBackground(colRed);
                            } else {
                                cell.setBackground(baseColor);
                            }
                        } catch (Exception e) {
                            cell.setBackground(baseColor);
                        }
                    } else {
                        cell.setBackground(baseColor);
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
        btnBrowseMovies = new javax.swing.JButton();
        btnRentalHistory = new javax.swing.JButton();
        btnMyPayments = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        scrlHeader4 = new javax.swing.JScrollPane();
        lblHeader4 = new javax.swing.JTextArea();
        lblMyPayments = new javax.swing.JLabel();
        scrlRental = new javax.swing.JScrollPane();
        tblPaymentRecord = new javax.swing.JTable();
        pnlReceipt = new javax.swing.JPanel();
        scrlReceipt = new javax.swing.JScrollPane();
        txtaReceipt = new javax.swing.JTextArea();
        lblMyPayments1 = new javax.swing.JLabel();
        btnGenerateReceipt = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        tglSort = new javax.swing.JToggleButton();
        cmbSort = new javax.swing.JComboBox<>();
        btnSearch = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        btnReset = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CineFlix: My Payments");
        setMinimumSize(new java.awt.Dimension(1315, 675));
        setResizable(false);
        setSize(new java.awt.Dimension(1315, 675));

        pnlMain.setBackground(new java.awt.Color(255, 255, 255));
        pnlMain.setMaximumSize(new java.awt.Dimension(1315, 675));
        pnlMain.setMinimumSize(new java.awt.Dimension(1315, 675));
        pnlMain.setPreferredSize(new java.awt.Dimension(1315, 675));
        pnlMain.setRequestFocusEnabled(false);

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

        btnMyPayments.setBackground(new java.awt.Color(255, 255, 255));
        btnMyPayments.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnMyPayments.setForeground(new java.awt.Color(0, 0, 0));
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
            .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(btnBrowseMovies, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRentalHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMyPayments, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblMyPayments.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblMyPayments.setForeground(new java.awt.Color(0, 0, 0));
        lblMyPayments.setText("My Payments");
        lblMyPayments.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        tblPaymentRecord.setBackground(new java.awt.Color(0, 0, 0));
        tblPaymentRecord.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblPaymentRecord.setForeground(new java.awt.Color(255, 255, 255));
        tblPaymentRecord.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Rental ID", "Movie Title", "Rental Date", "Return Date", "Rental Status", "Payment Status", "Rental Cost", "Overdue Amount", "Paid Amount", "Remaining Balance"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
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
        scrlRental.setViewportView(tblPaymentRecord);

        pnlReceipt.setBackground(new java.awt.Color(0, 0, 0));

        txtaReceipt.setBackground(new java.awt.Color(204, 204, 204));
        txtaReceipt.setColumns(20);
        txtaReceipt.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtaReceipt.setForeground(new java.awt.Color(0, 0, 0));
        txtaReceipt.setRows(5);
        scrlReceipt.setViewportView(txtaReceipt);

        lblMyPayments1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblMyPayments1.setForeground(new java.awt.Color(255, 255, 255));
        lblMyPayments1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMyPayments1.setText("Payment Receipt");
        lblMyPayments1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout pnlReceiptLayout = new javax.swing.GroupLayout(pnlReceipt);
        pnlReceipt.setLayout(pnlReceiptLayout);
        pnlReceiptLayout.setHorizontalGroup(
            pnlReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlReceiptLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(scrlReceipt, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlReceiptLayout.createSequentialGroup()
                .addComponent(lblMyPayments1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 20, Short.MAX_VALUE))
        );
        pnlReceiptLayout.setVerticalGroup(
            pnlReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlReceiptLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMyPayments1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrlReceipt, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnGenerateReceipt.setBackground(new java.awt.Color(0, 204, 153));
        btnGenerateReceipt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGenerateReceipt.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerateReceipt.setText("Generate Receipt");
        btnGenerateReceipt.setFocusable(false);
        btnGenerateReceipt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateReceiptActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(153, 153, 255));
        btnClear.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setText("Clear Receipt");
        btnClear.setFocusable(false);
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
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

        cmbSort.setBackground(new java.awt.Color(0, 0, 0));
        cmbSort.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmbSort.setForeground(new java.awt.Color(255, 255, 255));
        cmbSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sort by Payment Date", "Sort by Return Date", "Sort by Paid Amount", "Sort by Balance", "Sort by Rental Cost", "Sort by Rental Status" }));
        cmbSort.setFocusable(false);
        cmbSort.setRequestFocusEnabled(false);
        cmbSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSortActionPerformed(evt);
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

        txtSearch.setBackground(new java.awt.Color(0, 0, 0));
        txtSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSearch.setForeground(new java.awt.Color(255, 255, 255));
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(0, 0, 0));
        btnReset.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setText("Reset");
        btnReset.setFocusable(false);
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(pnlSideNav, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainLayout.createSequentialGroup()
                            .addComponent(btnClear)
                            .addGap(18, 18, 18)
                            .addComponent(btnGenerateReceipt, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(lblMyPayments)
                        .addComponent(scrlRental, javax.swing.GroupLayout.PREFERRED_SIZE, 830, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addComponent(txtSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tglSort, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(pnlReceipt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSideNav, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblMyPayments)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch)
                    .addComponent(cmbSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tglSort)
                    .addComponent(btnReset))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrlRental, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGenerateReceipt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(109, 109, 109))
            .addComponent(pnlReceipt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        new UserDashboard().setVisible(true);
        this.dispose();
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
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMyPaymentsActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        ActiveSession.clearSession(); // Clears active session.
        new Login().setVisible(true); // Returns to login frame.
        this.dispose();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void tblPaymentRecordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPaymentRecordMouseClicked

    }//GEN-LAST:event_tblPaymentRecordMouseClicked

    private void btnGenerateReceiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateReceiptActionPerformed
        int selectedRow = tblPaymentRecord.getSelectedRow(); // Get selected row index.

        if (selectedRow == -1) { // If theres none, display this error message.
            Message.error("Please select a payment record first.");
            return;
        }

        // Clear previous receipt before generating new one.
        txtaReceipt.setText("");
        
        // Get the corresponding Payment object from the list.
        Payment selectedPayment = payments.get(selectedRow);

        // Get all infos in that certain object model.
        int rentalID = selectedPayment.getRentalID();
        String movieTitle = selectedPayment.getMovieTitle();
        String rentalStatus = selectedPayment.getRentalStatus();
        String paymentStatus = selectedPayment.getPaymentStatus();
        double rentalCost = selectedPayment.getRentalCost();
        double amount = selectedPayment.getAmount();
        double overdue = selectedPayment.getOverdueAmount();
        double totalDue = rentalCost + overdue - amount; 

        // Format dates.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy : h:mm a");
        String rentalDate = selectedPayment.getRentalDate() != null
            ? selectedPayment.getRentalDate().atStartOfDay().format(formatter)
            : "—";

        String returnDate = selectedPayment.getReturnDate() != null
            ? selectedPayment.getReturnDate().atStartOfDay().format(formatter)
            : "—";


        String br = "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";
        // Displays the receipt inside the text area.
        txtaReceipt.append(br + "\n");
        txtaReceipt.append("CINEFLIX: PAYMENT RECEIPT\n");
        txtaReceipt.append(br + "\n");
        txtaReceipt.append("Rental ID: " + rentalID + "\n");
        txtaReceipt.append("Movie Title: " + movieTitle + "\n");
        txtaReceipt.append("Rental Date: " + rentalDate + "\n");
        txtaReceipt.append("Return Date: " + returnDate + "\n");
        txtaReceipt.append("Rental Status: " + rentalStatus + "\n");
        txtaReceipt.append("Payment Status: " + paymentStatus + "\n");
        txtaReceipt.append(br + "\n\n");

        txtaReceipt.append(br + "\n");
        txtaReceipt.append("Rental Cost: ₱" + formatAmount(rentalCost) + "\n");
        txtaReceipt.append("Amount Paid: ₱" + formatAmount(amount) + "\n");
        txtaReceipt.append("Overdue Amount: ₱" + formatAmount(overdue) + "\n");
        txtaReceipt.append(br + "\n");
        txtaReceipt.append("REMAINING BALANCE: ₱" + formatAmount(totalDue) + "\n");
        txtaReceipt.append(br + "\n\n");

        txtaReceipt.append(br + "\n");
        txtaReceipt.append(br + "\n");
        txtaReceipt.append("Please proceed to the front desk and say:\n");
        txtaReceipt.append("“Payment for Rental ID: " + rentalID + "”\n");
        txtaReceipt.append(br + "\n");
        txtaReceipt.append(br);
    }//GEN-LAST:event_btnGenerateReceiptActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        txtaReceipt.setText(""); // Resets receipts textarea back to empty string.
    }//GEN-LAST:event_btnClearActionPerformed

    private void tglSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tglSortActionPerformed
        if (tglSort.isSelected()) {
            tglSort.setText("DESC");
        } else {
            tglSort.setText("ASC");
        }
        populatePaymentTable(txtSearch.getText().trim());
    }//GEN-LAST:event_tglSortActionPerformed

    private void cmbSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSortActionPerformed
        String sortQuery = txtSearch.getText().trim();
        populatePaymentTable(sortQuery);
    }//GEN-LAST:event_cmbSortActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty()){
            Message.error("Please enter a keyword to search.");
            return;
        }
        populatePaymentTable(keyword);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        txtSearch.setText(""); // Clear the search field.
        cmbSort.setSelectedIndex(0); // Sets sort back to default value.
        tglSort.setSelected(false);
        tglSort.setText("ASC");
        populatePaymentTable(""); // Reset table to show all movies.
    }//GEN-LAST:event_btnResetActionPerformed

    /**
     * @param args the command br arguments
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
            java.util.logging.Logger.getLogger(UserMyPayments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserMyPayments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserMyPayments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserMyPayments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserMyPayments().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowseMovies;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnGenerateReceipt;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMyPayments;
    private javax.swing.JButton btnRentalHistory;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cmbSort;
    private javax.swing.JLabel lblHeader1;
    private javax.swing.JLabel lblHeader2;
    private javax.swing.JLabel lblHeader3;
    private javax.swing.JTextArea lblHeader4;
    private javax.swing.JLabel lblMyPayments;
    private javax.swing.JLabel lblMyPayments1;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlReceipt;
    private javax.swing.JPanel pnlSideNav;
    private javax.swing.JScrollPane scrlHeader4;
    private javax.swing.JScrollPane scrlReceipt;
    private javax.swing.JScrollPane scrlRental;
    private javax.swing.JTable tblPaymentRecord;
    private javax.swing.JToggleButton tglSort;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextArea txtaReceipt;
    // End of variables declaration//GEN-END:variables
}
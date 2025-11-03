package cineflix;

import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class UserRentalHistory extends javax.swing.JFrame {

    public UserRentalHistory() {
        initComponents();
        this.setSize(1315, 675);
        this.setLocationRelativeTo(null); // Center the JFrame
        lblHeader4.setText("Welcome, " + ActiveSession.loggedInUsername); // Welcome message.
        setDefaultTglSort();
        
        populateRentalTable(""); // Populates the rental table with only the necessary details.
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
    
    // Resets rental table.
    private void clearForm() {
        txtSearch.setText(""); // lear the search field.
        populateRentalTable(""); // Reset table to show all movies.
    }
    
    // Populates rental table of current loggedin user.
    private void populateRentalTable(String keyword) {
        DefaultTableModel model = (DefaultTableModel) tblRentalTable.getModel();
        model.setRowCount(0); // Clear existing rows

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) return;

            RentalDAO rentalDAO = new RentalDAO(conn);
            List<Rental> rentals = rentalDAO.getUserRentalHistory(ActiveSession.loggedInAccountID); 
            rentals = SearchUtils.searchUserRentalHistory(rentals, keyword);

            String selectedSort = cmbSort.getSelectedItem().toString();
            String selectedOrder = tglSort.isSelected() ? "DESC" : "ASC";

            switch (selectedSort) {
                case "Sort by Rental Date":
                    SortUtils.sortUserRentalByRentalDate(rentals);
                    break;
                case "Sort by Return Date":
                    SortUtils.sortUserRentalByReturnDate(rentals);
                    break;
                case "Sort by Stage":
                    SortUtils.sortUserRentalByStage(rentals);
                    break;
                case "Sort by Status":
                    SortUtils.sortUserRentalByStatus(rentals);
                    break;
                default:
                    break;
            }

            if ("DESC".equals(selectedOrder)) {
                Collections.reverse(rentals);
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy : h:mm a"); // e.g., November 10, 2025 at 2:30 PM

            for (Rental rental : rentals) {
                // Format rental and return dates for display
                String rentalDate = rental.getRentalDate() != null
                    ? rental.getRentalDate().toLocalDateTime().format(formatter)
                    : "—";

                String returnDate = rental.getReturnDate() != null
                    ? rental.getReturnDate().toLocalDateTime().format(formatter)
                    : "—";

                Object[] row = {
                    rental.getRentalID(),
                    rental.getMovieTitle(),
                    rentalDate,
                    returnDate,
                    String.format("₱%.2f", rental.getRentalCost()),
                    String.format("₱%.2f", rental.getAmountPaid()),
                    String.format("₱%.2f", rental.getRemainingBalance()),
                    rental.getRentalStage(),
                    rental.getRentalStatus()
                };
                model.addRow(row);
            }
            applyRentalColorRenderers(tblRentalTable);
        } catch (Exception e) {
            Message.error("Error loading rental rentals:\n" + e.getMessage());
        }
    }

    private void applyRentalColorRenderers(JTable tblRentalTable) {
        // Finals-defensible color palette
        Color colGray   = new Color(96, 96, 96); // Neutral (Requested, Pending).
        Color colGreen  = new Color(44, 160, 110); // Active (Approved, Ongoing).
        Color colPurple = new Color(102, 51, 153); // Completed (PickedUp, Returned).
        Color colRed    = new Color(180, 40, 40); // Problematic (Rejected, Late, Cancelled).

        // Apply row-wide renderer based on rentalStage (column index 7)
        tblRentalTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (isSelected) {
                    cell.setBackground(table.getSelectionBackground());
                    cell.setForeground(table.getSelectionForeground());
                } else {
                    Object stageVal = table.getValueAt(row, 7); // rentalStage column.
                    if (column != 8 && stageVal != null) { // skip rentalStatus column.
                        String stage = stageVal.toString();
                        switch (stage) {
                            case "Requested": cell.setBackground(colGray); break;
                            case "Approved":  cell.setBackground(colGreen); break;
                            case "PickedUp":  cell.setBackground(colPurple); break;
                            case "Rejected":  cell.setBackground(colRed); break;
                            default:          cell.setBackground(table.getBackground());
                        }
                    } else {
                        cell.setBackground(table.getBackground());
                    }
                    cell.setForeground(table.getForeground());
                }
                return cell;
            }
        });

        // Apply column-specific renderer to rentalStatus (column index 8).
        tblRentalTable.getColumnModel().getColumn(8).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (isSelected) {
                    cell.setBackground(table.getSelectionBackground());
                    cell.setForeground(table.getSelectionForeground());
                } else {
                    String status = value != null ? value.toString() : "";
                    switch (status) {
                        case "Pending":
                            cell.setBackground(colGray);
                            break;
                        case "Ongoing":
                            cell.setBackground(colGreen);
                            break;
                        case "Returned":
                            cell.setBackground(colPurple);
                            break;
                        case "Late":
                        case "Cancelled":
                            cell.setBackground(colRed);
                            break;
                        default:
                            cell.setBackground(table.getBackground());
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
        jScrollPane5 = new javax.swing.JScrollPane();
        lblHeader4 = new javax.swing.JTextArea();
        lblRentalHistory = new javax.swing.JLabel();
        scrlRental = new javax.swing.JScrollPane();
        tblRentalTable = new javax.swing.JTable();
        cmbSort = new javax.swing.JComboBox<>();
        tglSort = new javax.swing.JToggleButton();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CineFlix: Rental History");
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

        btnRentalHistory.setBackground(new java.awt.Color(255, 255, 255));
        btnRentalHistory.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnRentalHistory.setForeground(new java.awt.Color(0, 0, 0));
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
        jScrollPane5.setViewportView(lblHeader4);

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
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        lblRentalHistory.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblRentalHistory.setForeground(new java.awt.Color(0, 0, 0));
        lblRentalHistory.setText("Rental History");
        lblRentalHistory.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

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
                "Rental ID", "Movie Title", "Rental Date", "Return Date", "Rental Cost", "Amount Paid", "Remaining Balance", "Rental Stage", "Rental Status"
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
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRentalHistory)
                    .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(pnlMainLayout.createSequentialGroup()
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 714, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnSearch)
                            .addGap(12, 12, 12)
                            .addComponent(cmbSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tglSort, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(scrlRental, javax.swing.GroupLayout.PREFERRED_SIZE, 1132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSideNav, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblRentalHistory)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch)
                    .addComponent(cmbSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tglSort)
                    .addComponent(btnReset))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrlRental, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
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
        new UserDashboard().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnHomeActionPerformed

    private void btnBrowseMoviesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseMoviesActionPerformed
        new UserBrowseMovies().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBrowseMoviesActionPerformed

    private void btnRentalHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRentalHistoryActionPerformed
        // TODO add your handling code here:
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

    private void tblRentalTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRentalTableMouseClicked

    }//GEN-LAST:event_tblRentalTableMouseClicked

    private void cmbSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSortActionPerformed
        String sortQuery = txtSearch.getText().trim();
        populateRentalTable(sortQuery);
    }//GEN-LAST:event_cmbSortActionPerformed

    private void tglSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tglSortActionPerformed
        if (tglSort.isSelected()) {
            tglSort.setText("DESC");
        } else {
            tglSort.setText("ASC");
        }
        populateRentalTable(txtSearch.getText().trim());
    }//GEN-LAST:event_tglSortActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty()){
             Message.error("Please enter a keyword to search.");
             return;
        }
        populateRentalTable(keyword);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        clearForm();
    }//GEN-LAST:event_btnResetActionPerformed

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
            java.util.logging.Logger.getLogger(UserRentalHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserRentalHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserRentalHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserRentalHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserRentalHistory().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowseMovies;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMyPayments;
    private javax.swing.JButton btnRentalHistory;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cmbSort;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblHeader1;
    private javax.swing.JLabel lblHeader2;
    private javax.swing.JLabel lblHeader3;
    private javax.swing.JTextArea lblHeader4;
    private javax.swing.JLabel lblRentalHistory;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlSideNav;
    private javax.swing.JScrollPane scrlRental;
    private javax.swing.JTable tblRentalTable;
    private javax.swing.JToggleButton tglSort;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
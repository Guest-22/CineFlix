package cineflix;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.util.List;

public class UserMyPayments extends javax.swing.JFrame {
    private List<Payment> paymentRecords; // Used in generate receipts and populate payment table.
    
    public UserMyPayments() {
        initComponents();
        this.setSize(1315, 675);
        this.setLocationRelativeTo(null); // Center the JFrame
        lblHeader4.setText("Welcome, " + ActiveSession.loggedInUsername); // Welcome message.
        setDefaultReceiptTxta();
        
        populatePaymentTable(); // Populates tblPaymentRecord.
    }

    // Wraps the synopsys; proceed to new br when it exceed the length space.
    private void setDefaultReceiptTxta(){
        txtaReceipt.setLineWrap(true); 
        txtaReceipt.setWrapStyleWord(true);
        txtaReceipt.setEditable(false);
    }
    
    private void populatePaymentTable() {
        DefaultTableModel model = (DefaultTableModel) tblPaymentRecord.getModel();
        model.setRowCount(0); // Clear existing rows

        Connection conn = DBConnection.getConnection(); // Get database connection.
        if (conn == null) return; // DBConnection already shows error message

        try {
            PaymentDAO paymentDAO = new PaymentDAO(conn); // Pass the DB connection.
            // Retrieve payment records for the current user.
            paymentRecords = paymentDAO.getPaymentRecordsByAccountID(ActiveSession.loggedInAccountID); 

            for (Payment p : paymentRecords) {
                double totalDue = p.getRentalCost() + p.getOverdueAmount() - p.getAmount(); // Compute total amount due.

                /*Object[] row = {
                p.getRentalID(),
                p.getMovieTitle(),
                p.getRentalDate(),
                p.getReturnDate(),
                p.getRentalStatus(),
                p.getPaymentStatus(),
                "₱" + formatAmount(p.getRentalCost()),
                "₱" + formatAmount(p.getAmount()),
                "₱" + formatAmount(p.getOverdueAmount()),
                "₱" + formatAmount(totalDue)
                };*/

                // Only shows the necessary table data to user.
                Object[] row = {
                p.getRentalID(),
                p.getMovieTitle(),
                p.getReturnDate(),
                p.getPaymentStatus(),
                "₱" + formatAmount(totalDue)
            };

                model.addRow(row); // Add row to table
            }
        } catch (Exception e) {
            e.printStackTrace();
            Message.error("Error loading payment table: " + e.getMessage());
        }
    }

    // Formats the amount with two decimals.
    private String formatAmount(double amount) {
        return String.format("%.2f", amount);
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
        lblMyPayments = new javax.swing.JLabel();
        scrlRental = new javax.swing.JScrollPane();
        tblPaymentRecord = new javax.swing.JTable();
        pnlReceipt = new javax.swing.JPanel();
        scrlReceipt = new javax.swing.JScrollPane();
        txtaReceipt = new javax.swing.JTextArea();
        lblMyPayments1 = new javax.swing.JLabel();
        btnGenerateReceipt = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();

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
                .addContainerGap(18, Short.MAX_VALUE))
            .addComponent(btnHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnBrowseMovies, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnRentalHistory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnMyPayments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Rental ID", "Movie Title", "Return Date", "PaymentStatus", "Total Fee"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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
        btnClear.setText("Clear");
        btnClear.setFocusable(false);
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainLayout.createSequentialGroup()
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnGenerateReceipt, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblMyPayments)
                    .addComponent(scrlRental, javax.swing.GroupLayout.PREFERRED_SIZE, 830, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addComponent(scrlRental, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGenerateReceipt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(143, 143, 143))
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
        Payment selectedPayment = paymentRecords.get(selectedRow);

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
        String rentalDate = selectedPayment.getRentalDate().toString();
        String returnDate = selectedPayment.getReturnDate().toString();
        String generatedDate = java.time.LocalDateTime.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a"));

        String br = "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";
        // Displays the receipts inside the text area.
        txtaReceipt.append(br +"\n");
        txtaReceipt.append("CINEFLIX: PAYMENT RECEIPT\n");
        txtaReceipt.append(br + "\n");
        txtaReceipt.append("Rental ID: " + rentalID + "\n");
        txtaReceipt.append("Movie Title: " + movieTitle + "\n");
        txtaReceipt.append("Rental Date: " + rentalDate + "\n");
        txtaReceipt.append("Return Date: " + returnDate + "\n");
        txtaReceipt.append("Rental Status: " + rentalStatus + "\n");
        txtaReceipt.append("Payment Status: " + paymentStatus + "\n");
        txtaReceipt.append(br +"\n\n");

        txtaReceipt.append(br +"\n");
        txtaReceipt.append("Rental Cost: ₱" + formatAmount(rentalCost) + "\n");
        txtaReceipt.append("Amount Paid: ₱" + formatAmount(amount) + "\n");
        txtaReceipt.append("Overdue Amount: ₱" + formatAmount(overdue) + "\n");
        txtaReceipt.append(br +"\n");
        txtaReceipt.append("TOTAL FEE: ₱" + formatAmount(totalDue) + "\n");
        txtaReceipt.append(br +"\n\n");

        txtaReceipt.append(br +"\n");
        txtaReceipt.append(br +"\n");
        txtaReceipt.append("Please proceed to the front desk and say:\n");
        txtaReceipt.append("“Payment for Rental ID: " + rentalID + "”\n");
        txtaReceipt.append("Generated on: " + generatedDate + "\n");
        txtaReceipt.append(br +"\n");
        txtaReceipt.append(br +"\n");
    }//GEN-LAST:event_btnGenerateReceiptActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        txtaReceipt.setText(""); // Resets receipts textarea back to empty string.
    }//GEN-LAST:event_btnClearActionPerformed

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
    private javax.swing.JLabel lblHeader1;
    private javax.swing.JLabel lblHeader2;
    private javax.swing.JLabel lblHeader3;
    private javax.swing.JLabel lblHeader4;
    private javax.swing.JLabel lblMyPayments;
    private javax.swing.JLabel lblMyPayments1;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlReceipt;
    private javax.swing.JPanel pnlSideNav;
    private javax.swing.JScrollPane scrlReceipt;
    private javax.swing.JScrollPane scrlRental;
    private javax.swing.JTable tblPaymentRecord;
    private javax.swing.JTextArea txtaReceipt;
    // End of variables declaration//GEN-END:variables
}

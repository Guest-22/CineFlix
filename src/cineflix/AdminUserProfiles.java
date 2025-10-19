package cineflix;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class AdminUserProfiles extends javax.swing.JFrame {
    // Declaring variables.
    private Connection conn;
    private AccountDAO accountDAO;
    private PersonalInfoDAO infoDAO;

    private int selectedInfoID = -1; // Stores the ID of the selected info.
    
    public AdminUserProfiles() {
        initComponents();
        this.setLocationRelativeTo(null); // Centers the JFrame.
        
        // Attemps to get a DB Connection.
        conn = DBConnection.getConnection();
        if (conn == null) return; // Validates the connection before continuing; Error already handled inside DBConnection.
        infoDAO = new PersonalInfoDAO(conn); // Pass the connection as an argument inside PersonalInfoDAO class for CRUD methods.
        accountDAO = new AccountDAO(conn); // Pass the connection as an argument inside AccountDAO class for CRUD methods.
        
        populateInfoTable();
    }

    // Clears all inputs.
    public void clearForm() {
        txtFullName.setText("");
        radMale.setSelected(false);
        radFemale.setSelected(false);
        txtEmail.setText("");
        txtContactNum.setText("");
        txtaAddress.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        selectedInfoID = -1; // Deselect ID just in case.
    }

    // Populates personal info table.
    public void populateInfoTable() {
        DefaultTableModel model = (DefaultTableModel) tblInfoRecord.getModel();
        model.setRowCount(0); // Clear table

        List<PersonalInfo> profiles = infoDAO.getAllInfoWithUsername();
        for (PersonalInfo p : profiles) {
            Object[] row = {
                p.getInfoID(),
                p.getUsername(),
                p.getFullName(),
                p.getSex(),
                p.getEmail(),
                p.getContactNum(),
                p.getAddress()
            };
            model.addRow(row);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGrpSex = new javax.swing.ButtonGroup();
        pnlMain = new javax.swing.JPanel();
        pnlSideNav = new javax.swing.JPanel();
        lblHeader1 = new javax.swing.JLabel();
        lblHeader3 = new javax.swing.JLabel();
        lblHeader2 = new javax.swing.JLabel();
        btnMovieInventory = new javax.swing.JButton();
        btnUserProfiles = new javax.swing.JButton();
        btnRentalLogs = new javax.swing.JButton();
        btnPaymentReview = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        lblHeader4 = new javax.swing.JLabel();
        pnlForm = new javax.swing.JPanel();
        lblManageUserProfiles = new javax.swing.JLabel();
        lblFullName = new javax.swing.JLabel();
        txtFullName = new javax.swing.JTextField();
        lblSex = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtContactNum = new javax.swing.JTextField();
        lblContact = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        scrlAddress = new javax.swing.JScrollPane();
        txtaAddress = new javax.swing.JTextArea();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        lblUsername = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        chkShowPassword = new javax.swing.JCheckBox();
        radMale = new javax.swing.JRadioButton();
        radFemale = new javax.swing.JRadioButton();
        lblUserProfiles = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        cmbSort = new javax.swing.JComboBox<>();
        tglSort = new javax.swing.JToggleButton();
        txtSearch = new javax.swing.JTextField();
        scrpAccountRecord = new javax.swing.JScrollPane();
        tblInfoRecord = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CineFlix: User Profiles");

        pnlMain.setBackground(new java.awt.Color(255, 255, 255));
        pnlMain.setPreferredSize(new java.awt.Dimension(1000, 600));

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

        btnUserProfiles.setBackground(new java.awt.Color(255, 255, 255));
        btnUserProfiles.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnUserProfiles.setForeground(new java.awt.Color(0, 0, 0));
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

        lblHeader4.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblHeader4.setForeground(new java.awt.Color(255, 255, 255));
        lblHeader4.setText("Welcome, Admin");
        lblHeader4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

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
            .addComponent(btnMovieInventory, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
            .addComponent(btnUserProfiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnRentalLogs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnPaymentReview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        pnlForm.setBackground(new java.awt.Color(0, 0, 0));
        pnlForm.setPreferredSize(new java.awt.Dimension(416, 587));

        lblManageUserProfiles.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblManageUserProfiles.setForeground(new java.awt.Color(255, 255, 255));
        lblManageUserProfiles.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblManageUserProfiles.setText("Manage User Profiles");
        lblManageUserProfiles.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblFullName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblFullName.setForeground(new java.awt.Color(255, 255, 255));
        lblFullName.setText("Full Name:");

        txtFullName.setBackground(new java.awt.Color(255, 255, 255));
        txtFullName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtFullName.setForeground(new java.awt.Color(0, 0, 0));
        txtFullName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFullNameActionPerformed(evt);
            }
        });

        lblSex.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblSex.setForeground(new java.awt.Color(255, 255, 255));
        lblSex.setText("Sex:");

        lblEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(255, 255, 255));
        lblEmail.setText("Email:");

        txtEmail.setBackground(new java.awt.Color(255, 255, 255));
        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(0, 0, 0));
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        txtContactNum.setBackground(new java.awt.Color(255, 255, 255));
        txtContactNum.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtContactNum.setForeground(new java.awt.Color(0, 0, 0));
        txtContactNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContactNumActionPerformed(evt);
            }
        });

        lblContact.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblContact.setForeground(new java.awt.Color(255, 255, 255));
        lblContact.setText("Contact:");

        lblAddress.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAddress.setForeground(new java.awt.Color(255, 255, 255));
        lblAddress.setText("Address:");

        txtaAddress.setBackground(new java.awt.Color(255, 255, 255));
        txtaAddress.setColumns(20);
        txtaAddress.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtaAddress.setForeground(new java.awt.Color(0, 0, 0));
        txtaAddress.setRows(5);
        scrlAddress.setViewportView(txtaAddress);

        btnAdd.setBackground(new java.awt.Color(0, 204, 153));
        btnAdd.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Add");
        btnAdd.setFocusable(false);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
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

        lblUsername.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblUsername.setForeground(new java.awt.Color(255, 255, 255));
        lblUsername.setText("Username:");

        lblPassword.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblPassword.setForeground(new java.awt.Color(255, 255, 255));
        lblPassword.setText("Password:");

        txtUsername.setBackground(new java.awt.Color(255, 255, 255));
        txtUsername.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtUsername.setForeground(new java.awt.Color(0, 0, 0));
        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });

        txtPassword.setBackground(new java.awt.Color(255, 255, 255));
        txtPassword.setForeground(new java.awt.Color(0, 0, 0));
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        chkShowPassword.setBackground(new java.awt.Color(0, 0, 0));
        chkShowPassword.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        chkShowPassword.setForeground(new java.awt.Color(204, 204, 204));
        chkShowPassword.setText("Show Password");
        chkShowPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkShowPasswordActionPerformed(evt);
            }
        });

        radMale.setBackground(new java.awt.Color(0, 0, 0));
        btnGrpSex.add(radMale);
        radMale.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        radMale.setForeground(new java.awt.Color(255, 255, 255));
        radMale.setText("Male");
        radMale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radMaleActionPerformed(evt);
            }
        });

        radFemale.setBackground(new java.awt.Color(0, 0, 0));
        btnGrpSex.add(radFemale);
        radFemale.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        radFemale.setForeground(new java.awt.Color(255, 255, 255));
        radFemale.setText("Female");

        javax.swing.GroupLayout pnlFormLayout = new javax.swing.GroupLayout(pnlForm);
        pnlForm.setLayout(pnlFormLayout);
        pnlFormLayout.setHorizontalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlFormLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(lblUsername))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblPassword)))
                        .addGap(18, 18, 18)
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlFormLayout.createSequentialGroup()
                                .addComponent(chkShowPassword)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtUsername)
                            .addComponent(txtPassword)))
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlFormLayout.createSequentialGroup()
                                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFullName)
                                    .addComponent(lblSex))
                                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlFormLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(txtFullName))
                                    .addGroup(pnlFormLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(radMale, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(radFemale, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(pnlFormLayout.createSequentialGroup()
                                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                                    .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(pnlFormLayout.createSequentialGroup()
                                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEmail)
                                    .addComponent(lblContact)
                                    .addComponent(lblAddress))
                                .addGap(31, 31, 31)
                                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtContactNum)
                                    .addComponent(scrlAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                                    .addComponent(txtEmail))))))
                .addContainerGap(25, Short.MAX_VALUE))
            .addComponent(lblManageUserProfiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlFormLayout.setVerticalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addComponent(lblManageUserProfiles, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFullName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSex, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(radMale)
                    .addComponent(radFemale))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblContact, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtContactNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrlAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAddress))
                .addGap(11, 11, 11)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkShowPassword)
                .addGap(41, 41, 41)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        lblUserProfiles.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblUserProfiles.setForeground(new java.awt.Color(0, 0, 0));
        lblUserProfiles.setText("User Profiles");
        lblUserProfiles.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

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
        cmbSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sort by Title", "Sort by Genre", "Sort by Year" }));
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

        tblInfoRecord.setBackground(new java.awt.Color(0, 0, 0));
        tblInfoRecord.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblInfoRecord.setForeground(new java.awt.Color(255, 255, 255));
        tblInfoRecord.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "InfoID", "Username", "Full Name", "Sex", "Email", "Contact", "Address"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblInfoRecord.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblInfoRecordMouseClicked(evt);
            }
        });
        scrpAccountRecord.setViewportView(tblInfoRecord);

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
                    .addComponent(lblUserProfiles)
                    .addComponent(scrpAccountRecord))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(pnlForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSideNav, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlForm, javax.swing.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUserProfiles)
                .addGap(4, 4, 4)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch)
                    .addComponent(cmbSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tglSort))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrpAccountRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(122, 122, 122))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, 1312, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, 672, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMovieInventoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMovieInventoryActionPerformed
        new AdminMovieInventory().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnMovieInventoryActionPerformed

    private void btnUserProfilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserProfilesActionPerformed
        // TODO add your handling code here:
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

    private void txtFullNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFullNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFullNameActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtContactNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContactNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtContactNumActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // Get all infos from signup TextFields.
        String fullName = txtFullName.getText().trim();
        String sex = radMale.isSelected() ? "Male" : radFemale.isSelected() ? "Female" : null;
        String email = txtEmail.getText().trim();
        String contactNum = txtContactNum.getText().trim();
        String address = txtaAddress.getText().trim();
        String username = txtUsername.getText().trim();
        String password = String.valueOf(txtPassword.getPassword());

        // Validates all values of TextFields; if any of those are empty display the problem.
        if (fullName.isEmpty() || sex == null || email.isEmpty() || contactNum.isEmpty() ||
            address.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Message.show("Please fill in all fields.");
            return;
        }

        // Validates the connection before continuing; Error already handled inside DBConnection.
        if (conn == null) {
            Message.show("Database connection failed.");
            return;
        }

        // Check if username already exists.
        if (accountDAO.isUsernameExist(username)) {
            Message.show("Username already exists. Please choose another.");
            return;
        }

        // Check if email already exists.
        if (infoDAO.isEmailExist(email)) {
            Message.show("Email already exists. Please use a different one.");
            return;
        }
        
        // Inserts an account and get generated accountID; will be used for tblPersonalInfo.
        int accountID = accountDAO.insertAccount(username, password);
        if (accountID == -1) { // Failed attempt.
            Message.show("Account creation failed.");
            return; // Stops here.
        }

        // Create PersonalInfo object and link accountID; only runs if the account has been inserted successfully.
        PersonalInfo info = new PersonalInfo();
        info.setAccountID(accountID); // FK reference
        info.setFullName(fullName);
        info.setSex(sex);
        info.setEmail(email);
        info.setContactNum(contactNum);
        info.setAddress(address);

        // Inserts personal infos to DB.
        boolean success = infoDAO.insertInfo(info);
        if (success) { // If the insertion succeed, return to login frame.
            Message.show("Account added successfully!");
            populateInfoTable(); // Refreshes the JTable after insertion.
            clearForm(); // Optional: clears the form fields.
        } else { // Else, insertion failed.
            Message.show("Failed to save personal information.");
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (selectedInfoID == -1) {
        Message.error("Please select a record to update."); // If no row is selected, throw this error.
        return;
        }

        try { // Gets all the infos.
            String fullName = txtFullName.getText().trim();
            String sex = radMale.isSelected() ? "Male" : radFemale.isSelected() ? "Female" : null;
            String email = txtEmail.getText().trim();
            String contactNum = txtContactNum.getText().trim();
            String address = txtaAddress.getText().trim();
            String username = txtUsername.getText().trim();
            String password = String.valueOf(txtPassword.getPassword());

            // Validates all values of TextFields; if any are empty, display the problem.
            if (fullName.isEmpty() || sex == null || email.isEmpty() || contactNum.isEmpty() ||
                address.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Message.error("Please fill in all fields.");
                return;
            }

            // Get accountID using selectedInfoID
            int accountID = infoDAO.getAccountIDByInfoID(selectedInfoID);
            if (accountID == -1) {
                Message.error("Failed to retrieve account ID.");
                return;
            }

            // Create updated PersonalInfo object
            PersonalInfo updatedInfo = new PersonalInfo();
            updatedInfo.setInfoID(selectedInfoID);
            updatedInfo.setAccountID(accountID);
            updatedInfo.setFullName(fullName);
            updatedInfo.setSex(sex);
            updatedInfo.setEmail(email);
            updatedInfo.setContactNum(contactNum);
            updatedInfo.setAddress(address);

            // Update both tables
            boolean infoUpdated = infoDAO.updateInfo(updatedInfo);
            boolean accountUpdated = accountDAO.updateAccount(accountID, username, password);

            if (infoUpdated && accountUpdated) {
                Message.show("Account updated successfully!");

                clearForm();           // Reset form
                populateInfoTable();   // Refresh table
            } else {
                Message.error("Update failed. Please try again.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Message.error("Error updating account: " + e.getMessage());
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (selectedInfoID == -1) {
            Message.error("Please select a record to delete."); // If there's no data/row selected, throw this error.
            return;
        }

        if (!Message.confirm("Are you sure you want to delete this account?", "Confirm Deletion")) {
            return; // If user cancels, do nothing.
        } // False error.

        try {
            int accountID = infoDAO.getAccountIDByInfoID(selectedInfoID); // Get the linked accountID using infoID.

            // Pass the connection for both account for delete operation/method.
            accountDAO = new AccountDAO(conn); 
            infoDAO = new PersonalInfoDAO(conn);

            infoDAO.deleteInfo(selectedInfoID);   // Delete personal info first (FK constraint)
            accountDAO.deleteAccount(accountID);  // Then delete account

            Message.show("Account deleted successfully!");
            clearForm(); // Reset form.
            populateInfoTable(); // Refresh/Repopulate info table.

        } catch (Exception e) {
            e.printStackTrace();
            Message.error("Error deleting account: " + e.getMessage());
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearForm(); // Clear all inputs back to default; Deselects clicked ID just in case.
        tblInfoRecord.clearSelection(); // Clear movie table selection visually.
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchActionPerformed

    private void cmbSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSortActionPerformed

    private void tglSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tglSortActionPerformed

    }//GEN-LAST:event_tglSortActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void chkShowPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkShowPasswordActionPerformed
        if (chkShowPassword.isSelected()) {
        txtPassword.setEchoChar((char) 0); // Show characters.
    } else {
        txtPassword.setEchoChar('•'); // Mask again (you can use '*', '•', etc.)    
    }
    }//GEN-LAST:event_chkShowPasswordActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void radMaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radMaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radMaleActionPerformed

    private void tblInfoRecordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblInfoRecordMouseClicked
        tblInfoRecord.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblInfoRecord.getSelectedRow();
                if (row >= 0) {
                    selectedInfoID = Integer.parseInt(tblInfoRecord.getValueAt(row, 0).toString()); // Stores the selected infoID.

                    txtUsername.setText(tblInfoRecord.getValueAt(row, 1).toString());     // Username.
                    txtFullName.setText(tblInfoRecord.getValueAt(row, 2).toString());     // Full Name.
                    String sex = tblInfoRecord.getValueAt(row, 3).toString();             // Sex.
                    txtEmail.setText(tblInfoRecord.getValueAt(row, 4).toString());        // Email.
                    txtContactNum.setText(tblInfoRecord.getValueAt(row, 5).toString());   // Contact Number.
                    txtaAddress.setText(tblInfoRecord.getValueAt(row, 6).toString());     // Address.

                    int accountID = infoDAO.getAccountIDByInfoID(selectedInfoID); // Gets the account ID via selected info ID.
                    String password = accountDAO.getPasswordByAccountID(accountID); // Gets the password via account ID.
                    if (password != null) { 
                        txtPassword.setText(password); // Display password.
}

                    // Set radio button based on sex.
                    if (sex.equalsIgnoreCase("Male")) {
                        radMale.setSelected(true);
                        radFemale.setSelected(false);
                    } else if (sex.equalsIgnoreCase("Female")) {
                        radFemale.setSelected(true);
                        radMale.setSelected(false);
                    } else {
                        radMale.setSelected(false);
                        radFemale.setSelected(false);
                    }
                }
            }
        });
    }//GEN-LAST:event_tblInfoRecordMouseClicked

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
            java.util.logging.Logger.getLogger(AdminUserProfiles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminUserProfiles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminUserProfiles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminUserProfiles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminUserProfiles().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.ButtonGroup btnGrpSex;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMovieInventory;
    private javax.swing.JButton btnPaymentReview;
    private javax.swing.JButton btnRentalLogs;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnUserProfiles;
    private javax.swing.JCheckBox chkShowPassword;
    private javax.swing.JComboBox<String> cmbSort;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblContact;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFullName;
    private javax.swing.JLabel lblHeader1;
    private javax.swing.JLabel lblHeader2;
    private javax.swing.JLabel lblHeader3;
    private javax.swing.JLabel lblHeader4;
    private javax.swing.JLabel lblManageUserProfiles;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblSex;
    private javax.swing.JLabel lblUserProfiles;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlSideNav;
    private javax.swing.JRadioButton radFemale;
    private javax.swing.JRadioButton radMale;
    private javax.swing.JScrollPane scrlAddress;
    private javax.swing.JScrollPane scrpAccountRecord;
    private javax.swing.JTable tblInfoRecord;
    private javax.swing.JToggleButton tglSort;
    private javax.swing.JTextField txtContactNum;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFullName;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtUsername;
    private javax.swing.JTextArea txtaAddress;
    // End of variables declaration//GEN-END:variables
}
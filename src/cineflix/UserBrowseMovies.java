package cineflix;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import java.sql.Timestamp;

public class UserBrowseMovies extends javax.swing.JFrame {
    // Declaring DB-related variables.
    Connection conn;
    MovieDAO movieDAO;
    RentalDAO rentalDAO;
    PaymentDAO paymentDAO;
    
    private DefaultTableModel tblCartRecord; // For cart model.
    
    String defaultImage = "src/images/default_cover.png"; // Set movie cover image to default; using local directory.
    private int selectedMovieID = -1; // Stored the ID of the selected movie; used for renting purposes.
    
    // Cart selection tracking.
    private int selectedCartRow = -1;
    private String selectedCartMovieID;
    private String selectedCartTitle;
    private int selectedCartWeeks;
    private double selectedCartPricePerWeek;
    private double selectedCartTotalPrice;

    public UserBrowseMovies() {
        initComponents();
        this.setSize(1315, 675);
        this.setLocationRelativeTo(null); // Center the JFrame
        lblHeader4.setText("Welcome, " + ActiveSession.loggedInUsername); // Welcome message.
        
        setDefaultCoverImage(); // Sets default cover image for a movie.
        setDefaultSynopsisTxta(); // Wraps synopsis TextArea; continue in new line if characters exceed.
        clearCartTable(); // Clears cart default record.
        
        // Attemps to get a DB Connection.
        conn = DBConnection.getConnection();
        if (conn == null) return; // Validates the connection before continuing; Error already handled inside DBConnection.
        movieDAO = new MovieDAO(conn); // To usee Movie CRUD methods.
        rentalDAO = new RentalDAO(conn); // To use Rental CRUD methods.
        paymentDAO = new PaymentDAO(conn); // To use Payment CRUD methods.
        
        populateMovieTable();
    }
    
    // Sets the default cover image of a movie.
    private void setDefaultCoverImage() {
        ImageIcon defaultIcon = new ImageIcon(defaultImage); // Use the default movie cover image.
        Image scaled = defaultIcon.getImage().getScaledInstance(
        lblImagePath.getWidth(), lblImagePath.getHeight(), Image.SCALE_SMOOTH);
        lblImagePath.setIcon(new ImageIcon(scaled));
    }
    
    // Wraps the synopsis; proceed to new line when it exceed the width space.
    private void setDefaultSynopsisTxta(){
        txtaSynopsis.setLineWrap(true); 
        txtaSynopsis.setWrapStyleWord(true);
    }
    
    // Clears tblCart to insert new movie orders.
    public void clearCartTable() {
        tblCartRecord = (DefaultTableModel) tblCart.getModel();
        tblCartRecord.setRowCount(0); // This clears all rows
    }

    // Sets all infos back to default.
    private void clearMovieSelection() {
        selectedMovieID = -1;
        tblMovieRecord.clearSelection(); // Clear table selection visually.
        lblTitle.setText("N/A");
        lblGenre.setText("N/A");
        lblYear.setText("N/A");
        lblDuration.setText("N/A");
        lblPricePerWeek.setText("₱0.00");
        lblCopies.setText("N/A");
        txtaSynopsis.setText("");
        setDefaultCoverImage();
        cmbPricePerWeek.setSelectedIndex(0);
        txtWeeklyPrice.setText("₱0.00");
    }

    // Updates the total price of all selected movies; below tblCard.
    public void updateCartTotalPrice() {
        tblCartRecord = (DefaultTableModel) tblCart.getModel();
        double total = 0.0;

        for (int i = 0; i < tblCartRecord.getRowCount(); i++) {
            String totalText = tblCartRecord.getValueAt(i, 4).toString().replace("₱", "").trim();
            try {
                total += Double.parseDouble(totalText);
            } catch (NumberFormatException e) {
                // Skip invalid entries.
            }
        }
        txtTotalOrderPrice.setText("₱" + String.format("%.2f", total));
    }

    private void populateMovieTable() {
        DefaultTableModel model = (DefaultTableModel) tblMovieRecord.getModel();
        model.setRowCount(0); // Clear existing rows.

        try {
            List<Movie> movies = movieDAO.getAllMoviesForUsers(); // Get all the basic movie info for user table.
            for (Movie m : movies) {
                Object[] row = {
                    m.getMovieID(),
                    m.getTitle(),
                    m.getGenre(),
                    m.getReleaseYear(),
                    m.getDuration() + " mins",
                    "₱" + m.getPricePerWeek(),
                    m.getCopies()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Message.error("Error loading movie table: " + e.getMessage());
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
        lblHeader4 = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        lblBrowseMovies = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        cmbSort = new javax.swing.JComboBox<>();
        tglSort = new javax.swing.JToggleButton();
        txtSearch = new javax.swing.JTextField();
        scrlMovie = new javax.swing.JScrollPane();
        tblMovieRecord = new javax.swing.JTable();
        pnlMoviePreview = new javax.swing.JPanel();
        lblImagePath = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        lblGenre = new javax.swing.JLabel();
        lblYear = new javax.swing.JLabel();
        scrlSynopsis = new javax.swing.JScrollPane();
        txtaSynopsis = new javax.swing.JTextArea();
        lblDuration = new javax.swing.JLabel();
        lblPricePerWeek = new javax.swing.JLabel();
        pnlRent = new javax.swing.JPanel();
        lblRentalDetails = new javax.swing.JLabel();
        btnAddToCart = new javax.swing.JButton();
        cmbPricePerWeek = new javax.swing.JComboBox<>();
        lblTotalPrice = new javax.swing.JLabel();
        txtWeeklyPrice = new javax.swing.JTextField();
        lblRentalDuration = new javax.swing.JLabel();
        lblCopies = new javax.swing.JLabel();
        lblYear1 = new javax.swing.JLabel();
        lblDuration1 = new javax.swing.JLabel();
        lblGenre1 = new javax.swing.JLabel();
        lblCopies1 = new javax.swing.JLabel();
        lblMovieDetails = new javax.swing.JLabel();
        lblMovieCart = new javax.swing.JLabel();
        scrlCart = new javax.swing.JScrollPane();
        tblCart = new javax.swing.JTable();
        btnRemoveItem = new javax.swing.JButton();
        btnConfirmOrder = new javax.swing.JButton();
        lblTotalOrderPrice = new javax.swing.JLabel();
        txtTotalOrderPrice = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CineFlix: Browse Movies");
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

        btnBrowseMovies.setBackground(new java.awt.Color(255, 255, 255));
        btnBrowseMovies.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnBrowseMovies.setForeground(new java.awt.Color(0, 0, 0));
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

        lblBrowseMovies.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblBrowseMovies.setForeground(new java.awt.Color(0, 0, 0));
        lblBrowseMovies.setText("Browse Movies");
        lblBrowseMovies.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

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

        tblMovieRecord.setBackground(new java.awt.Color(0, 0, 0));
        tblMovieRecord.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblMovieRecord.setForeground(new java.awt.Color(255, 255, 255));
        tblMovieRecord.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Movie ID", "Title", "Genre", "Year", "Duration", "Price/Week", "Copies"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMovieRecord.setSelectionBackground(new java.awt.Color(74, 144, 226));
        tblMovieRecord.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tblMovieRecord.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMovieRecordMouseClicked(evt);
            }
        });
        scrlMovie.setViewportView(tblMovieRecord);

        pnlMoviePreview.setBackground(new java.awt.Color(0, 0, 0));
        pnlMoviePreview.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(74, 144, 226), 4));

        lblImagePath.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblTitle.setBackground(new java.awt.Color(0, 0, 0));
        lblTitle.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("N/A");
        lblTitle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblGenre.setBackground(new java.awt.Color(0, 0, 0));
        lblGenre.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblGenre.setForeground(new java.awt.Color(255, 255, 255));
        lblGenre.setText("N/A");
        lblGenre.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblYear.setBackground(new java.awt.Color(0, 0, 0));
        lblYear.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblYear.setForeground(new java.awt.Color(255, 255, 255));
        lblYear.setText("N/A");
        lblYear.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtaSynopsis.setBackground(new java.awt.Color(51, 51, 51));
        txtaSynopsis.setColumns(20);
        txtaSynopsis.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtaSynopsis.setForeground(new java.awt.Color(255, 255, 255));
        txtaSynopsis.setRows(5);
        txtaSynopsis.setEnabled(false);
        scrlSynopsis.setViewportView(txtaSynopsis);

        lblDuration.setBackground(new java.awt.Color(0, 0, 0));
        lblDuration.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblDuration.setForeground(new java.awt.Color(255, 255, 255));
        lblDuration.setText("N/A");
        lblDuration.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblPricePerWeek.setBackground(new java.awt.Color(0, 0, 0));
        lblPricePerWeek.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblPricePerWeek.setForeground(new java.awt.Color(0, 255, 51));
        lblPricePerWeek.setText("₱0.00");
        lblPricePerWeek.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        pnlRent.setBackground(new java.awt.Color(0, 0, 0));
        pnlRent.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(74, 144, 226), 2));

        lblRentalDetails.setBackground(new java.awt.Color(0, 0, 0));
        lblRentalDetails.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblRentalDetails.setForeground(new java.awt.Color(255, 255, 255));
        lblRentalDetails.setText("Rental Details:");
        lblRentalDetails.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btnAddToCart.setBackground(new java.awt.Color(0, 204, 153));
        btnAddToCart.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAddToCart.setForeground(new java.awt.Color(255, 255, 255));
        btnAddToCart.setText("Add to Cart");
        btnAddToCart.setFocusable(false);
        btnAddToCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddToCartActionPerformed(evt);
            }
        });

        cmbPricePerWeek.setBackground(new java.awt.Color(255, 255, 255));
        cmbPricePerWeek.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbPricePerWeek.setForeground(new java.awt.Color(0, 0, 0));
        cmbPricePerWeek.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1 Week", "2 Weeks", "3 Weeks", "4 Weeks", "5 Weeks", "6 Weeks" }));
        cmbPricePerWeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPricePerWeekActionPerformed(evt);
            }
        });

        lblTotalPrice.setBackground(new java.awt.Color(0, 0, 0));
        lblTotalPrice.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblTotalPrice.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalPrice.setText("Total Price:");
        lblTotalPrice.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtWeeklyPrice.setBackground(new java.awt.Color(204, 204, 204));
        txtWeeklyPrice.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtWeeklyPrice.setForeground(new java.awt.Color(0, 0, 0));
        txtWeeklyPrice.setText("₱0.00");
        txtWeeklyPrice.setFocusable(false);

        lblRentalDuration.setBackground(new java.awt.Color(0, 0, 0));
        lblRentalDuration.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblRentalDuration.setForeground(new java.awt.Color(255, 255, 255));
        lblRentalDuration.setText("Rental Duration:");
        lblRentalDuration.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout pnlRentLayout = new javax.swing.GroupLayout(pnlRent);
        pnlRent.setLayout(pnlRentLayout);
        pnlRentLayout.setHorizontalGroup(
            pnlRentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblRentalDetails, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlRentLayout.createSequentialGroup()
                        .addGroup(pnlRentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTotalPrice)
                            .addComponent(lblRentalDuration))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlRentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbPricePerWeek, 0, 116, Short.MAX_VALUE)
                            .addComponent(txtWeeklyPrice)))
                    .addGroup(pnlRentLayout.createSequentialGroup()
                        .addGap(0, 11, Short.MAX_VALUE)
                        .addComponent(btnAddToCart, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );
        pnlRentLayout.setVerticalGroup(
            pnlRentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRentalDetails)
                .addGap(18, 18, 18)
                .addGroup(pnlRentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbPricePerWeek, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRentalDuration))
                .addGap(15, 15, 15)
                .addGroup(pnlRentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalPrice)
                    .addComponent(txtWeeklyPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAddToCart, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        lblCopies.setBackground(new java.awt.Color(0, 0, 0));
        lblCopies.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblCopies.setForeground(new java.awt.Color(204, 204, 204));
        lblCopies.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCopies.setText("N/A");
        lblCopies.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblYear1.setBackground(new java.awt.Color(0, 0, 0));
        lblYear1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblYear1.setForeground(new java.awt.Color(255, 255, 255));
        lblYear1.setText("Year:");
        lblYear1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblDuration1.setBackground(new java.awt.Color(0, 0, 0));
        lblDuration1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblDuration1.setForeground(new java.awt.Color(255, 255, 255));
        lblDuration1.setText("Duration:");
        lblDuration1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblGenre1.setBackground(new java.awt.Color(0, 0, 0));
        lblGenre1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblGenre1.setForeground(new java.awt.Color(255, 255, 255));
        lblGenre1.setText("Genre: ");
        lblGenre1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblCopies1.setBackground(new java.awt.Color(0, 0, 0));
        lblCopies1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblCopies1.setForeground(new java.awt.Color(204, 204, 204));
        lblCopies1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCopies1.setText("Copies:");
        lblCopies1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout pnlMoviePreviewLayout = new javax.swing.GroupLayout(pnlMoviePreview);
        pnlMoviePreview.setLayout(pnlMoviePreviewLayout);
        pnlMoviePreviewLayout.setHorizontalGroup(
            pnlMoviePreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMoviePreviewLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblImagePath, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(pnlMoviePreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMoviePreviewLayout.createSequentialGroup()
                        .addComponent(lblTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblPricePerWeek))
                    .addGroup(pnlMoviePreviewLayout.createSequentialGroup()
                        .addGroup(pnlMoviePreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrlSynopsis, javax.swing.GroupLayout.PREFERRED_SIZE, 649, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlMoviePreviewLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(pnlMoviePreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlMoviePreviewLayout.createSequentialGroup()
                                        .addComponent(lblGenre1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblGenre))
                                    .addGroup(pnlMoviePreviewLayout.createSequentialGroup()
                                        .addComponent(lblYear1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblYear)
                                        .addGap(30, 30, 30)
                                        .addComponent(lblDuration1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblDuration)
                                        .addGap(24, 24, 24)
                                        .addComponent(lblCopies1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblCopies)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(pnlRent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        pnlMoviePreviewLayout.setVerticalGroup(
            pnlMoviePreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMoviePreviewLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pnlMoviePreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblImagePath, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMoviePreviewLayout.createSequentialGroup()
                        .addGroup(pnlMoviePreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pnlRent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlMoviePreviewLayout.createSequentialGroup()
                                .addGroup(pnlMoviePreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTitle)
                                    .addComponent(lblPricePerWeek))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlMoviePreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDuration1)
                                    .addGroup(pnlMoviePreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblYear)
                                        .addComponent(lblYear1)
                                        .addComponent(lblCopies1)
                                        .addComponent(lblCopies)
                                        .addComponent(lblDuration)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlMoviePreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblGenre)
                                    .addComponent(lblGenre1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                                .addComponent(scrlSynopsis, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(11, 11, 11)))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        lblMovieDetails.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblMovieDetails.setForeground(new java.awt.Color(0, 0, 0));
        lblMovieDetails.setText("Movie Details");
        lblMovieDetails.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblMovieCart.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblMovieCart.setForeground(new java.awt.Color(0, 0, 0));
        lblMovieCart.setText("Movie Cart:");
        lblMovieCart.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        tblCart.setBackground(new java.awt.Color(0, 0, 0));
        tblCart.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblCart.setForeground(new java.awt.Color(255, 255, 255));
        tblCart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Movie ID", "Title", "Weeks", "Price/Week", "Total Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCart.setSelectionBackground(new java.awt.Color(74, 144, 226));
        tblCart.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tblCart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCartMouseClicked(evt);
            }
        });
        scrlCart.setViewportView(tblCart);

        btnRemoveItem.setBackground(new java.awt.Color(255, 51, 0));
        btnRemoveItem.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnRemoveItem.setForeground(new java.awt.Color(255, 255, 255));
        btnRemoveItem.setText("Remove Item");
        btnRemoveItem.setFocusable(false);
        btnRemoveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveItemActionPerformed(evt);
            }
        });

        btnConfirmOrder.setBackground(new java.awt.Color(0, 153, 204));
        btnConfirmOrder.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnConfirmOrder.setForeground(new java.awt.Color(255, 255, 255));
        btnConfirmOrder.setText("Confirm Order");
        btnConfirmOrder.setFocusable(false);
        btnConfirmOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmOrderActionPerformed(evt);
            }
        });

        lblTotalOrderPrice.setBackground(new java.awt.Color(0, 0, 0));
        lblTotalOrderPrice.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblTotalOrderPrice.setForeground(new java.awt.Color(0, 0, 0));
        lblTotalOrderPrice.setText("Total Order Price:");
        lblTotalOrderPrice.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtTotalOrderPrice.setBackground(new java.awt.Color(204, 204, 204));
        txtTotalOrderPrice.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtTotalOrderPrice.setForeground(new java.awt.Color(0, 0, 0));
        txtTotalOrderPrice.setText("₱0.00");
        txtTotalOrderPrice.setFocusable(false);
        txtTotalOrderPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalOrderPriceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(pnlSideNav, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pnlMoviePreview, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlMainLayout.createSequentialGroup()
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tglSort, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(scrlMovie, javax.swing.GroupLayout.PREFERRED_SIZE, 724, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMovieDetails)
                            .addComponent(lblBrowseMovies))
                        .addGap(18, 18, 18)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrlCart, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addComponent(btnRemoveItem, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnConfirmOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addComponent(lblMovieCart)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addComponent(lblTotalOrderPrice)
                                .addGap(12, 12, 12)
                                .addComponent(txtTotalOrderPrice)))))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSideNav, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBrowseMovies)
                    .addComponent(lblMovieCart))
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearch)
                            .addComponent(cmbSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tglSort))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scrlMovie, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrlCart, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTotalOrderPrice)
                            .addComponent(txtTotalOrderPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblMovieDetails)
                    .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnRemoveItem, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnConfirmOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlMoviePreview, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblMovieRecordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMovieRecordMouseClicked
        tblMovieRecord.addMouseListener(new MouseAdapter() {
          @Override
    public void mouseClicked(MouseEvent e) {
        int row = tblMovieRecord.getSelectedRow();
        if (row >= 0) {
            selectedMovieID = Integer.parseInt(tblMovieRecord.getValueAt(row, 0).toString()); // movieID

            lblTitle.setText(tblMovieRecord.getValueAt(row, 1).toString());       // Title
            lblGenre.setText(tblMovieRecord.getValueAt(row, 2).toString());       // Genre
            lblYear.setText(tblMovieRecord.getValueAt(row, 3).toString());        // Year
            lblDuration.setText(tblMovieRecord.getValueAt(row, 4).toString());    // Duration
            lblPricePerWeek.setText(tblMovieRecord.getValueAt(row, 5).toString());// Price
            lblCopies.setText(tblMovieRecord.getValueAt(row, 6).toString());      // Copies
            
            // Retrieve synopsis from DB and display.
            String synopsis = movieDAO.getSynopsisByMovieID(selectedMovieID);
            txtaSynopsis.setText(synopsis != null ? synopsis : "No synopsis available.");
            
            // Retrieve imagepath directory from DB and display.
            String imagePath = movieDAO.getImagePathByMovieID(selectedMovieID);
            if (imagePath != null && !imagePath.isEmpty()) {
                ImageIcon icon = new ImageIcon(imagePath);
                Image scaled = icon.getImage().getScaledInstance(
                lblImagePath.getWidth(), lblImagePath.getHeight(), Image.SCALE_SMOOTH);
                lblImagePath.setIcon(new ImageIcon(scaled));
            } else {
                lblImagePath.setIcon(null); // Clear if no image
            }
             // Updates txtWeeklyPrice based on selected record.
            String priceText = tblMovieRecord.getValueAt(row, 5).toString().replace("₱", "");
            double pricePerWeek = Double.parseDouble(priceText);
            int selectedWeeks = cmbPricePerWeek.getSelectedIndex() + 1;
            double totalPrice = pricePerWeek * selectedWeeks;
            txtWeeklyPrice.setText("₱" + String.format("%.2f", totalPrice));
            }
    }
});
    }//GEN-LAST:event_tblMovieRecordMouseClicked

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void tglSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tglSortActionPerformed

    }//GEN-LAST:event_tglSortActionPerformed

    private void cmbSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSortActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        ActiveSession.clearSession(); // Clears active session.
        new Login().setVisible(true); // Returns to login frame.
        this.dispose();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnMyPaymentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMyPaymentsActionPerformed
        new UserMyPayments().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnMyPaymentsActionPerformed

    private void btnRentalHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRentalHistoryActionPerformed
        new UserRentalHistory().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRentalHistoryActionPerformed

    private void btnBrowseMoviesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseMoviesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBrowseMoviesActionPerformed

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        new UserDashboard().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnHomeActionPerformed

    private void btnAddToCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddToCartActionPerformed
        int row = tblMovieRecord.getSelectedRow();
        if (row < 0) {
            Message.error("Please select a movie first.");
            return;
        }
        
        // Evaluates if the user really selected a movie after adding a new one to cart.
        if (lblTitle.getText().equals("N/A")) {
            Message.error("Please select a movie first.");
            return;
        }

        // Get values from selected row
        String movieID = tblMovieRecord.getValueAt(row, 0).toString();
        String title = tblMovieRecord.getValueAt(row, 1).toString();
        String priceText = tblMovieRecord.getValueAt(row, 5).toString().replace("₱", "");
        double pricePerWeek = Double.parseDouble(priceText);

        // Get selected weeks
        int selectedWeeks = cmbPricePerWeek.getSelectedIndex() + 1;
        double totalPrice = pricePerWeek * selectedWeeks;

        // Access table cart model.
        tblCartRecord = (DefaultTableModel) tblCart.getModel();

        // Check for duplicates
        for (int i = 0; i < tblCartRecord.getRowCount(); i++) {
            String existingID = tblCartRecord.getValueAt(i, 0).toString().trim();
            if (existingID.equals(movieID)) { // User must delete the duplicate item first inside tblCart.
                Message.show("This movie is already in your cart."
                + "\nPlease delete it first if you want to change the rental duration.", "Duplicate Entry");
                return; 
            }
        }

        // Add new row
        tblCartRecord.addRow(new Object[] {
            movieID,
            title,
            selectedWeeks,
            "₱" + String.format("%.2f", pricePerWeek),
            "₱" + String.format("%.2f", totalPrice)
        });
        
        selectedCartRow = -1; // Resets the cart row.
        updateCartTotalPrice(); // Updates cart total price from all selected movies.
        clearMovieSelection(); // Clears the movie preview.
        Message.show("Movie added to cart successfully!", "Info");
    }//GEN-LAST:event_btnAddToCartActionPerformed

    private void cmbPricePerWeekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPricePerWeekActionPerformed
        int row = tblMovieRecord.getSelectedRow();
        if (row >= 0) {
            // Get price from column 5 (₱100.00 style)
            String priceText = tblMovieRecord.getValueAt(row, 5).toString().replace("₱", "");
            double pricePerWeek = Double.parseDouble(priceText);

            int selectedWeeks = cmbPricePerWeek.getSelectedIndex() + 1;
            double totalPrice = pricePerWeek * selectedWeeks;

            txtWeeklyPrice.setText("₱" + String.format("%.2f", totalPrice));
        } else {
            txtWeeklyPrice.setText("₱0.00"); // fallback if no row selected
        }
    }//GEN-LAST:event_cmbPricePerWeekActionPerformed

    private void tblCartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCartMouseClicked
        selectedCartRow = tblCart.getSelectedRow();
        if (selectedCartRow >= 0) {
            selectedCartMovieID = tblCart.getValueAt(selectedCartRow, 0).toString();
            selectedCartTitle = tblCart.getValueAt(selectedCartRow, 1).toString();
            selectedCartWeeks = Integer.parseInt(tblCart.getValueAt(selectedCartRow, 2).toString().split(" ")[0]);
            selectedCartPricePerWeek = Double.parseDouble(tblCart.getValueAt(selectedCartRow, 3).toString().replace("₱", ""));
            selectedCartTotalPrice = Double.parseDouble(tblCart.getValueAt(selectedCartRow, 4).toString().replace("₱", ""));
        }
    }//GEN-LAST:event_tblCartMouseClicked

    private void btnConfirmOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmOrderActionPerformed
        int accountID = ActiveSession.loggedInAccountID; // Use the current active user's accountID.

        // Validates empty cart.
        if (tblCartRecord.getRowCount() == 0) {
            Message.error("Your cart is empty.");
            return;
        }

        // Loop thru our cart table and populate tblRentals and tblPayments until our cart runs out of data.
        for (int i = 0; i < tblCartRecord.getRowCount(); i++) {
            int movieID = Integer.parseInt(tblCartRecord.getValueAt(i, 0).toString()); // column 0 = movieID.
            int weeks = Integer.parseInt(tblCartRecord.getValueAt(i, 2).toString()); // column 2 = rental duration in weeks.
            String rawCost = tblCartRecord.getValueAt(i, 4).toString().replace("₱", "").replace("?", "").trim();
            double rentalCost = Double.parseDouble(rawCost); // column 4 = total price.
            
            Timestamp rentalDate = new Timestamp(System.currentTimeMillis()); // Get current timestamp.
            Timestamp returnDate = Timestamp.valueOf(
                rentalDate.toLocalDateTime().plusDays(weeks * 7) // returnDate = rentalDate + (weeks + 7 days).
            );

            // Inserts new rental record that references both the current user and their chosen movie from tblCartRecord.
            Rental rental = new Rental(accountID, movieID, rentalDate, returnDate, rentalCost);
            int rentalID = rentalDAO.insertRental(rental);

            if (rentalID == -1) {
                Message.error("Rental failed for movie ID: " + movieID);
                continue;
            }

            // Insert default payment record (visible in My Payments).
            paymentDAO.insertPayment(rentalID);

            // Decrement available copies.
            boolean updated = movieDAO.decrementMovieCopies(movieID);
            if (!updated) {
                Message.error("Warning: Could not update copies for movie ID: " + movieID);
            }
        }

        // Clear cart and reset UI
        populateMovieTable(); // Repopulate tblMovieRecord with updated values.
        tblCartRecord.setRowCount(0);
        selectedCartRow = -1; // Clears cart row tracking.
        clearMovieSelection(); // Clears movie preview.
        updateCartTotalPrice(); // Recalculates total price.

        Message.show("Order confirmed! Rentals are now active and payments are pending.");
    }//GEN-LAST:event_btnConfirmOrderActionPerformed

    private void btnRemoveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveItemActionPerformed
        int row = tblCart.getSelectedRow();
        if (row >= 0) {
            String title = tblCart.getValueAt(row, 1).toString();
            boolean confirm = Message.confirm("Remove \"" + title + "\" from cart?", "Confirm Deletion");
            if (confirm) {
                tblCartRecord = (DefaultTableModel) tblCart.getModel();
                tblCartRecord.removeRow(row);
                selectedCartRow = -1; // Reset selected cart row.
                updateCartTotalPrice(); // Updates total order from cart.
                Message.show("Item removed from cart.", "Info");
            }
        } else {
            Message.show("Please select an item to delete.", "Warning");
        }
    }//GEN-LAST:event_btnRemoveItemActionPerformed

    private void txtTotalOrderPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalOrderPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalOrderPriceActionPerformed

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
            java.util.logging.Logger.getLogger(UserBrowseMovies.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserBrowseMovies.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserBrowseMovies.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserBrowseMovies.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserBrowseMovies().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddToCart;
    private javax.swing.JButton btnBrowseMovies;
    private javax.swing.JButton btnConfirmOrder;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMyPayments;
    private javax.swing.JButton btnRemoveItem;
    private javax.swing.JButton btnRentalHistory;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cmbPricePerWeek;
    private javax.swing.JComboBox<String> cmbSort;
    private javax.swing.JLabel lblBrowseMovies;
    private javax.swing.JLabel lblCopies;
    private javax.swing.JLabel lblCopies1;
    private javax.swing.JLabel lblDuration;
    private javax.swing.JLabel lblDuration1;
    private javax.swing.JLabel lblGenre;
    private javax.swing.JLabel lblGenre1;
    private javax.swing.JLabel lblHeader1;
    private javax.swing.JLabel lblHeader2;
    private javax.swing.JLabel lblHeader3;
    private javax.swing.JLabel lblHeader4;
    private javax.swing.JLabel lblImagePath;
    private javax.swing.JLabel lblMovieCart;
    private javax.swing.JLabel lblMovieDetails;
    private javax.swing.JLabel lblPricePerWeek;
    private javax.swing.JLabel lblRentalDetails;
    private javax.swing.JLabel lblRentalDuration;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTotalOrderPrice;
    private javax.swing.JLabel lblTotalPrice;
    private javax.swing.JLabel lblYear;
    private javax.swing.JLabel lblYear1;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlMoviePreview;
    private javax.swing.JPanel pnlRent;
    private javax.swing.JPanel pnlSideNav;
    private javax.swing.JScrollPane scrlCart;
    private javax.swing.JScrollPane scrlMovie;
    private javax.swing.JScrollPane scrlSynopsis;
    private javax.swing.JTable tblCart;
    private javax.swing.JTable tblMovieRecord;
    private javax.swing.JToggleButton tglSort;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTotalOrderPrice;
    private javax.swing.JTextField txtWeeklyPrice;
    private javax.swing.JTextArea txtaSynopsis;
    // End of variables declaration//GEN-END:variables
}
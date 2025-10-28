package cineflix;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Connection;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class AdminMovieInventory extends javax.swing.JFrame {
    // Declaring global variables.
    private Connection conn;
    private MovieDAO movieDAO;
    
    private String selectedImagePath = "src/images/default.png"; // Default cover image of a movie.
    private int selectedMovieID = -1; // Stores the ID of selected movie; default -1.
    
    public AdminMovieInventory() {
        initComponents();
        this.setSize(1315, 675);
        this.setLocationRelativeTo(null); // Center the JFrame.
        lblHeader4.setText("Welcome, " + ActiveSession.loggedInUsername); // Welcome message.
        
        setDefaultCoverImage(); // Sets default cover for new movie.
        setDefaultTglSort();
        setDefaultSynopsisTxta();
        
        conn = DBConnection.getConnection(); // Attemps to get a DB Connection.
        if (conn == null) return; // Validates the connection before continuing.
        movieDAO = new MovieDAO(conn); // Pass the connection as an argument inside MovieDAO class for CRUD operations.
        
        populateMovieTable(); // Populates the movie table.
    }

    // Sets the default cover image of a movie.
    private void setDefaultCoverImage() {
    selectedImagePath = "src/images/default_cover.png"; // Default movie image path poster.
    
    ImageIcon defaultIcon = new ImageIcon(selectedImagePath); // Use the default movie cover image.
    Image scaled = defaultIcon.getImage().getScaledInstance(
    lblImagePath.getWidth(), lblImagePath.getHeight(), Image.SCALE_SMOOTH);
    lblImagePath.setIcon(new ImageIcon(scaled)); // Sets the cover back to default (scaled).
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
    
    // Wraps the synopsis textarea; proceed to new line when it exceed the width space.
    private void setDefaultSynopsisTxta(){
        txtaSynopsis.setLineWrap(true); 
        txtaSynopsis.setWrapStyleWord(true);
    }
    
    // Clears all inputs and set all things back to its default value.
    private void clearForm() {
        txtTitle.setText("");
        txtGenre.setText("");
        txtaSynopsis.setText("");
        txtReleaseYear.setText("");
        txtDuration.setText("");
        txtCopies.setText("");
        txtPricePerWeek.setText("");
        setDefaultCoverImage();
        lblCreatedAt.setText("N/A");
        tblMovieRecord.clearSelection();
        selectedMovieID = -1; // Reset selected ID from movie table.
    }
    
    // Populates movie table with data from our tblMovies.
    private void populateMovieTable() {
        DefaultTableModel movieModel = (DefaultTableModel) tblMovieRecord.getModel();
        movieModel.setRowCount(0); // Clear existing rows.

        try {
            List<Movie> movies = movieDAO.getAllMovies(); // Gets all the movies and store it inside a list.
            for (Movie m : movies) { // Enhance for loop; loops thru the list of movies and adds the record per row.
                Object[] row = {
                    m.getMovieID(),
                    m.getTitle(),
                    m.getSynopsis(),
                    m.getGenre(),
                    m.getReleaseYear(),
                    m.getDuration(),
                    m.getCopies(),
                    m.getPricePerWeek(),
                    m.getImagePath(),
                    m.getCreatedAt()
                };
                movieModel.addRow(row); // Adds the movie one-by-one.
            }
            // Hides movie ID.
            tblMovieRecord.getColumnModel().getColumn(0).setMinWidth(0);
            tblMovieRecord.getColumnModel().getColumn(0).setMaxWidth(0);
            tblMovieRecord.getColumnModel().getColumn(0).setWidth(0);
            
            // Hides imagepath.
            tblMovieRecord.getColumnModel().getColumn(8).setMinWidth(0);
            tblMovieRecord.getColumnModel().getColumn(8).setMaxWidth(0);
            tblMovieRecord.getColumnModel().getColumn(8).setWidth(0);

            // Hides createdAt.
            tblMovieRecord.getColumnModel().getColumn(9).setMinWidth(0);
            tblMovieRecord.getColumnModel().getColumn(9).setMaxWidth(0);
            tblMovieRecord.getColumnModel().getColumn(9).setWidth(0);

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
        btnMovieInventory = new javax.swing.JButton();
        btnUserProfiles = new javax.swing.JButton();
        btnRentalLogs = new javax.swing.JButton();
        btnPaymentReview = new javax.swing.JButton();
        lblHeader4 = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        pnlForm = new javax.swing.JPanel();
        lblManageMovieRecord = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        txtTitle = new javax.swing.JTextField();
        lblGenre = new javax.swing.JLabel();
        txtGenre = new javax.swing.JTextField();
        lblReleaseYear = new javax.swing.JLabel();
        txtReleaseYear = new javax.swing.JTextField();
        lblCopies = new javax.swing.JLabel();
        txtDuration = new javax.swing.JTextField();
        lblPricePerWeek = new javax.swing.JLabel();
        lblDuration = new javax.swing.JLabel();
        txtCopies = new javax.swing.JTextField();
        txtPricePerWeek = new javax.swing.JTextField();
        lblSynopsis = new javax.swing.JLabel();
        scrlSynopsis = new javax.swing.JScrollPane();
        txtaSynopsis = new javax.swing.JTextArea();
        lblImagePath = new javax.swing.JLabel();
        lblCoverImage = new javax.swing.JLabel();
        btnBrowseImage = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        lblCreatedAt1 = new javax.swing.JLabel();
        lblCreatedAt = new javax.swing.JLabel();
        lblMovieInventory = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        cmbSort = new javax.swing.JComboBox<>();
        tglSort = new javax.swing.JToggleButton();
        txtSearch = new javax.swing.JTextField();
        scrlMovie = new javax.swing.JScrollPane();
        tblMovieRecord = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CineFlix: Movie Inventory");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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

        btnMovieInventory.setBackground(new java.awt.Color(255, 255, 255));
        btnMovieInventory.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnMovieInventory.setForeground(new java.awt.Color(0, 0, 0));
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

        pnlForm.setBackground(new java.awt.Color(0, 0, 0));

        lblManageMovieRecord.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblManageMovieRecord.setForeground(new java.awt.Color(255, 255, 255));
        lblManageMovieRecord.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblManageMovieRecord.setText("Manage Movie Record");
        lblManageMovieRecord.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblTitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("Title:");

        txtTitle.setBackground(new java.awt.Color(255, 255, 255));
        txtTitle.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtTitle.setForeground(new java.awt.Color(0, 0, 0));
        txtTitle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTitleActionPerformed(evt);
            }
        });

        lblGenre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblGenre.setForeground(new java.awt.Color(255, 255, 255));
        lblGenre.setText("Genre:");

        txtGenre.setBackground(new java.awt.Color(255, 255, 255));
        txtGenre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtGenre.setForeground(new java.awt.Color(0, 0, 0));
        txtGenre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGenreActionPerformed(evt);
            }
        });

        lblReleaseYear.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblReleaseYear.setForeground(new java.awt.Color(255, 255, 255));
        lblReleaseYear.setText("Release Year:");

        txtReleaseYear.setBackground(new java.awt.Color(255, 255, 255));
        txtReleaseYear.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtReleaseYear.setForeground(new java.awt.Color(0, 0, 0));
        txtReleaseYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReleaseYearActionPerformed(evt);
            }
        });

        lblCopies.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCopies.setForeground(new java.awt.Color(255, 255, 255));
        lblCopies.setText("Copies:");

        txtDuration.setBackground(new java.awt.Color(255, 255, 255));
        txtDuration.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDuration.setForeground(new java.awt.Color(0, 0, 0));
        txtDuration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDurationActionPerformed(evt);
            }
        });

        lblPricePerWeek.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblPricePerWeek.setForeground(new java.awt.Color(255, 255, 255));
        lblPricePerWeek.setText("Price/Week:");

        lblDuration.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDuration.setForeground(new java.awt.Color(255, 255, 255));
        lblDuration.setText("Duration:");

        txtCopies.setBackground(new java.awt.Color(255, 255, 255));
        txtCopies.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCopies.setForeground(new java.awt.Color(0, 0, 0));
        txtCopies.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCopiesActionPerformed(evt);
            }
        });

        txtPricePerWeek.setBackground(new java.awt.Color(255, 255, 255));
        txtPricePerWeek.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPricePerWeek.setForeground(new java.awt.Color(0, 0, 0));
        txtPricePerWeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPricePerWeekActionPerformed(evt);
            }
        });

        lblSynopsis.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblSynopsis.setForeground(new java.awt.Color(255, 255, 255));
        lblSynopsis.setText("Synopsis:");

        txtaSynopsis.setBackground(new java.awt.Color(255, 255, 255));
        txtaSynopsis.setColumns(20);
        txtaSynopsis.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtaSynopsis.setForeground(new java.awt.Color(0, 0, 0));
        txtaSynopsis.setRows(5);
        scrlSynopsis.setViewportView(txtaSynopsis);

        lblImagePath.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblImagePath.setForeground(new java.awt.Color(255, 255, 255));

        lblCoverImage.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCoverImage.setForeground(new java.awt.Color(255, 255, 255));
        lblCoverImage.setText("Cover Image:");

        btnBrowseImage.setBackground(new java.awt.Color(255, 255, 255));
        btnBrowseImage.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnBrowseImage.setForeground(new java.awt.Color(0, 0, 0));
        btnBrowseImage.setText("Browse Image");
        btnBrowseImage.setFocusable(false);
        btnBrowseImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseImageActionPerformed(evt);
            }
        });

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

        lblCreatedAt1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblCreatedAt1.setForeground(new java.awt.Color(255, 255, 255));
        lblCreatedAt1.setText("Created At:");

        lblCreatedAt.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblCreatedAt.setForeground(new java.awt.Color(255, 255, 255));
        lblCreatedAt.setText("N/A");

        javax.swing.GroupLayout pnlFormLayout = new javax.swing.GroupLayout(pnlForm);
        pnlForm.setLayout(pnlFormLayout);
        pnlFormLayout.setHorizontalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlFormLayout.createSequentialGroup()
                                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlFormLayout.createSequentialGroup()
                                            .addComponent(lblSynopsis)
                                            .addGap(172, 172, 172)
                                            .addComponent(lblCoverImage))
                                        .addComponent(lblTitle)
                                        .addComponent(lblGenre)
                                        .addGroup(pnlFormLayout.createSequentialGroup()
                                            .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(pnlFormLayout.createSequentialGroup()
                                                    .addGap(118, 118, 118)
                                                    .addComponent(btnBrowseImage))
                                                .addComponent(scrlSynopsis))
                                            .addGap(18, 18, 18)
                                            .addComponent(lblImagePath, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlFormLayout.createSequentialGroup()
                                            .addComponent(lblCreatedAt1)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(lblCreatedAt))
                                        .addGroup(pnlFormLayout.createSequentialGroup()
                                            .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                                                .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                                                .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                                .addGap(0, 20, Short.MAX_VALUE))
                            .addGroup(pnlFormLayout.createSequentialGroup()
                                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblReleaseYear)
                                    .addComponent(lblDuration))
                                .addGap(18, 18, 18)
                                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtReleaseYear, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                                    .addComponent(txtDuration))
                                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlFormLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblCopies)
                                        .addGap(18, 18, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblPricePerWeek)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtCopies, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnlFormLayout.createSequentialGroup()
                                        .addComponent(txtPricePerWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(1, 1, 1))))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtGenre, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(20, 20, 20))
            .addComponent(lblManageMovieRecord, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlFormLayout.setVerticalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addComponent(lblManageMovieRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGenre, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGenre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblReleaseYear, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtReleaseYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlFormLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlFormLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(txtDuration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCopies, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCopies, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPricePerWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPricePerWeek, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSynopsis, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCoverImage, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblImagePath, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addComponent(scrlSynopsis, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlFormLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btnBrowseImage))
                            .addGroup(pnlFormLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblCreatedAt1)
                                    .addComponent(lblCreatedAt))))))
                .addGap(18, 18, 18)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblMovieInventory.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblMovieInventory.setForeground(new java.awt.Color(0, 0, 0));
        lblMovieInventory.setText("Movie Inventory");
        lblMovieInventory.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

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
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Title", "Synopsis", "Genre", "Year", "Duration", "Copies", "Price/Week", "ImagePath", "CreatedAt"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
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
                    .addComponent(lblMovieInventory)
                    .addComponent(scrlMovie))
                .addGap(18, 18, 18)
                .addComponent(pnlForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSideNav, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainLayout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(lblMovieInventory)
                .addGap(4, 4, 4)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch)
                    .addComponent(cmbSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tglSort))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrlMovie, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97))
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

    private void btnPaymentReviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaymentReviewActionPerformed
        new AdminPaymentReview().setVisible(true);
    }//GEN-LAST:event_btnPaymentReviewActionPerformed

    private void btnRentalLogsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRentalLogsActionPerformed
        new AdminRentalLogs().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRentalLogsActionPerformed

    private void btnUserProfilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserProfilesActionPerformed
        new AdminUserProfiles().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnUserProfilesActionPerformed

    private void btnMovieInventoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMovieInventoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMovieInventoryActionPerformed

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        new AdminDashboard().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnHomeActionPerformed

    private void txtTitleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTitleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTitleActionPerformed

    private void txtGenreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGenreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGenreActionPerformed

    private void txtReleaseYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtReleaseYearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtReleaseYearActionPerformed

    private void txtDurationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDurationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDurationActionPerformed

    private void txtCopiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCopiesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCopiesActionPerformed

    private void txtPricePerWeekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPricePerWeekActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPricePerWeekActionPerformed

    private void btnBrowseImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseImageActionPerformed
        JFileChooser fileChooser = new JFileChooser(); // Create an instance of JFileChooser; for cover image selection.
        fileChooser.setDialogTitle("Select Cover Image"); // Sets the dialog title.

        fileChooser.setCurrentDirectory(new File("src/images")); // Sets the default directory inside the src project.

        // Creates a filtered selection.
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
        "Image Files (*.jpg, *.jpeg, *.png)", "jpg", "jpeg", "png"); // Only accepts the ff. file types.
        fileChooser.setFileFilter(imageFilter); // Apply file filters.
        fileChooser.setAcceptAllFileFilterUsed(false); // Disable other file type options.

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            selectedImagePath = selectedFile.getAbsolutePath(); // Stores the newly selected image path.

            ImageIcon icon = new ImageIcon(selectedImagePath);
            Image scaled = icon.getImage().getScaledInstance(
                lblImagePath.getWidth(), lblImagePath.getHeight(), Image.SCALE_SMOOTH);
            lblImagePath.setIcon(new ImageIcon(scaled)); // Sets the new movie cover image.
        }
    }//GEN-LAST:event_btnBrowseImageActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // Validate empty fields before inserting.
        if (txtTitle.getText().trim().isEmpty() ||
            txtGenre.getText().trim().isEmpty() ||
            txtaSynopsis.getText().trim().isEmpty() ||
            txtReleaseYear.getText().trim().isEmpty() ||
            txtDuration.getText().trim().isEmpty() ||
            txtCopies.getText().trim().isEmpty() ||
            txtPricePerWeek.getText().trim().isEmpty()) {
            Message.error("Please fill in all required fields.");
            return;
        }
        
        try {
            String title = txtTitle.getText().trim();
            String genre = txtGenre.getText().trim();
            String synopsis = txtaSynopsis.getText().trim();
            int releaseYear = Integer.parseInt(txtReleaseYear.getText().trim());
            int duration = Integer.parseInt(txtDuration.getText().trim());
            int copies = Integer.parseInt(txtCopies.getText().trim());
            double pricePerWeek = Double.parseDouble(txtPricePerWeek.getText().trim());
            String imagePath = selectedImagePath;

            // Validates the connection before continuing.
            if (conn == null) return;
                           
            // Pass the values inside our movie model.
            Movie movie = new Movie(title, genre, synopsis, releaseYear, duration, copies, pricePerWeek, imagePath);

            MovieDAO dao = new MovieDAO(conn); // Pass the Connection to perform an insertion (insertMovie method).
            dao.insertMovie(movie); // Pass the recently created model object.

            Message.show("Movie added successfully!");
            clearForm(); // Clear all inputs.
            populateMovieTable(); // Refresh/Repopulate movie table.
        } catch (NumberFormatException e) { // Catch invalid numerical values.
            Message.show("Please enter valid numeric values for year, duration, copies, and price/week.");
        } catch (Exception e) {
            e.printStackTrace();
            Message.error("Error adding movie: " + e.getMessage());
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (selectedMovieID == -1) { // Handles no selected rows.
        Message.error("Please select a movie to update."); // Throw error message.
        return;
        }

        try { // Gets all the movie infos.
            String title = txtTitle.getText().trim();
            String genre = txtGenre.getText().trim();
            String synopsis = txtaSynopsis.getText().trim();
            int releaseYear = Integer.parseInt(txtReleaseYear.getText().trim());
            int duration = Integer.parseInt(txtDuration.getText().trim());
            int copies = Integer.parseInt(txtCopies.getText().trim());
            double pricePerWeek = Double.parseDouble(txtPricePerWeek.getText().trim());
            String imagePath = selectedImagePath;

            Movie updatedMovie = new Movie(
                selectedMovieID, title, genre, synopsis,
                releaseYear, duration, copies, pricePerWeek, imagePath
            );
            
            movieDAO.updateMovie(updatedMovie); // Updates movie details.
            Message.show("Movie updated successfully!");

            clearForm(); // Reset form
            populateMovieTable(); // Refresh/Repopulate movie table.
        } catch (NumberFormatException e) { 
            Message.error("Please enter valid numeric values.");
        } catch (Exception e) {
            e.printStackTrace();
            Message.error("Error updating movie: " + e.getMessage());
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (selectedMovieID == -1) {
        Message.error("Please select a movie to delete."); // If there's no record/row that is being selected, throw this error.
        return;
        }

        // Confirm option dialog.
        if (!Message.confirm("Are you sure you want to delete this movie?", "Confirm Deletion")) {
            return; // If user cancels, do nothing.
        }

        if (conn == null) return; // Test the connection.
        try {
            MovieDAO dao = new MovieDAO(conn); // Pass the Connection to perform deletion (deleteMovie method).
            dao.deleteMovie(selectedMovieID); // Deletes the movie using its unique ID.

            Message.show("Movie deleted successfully!");
            clearForm(); // Reset form.
            populateMovieTable(); // Refresh/Repopulate movie table.
        } catch (Exception e) {
            e.printStackTrace();
            Message.error("Error deleting movie: " + e.getMessage());
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchActionPerformed

    private void tglSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tglSortActionPerformed
        if (tglSort.isSelected()) {
        tglSort.setText("DESC");
        } else {
            tglSort.setText("ASC");
        }
    }//GEN-LAST:event_tglSortActionPerformed

    private void cmbSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSortActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void tblMovieRecordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMovieRecordMouseClicked
        tblMovieRecord.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // Handles tblMovieRecord click.
                int row = tblMovieRecord.getSelectedRow();
                if (row >= 0) {
                    selectedMovieID = Integer.parseInt(tblMovieRecord.getValueAt(row, 0).toString()); // Stores the selected ID as reference.
                    // Populate forms with the value from selected row.
                    txtTitle.setText(tblMovieRecord.getValueAt(row, 1).toString()); 
                    txtaSynopsis.setText(tblMovieRecord.getValueAt(row, 2).toString()); 
                    txtGenre.setText(tblMovieRecord.getValueAt(row, 3).toString()); 
                    txtReleaseYear.setText(tblMovieRecord.getValueAt(row, 4).toString()); 
                    txtDuration.setText(tblMovieRecord.getValueAt(row, 5).toString()); 
                    txtCopies.setText(tblMovieRecord.getValueAt(row, 6).toString()); 
                    txtPricePerWeek.setText(tblMovieRecord.getValueAt(row, 7).toString()); 
                    selectedImagePath = tblMovieRecord.getValueAt(row, 8).toString(); 
                    lblCreatedAt.setText(tblMovieRecord.getValueAt(row, 9).toString());
                    
                    // Display movie cover image in lblImagePath.
                    ImageIcon icon = new ImageIcon(selectedImagePath);
                    Image scaled = icon.getImage().getScaledInstance(
                        lblImagePath.getWidth(), lblImagePath.getHeight(), Image.SCALE_SMOOTH);
                    lblImagePath.setIcon(new ImageIcon(scaled));
                }
            }
        });
    }//GEN-LAST:event_tblMovieRecordMouseClicked

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearForm(); // Clear all inputs back to default; deselect clicked ID just in case.
        tblMovieRecord.clearSelection(); // Clear movie table selection visually.
    }//GEN-LAST:event_btnClearActionPerformed

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
            java.util.logging.Logger.getLogger(AdminMovieInventory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminMovieInventory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminMovieInventory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminMovieInventory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminMovieInventory().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBrowseImage;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMovieInventory;
    private javax.swing.JButton btnPaymentReview;
    private javax.swing.JButton btnRentalLogs;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnUserProfiles;
    private javax.swing.JComboBox<String> cmbSort;
    private javax.swing.JLabel lblCopies;
    private javax.swing.JLabel lblCoverImage;
    private javax.swing.JLabel lblCreatedAt;
    private javax.swing.JLabel lblCreatedAt1;
    private javax.swing.JLabel lblDuration;
    private javax.swing.JLabel lblGenre;
    private javax.swing.JLabel lblHeader1;
    private javax.swing.JLabel lblHeader2;
    private javax.swing.JLabel lblHeader3;
    private javax.swing.JLabel lblHeader4;
    private javax.swing.JLabel lblImagePath;
    private javax.swing.JLabel lblManageMovieRecord;
    private javax.swing.JLabel lblMovieInventory;
    private javax.swing.JLabel lblPricePerWeek;
    private javax.swing.JLabel lblReleaseYear;
    private javax.swing.JLabel lblSynopsis;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlSideNav;
    private javax.swing.JScrollPane scrlMovie;
    private javax.swing.JScrollPane scrlSynopsis;
    private javax.swing.JTable tblMovieRecord;
    private javax.swing.JToggleButton tglSort;
    private javax.swing.JTextField txtCopies;
    private javax.swing.JTextField txtDuration;
    private javax.swing.JTextField txtGenre;
    private javax.swing.JTextField txtPricePerWeek;
    private javax.swing.JTextField txtReleaseYear;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTitle;
    private javax.swing.JTextArea txtaSynopsis;
    // End of variables declaration//GEN-END:variables
}
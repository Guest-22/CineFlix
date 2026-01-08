# Final Requirement for Data Structure & Algorithm (CINEFLIX: A SIMPLE VIDEO RENTAL SYSTEM)

## Main Objective 
To make video rental simple and hassle-free for both customers (users) and establishment owners (admin). The system helps customers browse through a variety of movies and search for movies that are onto their likings and rent it, while keeping track of rental activity and maintaining organized records all in one place. The system provides the customers with a smooth browsing and renting experience, while giving administrators the necessary tools to manage inventory, monitor transactions, and oversee daily operations efficiently within a single organized software.

## CINEFLIX OPERATIONS
**I. Login Frame**
Login  –  Evaluates the username, password, and role in the database and saves the credentials in the ActiveSession class for reference.
Signup  –  Redirects to the Signup frame.
**II. Signup Frame**
Signup  –  Inserts all of the necessary details and account credentials in the database for a successful account creation.
Login  –  Redirects to the Login frame.

**III. User Dashboard**
Home 
Rental Summary  –  Displays the rental summary of the currently logged-in user based on Account ID.
Payment Summary  –  Displays the payment summary of the  currently logged-in user based on Account ID.
Top Rented Movies  –  Lists of top 4 rented movies of all time based on rental count.
Recent Rental History  –  Lists of the 10 recently rented movies of the currently logged-in user
Browse Movies
Movie Table  –  Movie ID (hidden), Title, Genre, Year, Duration, Price/Week, Copies.
Movie Table Mouse Click Listener –  Displays the selected movie in the movie preview panel for cart addition purposes.
Search  –  Search movie table by keyword (title, genre, or year).
Sort Options –  Sorts movie table by title, genre, release year.
Sort Order  –  Sorts the movie table in ascending or descending order.
Reset  –  Resets movie table and sorted options back to default.
Add to Cart  –  Adds the selected movie item into the movie cart table for summing up all rental orders.
Clear  –  Clears the movie details panel.
Movie Cart Table  –  Movie ID (hidden), Title, Weeks, Price/Week, Total Price.
Remove Item  –  Removes the selected item from the movie cart table.
Confirm Order  –  Finalizes the cart order and sends a request for the admin to approve.
Rental History
Rental Table  –  Rental ID, Movie Title, Rental Date, Return Date, Rental Cost, Amount Paid, Remaining Balance, Rental Stage, Rental Status.
Rental Table Color Renderer  –  Applies color highlights based on Rental Stage: requested (gray), approved (green), pickedup (purple), rejected (red). Rental Status: pending (gray), green (ongoing), returned (purple), late/cancelled (red).
Search  –  Search table rental by keyword (rental id, title, rental stage, or rental status).
Sort Options  –  Sorts the rental table by rental date, return date, stage, or status.
Sort Order  –  Sorts rental table in ascending or descending order.
Reset  –  Resets the rental table and sorted options back to its default value.
My Payments 
Payments Table  –  Rental ID, Movie Title, Rental Date, Return Date, Rental Status, Payment Status, Rental Cost, Overdue Amount, Paid Amount, Remaining Balance.
Payment Table Color Renderer  –  Applies color highlights based on Payment Status: pending (gray), paid upfront (green), paid full (purple). Rental Status: ongoing (blue), returned (teal), late (orange), cancelled (red).
Search  –  Search table payment by keyword (rental id, title, rental status, or payment status).
Sort Options  –  Sorts the payment table by payment date, return date, paid amount, balance, rental stage, or rental status.
Sort Order  –  Sorts payment table in ascending or descending order.
Reset  –  Resets the payment table and sorted options back to its default value.
Generate Receipt  –  Generates a digital receipt of the selected item with a unique rental ID for payment reference.
Clear Receipt  –  Clears the generated digital receipt.

**IV. Admin Dashboard**
Home
Summary Details  –  List of all record statistics based on tables (movie, user, rental, and payment).
Top Rented Movies of All Time  –  List of top 5 rented movies of all time based on rental count.
Today’s Highlight  –  List of current transaction record count within each table.
Movie Inventory
Movie Table  –  Movie ID (hidden), Title, Synopsis, Genre, Year, Duration, Copies, Price/Week, Image Path (hidden), CreatedAt (hidden).
Movie Table Mouse Click Listener  –  Displays the selected movie inside the manage movie record panel for update & deletion purposes.
Add  –  Inserts a new movie record.
Update  –  Updates the selected movie record with new values.
Delete  –  Delete selected movie record.
Clear  –  Clears the manage movie records input fields.
Search  –  Search movie table by keyword (title, genre, or year).
Sort Options  –  Sorts the movie table by date added, title, genre, or release year.
Sort Order  –  Sorts movie table in ascending or descending order.
User Profiles
Info Table  –  Info ID (hidden), Username, Account Name (user’s full name), Sex, Email, Contact, Address.
Info Mouse Click Listener  –  Displays the selected info inside the manage user profile for update & deletion purposes.
Add  –  Insert new personal info & account record.
Update   –  Updates the selected info record with new values.
Delete  – Delete selected info record.
Clear  –  Clears the manage user profiles input fields.
Search  –  Search info table by keyword (account id, fullname, username, or email).
Sort Options  –  Sort info table by date added, full name, and username.
Sort Order  –  Sorts info table in ascending or descending order.
Rental Logs
Rental Table  –  Rental ID, Account Name (full name), Movie Title, Rental Date, Return Date, Rental Stage, Rental Status, Payment Status (hidden), Total Cost.
Rental Table Mouse Click Listener  –  Displays the selected rental inside the manage rental details for update, deletion, and rental finalization purposes.
Rental Table Color Renderer  –  Applies color highlights based on Rental Stage: requested(gray), approved (green), pickedup (purple), rejected (red). Rental Status: pending (gray), ongoing (green), returned (purple), late/cancelled (red).
Update  –  Updates the selected rental record’s stage & status with new updated values.
Delete  –  Delete selected rental record alongside its payment record.
Clear  –  Clears the manage rental details input fields.
Confirm Transaction  –  Retrieves payment amount to finalize the rental transaction with the customer (whether the customer pays the upfront or full rental cost).
Search  –  Search rental table by keyword (rental id, full name, title, rental stage, or rental status).
Sort Options  –  Sort by rental date, return date, stage, and status.
Sort Order  –  Sorts rental table in ascending or descending order.
Payment Review
Payment Table  –  Payment ID (hidden), Rental ID, Account Name (user’s full name), Movie Title, Rental Date, Return Date, Rental Stage (hidden), Rental Status, Payment Status, Amount, Total Cost, Paid Amount, Overdue Amount, and Payment Date (hidden).
Payment Table Mouse Click Listener  –  Displays the selected payment record inside the manage payment details for update, delete, and payment finalization purposes.
Payment Table Color Renderer  –  Applies color highlights based on Payment Status: pending (gray), paid upfront (green), paid full (purple). Rental Status: ongoing (blue), returned (teal), late (orange), cancelled (red).
Update  –  Update selected payment record with new updated values.
Delete  –  Delete selected payment record alongside its rental record.
Clear  –  Clears the manage payment details input fields.
Confirm Transaction  –  Finalize the rental payment (only accepts the full remaining balance amount).
Search  –  Search payment table by keyword (rental id, full name, title, payment status, or rental status).
Sort Options  –  Sorts the payment table by return date, payment date, or payment status.
Sort Order  –  Sort payment table in ascending or descending order.

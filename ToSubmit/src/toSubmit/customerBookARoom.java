package toSubmit;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.InputMismatchException;

public class customerBookARoom {

    public customerBookARoom(String customerID, int[] credentials){
        
        //Establish a connection.
        Connection connect = null;
        Statement statement = null, statement2 = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null, resultSet2 = null, resultSet3 = null, resultSet4 = null, resultSet5 = null;
        
        //Attributes
        String roomID, description;
        int countRoom = 1, amountOfGuest = 0, amountOfBed = 0, countTransaction = 0, transactionID = 0;
        int bookingID = 0, roomsBookedID = 0;
        double averageRating = 0, roomPrice = 0;
        Scanner sc = new Scanner (System.in);
        
        try {
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/luckyhoteldatabase", "root", "");
            statement = connect.createStatement();
            statement2 = connect.createStatement();

            System.out.print("---------------------------------------------------------------------------------\n");
                                System.out.println("》 The available rooms are as follows,");
                                String SQL13 = "SELECT roomID, roomPrice, averageRating FROM room WHERE roomStatus = 0"; //0 is true = available.
                                resultSet = statement.executeQuery(SQL13);
                                while (resultSet.next()){
                                    roomID = resultSet.getString("roomID");
                                    roomPrice = resultSet.getDouble("roomPrice");
                                    double avg = resultSet.getDouble("averageRating");
                                    
                                    
                                    System.out.println("\n♠ Room " + roomID + "\n--------------------");
                                    System.out.println("Room Price          : RM" + roomPrice);
                                    
                                    String SQL6 = "SELECT amountOfGuest, amountOfBed, description FROM roomtype WHERE roomID = '" + roomID + "'";
                                    resultSet2 = statement2.executeQuery(SQL6);
                                    if (resultSet2.next()){
                                        amountOfGuest = resultSet2.getInt("amountOfGuest");
                                        amountOfBed = resultSet2.getInt("amountOfBed");
                                        description = resultSet2.getString("description");
                                    System.out.println("Amount of Bed       : " + amountOfBed);
                                    System.out.println("Amount of Guest     : " + amountOfGuest);
                                    System.out.println("Description         : " + description);
                                    }
                                    System.out.println("Average Rating      : " + avg);
                                }
                                
                                
                                //Filter out the rooms.
                                System.out.println("\n◉ Enter FILTER to filter rooms based on categories.\n◉ Enter BOOK to book a room.\n◉ Press 'X' to go back.");
                                System.out.print("\nChoose [FILTER / BOOK / X]: ");
                                String choice = sc.next();
                                System.out.print("---------------------------------------------------------------------------------\n");
                                while (choice.charAt(0) == 'F') {
                                    System.out.print("---------------------------------------------------------------------------------\n");
                                    System.out.println("》 FILTER has been chosen. Please select one of the options.\n\n1. PRICE RANGE PER DAY.\n2. RATING.\n3. AMOUNT OF BED(S).\n4. AMOUNT OF GUEST(S).\n5. EXIT FILTERING.");
                                    System.out.print("\nChoose [1 - 5]: ");
                                    int filterChoice = sc.nextInt();
                                while (filterChoice != 5) { 
                                        switch (filterChoice) {
                                            
                                            case (1) : //Price range per day
                                                System.out.print("---------------------------------------------------------------------------------\n");
                                                System.out.print("》 Key in the desired maximum value: RM");
                                                double maxPrice = sc.nextDouble();
                                                System.out.print("》 Key in the desired minimum value: RM");
                                                double minPrice = sc.nextDouble();
                                                
                                                String SQL17 = "SELECT roomID FROM room WHERE roomPrice <= " + maxPrice + " AND " + minPrice + " <= roomPrice AND roomStatus = 0";
                                                resultSet = statement.executeQuery(SQL17);
                         
                                                if (resultSet.next()) {
                                                System.out.printf("\n✔ Our system has succesfully filtered the room(s) ranging from RM%.2f to RM%.2f,\n", minPrice, maxPrice);
                                                countRoom = 1;
                                                resultSet = statement.executeQuery(SQL17);
                                                while (resultSet.next()) {
                                                    roomID = resultSet.getString("roomID");
                                                    System.out.println("  " + countRoom + ". Room " + roomID);
                                                    countRoom++;
                                                } 
                                                } else {
                                                    System.out.println("\n✘ There are no relevant hotel rooms for the chosen filter.\n  Do try it again with different values."); 
                                                }
                                                break;
                                                
                                            case (2) : //Rating
                                                System.out.print("---------------------------------------------------------------------------------\n");
                                                System.out.print("》 Key in the desired maximum rating [Out of 5]: ");
                                                int ratingChoiceMax = sc.nextInt();
                                                System.out.print("》 Key in the desired minimum rating [Out of 5]: ");
                                                int ratingChoiceMin = sc.nextInt();
         
                                                String SQL18 = "SELECT roomID FROM room WHERE averageRating <= " + ratingChoiceMax + " AND " + ratingChoiceMin + " <= averageRating AND roomStatus = 0";
                                                resultSet = statement.executeQuery(SQL18);
                                                if (resultSet.next()) {
                                                System.out.printf("\n✔ Our system has succesfully filtered the room(s) ranging from %d rating to %d rating,\n", ratingChoiceMin, ratingChoiceMax);
                                                countRoom = 1;
                                                resultSet = statement.executeQuery(SQL18);
                                                while (resultSet.next()) {
                                                    roomID = resultSet.getString("roomID");
                                                    System.out.println("  " + countRoom + ". Room " + roomID);
                                                    countRoom++;
                                                } 
                                                } else {
                                                    System.out.println("\n✘ There are no relevant hotel rooms for the chosen filter.\n  Do try it again with different values."); 
                                                }
                                                break;
                                            case (3) :
                                                System.out.print("---------------------------------------------------------------------------------\n");
                                                System.out.print("》 Key in the desired amount of bed(s) [1 - 4]: ");
                                                int amountOfBedChoice = sc.nextInt();
                                                
                                                String SQL19 = "SELECT roomID FROM room WHERE roomStatus = 0 AND roomID IN (SELECT roomID FROM roomtype WHERE amountOfBed = " + amountOfBedChoice + ")";
                                                resultSet = statement.executeQuery(SQL19);
                                                
                                                if (resultSet.next()) {
                                                    System.out.printf("\n✔ Our system has succesfully filtered the room(s) having %d bed(s).\n", amountOfBedChoice);
                                                    countRoom = 1;
                                                    resultSet = statement.executeQuery(SQL19);
                                                while(resultSet.next()) {
                                                    roomID = resultSet.getString("roomID");
                                                    System.out.println("  " + countRoom + ". Room " + roomID);
                                                    countRoom++;
                                                } 
                                                } else {
                                                    System.out.println("\n✘ There are no relevant hotel rooms for the chosen filter.\n  Do try it again with different values."); 
                                                }
                                                break;
                                            case (4) :
                                                System.out.print("---------------------------------------------------------------------------------\n");
                                                System.out.print("》 Key in the desired amount of guest(s) [1 - 5]: ");
                                                int amountOfGuestChoice = sc.nextInt();
                                                
                                                String SQL20 = "SELECT roomID FROM room WHERE roomStatus = 0 AND roomID IN (SELECT roomID FROM roomtype WHERE amountOfGuest = " + amountOfGuestChoice + ")";
                                                resultSet = statement.executeQuery(SQL20);
                                                
                                                if (resultSet.next()){
                                                    System.out.printf("\n✔ Our system has succesfully filtered the room(s) having %d guest(s).\n", amountOfGuestChoice);
                                                    countRoom = 1;
                                                    resultSet = statement.executeQuery(SQL20);
                                                while(resultSet.next()) {
                                                    roomID = resultSet.getString("roomID");
                                                    System.out.println("  " + countRoom + ". Room " + roomID);
                                                    countRoom++;
                                                } } 
                                                
                                                else {
                                                    System.out.println("\n✘ There are no relevant hotel rooms for the chosen filter.\n  Do try it again with different values.");
                                                } 
                                                break;
                                        }
                                        break;   
                                   }
                                System.out.println("\n◉ Enter FILTER to filter rooms based on categories.\n◉ Enter BOOK to book a room.\n◉ Press 'X' to go back.");
                                System.out.print("\nChoose [FILTER / BOOK / X]: ");
                                choice = sc.next();
                                } //end of while loop
                                if (choice.charAt(0) == 'B') { //Booking room through transaction
                                    while (true) {
                                    System.out.print("》 Please choose a room to inspect: Room ");
                                    roomID = sc.next();
                                    
                                    String SQL85 = "SELECT amountOfGuest FROM roomtype WHERE roomID = '" + roomID + "'";
                                    resultSet = statement.executeQuery(SQL85);
                                    if (resultSet.next()) {
                                    System.out.println("》 List of images of the selected room will be displayed on the screen.\n"
                                                     + "   Please check it out.");
                                    slidingImage roomPictures = new slidingImage(resultSet.getInt("amountOfGuest"));
                                    }
                                    System.out.print("\n✒ Proceed to book this room? Choose [B to BOOK / N for NO]: ");
                                    String ans1 = sc.next(); 
                                    if (ans1.charAt(0) == 'B' || ans1.charAt(0) == 'b') {
                                        break;
                                        }
                                    System.out.print("---------------------------------------------------------------------------------\n");
                                    }
                                   
                                    String SQL45 = "SELECT roomPrice FROM room WHERE roomID ='" + roomID + "'";
                                    sc.nextLine();
                                    System.out.print("---------------------------------------------------------------------------------\n");
                                    
                                        System.out.print("》 Please enter your card number, CVV number, and expiry date without slashes\n"
                                                       + "  [Format: CARDNUMBER CVV EXPIRYDATE]\t: ");
                                        credentials[0] = sc.nextInt();
                                        credentials[1] = sc.nextInt();
                                        credentials[2] = sc.nextInt();
                                        
                                        String SQL99 = "SELECT roomPrice FROM room WHERE roomID ='" + roomID + "'";
                                        resultSet = statement.executeQuery(SQL99);
                                        if (resultSet.next()){
                                            roomPrice = resultSet.getDouble("roomPrice");
                                        System.out.printf("》 Booking Fee of Room %s\t: RM%.2f\n", roomID, roomPrice);
                                            
                                         System.out.print("》 Please pay your booking fees\t: RM");
                                            double amountPaid = sc.nextDouble();
                                               
                                            while (true) {
                                            if (amountPaid == roomPrice) {
                                                String SQL100 = "INSERT INTO transaction (customerID, roomID, amountPaid) "
                                                        + "VALUES (?, ?, ?)";
                                                
                                                preparedStatement = connect.prepareStatement(SQL100);
                                                preparedStatement.setString(1, customerID);
                                                preparedStatement.setString(2, roomID);
                                                preparedStatement.setDouble(3, amountPaid);
                                                preparedStatement.executeUpdate();
                                                preparedStatement.close();
                                                
                                                String SQL101 = "SELECT transactionID FROM transaction WHERE customerID = '" + customerID + "' AND "
                                                                + "roomID = '" + roomID + "' AND amountPaid = " + amountPaid + " GROUP BY transactionID";
                                                resultSet = statement.executeQuery(SQL101);
                                                if (resultSet.next()){
                                                    transactionID = resultSet.getInt("transactionID");
                                                    
                                        System.out.print("\n》 Please enter the starting date\n"
                                                       + "  [YYYY-MM-DD]\t\t: ");
                                                    String dateBookingStart = sc.next();
                                                    sc.nextLine();

                                        System.out.print("》 Please enter the ending date\n"
                                                       + "  [YYYY-MM-DD]\t\t: ");
                                        
                                                    String dateBookingEnd = sc.next();
                                                    sc.nextLine();
                                                    
                                                    String SQL102 = "INSERT INTO bookingrooms (customerID, roomID, transactionID) VALUES (?, ?, ?)";
                                                    
                                                    preparedStatement = connect.prepareStatement(SQL102);
                                                    preparedStatement.setString(1, customerID);
                                                    preparedStatement.setString(2, roomID);
                                                    preparedStatement.setDouble(3, transactionID);
                                                    preparedStatement.executeUpdate();
                                                    preparedStatement.close();
                                                    
                                                    String SQL103 = "SELECT bookingID FROM bookingrooms WHERE customerID = '" + customerID + "' AND "
                                                                + "roomID = '" + roomID + "' AND transactionID = " + transactionID;
                                                    resultSet = statement.executeQuery(SQL103);
                                                    if (resultSet.next()){
                                                        bookingID = resultSet.getInt("bookingID");
                                        
                                                        String SQL104 = "INSERT INTO bookingdates (bookingID, dateBookingStart, dateBookingEnd) VALUES (?, ?, ?)";
                                                        
                                                        preparedStatement = connect.prepareStatement(SQL104);
                                                        preparedStatement.setInt(1, bookingID);
                                                        preparedStatement.setDate(2, Date.valueOf(dateBookingStart));
                                                        preparedStatement.setDate(3, Date.valueOf(dateBookingEnd));
                                                        preparedStatement.executeUpdate();
                                                        
                                                        String SQL108 = "INSERT INTO roomsbooked (bookingID, roomID) VALUES (?, ?)";
                                                        
                                                        preparedStatement = connect.prepareStatement(SQL108);
                                                        preparedStatement.setInt(1, bookingID);
                                                        preparedStatement.setString(2, roomID);
                                                        preparedStatement.executeUpdate();
                                                        
                                                        preparedStatement.close();
                                                        System.out.println("✔ Our system received your booking and payment successfully. Please proceed.");
                                                        
                                                        String SQL67 = "SELECT bookingDatesID FROM bookingdates WHERE bookingID = " + bookingID;
                                                        resultSet = statement.executeQuery(SQL67);
                                                        if (resultSet.next()) {
                                                            int bookingDatesID = resultSet.getInt("bookingDatesID");
                                                            
                                                            String SQL47 = "INSERT INTO transactionlist (transactionID, bookingDatesID, transactionStatus) VALUES (?, ?, ?)";
                                
                                                            preparedStatement = connect.prepareStatement(SQL47);
                                                            preparedStatement.setInt(1, transactionID);
                                                            preparedStatement.setInt(2, bookingDatesID);
                                                            preparedStatement.setInt(3, 1); //1 means bayar
                                                            preparedStatement.executeUpdate();
                                                            
                                                            String SQL62 = "UPDATE room SET roomStatus = ? WHERE roomID = '" + roomID + "'";
                                                            
                                                            preparedStatement = connect.prepareStatement(SQL62);
                                                            preparedStatement.setInt(1, 1);
                                                            preparedStatement.executeUpdate();
                                                            preparedStatement.close();
                                                        }
                 
                                                    }
                                                }
                                            
                                            } else if (amountPaid > roomPrice) {
                                                String SQL101 = "INSERT INTO transaction (customerID, roomID, amountPaid) "
                                                        + "VALUES (?, ?, ?)";
                                                
                                                preparedStatement = connect.prepareStatement(SQL101);
                                                preparedStatement.setString(1, customerID);
                                                preparedStatement.setString(2, roomID);
                                                preparedStatement.setDouble(3, amountPaid);
                                                preparedStatement.executeUpdate();
                                                preparedStatement.close();
                                                System.out.printf("》 Remainder will be banked\n"
                                                                + "  into your account\t\t: RM%.2f", (amountPaid - roomPrice));
                                                
                                                String SQL190 = "SELECT transactionID FROM transaction WHERE customerID = '" + customerID + "' AND "
                                                                + "roomID = '" + roomID + "' AND amountPaid = " + amountPaid + " GROUP BY transactionID";
                                                resultSet = statement.executeQuery(SQL190);
                                                if (resultSet.next()){
                                                    transactionID = resultSet.getInt("transactionID");
                                                    
                                                    System.out.print("\n》 Please enter the starting date\n"
                                                       + "  [YYYY-MM-DD]\t\t\t: ");
                                                    String dateBookingStart = sc.next();
                                                    sc.nextLine();

                                                    System.out.print("》 Please enter the ending date\n"
                                                       + "  [YYYY-MM-DD]\t\t\t: ");
                                                    String dateBookingEnd = sc.next();
                                                    sc.nextLine();
                                                    
                                                    String SQL102 = "INSERT INTO bookingrooms (customerID, roomID, transactionID) VALUES (?, ?, ?)";
                                                    
                                                    preparedStatement = connect.prepareStatement(SQL102);
                                                    preparedStatement.setString(1, customerID);
                                                    preparedStatement.setString(2, roomID);
                                                    preparedStatement.setDouble(3, transactionID);
                                                    preparedStatement.executeUpdate();
                                                    preparedStatement.close();
                                                    
                                                    String SQL103 = "SELECT bookingID FROM bookingrooms WHERE customerID = '" + customerID + "' AND "
                                                                + "roomID = '" + roomID + "' AND transactionID = " + transactionID;
                                                    resultSet = statement.executeQuery(SQL103);
                                                    if (resultSet.next()){
                                                        bookingID = resultSet.getInt("bookingID");
                                        
                                                        String SQL104 = "INSERT INTO bookingdates (bookingID, dateBookingStart, dateBookingEnd) VALUES (?, ?, ?)";
                                                        
                                                        preparedStatement = connect.prepareStatement(SQL104);
                                                        preparedStatement.setInt(1, bookingID);
                                                        preparedStatement.setDate(2, Date.valueOf(dateBookingStart));
                                                        preparedStatement.setDate(3, Date.valueOf(dateBookingEnd));
                                                        preparedStatement.executeUpdate();
                                                        
                                                        String SQL108 = "INSERT INTO roomsbooked (bookingID, roomID) VALUES (?, ?)";
                                                        
                                                        preparedStatement = connect.prepareStatement(SQL108);
                                                        preparedStatement.setInt(1, bookingID);
                                                        preparedStatement.setString(2, roomID);
                                                        preparedStatement.executeUpdate();
                                                        
                                                        preparedStatement.close();
                                                        System.out.println("\n✔ Our system received your booking and payment successfully. Please proceed.");
                                                        
                                                        String SQL67 = "SELECT bookingDatesID FROM bookingdates WHERE bookingID = " + bookingID;
                                                        resultSet = statement.executeQuery(SQL67);
                                                        if (resultSet.next()) {
                                                            int bookingDatesID = resultSet.getInt("bookingDatesID");
                                                            
                                                            String SQL47 = "INSERT INTO transactionlist (transactionID, bookingDatesID, transactionStatus) VALUES (?, ?, ?)";
                                
                                                            preparedStatement = connect.prepareStatement(SQL47);
                                                            preparedStatement.setInt(1, transactionID);
                                                            preparedStatement.setInt(2, bookingDatesID);
                                                            preparedStatement.setInt(3, 1); //1 means bayar
                                                            preparedStatement.executeUpdate();
                                                            
                                                            String SQL62 = "UPDATE room SET roomStatus = ? WHERE roomID = '" + roomID + "'";
                                                            
                                                            preparedStatement = connect.prepareStatement(SQL62);
                                                            preparedStatement.setInt(1, 1);
                                                            preparedStatement.executeUpdate();
                                                            preparedStatement.close();
                                                            
                                                            preparedStatement.close();
                                                        }
                                                    }
                                            }
                                                break;
                                            }else {
                                                System.out.print("✘ Insufficient funds! Please pay your booking fees: RM");
                                                amountPaid = sc.nextDouble();
                                            }
                                            }
                                        }
                                        }
                                        if (choice.charAt(0) == 'X') {
                                            
                                        }  
        } catch (Exception e) {
            e.printStackTrace(); 
        } finally {
            try {
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException ex) {
         }
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
            }
        }
        
    }
    
    
}

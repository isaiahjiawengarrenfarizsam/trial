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
import static toSubmit.Main.adminID;

public class customerReview {

    public customerReview(String customerID) {
        
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
        String password, email;
        
        
        try {
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/luckyhoteldatabase", "root", "");
            statement = connect.createStatement();
            statement2 = connect.createStatement();
            
            
            System.out.print("---------------------------------------------------------------------------------\n");
               
                                String SQL25 = "SELECT roomID FROM bookingrooms WHERE customerID = '" + customerID + "'";

                                resultSet = statement.executeQuery(SQL25);

                                if (resultSet.next()) {
                                int count = 1;
                                System.out.println("\n》 Room(s) you booked in the past,\n");
                                resultSet = statement.executeQuery(SQL25);
                                while (resultSet.next()) {
                                    roomID = resultSet.getString("roomID");
                                    System.out.println("  " + count + ". Room " + roomID);
                                    count++;
                                }
           
                                System.out.print("\n》 Please choose which room that you want to submit your comment: ");
                                String roomChosen = sc.next(); //masukkan dalam roomID

                                sc.nextLine(); //do this before the one yang consumed the scanner

                                String SQL26 = "SELECT comment FROM review WHERE roomID = '" + roomChosen + "' AND customerID = '" + customerID + "'";
                                resultSet = statement.executeQuery(SQL26);

                                if (resultSet.next()){
                                System.out.print("》 You have inserted your review for this room. ");} 

                                else {
                                System.out.print("》 Comment your experience: ");
                                String comment = sc.nextLine();

                                System.out.print("》 Rate your experience! [Choose out of 5]: ");
                                int rating = sc.nextInt();

                                while (rating > 5) {
                                    System.out.println("✘ Oh no, you exceeded the maximum rating that you can enter.\n   Please try again: ");
                                    rating = sc.nextInt();
                                }
                                String SQL27 = "INSERT INTO review (rating, comment, roomID, customerID)" +
                                              " VALUES (?, ?, ?, ?)";

                                preparedStatement = connect.prepareStatement(SQL27);
                                preparedStatement.setInt(1, rating);
                                preparedStatement.setString(2, comment);
                                preparedStatement.setString(3, roomChosen);
                                preparedStatement.setString(4, customerID);
                                preparedStatement.executeUpdate();
                                //Count average rating.
                                
                                String SQL559 = "SELECT AVG(rating) FROM review WHERE roomID = '" + roomChosen + "'";
                                resultSet2 = statement.executeQuery(SQL559);
                                if (resultSet2.next()) {
                                    double avg = resultSet2.getDouble(1);
                                    String SQL560 = "UPDATE room SET averageRating = ? WHERE roomID = '" + roomChosen + "'";
                                    
                                    preparedStatement = connect.prepareStatement(SQL560);
                                    preparedStatement.setDouble(1, avg);
                                    preparedStatement.executeUpdate();
                                    preparedStatement.close();
                                }
                    
                                preparedStatement.close();
            
                                System.out.println("\n✔ We have succesfully add your review into the targeted room.\n"
                                        + "  Thank you for using our system.");
                                }                             
                                } else {
                                    System.out.println("》 There are no rooms that you can submit your review.\n   Book a room and you are able to submit one.");
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

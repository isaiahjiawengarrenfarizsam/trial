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

public class customerBookingList {

    public customerBookingList(String customerID, String email) {
        
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
                                String SQL10 = "SELECT roomsBookedID FROM roomsbooked WHERE bookingID IN (SELECT bookingID FROM bookingrooms WHERE customerID = '" + customerID + "');";
                                
                                resultSet = statement.executeQuery(SQL10);
                                if (resultSet.next()) {
                                    System.out.println("》 The following are the past bookings you have made,\n");
                                    resultSet = statement.executeQuery(SQL10);
                                    countRoom = 1;
                                    while (resultSet.next()){
                                    roomsBookedID = resultSet.getInt("roomsBookedID");
                                    System.out.println("  " + countRoom + ". Room Booking " + roomsBookedID);
                                    countRoom++;
                                    }
                                    
                                    //Already filtered out the details.
                                    System.out.print("\n》 Please select which room bookings you want to inspect [Choose the room booking number]: ");
                                    roomsBookedID = sc.nextInt();
                                    sc.nextLine();
                                    
                                    String SQL88 = "SELECT roomID FROM roomsbooked WHERE roomsBookedID = " + roomsBookedID;
                                    resultSet = statement.executeQuery(SQL88);
                                    
                                    if (resultSet.next()){
                                        roomID = resultSet.getString("roomID");
                                        
                                        String SQL11 = "SELECT roomID, roomPrice FROM room WHERE roomID = '" + roomID + "'";
                                        resultSet3 = statement2.executeQuery(SQL11);
                                        if (resultSet3.next()) {
                                            roomID = resultSet3.getString("roomID");
                                        roomPrice = resultSet3.getDouble("roomPrice");
                                        String SQL12 = "SELECT amountOfBed, amountOfGuest, description FROM roomtype WHERE roomID = '" + roomID + "'";
                                        resultSet2 = statement2.executeQuery(SQL12);
                                        
                                        if (resultSet2.next()){
                                        
                                        amountOfGuest = resultSet2.getInt("amountOfGuest");
                                        amountOfBed = resultSet2.getInt("amountOfBed");
                                        description = resultSet2.getString("description");
                                        
                                        System.out.printf("\n✔ Succesfully retrieved information for Room Booking %d,\nRoom : %s\nRoom Price: %.2f\nAmount Of Guest: %d\nAmount Of Bed: %d\nDescription: %s\n", roomsBookedID, roomID, roomPrice, amountOfGuest, amountOfBed, description);  
                                    }
                                    else {
                                        System.out.println("✘ One or more information about this room has been removed by the admin.");
                                    }
                                        }else {
                                        System.out.println("✘ One or more information about this room has been removed by the admin.");
                                    } 
                                    }
                                } else {
                                    System.out.println("》 You have not made any bookings. Any bookings you created will appear here.");
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

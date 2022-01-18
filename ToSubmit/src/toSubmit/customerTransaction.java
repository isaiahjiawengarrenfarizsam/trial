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

public class customerTransaction {

    public customerTransaction(String customerID) {
        
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
                                String SQL20 = "SELECT transactionID FROM transaction WHERE customerID = '" + customerID + "'";
                                resultSet = statement.executeQuery(SQL20);
                     
                                if (resultSet.next()){
                                    System.out.println("》 Past transactions that you have made,\n");
                                    resultSet = statement.executeQuery(SQL20);
                                    countRoom = 1;
                                while (resultSet.next()) {
                                    transactionID = resultSet.getInt("transactionID");
                                    System.out.println("   " + countRoom + ". Transaction ID: " + transactionID);
                                    countRoom++;
                                }
                                System.out.print("\n》 Which transaction that you want to know more? [Choose Transaction ID]: ");
                                transactionID = sc.nextInt();
                                
                                String SQL21 = "SELECT amountPaid, roomID FROM transaction WHERE transactionID = '" + transactionID + "' AND customerID = '" + customerID + "'";
                                
                                resultSet = statement.executeQuery(SQL21);
                                if (resultSet.next()) {
                                    double displayTransactionID = resultSet.getDouble("amountPaid");
                                    String displayRoomID = resultSet.getString("roomID");
                                    System.out.printf("\n》 Transaction %d has chosen. The following are the information for the selected transaction.\n\n", transactionID);
                                   System.out.println("   1. Room name\t\t\t: " + displayRoomID);
                                   System.out.println("   2. Amount Paid\t\t: " + displayTransactionID);
                                

                                String SQL22 = "SELECT dateBookingStart, dateBookingEnd FROM bookingDates WHERE bookingDatesID IN (SELECT bookingDatesID FROM transactionlist WHERE transactionID = " + transactionID + ")";
                                resultSet = statement.executeQuery(SQL22);
                                
                                if (resultSet.next()) {
                                    String displayDateStart = resultSet.getString("dateBookingStart");
                                    String displayDateEnd = resultSet.getString("dateBookingEnd");
                                    System.out.println("   3. Starting booking date\t: " + displayDateStart);
                                    System.out.println("   4. Ending booking date\t: " + displayDateEnd);
                                } }
                                else { System.out.println("✘ The transaction ID that you have entered is invalid."); }
                                } else {
                                    System.out.println("》 There are no transactions you made up until " + java.time.LocalDate.now() + ".");
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

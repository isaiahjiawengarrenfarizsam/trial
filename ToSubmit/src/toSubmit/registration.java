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

public class registration {

    public registration() {
        
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
            
            System.out.print("________________________________________________________________________________\n\n");
                    System.out.println("\t\t\tREGISTRATION OF A NEW ACCOUNT! ✈");
                    System.out.print("________________________________________________________________________________\n");
                    
                    System.out.print("\n》 Enter your very own customer ID\t: ");
                    String customerid = sc.next();
                    sc.nextLine();
                    String SQL3 = "SELECT customerID FROM customeruser WHERE customerid = '" + customerid + "'"; //must use WHERE to filter
                    resultSet = statement.executeQuery(SQL3);
                    
                    while (resultSet.next()) { //to filter if there are two duplicate data for customer ID 
                        System.out.print("》 Oh no! Someone already took the customer ID. Please enter other customer ID: ");
                        customerid = sc.next();
                        sc.nextLine();
                        SQL3 = "SELECT customerID FROM customeruser WHERE customerid = '" + customerid + "'";
                        resultSet = statement.executeQuery(SQL3);
                    } 
                        System.out.print("》 Enter your email\t\t\t: ");
                        email = sc.next();
                        sc.nextLine();
                        System.out.print("》 What's your name?\t\t\t: ");
                        String customerName = sc.nextLine();
                        
                        System.out.print("》 Enter your password\t\t\t: ");
                        password = sc.nextLine();
                        
                        String SQL4 = "INSERT INTO customeruser (customerID, password, customerName, email)" +
                                      " VALUES(?, ?, ?, ?)";
                        
                        preparedStatement = connect.prepareStatement(SQL4);
                        preparedStatement.setString(1, customerid);
                        preparedStatement.setString(2, password);
                        preparedStatement.setString(3, customerName);
                        preparedStatement.setString(4, email);
                        
                        preparedStatement.executeUpdate();
                        
                        preparedStatement.close();
                    
                    System.out.print("\n\n✔ Account registered succesfully! Please proceed.\n");
                    System.out.print("________________________________________________________________________________\n\n");
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

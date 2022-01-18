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

public class forgotten {

    public forgotten() {
        
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
        
        try{
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/luckyhoteldatabase", "root", "");
            statement = connect.createStatement();
            statement2 = connect.createStatement();

            
            System.out.print("_________________________________________________________________________________\n\n");
                    System.out.println("\t\t\t     FORGOTTEN PASSWORD? ✈");
                    System.out.print("_________________________________________________________________________________\n");
                    
                    System.out.print("\n》 Please enter your customer ID\t\t: ");
                    String customerid = sc.next();
                    sc.nextLine();
                    System.out.print("》 Please enter your email that is \n   associated with your customer ID\t: ");
                    email = sc.nextLine();
                    System.out.print("》 Please enter your old password\t: ");
                    String oldpassword = sc.nextLine();
                    
                    String SQL5 = "SELECT customerID FROM customeruser WHERE email ='" + email + "' AND password = '" + oldpassword + "'";
                    resultSet = statement.executeQuery(SQL5);
                    
                    if (resultSet.next()){
                        System.out.print("》 Record found.\n   Do set your new password here\t: ");
                        password = sc.nextLine();
                        
                        String SQL6 = "UPDATE customeruser SET password = ? WHERE customerid = ?";
                        preparedStatement = connect.prepareStatement(SQL6);
                        preparedStatement.setString (1, password);
                        preparedStatement.setString (2, customerid);
                        
                        preparedStatement.executeUpdate();
                        System.out.print("\n\n✔ Password changed succesfully! Please proceed.\n");
                        System.out.print("________________________________________________________________________________\n\n");
                    } else {
                        System.out.println("✘ You don't have any access.");
                        System.out.print("________________________________________________________________________________\n\n");
                    }
        }catch (Exception e) {
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

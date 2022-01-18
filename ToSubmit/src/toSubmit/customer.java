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

public class customer {

    public customer() {
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
            
            System.out.print("________________________________________________________________________________\n\n");
                    System.out.println("\t\t\t     LOGIN AS CUSTOMER! ✈");
                    System.out.print("________________________________________________________________________________\n");
                    
                    int[] credentials = new int[3];
                    String customerID, email;
                    
                    System.out.print("\n》 Enter your email\t: ");
                    email = sc.next();
                    sc.nextLine();
                    
                    System.out.print("》 Enter your password\t: ");
                    String password = sc.nextLine();
                    
                    String SQL9 = "SELECT customerID, email = '" + email + "' FROM customeruser WHERE password = '" + password + "'";
                    
                    resultSet = statement.executeQuery(SQL9);
                    if (resultSet.next()){
                        customerID = resultSet.getString("customerID");
                        System.out.print("________________________________________________________________________________\n\n");
                        System.out.println("\t\t\t YOU HAVE SUCCESFULLY LOGIN! ✈");
                        System.out.print("________________________________________________________________________________\n");
                        System.out.println("\n》 Welcome back, " + customerID + ".\n》 Choose what you want to do.");
                        System.out.println("\nA. INSPECT YOUR BOOKING LIST.\nB. BOOK A ROOM.\nC. INSPECT YOUR TRANSACTION LIST.\nD. SUBMIT YOUR REVIEW!\nE. CHANGE PASSWORD.\nF. PRINT RECEIPTS.\nG. LOG OUT.");
                        System.out.print("\nChoose [A - G]: ");
                        String answer = sc.next();
                        
                        while (answer.charAt(0) != 'G')  {
                        switch (answer.charAt(0)) {
                            case 'A' : //Booking List.
                                customerBookingList bookingList = new customerBookingList(customerID, email);
                                break;
                                
                            case 'B' : //Check the availability of the room.
                                customerBookARoom toBook = new customerBookARoom(customerID, credentials);
                                break;
                                
                            case 'C' : //Check your customerTransaction list  
                                    customerTransaction trsc = new customerTransaction(customerID);
                                    break; 
                            case 'D' :   
                                customerReview rv = new customerReview(customerID);
                                break;
                            case 'E' :
                                customerForgotten csForgot = new customerForgotten(customerID);
                                break;
                            case 'F' : 
                                customerPrintReceipt csPrint = new customerPrintReceipt(customerID);
                                break;
                                
                        } //end of switch
                        System.out.print("---------------------------------------------------------------------------------\n");
                        System.out.println("》 Choose what you want to do next.\n");
                        System.out.println("A. INSPECT YOUR BOOKING LIST.\nB. BOOK A ROOM.\nC. INSPECT YOUR TRANSACTION LIST.\nD. SUBMIT YOUR REVIEW!\nE. CHANGE PASSWORD.\nF. PRINT RECEIPT.\nG. LOG OUT.");
                        System.out.print("\nChoose [A - G]: ");
                        answer = sc.next();
                        }//end of while 
                    } //end of if 
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

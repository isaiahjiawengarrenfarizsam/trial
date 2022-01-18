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

public class customerForgotten {

    public customerForgotten(String customerID) {
        
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
            
                                                System.out.print("》 Enter your email\t: ");
                                                String emailUser = sc.nextLine();
                                                System.out.print("》 Enter old password\t: ");
                                                    String oldpassword = sc.nextLine();
                                                
                                                String SQL58 = "SELECT customerID FROM customeruser WHERE email = '" + emailUser + "' AND password = '" + oldpassword + "'";
                                                while (true){
                                                resultSet = statement.executeQuery(SQL58);
                                                if (resultSet.next()){
                                                System.out.print("\n✒ Proceed to change your password? Choose [Y for YES / N for NO]: ");
                                                String yourAnswer = sc.next();
                                                sc.nextLine();
                                                        if (yourAnswer.charAt(0) == 'Y' || yourAnswer.charAt(0) == 'y') {

                                                            System.out.print("\n》 Enter new password\t: ");
                                                            String newpassword1 = sc.nextLine();

                                                            String SQL6 = "UPDATE customeruser "
                                                                   + "SET password = ? "
                                                                   + "WHERE customerid = ?";
                                                            preparedStatement = connect.prepareStatement(SQL6);
                                                            preparedStatement.setString (1, newpassword1);
                                                            preparedStatement.setString (2, customerID);

                                                            preparedStatement.executeUpdate();
                                                            preparedStatement.close();
                                                            System.out.println("\n✔ We have successfully reset your password!");
                                                        } else {
                                                            System.out.println("\n✔ No changes were made. Thank you for using our system.");
                                                        }
                                                        break;
                                                }
                                                System.out.println("\n✘ One of your credentials are incorrect. Please try again.\n");   
                                                System.out.print("》 Enter your email [Press Y to go back]   : ");
                                                emailUser = sc.nextLine();
                                                if (emailUser.equals("Y")){
                                                    break;
                                                }
                                                System.out.print("》 Enter old password [Press Y to go back] : ");
                                                oldpassword = sc.nextLine();
                                                if (oldpassword.equals("Y")){
                                                    break;
                                                }
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

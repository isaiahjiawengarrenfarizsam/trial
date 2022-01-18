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

public class indexChoice {

    public indexChoice(String answer) {
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
            
            while(answer.charAt(0) != 'E') {
            switch (answer.charAt(0)) {
                case 'A' :
                    admin ad = new admin();
                    break; 
                case 'B' :
                    customer cu = new customer();
                        break;
                case 'C':
                    registration reg = new registration();
                    break;
                case 'D' :
                    forgotten fgt = new forgotten();
                       break;
                default :
                    System.out.println("\n✘ Invalid input.");
                    System.out.print("_________________________________________________________________________________\n\n");
                    break;
            } 
            System.out.println("\t WELCOME TO LUCKY'S HOTEL, WHERE CUSTOMERS FEEL HAPPILY LUCKY! ✈");
            System.out.print("_________________________________________________________________________________\n");
            System.out.println("\n》 Choose what you want to do.\n\nA. LOGIN AS ADMIN.\nB. LOGIN AS CUSTOMER.\n"
                    + "C. DON'T HAVE AN ACCOUNT? CHOOSE ME!\nD. FORGOTTEN PASSWORD? OVER HERE!\nE. LOG OUT FROM THE SYSTEM.\n");
            System.out.print("Choose [A - E]: ");
            answer = sc.next();
            }
        }  catch (Exception e) {
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

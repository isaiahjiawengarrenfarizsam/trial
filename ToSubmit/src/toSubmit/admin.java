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

public class admin {

    public admin() {
        
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
            
            System.out.print("_________________________________________________________________________________\n\n");
                    System.out.println("\t\t\t\t LOGIN AS ADMIN. ✈");
                    System.out.print("_________________________________________________________________________________\n");
                    
                    System.out.print("\n》 Enter your email\t: ");
                    String emailAdmin = sc.next();
                    
                    sc.nextLine(); //to consume
                    System.out.print("》 Enter your password\t: ");
                    String password = sc.nextLine();
                    
                    String SQL = "SELECT adminID FROM adminuser WHERE email = '" + emailAdmin + "' AND password = '" + password + "' ";
                    resultSet = statement.executeQuery(SQL);
                    
                    if (resultSet.next()){
                        System.out.print("_________________________________________________________________________________\n\n");
                        System.out.println("\t\t\t   YOU HAVE SUCCESFULLY LOGIN! ✈");
                        adminID = resultSet.getString("adminID");
                        System.out.print("_________________________________________________________________________________\n");
                        System.out.println("\n》 Welcome back, " + adminID + ".\n》 Choose what you want to do.");
                        System.out.println("\nA. ADD NEW ROOMS.\nB. EDIT EXISTING ROOMS.\nC. AVAILABILITY OF THE ROOMS.\nD. LOG OUT");
                        System.out.print("\nChoose [A - D]: ");
                        String answer = sc.next();
                        while(answer.charAt(0) != 'D') {
                        switch (answer.charAt(0)) {
                            case 'A' : 
                                System.out.print("---------------------------------------------------------------------------------\n"
                                                 + "》 Please key in the name of the room\t\t\t: ");
                                
                                String roomName = sc.next();
                                sc.nextLine();

                                System.out.print("》 Please key in the room price\t\t\t\t: RM");
                                roomPrice = sc.nextDouble();

                                System.out.print("》 Please key in the amount of bed(s) for room " + roomName + "\t: ");
                                amountOfBed = sc.nextInt();

                                System.out.print("》 Please key in the amount of guest(s) for room " + roomName + "\t: ");
                                amountOfGuest = sc.nextInt();

                                sc.nextLine();

                                System.out.print("》 Please enter the description of this room\t\t: ");
                                description = sc.nextLine();
            
                                System.out.print("\n✒ Proceed to add the new room? [Press X to cancel, Y to proceed]: ");
                                String ans;
                                ans = sc.next();
                                if (ans.charAt(0) == 'Y' || ans.charAt(0) == 'y') {
                                String SQL2 = "INSERT INTO room (roomID, adminID, roomPrice, averageRating, roomStatus)" +
                                              " VALUES (?, ?, ?, ?, ?)";
                                
                                preparedStatement = connect.prepareStatement(SQL2);
                                preparedStatement.setString(1, roomName); 
                                preparedStatement.setString(2, adminID);
                                preparedStatement.setDouble(3, roomPrice);
                                preparedStatement.setDouble(4, 0);
                                preparedStatement.setInt(5, 0);
                                preparedStatement.executeUpdate();
                                
                                String SQL3 = "INSERT INTO roomtype (roomID, amountOfBed, amountOfGuest, description)" +
                                              " VALUES (?, ?, ?, ?)";

                                preparedStatement = connect.prepareStatement(SQL3);
                                preparedStatement.setString(1, roomName);
                                preparedStatement.setInt(2, amountOfBed);
                                preparedStatement.setInt(3, amountOfGuest);
                                preparedStatement.setString(4, description);
                                preparedStatement.executeUpdate();

                                preparedStatement.close();
                                
                                System.out.println("\n✔ We have received the information for room " + roomName + "."
                                        + " Please proceed."); }
                                break;
                                
                            case 'B' : //Edit existing rooms. - WORKED
                                String SQL4 = "SELECT roomID, adminID, roomPrice FROM room";
                                resultSet = statement.executeQuery(SQL4);
                                System.out.print("---------------------------------------------------------------------------------");
                                while (resultSet.next()){
                                    roomID = resultSet.getString("roomID");
                                    String adminName = resultSet.getString("adminID");
                                    roomPrice = resultSet.getDouble("roomPrice");
                                    
                                    
                                    System.out.println("\n♠ Room " + roomID + "\n--------------------");
                                    System.out.println("Registered by       : " + adminName);
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
                                    System.out.println("");
                                    }
                                }
                                System.out.print("---------------------------------------------------------------------------------\n");
                                countRoom = 1;
                                System.out.print("》 Please select which room to edit [Press X to go back]: ");
                                roomID = sc.next();
                                if (roomID.charAt(0) == 'X' || roomID.charAt(0) == 'x') {
                                    break;
                                }
                                sc.nextLine();
                                
                                    System.out.println("\nEnter the new values,");   
                                    System.out.print("》 Room Price        : RM");
                                    roomPrice = sc.nextDouble();
                                    System.out.print("》 Amount of Bed     : ");
                                    amountOfBed = sc.nextInt();
                                    System.out.print("》 Amount of Guest   : ");
                                    amountOfGuest = sc.nextInt();
                                    
                                    sc.nextLine();
                                    
                                    System.out.print("》 Description       : ");
                                    description = sc.nextLine();
                                    
                                    String SQL7 = "UPDATE room SET roomPrice = ?, adminID = ? WHERE roomID = '" + roomID + "'";

                                    preparedStatement = connect.prepareStatement(SQL7);
                                    preparedStatement.setDouble(1, roomPrice);
                                    preparedStatement.setString(2, adminID);
                                    preparedStatement.executeUpdate();
                                    
                                    String SQL8 = "UPDATE roomtype SET amountOfBed = ?, amountOfGuest = ?, description = ? WHERE roomID = '" + roomID + "'";
                                    
                                    preparedStatement = connect.prepareStatement(SQL8);
                                    preparedStatement.setInt(1, amountOfBed);
                                    preparedStatement.setInt(2, amountOfGuest);
                                    preparedStatement.setString(3, description);
                                    preparedStatement.executeUpdate();
                                    
                                    preparedStatement.close();
                                    
                                    System.out.println("\n✔ We have edited room " + roomID + " succesfully! Please proceed.");
                                
                                break;
                            case 'C' : 
                                System.out.print("---------------------------------------------------------------------------------\n");
                                String SQL9 = "SELECT roomID FROM room WHERE roomStatus = 1";
                                resultSet = statement.executeQuery(SQL9);
                                
                                if (resultSet.next()){
                                    System.out.print("》 Booked rooms until " + java.time.LocalDate.now() + ", \n");
                                    resultSet = statement.executeQuery(SQL9);
                                    while (resultSet.next()) {
                                        roomID = resultSet.getString("roomID");
                                        System.out.println(countRoom + ". Room " + roomID);
                                        countRoom++;
                                    }
                             
                                    System.out.println("");
                                    System.out.print("》 Choose which room to check-out [Press X to finish editing],\n");
                                    countRoom = 1;
                                    while (true) {
                                    System.out.print(countRoom + ". Room ");
                                    roomID = sc.next();
                                    String SQL10 = "UPDATE room SET roomStatus = ? WHERE roomID = '" + roomID + "'";
                                    
                                    preparedStatement = connect.prepareStatement(SQL10);
                                    preparedStatement.setInt(1, 0);
                                    preparedStatement.executeUpdate();
                                    preparedStatement.close();
                                    if (roomID.charAt(0) == 'X' || roomID.charAt(0) == 'x') {
                                        break;
                                    }
                                    countRoom++;
                                    }
                                    System.out.println("\n✔ The changes in the availability of the rooms have been saved." );
                                } else {
                                    System.out.println("》 There are no booked rooms for now.");
                                }
                        }
                        System.out.print("---------------------------------------------------------------------------------\n"
                                       + "》 Choose what you want to do.");
                        System.out.println("\n\nA. ADD NEW ROOMS.\nB. EDIT EXISTING ROOMS.\nC. AVAILABILITY OF THE ROOMS.\nD. LOG OUT");
                        System.out.print("\nChoose [A - D]: ");
                        answer = sc.next();
                        } //end of while loop
                        System.out.print("_________________________________________________________________________________\n\n"
                                         + "\t    LOGGED OUT SUCCESFULLY! THANK YOU FOR USING OUR SYSTEM. ✈\n\n"
                                         + "_________________________________________________________________________________\n");
                        
                    } else {
                        System.out.print("_________________________________________________________________________________\n\n"
                                + "\t        USER NOT FOUND! INCORRECT EMAIL OR PASSWORD INPUT! ✈\n\n"
                                + "_________________________________________________________________________________\n");
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

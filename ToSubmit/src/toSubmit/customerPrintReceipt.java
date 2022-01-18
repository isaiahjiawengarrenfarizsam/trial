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

public class customerPrintReceipt {
    
    private int printTransaction;
    private String printCustomerID;

    public customerPrintReceipt() {
    }
    
    public customerPrintReceipt(String customerID) {
        
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
            
            System.out.print("\n---------------------------------------------------------------------------------\n");
      
            String SQL7 = "SELECT transactionID FROM transaction WHERE customerID = '" + customerID + "'";
            resultSet = statement.executeQuery(SQL7);
            if (resultSet.next()) {
                System.out.println("》 Which transaction receipts that you want to print out?\n");
                resultSet = statement.executeQuery(SQL7);
            while (resultSet.next()){
                System.out.println("   " + countRoom + ". Transaction " + resultSet.getInt("transactionID"));
                countRoom++;
            }
            System.out.print("\nChoose which transaction ID: ");
            transactionID = sc.nextInt();
            
            printTransaction = transactionID;
            printCustomerID = customerID;
            printing();
            System.out.println("》 Please refer to the booking information and transaction confirmation that we have displayed.");
            } else {
                System.out.print("》 Any transactions that you have succesfully made will appear here.\n");
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

    public int getPrintTransaction() {
        return printTransaction;
    }

    public String getPrintCustomerID() {
        return printCustomerID;
    }
    
    public void printing() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(printReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(printReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(printReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(printReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new printReceipt(printTransaction, printCustomerID).setVisible(true);
            }
        });
    }
    
}

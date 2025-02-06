package org.bankbud.mainapp;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import org.bankbud.mainapp.Classes.Manager;
import org.bankbud.mainapp.Pages.LoggedOut;

//Main UI handler providing frame for panel objects
public class Main {

    public static void main(String[] args) {

        //Colour system for dialog boxes
        UIManager.put("MenuBar.background", new ColorUIResource(Color.decode("#16152b")));
        UIManager.put("OptionPane.background",new ColorUIResource(Color.decode("#16152b")));
        UIManager.put("OptionPane.messageForeground", Color.white);
        UIManager.put("Panel.background",new ColorUIResource(Color.decode("#16152b")));
        UIManager.put("TextField.background", new ColorUIResource(75,72,130));
        UIManager.put("TextField.foreground", Color.white);
        UIManager.put("TextField.caretForeground",Color.white);
        UIManager.put("PasswordField.background", new ColorUIResource(75,72,130));
        UIManager.put("PasswordField.foreground", Color.white);
        UIManager.put("PasswordField.caretForeground", Color.white);
        UIManager.put("Label.foreground", Color.white);

        //Manager object initialised
        Manager accManager = new Manager();

        //The root frame window
        JFrame root = new JFrame("BankBud | A Bank app demo"); 
        root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        
        //First page is initialised in this function
        JPanel logOutPage = new LoggedOut(accManager,root);
        root.add(logOutPage);

        //Window settings
        root.setBackground(Color.BLACK);
        root.setResizable(false);
        root.setLayout(null);  
        root.setSize(1000, 600);
        root.setVisible(true);   
    }

}



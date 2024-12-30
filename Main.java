import Classes.Manager;
import Pages.LoggedOut;
import java.awt.Color;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;

//Main UI handler providing frame for panel objects
public class Main {

    public static void main(String[] args) {

        //Colour system for dialog boxes
        UIManager.put("MenuBar.background", new ColorUIResource(17,17,35));
        UIManager.put("OptionPane.background",new ColorUIResource(17,17,35));
        UIManager.put("OptionPane.messageForeground", Color.white);
        UIManager.put("Panel.background",new ColorUIResource(17,17,35));
        UIManager.put("TextField.background", new ColorUIResource(26,26,53));
        UIManager.put("TextField.foreground", Color.white);
        UIManager.put("TextField.caretForeground",Color.white);
        UIManager.put("PasswordField.background", new ColorUIResource(26,26,53));
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


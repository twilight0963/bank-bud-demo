package org.bankbud.mainapp.Pages;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.bankbud.mainapp.Classes.ChangePassPrompt;
import org.bankbud.mainapp.Classes.HoverButton;
import org.bankbud.mainapp.Classes.Manager;
import org.bankbud.mainapp.Classes.PromptBox;
import org.bankbud.mainapp.Classes.TripleBox;

//Page created using Swing JPanels
public class LoggedIn extends JPanel{

    //Initialise function
    public LoggedIn(Manager accManager,JFrame root){

        JPanel BG = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        BG.setBounds(10, 10, 980, 330);
        BG.setBackground(Color.decode("#282649"));

        //Show name at the top
        JLabel greeting = new JLabel("Hello, " + accManager.getOwner()+"!",SwingConstants.CENTER);
        greeting.setFont(new Font("Segoe UI",Font.BOLD,36));
        greeting.setForeground(Color.WHITE);

        //Account number label
        Font regFont = new Font("Segoe UI", Font.PLAIN,16);
        JLabel acNumDetail = new JLabel("Account Number: " + accManager.getUID(),SwingConstants.CENTER);
        acNumDetail.setFont(regFont);
        acNumDetail.setForeground(Color.WHITE);

        //Account balance label
        JLabel balDetail = new JLabel("Current balance: $"+accManager.getBalance(),SwingConstants.CENTER);
        balDetail.setFont(regFont);
        balDetail.setForeground(Color.WHITE);

        //Deposit function
        JButton depositButton = new HoverButton("Deposit","Deposit.png");
        depositButton.setBounds(100,400,190,100);
        depositButton.addActionListener(_->{
            //Ask variables using Classes.PromptBox.java
            PromptBox pBox = new PromptBox("Deposit","Deposit Amount: ", "Password: ");
            //if ok button clicked
            if (pBox.done){
                try{
                    double newBal = accManager.deposit(Double.parseDouble(pBox.ans1),pBox.ans2);
                switch ((int) newBal){

                        //ERRORS
                        //-1 = Auth error
                        // -5 = Value error
                        case -1 -> {
                            JOptionPane.showMessageDialog(this, "Password is incorrect!");
                        }
                        case -5-> {
                            JOptionPane.showMessageDialog(this, "Invalid amount!");
                        }
                        default -> {
                            JOptionPane.showMessageDialog(this, "Deposited successfully!\n Your new balance is $"+newBal);
                            balDetail.setText("Current balance: $" + accManager.getBalance());
                        }


                    }
                }catch(HeadlessException | NumberFormatException e){

                    JOptionPane.showMessageDialog(this, "Unexpected Error Occured!");

                }
            }
        });


        //Withdraw function
        JButton withdrawButton = new HoverButton("Withdraw","Withdraw.png");
        withdrawButton.setBounds(300,400,190,100);
        withdrawButton.addActionListener(_->{
            //Ask variables using Classes.PromptBox.java
            PromptBox pBox = new PromptBox("Withdraw","Withdrawal Amount: ", "Password: ");
            //if ok button clicked
            if (pBox.done){
                try{
                        double newBal = accManager.withdraw(Double.parseDouble(pBox.ans1),pBox.ans2);
                        
                        switch ((int) newBal){

                            //ERRORS
                            //-1 = Auth Error
                            //-2 = Balance > Withdraw amount
                            //-5 = Value error

                            case -1 -> {
                                JOptionPane.showMessageDialog(this, "Password is incorrect!");
                            }
                            case -2 -> {
                                JOptionPane.showMessageDialog(this, "Balance too low!");
                            }
                            case -5 -> {
                                JOptionPane.showMessageDialog(this, "Invalid withdraw amount!");
                            }
                            default -> {
                                {
                                    JOptionPane.showMessageDialog(this, "Withdrawed successfully!\n Your balance is $"+newBal);
                                    balDetail.setText("Current balance: $" + accManager.getBalance());
                                }
                            }


                        }
                }catch(HeadlessException | NumberFormatException e){

                    JOptionPane.showMessageDialog(this, "Unexpected Error Occured!");

                }
            }
        });

        //Transfer function
        JButton sendButton = new HoverButton("Transfer funds","Transfer.png");
        sendButton.setBounds(500,400,190,100);
        sendButton.addActionListener(_->{
            //Ask variables using Classes.TripleBox.java
            TripleBox tBox = new TripleBox("Transfer","Recipient Account number: ","Transfer Amount: ", "Password: ");
            //if ok button clicked
            if (tBox.done){
                try{
                        double newBal = accManager.sendBal(Integer.parseInt(tBox.ans1), Double.parseDouble(tBox.ans2), tBox.ans3);
                        switch ((int)newBal){

                            //ERRORS
                            //-1 = Auth Error
                            //-2 = Balance < Withdraw amount
                            //-3 = Account number map returns null
                            //-4 = Account number inputted is same as saved
                            //-5 = Value Error

                            case -1 -> {
                                JOptionPane.showMessageDialog(this, "Password is incorrect!");
                            }
                            case -2 -> {
                                JOptionPane.showMessageDialog(this, "Insufficient Balance!");
                            }
                            case -3 -> {
                                JOptionPane.showMessageDialog(this, "Account Number does not exist!");
                            }
                            case -4 -> {
                                JOptionPane.showMessageDialog(this, "Cannot send money to yourself!");

                            }
                            case -5 -> {
                                JOptionPane.showMessageDialog(this, "Invalid transfer amount!");
                            }
                            default -> {
                                {
                                    JOptionPane.showMessageDialog(this, "Sent successfully!\n Your balance is $"+accManager.getBalance());
                                    balDetail.setText("Current balance: $" + accManager.getBalance());
                                }
                            }
                    }
                }
                catch(HeadlessException | NumberFormatException e){

                    JOptionPane.showMessageDialog(this, "Unexpected Error Occured!");

                }
            }
        });


        //Go back to log out screen and overwrite all saved information
        JButton logOutButton = new HoverButton("Log Out","#993051","#b24064","LogOut.png");

        logOutButton.setBounds(700,400,190,100);
        logOutButton.addActionListener(_->{

            //Initialise Pages.LoggedOut.java
            JPanel logOutPage = new LoggedOut(accManager,root);

            accManager.signOut();
            
            //Set this page invisible and remove from JFrame
            setVisible(false);
            root.getContentPane().remove(this);

            //Add logoutpage to JFrame
            root.add(logOutPage);
            logOutPage.setVisible(true);
            //Update JFrame
            root.repaint();
        });
        
        JButton transactionsButton = new HoverButton("Transactions","Transactions.png");

        transactionsButton.setBounds(700,400,400,100);
        transactionsButton.addActionListener(_->{

            //Initialise Pages.LoggedOut.java
            JPanel transactionPage = new TransactionHistory(accManager,root);

            
            //Set this page invisible and remove from JFrame
            setVisible(false);
            root.getContentPane().remove(this);

            //Add logoutpage to JFrame
            root.add(transactionPage);
            transactionPage.setVisible(true);
            //Update JFrame
            root.repaint();
        });

        //changePass function
        JButton changePassButton = new HoverButton("Change password","#36966f","#78baa0","Auth.png");
        changePassButton.setBounds(100,350,790,40);
        changePassButton.addActionListener(_->{
            //Ask variables using Classes.PromptBox.java
            ChangePassPrompt tBox = new ChangePassPrompt("Change password","Current Password: ", "New Password: ","Confirm Password: ");
            //if ok button clicked
            if (tBox.done){
                try{
                    int newBal = accManager.changePass(tBox.ans1, tBox.ans2,tBox.ans3);
                switch (newBal){

                        //ERRORS
                        //-1 = Auth error
                        // -5 = newPassword != confirmPass
                        case -1 -> {
                            JOptionPane.showMessageDialog(this, "Current password is incorrect!");
                        }
                        case -5 -> {
                            JOptionPane.showMessageDialog(this, "Passwords do not match");
                        }
                        default -> {
                            JOptionPane.showMessageDialog(this, "Password changed successfully!");
                            balDetail.setText("Current balance: $" + accManager.getBalance());
                        }


                    }
                }catch(HeadlessException | NumberFormatException e){

                    JOptionPane.showMessageDialog(this, "Unexpected Error Occured!");

                }
            }
        });
        
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.ipady=20;
        gbc.gridx=0;
        gbc.gridy=0;
        BG.add(greeting,gbc);
        gbc.gridx=0;
        gbc.gridy=1;
        BG.add(acNumDetail,gbc);
        gbc.gridx=0;
        gbc.gridy=2;
        BG.add(balDetail,gbc);
        gbc.ipadx = 20;
        gbc.gridx=0;
        gbc.gridy=3;
        BG.add(transactionsButton,gbc);
        add(BG);

        add(changePassButton);
        add(logOutButton);
        add(sendButton);
        add(withdrawButton);
        add(depositButton);

        //Page settings
        setBackground(Color.decode("#16152b"));
        setLayout(null);
        setSize(1000,600);
        setVisible(true);
    }
}

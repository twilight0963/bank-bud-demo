package org.bankbud.mainapp.Pages;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.bankbud.mainapp.Classes.AmountCellRenderer;
import org.bankbud.mainapp.Classes.DatabaseManager;
import org.bankbud.mainapp.Classes.HoverButton;
import org.bankbud.mainapp.Classes.Manager;

public class TransactionHistory extends JPanel{
    public TransactionHistory(Manager accManager,JFrame root){
        
        setBackground(Color.decode("#16152b"));
        
        JPanel BG = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        BG.setBounds(10, 10, 980, 260);
        BG.setBackground(Color.decode("#282649"));
        JLabel title = new JLabel("Your Transactions");
        title.setFont(new Font("Segoe UI", Font.BOLD, 48));
        title.setForeground(Color.white);

        List<String[]> data = new ArrayList<>();
        ResultSet transactions;
        DatabaseManager db = new DatabaseManager();
        try {
            PreparedStatement stmt = db.getConnection().prepareStatement(            
                """
                SELECT 
                TransactionID,
                Type,
                SenderID,
                ReceiverID,
                DateTime,
                CASE 
                    WHEN ReceiverID = ? THEN CONCAT('+', Amt)
                    WHEN SenderID = ? THEN CONCAT('-', Amt)
                    ELSE Amt
                END AS FormattedAmt
                FROM Transactions
                WHERE SenderID = ? OR ReceiverID = ?
                ORDER BY DateTime DESC;""");
            stmt.setInt(1, Integer.parseInt(accManager.getUID()));
            stmt.setInt(2, Integer.parseInt(accManager.getUID()));
            stmt.setInt(3, Integer.parseInt(accManager.getUID()));
            stmt.setInt(4, Integer.parseInt(accManager.getUID()));
            transactions = db.selectQuery(stmt);
            while (transactions.next()){
                String transactionID = String.valueOf(transactions.getInt("TransactionID"));
                String transactionType = transactions.getString("Type");
                
                // Handling Account(s) field
                String sender = transactions.getString("SenderID");
                String receiver = transactions.getString("ReceiverID");
                sender = (sender == null ? accManager.getUID() == null : sender.equals(accManager.getUID()))?null:sender;
                receiver = (receiver == null ? accManager.getUID() == null : receiver.equals(accManager.getUID()))?null:receiver;
                String accounts = (sender == null ? "" : "From: " + sender) + 
                                  (receiver == null ? "" : " To: " + receiver);
                if (accounts.equals("")){
                    accounts = "Self";
                }
                String netAmt = transactions.getString("FormattedAmt"); // Prefixed with + or -
                netAmt = String.valueOf(netAmt).replaceAll("\\.?0+$", ""); 
                String dateTime = transactions.getTimestamp("DateTime").toString();

                // Add row to list
                data.add(new String[]{transactionID, transactionType, accounts, netAmt, dateTime});
            }
        } catch (SQLException e) {
            System.err.println("SQL Error! "+e);
        }
        
        JButton backButton = new HoverButton("Back","BackButton.png");

        backButton.setBounds(50,50,150,50);
        backButton.addActionListener(_->{

            //Initialise Pages.LoggedOut.java
            JPanel logInPage = new LoggedIn(accManager,root);

            
            //Set this page invisible and remove from JFrame
            setVisible(false);
            root.getContentPane().remove(this);

            //Add logoutpage to JFrame
            root.add(logInPage);
            logInPage.setVisible(true);
            //Update JFrame
            root.repaint();
        });

        // Column Names
        String[] columnNames = { "TransactionsID", "Transaction Type", "Account(s)", "Net Amt", "Date & Time" };
        JTable tTable = new JTable(data.toArray(new String[0][0]),columnNames);
        JScrollPane scroll = new JScrollPane(tTable);
        scroll.setBackground(Color.decode("#282649"));
        tTable.setEnabled(false);
        tTable.setRowHeight(40);
        tTable.setBackground(Color.decode("#282649")); // Dark purple background
        tTable.setForeground(Color.WHITE); // White text
        tTable.getColumnModel().getColumn(3).setCellRenderer(new AmountCellRenderer());
        tTable.setSelectionBackground(new Color(50, 44, 89)); // Selection color
        tTable.setSelectionForeground(Color.WHITE); // Selected text color
        tTable.setGridColor(new Color(90, 80, 160)); // Grid color
        tTable.setBounds(50, 300, 900, 180);
        scroll.setBounds(50, 300, 900, 180);
        
        JTableHeader header = tTable.getTableHeader();
        header.setBackground(Color.decode("#16152b")); // Header background
        header.setForeground(Color.WHITE); // Header text
        header.setFont(new Font("Arial", Font.BOLD, 14));
        
        add(scroll);
        add(backButton);
        BG.add(title,gbc);
        add(BG);
        setLayout(null);
        setSize(1000, 600);
        setVisible(true);
    }
}

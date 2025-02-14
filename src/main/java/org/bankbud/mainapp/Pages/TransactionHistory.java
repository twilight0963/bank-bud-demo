package org.bankbud.mainapp.Pages;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.bankbud.mainapp.Classes.Manager;

public class TransactionHistory extends JPanel{
    public TransactionHistory(Manager accManager,JFrame root){
        setBackground(Color.decode("#16152b"));
        setLayout(null);
        setSize(1000, 600);
        setVisible(true);

        JTable tTable = new JTable();
        add(tTable);
    }
}

package org.bankbud.mainapp.Classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class hoverButton extends JButton{



    public hoverButton(String text,String hex1, String hex2,String ic){
        //#6d58f4
        //#aea4f4
        Image icon = new ImageIcon("Resources/"+ic).getImage().getScaledInstance(45, 45,Image.SCALE_SMOOTH);
        Color usualBG = Color.decode(hex1);
        Color usualFG = Color.white;
        Color hoveredBG = Color.decode(hex2);
        Color hoveredFG = Color.black;
        setIcon(new ImageIcon(icon));
        setText(text);
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setOpaque(true);
        setBorderPainted(false);
        setFocusable(false);
        setBackground(usualBG);
        setForeground(usualFG);
        addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    setBackground(hoveredBG);
                    setForeground(hoveredFG);
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    setBackground(usualBG);
                    setForeground(usualFG);
                }
        });
    }
    public hoverButton(String text,String ic){
        this(text,"#5650a8","#736be5",ic);
    }
}
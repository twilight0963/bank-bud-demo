package org.bankbud.mainapp.Classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class HoverButton extends JButton{



    public HoverButton(String text,String hex1, String hex2,String ic){
        //#6d58f4
        //#aea4f4
        Image icon = new ImageIcon("src/main/java/org/bankbud/mainapp/Resources/"+ic).getImage().getScaledInstance(35, 35,Image.SCALE_SMOOTH);
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
    public HoverButton(String text,String ic){
        this(text,"#5650a8","#736be5",ic);
    }
}
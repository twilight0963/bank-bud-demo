package org.bankbud.mainapp.Classes;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

//Two field input box for the reusing purpose
//Second field is a password field
public class PromptBox{

    //Object variables
    public String ans1;
    public String ans2;
    public Boolean done = false;

        //initialisation function
        public PromptBox(String prompt,String f1, String f2){
            Image icon = new ImageIcon("Resources/Auth.png").getImage().getScaledInstance(75, 75,Image.SCALE_SMOOTH);
            JTextField field1 = new JTextField(5);
            JPasswordField field2 = new JPasswordField(5);
      
            //Root
            JPanel myPanel = new JPanel();

            //Field1
            myPanel.add(new JLabel(f1));
            myPanel.add(field1);

            //Spacer
            myPanel.add(Box.createHorizontalStrut(15));

            //Field2(Password field)
            myPanel.add(new JLabel(f2));
            myPanel.add(field2);
    
            int result = JOptionPane.showConfirmDialog(null, myPanel, 
            prompt, JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE,new ImageIcon(icon));
            if (result == JOptionPane.OK_OPTION) {

                //Upon clicking ok variables have their values applied
                this.done = true;
                this.ans1 = field1.getText();
                this.ans2 = String.valueOf(field2.getPassword());
                myPanel.setVisible(false);
                myPanel.invalidate();
        }
    }
}
package Classes;
import java.awt.Image;
import javax.swing.*;

//Triple input version of PrompBox
public class TripleBox{

    //Object variables
    public String ans1;
    public String ans2;
    public String ans3;
    public Boolean done = false;

        //initialisation function
        public TripleBox(String prompt, String f1, String f2, String f3){
            Image icon = new ImageIcon("Resources/Auth.png").getImage().getScaledInstance(75, 75,Image.SCALE_SMOOTH);
            JTextField field1 = new JTextField(5);
            JTextField field2 = new JTextField(5);
            JPasswordField field3 = new JPasswordField(5);
      
            //Root
            JPanel myPanel = new JPanel();

            //Field1
            myPanel.add(new JLabel(f1));
            myPanel.add(field1);

            //Spacer
            myPanel.add(Box.createHorizontalStrut(15));

            //Field 2
            myPanel.add(new JLabel(f2));
            myPanel.add(field2);

            //Spacer
            myPanel.add(Box.createHorizontalStrut(15));

            //Field 3
            myPanel.add(new JLabel(f3));
            myPanel.add(field3);

    
            int result = JOptionPane.showConfirmDialog(null, myPanel, 
            prompt, JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE,new ImageIcon(icon));
            if (result == JOptionPane.OK_OPTION) {
                //Upon clicking ok variables have their values applied
                this.done = true;
                this.ans1 = field1.getText();
                this.ans2 = field2.getText();
                this.ans3 = String.valueOf(field3.getPassword());
                myPanel.setVisible(false);
                myPanel.invalidate();
        }
    }
}


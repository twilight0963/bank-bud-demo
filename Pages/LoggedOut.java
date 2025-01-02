package Pages;
import Classes.Manager;
import Classes.PromptBox;
import Classes.hoverButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import javax.swing.*;


public class LoggedOut extends JPanel{
    public LoggedOut(Manager accManager,JFrame root){

        JPanel BG = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        BG.setBounds(10, 10, 980, 260);
        BG.setBackground(Color.decode("#282649"));
        JLabel title = new JLabel("BankBud");
        title.setFont(new Font("Segoe UI", Font.BOLD, 48));
        title.setForeground(Color.white);
        
        JButton SignUpButton = new hoverButton("Create New Account","SignUp.png");
            SignUpButton.addActionListener(_ -> {
                PromptBox prompt = new PromptBox("Sign Up Form","Name: ", "Password: ");
                if (prompt.done==true){
                    String result = accManager.signUp(prompt.ans2,prompt.ans1);
                    JOptionPane.showMessageDialog(this,result);
                }
            });

            JButton SignInButton = new hoverButton("Sign in!","SignIn.png");
            SignInButton.addActionListener(_ ->{
                PromptBox prompt = new PromptBox("Please Authenticate","Account Number: ", "Password: ");
                if (prompt.done){
                try {
                    int result = accManager.signIn(Integer.valueOf(prompt.ans1), prompt.ans2);
                    switch (result){
                        case 0 -> {
                            setVisible(false);
                            root.getContentPane().remove(this);
                            JPanel logInPage = new LoggedIn(accManager,root);
                            root.add(logInPage);
                            root.validate();
                            logInPage.validate();
                            JOptionPane.showMessageDialog(this,"Welcome, "+accManager.getOwner()+"!");
                        }
                        default -> JOptionPane.showMessageDialog(this,"Invalid Account Number or Password!");
                    }
                }
                catch (HeadlessException | NumberFormatException e) {
                    JOptionPane.showMessageDialog(this,"Unexpected error occured");
                }}
            });

            SignUpButton.setBounds(250,280,500,40);
            SignInButton.setBounds(250,350,500,40);

            setBackground(Color.decode("#16152b"));
            setLayout(null);
            BG.add(title,gbc);
            add(BG);
            add(SignUpButton);
            add(SignInButton); 
            setSize(1000, 600);
            setVisible(true);
    }
}
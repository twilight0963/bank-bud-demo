package org.bankbud.mainapp.Classes;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;



//Account controller class
public class Manager{
    //Hashtable contains account numbers linked with details
    DatabaseManager db = new DatabaseManager();
    //Account numbers are serialised

    //Currently saved password
    private Integer savedUID;
    private String savedPass;
    private Account savedAcc;
    public boolean signedIn = false;

    public Manager(){
    }

    private double truncate(double a){
        if (a >= 1000000000){
            return -1;
        }
        double factor = Math.pow(10,10);
        return Math.floor(a*factor) / factor;
    }

    private void updateDB(){
        //Update the DB details to the ones saved in variables
        String details[] = savedAcc.accDetails(savedPass);
        String stmt = String.format("""
        UPDATE Accounts
        SET balance = %s, password = %s  
        WHERE AccountID = %s""", details[1], savedPass, savedUID);
        db.execute(stmt);
    }

    //Create new account and map to db
    public String signUp(String password, String name){
        if (password.length() > 30){
            return "Password too long! Please choose a password with less than 30 characters!";
        }
        if (name.length() > 30){
            return "Name length exceeded! Please use Initials!";
        }
        try {
            String stmt = String.format("INSERT INTO Accounts (password, owner, Balance) VALUES ('%s', '%s', 0);", password, name);
            long accno = db.executeInsert(stmt);
            
            return "Account successfully created.\n Your Account number: " + (accno);
        } catch (Exception e) {
            return "Unexpected error occured";
        }
    }

    //Function to "save" current login information for quick comparasion
    public Integer signIn(Integer uid, String password){
        String stmt = "SELECT * FROM Accounts WHERE AccountID = ? AND password = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(stmt)) {
            pstmt.setInt(1,uid);
            pstmt.setString(2,password);
            ResultSet acc = db.selectQuery(pstmt);
            if (acc.next()){
                try {
                    this.signedIn = true;
                    this.savedUID = acc.getInt("AccountID");
                    this.savedPass = acc.getString("password");
                    String savedName = acc.getString("owner");
                    double savedBal = acc.getDouble("Balance");
                    
                    savedAcc = new Account(savedPass, savedName, savedBal);
                    return 0;
                } catch (SQLException e) {
                    return -2;
                }
                
            }
        } catch (SQLException e) {
            return -2;
        }
        return -1;
    }

    //Remove existing saved information
    public Integer signOut(){ 
        this.signedIn = false;
        this.savedUID = 0;
        this.savedPass = "";
        return 0;
    }

    //Check password and call function
    public double deposit(double amount,String pass){
        if (pass.equals(savedPass)){
            amount = truncate(amount);
            String stmt = String.format("INSERT INTO Transactions (type, ReceiverID, Amt) VALUES ('Deposit', '%s', %f);", this.savedUID, amount);
            db.executeInsert(stmt);
            return savedAcc.deposit(amount);
        }
        return -1;

    }

    //Password is not checked since it is passed onto account function
    public double withdraw(double amount,String pass){
            amount = truncate(amount);
            String stmt = String.format("INSERT INTO Transactions (type, SenderID, Amt) VALUES ('Withdraw', '%s', %f);", this.savedUID, amount);
            db.executeInsert(stmt);
            return savedAcc.withdraw(pass, amount);
    }


    //Grab current balance using saved password
    public String getBalance(){
        String[] details = savedAcc.accDetails(savedPass);
        updateDB();
        return details[1];
    }

    //Grab current account name from saved value
    public String getOwner(){
        String[] details = savedAcc.accDetails(savedPass);
        updateDB();
        return details[0];
    }

    //Returns current saved address
    public String getUID(){
        return String.valueOf(savedUID);
    }

    //Withdraw from current account and deposit to another
    public double sendBal(int address, double amount ,String pass){
        amount = truncate(amount);
        if (Objects.equals(address, savedUID)){
            return -4;
        }
        Account acc1 = savedAcc;
        double sendBal = acc1.withdraw(pass, amount);
        if (sendBal>=0){
            //select other acc
            String selStmt = "SELECT * FROM Accounts WHERE AccountID = ?";
            PreparedStatement pstmt;
            try {
                pstmt = db.getConnection().prepareStatement(selStmt);
                pstmt.setInt(1,address);
            } catch (SQLException e) {
                return -2;
            }
            
            ResultSet acc2 = db.selectQuery(pstmt);
            try {
                if (acc2.next()){
                    try {
                        //change balance
                        double bal = acc2.getDouble("Balance");
                        String stmt = String.format("""
                            UPDATE Accounts
                            SET balance = %s 
                            WHERE AccountID =%d""", amount+bal, address);
                        db.execute(stmt);
                        String stmt2 = String.format("INSERT INTO Transactions (type, ReceiverID, SenderID, Amt) VALUES ('Transfer', '%s', '%s', %f);",  address, this.savedUID, amount);
                        db.executeInsert(stmt2);
                    } catch (SQLException e) {
                        return -1;
                    }
                    return 0;
                }
            } catch (SQLException e) {
                return -2;
            }
            return -3;
        }
        return sendBal;
    }

    public Integer changePass(String oldPass, String pass,String confirmPass){
        if (pass.equals(confirmPass)){
            int x = savedAcc.changePassword(oldPass, pass);
            if (x==0){
                savedPass = pass;
                updateDB();
            }
            return x;
        }
        return -5;
    }
}
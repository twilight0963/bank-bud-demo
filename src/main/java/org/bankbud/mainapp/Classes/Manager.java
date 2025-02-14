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

    private void updateDB(){
        String details[] = savedAcc.accDetails(savedPass);
        String stmt = String.format("""
        UPDATE Accounts
        SET balance = %s 
        WHERE AccountID = %s""", details[1], savedUID);
        db.execute(stmt);
    }

    //Create new account and map to hash
    public String signUp(String password, String name){
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
                this.signedIn = true;
                try {
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
        updateDB();
        return 0;
    }

    //Check password and call function
    public double deposit(double amount,String pass){
        if (pass.equals(savedPass)){
            return savedAcc.deposit(amount);
        }
        return -1;

    }

    //Password is not checked since it is passed onto account function
    public double withdraw(double amount,String pass){
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
        if (Objects.equals(address, savedUID)){
            return -4;
        }
        Account acc1 = savedAcc;
        double sendBal = acc1.withdraw(pass, amount);
        if (sendBal>=0){
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
                        double bal = acc2.getDouble("Balance");
                        String stmt = String.format("""
                            UPDATE Accounts
                            SET balance = %s 
                            WHERE AccountID =%d""", amount+bal, address);
                        db.execute(stmt);

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
            return savedAcc.changePassword(oldPass, pass);
        }
        return -5;
    }
}
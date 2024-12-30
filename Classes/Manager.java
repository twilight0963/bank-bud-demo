package Classes;
import java.util.HashMap;
import java.util.Objects;
//import org.json.JSONObject;


//Account controller class
public class Manager{
    //Hashtable contains account numbers linked with details
    private final HashMap<Integer,Account> accounts = new HashMap<>();
    //Account numbers are serialised
    private int accno = 10001;

    //Currently saved password
    private Integer savedUID;
    private String savedPass;
    public boolean signedIn = false;

    public Manager(){
        //TODO
        //Read database
    }

    //Create new account and map to hash
    public String signUp(String password, String name){
        try {
            Account acc = new Account(password,name);
            this.accounts.put(accno,acc);
            this.accno++;
            return "Account successfully created.\n Your Account number: " + (accno-1);
        } catch (Exception e) {
            return "Unexpected error occured";
        }
    }

    //Function to "save" current login information for quick comparasion
    public Integer signIn(Integer uid, String password){
        Account acc = accounts.get(uid);
        if (acc!=null && acc.checkPass(password)){
            this.signedIn = true;
            this.savedPass = password;
            this.savedUID = uid;
            return 0;
        }
        return -1;
    }

    //Remove existing saved information
    public Integer signOut(){ 
        //TODO
        //Add objects to database
        this.signedIn = false;
        this.savedUID = 0;
        this.savedPass = "";
        return 0;
    }

    //Check password and call function
    public Integer deposit(Integer amount,String pass){
        Account acc = accounts.get(savedUID);
        if (pass.equals(savedPass)){
            return acc.deposit(amount);
        }
        return -1;

    }

    //Password is not checked since it is passed onto account function
    public Integer withdraw(Integer amount,String pass){
            Account acc = accounts.get(savedUID);
            return acc.withdraw(pass, amount);
    }


    //Grab current balance using saved password
    public String getBalance(){
        Account acc = accounts.get(savedUID);
        String[] details = acc.accDetails(savedPass);
        return details[1];
    }

    //Grab current account name from saved value
    public String getOwner(){
        Account acc = accounts.get(savedUID);
        String[] details = acc.accDetails(savedPass);
        return details[0];
    }

    //Returns current saved address
    public String getUID(){
        return String.valueOf(savedUID);
    }

    //Withdraw from current account and deposit to another
    public Integer sendBal(Integer address, Integer amount ,String pass){
        if (Objects.equals(address, savedUID)){
            return -4;
        }
        Account acc1 = accounts.get(savedUID);
        int sendBal = acc1.withdraw(pass, amount);
        if (sendBal>=0){
            Account acc2 = accounts.get(address);
            if (acc2!=null){
                acc2.deposit(amount);
                return 0;
            }
            return -3;
        }
        return sendBal;
    }

    public Integer changePass(String oldPass, String pass,String confirmPass){
        Account acc = accounts.get(savedUID);
        if (pass.equals(confirmPass)){
            return acc.changePassword(oldPass, pass);
        }
        return -5;
    }
}
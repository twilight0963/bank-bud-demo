package Classes;
//class for holding account variable
public class Account{
    
    private String pass;
    private final String owner;
    private int bal;
        
    //init new account

    public Account(String pass,String name){
        //Capitalise first letter
        String capName = name.substring(0, 1).toUpperCase() + name.substring(1);

        //set variables
        this.owner = capName;
        this.pass = pass;
        this.bal = 0;
    }

    public boolean checkPass(String password){
        return this.pass.equals(password);
    }

    //ADD to balance and return newBalance

    public int deposit(Integer amt){
        if (amt<0){
            return -5;
        }
        
        int curBal = this.bal;
        this.bal = curBal+amt;
        return this.bal;
            
    }

    //REMOVE from balance and return newBalance

    public int withdraw(String pass, int amt){
        if (pass.equals(this.pass)){
            if (amt<0){
                return -5;
            }
            
            int curBal = this.bal;
            if (amt<=curBal){
            this.bal = curBal-amt;
            return this.bal;
            }else{
                return -2;
            }
        }
        return -1; 
    }

    //return account name [0] and current balance [1]

    public String[] accDetails(String pass){
            String[] details = new String[]{this.owner,String.valueOf(this.bal)};
            return details;
        
    }

    //replace password

    public int changePassword(String pass,String newPass){
        if (pass.equals(this.pass)){
            this.pass = newPass;
            return 0;
        }
        return -1;
    }
}
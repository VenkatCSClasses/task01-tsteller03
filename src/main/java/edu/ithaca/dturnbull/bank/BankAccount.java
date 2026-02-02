package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * @throws InsufficientFundsException if amount is negative
     * @throws InsufficientFundsException if amount is larger than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount <= balance && amount > 0){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }


    public static boolean isEmailValid(String email){
        if (email.indexOf('@') == -1
                || email.indexOf('@') != email.lastIndexOf('@')
                || email.indexOf('@') == 0
                || email.indexOf('@') == email.length() - 1
                || email.indexOf(' ') != -1
                || email.indexOf('.') == -1
                || email.indexOf('.') == 0
                || email.indexOf('.') == email.length() - 1
                || email.indexOf('.') == email.indexOf('@') - 1
                || email.indexOf('.') == email.indexOf('@') + 1
                || email.lastIndexOf('.') >= email.length() - 2
                || email.indexOf("..") != -1
                || email.indexOf('#') != -1
                || email.indexOf('!') != -1
                || email.indexOf('$') != -1
                || email.indexOf('%') != -1
                || email.indexOf('^') != -1
                || email.indexOf('&') != -1
                || email.indexOf('*') != -1
                || email.indexOf('(') != -1
                || email.indexOf(')') != -1
                || email.indexOf('+') != -1
                || email.indexOf('=') != -1
                || email.indexOf('{') != -1
                || email.indexOf('}') != -1
                || email.indexOf('[') != -1
                || email.indexOf(']') != -1
                || email.indexOf('~') != -1
                || email.indexOf('`') != -1
                || email.indexOf("'") != -1
                || email.indexOf('"') != -1
                || email.indexOf('?') != -1
                || email.indexOf('-') == email.indexOf('@') - 1
                || email.indexOf('-') == email.indexOf('@') + 1
                || email.indexOf('-') == email.length() - 1
                || email.indexOf('-') == 0
                || email.indexOf("--") != -1
                || email.indexOf('_') == email.indexOf('@') - 1
                || email.indexOf('_') == email.indexOf('@') + 1
                || email.indexOf('_') == email.length() - 1
                || email.indexOf('_') == 0
                || email.indexOf("__") != -1
                || email.substring(email.indexOf('@')).indexOf('.') == -1
                || email.equals(null)
                )
                {
            return false;
        }
        else {
            return true;
        }
    }
}
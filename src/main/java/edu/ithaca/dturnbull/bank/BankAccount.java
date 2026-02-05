package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email and/or balance is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email) && isAmountValid(startingBalance)) {
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException();
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
     * @throws IllegalArgumentException if amount is negative or has unacceptable amount of decimal places
     * @throws InsufficientFundsException if amount is larger than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount > balance) {
            throw new InsufficientFundsException("Not enough money");
        } else if (amount <= balance && isAmountValid(amount)) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Invalid amount");
        }
    }

    /**
     * @return true if amount is positive and at most two decimal places, and false if otherwise
     */
    public static boolean isAmountValid(double amount) {
        //Check if amount is negative
        if (amount < 0) {
            return false;
        }

        String amountString = String.valueOf(amount);  
        int amountLength = amountString.length();
        int decimalIndex = amountString.indexOf('.');

        //If there is no decimal value, or only one or two decimal places, automatically return true
        if (decimalIndex == -1) { 
            return true;
        } else if (amountLength - decimalIndex-1 == 1 || amountLength - decimalIndex-1 == 2) {
            return true;
        } else {
            //If not, check if every digit beyond the second decimal place is 0 - return false if not
            for (int i = decimalIndex+2; i <= amountLength; i++) {
                if(amountString.charAt(i) != '0') {
                    return false;
                }
            }
        }
          
        return true;
    }

    public static boolean isEmailValid(String email){
        //must not be blank
        if (email == null || email.equals("")) {
            return false;
        }

        //must have one and only one @ symbol
        int atCount = 0;
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@') {
                atCount++;
            }
        }

        if (atCount != 1) {
            return false;
        }

        //split prefix and domain for specific checking
        int atSymbol = email.indexOf('@');
        String prefix = "";
        String domain = "";

        for (int i = 0; i < email.length(); i++) {
            if (i < atSymbol) {
                prefix += email.charAt(i);
            } else if (i > atSymbol) {
                domain += email.charAt(i);
            }
        }

        //prefix and domain must not be empty
        if (prefix.length() == 0 || domain.length() == 0) {
            return false;
        }
        
        //no ".." anywhere
        for (int i = 0; i < email.length() - 1; i++) {
            if (email.charAt(i) == '.' && email.charAt(i + 1) == '.') {
                return false;
            }
        }

        //no "--" anywhere
        for (int i = 0; i < email.length() - 1; i++) {
            if (email.charAt(i) == '-' && email.charAt(i + 1) == '-') {
                return false;
            }
        }

        //no "__" anywhere
        for (int i = 0; i < email.length() - 1; i++) {
            if (email.charAt(i) == '_' && email.charAt(i + 1) == '_') {
                return false;
            }
        }

        //prefix start & end character rules (., -, _ can't be first or last)
        char prefixFirst = prefix.charAt(0);
        char prefixLast = prefix.charAt(prefix.length() - 1);
        if (prefixFirst == '.' || prefixFirst == '-' || prefixFirst == '_' ||
            prefixLast == '.' || prefixLast == '-' || prefixLast == '_') {
            return false;
        }

        //domain start & end character rules (., -, _, @ can't be first or last)
        char domainFirst = domain.charAt(0);
        char domainLast = domain.charAt(domain.length() - 1);
        if (domainFirst == '.' || domainFirst == '-' || domainFirst == '_' || domainFirst == '@' ||
            domainLast == '.' || domainLast == '-' || domainLast == '_' || domainLast == '@') {
            return false;
        }

        //must have at least two characters following the dot in the domain
        int lastDot = -1;
        for (int i = 0; i < domain.length(); i++) {
            if (domain.charAt(i) == '.') {
                lastDot = i;
            }
        }

        if (lastDot == -1 || domain.length() - lastDot - 1 < 2) {
            return false;
        }

        //validate characters in prefix, ensure no special characters
        for (int i = 0; i < prefix.length(); i++) {
            char current = prefix.charAt(i);
            if ((current < 'a' || current > 'z') &&
                (current < 'A' || current > 'Z') &&
                (current < '0' || current > '9') &&
                (current != '.' && current != '_' && current != '-')) {
                    return false;
                }
        }

        //validate characters in domain, ensure no special characters
        for (int i = 0; i < domain.length(); i++) {
            char current = domain.charAt(i);
            if ((current < 'a' || current > 'z') &&
                (current < 'A' || current > 'Z') &&
                (current < '0' || current > '9') &&
                (current != '.' && current != '_' && current != '-')) {
                    return false;
                }
        }

        return true;
    }
}
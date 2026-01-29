package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void isEmailValidTest(){
        //valid addresses
        assertTrue(BankAccount.isEmailValid( "a@b.com")); // Equivalence class: simple valid email (border case: minimum number of characters in prefix and domain)
        assertTrue(BankAccount.isEmailValid("abc-d@mail.com")); // Equivalence class: valid email with hyphen
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com"));  // Equivalence class: valid email with period
        assertTrue(BankAccount.isEmailValid("abc_def@mail.com")); // Equivalence class: valid email with underscore

        //valid domains
        assertTrue(BankAccount.isEmailValid("abc.def@mail.cc")); // Equivalence class: valid domain with two-letter suffix
        assertTrue(BankAccount.isEmailValid("abc.def@mail-archive.com")); // Equivalence class: valid domain with hyphen
        assertTrue(BankAccount.isEmailValid("abc.def@mail.org")); // Equivalence class: valid domain with .org suffix
        assertTrue(BankAccount.isEmailValid("abc.def@mail.net")); // Equivalence class: valid domain with .net suffix

        //invalid addresses
        assertFalse(BankAccount.isEmailValid("")); // Equivalence class: empty string
        assertFalse(BankAccount.isEmailValid("abc-@mail.com")); // Equivalence class: hyphen at end of handle
        assertFalse(BankAccount.isEmailValid("abc..def@mail.com")); // Equivalence class: consecutive periods in handle (border case: minimum number of invalid periods)
        assertFalse(BankAccount.isEmailValid(".abc@mail.com")); // Equivalence class: period at start of handle
        assertFalse(BankAccount.isEmailValid("abc#def@mail.com")); // Equivalence class: invalid character in handle (border case: minimum number of invalid special characters)

        //invalid domains
        assertFalse(BankAccount.isEmailValid("abc.def@mail.c")); // Equivalence class: domain with one-letter suffix
        assertFalse(BankAccount.isEmailValid("abc.def@mail#archive.com")); // Equivalence class: inavlid character in domain
        assertFalse(BankAccount.isEmailValid("abc.def@mail")); // Equivalence class: domain with no suffix
        assertFalse(BankAccount.isEmailValid("abc.def@mail..com")); // Equivalence class: consecutive periods in domain (border case: minimum number of invalid periods)
    
        // Missing equivalency cases: space in email, multiple @ symbols, @ at beginning or end, . at beginning or end, . next to @
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}
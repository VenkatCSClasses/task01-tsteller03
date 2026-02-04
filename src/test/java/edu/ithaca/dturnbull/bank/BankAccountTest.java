package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance(), 0.001); //equivalence class - positive amount

        BankAccount bankAccount2 = new BankAccount("a@b.com", 74.29);
        assertEquals(74.29, bankAccount2.getBalance(), 0.001); //equivalence class - positive amount

        BankAccount bankAccount3 = new BankAccount("a@b.com", 0);
        assertEquals(0, bankAccount3.getBalance(), 0.001); //equivalence class - balance zero

        BankAccount bankAccount4 = new BankAccount("a@b.com", -200);
        assertEquals(-200, bankAccount4.getBalance(), 0.001); //equivalence class - negative amount

        BankAccount bankAccount5 = new BankAccount("a@b.com", -74.29);
        assertEquals(-74.29, bankAccount5.getBalance(), 0.001); //equivalence class - negative amount
    }

    @Test
    void isAmountValidTest() {
        //equivalence class - positive, zero decimal places
        assertTrue(BankAccount.isAmountValid(100)); //middle case - expected amount
        assertTrue(BankAccount.isAmountValid(1)); //middle case - expected amount
        assertTrue(BankAccount.isAmountValid(0)); //border case - minimum amount

        //equivalence class - positive, one decimal place
        assertTrue(BankAccount.isAmountValid(91.4)); //middle case - expected amount
        assertTrue(BankAccount.isAmountValid(1000.0)); //middle case - expected amount
        assertTrue(BankAccount.isAmountValid(0.0)); //border case - minimum amount
        assertTrue(BankAccount.isAmountValid(0.1)); //border case - minimum amount that is non-zero

        //equivalence class - positive, two decimal places
        assertTrue(BankAccount.isAmountValid(68.23)); //middle case - expected amount
        assertTrue(BankAccount.isAmountValid(68.20)); //middle case - expected amount
        assertTrue(BankAccount.isAmountValid(0.00)); //border case - minimum amount
        assertTrue(BankAccount.isAmountValid(0.01)); //border case - minimum amount that is non-zero

        //equivalence class - positive, more than two decimal places - only zeroes beyond 2nd decimal place
        assertTrue(BankAccount.isAmountValid(31.000)); //middle case - expected amount
        assertTrue(BankAccount.isAmountValid(25.7300)); //middle case - expected amount
        assertTrue(BankAccount.isAmountValid(197328.0600000)); //middle case - expected amount
        assertTrue(BankAccount.isAmountValid(748.1000)); //middle case - expected amount
        assertTrue(BankAccount.isAmountValid(1.0000000000000000)); //border case - minimum amount (any form of 0s following)

        //equivalence class - negative, zero decimal places
        assertFalse(BankAccount.isAmountValid(-100)); //middle case - expected amount
        assertFalse(BankAccount.isAmountValid(-1)); //border case - minimum amount

        //equivalence class - negative, one decimal place
        assertFalse(BankAccount.isAmountValid(-91.4)); //middle case - expected amount
        assertFalse(BankAccount.isAmountValid(-0.1)); //border case - minimum amount

        //equivalence class - negative, two decimal places
        assertFalse(BankAccount.isAmountValid(-68.23)); //middle case - expected amount
        assertFalse(BankAccount.isAmountValid(-68.20)); //middle case - expected amount
        assertFalse(BankAccount.isAmountValid(-0.01)); //border case - minimum amount

        //equivalence class - negative, more than two decimal places
        assertFalse(BankAccount.isAmountValid(-68.200000)); //middle case - expected amount
        assertFalse(BankAccount.isAmountValid(-1.047)); //middle case - expected amount
        assertFalse(BankAccount.isAmountValid(-43.0009)); //middle case - expected amount
        assertFalse(BankAccount.isAmountValid(-0.00000000001)); //border case - minimum amount (in practice)

        //equivalence class - positive, more than two decimal places - not only zeroes beyond 2nd decimal place
        assertFalse(BankAccount.isAmountValid(49.2187415)); //middle case - expected amount
        assertFalse(BankAccount.isAmountValid(81.09600)); //middle case - expected amount
        assertFalse(BankAccount.isAmountValid(124.1507)); //middle case - expected amount
        assertFalse(BankAccount.isAmountValid(31.0049)); //middle case - expected amount
        assertFalse(BankAccount.isAmountValid(1.005)); //middle case - expected amount
        assertFalse(BankAccount.isAmountValid(1.000000000000001)); //border case - minimum amount of zeroes before automatic rounding
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        bankAccount.withdraw(100); //equivalence class - amount is positive
        assertEquals(100, bankAccount.getBalance(), 0.001); //middle case - expected amount

        bankAccount.withdraw(64.10); //equivalence class - amount is positive
        assertEquals(35.90, bankAccount.getBalance(), 0.001); //middle case - expected amount

        bankAccount.withdraw(0.01); //equivalence class - amount is positive
        assertEquals(35.89, bankAccount.getBalance(), 0.001); //border case - minimum amount

        //equivalence class - amount exceeds balance
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(bankAccount.getBalance() + 0.01)); //border case - minimum amount

        //equivalence class - amount exceeds balance
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(63.72)); //middle case - greater than balance

        //equivalence class - amount is negative
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(-0.01)); //border case - minimum amount
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(-18.44)); //middle case - less than balance
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(-95)); //middle case - greater than balance

        bankAccount.withdraw(35.89); //border case - maximum amount
        assertEquals(0, bankAccount.getBalance(), 0.001);

        //equivalence case - amount exceeds balance
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(0.01)); //border case - minimum amount when balance 0
    }

    @Test
    void isEmailValidTest(){
        //valid addresses - border cases
        assertTrue(BankAccount.isEmailValid( "a@mail.com")); // Equivalence class: simple valid email with minimum address length
        assertTrue(BankAccount.isEmailValid( "a@b.com")); // Equivalence class: simple valid email with minimum address and domain length
        assertTrue(BankAccount.isEmailValid( "abc@b.com")); // Equivalence class: simple valid email with minimum domain length

        //valid addresses - all middle cases, expected input
        assertTrue(BankAccount.isEmailValid("abc-d@mail.com")); // Equivalence class: valid email with hyphen
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com"));  // Equivalence class: valid email with period
        assertTrue(BankAccount.isEmailValid("abc_def@mail.com")); // Equivalence class: valid email with underscore
        assertTrue(BankAccount.isEmailValid("a-bc.def@mail.com")); // Equivalence class: valid email with hyphen and period
        assertTrue(BankAccount.isEmailValid("ab.cde_f@mail.com")); // Equivalence class: valid email with period and underscore
        assertTrue(BankAccount.isEmailValid("abc-de_f@mail.com")); // Equivalence class: valid email with hyphen and underscore
        assertTrue(BankAccount.isEmailValid("ab.cd-e_f@mail.com")); // Equivalence class: valid email with hyphen, period, and underscore

        //valid domains - all middle cases, expected input
        assertTrue(BankAccount.isEmailValid("abc.def@mail.cc")); // Equivalence class: valid domain with two-letter suffix
        assertTrue(BankAccount.isEmailValid("abc.def@mail-archive.com")); // Equivalence class: valid domain with hyphen
        assertTrue(BankAccount.isEmailValid("abc.def@mail.org")); // Equivalence class: valid domain with .org suffix
        assertTrue(BankAccount.isEmailValid("abc.def@mail.net")); // Equivalence class: valid domain with .net suffix

        //invalid addresses - missing info
        assertFalse(BankAccount.isEmailValid("")); // Equivalence class: empty string
        // assertFalse(BankAccount.isEmailValid(null)); // Equivalence class: null
        assertFalse(BankAccount.isEmailValid("@mail.com")); // Equivalence class: no address

        //invalid addresses - start & ending symbols
        assertFalse(BankAccount.isEmailValid("-abc@mail.com")); // Equivalence class: hyphen at start of handle
        assertFalse(BankAccount.isEmailValid("abc-@mail.com")); // Equivalence class: hyphen at end of handle
        assertFalse(BankAccount.isEmailValid(".abc@mail.com")); // Equivalence class: period at start of handle
        assertFalse(BankAccount.isEmailValid("abc.@mail.com")); // Equivalence class: period at end of handle
        assertFalse(BankAccount.isEmailValid("_abc@mail.com")); // Equivalence class: underscore at start of handle
        assertFalse(BankAccount.isEmailValid("abc_@mail.com")); // Equivalence class: underscore at end of handle

        //invalid addresses - consecutive symbols (all border cases, minimum # of consecutive symbols)
        assertFalse(BankAccount.isEmailValid("abc..def@mail.com")); // Equivalence class: consecutive periods
        assertFalse(BankAccount.isEmailValid("abcde--f@mail.com")); // Equivalence class: consecutive hyphens
        assertFalse(BankAccount.isEmailValid("ab__cdef@mail.com")); // Equivalence class: consecutive underscores

        //invalid addresses - consecutive symbols (all middle cases, 3 consecutive symbols)
        assertFalse(BankAccount.isEmailValid("abc...def@mail.com")); // Equivalence class: consecutive periods
        assertFalse(BankAccount.isEmailValid("abcde---f@mail.com")); // Equivalence class: consecutive hyphens
        assertFalse(BankAccount.isEmailValid("ab___cdef@mail.com")); // Equivalence class: consecutive underscores

        //invalid addresses - prohibited symbols (all border cases, minimum # of invalid special characters)
        assertFalse(BankAccount.isEmailValid("abc#def@mail.com")); // Equivalence class: #
        assertFalse(BankAccount.isEmailValid("a!bcdef@mail.com")); // Equivalence class: !
        assertFalse(BankAccount.isEmailValid("abcd$ef@mail.com")); // Equivalence class: $
        assertFalse(BankAccount.isEmailValid("abc^def@mail.com")); // Equivalence class: ^
        assertFalse(BankAccount.isEmailValid("abcde+f@mail.com")); // Equivalence class: +
        assertFalse(BankAccount.isEmailValid("abcd=ef@mail.com")); // Equivalence class: =
        assertFalse(BankAccount.isEmailValid("a~bcdef@mail.com")); // Equivalence class: ~
        assertFalse(BankAccount.isEmailValid("abc?def@mail.com")); // Equivalence class: ?
        assertFalse(BankAccount.isEmailValid("abcd'ef@mail.com")); // Equivalence class: '
        assertFalse(BankAccount.isEmailValid("abcde@f@mail.com")); // Equivalence class: @
        assertFalse(BankAccount.isEmailValid("a%bcdef@mail.com")); // Equivalence class: %
        assertFalse(BankAccount.isEmailValid("abc*def@mail.com")); // Equivalence class: *

        //invalid addresses - prohibited symbols (all middle cases, 2 invalid special characters)
        assertFalse(BankAccount.isEmailValid("ab$c#def@mail.com")); // Equivalence class: prohibited symbols
        assertFalse(BankAccount.isEmailValid("ab$$cdef@mail.com")); // Equivalence class: prohibited symbols
        assertFalse(BankAccount.isEmailValid("!abcdef*@mail.com")); // Equivalence class: prohibited symbols
        assertFalse(BankAccount.isEmailValid("abcde?f?@mail.com")); // Equivalence class: prohibited symbols

        //invalid domains - missing info
        assertFalse(BankAccount.isEmailValid("abc@")); // Equivalence class: no domain or suffix
        assertFalse(BankAccount.isEmailValid("abc@.com")); // Equivalence class: no domain
        assertFalse(BankAccount.isEmailValid("abc.def@mail")); // Equivalence class: domain with no suffix

        //invalid domains - suffix length (border cases, minimum suffix length)
        assertFalse(BankAccount.isEmailValid("abc.def@mail.")); // Equivalence class: domain with zero-letter suffix
        assertFalse(BankAccount.isEmailValid("abc.def@mail.c")); // Equivalence class: domain with one-letter suffix

        //invalid domains - prohibited symbols (all border cases, minimum # of invalid special characters)
        assertFalse(BankAccount.isEmailValid("abc.def@mail#archive.com")); // Equivalence class: #
        assertFalse(BankAccount.isEmailValid("abc.def@ma!l.com")); // Equivalence class: !
        assertFalse(BankAccount.isEmailValid("abc.def@m$ail.com")); // Equivalence class: $

        //invalid domains - consecutive symbols (border cases, minimum number of consecutive symbols)
        assertFalse(BankAccount.isEmailValid("abc.def@mail..com")); // Equivalence class: consecutive periods in domain
        assertFalse(BankAccount.isEmailValid("abc.def@mail--archive.com")); // Equivalence class: consecutive hyphens in domain
        assertFalse(BankAccount.isEmailValid("abc@@mail.com")); // Equivalence class: consecutive @ symbols

        //spaces in email - all are equivalence classes and border cases, minimum number of spaces for address to be invalid
        assertFalse(BankAccount.isEmailValid(" abc@mail.com")); 
        assertFalse(BankAccount.isEmailValid("a bc@mail.com")); 
        assertFalse(BankAccount.isEmailValid("abc @mail.com")); 
        assertFalse(BankAccount.isEmailValid("abc@ mail.com")); 
        assertFalse(BankAccount.isEmailValid("abc@m ail.com")); 
        assertFalse(BankAccount.isEmailValid("abc@mail .com")); 
        assertFalse(BankAccount.isEmailValid("abc@mail. com")); 
        assertFalse(BankAccount.isEmailValid("abc@mail.co m")); 
        assertFalse(BankAccount.isEmailValid("abc@mail.com ")); 

        //spaces in email - all middle cases, two spaces
        assertFalse(BankAccount.isEmailValid(" a bc@mail.com")); 
        assertFalse(BankAccount.isEmailValid("a  bc@mail.com")); 
        assertFalse(BankAccount.isEmailValid("abc @ma il.com")); 
        assertFalse(BankAccount.isEmailValid("abc@  mail.com")); 
        assertFalse(BankAccount.isEmailValid("abc@m ai l.com")); 
        assertFalse(BankAccount.isEmailValid("abc@ma il.co m")); 
        assertFalse(BankAccount.isEmailValid(" abc@mail. com")); 
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
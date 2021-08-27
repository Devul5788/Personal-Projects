package com.example.accountapp.logic;

import com.example.accountapp.ui.OutputInterface;

import static java.util.Objects.hash;

/**
 * This file defines the Account class.  It provides the basis for a
 * series of improvements you'll need to make as you progress through
 * the lessons in Module 6.
 */
public class Account {
    /**
     * This is the variable that stores our OutputInterface instance.
     * <p/>
     * This is how we will interact with the User Interface
     * [MainActivity.java].
     * </p>
     * This was renamed to 'mOut' from 'out', as it is in the video
     * lessons, to better match Android/Java naming guidelines.
     */
    final OutputInterface mOut;

    /**
     * Name of the account holder.
     */
    private String name;

    /**
     * Number of the account.
     */
    private String number;

    /**
     * Current balance in the account.
     */
    private double balance;

    private static int count;


    /**
     * Constructor initializes the field
     */

    public Account(OutputInterface out, String newName)  {
        mOut = out;
        name = newName;
    }

    public Account(OutputInterface out, String newName, double initialBalance) {
        this(out, newName);
        balance = initialBalance;
    }



    /**
     * Deposit @a amount into the account.
     */
    public String getName(){
        return name;
    }

    public String getAccountNumber(){
        return number;
    }

    public double getBalance(){
        return balance;
    }

    public void setName(String nm){
        name = nm;
    }


    public void deposit(double amount) {
        balance += amount;
    }

    /**
     * Withdraw @a amount from the account.  Prints "Insufficient
     * Funds" if there's not enough money in the account.
     */
    public void withdrawal(double amount) {
        if (balance > amount)
            balance -= amount;
        else 
            mOut.println("Insufficient Funds");
    }

    /**
     * Display the current @a amount in the account.
     */
    public void displayBalance() {
        mOut.println("The balance on account " 
                     + number
                     + " is " 
                     + balance);
    }

    public String toString(){
        return("Account Holder " + getName() + ", " + getAccountNumber());
    }

    public boolean equals (Object other){
        if(other instanceof Account){
            Account otheracct = (Account)other;
            return number==otheracct.number;
        }
        else {
            return false;
        }
    }

}

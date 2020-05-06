package com.example.makingit.softchatapp;

/*
    Used to create individual user objects composed of name a network id(probably ip), and either the key pair the user will have or if its another member of the chat just a public key
    private variables
        name - how the user is seen in chat
        ip - what ip address messages will be sent to
        publicKey - the users public key used to encrypt messages
    methods
        public constructors
        getUserName() - returns users name as a string
        getIP() - returns the users IP as a string
        getPublicUserProfile() - returns the user profile as a string array to be sent to other users with the app running
        verifyUserData() - validate input to see if its a valid user profile, returns an int for different exit status
        compare() - boolean value that checks username and ip of two users to see if its the same
 */


public class User{

    private String name, ip;//self explanatory
    //private String publicKey;//the users public key to be sent out to other users, placeholder for actual public key var

    //default user constructor
    public User(){
        this.name = "User";
        this.ip = "0.0.0.0";
        //this.publicKey = "Default Public Key";
    }
    //specified user constructor
    public User(String n, String i/*, String k*/){
        this.name = n;
        this.ip = i;
        //this.publicKey = k;
    }

    public String getUserName(){
        return this.name;
    }

    public String getIP(){
        return this.ip;
    }

    public String[] getPublicUserProfile(){
        return new String[] {this.name, this.ip/*, this.publicKey*/};
    }

    public static int verifyUserData(String[] U){
        //TODO check the first index of the user array to make sure its a valid name
        String[] badChars = {".",",",":","?","<",">","{","[","}","]","!","@"};
        for(int i =0; i < badChars.length; i++)
        {
            if(U[0].contains(badChars[i]))
            {
                return 1;//failed for the username having a forbidden character
            }
        }

        //check the second index to make sure its a valid ip
        String[] ipnums = U[1].split(".");
        if(ipnums.length != 4){
            return 2;//failed for being the wrong format
        }else{
            for(int i=0; i<4; i++){
                try {
                    int toCheck = Integer.parseInt(ipnums[i]);
                    if((toCheck > 255) || (toCheck < 0)) { return 3; }//fails for having invalid ip number
                } catch(Exception e){
                    return 4;//fails for ip containing non integers
                }
            }
        }
        return 0;//success
    }//ends verifyUserData()

    public boolean compare(User U){
        if (this.name.equals(U.name) && this.ip.equals(U.ip)){ return true;}
        return false;
    }

    public String toString(){
        return "User: " + this.name + " | Address: " + this.ip;
    }
}//end User class

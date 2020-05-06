package com.example.makingit.softchatapp;

import java.util.ArrayList;

/*
        Keeps track of all the other users in the chat holding each other users public profile.
        This gives it the public keys to encrypt messages, the name to identify users and the ip for where to send messages
private variables
        userList - the list of users
        methods
default constructor - builds the empty table if there is no one else using the app around
        existing table constructor - builds the table if other users are already around with an existing userlist
        addUser() - add user profiles to the table returns boolean for status
        addFromList() - adds users from an existing list
        searchUser() - looks through the userList to find a specific user and returns the index for that user as an int, if no user is found returns -1
        removeUser() - removes user from the list if they leave the chat returns boolean for status
        */


public class UserList {

    private ArrayList<User> userList;

    public UserList(){
        this.userList = new ArrayList();
    }

    //TODO verify user doesn't already exist
    public boolean addUser(User U){
        try {
            if(User.verifyUserData(U.getPublicUserProfile()) == 0){
                userList.add(U);
                return true;
            }
            return false;
        } catch(Exception E){
            return false;
        }
    }//end addUser()

    public void addFromList(ArrayList L){
        userList.removeAll(L);//removes duplicates from L
        userList.addAll(L);//adds remaining users in L to userList
    }//end addFromList()

    public int searchUser(User U){
        for(User temp: userList){
            if(U.compare(temp)){
                return userList.indexOf(temp);
            }
        }
        return -1;
    }//end searchUser()

    public boolean removeUser(int index){
        try{
            userList.remove(index);
            return true;
        }catch(Exception e){
            return false;
        }
    }//end removeUser

    public String toString(){
        String msg = "";
        for(User U: userList){
            msg += U + ", ";
        }
        return msg;
    }
}//end userList class

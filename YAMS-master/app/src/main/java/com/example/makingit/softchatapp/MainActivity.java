package com.example.makingit.softchatapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.View;
import android.widget.TextView;
import android.net.wifi.p2p.WifiP2pManager;


public class MainActivity extends AppCompatActivity {

    String userName; //holds what the user wants to be identified as
    String userIP = "IP Address Place Holder";//holds the users ip address
    User me;//holds the local users profile
    UserList chatGroup;//holds the public profiles of the other users



    //will return the ip address as a string
    public String getNetworkInfo(){
        try {
            WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            return Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        }catch(Exception E){
            return "Could Not Resolve IP Address";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set ip text box to ip address
        userIP = getNetworkInfo();
        ((TextView) findViewById(R.id.editText2)).setText(userIP);

    }

    //join chat is to move the user to the messaging screen
    public void joinChat (View view){
        userName = ((TextView) findViewById(R.id.editText)).getText().toString();//sets username value to the name field value in the xml page
        me = new User(userName, userIP);//populates the variables in the me user

        Intent joinChatNow = new Intent(this, TextActivity.class);
        startActivity(joinChatNow);
    }
}

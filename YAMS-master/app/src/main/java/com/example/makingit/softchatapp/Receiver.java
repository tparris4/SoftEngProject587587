package com.example.makingit.softchatapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.os.Bundle;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pConfig;


public class Receiver extends BroadcastReceiver{

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private Context mContext;
    BroadcastReceiver mReceiver;
    IntentFilter mIntentFilter;
    WifiP2pManager.PeerListListener peerList;//holds each of the peers found
    WifiP2pDevice peer;



    public Receiver(WifiP2pManager m, WifiP2pManager.Channel c, Context co){
        this.mManager = m;
        this.mChannel = c;
        this.mContext = co;
    }

    @Override
    public void onReceive(Context c, Intent i){
        String action = i.getAction();

        // Check to see if Wi-Fi is enabled and notify appropriate activity
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int state = i.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                // Wifi P2P is enabled
            } else {
                // try to reenable otherwise warn user
            }

        }
        // Call WifiP2pManager.requestPeers() to get a list of current peers
        else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            if(mManager != null){
                //request a list of peers
                mManager.requestPeers(mChannel, peerList);/*requests a list of peers****************************************************/
            }
        }
        // Respond to new connection or disconnections
        else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

        }
        // Respond to this device's wifi state changing
        else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {

        }

    }//end onReceive


    protected void onCreate(Bundle savedInstanceState){


        mManager = (WifiP2pManager) mContext.getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this.mContext, mContext.getMainLooper(), null);
        mReceiver = new Receiver(mManager, mChannel, this.mContext);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);


        //peer discovery code
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {

            public void onSuccess() {
                //code for successful peer discovery
                //peerList can be obtained in the peers changed action in the onReceive method
            }


            public void onFailure(int reasonCode) {
                //failure state
            }
        });

        //builds device list
        mManager.requestPeers(mChannel, new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
                for(WifiP2pDevice device : wifiP2pDeviceList.getDeviceList() ){
                    if (peer.deviceName.equals("ABC")) {
                        /*GET DEVICE INFO HERE*********************************************************************************/
                    }
                }
            }//end onPeersAcailable
        });

    }//end onCreate

    //these two functions register and register the receiver when the activity
    //is active and paused respectively
    protected void onResume(){
        this.onResume();
        mContext.registerReceiver(mReceiver,mIntentFilter);
    }
    protected void onPause() {
        this.onPause();
        mContext.unregisterReceiver(mReceiver);
    }


    //connects to Peer and returns value on whether or not it worked
    boolean check1 = false;//used specifically with below function so onSuccess and failure could be uesd
    //for return value
    public boolean connectPeer(WifiP2pDevice d, WifiP2pConfig c){
        c.deviceAddress = d.deviceAddress;
        this.mManager.connect(mChannel, c, new ActionListener() {
            @Override
            public void onSuccess() {check1 = true;}
            @Override
            public void onFailure(int reason) {check1 = false;}
        });
        return check1;
    }//end connectPeer

}

package com.example.makingit.softchatapp;



import android.content.ContentResolver;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * TODO
 *  build and send messages
 *  display messages in text activity
 *  peer discovery + userList building
 *  user creation
 */

public class TextActivity extends AppCompatActivity {

    Receiver wifiReceiver;
    Context context;
    WifiP2pManager manager;
    Channel channel;


    //onCreate to to link this java class to the text activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);




        wifiReceiver = new Receiver(manager, channel, context);

        //set up as a server socket until a message is sent

    }

    public void sendMessage() {//change function type so it pulls text value from message text box
        Context context = this.getApplicationContext();
        String host; //set this to what ever the ip of the device is can be pulled from receiver class*****************************************************
        int port = 3654;
        int len;
        Socket socket = new Socket();
        byte buf[] = new byte[1024];
        try {
            //create a client socket
            socket.bind(null);
            socket.connect((new InetSocketAddress(host, port)), 500);

            //create a byte stream containing message text
            OutputStream outputStream = socket.getOutputStream();
            ContentResolver cr = context.getContentResolver();
            InputStream inputStream = null;
            inputStream = cr.openInputStream(/*message text here*/);//****************************************************************************************
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();


        } catch (Exception E) {
            //do nothing
        }

        //cleanup
        finally {
            if (socket != null) {
                if (socket.isConnected()) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        //catch logic
                    }
                }
            }
        }
    }
}


class serverTask extends AsyncTask {
    private Context context;
    private TextView status;

    public serverTask(Context c, TextView v){
        this.context = c;
        this.status = v;
    }

    @Override
    protected String doInBackground(Object[] objects){
        try {

            /**
             * Create a server socket and wait for client
             */
            ServerSocket serverSocket = new ServerSocket(3654);
            Socket client = serverSocket.accept();

            /**
             * code below is for when a client connects and transfers data
             */
            InputStream in = client.getInputStream();
            java.util.Scanner scan = new java.util.Scanner(in).useDelimiter("\\A");
            serverSocket.close();
            return scan.hasNext() ? scan.next() : "";//returns input stream as a string
        } catch (IOException e) {

            return null;
        }

    }//end doInBackground


    protected void onPostExecute(String result) {
        //DISPLAY ANY RECEIVED MESSAGES**************************************************************************************************
    }//end post Execute


}
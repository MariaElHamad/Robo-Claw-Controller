package pin.beyond.nanny.kaddouraaj.mearm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.net.*;

public class MainActivity extends AppCompatActivity {

    //Buttons on app used to Control Arm
    private Button base_right, base_left, shoulder_up, shoulder_down, elbow_up, elbow_down, gripper_open, gripper_close, stop_movement; private EditText ip_text; //Text box for user to input ip to send it
    private DatagramSocket socket;
    private DatagramPacket packet;
    int port = 1001; //Port the app uses to send data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //sets the view of the app

        //Initializing Buttons and textboxes
        base_right = (Button) findViewById(R.id.BR);
        base_left = (Button) findViewById(R.id.BL);
        shoulder_up = (Button) findViewById(R.id.SU);
        shoulder_down = (Button) findViewById(R.id.SD);
        elbow_up = (Button) findViewById(R.id.EU);
        elbow_down = (Button) findViewById(R.id.ED);
        gripper_open = (Button) findViewById(R.id.GO);
        gripper_close = (Button) findViewById(R.id.GC);
        stop_movement = (Button) findViewById(R.id.button);
        ip_text = (EditText)findViewById(R.id.editText);


        //Button on click listeners which call the sendSignal Method with a proper signal as an argument.
        //The String argument is the signal which is sent to the arm and thus moves.
        base_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("Base_Right");
            }
        });

        base_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { sendSignal("Base_Left");
            }
        });

        shoulder_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("Shoulder_Up");
            }
        });

        shoulder_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("Shoulder_Down");
            }
        });

        elbow_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("Elbow_Up");
            }
        });

        elbow_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("Elbow_Down");
            }
        });

        gripper_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("Gripper_Open");
            }
        });

        gripper_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("Gripper_Close");
            }
        });

        stop_movement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("Stop_Movement");
            }
        });
    }


    private void sendSignal(String msg) {
        socket = null; //set socket as null for efficiency
        try {
            InetAddress host = InetAddress.getByName(ip_text.getText().toString()); //gets the ip, which user inputs, from the text box.
            socket = new DatagramSocket(); //Initialize a socket to send signals through
            byte[] data = msg.getBytes(); //Convert the msg signal into bytes to be sent through the socket to the server
            packet = new DatagramPacket(data, data.length, host, port); //Initialize a packet of the signal informaation to be sent

            //UDP communication on java can not run on the main thread of the app.
            //Java has to use a separate thread to send the signal packet through the socket to the server.
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() { //Initialize a new thread
                    try  {
                        socket.send(packet); //Send the Signal packet through the socket
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start(); //start the thread above
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

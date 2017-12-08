import java.net.*;
import java.util.Scanner;
import java.util.List;
import java.util.Arrays;

public class Server {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = "";
        
        /*get target's ip address */
        System.out.print("Enter target's IP address: ");
        input = sc.nextLine();
        
        init_Server(input, sc);//Call the server initializing method
        
    }
    
    
    private static void init_Server(String input, Scanner sc) {
    	
    	/*create a collector using the target's ip address and start */
        Collector3 c = new Collector3(input);
        c.start();
        
        /* keep running until 'Q' is entered */
        while(true){
            System.out.println("--------Entrer 'Q' to quit at any time--------");
            input = sc.nextLine();
            if(input.toUpperCase().equals("Q")){
                break;
            }
        }
        
        /* stop the system and close scanner */
        c.Stop();
        sc.close();
    	
    }
}

class Collector3 extends Thread {
    //predefined variables
    private final static int PACKETSIZE = 100;
    private final static int SENDER_PORT = 1002;
    private final static int RECEIVE_PORT = 1001;
    private String SEND_IP = "";
    private final static String[] CORRECT_MSG = new String[] {"Base_Right", "Base_Left", "Shoulder_Up",  "Shoulder_Down", "Elbow_Up", "Elbow_Down", "Gripper_Open", "Gripper_Close", "Stop_Movement", "closing"};
    private final static List<String> CORRECT_MSG_LIST = Arrays.asList(CORRECT_MSG);
    
    private static DatagramSocket sendingSocket;
    private static DatagramSocket receivingSocket;
    
    private static String message = " ";
    private static boolean running;
    
    public Collector3(String ip) {
        try{
           sendingSocket = new DatagramSocket() ;
           receivingSocket = new DatagramSocket( RECEIVE_PORT ) ;
        }catch( Exception e ) {
            System.out.println( e ) ;
        }
        
        SEND_IP = ip;
        running = true;
        System.out.println("------------------Ready to Rumble!-------------------");
    }
    
    /* similar to main method */
    public void run() {
        while(running){
            receive(RECEIVE_PORT);
            
            //check if message is valid
            if(CORRECT_MSG_LIST.contains(message)){
                //send message if valid
                send(SEND_IP, message, SENDER_PORT);
            }else{
                System.out.println("Wrong message, ignored");
            }
        }
        
        /* stop the system if quit was entered*/
        System.out.println("Closing the system");
        
        //close the sockets
        sendingSocket.close();
        System.out.println("Sending socket closed");
        
        receivingSocket.close();
        System.out.println("Receiving socket closed");
    }
    
    /* stop the server */
    public void Stop() {
        running = false;
        send("localhost", "closing", RECEIVE_PORT);
        System.out.println("Closing down the Server");
    }
    
    /* Receive a UDP packet from port "port" 
       @param int port port number used to receive udp packet
       
       @return nothing
    */
    private static void receive(int p){
        try {
            //receive message
            System.out.println("Receiving on port " + RECEIVE_PORT ) ;
            DatagramPacket packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE ) ;
            receivingSocket.receive(packet) ;
                
            //print message received
            message = new String(packet.getData()).trim();
            System.out.println( "Address: " + packet.getAddress() + " - Port: " + packet.getPort() + " - Msg: " + message );
            
        }catch( Exception e ) {
            System.out.println( e );
        }
    }
    
    /* Sends a UDP packet to destination "ip" through port "port" 
       @param String ip the destination
       @param int port port number used to send udp packet
       @param String msg the msg to be sent
       
       @return nothing
    */
    private static void send(String ip, String msg, int port){
        try {
           InetAddress host = InetAddress.getByName( ip ) ;
           byte [] data = message.getBytes() ;
           DatagramPacket packet = new DatagramPacket(data, data.length, host, port) ;
           sendingSocket.send( packet ) ;
        }catch( Exception e ) {
            System.out.println( e ) ;
        }
    }
}
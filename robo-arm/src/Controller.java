import java.net.*;      
import com.pi4j.wiringpi.Serial;

public class Controller
{
    private static final int RECEIVING_PORT = 1002;
    private static DatagramSocket receivingSocket;
    private static int SP;
    private static boolean running;
    private static int packetSize = 100;
    
    @SuppressWarnings("resource")
    
    public static void main(String[] args){
        try{
            
           init();
            
           while(running){
                System.out.println("receiving on port " + RECEIVING_PORT );
            
                DatagramPacket packet = new DatagramPacket(new byte[packetSize], packetSize);
                receivingSocket.receive(packet);
                String msg = new String(packet.getData()).trim();
                System.out.println(msg);
                
                if(msg.equals("Base_Left")){
                    Serial.serialPuts(SP, "1");
                    System.out.println("Message sent to arduino: 1");
                }else if(msg.equals("Base_Right")){
                    Serial.serialPuts(SP, "2");
                    System.out.println("Message sent to arduino: 2");
                }else if(msg.equals("Shoulder_Down")){
                    Serial.serialPuts(SP, "3");
                    System.out.println("Message sent to arduino: 3");
                }else if(msg.equals("Shoulder_UP")){
                    Serial.serialPuts(SP, "4");
                    System.out.println("Message sent to arduino: 4");
                }else if(msg.equals("Elbow_Up")){
                    Serial.serialPuts(SP, "5");
                    System.out.println("Message sent to arduino: 5");
                }else if(msg.equals("Elbow_Down")){
                    Serial.serialPuts(SP, "6");
                    System.out.println("Message sent to arduino: 6");
                }else if(msg.equals("Gripper_Open")){
                    Serial.serialPuts(SP, "7");
                    System.out.println("Message sent to arduino: 7");
                }else if(msg.equals("Gripper_Close")){
                    Serial.serialPuts(SP, "8");
                    System.out.println("Message sent to arduino: 8");
                }else if(msg.equals("Stop_Movement")){
                    Serial.serialPuts(SP, "9");
                    System.out.println("Message sent to arduino: 9");
                }else if(msg.equals("closing")){
                    Serial.serialPuts(SP, "0");
                    System.out.println("Message sent to arduino: 0");
                    receivingSocket.close();
                    System.out.println("Receiving socket closed");
                    running = false;
                    break;
                }
            }
        }catch(Exception e){
            System.out.println("DatagramSocket error: " + e); 
        }
    }
    
    private static void init(){
         serialConnect();
         
         try{   
             receivingSocket = new DatagramSocket(RECEIVING_PORT);
         }catch(Exception e){
            System.out.println("DatagramSocket error: " + e); 
         }
             
         running = true;
    }

    private static void serialConnect(){
        SP = Serial.serialOpen("/dev/ttyACM0", 9600);
        if(SP == -1){
            System.out.println("Serial port failed go to next");
        }else{
            System.out.println("Serial Port " + SP);
            return;
        }
            
        SP = Serial.serialOpen("/dev/ttyACM1", 9600);    
        if(SP == -1){
            System.out.println("Serial port failed go to next");
        }else{
            System.out.println("Serial Port " + SP);
            return;
        }
    
        SP = Serial.serialOpen("/dev/ttyACM2", 9600);
        if(SP == -1){
            System.out.println("Serial port failed go to next");
        }else{
            System.out.println("Serial Port " + SP);
            return;
        }
    
        SP = Serial.serialOpen("/dev/ttyACM3", 9600);
        if(SP == -1){
            System.out.println("Serial port failed go to next");
        }else{
            System.out.println("Serial Port " + SP);
            return;
        }
    }   

}
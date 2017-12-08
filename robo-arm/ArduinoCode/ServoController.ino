/*
 * Group F1
 * SYSC 3010
 */
#include <Servo.h>

Servo base, shoulder, elbow, gripper;  // create servo object to control the base, shoulder, elbow and grip

int pos = 90;    // variable to store the servo position
int basePosition = 0, shoulderPosition = 0, elbowPosition = 0, gripperPosition = 0; //variables to store the servos position
int basePin = 9, shoulderPin = 8, elbowPin = 7, gripperPin = 6; //servo pin numbers
char input; //variable to store input

void setup() {
  //setup Serial
  Serial.begin(9600);   //initializes the serial port (9600 is the number of bits)
  
  //setup servos
  base.attach(basePin);          // attaches the base on pin 9 to the servo object
  shoulder.attach(shoulderPin); // attaches the shoulder on pin  to the servo object
  elbow.attach(elbowPin);       // attaches the elbow on pin  to the servo object
  gripper.attach(gripperPin);  // attaches the gripper on pin  to the servo object
}

//syncs all the servos so they start at the same position, to be called once before assembling.
void synch_servos(){
  base.write(pos);
  shoulder.write(pos);
  elbow.write(pos);
  gripper.write(pos);
  delay(100);
} 

void loop(){
  if(Serial.available() > 0){       //serial available returns number of bits recieved
    input = Serial.read();          //reads the bits and saves it into input

    //cases to control the robatic arm
    switch(input){
      case '1': 
        rotateBaseC();
        break;
      case '2': 
        rotateBaseAC();
        break;
      case '3': 
        rotateShoulderU();
        break;
      case '4': 
        rotateShoulderD();
        break;
      case '5': 
        rotateElbowU();
        break;
      case '6': 
        rotateElbowD();
        break;
      case '7': 
        rotateGripperO();
        break;
      case '8': 
        rotateGripperC();
        break;
      case '9':
        stopServo();
        break;
      case '0':
        stopSystem();
        break;
      default:
        synch_servos();
        break;
        
    }
  }
}

/*rotate the base clockwise.*/
void rotateBaseC() {
  basePosition = base.read() - 10;
  if(basePosition < 13){
    basePosition = 13;
  }
  base.write(basePosition);
  Serial.print(base.read());
  delay(20);
}

/*Rotate the base counter clockwise*/
void rotateBaseAC() {
  basePosition = base.read() + 10;
  base.write(basePosition);
  delay(20);
}

/*Moves the shoulder up*/
void rotateShoulderU() {
  shoulderPosition = shoulder.read() - 10;
  if(shoulderPosition < 43){
    shoulderPosition = 43;
  }
  shoulder.write(shoulderPosition);
  delay(20);
}

/*Moves the shoulder down*/
void rotateShoulderD() {
  shoulderPosition = shoulder.read() + 10;
  if(shoulderPosition > 150){
     shoulderPosition = 150;
  }
  shoulder.write(shoulderPosition);
  delay(20);
}//rotate shoulder anti-clockwise.

/*Moves the elbow up*/
void rotateElbowU() {
  elbowPosition = elbow.read() - 10;
  if(elbowPosition < 53){
    elbowPosition = 53;
  }
  elbow.write(elbowPosition);
  delay(20);
}//rotate elbow clockwise.

/*Moves the elbow down*/
void rotateElbowD() {
  elbowPosition = elbow.read() + 10;
  elbow.write(elbowPosition);
  delay(20);
}//rotate elbow anti-clockwise.

/*Opens up the gripper*/
void rotateGripperO() {
  gripperPosition = gripper.read() - 10;
  if(gripperPosition < 93){
    gripperPosition = 93;
  }
  gripper.write(gripperPosition);
  delay(20);
}//gripper open.

/*Closes the gripper*/
void rotateGripperC() {
  gripperPosition = gripper.read() + 10;
  if(gripperPosition > 123){
    gripperPosition = 125;
  }
  gripper.write(gripperPosition);
  delay(20);
}//gripper close.

/*Puts all the pieces to 90 degrees and detach them*/
void stopServo(){
  base.write(pos);
  shoulder.write(pos);
  elbow.write(pos);
  gripper.write(pos);
  delay(100);
  
  stopSystem();         
}//stop the servos from rotating

/*Detaches all the servos*/ 
void stopSystem(){
  base.detach();  
  shoulder.detach();
  elbow.detach();
  gripper.detach();
}//detach all the servos

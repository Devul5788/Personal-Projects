/*Dynamic Host Connection Protocol (DHCP)
Every node on the internet needs an IP address
DHCP allows the IP address to be assigned dynamically
DHCP is invoked if Ethernet.begin() has no IP address arguments
Most routers are configured for DHCP
Static IP addresses are typical for servers */

/*
 DHCP Chat  Server

 A simple server that uses ethernet to turn LED on, off and to recieve
 values from the photoresistor. This version attempts to get an IP address using DHCP

 Circuit:
 * Ethernet shield attached to pins 10, 11, 12, 13   
 */

#include <SPI.h>
#include <Ethernet.h>

// Enter a MAC address and IP address for your controller below.
// The IP address will be dependent on your local network.
// gateway and subnet are optional:
//unless you have more shields connected to arduino, you do not need to change
//mac address. This sets the mac address through software. 
byte mac[] = {
  0x00, 0xAA, 0xBB, 0xCC, 0xDE, 0x02
}; 
//IP address must be static as this is a server application. The first three nums 
//must be exact for local area network, the last one could be made up. This allows
//us to set the IP address of ethernet through software. 
IPAddress ip(192, 168, 1, 177);
//Keep the gateway always the same. This is the ip address of the routher. 
IPAddress gateway(192, 168, 1, 1);
//Subnet Mask specifies the local network (most home networks have this subnet mask)
IPAddress subnet(255, 255, 255, 0);

// telnet defaults to port 23
// port 23 is a default port for internet services 
// the arduino is the server and the ethernet shield is the client(. Telnet allows communication
// between them. 
EthernetServer server(23);
boolean alreadyConnected = false;

int ledPin = 2;

String commandString;

void setup() {
  // put your setup code here, to run once:
  pinMode(ledPin, OUTPUT); 

  // start the Ethernet connection:
  Serial.println("Trying to get an IP address using DHCP");
  if (Ethernet.begin(mac) == 0) {
    Serial.println("Failed to configure Ethernet using DHCP");
    // Check for Ethernet hardware present
    if (Ethernet.hardwareStatus() == EthernetNoHardware) {
      Serial.println("Ethernet shield was not found.  Sorry, can't run without hardware. :(");
      while (true) {
        delay(1); // do nothing, no point running without Ethernet hardware
      }
    }
    if (Ethernet.linkStatus() == LinkOFF) {
      Serial.println("Ethernet cable is not connected.");
    }
    //initialize the ethernet device
    Ethernet.begin(mac, ip, gateway, subnet);
  }
  
  //start listening for clients
  server.begin();
  Serial.begin(9600); 

  //The IP address printed is of the ethernet shield. 
  Serial.print("Chat server address: ");
  Serial.println(Ethernet.localIP());

  while(!Serial){} //needed as sometimes serial library takes time to load
}

void loop() {
  // wait for new client
  EthernetClient client = server.available();

  //when the client sens the first byte, say hello:
  if(client){
    if(!alreadyConnected){
      //clear out the input buffer
      client.flush();
      commandString = ""; //clear the commandString variable

      //Serial.print will print to serial.
      Serial.println("We have a new client");
      client.println("Hello, client!");
      alreadyConnected = true;

      //Print data, followed by a newline, to all the clients connected to a server.
      server.println("--> Please type your command and hit Return...");
    }

    //The read function reads the next byte received from the server the client is connected to
    char newChar = client.read();

    //enter key in ascii is 0x0D
    if(newChar == 0x0D){
      server.print("Recieved this command: ");
      server.println(commandString);
      processCommand(commandString);
    } else {
      Serial.println(newChar);
      commandString += newChar;
    }
  }
}

void processCommand(String command){
  server.print("Processing command ");
  server.println(command);

  if(command.indexOf("photo") > -1){
    Serial.println("Photo command recieved");
    server.print("Reading from photoresitor: ");
    server.println(analogRead(A0)); //Print the reading from A0 pin that is connected to the photoresistor
    commandString = "";
    return;
  }

  if(command.indexOf("LEDon") > -1){
    Serial.println("LED On command recieved");
    digitalWrite(ledPin, HIGH);
    server.println("LED is truned on");
    commandString = "";
    return;
  }

  if(command.indexOf("LEDoff") > -1){
    Serial.println("LED Off command recieved");
    digitalWrite(ledPin, LOW);
    server.println("LED is truned off");
    commandString = "";
    return;
  }

  commandString = "";
  instructions();
}

void instructions(){
  server.println("I don't understand");
  server.println("Please use one of these commands: ");
  server.println(" * photo, to get a reading from the photoresistor");
  server.println(" * LEDon, to turn LED on");
  server.println(" * LEDoff, to turn LED off");
}

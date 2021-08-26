#include <SPI.h>
#include <Ethernet.h>

byte mac[] = {
  0x00, 0xAA, 0xBB, 0xCC, 0xDE, 0x02
}; 

char server[] = "testdomain.edu";
EthernetClient client;

void setup() {
  // put your setup code here, to run once:
  Ethernet.begin(mac);

  //port 80 is on a web server for HTTP. This port tells the server
  //to "listen to" or expect to recievee from a Web CLient
  if(client.connect(server, 80)){
    //This is the HTTP format for a get message. It requests a web
    //server that "give me this index.html". Which is a webpage.
    client.println("GET index.html HTTP/1.1");
    
    //client recieves data from the web server and sends data to serial monitor
    if(client.available()){
      Serial.print(client.read());
    }
    client.stop();
  }
}

void loop() {
  // put your main code here, to run repeatedly:

}

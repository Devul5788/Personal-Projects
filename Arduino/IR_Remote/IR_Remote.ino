#include <IRremote.h>
int IRpin = 9;
int LEDPin = 6;
IRrecv IR(IRpin);
decode_results cmd;
int bright = 0;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(6, INPUT);
  IR.enableIRIn();
}

void loop() {
  // put your main code here, to run repeatedly:
  while(IR.decode(&cmd)== 0){
    
  }
  if(cmd.value == 0xFF18E7){
    bright+=25;
  } else if(cmd.value == 0xFF4AB5){
    bright-=25;
  }

  if(bright > 255){
    bright = 255;
  } else if (bright < 0){
    bright = 0;
  }

  analogWrite(LEDPin, bright);
  
  Serial.println(cmd.value, HEX);
  Serial.println(bright, DEC);
  //delay(500);
  IR.resume();
}

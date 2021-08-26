#include <EEPROM.h>

int LEDPin = 8;
int buttonPin = 2;
int LEDState = 0;
int prevState = 1;
int currState;

int counter = 0;

//as millis returns a long int
long unsigned int lastPress = 0;

int debounceTime = 20;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);

  Serial.begin(9600);
  pinMode(LEDPin, OUTPUT);
  pinMode(buttonPin, INPUT_PULLUP);
  
  //assign counter the valye of address 0 
  counter = EEPROM.read(0);
  
  //write a 0 to 0. This allows for consecutive resets to reset
  //the counter
  EEPROM.write(0, 0);
}

void loop() {
  // put your main code here, to run repeatedly:
  currState = digitalRead(buttonPin);

  if(millis() - lastPress > debounceTime){
    lastPress = millis();
    if(prevState == 1 && currState == 0){
      counter++;
      EEPROM.write(0, counter);
      LEDState = !LEDState;
      digitalWrite(LEDPin, LEDState);

      Serial.print("Counter: ");
      Serial.println(counter);
    }
    prevState = currState;
  }
}

//Side note:
  //will only write to EEPROM from 0 to 255
  //as arduino is a 8 bit system, and the arduino 
  //stores (value)%256
//  for(int addr = 0; addr < 1024; addr++){
//    EEPROM.write(addr, addr);
//    Serial.println(EEPROM.read(addr), DEC);
//  }

//To fix this issue we can use the little Endian ordering
//which baisically masks the first 8 bits and stores them
//in one memory location and stores the second 8 bits
//in the second memory location

//int bigData;
//byte littleData;
//void setup(){
//  littleData = bigData & 0xFF;
//  EEPROM.write(0, littleData);
//  littleData = bigData >> 8;
//  EEPROM.write(1, littleData);
//}

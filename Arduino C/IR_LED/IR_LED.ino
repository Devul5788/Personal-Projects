k//int IRLEDPin = 10;
int IRRecPin = 9;
//int butPin = 8;

void setup() {
  // put your setup code here, to run once:
  //pinMode(IRLEDPin, OUTPUT);
  pinMode(IRRecPin, INPUT);
  //pinMode(butPin, INPUT_PULLUP);
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
//  while(digitalRead(butPin) == 1){
//    
//  }

  //digitalWrite(IRLEDPin, HIGH);
  
  if(digitalRead(IRRecPin) == LOW){
    //delay(500);
    Serial.println("obstacle");
  } else {
    Serial.println("high");
  }
//  } else if (digitalRead(IRRecPin) == HIGH){
//    delay(500);
//    Serial.println("clear");
//  }
}

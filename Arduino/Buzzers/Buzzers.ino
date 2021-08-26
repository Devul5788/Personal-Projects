int potVal;
int buzzPin = 11;
int potPin = A0;
int toneVal;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(buzzPin, OUTPUT);
  pinMode(potPin, INPUT);
}

void loop() {
  // put your main code here, to run repeatedly;
  potVal = analogRead(potPin);

  toneVal = (9940./1023.)*potVal + 60;

  digitalWrite(buzzPin, HIGH);
  delayMicroseconds(toneVal);
  digitalWrite(buzzPin, LOW);
  delayMicroseconds(toneVal); 

  //this also works 

//  for(int i = 1; i < 200; i++){
//    tone(buzzPin, 50*i);
//    delay(100);
//    noTone(buzzPin);
//    delay(100);
//  }
}

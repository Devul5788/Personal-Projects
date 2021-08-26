//int gPin = 9;
//int rPin = 8;
int buzzPin = 9;
int lightPin = A0;
int lightVal;
int dt = 250;

void setup() {
  // put your setup code here, to run once:
//  pinMode(gPin, OUTPUT);
//  pinMode(rPin, OUTPUT);
  pinMode(buzzPin, OUTPUT);
  pinMode(lightPin, INPUT);
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  lightVal = analogRead(lightPin);
  Serial.println(lightVal);
  //delay(dt);

  float buzzVal = 0.01 * lightVal - 0.41;

  digitalWrite(buzzPin, HIGH);
  delay(buzzVal);
  digitalWrite(buzzPin, LOW);
  delay(buzzVal);
  
//  if(lightVal > 100){
//    digitalWrite(gPin, HIGH);
//    digitalWrite(rPin, LOW);
//  } else {
//    digitalWrite(gPin, LOW);
//    digitalWrite(rPin, HIGH);
//  }
}

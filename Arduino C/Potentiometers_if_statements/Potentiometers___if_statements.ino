int readPin = A3;
int warningPin = 8;
float V2 = 0;
int delayTime = 250;

void setup() {
  // put your setup code here, to run once:
  pinMode(readPin, INPUT);
  pinMode(warningPin, OUTPUT);
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  int readVal = analogRead(readPin);
  V2 = (float)readVal/1023 * 5;
  Serial.println(V2);

  if(V2 > 4.0){
    digitalWrite(warningPin, HIGH);
  } else {
    digitalWrite(warningPin, LOW);
  }
  
  delay(delayTime);
}

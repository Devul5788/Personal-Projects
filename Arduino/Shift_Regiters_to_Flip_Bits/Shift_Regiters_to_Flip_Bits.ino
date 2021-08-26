int latchPin = 11;
int clockPin = 9;
int dataPin = 12;
int dt = 1000;

byte myByte = B0101;
byte myByteFlipped;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(latchPin, OUTPUT);
  pinMode(dataPin, OUTPUT);
  pinMode(clockPin, OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
  digitalWrite(latchPin, LOW);
  shiftOut(dataPin, clockPin, LSBFIRST, myByte);
  digitalWrite(latchPin, HIGH);
  Serial.println(myByte, BIN);
  delay(dt);

  myByteFlipped = ~myByte;
  digitalWrite(latchPin, LOW);
  shiftOut(dataPin, clockPin, LSBFIRST, myByte);
  digitalWrite(latchPin, HIGH);
  Serial.println(myByteFlipped, BIN);
  Serial.println(" ");
  delay(dt);
  
}

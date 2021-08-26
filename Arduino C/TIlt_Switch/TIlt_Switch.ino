int tiltPin = 9;
int tiltVal;
int LEDPin = 11;

void setup() {
  // put your setup code here, to run once:
  pinMode(tiltPin, INPUT_PULLUP);
  pinMode(LEDPin, INPUT);
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  tiltVal = digitalRead(tiltPin);
  
  Serial.println(tiltVal);
}

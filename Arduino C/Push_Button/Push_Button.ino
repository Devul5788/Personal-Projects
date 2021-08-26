int butPin = 9;
int butVal;

void setup() {
  // put your setup code here, to run once:
  pinMode(butPin, INPUT_PULLUP);

//   Unlike pinMode(INPUT), there is no pull-down resistor necessary. An internal
//  20K-ohm resistor is pulled to 5V. This configuration causes the input to read
//  HIGH when the switch is open, and LOW when it is closed.

  //essentially equal to digitalWrite(butPin, HIGH);
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  butVal = digitalRead(butPin);
  Serial.print(butVal);
  delay(100);
}

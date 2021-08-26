#include <Servo.h>

int lightVal;
int lightPin = A4;
int servoPin = 9;
float servoSpeed; 
Servo myServo;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  myServo.attach(servoPin);
}

void loop() {
  // put your main code here, to run repeatedly:
  lightVal = analogRead(lightPin);
  Serial.println(lightVal);
  delay(250);

  servoSpeed = -0.29 * lightVal + 187.14;

  Serial.println(servoSpeed);
  
  myServo.write(servoSpeed);
}

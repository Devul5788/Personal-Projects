#include <Servo.h>

int servoPin = 9;
int servoPos = 120;

#define TURN_TIME 175

Servo myServo; 

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  myServo.attach(servoPin);
}

void loop() {
  // put your main code here, to run repeatedly:
    myServo.write(0);
    // Go on turning for the right duration
    delay(TURN_TIME);
    // Stop turning
    myServo.write(90);
}

/*Do's & Don'ts of Interupts:
Do's: 
1. Whatever function you are calling, get in and out quick. This is not multithreading/multitasking. So do things that you could do quickly.
2. Do something simply in the interuptt.

Don'ts:
1. Don't use delays in your interuptt as it will get ignored.
2. Slow setps such as printing
3. Don't mess with serial port as it can have an effect on things related to timing.
4. Don't do serial.available == 0
*/

# include "TimerOne.h"

int LEDStatus = 0;
int greenLED = 9;
int redLED = 10;

void setup() {
  // put your setup code here, to run once:
  pinMode(greenLED, OUTPUT);
  pinMode(redLED, OUTPUT);
  Timer1.initialize(1000000); //as the argument is in microseconds
  Timer1.attachInterrupt(BlinkGreen);
}

void loop() {
  // put your main code here, to run repeatedly:
  digitalWrite(redLED, HIGH);
  delay(250);
  digitalWrite(redLED, LOW);
  delay(250);
}

void BlinkGreen(){
  if(LEDStatus == 1){
    digitalWrite(greenLED, LOW);
    LEDStatus = 0;
    return;
  } else if (LEDStatus == 0){
    digitalWrite(greenLED, HIGH);
    LEDStatus = 1;
    return;
  }
}

int LEDPin = 8;
int buttonPin = 2;
int LEDState = 0;
int prevState = 1;
int currState;

//as millis returns a long int
long unsigned int lastPress = 0;

int debounceTime = 20; // the debounce time; increase if the output flickers


void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(LEDPin, OUTPUT);
  pinMode(buttonPin, INPUT_PULLUP);
}
  
void loop() {
  // put your main code here, to run repeatedly:
  currState = digitalRead(buttonPin);
  Serial.println(LEDState);

  if(millis() - lastPress > debounceTime){
    lastPress = millis();
    if(prevState == 1 && currState == 0){
      LEDState = !LEDState;
      digitalWrite(LEDPin, LEDState);
    }
    prevState = currState;
  }

  //this solves the bouncing issue of the button
  //however it is quite inefficent, as it makes
  //the processor wait for a certain time. The above
  //method is better.
  //delay(100);
}

int buzzPin = 12;
int upButPin = 11;
int downButPin = 10;
int LEDPin = 9;
int currButState1 = 0;
int currButState2 = 0;
int prevState = 0;
int LEDState = 0;
int bright;

void setup() {
  // put your setup code here, to run once:
  pinMode(buzzPin, OUTPUT);
  pinMode(upButPin, INPUT);
  pinMode(downButPin, INPUT);
  pinMode(LEDPin, OUTPUT); 
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  currButState1 = digitalRead(upButPin);
  currButState2 = digitalRead(downButPin);

//  Serial.print("Button 1: ");
//  Serial.print(currButState1);
//  Serial.print("Button 2: ");
//  Serial.println(currButState2);

  Serial.println(bright);

  if(currButState1){
    bright++;
  }

  if(currButState2){
    bright--;
  }

  if(bright > 255){
    bright = 255;
    digitalWrite(buzzPin, HIGH);
    delay(500);
    digitalWrite(buzzPin, LOW);
  } 

  if(bright < 0){
    bright = 0; 
    digitalWrite(buzzPin, HIGH);
    delay(500);
    digitalWrite(buzzPin, LOW);
  }

  analogWrite(LEDPin, bright);
}

int readPin = A3;
int dimPin = 9;
float V2 = 0;
int delayTime = 250;

void setup() {
  // put your setup code here, to run once:
  pinMode(readPin, INPUT);
  pinMode(dimPin, OUTPUT);
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  int readVal = analogRead(readPin);
  float LEDVal = (255./1023.)*readVal;
//  float ratio = (float) readVal/1023;
  V2 = readVal/1023. * 5.;
//  float LEDbrightness = (float) ratio * 255;

  analogWrite(dimPin, LEDVal);

  Serial.println(V2);
  //delay(delayTime);
}

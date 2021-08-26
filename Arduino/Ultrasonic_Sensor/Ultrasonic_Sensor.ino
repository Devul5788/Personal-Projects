int trigPin = 12;
int echoPin = 11;
float pingTravelTime;
float pingTravelDistance;
float distanceToTarget;

void setup() {
  // put your setup code here, to run once:
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  digitalWrite(trigPin, LOW);
  delayMicroseconds(10);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  pingTravelTime = pulseIn(echoPin, HIGH);
  pingTravelDistance = (pingTravelTime*765.*5280.*12.*2.54)/(3600.*1000000);
  distanceToTarget = pingTravelDistance/2;
  
  Serial.println(distanceToTarget);

  //or just use 0.0171*pingTravelTime
  delay(25);
}

#include "DHT.h"
#define Type DHT22

int sensePin = 2;
DHT HT(sensePin, Type);

float humidity;
float tempC;
float tempF;


void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  HT.begin();
}

void loop() {
  // put your main code here, to run repeatedly:
  humidity = HT.readHumidity(); 
  tempF = HT.readTemperature(true);
  Serial.print(tempF);
  Serial.print(" , ");
  Serial.println(humidity);
  delay(250);
}

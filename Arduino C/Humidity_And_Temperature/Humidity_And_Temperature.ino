#include "DHT.h"
#include <LiquidCrystal.h>
#define Type DHT22

int rs = 7;
int en = 8;
int d4 = 9;
int d5 = 10;
int d6 = 11;
int d7 = 12;

LiquidCrystal lcd(rs, en, d4, d5, d6, d7);

int sensePin = 2;
DHT HT(sensePin, Type);

float humidity;
float tempC;
float tempF;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  HT.begin();
  lcd.begin(16, 2);
  delay(500);
}

void loop() {
  // put your main code here, to run repeatedly:
  humidity = HT.readHumidity(); 
  tempC = HT.readTemperature();
  tempF = HT.readTemperature(true);

  lcd.setCursor(0,0);

  lcd.print("Humidity: ");
  lcd.print(humidity);

  lcd.setCursor(0,1);
  lcd.print("Temp: ");
  lcd.print(tempC);
  lcd.print("C");
  delay(500);
  lcd.clear();
}

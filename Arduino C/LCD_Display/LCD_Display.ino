#include <LiquidCrystal.h>
int rs = 7;
int en = 8;
int d4 = 9;
int d5 = 10;
int d6 = 11;
int d7 = 12;
LiquidCrystal lcd(rs, en, d4, d5, d6, d7);

void setup() {
  // put your setup code here, to run once:
  lcd.begin(16, 2);
}

void loop() {
  // put your main code here, to run repeatedly:
  lcd.setCursor(0,0);
//  lcd.print("Hello World!");
//  lcd.setCursor(0,1);
//  lcd.print("My Name is Devul");
//  delay(1000);
//  lcd.clear();

  lcd.setCursor(0,0);
  lcd.print("Watch me count");
  for(int i = 0; i <= 10; i++){
    lcd.setCursor(0, 1);
    lcd.print(i);
    delay(250);
  }

  lcd.clear();
}

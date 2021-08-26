#include <LiquidCrystal.h>
int rs = 7;
int en = 8;
int d4 = 9;
int d5 = 10;
int d6 = 11;
int d7 = 12;

float first;
float second; 
float res;
String oper;

LiquidCrystal lcd(rs, en, d4, d5, d6, d7);

void setup() {
  // put your setup code here, to run once:
  lcd.begin(16, 2);
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  lcd.setCursor(0,0);
  lcd.print("Input First Num:");

  while(Serial.available() == 0){
    
  }
  first = Serial.parseFloat();

  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("Input Second Num:");

  while(Serial.available() == 0){
    
  }
  second = Serial.parseFloat();

  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("Input +,-,*,/");
  while(Serial.available () == 0){

  }
  oper = Serial.readString();

  if(oper == "+"){
    res = first + second;
  } else if(oper == "-"){
    res = first - second;
  } else if(oper == "*"){
    res = first * second;
  } else if(oper == "/"){
    
    res = first/second;
  }

  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("Your Answer is: ");
  lcd.setCursor(0,1);
  lcd.print(first);
  lcd.print(oper);
  lcd.print(second);
  lcd.print("=");
  lcd.print(res);

  delay(5000);
  lcd.clear();
}

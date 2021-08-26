byte myByte = 0x0;
//byte myByte = B000;
//byte myByte = 0;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600); 
}

void loop() {
  // put your main code here, to run repeatedly:
  Serial.print(myByte, DEC);
  Serial.print("  ");
  Serial.print(myByte, BIN);
  Serial.print("  ");
  Serial.println(myByte, HEX);
  delay(500);

  myByte++;
}

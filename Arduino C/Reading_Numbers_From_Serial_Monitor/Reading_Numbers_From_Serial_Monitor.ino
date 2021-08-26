int myNum;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(9, OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
  Serial.println("Please enter your number: ");
  while(Serial.available() == 0){
    
  }

  myNum = Serial.parseInt();

  for(int i = 0; i < myNum; i=i+1){
    digitalWrite(9, HIGH);
    delay(500);
    digitalWrite(9, LOW);
    delay(500);
  }
  
//  Serial.print("Your number is: ");
//  Serial.println(myNum);
}

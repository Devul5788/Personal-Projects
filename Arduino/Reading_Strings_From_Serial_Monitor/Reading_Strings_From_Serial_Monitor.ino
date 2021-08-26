int gPin = 11;
int bPin = 10;
int rPin = 9;
String choice;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(9, OUTPUT);
  pinMode(10, OUTPUT);
  pinMode(11, OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
  Serial.println("Please enter the color: ");
  while(Serial.available() == 0){
    
  }

  choice = Serial.readString();

  if(choice == "red"){
    digitalWrite(rPin, HIGH);
    digitalWrite(gPin, LOW);
    digitalWrite(bPin, LOW);
  } else if (choice == "green"){
    digitalWrite(rPin, LOW);
    digitalWrite(gPin, HIGH);
    digitalWrite(bPin, LOW);
  } else if (choice == "blue"){
    digitalWrite(rPin, LOW);
    digitalWrite(gPin, LOW);
    digitalWrite(bPin, HIGH);
  }
  
//  Serial.print("Your number is: ");
//  Serial.println(myNum);
}

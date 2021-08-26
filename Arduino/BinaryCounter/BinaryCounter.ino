int del = 1000; 
int count = 0;

void setup() {
  // put your setup code here, to run once:
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);
  pinMode(10, OUTPUT);
  pinMode(11, OUTPUT);
}

void loop() { 
  if((count/8)%2 == 0){
    digitalWrite(8, LOW);
    digitalWrite(9, LOW);
    digitalWrite(10, LOW);
    digitalWrite(11, LOW);
  }else if((count/8)%2 == 1){
    digitalWrite(11, HIGH);
  }
  
  if((count/4)%2 == 0){
    digitalWrite(10, LOW);
  } else if ((count/4)%2 == 1){
    digitalWrite(10, HIGH);
  }

  if((count/2)%2 == 0){
    digitalWrite(9, LOW);
  } else if ((count/2)%2 == 1){
    digitalWrite(9, HIGH);
  }

  if(count%2 == 0){
    digitalWrite(8, LOW);
  } else if (count%2 == 1){
    digitalWrite(8, HIGH);
  }

  delay(del);

  if(count < 16){
    count++;
  } else {
    count = 0;
  }
}

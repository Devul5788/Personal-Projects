//To Review how the I2C Communication works,
//look at this video: https://www.youtube.com/watch?v=6IAkYpmA1DQ
//SDA (Serial Data) pin in arduino is the one above AREF Pin
//SCL (Serial Clock)pin in arduino us the one above the SDA Pin

//Master initiates and terminates transmission. It also generates SCL
//Slave is addressed by the master
//Transmittor - placing data on the bus
//Reciever - reading data from the bus
//Both master, slave can be transmittor or reciever


//The I2C protocol has:
//1) 1 Start Bit
//2) 7 bit sequence for Device (slave) Address + 1 bit for R/W
//3) 1 Acknowledge bit for error checking essentially. Must be low if no errors, handled by transmittor
//4) 8 bit sequence for Internal Register Address (this is for the internal regiters of the slave device selected
//5) 1 Acknowledge bit
//6) 8 bits of Data being read/written into/from the register
//7) 1 Acknowledge bit
//8) 1 Stop Bit

//To get the slave device address and the slave device register address look at the datasheet
//You can also use the command i2c_scanner command

//The I2C protocol is active low, meaning that it needs a a pullup register so that the both SDA & SCL are high.
//We can use 2k ohm resistor for 400kbps and 10k for 100 kbps. Note that the ADXL already has a built in pullup resistor

//Wire library is used to access I2C
#include <Wire.h>

int ADXL = 0x53; //The ADXL345 sensor I2C address

float X, Y, Z; //output

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);

  //Calling Wire.begin() with no arguments makes the arduino the master. Otherwise if you send an address as an
  //argument it makes that address the master and the arduino a slave
  Wire.begin();
  Wire.beginTransmission(ADXL); //start communicating with device
  Wire.write(0x2D); //Access talk to POWER_CTL Register 0x2D
  Wire.write(8); // (8 dec -> 0000 1000 binary) Bit D3 High for measuring. Look at datasheet for power register
  Wire.endTransmission();
  delay(10); 

  //Off-set Calibration
  //X-axis
  Wire.beginTransmission(ADXL);
  Wire.write(0x1E); //X-axis offset register
  Wire.write(1);
  Wire.endTransmission();
  delay(10);

  //X-axis
  Wire.beginTransmission(ADXL);
  Wire.write(0x1F); //X-axis offset register
  Wire.write(-2);
  Wire.endTransmission();
  delay(10);

  //Z-axis
  Wire.beginTransmission(ADXL);
  Wire.write(0x20); //X-axis offset register
  Wire.write(-7);
  Wire.endTransmission();
  delay(10);
}

void loop() {
  // put your main code here, to run repeatedly:
  Wire.beginTransmission(ADXL);
  Wire.write(0x32); //Start with ACCEL_XOUT_H register
  Wire.endTransmission(false);
  Wire.requestFrom(ADXL, 6, true); //Read 6 registers total, each axis has 2 registers. the third argument is optional
  //stop argument to release the bus after the reading is done

  //note that the combined value for X is in 2s compliment
  //The output of the X, Y, and Z depends on sensitivity which can vary from +/- 2g to +/- 16g. For changes look at datasheet
  //we will choose sensitivity of +/- 2g. Accordingly we must divide by 256

  // this combines the 2 bytes stored in 2 different registers for X, Y, and Z
  //Wire.rea() returns a single byte from recieve buffer
  X = (Wire.read() | (Wire.read() << 8))/256; 
  Y = (Wire.read() | (Wire.read() << 8))/256;
  Z = (Wire.read() | (Wire.read() << 8))/256;

  //Wire.available() returns the number of bytes waiting
  
  Serial.print("X = ");
  Serial.print(X);
  Serial.print("Y = ");
  Serial.print(Y);
  Serial.print("Z = ");
  Serial.print(Z);
}

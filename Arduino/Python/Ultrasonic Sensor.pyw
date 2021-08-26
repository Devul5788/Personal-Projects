import serial # Import the serial library
from visual import * # Import all the vPython library

arduinoSerialData = serial.Serial('com6', 9600)
measuringRod = cylinder(length =6, color = color.yellow, radius=0.1, pos=(-3, -2, 0))
lengthLabel = label(text = 'Target Distance is: ', pos = (0,3,0), height =30, box = false)
target = box(color = color.green, length = 0.2, width=3, height =3, pos=(0,-0.5,0))

while (1==1):
    rate(2000)
    if (arduinoSerialData.inWaiting() > 0):
        myData = arduinoSerialData.readline()
        distance = float(myData)
        myLabel ='Target Distance is: ' + myData
        lengthLabel.text = myLabel
        target.pos = (-3 + distance, -0.5, 0)
        measuringRod.length = distance

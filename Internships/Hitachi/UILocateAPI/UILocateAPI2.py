#PSELocateAPI.py

#Purpose:Whenever Locate PIN is found on the screen right click on it
#This daemon will act like a helper to further navigate PSE Workflow
#in Geographic View
#Author : Damodar Hegde
#Change History
#06:27:2020 :Optimization
#01:10:2021 :Perform Sys.exit on detection of the network object
#01:21:2021 :Remove unnacessary mouse movements.include grayscale
#01:26:2021 :v42:Increase depth to 50 percent and click will be at the bottom of the pin
#06:28:2021 :v47:Grayscale = False amd confidence to 0.70
#06:28:2021 :v48:Find the nearby line
import pyautogui
import time
import os
import sys
__version__="1.0.47"
__maxtimeout__ = int (sys.argv[1].strip())
__findline__ = bool (sys.argv[2].strip())
(width,depth)=pyautogui.size()
__location__ = os.path.realpath(os.path.join(os.getcwd(), os.path.dirname(__file__)))
print(width,depth)
timeout = time.time() + __maxtimeout__# Time out to find the pin
__icon_file__="icnLocate.png"
print(os.path.join(__location__, __icon_file__))
network_object_found=  False
print ("Version:",__version__)
while time.time() < timeout:
   try:
      (x,y)=pyautogui.locateCenterOnScreen(os.path.join(__location__, __icon_file__), region=(round(width*0.35),round(depth*0.15),round(width * 0.35),round(depth* 0.60)),grayscale=False,confidence =  0.70)
	  #im = pyautogui.screenshot(region=(x-100,y-100,300,300))
      #pyautogui.moveTo(x,y+26)  
      time.sleep(0.5) 
      #pyautogui.click(x,y+26)
      time.sleep(0.5)
      pyautogui.rightClick(x,y+26)
      sys.exit(0)
   except Exception as e: 
      print('********************************************')
      time.sleep(0.02)
      print("UI Locate API is Scanning:",width,'X',depth)

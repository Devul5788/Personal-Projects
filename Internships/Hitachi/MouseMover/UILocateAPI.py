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
#07:14:2021 :v48:Find the nearby line
#07:19:2021 :v49: click on nearest point on line
#07:20:2021 :v50: click on nearest point on line implementation 2 
#07:20:2021 :v51: fix the color of the line and handle edge case when x and y coordinates are more then 90% of screen
#07:20:2021 :v2.0: make sure that the mouse only right clicks at the line not the capacitor/fuse/switch. Make it work for any dotted lines
#08:02:2021 :v2.0.1: Save RGB values in a file

import pyautogui
import time
import os
import sys
import cv2 
import numpy as np
from PIL import Image
import csv

__version__="2.0.1"
__maxtimeout__ = int (sys.argv[1].strip())
__findline__ = sys.argv[2].lower() == 'true'
(width,depth)=pyautogui.size()
__location__ = os.path.realpath(os.path.join(os.getcwd(), os.path.dirname(__file__)))
print(width,depth)
timeout = time.time() + __maxtimeout__# Time out to find the pin
loc1 = os.path.join(__location__, "icnLocate.png")
loc2 = os.path.join(__location__, "ss.png") # used only for line detection
loc3 = os.path.join(__location__, "lineColor.png") # used only for line detection
loc4 = os.path.join(__location__, "lineColor.csv") # used to save the RGB color of the line in a file
print ("Version:",__version__)
print("line detection: ", __findline__)


# (x, y) are the coordinates of the pin (x1, y1) & (x2, y2) are the coordinates of the line
def pointAndDistanceOnLine(x, y, x1, y1, x2, y2):
   # if x2== x1 or if y2 == y1, then we increase x1 and y1 by 1 pixel so that calculating slope doesn't give an error
   if(x2 == x1):
      x2 = x2 + 1
   elif (y2 == y1):
      y2 = y2 + 1
               
   # calculate the slope and y-int of the line and the the reciprocal line passing through the x and y coordinates of the pin, and then find the intersection point of the 2 lines.
   m1 = (y2 - y1)/(x2 - x1)
   b1 = y2 - m1 * x2
   m2 = -1/m1
   b2 = y - m2 * x
   X = (b2 - b1)/(m1 - m2)
   Y = m2 * X + b2

   # calculate the distance between the pin and (X, Y) point on the line
   d = np.sqrt([(X-x)**2 + (Y-y)**2])
   return (X, Y, d)

def getColor(minX, minY, pinX, pinY, dimen):
   # take a small screenshot around minX & minY, save it, and convert it to a numpy array of RGB values
   img = pyautogui.screenshot(region=(minX-dimen/2,minY-dimen/2,dimen,dimen))
   img.save(loc3)
   im = Image.open(loc3).convert('RGB')
   na = np.array(im)

   # arrange all pixels into a tall column of 3 RGB values and find unique rows (colours)
   colors, counts = np.unique(na.reshape(-1,3),axis = 0, return_counts=1)

   maxCount = 0
   (R, G, B) = (0, 0, 0)

   # loop through all the unique RGB values and return the RGB value which has the max occurance, this is the color of the line
   for i in range(len(colors)):
      # convert array element into tuples
      (r,g,b) = tuple(colors[i].reshape(1,-1)[0]) 
      if(r < 20 and  g< 20 and b< 20):
         (r, g, b) = (0, 0, 0)
      elif(235 <r  and  235 <g and 235 <b):
         (r, g, b) = (255,255,255)

      if((r,g,b) != (0,0,0) and (r,g,b) != (43,43,43) and (r,g,b) != (118,118,118) and (r,g,b) != (121,210,247) and (r, g, b) != (255, 255, 255) and (r, g, b) != (117, 207, 242) and maxCount<counts[i]):
         (R, G, B) = (r, g, b)
         maxCount = counts[i]
   
   print((R, G, B))
   print(maxCount)

   # writing the rgb color to a csv file
   with open(loc4, 'w') as f:
      w = csv.writer(f)
      w.writerow([R, G, B])

   # loop through the screen shot to find the x and y values that match the color of the line and return the coordiantes of that point
   for i in range(dimen):
      for j in range(dimen):
         if((R, G, B) == img.getpixel((i,j))):
            return (minX-dimen/2+i, minY-dimen/2+j)

while time.time() < timeout:
   try:
      # finding the x and y coordinates of the pin image on the screen
      (x,y)=pyautogui.locateCenterOnScreen(loc1, region=(round(width*0.35),round(depth*0.15),round(width * 0.35),round(depth* 0.75)),grayscale=False,confidence =  0.70)      
      #pyautogui.moveTo(x,y+26)
      time.sleep(0.5)
      #pyautogui.click(x,y+26)
      time.sleep(0.5)

      if (__findline__== False):
         pyautogui.rightClick(x,y+26)
      else:
         # take a screenshot of size 400x400 around the pin, save it, and convert it to a grayscale image
         im = pyautogui.screenshot(region=(x-200,y-200,400,400))
         im.save(loc2)
         img = cv2.imread(loc2)

         # Set up and apply kernels to find dotted lines properly. Chanvge the second and the third param of threshold for changes to how line is detected f
         kernel1 = np.ones((3,5),np.uint8)
         kernel2 = np.ones((9,9),np.uint8)
         gray = cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
         imgBW=cv2.threshold(gray, 35, 200, cv2.THRESH_BINARY_INV)[1]
         imgBW=cv2.threshold(imgBW, 35, 200, cv2.THRESH_BINARY_INV)[1]
         img1=cv2.erode(imgBW, kernel1, iterations=1)
         img2=cv2.dilate(img1, kernel2, iterations=3)
         img3 = cv2.bitwise_and(imgBW,img2)
         img3= cv2.bitwise_not(img3)
         img4 = cv2.bitwise_and(imgBW,imgBW,mask=img3)

         # find all the lines (dotted and straight) using img4. Do not change minLineLength & maxLineGap
         imgLines= cv2.HoughLinesP(img4,15,np.pi/180,2, minLineLength = 46, maxLineGap =10)

         (minX, minY, minD) = (x, y, depth)

         # loop through all the lines in the 2D lines vector to get the minX, minY, and minD 
         for i in range(len(imgLines)):
            for x1,y1,x2,y2 in imgLines[i]:
               #cv2.line(img,(x1,y1),(x2,y2),(0,255,0),2) # debug line
               # detect the line only if it is less then 90% of the depth
               if(y1 < 0.9 *depth and y2 < 0.9 * depth):
                  (x3,y3, current_d) = pointAndDistanceOnLine(x, y, x-200+x1, y-200+y1, x-200+x2, y-200+y2)
                  if(current_d < minD):
                     minD = current_d
                     (minX, minY) = (x3, y3)
         #cv2.imwrite(loc2, img) # debug line
         (x, y) = getColor(minX, minY, x, y, 175)
         pyautogui.rightClick(x, y)
      
      sys.exit(0)
   except Exception as e: 
      print('********************************************')
      time.sleep(0.02)
      if (e.__class__ != TypeError):
         print(e)
         sys.exit(0)
      print("UI Locate API is Scanning:",width,'X',depth)
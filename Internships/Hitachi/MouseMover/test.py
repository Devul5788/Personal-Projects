import pyautogui
import time
import random

__version__="1.0.0"
(width,depth)=pyautogui.size()
print ("Version:",__version__)

while True:
    time.sleep(3)
    (x, y) = pyautogui.position()
    (newX, newY) = (random.uniform(0, width), random.uniform(0, depth))
    pyautogui.moveTo(newX, newY)
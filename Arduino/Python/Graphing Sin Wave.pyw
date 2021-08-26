import matplotlib.pyplot as plt
import numpy as np

x=[]
y=[]

for i in np.arange(0, 2*np.pi, 2*np.pi/50):
    x.append(i)
    #y.append(np.sin(i))
    y.append(i**2)
plt.plot(x,y,'bo')
plt.plot(x,y, 'r-', linewidth=2)
plt.axis([0, 2*np.pi, -1.5, 1.5])
plt.title('My simple Line')
plt.xlabel('My X Axis')
plt.ylabel('My Y Axis')
plt.grid(True)
plt.show()

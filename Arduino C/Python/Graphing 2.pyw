import numpy as np
import matplotlib.pyplot as plt

x = [1, 2, 3, 4, 5]
y = [2, 4, 6, 8, 10]
z = [2,0,-2,-4,-6]

plt.plot(x, y, 'bo')
plt.plot(x,y,'r-', label = 'y=2x')
plt.plot(x,z,'g--', label = 'y=-x+4')
plt.plot(x,z,'g^')
plt.axis([0,6,-8,12])
plt.title('My Great Line Graph')
plt.xlabel('My X axis')
plt.ylabel('My Y values')
plt.grid(True)
plt.legend()
plt.show()

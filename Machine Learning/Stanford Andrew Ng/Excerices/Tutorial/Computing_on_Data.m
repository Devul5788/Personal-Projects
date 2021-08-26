A = [1 2; 3 4; 5 6];
B = [11 12; 13 14; 15 16];
C = [1 1; 2 2];

A * C %matrix by matrix mult.
A .* B %element by element mult.
A .^ 2

v = [1; 2; 3]
1 ./ v % element wise reciprocal
log(v)
abs(v)

v + ones(length(v), 1) %increment all elements by 1
v + 1 %does the same thing
A' % transpose

a = [1 15 2 0.5]
val = max(A)
[val, ind]  = max(A) % gets the index of highest value

a < 3 % does element wise comparison

find(a < 3) %finds the indxs of when cond is true

A = magic(3) %all rows, cols, diagnols sum to the samae thing

[r, c] = find(A >= 7) %find the idxs of all the elements >=7

sum(a) %adds all the elements together
sum(A, 1) %adds per col sum
sum(A, 2) %adds per row sum
A.*eye(3) %gets the diagnols
sum(sum(A.*eye(3))) %sum of the elements in diagnols
sum(sum(A.*flipud(eye(3)))) %sum of the elements in the other diagnols
prod(a) %multiplies all the elements togther
floor(a) %rounds down
ceil(a) %rounds up

max(rand(3), rand(3)) %takes element by element max of 2 random matrices

max(A, [], 1) %takes col wise max
max(A, [], 2) %takes row wise max 
max(A) %does the same thing as max(A, [], 1)
max(max(A)) %returns the max element in A
max(A(:)) %same thing as above


temp = pinv(A) %inverse of A
temp * A %equal to the identity matrix

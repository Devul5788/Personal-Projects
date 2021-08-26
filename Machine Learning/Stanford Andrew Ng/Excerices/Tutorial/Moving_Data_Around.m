A = [1 2; 3 4; 5 6]
% size(A)
% size(A, 1) %number of rows
% size(A, 2) %number of cols
% v = [1 2 3 4]
% length(v) 
% length(A) %gives the size of the longest dimen.

w = A(2:3) %sets w to the first 2 elements of the matrix A. Cols first
%save hello.mat w --> use this in command window
%save hello.text w -ascii % save as text (ASCII)

% data manipulation

A = [1 2; 3 4; 5 6]
A(3,2) %3rd row, 2nd col
A(3,:) % ":" means every element along that row/col
A(:,1:2)
A([1 3], :)
A(:, 2) = [10; 11; 12] %change the elements of second col

A = [A, [100; 102; 103]] % append another col to the right
A(:) % put all elements of A into a single col vector

A = [1 2; 3 4; 5 6]
B = [11 12; 13 14; 15 16]
AB
C = [A B] % put B on the right
C = [A; B] % out A on the left


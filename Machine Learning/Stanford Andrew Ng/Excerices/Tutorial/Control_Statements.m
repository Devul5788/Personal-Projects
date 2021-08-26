% v = zeros(10, 1)
% 
% for i = 1: 10,
%     v(i) = 2^i;
% end;
% 
% i = 1;
% while i <=5,
%     v(i) = 100;
%     i = i+1; %i++ does not work
% end;
% 
% i = 1;
% 
% while true
%     v(i) = 999;
%     i = i+1
%     if i == 6,
%         break;
%     end;
% end;
% 
% v(1) = 2;
% if v(1) == 1,
%     disp('The value is one');
% elseif v(1) == 2,
%     disp('the value is 2');
% else 
%     disp('The value is not 1 or 2.');
% end;
% 
% squareAndCubeFunc(5);

X = [1 1; 1 2; 1 3]
y = [1; 2; 3]
theta = [0;1];
j = costFunctionJ(X, y, theta)
theta = [0;0];
j = costFunctionJ(X, y, theta)
(1^2 + 2^2 + 3^2)/(2*3)
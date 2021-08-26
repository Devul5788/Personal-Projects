function J = costFunctionJ(X, y, theta)

% X is the "design matrix" containg our training examples.
% y is the class labels

m = size(X, 1); %number of training examples
predictions = X*theta; %predictions of hypothesis on all examples
sqrErrors = (predictions-y).^2 %squared errors

J = 1/(2*m) * sum(sqrErrors); 
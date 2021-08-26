function [J grad] = nnCostFunction(nn_params, ...
                                   input_layer_size, ...
                                   hidden_layer_size, ...
                                   num_labels, ...
                                   X, y, lambda)
%NNCOSTFUNCTION Implements the neural network cost function for a two layer
%neural network which performs classification
%   [J grad] = NNCOSTFUNCTON(nn_params, hidden_layer_size, num_labels, ...
%   X, y, lambda) computes the cost and gradient of the neural network. The
%   parameters for the neural network are "unrolled" into the vector
%   nn_params and need to be converted back into the weight matrices. 
% 
%   The returned parameter grad should be a "unrolled" vector of the
%   partial derivatives of the neural network.
%

% Reshape nn_params back into the parameters Theta1 and Theta2, the weight matrices
% for our 2 layer neural network
Theta1 = reshape(nn_params(1:hidden_layer_size * (input_layer_size + 1)), ...
                 hidden_layer_size, (input_layer_size + 1));

Theta2 = reshape(nn_params((1 + (hidden_layer_size * (input_layer_size + 1))):end), ...
                 num_labels, (hidden_layer_size + 1));

% Setup some useful variables
m = size(X, 1);
         
% You need to return the following variables correctly 
J = 0;
Theta1_grad = zeros(size(Theta1));
Theta2_grad = zeros(size(Theta2));

% ====================== YOUR CODE HERE ======================
% Instructions: You should complete the code by working through the
%               following parts.
%
% Part 1: Feedforward the neural network and return the cost in the
%         variable J. After implementing Part 1, you can verify that your
%         cost function computation is correct by verifying the cost
%         computed in ex4.m
%
% Part 2: Implement the backpropagation algorithm to compute the gradients
%         Theta1_grad and Theta2_grad. You should return the partial derivatives of
%         the cost function with respect to Theta1 and Theta2 in Theta1_grad and
%         Theta2_grad, respectively. After implementing Part 2, you can check
%         that your implementation is correct by running checkNNGradients
%
%         Note: The vector y passed into the function is a vector of labels
%               containing values from 1..K. You need to map this vector into a 
%               binary vector of 1's and 0's to be used with the neural network
%               cost function.
%
%         Hint: We recommend implementing backpropagation using a for-loop
%               over the training examples if you are implementing it for the 
%               first time.
%
% Part 3: Implement regularization with the cost function and gradients.
%
%         Hint: You can implement this around the code for
%               backpropagation. That is, you can compute the gradients for
%               the regularization separately and then add them to Theta1_grad
%               and Theta2_grad from Part 2.
%

% calculating the Y matrix in vector form
V = [1:num_labels];
Y = ones(m,1) * V == y;

% Add ones to the X data matrix
X = [ones(m, 1) X];

% Forward Propogation
z2 = X*Theta1';
a2 = [ones(m, 1) sigmoid(z2)];
z3 = a2*Theta2';
h = sigmoid(z3); %equal to h

% a2 = sigmoid(X*Theta1');
% a2 = [ones(m, 1) a2];
% h = sigmoid(a2*Theta2'); %equal to a3

% Calculating how much to penalize by regularization
% Note that previously theta was a single dimensional vector storing the
% values for all the parameters. We ignored the first parameter as it was
% the intercept\bias. For NN, theta is now a matrix, where the first col is
% for the bias unit in layer j. We ignore this first col. 
penalize = lambda/(2*m) * (sum(sum(Theta1(:,2:end).^2)) + sum(sum(Theta2(:,2:end).^2)));

%Element wise multiplication means that you are multiplying each
%consequitive elements of 2 matrix. 
J = 1/m * sum(sum(-Y .* log(h) - (1-Y) .* log(1-h))) + penalize;

% =========================================================================
% Netural Net Gradient Function (Backpropogation)

% Essentially by using backpropogation we find the derivatives that
% determine each component in the gradient. The gradient essentially tells 
% us how much the cost will change if we change the activation of each unit 
% by changing the NN's weights. We do forward propogation within back
% propogation so that we can calculate deltas, and we calculate deltas so
% that we can iteratively adjust gradients by adding it to the theta
% matrix. Once this has been done, we send both the cost and the gradient
% calculated for theta1 and theta2 to a function similar to gradient
% descent so that we can find the optimal weights that classifes each
% number properly. 


% For each training set

for t = 1:m
    % Step 1: Set a1 = X^(t). Note that a1 already contains the bias unit
    % as X already contains the bias unit. 
    a1 = X(t,:);
    
    % Step 2: Forward propogation
    z2 = a1*Theta1';
    a2 = [1 sigmoid(z2)];
    z3 = a2*Theta2';
    a3 = sigmoid(z3); %equal to h
    
    z2=[1 z2]; % adding bias as we use it to calculate delta2
    
    % Step 3 & 4: Computing delta errors for each unit
    delta3 = a3 - Y(t, :);
    
    %Note for the way my matrices are set up I dont need to do transverse
    %of theta2 * delta3. For my implementation this is what works.
    delta2 = (delta3 * Theta2) .* sigmoidGradient(z2);
    
    % We skip delta_2(0) as we don't consider the bias term when making
    % gradient calculations. 
    delta2 = delta2(2:end);
    
    % Step 5: Iteratively adjusting Theta1. We are simply 
    Theta2_grad = Theta2_grad + (a2' * delta3)';
    Theta1_grad = Theta1_grad + (a1' * delta2)';
end

% Regularization of weight gradients
Theta1_grad = 1/m * Theta1_grad;
Theta2_grad = 1/m * Theta2_grad;
Theta1_grad(:,2:end) = Theta1_grad(:,2:end) + lambda/m*Theta1(:, 2:end);
Theta2_grad(:,2:end) = Theta2_grad(:,2:end) + lambda/m*Theta2(:, 2:end);

% Unroll gradients
grad = [Theta1_grad(:) ; Theta2_grad(:)];
% =========================================================================
end

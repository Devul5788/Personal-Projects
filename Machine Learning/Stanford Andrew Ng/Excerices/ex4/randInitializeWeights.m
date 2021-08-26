function W = randInitializeWeights(L_in, L_out)
%RANDINITIALIZEWEIGHTS Randomly initialize the weights of a layer with L_in
%incoming connections and L_out outgoing connections
%   W = RANDINITIALIZEWEIGHTS(L_in, L_out) randomly initializes the weights 
%   of a layer with L_in incoming connections and L_out outgoing 
%   connections. 
%
%   Note that W should be set to a matrix of size(L_out, 1 + L_in) as
%   the first column of W handles the "bias" terms
%

% You need to return the following variables correctly 
W = zeros(L_out, 1 + L_in);

% ====================== YOUR CODE HERE ======================
% Instructions: Initialize W randomly so that we break the symmetry while
%               training the neural network.
%
% Note: The first column of W corresponds to the parameters for the bias unit
%

% Randomly initialize the weights to small values
% This is good approx. as it is based of from the number of units
epsilon_init = sqrt(6)/sqrt(L_in + L_out);

% The reason we do +2*e - e, is because rand outputs values from 0 to 1,
% and the only way we can get a range from [-e, e] is by doing this method,
% where if rand outputs greater then 0.5 then it is positive and if it
% outputs less then 0.5 it is negative.
W = rand(L_out, 1 + L_in) * 2 * epsilon_init - epsilon_init;







% =========================================================================

end

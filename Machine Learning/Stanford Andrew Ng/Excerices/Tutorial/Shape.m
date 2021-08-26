classdef Shape
    properties
        height
        width
    end
    % Static methods and properties are shared by
    % all Shape objects
    methods(Static)
        function out = setGetNumShapes(data)
            % This value is shared by all objects
            persistent Var;
            % Give Var a default value
            if isempty(Var)
                Var = 0;
            end
            
            % If you receive a value assign it
            if nargin
                Var = Var + data;
            end
            
            % Return the stored value
            out = Var;
        end
    end
    methods
        % This constructor is called when a shape
        % is created
        function obj = Shape(height, width)
            obj.height = height;
            obj.width = width;
            % Increment number of shapes
            obj.setGetNumShapes(1);
        end
        
        % Overload the disp function
        function disp(obj)
            fprintf('Height : %.2f / Width : %.2f\n', ...
                obj.height, obj.width);
        end
        
        function area = getArea(obj)
            area = obj.height * obj.width;
        end
        
        % Overload the gt operator
        % Others to overload
        % mathworks.com/help/matlab/matlab_oop/implementing-operators-for-your-class.html
        function tf = gt(obja, objb)
            tf = obja.getArea > objb.getArea
        end
    end
    
end
% Reference the superclass to inherit from
 
classdef Trapezoid < Shape
    properties
        width2
    end
    
    methods
        % This constructor is called when a trapezoid
        % is created
        function obj = Trapezoid(height, width, width2)
            % Have superclass initialize
            obj@Shape(height, width);
            obj.width2 = width2;
        end
        
        % Overload changed functions
        function disp(obj)
            fprintf('Height : %.2f / Width1 : %.2f / Width2 : %.2f\n', ...
                obj.height, obj.width, obj.width2);
        end
        
        function area = getArea(obj)
            area = (obj.height/2) * (obj.width + obj.width2);
        end
    end
end
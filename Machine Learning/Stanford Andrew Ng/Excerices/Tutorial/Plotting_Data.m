% t = [0:0.01:0.98];
% y1 = sin(2*pi*4*t);
% plot(t, y1);
% 
% y2 = cos(2*pi*4*t);
% % % plot(t,y1);
% % % hold on; %allows plotting of new figurs on top of old ones
% % % plot(t, y2);
% 
% xlabel('time')
% ylabel('value')
% legend('sin', 'cos')
% title('my plot')
% 
% %print -dpng 'myPlot.png' --> for saving the plot
% figure(1); plot(t, y1);
% figure(2); plot(t, y2);
% subplot(1,2,1); %Divides plot into a 1x2 grid, access first element
% plot(t, y1)
% axis([0.5 1 -1 1])
% subplot(1,2,2);
% plot(t, y2);

% A = magic(10);
% 
% imagesc(A) %graphing a matrix
% imagesc(A), colorbar, colormap gray;  %comma chaning of commands
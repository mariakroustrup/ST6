clear all
load Nedogop1
%load matlab
close all

data=data(7,1:1000)*10000; %Ved accelerometer vælg måling 2. Ved EMG vælg måling 7
data=data-data(1);

L = length(data);

Central_port = serial('COM3');  

set(Central_port,'baudrate',115200)

fopen(Central_port)

M = 4;
tic 
for i = 0:M:L-1,
    fwrite(Central_port,data(i+1:i+M),'int16');
    return_data(i+1:i+M) = fread(Central_port,M,'int16');
    hold off
    plot(data(1:i+M))
    hold on
    plot(return_data(1:i+M),'r')
    %axis([0 1000 1250 1500])
    drawnow
end
toc
fclose(Central_port)


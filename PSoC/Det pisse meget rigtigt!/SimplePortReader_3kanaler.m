comport = 'COM3'; % Vælg porten til jeres egen PSoC enhed

serial_port = serial(comport, 'TimeOut', 2, 'BaudRate', 115200);

num_of_channels = 1;    % Antallet af kanaler
data_length = 2000;     % Antal samples per kanal i plottet
data = NaN*ones(data_length,num_of_channels);   % Initialisering af data
byte_per_channel = 2;   % Antal bytes per kanal (int16 = 2 bytes)
samples_per_channel = 15; % Antal samples per læsning fra porten

try 
    fopen(serial_port)          % Dette forsøger at åbne porten
    port_open = 1;
catch err
    disp(err.message)           % Skriv en fejlmeddelelse, hvis det ikke lykkedes
    disp(sprintf('\nTjek enhedshåndteringen (device manager) for at finde jeres egen com-port\n'))
    port_open = 0;
end

if port_open                        % Kør kun nedenstående, hvis porten kunne åbnes
    H = figure(1);                  % Skab en figur
    set(H,'windowstyle','modal')    % Dette tvinger figuren til at være i front
    
    fwrite(serial_port,'S');        % Dette starter ADC'en på PSoC'en
    
    key = '';                       % Variable til at stoppe while-løkken
    
    disp('Data visualisering startet')  % Skriv start-besked i command-vinduet
    while ~strcmp(key,'q')          % while-løkken stoppes ved at trykke på "q"-tasten
        
        data_stream = fread(serial_port,samples_per_channel*num_of_channels,'int16');   % Læs en byte_stream fra porten
        for i = 1:num_of_channels   % Pak byte_stream data ud til en matrice
            data(:,i) = [data(samples_per_channel+1:end,i) ; data_stream(i:num_of_channels:end)];
        end
        plot(data)                              % Plot data
        %axis([0 data_length 0 2^11+50])  % Tilpas akserne
        legend('Y1-akse','Y2-akse','EMG');
        drawnow                                 % Tving grafen til at opdatere
        
        key = lower(get(H,'CurrentCharacter')); % Læs om der er trykket på en tast
    end
    
    fwrite(serial_port,'E');            % Dette stopper ADC'en på PSoC'en
    fclose(serial_port)                 % Dette lukker porten
    close(H)                            % Luk figuren
    disp('Data visualisering stoppet')  % Skriv slut-besked i command-vinduet
end
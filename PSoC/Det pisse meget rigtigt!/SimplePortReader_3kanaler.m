comport = 'COM3'; % V�lg porten til jeres egen PSoC enhed

serial_port = serial(comport, 'TimeOut', 2, 'BaudRate', 115200);

num_of_channels = 1;    % Antallet af kanaler
data_length = 2000;     % Antal samples per kanal i plottet
data = NaN*ones(data_length,num_of_channels);   % Initialisering af data
byte_per_channel = 2;   % Antal bytes per kanal (int16 = 2 bytes)
samples_per_channel = 15; % Antal samples per l�sning fra porten

try 
    fopen(serial_port)          % Dette fors�ger at �bne porten
    port_open = 1;
catch err
    disp(err.message)           % Skriv en fejlmeddelelse, hvis det ikke lykkedes
    disp(sprintf('\nTjek enhedsh�ndteringen (device manager) for at finde jeres egen com-port\n'))
    port_open = 0;
end

if port_open                        % K�r kun nedenst�ende, hvis porten kunne �bnes
    H = figure(1);                  % Skab en figur
    set(H,'windowstyle','modal')    % Dette tvinger figuren til at v�re i front
    
    fwrite(serial_port,'S');        % Dette starter ADC'en p� PSoC'en
    
    key = '';                       % Variable til at stoppe while-l�kken
    
    disp('Data visualisering startet')  % Skriv start-besked i command-vinduet
    while ~strcmp(key,'q')          % while-l�kken stoppes ved at trykke p� "q"-tasten
        
        data_stream = fread(serial_port,samples_per_channel*num_of_channels,'int16');   % L�s en byte_stream fra porten
        for i = 1:num_of_channels   % Pak byte_stream data ud til en matrice
            data(:,i) = [data(samples_per_channel+1:end,i) ; data_stream(i:num_of_channels:end)];
        end
        plot(data)                              % Plot data
        %axis([0 data_length 0 2^11+50])  % Tilpas akserne
        legend('Y1-akse','Y2-akse','EMG');
        drawnow                                 % Tving grafen til at opdatere
        
        key = lower(get(H,'CurrentCharacter')); % L�s om der er trykket p� en tast
    end
    
    fwrite(serial_port,'E');            % Dette stopper ADC'en p� PSoC'en
    fclose(serial_port)                 % Dette lukker porten
    close(H)                            % Luk figuren
    disp('Data visualisering stoppet')  % Skriv slut-besked i command-vinduet
end
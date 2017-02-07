#include <project.h>
#include <my_defines.h>
#include <stdlib.h>

#define FALSE 0
#define TRUE 1
#define LED_ON 0u
#define LED_OFF 1u

/* Moving average filter */ 
#define FILTER_LENGTH_mavg 9
//int y[2] = {0,0};
double x_mavg[FILTER_LENGTH_mavg+1] = {0,0,0,0,0,0,0,0,0,0};
double y_mavg = 0;
double b_mavg[FILTER_LENGTH_mavg+1] = {0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1};

/* Moving average filter 2 */
#define FILTER_LENGTH_mavg2 9
//int y2[2] = {0,0};
double x2_mavg[FILTER_LENGTH_mavg2+1] = {0,0,0,0,0,0,0,0,0,0};
double y2_mavg = 0;
double b2_mavg[FILTER_LENGTH_mavg2+1] = {0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1};


#define FILTER_LENGTH_low 2
double x_low[FILTER_LENGTH_low+1] = {0, 0, 0};
double y_low[FILTER_LENGTH_low+1] = {0, 0, 0};
double b_low[FILTER_LENGTH_low+1] = {0.0015, 0.0029, 0.0015};
double a_low[FILTER_LENGTH_low+1] = {1.0000, -1.8890, 0.8949};

int16 value_in,value_out[2]={0,0},i=0,j=0,k=0, grader_groen, grader_roed, diff, old, counter=0; 
char FirstCall = TRUE;

int16 accroed = {0};
int16 accgroen = {0};
int16 EMG = {0};
int16 result[num_of_channels] = {0};
uint8 data_ready = FALSE;

int16 mav_data_roed;
int16 mav_data_groen;
int16 samlet_grader;
int16 low_pass_data;
int16 diff_data;

#define StartMeasuring Control_Reg_1_Write(1);
#define StopMeasuring Control_Reg_1_Write(0);

/***********************************Konvertering til heltal***********************************/
signed int floor_and_convert(double y)
{
   if (y > 0)
      return (signed int) (y+0.5);
   else
      return (signed int) (y-0.5);
}

/***********************************Moving average filter***********************************/
signed int mavg_filter(signed int data)
{
   x_mavg[0] =  (double) data;      // Read received sample and perform type casts
   
   y_mavg = 0;
   for(i = 0;i <= FILTER_LENGTH_mavg;i++)           // Run FIR filter for each received sample
   {
      y_mavg += b_mavg[i]*x_mavg[i];
   } 
   
   for(i = FILTER_LENGTH_mavg-1;i >= 0;i--)         // Roll x array in order to hold old sample inputs
   {
      x_mavg[i+1] = x_mavg[i];
   }
  
   return (signed int) y_mavg;
}


/***********************************Moving average filter 2***********************************/
signed int mavg_filter2(signed int data)
{
   x2_mavg[0] =  (int16) data;                     // Read received sample and perform type casts
    
   y2_mavg = 0;                                     // 
   for(i = 0;i <= FILTER_LENGTH_mavg2;i++)          // Run FIR filter for each received sample
   {
      y2_mavg += b2_mavg[i]*x2_mavg[i];             // 
   } 
   
   for(i = FILTER_LENGTH_mavg2-1;i >= 0;i--)        // Roll x array in order to hold old sample inputs
   {
      x2_mavg[i+1] = x2_mavg[i];                    // 
   }
      
   return (signed int) y2_mavg;               // 
}

/***********************************Lavpas filter***********************************/

int low_pass_filter(int data)
{
   int i;
   x_low[0] =  (double) data;
   
   y_low[0] = 0;
  
   for(i = 0;i <= FILTER_LENGTH_low;i++)                 // Run IIR filter for each received sample
   {
      y_low[0] += b_low[i]*x_low[i];
   } 
   
   for(i =1;i <= FILTER_LENGTH_low;i++)               // Roll x array in order to hold old sample inputs
   {
      y_low[0] -= a_low[i]*y_low[i];
   }

   for(i = FILTER_LENGTH_low-1;i >= 0;i--)
    {
       x_low[i+1]=x_low[i];
       y_low[i+1]=y_low[i];
    }

    return floor_and_convert(y_low[0]);
}


/***********************************Omregning til grader for rød***********************************/
int roed_grader(int data)
{
    if ((data >= -204) && (data <= -202))
    {
        data=(0+(10-0)*(data-(-202))/(-204-(-202)));
        return data; 
    }
    else if ((data >= -202) && (data <= -185))
    {
        data=(10+(30-10)*(data-(-202))/(-185-(-202)));
        return data;
    }
    else if ((data >= -185) && (data <= -143))
    {
        data=(30+(50-30)*(data-(-185))/(-143-(-185)));
        return data;
    }
    else if ((data >= -143) && (data <= -84))
    {
        data=(50+(70-50)*(data-(-143))/(-84-(-143)));
        return data;
    }
    else if ((data >= -84) && (data <= -48))
    {
        data=(70+(80-70)*(data-(-84))/(-48-(-84)));
        return data;
    }
    else if ((data >= -48) && (data <= -7))
    {
        data=(80+(90-80)*(data-(-48))/(-7-(-48)));
        return data;
    }
    
    else
    {
        data = -200;
        return data;
    }
}

/***********************************Omregning til grader for grøn***********************************/
int groen_grader(int data)
{
    if ((data >= -190) && (data <= -186))
    {
        data=(0+(10-0)*(data-(-190))/(-186-(-190)));
        return data; 
    }
    else if ((data >= -186) && (data <= -163))
    {
        data=(10+(30-10)*(data-(-186))/(-163-(-186)));
        return data;
    }
    else if ((data >= -163) && (data <= -121))
    {
        data=(30+(50-30)*(data-(-163))/(-121-(-163)));
        return data;
    }
    else if ((data >= -121) && (data <= -62))
    {
        data=(50+(70-50)*(data-(-121))/(-62-(-121)));
        return data;
    }
    else if ((data >= -62) && (data <= -26))
    {
        data=(70+(80-70)*(data-(-62))/(-26-(-62)));
        return data;
    }
    else if ((data >= -26) && (data <=11))
    {
        data=(80+(90-80)*(data-(-26))/(11-(-26)));
        return data;
    }
    
    else
    {
        data = -200;
        
        return data;
    }
}

/***********************************Differentiator***********************************/

signed int differentiator(signed int new)
{
   diff = (new - old)/0.01;
   old = new;
   //return diff;
    if(diff > 1)        //Stigende 
    {
        diff = 10;
        return diff;
    }
    
    else if (diff < 1)  //faldene 
    {
        diff = -10;
        return diff;
    }
    
    else
    {
        diff=-200;
        return diff;
    }
}

/***********************************ISR***********************************/

CY_ISR(ADC_interrupt)
{  
    accroed = ADC_GetResult16(0)-1010+7;
    accgroen = ADC_GetResult16(1)-1024+52;
    EMG = ADC_GetResult16(2);
    
    data_ready = TRUE;
    
    /* Databehandling */
         
    if(data_ready)
    {   
        /* Accelerometerne*/
        StartMeasuring
        mav_data_roed = mavg_filter(accroed);           //Moving average filter til kanal 1
        grader_roed = roed_grader(mav_data_roed);       //Grader tages til det filtrede signal
        
        mav_data_groen = mavg_filter2(accgroen);        //Moving average filter til kanal 2
        grader_groen = groen_grader(mav_data_groen);    //Grader tages til det filtrede signal

        samlet_grader=grader_roed+grader_groen;         //Graderne lægges sammen, således vinklen over knæet visualiseres
              
        StopMeasuring
        
         if (samlet_grader<90)
        {
            Pin_Green_Write(LED_OFF); 
            Pin_Red_Write(LED_ON);
        }
        if (samlet_grader>90)
        {
            Pin_Red_Write(LED_OFF);
            Pin_Green_Write(LED_ON);
        }
        if (k==1)
        {
           Pin_Red_Write(LED_OFF);
           Pin_Green_Write(LED_OFF);
        }
        
        /* EMG */
        low_pass_data = low_pass_filter(EMG);        
        diff_data = differentiator(low_pass_data);
            
        
        UART_UartPutChar(samlet_grader);                //plottes tilbage i MATLAB
        UART_UartPutChar(samlet_grader>>8);
      
       
        //UART_UartPutChar(counter);             
        //UART_UartPutChar(counter>>8);
        
        //UART_UartPutChar(diff_data);
        //UART_UartPutChar(diff_data>>8);
        
        data_ready = FALSE;
    }
}

CY_ISR(Pin_SW2_handler)
{
    
    if (j==0)
    {
        k=0;
        Pin_Green_Write(LED_ON);
        Pin_Red_Write(LED_OFF);
        ADC_StartConvert();
        j++; 
    }
    
    else 
    {
        ADC_StopConvert();
        Pin_Red_Write(LED_OFF);
        Pin_Green_Write(LED_OFF);
        j=0;
        k=1;
    }
    
    Pin_SW2_ClearInterrupt(); 
}

CY_ISR(Timer_interrupt)
{
    counter=Timer_1_ReadCapture();
}

/***********************************Main***********************************/
int main()
{   
    pin_SW2_int_StartEx(Pin_SW2_handler);
    ADC_ISR_StartEx(ADC_interrupt);
    Timer_ISR_StartEx(Timer_interrupt);
    CyGlobalIntEnable;
    UART_Start();                       /* Initialize ADC */    
    ADC_Start();                        /* Initialize ADC */
    Timer_1_Start();
    Timer_ISR_Enable();
    ADC_IRQ_Enable();                   /* Enable ADC interrupts */   
    CySysPmSleep();
    return 0;  
}
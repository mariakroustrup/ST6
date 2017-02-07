#include <project.h>
#include <my_defines.h>
#include <stdlib.h>

#define LOWBYTE 0                                   // Used to toggle between HIGH byte and LOW byte
#define HIGHBYTE 1                                  // Used to toggle between HIGH byte and LOW byte
#define FALSE 0
#define TRUE 1

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

/* Lavpas filter*/
#define FILTER_LENGTH_low 2
double x_low[FILTER_LENGTH_low+1] = {0, 0, 0};
double y_low[FILTER_LENGTH_low+1] = {0, 0, 0};
double b_low[FILTER_LENGTH_low+1] = {0.0201, 0.0402, 0.0201};
double a_low[FILTER_LENGTH_low+1] = {1.0000, -1.5610, 0.6414};

char current_byte = LOWBYTE;                        // Receive LOW byte first as default
int16 value_in,value_out[2]={0,0},i=0,j=0, avg_new, gradergroen; 
char FirstCall = TRUE;

int16 accroed = {0};
int16 accgroen = {0};
int16 result[num_of_channels] = {0};
uint8 data_ready = FALSE;
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

/***********************************Omregning til grader***********************************/

//int grader(int data)
//{
//    if ((data < 194) && (data>= 159))
//    {
//        data=(90+(80-90)*(data-194)/(159-194));
//        return data; 
//    }
//    else if((data < 159) && (data >= 96))
//    {
//        data=((80+(60-80))*(data-159)/(96-159));
//        return data;
//    }
//    else if((data < 96) && (data >= 44))
//    {
//        data=((60+(40-60))*(data-96)/(44-96));
//        return data;
//    }
//    else if((data < 44) && (data >= 12))
//    {
//        data =((40+(20-40))*(data-44)/(12-44));
//        return data;
//    }
//    else if ((data < 12) && (data >= 0))
//    {
//        data =((20+(0-20))*(data-12)/(0-12));
//       return data;
//    }
//   else
//    {
//        return data;
//    }
//}

int grader(int data)
{
    if ((data <= 1) && (data >= -35))
    {
        data=(0+(10-0)*(data-1)/(-35-1));
        return data; 
    }
    else if ((data <= -35) && (data >= -96))
    {
        data=(10+(30-10)*(data-(-35))/(-96-(-35)));
        return data;
    }
    else if ((data <= -96) && (data >= -147))
    {
        data=(30+(50-30)*(data-(-96))/(-147-(-96)));
        return data;
    }
    else if ((data <= -147) && (data >= -180))
    {
        data=(50+(70-50)*(data-(-147))/(-180-(-147)));
        return data;
    }
    else if ((data <= -180) && (data >= -189))
    {
        data=(70+(80-70)*(data-(-180))/(-189-(-180)));
        return data;
    }
    else if ((data <= -189) && (data >= -193))
    {
        data=(80+(90-80)*(data-(-189))/(-193-(-189)));
        return data;
    }
    
    else
    {
        data = -200;
        return data;
    }
}

/***********************************ISR***********************************/

CY_ISR(ADC_interrupt)
{
    accgroen = ADC_GetResult16(0)-1024+52;
    //accgroen = ADC_GetResult16(0)-1010+232;
         
//    int i;
//    for(i=0; i<num_of_channels;i++)
//    {
//        result[i] = ADC_GetResult16(i)-1024+52;
//        accroed=result[0];
//       
//    }
    data_ready = TRUE;
}

/***********************************Main***********************************/
int main()
{
    int16 mav_data;
    ADC_ISR_StartEx(ADC_interrupt);
    CyGlobalIntEnable;
    UART_Start();                       /* Initialize ADC */    
    ADC_Start();                        /* Initialize ADC */
    ADC_StartConvert();                 /* Start ADC conversions */
    ADC_IRQ_Enable();                   /* Enable ADC interrupts */    
    
    for (;;)
    {      
        if(data_ready)
        {  
            mav_data = mavg_filter(accgroen);
            gradergroen = grader(mav_data);
            //UART_UartPutChar(mav_data);
            //UART_UartPutChar(mav_data>>8);
            UART_UartPutChar(gradergroen);
            UART_UartPutChar(gradergroen>>8);

            data_ready = FALSE;
        }
    }
}
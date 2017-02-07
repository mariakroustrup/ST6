/* ========================================
 *
 * UART echo with interrupt
 *
 * ========================================
*/
#include <project.h>
#include <stdlib.h>
#define LOWBYTE 0                                   // Used to toggle between HIGH byte and LOW byte
#define HIGHBYTE 1                                  // Used to toggle between HIGH byte and LOW byte
#define FALSE 0
#define TRUE 1

#define StartMeasuring Measure_When_Low_Write(0);   // Used to start the Timer
#define StopMeasuring Measure_When_Low_Write(1);    // Used to stop the Timer and capture the time spent

/* Lavpas filter*/
#define FILTER_LENGTH_low 2
double x_low[FILTER_LENGTH_low+1] = {0, 0, 0};
double y_low[FILTER_LENGTH_low+1] = {0, 0, 0};
double b_low[FILTER_LENGTH_low+1] = {0.0201, 0.0402, 0.0201};
double a_low[FILTER_LENGTH_low+1] = {1.0000, -1.5610, 0.6414};

char current_byte = LOWBYTE;                        // Receive LOW byte first as default
int16 value_in,value_out[2]={0,0},i=0,j=0,diff,signed_data,old, test_data;
uint16 counter = 0;                                     // Variable for captured time



signed int floor_and_convert(double y)
{
   if (y > 0)
      return (signed int) (y+0.5);
   else
      return (signed int) (y-0.5);
}


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


CY_ISR(RX_interrupt)
{
    //char str[4];
    uint8 ch = UART_UartGetChar();
    UART_ClearRxInterruptSource(UART_GetRxInterruptSource());
    
    if(current_byte == LOWBYTE)                     // Receive LOW byte first
    {
        
        
        value_in = ch;                              // Save UCA0RXBUF in the LOW byte of "value"
        current_byte = HIGHBYTE;                    // Prepare to receive HIGH byte on next interrupt
        return;                                     // Return from interrupt
    }
    if(current_byte == HIGHBYTE)                    // Recieve HIGH byte
    {
        
        value_in += (ch<<8);                        // Save UCA0RXBUF in the HIGH byte of "value"
        current_byte = LOWBYTE;                     // Prepare to receive LOW byte on next interrupt
                
        StartMeasuring
            test_data = low_pass_filter(value_in);
            signed_data = differentiator(test_data);          // Applay differentiator
            
            value_out[1] = value_out[0];
            value_out[0] = signed_data;
        
        StopMeasuring
        
        

        #ifdef TRANSMIT_TIME
        UART_UartPutChar(counter);                    // Send back lowbyte of value
        UART_UartPutChar((counter>>8));               // Send back highbyte of value
        #else
        UART_UartPutChar(value_out[0]);
        UART_UartPutChar(value_out[0]>>8);
        #endif
        
    
    }
}


CY_ISR(Timer_interrupt)
{
    counter = Timer_ReadCapture();   
}


int main()
{
    CyGlobalIntEnable;                          /* Enable global interrupts */
    Timer_ISR_StartEx(Timer_interrupt);         /* Set interrupt address */
    UART_RX_ISR_StartEx(RX_interrupt);          /* Set interrupt address */
  //  UART_BLE_RX_ISR_StartEx(BLE_RX_interrupt);  /* Set interrupt address */
    UART_Start();                               /* Start UART */
    UART_BLE_Start();                           /* Start UART_BLE */
    Timer_Start();                              /* Start Timer */
    Timer_ISR_Enable();                         /* Enable Timer interrupt service routine*/
    CySysPmSleep();                             /* Enter Sleep mode */
    return 0;
}


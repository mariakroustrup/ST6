/* ========================================
 *
 * UART echo with interrupt
 *
 * ========================================
*/
#include <project.h>

CY_ISR(RX_interrupt)
{
    uint8 ch = UART_UartGetChar();
    UART_ClearRxInterruptSource(UART_GetRxInterruptSource());
    
    /* her kan vi kode!!!!!!!!!!!!!!! */
     
    UART_BLE_UartPutChar(ch);
    BLUE_LED_Write(~BLUE_LED_Read());
}

CY_ISR(BLE_RX_interrupt)
{
    uint8 ch = UART_BLE_UartGetChar();
    UART_BLE_ClearRxInterruptSource(UART_BLE_GetRxInterruptSource());
    
    
    UART_UartPutChar(ch);
}

int main()
{
    CyGlobalIntEnable;                  /* Enable global interrupts */
    UART_RX_ISR_StartEx(RX_interrupt);  /* Set interrupt address */
    UART_BLE_RX_ISR_StartEx(BLE_RX_interrupt);  /* Set interrupt address */
    UART_Start();                       /* Start UART */
    UART_BLE_Start();                       /* Start UART */
    CySysPmSleep();                     /* Enter Sleep mode */
    return 0;
}


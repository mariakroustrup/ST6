/*******************************************************************************
* File Name: Measure_When_Low_PM.c
* Version 1.80
*
* Description:
*  This file contains the setup, control, and status commands to support 
*  the component operation in the low power mode. 
*
* Note:
*
********************************************************************************
* Copyright 2015, Cypress Semiconductor Corporation.  All rights reserved.
* You may use this file only in accordance with the license, terms, conditions, 
* disclaimers, and limitations in the end user license agreement accompanying 
* the software package with which this file was provided.
*******************************************************************************/

#include "Measure_When_Low.h"

/* Check for removal by optimization */
#if !defined(Measure_When_Low_Sync_ctrl_reg__REMOVED)

static Measure_When_Low_BACKUP_STRUCT  Measure_When_Low_backup = {0u};

    
/*******************************************************************************
* Function Name: Measure_When_Low_SaveConfig
********************************************************************************
*
* Summary:
*  Saves the control register value.
*
* Parameters:
*  None
*
* Return:
*  None
*
*******************************************************************************/
void Measure_When_Low_SaveConfig(void) 
{
    Measure_When_Low_backup.controlState = Measure_When_Low_Control;
}


/*******************************************************************************
* Function Name: Measure_When_Low_RestoreConfig
********************************************************************************
*
* Summary:
*  Restores the control register value.
*
* Parameters:
*  None
*
* Return:
*  None
*
*
*******************************************************************************/
void Measure_When_Low_RestoreConfig(void) 
{
     Measure_When_Low_Control = Measure_When_Low_backup.controlState;
}


/*******************************************************************************
* Function Name: Measure_When_Low_Sleep
********************************************************************************
*
* Summary:
*  Prepares the component for entering the low power mode.
*
* Parameters:
*  None
*
* Return:
*  None
*
*******************************************************************************/
void Measure_When_Low_Sleep(void) 
{
    Measure_When_Low_SaveConfig();
}


/*******************************************************************************
* Function Name: Measure_When_Low_Wakeup
********************************************************************************
*
* Summary:
*  Restores the component after waking up from the low power mode.
*
* Parameters:
*  None
*
* Return:
*  None
*
*******************************************************************************/
void Measure_When_Low_Wakeup(void)  
{
    Measure_When_Low_RestoreConfig();
}

#endif /* End check for removal by optimization */


/* [] END OF FILE */

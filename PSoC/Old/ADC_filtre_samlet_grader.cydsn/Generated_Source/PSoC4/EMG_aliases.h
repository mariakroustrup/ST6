/*******************************************************************************
* File Name: EMG.h  
* Version 2.20
*
* Description:
*  This file contains the Alias definitions for Per-Pin APIs in cypins.h. 
*  Information on using these APIs can be found in the System Reference Guide.
*
* Note:
*
********************************************************************************
* Copyright 2008-2015, Cypress Semiconductor Corporation.  All rights reserved.
* You may use this file only in accordance with the license, terms, conditions, 
* disclaimers, and limitations in the end user license agreement accompanying 
* the software package with which this file was provided.
*******************************************************************************/

#if !defined(CY_PINS_EMG_ALIASES_H) /* Pins EMG_ALIASES_H */
#define CY_PINS_EMG_ALIASES_H

#include "cytypes.h"
#include "cyfitter.h"
#include "cypins.h"


/***************************************
*              Constants        
***************************************/
#define EMG_0			(EMG__0__PC)
#define EMG_0_PS		(EMG__0__PS)
#define EMG_0_PC		(EMG__0__PC)
#define EMG_0_DR		(EMG__0__DR)
#define EMG_0_SHIFT	(EMG__0__SHIFT)
#define EMG_0_INTR	((uint16)((uint16)0x0003u << (EMG__0__SHIFT*2u)))

#define EMG_INTR_ALL	 ((uint16)(EMG_0_INTR))


#endif /* End Pins EMG_ALIASES_H */


/* [] END OF FILE */

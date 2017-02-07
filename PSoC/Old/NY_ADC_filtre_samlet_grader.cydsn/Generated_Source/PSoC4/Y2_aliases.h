/*******************************************************************************
* File Name: Y2.h  
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

#if !defined(CY_PINS_Y2_ALIASES_H) /* Pins Y2_ALIASES_H */
#define CY_PINS_Y2_ALIASES_H

#include "cytypes.h"
#include "cyfitter.h"
#include "cypins.h"


/***************************************
*              Constants        
***************************************/
#define Y2_0			(Y2__0__PC)
#define Y2_0_PS		(Y2__0__PS)
#define Y2_0_PC		(Y2__0__PC)
#define Y2_0_DR		(Y2__0__DR)
#define Y2_0_SHIFT	(Y2__0__SHIFT)
#define Y2_0_INTR	((uint16)((uint16)0x0003u << (Y2__0__SHIFT*2u)))

#define Y2_INTR_ALL	 ((uint16)(Y2_0_INTR))


#endif /* End Pins Y2_ALIASES_H */


/* [] END OF FILE */

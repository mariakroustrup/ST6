/*******************************************************************************
* File Name: Z_pos.h  
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

#if !defined(CY_PINS_Z_pos_ALIASES_H) /* Pins Z_pos_ALIASES_H */
#define CY_PINS_Z_pos_ALIASES_H

#include "cytypes.h"
#include "cyfitter.h"
#include "cypins.h"


/***************************************
*              Constants        
***************************************/
#define Z_pos_0			(Z_pos__0__PC)
#define Z_pos_0_PS		(Z_pos__0__PS)
#define Z_pos_0_PC		(Z_pos__0__PC)
#define Z_pos_0_DR		(Z_pos__0__DR)
#define Z_pos_0_SHIFT	(Z_pos__0__SHIFT)
#define Z_pos_0_INTR	((uint16)((uint16)0x0003u << (Z_pos__0__SHIFT*2u)))

#define Z_pos_INTR_ALL	 ((uint16)(Z_pos_0_INTR))


#endif /* End Pins Z_pos_ALIASES_H */


/* [] END OF FILE */

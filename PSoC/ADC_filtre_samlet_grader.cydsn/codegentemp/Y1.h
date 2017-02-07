/*******************************************************************************
* File Name: Y1.h  
* Version 2.20
*
* Description:
*  This file contains Pin function prototypes and register defines
*
********************************************************************************
* Copyright 2008-2015, Cypress Semiconductor Corporation.  All rights reserved.
* You may use this file only in accordance with the license, terms, conditions, 
* disclaimers, and limitations in the end user license agreement accompanying 
* the software package with which this file was provided.
*******************************************************************************/

#if !defined(CY_PINS_Y1_H) /* Pins Y1_H */
#define CY_PINS_Y1_H

#include "cytypes.h"
#include "cyfitter.h"
#include "Y1_aliases.h"


/***************************************
*     Data Struct Definitions
***************************************/

/**
* \addtogroup group_structures
* @{
*/
    
/* Structure for sleep mode support */
typedef struct
{
    uint32 pcState; /**< State of the port control register */
    uint32 sioState; /**< State of the SIO configuration */
    uint32 usbState; /**< State of the USBIO regulator */
} Y1_BACKUP_STRUCT;

/** @} structures */


/***************************************
*        Function Prototypes             
***************************************/
/**
* \addtogroup group_general
* @{
*/
uint8   Y1_Read(void);
void    Y1_Write(uint8 value);
uint8   Y1_ReadDataReg(void);
#if defined(Y1__PC) || (CY_PSOC4_4200L) 
    void    Y1_SetDriveMode(uint8 mode);
#endif
void    Y1_SetInterruptMode(uint16 position, uint16 mode);
uint8   Y1_ClearInterrupt(void);
/** @} general */

/**
* \addtogroup group_power
* @{
*/
void Y1_Sleep(void); 
void Y1_Wakeup(void);
/** @} power */


/***************************************
*           API Constants        
***************************************/
#if defined(Y1__PC) || (CY_PSOC4_4200L) 
    /* Drive Modes */
    #define Y1_DRIVE_MODE_BITS        (3)
    #define Y1_DRIVE_MODE_IND_MASK    (0xFFFFFFFFu >> (32 - Y1_DRIVE_MODE_BITS))

    /**
    * \addtogroup group_constants
    * @{
    */
        /** \addtogroup driveMode Drive mode constants
         * \brief Constants to be passed as "mode" parameter in the Y1_SetDriveMode() function.
         *  @{
         */
        #define Y1_DM_ALG_HIZ         (0x00u) /**< \brief High Impedance Analog   */
        #define Y1_DM_DIG_HIZ         (0x01u) /**< \brief High Impedance Digital  */
        #define Y1_DM_RES_UP          (0x02u) /**< \brief Resistive Pull Up       */
        #define Y1_DM_RES_DWN         (0x03u) /**< \brief Resistive Pull Down     */
        #define Y1_DM_OD_LO           (0x04u) /**< \brief Open Drain, Drives Low  */
        #define Y1_DM_OD_HI           (0x05u) /**< \brief Open Drain, Drives High */
        #define Y1_DM_STRONG          (0x06u) /**< \brief Strong Drive            */
        #define Y1_DM_RES_UPDWN       (0x07u) /**< \brief Resistive Pull Up/Down  */
        /** @} driveMode */
    /** @} group_constants */
#endif

/* Digital Port Constants */
#define Y1_MASK               Y1__MASK
#define Y1_SHIFT              Y1__SHIFT
#define Y1_WIDTH              1u

/**
* \addtogroup group_constants
* @{
*/
    /** \addtogroup intrMode Interrupt constants
     * \brief Constants to be passed as "mode" parameter in Y1_SetInterruptMode() function.
     *  @{
     */
        #define Y1_INTR_NONE      ((uint16)(0x0000u)) /**< \brief Disabled             */
        #define Y1_INTR_RISING    ((uint16)(0x5555u)) /**< \brief Rising edge trigger  */
        #define Y1_INTR_FALLING   ((uint16)(0xaaaau)) /**< \brief Falling edge trigger */
        #define Y1_INTR_BOTH      ((uint16)(0xffffu)) /**< \brief Both edge trigger    */
    /** @} intrMode */
/** @} group_constants */

/* SIO LPM definition */
#if defined(Y1__SIO)
    #define Y1_SIO_LPM_MASK       (0x03u)
#endif

/* USBIO definitions */
#if !defined(Y1__PC) && (CY_PSOC4_4200L)
    #define Y1_USBIO_ENABLE               ((uint32)0x80000000u)
    #define Y1_USBIO_DISABLE              ((uint32)(~Y1_USBIO_ENABLE))
    #define Y1_USBIO_SUSPEND_SHIFT        CYFLD_USBDEVv2_USB_SUSPEND__OFFSET
    #define Y1_USBIO_SUSPEND_DEL_SHIFT    CYFLD_USBDEVv2_USB_SUSPEND_DEL__OFFSET
    #define Y1_USBIO_ENTER_SLEEP          ((uint32)((1u << Y1_USBIO_SUSPEND_SHIFT) \
                                                        | (1u << Y1_USBIO_SUSPEND_DEL_SHIFT)))
    #define Y1_USBIO_EXIT_SLEEP_PH1       ((uint32)~((uint32)(1u << Y1_USBIO_SUSPEND_SHIFT)))
    #define Y1_USBIO_EXIT_SLEEP_PH2       ((uint32)~((uint32)(1u << Y1_USBIO_SUSPEND_DEL_SHIFT)))
    #define Y1_USBIO_CR1_OFF              ((uint32)0xfffffffeu)
#endif


/***************************************
*             Registers        
***************************************/
/* Main Port Registers */
#if defined(Y1__PC)
    /* Port Configuration */
    #define Y1_PC                 (* (reg32 *) Y1__PC)
#endif
/* Pin State */
#define Y1_PS                     (* (reg32 *) Y1__PS)
/* Data Register */
#define Y1_DR                     (* (reg32 *) Y1__DR)
/* Input Buffer Disable Override */
#define Y1_INP_DIS                (* (reg32 *) Y1__PC2)

/* Interrupt configuration Registers */
#define Y1_INTCFG                 (* (reg32 *) Y1__INTCFG)
#define Y1_INTSTAT                (* (reg32 *) Y1__INTSTAT)

/* "Interrupt cause" register for Combined Port Interrupt (AllPortInt) in GSRef component */
#if defined (CYREG_GPIO_INTR_CAUSE)
    #define Y1_INTR_CAUSE         (* (reg32 *) CYREG_GPIO_INTR_CAUSE)
#endif

/* SIO register */
#if defined(Y1__SIO)
    #define Y1_SIO_REG            (* (reg32 *) Y1__SIO)
#endif /* (Y1__SIO_CFG) */

/* USBIO registers */
#if !defined(Y1__PC) && (CY_PSOC4_4200L)
    #define Y1_USB_POWER_REG       (* (reg32 *) CYREG_USBDEVv2_USB_POWER_CTRL)
    #define Y1_CR1_REG             (* (reg32 *) CYREG_USBDEVv2_CR1)
    #define Y1_USBIO_CTRL_REG      (* (reg32 *) CYREG_USBDEVv2_USB_USBIO_CTRL)
#endif    
    
    
/***************************************
* The following code is DEPRECATED and 
* must not be used in new designs.
***************************************/
/**
* \addtogroup group_deprecated
* @{
*/
#define Y1_DRIVE_MODE_SHIFT       (0x00u)
#define Y1_DRIVE_MODE_MASK        (0x07u << Y1_DRIVE_MODE_SHIFT)
/** @} deprecated */

#endif /* End Pins Y1_H */


/* [] END OF FILE */

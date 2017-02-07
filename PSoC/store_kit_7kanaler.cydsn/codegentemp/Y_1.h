/*******************************************************************************
* File Name: Y_1.h  
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

#if !defined(CY_PINS_Y_1_H) /* Pins Y_1_H */
#define CY_PINS_Y_1_H

#include "cytypes.h"
#include "cyfitter.h"
#include "Y_1_aliases.h"


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
} Y_1_BACKUP_STRUCT;

/** @} structures */


/***************************************
*        Function Prototypes             
***************************************/
/**
* \addtogroup group_general
* @{
*/
uint8   Y_1_Read(void);
void    Y_1_Write(uint8 value);
uint8   Y_1_ReadDataReg(void);
#if defined(Y_1__PC) || (CY_PSOC4_4200L) 
    void    Y_1_SetDriveMode(uint8 mode);
#endif
void    Y_1_SetInterruptMode(uint16 position, uint16 mode);
uint8   Y_1_ClearInterrupt(void);
/** @} general */

/**
* \addtogroup group_power
* @{
*/
void Y_1_Sleep(void); 
void Y_1_Wakeup(void);
/** @} power */


/***************************************
*           API Constants        
***************************************/
#if defined(Y_1__PC) || (CY_PSOC4_4200L) 
    /* Drive Modes */
    #define Y_1_DRIVE_MODE_BITS        (3)
    #define Y_1_DRIVE_MODE_IND_MASK    (0xFFFFFFFFu >> (32 - Y_1_DRIVE_MODE_BITS))

    /**
    * \addtogroup group_constants
    * @{
    */
        /** \addtogroup driveMode Drive mode constants
         * \brief Constants to be passed as "mode" parameter in the Y_1_SetDriveMode() function.
         *  @{
         */
        #define Y_1_DM_ALG_HIZ         (0x00u) /**< \brief High Impedance Analog   */
        #define Y_1_DM_DIG_HIZ         (0x01u) /**< \brief High Impedance Digital  */
        #define Y_1_DM_RES_UP          (0x02u) /**< \brief Resistive Pull Up       */
        #define Y_1_DM_RES_DWN         (0x03u) /**< \brief Resistive Pull Down     */
        #define Y_1_DM_OD_LO           (0x04u) /**< \brief Open Drain, Drives Low  */
        #define Y_1_DM_OD_HI           (0x05u) /**< \brief Open Drain, Drives High */
        #define Y_1_DM_STRONG          (0x06u) /**< \brief Strong Drive            */
        #define Y_1_DM_RES_UPDWN       (0x07u) /**< \brief Resistive Pull Up/Down  */
        /** @} driveMode */
    /** @} group_constants */
#endif

/* Digital Port Constants */
#define Y_1_MASK               Y_1__MASK
#define Y_1_SHIFT              Y_1__SHIFT
#define Y_1_WIDTH              1u

/**
* \addtogroup group_constants
* @{
*/
    /** \addtogroup intrMode Interrupt constants
     * \brief Constants to be passed as "mode" parameter in Y_1_SetInterruptMode() function.
     *  @{
     */
        #define Y_1_INTR_NONE      ((uint16)(0x0000u)) /**< \brief Disabled             */
        #define Y_1_INTR_RISING    ((uint16)(0x5555u)) /**< \brief Rising edge trigger  */
        #define Y_1_INTR_FALLING   ((uint16)(0xaaaau)) /**< \brief Falling edge trigger */
        #define Y_1_INTR_BOTH      ((uint16)(0xffffu)) /**< \brief Both edge trigger    */
    /** @} intrMode */
/** @} group_constants */

/* SIO LPM definition */
#if defined(Y_1__SIO)
    #define Y_1_SIO_LPM_MASK       (0x03u)
#endif

/* USBIO definitions */
#if !defined(Y_1__PC) && (CY_PSOC4_4200L)
    #define Y_1_USBIO_ENABLE               ((uint32)0x80000000u)
    #define Y_1_USBIO_DISABLE              ((uint32)(~Y_1_USBIO_ENABLE))
    #define Y_1_USBIO_SUSPEND_SHIFT        CYFLD_USBDEVv2_USB_SUSPEND__OFFSET
    #define Y_1_USBIO_SUSPEND_DEL_SHIFT    CYFLD_USBDEVv2_USB_SUSPEND_DEL__OFFSET
    #define Y_1_USBIO_ENTER_SLEEP          ((uint32)((1u << Y_1_USBIO_SUSPEND_SHIFT) \
                                                        | (1u << Y_1_USBIO_SUSPEND_DEL_SHIFT)))
    #define Y_1_USBIO_EXIT_SLEEP_PH1       ((uint32)~((uint32)(1u << Y_1_USBIO_SUSPEND_SHIFT)))
    #define Y_1_USBIO_EXIT_SLEEP_PH2       ((uint32)~((uint32)(1u << Y_1_USBIO_SUSPEND_DEL_SHIFT)))
    #define Y_1_USBIO_CR1_OFF              ((uint32)0xfffffffeu)
#endif


/***************************************
*             Registers        
***************************************/
/* Main Port Registers */
#if defined(Y_1__PC)
    /* Port Configuration */
    #define Y_1_PC                 (* (reg32 *) Y_1__PC)
#endif
/* Pin State */
#define Y_1_PS                     (* (reg32 *) Y_1__PS)
/* Data Register */
#define Y_1_DR                     (* (reg32 *) Y_1__DR)
/* Input Buffer Disable Override */
#define Y_1_INP_DIS                (* (reg32 *) Y_1__PC2)

/* Interrupt configuration Registers */
#define Y_1_INTCFG                 (* (reg32 *) Y_1__INTCFG)
#define Y_1_INTSTAT                (* (reg32 *) Y_1__INTSTAT)

/* "Interrupt cause" register for Combined Port Interrupt (AllPortInt) in GSRef component */
#if defined (CYREG_GPIO_INTR_CAUSE)
    #define Y_1_INTR_CAUSE         (* (reg32 *) CYREG_GPIO_INTR_CAUSE)
#endif

/* SIO register */
#if defined(Y_1__SIO)
    #define Y_1_SIO_REG            (* (reg32 *) Y_1__SIO)
#endif /* (Y_1__SIO_CFG) */

/* USBIO registers */
#if !defined(Y_1__PC) && (CY_PSOC4_4200L)
    #define Y_1_USB_POWER_REG       (* (reg32 *) CYREG_USBDEVv2_USB_POWER_CTRL)
    #define Y_1_CR1_REG             (* (reg32 *) CYREG_USBDEVv2_CR1)
    #define Y_1_USBIO_CTRL_REG      (* (reg32 *) CYREG_USBDEVv2_USB_USBIO_CTRL)
#endif    
    
    
/***************************************
* The following code is DEPRECATED and 
* must not be used in new designs.
***************************************/
/**
* \addtogroup group_deprecated
* @{
*/
#define Y_1_DRIVE_MODE_SHIFT       (0x00u)
#define Y_1_DRIVE_MODE_MASK        (0x07u << Y_1_DRIVE_MODE_SHIFT)
/** @} deprecated */

#endif /* End Pins Y_1_H */


/* [] END OF FILE */

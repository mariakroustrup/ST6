/*******************************************************************************
* File Name: EMG.h  
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

#if !defined(CY_PINS_EMG_H) /* Pins EMG_H */
#define CY_PINS_EMG_H

#include "cytypes.h"
#include "cyfitter.h"
#include "EMG_aliases.h"


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
} EMG_BACKUP_STRUCT;

/** @} structures */


/***************************************
*        Function Prototypes             
***************************************/
/**
* \addtogroup group_general
* @{
*/
uint8   EMG_Read(void);
void    EMG_Write(uint8 value);
uint8   EMG_ReadDataReg(void);
#if defined(EMG__PC) || (CY_PSOC4_4200L) 
    void    EMG_SetDriveMode(uint8 mode);
#endif
void    EMG_SetInterruptMode(uint16 position, uint16 mode);
uint8   EMG_ClearInterrupt(void);
/** @} general */

/**
* \addtogroup group_power
* @{
*/
void EMG_Sleep(void); 
void EMG_Wakeup(void);
/** @} power */


/***************************************
*           API Constants        
***************************************/
#if defined(EMG__PC) || (CY_PSOC4_4200L) 
    /* Drive Modes */
    #define EMG_DRIVE_MODE_BITS        (3)
    #define EMG_DRIVE_MODE_IND_MASK    (0xFFFFFFFFu >> (32 - EMG_DRIVE_MODE_BITS))

    /**
    * \addtogroup group_constants
    * @{
    */
        /** \addtogroup driveMode Drive mode constants
         * \brief Constants to be passed as "mode" parameter in the EMG_SetDriveMode() function.
         *  @{
         */
        #define EMG_DM_ALG_HIZ         (0x00u) /**< \brief High Impedance Analog   */
        #define EMG_DM_DIG_HIZ         (0x01u) /**< \brief High Impedance Digital  */
        #define EMG_DM_RES_UP          (0x02u) /**< \brief Resistive Pull Up       */
        #define EMG_DM_RES_DWN         (0x03u) /**< \brief Resistive Pull Down     */
        #define EMG_DM_OD_LO           (0x04u) /**< \brief Open Drain, Drives Low  */
        #define EMG_DM_OD_HI           (0x05u) /**< \brief Open Drain, Drives High */
        #define EMG_DM_STRONG          (0x06u) /**< \brief Strong Drive            */
        #define EMG_DM_RES_UPDWN       (0x07u) /**< \brief Resistive Pull Up/Down  */
        /** @} driveMode */
    /** @} group_constants */
#endif

/* Digital Port Constants */
#define EMG_MASK               EMG__MASK
#define EMG_SHIFT              EMG__SHIFT
#define EMG_WIDTH              1u

/**
* \addtogroup group_constants
* @{
*/
    /** \addtogroup intrMode Interrupt constants
     * \brief Constants to be passed as "mode" parameter in EMG_SetInterruptMode() function.
     *  @{
     */
        #define EMG_INTR_NONE      ((uint16)(0x0000u)) /**< \brief Disabled             */
        #define EMG_INTR_RISING    ((uint16)(0x5555u)) /**< \brief Rising edge trigger  */
        #define EMG_INTR_FALLING   ((uint16)(0xaaaau)) /**< \brief Falling edge trigger */
        #define EMG_INTR_BOTH      ((uint16)(0xffffu)) /**< \brief Both edge trigger    */
    /** @} intrMode */
/** @} group_constants */

/* SIO LPM definition */
#if defined(EMG__SIO)
    #define EMG_SIO_LPM_MASK       (0x03u)
#endif

/* USBIO definitions */
#if !defined(EMG__PC) && (CY_PSOC4_4200L)
    #define EMG_USBIO_ENABLE               ((uint32)0x80000000u)
    #define EMG_USBIO_DISABLE              ((uint32)(~EMG_USBIO_ENABLE))
    #define EMG_USBIO_SUSPEND_SHIFT        CYFLD_USBDEVv2_USB_SUSPEND__OFFSET
    #define EMG_USBIO_SUSPEND_DEL_SHIFT    CYFLD_USBDEVv2_USB_SUSPEND_DEL__OFFSET
    #define EMG_USBIO_ENTER_SLEEP          ((uint32)((1u << EMG_USBIO_SUSPEND_SHIFT) \
                                                        | (1u << EMG_USBIO_SUSPEND_DEL_SHIFT)))
    #define EMG_USBIO_EXIT_SLEEP_PH1       ((uint32)~((uint32)(1u << EMG_USBIO_SUSPEND_SHIFT)))
    #define EMG_USBIO_EXIT_SLEEP_PH2       ((uint32)~((uint32)(1u << EMG_USBIO_SUSPEND_DEL_SHIFT)))
    #define EMG_USBIO_CR1_OFF              ((uint32)0xfffffffeu)
#endif


/***************************************
*             Registers        
***************************************/
/* Main Port Registers */
#if defined(EMG__PC)
    /* Port Configuration */
    #define EMG_PC                 (* (reg32 *) EMG__PC)
#endif
/* Pin State */
#define EMG_PS                     (* (reg32 *) EMG__PS)
/* Data Register */
#define EMG_DR                     (* (reg32 *) EMG__DR)
/* Input Buffer Disable Override */
#define EMG_INP_DIS                (* (reg32 *) EMG__PC2)

/* Interrupt configuration Registers */
#define EMG_INTCFG                 (* (reg32 *) EMG__INTCFG)
#define EMG_INTSTAT                (* (reg32 *) EMG__INTSTAT)

/* "Interrupt cause" register for Combined Port Interrupt (AllPortInt) in GSRef component */
#if defined (CYREG_GPIO_INTR_CAUSE)
    #define EMG_INTR_CAUSE         (* (reg32 *) CYREG_GPIO_INTR_CAUSE)
#endif

/* SIO register */
#if defined(EMG__SIO)
    #define EMG_SIO_REG            (* (reg32 *) EMG__SIO)
#endif /* (EMG__SIO_CFG) */

/* USBIO registers */
#if !defined(EMG__PC) && (CY_PSOC4_4200L)
    #define EMG_USB_POWER_REG       (* (reg32 *) CYREG_USBDEVv2_USB_POWER_CTRL)
    #define EMG_CR1_REG             (* (reg32 *) CYREG_USBDEVv2_CR1)
    #define EMG_USBIO_CTRL_REG      (* (reg32 *) CYREG_USBDEVv2_USB_USBIO_CTRL)
#endif    
    
    
/***************************************
* The following code is DEPRECATED and 
* must not be used in new designs.
***************************************/
/**
* \addtogroup group_deprecated
* @{
*/
#define EMG_DRIVE_MODE_SHIFT       (0x00u)
#define EMG_DRIVE_MODE_MASK        (0x07u << EMG_DRIVE_MODE_SHIFT)
/** @} deprecated */

#endif /* End Pins EMG_H */


/* [] END OF FILE */

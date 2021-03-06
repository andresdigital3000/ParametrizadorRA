
package com.oracle.xmlns.odi.odiinvoke;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OdiStartLoadPlanType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OdiStartLoadPlanType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StartedRunInformation" type="{xmlns.oracle.com/odi/OdiInvoke/}OdiLoadPlanInstanceRunInformationType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OdiStartLoadPlanType", propOrder = {
    "startedRunInformation"
})
public class OdiStartLoadPlanType implements Serializable{

    @XmlElement(name = "StartedRunInformation", required = true)
    protected OdiLoadPlanInstanceRunInformationType startedRunInformation;

    /**
     * Gets the value of the startedRunInformation property.
     * 
     * @return
     *     possible object is
     *     {@link OdiLoadPlanInstanceRunInformationType }
     *     
     */
    public OdiLoadPlanInstanceRunInformationType getStartedRunInformation() {
        return startedRunInformation;
    }

    /**
     * Sets the value of the startedRunInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link OdiLoadPlanInstanceRunInformationType }
     *     
     */
    public void setStartedRunInformation(OdiLoadPlanInstanceRunInformationType value) {
        this.startedRunInformation = value;
    }

}

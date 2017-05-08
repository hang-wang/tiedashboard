//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.04.05 at 10:41:30 AM EDT 
//


package com.tie.xmlprocessor.cbcrxmlprocessor.cbcrxmljaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 			The user has the option to enter the data about the address of a party either as one long field or to spread the data over up to eight  elements or even to use both formats. If the user chooses the option to enter the data required in separate elements, the container element for this will be 'AddressFix'. If the user chooses the option to enter the data required in a less structured way in 'AddressFree' all available address details shall be presented as one string of bytes, blank or "/" (slash) or carriage return- line feed used as a delimiter between parts of the address. PLEASE NOTE that the address country code is outside  both of these elements. The use of the fixed form is recommended as a rule to allow easy matching. However, the use of the free form is recommended if the sending state cannot reliably identify and distinguish the different parts of the address. The user may want to use both formats e.g. if besides separating the logical parts of the address he also wants to indicate a suitable breakdown into print-lines by delimiters in the free text form. In this case 'AddressFix' has to precede 'AddressFree'.
 * 			
 * 
 * <p>Java class for Address_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Address_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CountryCode" type="{urn:oecd:ties:isocbctypes:v1}CountryCode_Type"/>
 *         &lt;choice>
 *           &lt;element name="AddressFree" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;sequence>
 *             &lt;element name="AddressFix" type="{urn:oecd:ties:cbc:v1}AddressFix_Type"/>
 *             &lt;element name="AddressFree" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *           &lt;/sequence>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="legalAddressType" type="{urn:oecd:ties:stf:v4}OECDLegalAddressType_EnumType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Address_Type", propOrder = {
    "content"
})
public class AddressType {

    @XmlElementRefs({
        @XmlElementRef(name = "AddressFree", namespace = "urn:oecd:ties:cbc:v1", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "AddressFix", namespace = "urn:oecd:ties:cbc:v1", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "CountryCode", namespace = "urn:oecd:ties:cbc:v1", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<?>> content;
    @XmlAttribute(name = "legalAddressType")
    protected OECDLegalAddressTypeEnumType legalAddressType;

    /**
     * Gets the rest of the content model. 
     * 
     * <p>
     * You are getting this "catch-all" property because of the following reason: 
     * The field name "AddressFree" is used by two different parts of a schema. See: 
     * line 250 of file:/C:/tiegit/TIEapp/src/main/xsd/CbcXML_v1.0.xsd
     * line 247 of file:/C:/tiegit/TIEapp/src/main/xsd/CbcXML_v1.0.xsd
     * <p>
     * To get rid of this property, apply a property customization to one 
     * of both of the following declarations to change their names: 
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link CountryCodeType }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link AddressFixType }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getContent() {
        if (content == null) {
            content = new ArrayList<JAXBElement<?>>();
        }
        return this.content;
    }

    /**
     * Gets the value of the legalAddressType property.
     * 
     * @return
     *     possible object is
     *     {@link OECDLegalAddressTypeEnumType }
     *     
     */
    public OECDLegalAddressTypeEnumType getLegalAddressType() {
        return legalAddressType;
    }

    /**
     * Sets the value of the legalAddressType property.
     * 
     * @param value
     *     allowed object is
     *     {@link OECDLegalAddressTypeEnumType }
     *     
     */
    public void setLegalAddressType(OECDLegalAddressTypeEnumType value) {
        this.legalAddressType = value;
    }

}
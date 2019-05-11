package com.sanshengshui.server.common.data;

import com.sanshengshui.server.common.data.id.UUIDBased;
import lombok.EqualsAndHashCode;

/**
 * @author james mu
 * @date 19-1-3 下午12:07
 */
@EqualsAndHashCode(callSuper = true)
public abstract class ContactBased<I extends UUIDBased> extends SearchTextBasedWithAdditionalInfo<I> {
    public static final long serialVersionUID = 5047448057830660988L;

    protected  String country;
    protected  String state;
    protected  String city;
    protected  String address;
    protected  String address2;
    protected  String zip;
    protected  String phone;
    protected  String email;

    public ContactBased(){
        super();
    }
    public ContactBased(I id){
        super(id);
    }
    public ContactBased(ContactBased<I> contact){
        super(contact);
        this.country = contact.getCountry();
        this.state = contact.getState();
        this.city = contact.getCity();
        this.address = contact.getAddress();
        this.address2 = contact.getAddress2();
        this.zip = contact.getZip();
        this.phone = contact.getPhone();
        this.email = contact.getEmail();

    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

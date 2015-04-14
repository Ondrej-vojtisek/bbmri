/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: Masaryk University
 * License Type: Academic
 */
package cz.bbmri.entity;

import java.io.Serializable;
public class Contact implements Serializable {

    public static final String PROP_BIOBANK = "biobank";
   	public static final String PROP_PHONE = "phone";
   	public static final String PROP_EMAIL = "email";
   	public static final String PROP_ADDRESS = "address";
   	public static final String PROP_ZIP = "zip";
   	public static final String PROP_CITY = "city";
   	public static final String PROP_COUNTRY = "country";
   	public static final String PROP_USER = "user";
   	public static final String PROP_ID = "id";
    public static final String PROP_URL = "url";

    private long id;
    private Biobank biobank;
    private String phone;
    private String email;
    private String address;
    private String zip;
    private String city;
    private Country country;
    private User user;
    private String url;

    public Biobank getBiobank() {
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

package com.abrarkotwal.mycontactbook.Adapter.Pojo;

public class Contacts {
    private String contactNumber, contactName;

    public Contacts(String contactName, String contactNumber) {
        this.contactNumber = contactNumber;
        this.contactName = contactName;
    }

    public Contacts() {
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

}

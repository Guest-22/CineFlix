package cineflix;

// Personal Info Model.
public class PersonalInfo {
    private int infoID, accountID; // PK & FK.
    private String fullName, sex, email, contactNum, address; // Other user details.

    
    public PersonalInfo() {}
    
    public PersonalInfo(int infoID, int accountID, String fullName, String sex, String email, String contactNum, String address) {
        this.infoID = infoID;
        this.accountID = accountID;
        this.fullName = fullName;
        this.sex = sex;
        this.email = email;
        this.contactNum = contactNum;
        this.address = address;
    }

    // Getter method.
    public int getInfoID() {
        return infoID;
    }
    public int getAccountID() {
        return accountID;
    }
    public String getFullName() {
        return fullName;
    }
    public String getSex() {
        return sex;
    }
    public String getEmail() {
        return email;
    }
    public String getContactNum() {
        return contactNum;
    }
    public String getAddress() {
        return address;
    }
    
    // Setter method.
    public void setInfoID(int infoID) {
        this.infoID = infoID;
    }
    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
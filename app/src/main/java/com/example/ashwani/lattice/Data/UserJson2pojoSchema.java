package com.example.ashwani.lattice.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserJson2pojoSchema {

@SerializedName("name")
@Expose
private String name;
@SerializedName("address")
@Expose
private String address;
@SerializedName("email")
@Expose
private String email;
@SerializedName("phone_no")
@Expose
private String phoneNo;
@SerializedName("password")
@Expose
private String password;

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getAddress() {
return address;
}

public void setAddress(String address) {
this.address = address;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getPhoneNo() {
return phoneNo;
}

public void setPhoneNo(String phoneNo) {
this.phoneNo = phoneNo;
}

public String getPassword() {
return password;
}

public void setPassword(String password) {
this.password = password;
}

}

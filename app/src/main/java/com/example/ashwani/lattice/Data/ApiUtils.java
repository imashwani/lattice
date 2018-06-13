package com.example.ashwani.lattice.Data;

public class ApiUtils {
 
    public static final String BASE_URL = "https://45.55.53.57:3000/data/";
 
    public static UserService getUserService() {
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }
}
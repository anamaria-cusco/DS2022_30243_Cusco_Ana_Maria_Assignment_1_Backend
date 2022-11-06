package com.example;

public class UrlMapping {
    public static final String API_PATH = "api";
    public static final String ID = "/{id}";
    public static final String ALL = "/all";

    //AUTH CONTROLLER
    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP =  "/sign-up";


    // ADMIN CONTROLLER
    public static final String NAME = "/{name}";
    public static final String USER = "/user";
    public static final String ADMIN = "/admin";
    public static final String ADMIN_USERS = API_PATH + ADMIN + USER;
    public static final String ALL_USERS = "/all"; //VIEW ALL USERS
    public static final String EDIT_USER =  "/edit" + ID; //EDIT USER
    public static final String DELETE_USER = "/delete" + ID; //DELETE USER
    public static final String DELETE_ALL_USERS = "/delete" + ALL; //DELETE ALL USERS
    public static final String SEARCH_USERS_BY_NAME = "/search" + NAME;

    // DEVICE CONTROLLER
    public static final String DESCRIPTION = "/{description}";
    public static final String DEVICE = "/device";
    public static final String ADMIN_DEVICES = API_PATH + ADMIN + DEVICE;
    public static final String ALL_DEVICES = "/all";            //VIEW ALL DEVICES
    public static final String ADD_DEVICE =  "/add" + ID;       //VIEW ALL DEVICES
    public static final String EDIT_DEVICE =  "/edit" + ID;     //EDIT DEVICES
    public static final String DELETE_DEVICE = "/delete" + ID;  //DELETE DEVICES
    public static final String DELETE_ALL_DEVICES = "/delete" + ALL;  //DELETE ALL DEVICES
    public static final String SEARCH_DEVICES_BY_DESCRIPTION = "/search" + DESCRIPTION;

    //MONITORED VALUE CONTROLLER
    public static final String DATE = "/{date}";
    public static final String ADMIN_MONITORED_VALUES =API_PATH + ADMIN + DEVICE + "/monitored_values";
    public static final String DEVICE_MONITORED_VALUES =  ID;
    public static final String ADD_MONITORED_VALUE =  "/add" + ID;

    //USER CONTROLLER
    public static final String CLIENT = API_PATH  + "/user";
    public static final String USER_DEVICES = "/devices"+ID;
    public static final String DAILY_CONSUMPTION = DEVICE + ID  + "/daily_consumption" + DATE;


}

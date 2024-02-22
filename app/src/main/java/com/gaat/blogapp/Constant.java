package com.gaat.blogapp;

//Constantes como o próprio nome da class diz,está armazenando todas os endpoint's da API Laravel
public class Constant {
    public static final String URL = "http://192.168.1.4:80/";
    public static final String HOME = URL+"api";

    public static final String LOGIN = HOME+"/login";
    public static final String REGISTER = HOME+"/register";
    public static final String SAVE_USER_INFO = HOME+"/save_user_info";

}

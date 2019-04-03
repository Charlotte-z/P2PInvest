package com.example.administrator.p2pinvest.common;

/**
 * 配置网络请求相关地址
 */
public class AppNetConfig {
    public static final String IPADDRESS = "192.168.137.106:8080";

    public static final String BASE_URL = "http://" + IPADDRESS +"/P2PInvest/";

    public static final String BASE_JSON = "file/";
 
    public static final String BASE_IMG = "image/";

    public static final String IMAGE = BASE_URL + BASE_IMG;

    public static final String PRODUCT = BASE_URL + BASE_JSON + "product";

    public static final String LOGIN = BASE_URL + BASE_JSON + "product";

    public static final String INDEX = BASE_URL + BASE_JSON + "index";//访问homeFragment

    public static final String USERREGISTER = BASE_URL + BASE_JSON + "UserRegister";//注册

    public static final String FeedBack = BASE_URL + BASE_JSON + "FeedBack";//反馈

    public static final String UpdateJSON = BASE_URL + BASE_JSON + "update.json";//更新应用




}

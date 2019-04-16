package com.example.administrator.p2pinvest.common;

/**
 * 配置网络请求相关地址
 */
public class AppNetConfig {
    public static final String IPADDRESS = "192.168.1.107:8080";

    public static final String BASE_URL = "http://" + IPADDRESS +"/P2PInvest/";

    public static final String BASE_JSON = "file/";

    public static final String BASE_IMG = "images/";

    public static final String IMAGE = BASE_URL + BASE_IMG;

    public static final String PRODUCT = BASE_URL + BASE_JSON + "product.json";

    public static final String LOGIN = BASE_URL + BASE_JSON + "product.json";

    public static final String INDEX = BASE_URL + BASE_JSON + "index.json";//访问homeFragment

    public static final String USERREGISTER = BASE_URL + BASE_JSON + "UserRegister.json";//注册

    public static final String FeedBack = BASE_URL + BASE_JSON + "FeedBack.json";//反馈

    public static final String UpdateJSON = BASE_URL + BASE_JSON + "update.json";//更新应用




}

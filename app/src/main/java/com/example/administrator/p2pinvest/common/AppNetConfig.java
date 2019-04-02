package com.example.administrator.p2pinvest.common;

/**
 * 配置网络请求相关地址
 */
public class AppNetConfig {
    public static final String IPADDRESS = "192.168.xx.xx";

    public static final String BASE_URL = "http://" + IPADDRESS +"/P2PInvest/";

    public static final String PRODUCT = BASE_URL + "product";

    public static final String LOGIN = BASE_URL + "product";

    public static final String INDEX = BASE_URL + "index";//访问homeFragment

    public static final String USERREGISTER = BASE_URL + "UserRegister";//注册

    public static final String FeedBack = BASE_URL + "FeedBack";//反馈

    public static final String UpdateJSON = BASE_URL + "update.json";//更新应用




}

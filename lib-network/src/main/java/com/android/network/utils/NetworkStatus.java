package com.android.network.utils;

/**
 * Http请求异常状态
 */
public enum NetworkStatus {

    NETWORK_DISCONNECTED(-1, "网络未连接"), // 网络未连接
    NETWORK_UNABLE(-2, "网络异常,请连接可用网络"), // 已连接网络，但是不可访问网络
    NETWORK_SERVER_EXCEPTION(-3, "服务器连接异常"), // 已连接网络，访问服务器异常：404、500、UnknownHostException等
    NETWORK_SERVER_TIMEOUT(-4, "服务器连接超时"),//  已连接网络，访问服务器超时 TimeoutException情况
    NETWORK_JSON_EXCEPTION(-5, "Json转换异常");// Json解析异常

    private String errorMessage;
    private int errorCode;

    NetworkStatus(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public class Type {
        public static final int RETROFIT_DEFAULT = -1;
        public static final int RETROFIT_DATAWRAPPER = 0;
        public static final int RETROFIT_RXJAVA = 1; // 使用Retrofit+RxJava2组合
        public static final int RETROFIT_RXJAVA_DATAWRAPPER = 2;
        public static final int TIMEOUT_MILLISECONDS = 15 * 1000;
    }

}

package com.orc.rpc.common.constants;

/**
 * @author xiaoxu123
 */
public class RpcConstant {

    /**
     * Zookeeper服务注册地址
     */
    public static final String ZK_SERVICE_PATH = "/rpc";

    /***
     * 编码
     */
    public static final String UTF_8 = "UTF-8";

    /**
     * 路径分隔符
     */
    public static final String PATH_DELIMITER = "/";

    /**
     * java序列化协议
     */
    public static final String PROTOCOL_JAVA = "java";

    /**
     * Hessian序列化协议
     */
    public static final String PROTOCOL_Hessian = "hessian";

    /**
     * protobuf序列化协议
     */
    public static final String PROTOCOL_PROTOBUF = "protobuf";
    /**
     * kryo序列化协议
     */
    public static final String PROTOCOL_KRYO = "kryo";
    /**
     * 随机
     */
    public static final String BALANCE_RANDOM = "random";
    /**
     * 轮询
     */
    public static final String BALANCE_ROUND = "round";
    /**
     * 加权轮询
     */
    public static final String BALANCE_WEIGHT_ROUND = "weightRound";
    /**
     * 平滑加权轮询
     */
    public static final String BALANCE_SMOOTH_WEIGHT_ROUND = "smoothWeightRound";

}

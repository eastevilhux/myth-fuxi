package com.starlight.dot.framework.network

interface SDKDataSecurity{

    /**
     * 数据解密
     * create by Eastevil at 2022/5/9 18:20
     * @author Eastevil
     * @param data
     *      需要解析的数据
     * @return
     *      解析后的数据
     */
    fun dataDecrypt(data : String?) : String?;

    /**
     * 数据加密处理
     * create by Administrator at 2022/10/15 23:39
     * @author Administrator
     * @param data
     *      需要加密的数据
     * @return
     *      加密后的数据
     */
    fun dataEncrypt(data : String?) : String?;
}

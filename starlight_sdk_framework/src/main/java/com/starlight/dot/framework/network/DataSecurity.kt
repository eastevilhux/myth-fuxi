package com.starlight.dot.framework.network

interface DataSecurity {

    /**
     * 数据解密
     * create by Eastevil at 2022/5/9 18:20
     * @author Eastevil
     * @param data
     *      需要解析的数据
     * @return
     *      解析后的数据
     */
    fun aesDecrypt(data : String?) : String?;

    /**
     * 数据加密
     * create by Eastevil at 2022/5/9 18:21
     * @author Eastevil
     * @param data
     *      需要加密的数据
     * @return
     *      加密后的数据
     */
    fun aesEncrypt(data : String?) : String?;

    /**
     * 数据验签
     * create by Eastevil at 2022/5/9 18:21
     * @author Eastevil
     * @param data
     *      源数据
     * @return
     *      根据源数据通过验签规则生成的验签数据
     */
    fun signData(data : String?) : String?;
}

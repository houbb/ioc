package com.github.houbb.ioc.exception.respcode;

import com.github.houbb.heaven.response.respcode.AdviceRespCode;
import com.github.houbb.heaven.response.respcode.RespCode;

/**
 * IoC 响应编码
 * @author binbin.hou
 * @since 0.0.5
 */
public enum IocRespCode implements AdviceRespCode {
    ;

    /**
     * 编码
     * @since 0.1.38
     */
    private final String code;

    /**
     * 消息
     * @since 0.1.38
     */
    private final String msg;

    /**
     * 建议
     * @since 0.1.38
     */
    private final String advice;

    IocRespCode(String code, String msg, String advice) {
        this.code = code;
        this.msg = msg;
        this.advice = advice;
    }


    IocRespCode(final RespCode respCode, String advice) {
        this.code = respCode.getCode();
        this.msg = respCode.getMsg();
        this.advice = advice;
    }

    @Override
    public String getAdvice() {
        return this.advice;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

    @Override
    public String toString() {
        return "IocRespCode{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", advice='" + advice + '\'' +
                '}';
    }

}

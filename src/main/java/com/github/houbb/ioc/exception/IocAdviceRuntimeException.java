package com.github.houbb.ioc.exception;

import com.github.houbb.heaven.response.respcode.AdviceRespCode;

/**
 * IoC 包含建议的异常信息
 * <p> project: ioc-IocRuntimeException </p>
 * <p> create on 2019/11/6 19:29 </p>
 *
 * @author Administrator
 * @since 0.0.5
 */
public class IocAdviceRuntimeException extends RuntimeException {

    /**
     * 包含建议的响应编码
     * @since 0.0.5
     */
    private final AdviceRespCode adviceRespCode;

    public IocAdviceRuntimeException(AdviceRespCode adviceRespCode) {
        this.adviceRespCode = adviceRespCode;
    }

    public IocAdviceRuntimeException(String message, AdviceRespCode adviceRespCode) {
        super(message);
        this.adviceRespCode = adviceRespCode;
    }

    public IocAdviceRuntimeException(String message, Throwable cause, AdviceRespCode adviceRespCode) {
        super(message, cause);
        this.adviceRespCode = adviceRespCode;
    }

    public IocAdviceRuntimeException(Throwable cause, AdviceRespCode adviceRespCode) {
        super(cause);
        this.adviceRespCode = adviceRespCode;
    }

    public IocAdviceRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, AdviceRespCode adviceRespCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.adviceRespCode = adviceRespCode;
    }

}

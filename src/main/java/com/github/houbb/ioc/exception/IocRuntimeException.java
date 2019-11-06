package com.github.houbb.ioc.exception;

/**
 * <p> project: ioc-IocRuntimeException </p>
 * <p> create on 2019/11/6 19:29 </p>
 *
 * @author Administrator
 * @since 0.0.1
 */
public class IocRuntimeException extends RuntimeException {

    public IocRuntimeException() {
    }

    public IocRuntimeException(String message) {
        super(message);
    }

    public IocRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public IocRuntimeException(Throwable cause) {
        super(cause);
    }

    public IocRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

package com.trd.oecms.function;

import java.util.function.Supplier;

/**
 * 可抛出去的Supplier（转换为了uncheckedException）
 * @author tanruidong
 * @date 2020-05-19 12:01
 */
@FunctionalInterface
public interface ThrowableSupplier<T> {

    /**
     * get a result
     * @return
     * @throws Throwable  an exception that is thrown
     */
    T get() throws Throwable;

    /**
     * 包装checkedException,转化为uncheckedException
     * @param throwableSupplier
     * @param <T>
     * @return
     */
    static <T> Supplier<T> throwableSupplierWrapper(ThrowableSupplier<T> throwableSupplier) {
        return () -> {
            try {
                return throwableSupplier.get();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }
}

package com.zhong.enums;

/**
 * @author haiqu
 */
public interface ErrorEnumInterface {

    /**
     * 获取异常信息
     *
     * @return
     */
    String getErrorMessage();

    /**
     * 获取异常码
     *
     * @return
     */
    int getErrorCode();

    /**
     * 获取父级异常码
     *
     * @return
     */
    int getParentCode();
}

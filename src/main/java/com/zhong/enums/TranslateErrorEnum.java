package com.zhong.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**
 * 异常code映射<br>
 *
 * @date 2022/7/20 20:50
 */
public enum TranslateErrorEnum implements ErrorEnumInterface {


    SYSTEM_ERROR(50000, 50001, "系统开小差，请稍后再试") {
        public Set<String> mapping() {
            return new HashMap<String, String>() {
                private static final long serialVersionUID = 1L;

                {
                    put("", "");
                }
            }.keySet();
        }

        @Override
        public String getErrorMessage() {
            return SYSTEM_ERROR.errorMessage;
        }

        @Override
        public int getErrorCode() {
            return SYSTEM_ERROR.errorCode;
        }

        @Override
        public int getParentCode() {
            return SYSTEM_ERROR.parentCode;
        }
    },

    TRANSLATION_FAILED(40000, 40620, "翻译失败") {
        public Set<String> mapping() {
            return new HashMap<String, String>() {
                private static final long serialVersionUID = 1L;

                {
                    put("10001", "翻译超时");
                    put("10003", "译文必须UTF-8编码");
                    put("10004", "参数缺失");
                    put("10005", "语言不支持");
                    put("10006", "语种识别失败");
                    put("10008", "字符长度过长");
                    put("10021", "参数无效");
                    put("System.TranslateError", "翻译错误");
                }
            }.keySet();
        }

        @Override
        public String getErrorMessage() {
            return TRANSLATION_FAILED.errorMessage;
        }

        @Override
        public int getErrorCode() {
            return TRANSLATION_FAILED.errorCode;
        }

        @Override
        public int getParentCode() {
            return TRANSLATION_FAILED.parentCode;
        }
    },

    OTHER_ERROR(50000, 59999, "其他错误") {
        @Override
        public Set<String> mapping() {
            return new HashMap<String, String>() {
                private static final long serialVersionUID = 1L;

                {
                    put("10012", "翻译服务调用失败");
                    put("10013", "翻译账号未开通或欠费");
                    put("10028", "接口QPS过高");
                    put("InvalidAccountStatus", "用户未开通机器翻译服务");
                    put("19999", "未知异常");
                }
            }.keySet();
        }

        @Override
        public String getErrorMessage() {
            return OTHER_ERROR.errorMessage;
        }

        @Override
        public int getErrorCode() {
            return OTHER_ERROR.errorCode;
        }

        @Override
        public int getParentCode() {
            return OTHER_ERROR.parentCode;
        }
    },

    ;

    private final int errorCode;
    private final int parentCode;
    private final String errorMessage;

    TranslateErrorEnum(int parentCode, int errorCode, String errorMessage) {
        this.parentCode = parentCode;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }


    public Set<String> mapping() {
        return Collections.emptySet();
    }

    public static TranslateErrorEnum getByMappingCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            if (TRANSLATION_FAILED.mapping().contains(code)) {
                return TRANSLATION_FAILED;
            } else if (SYSTEM_ERROR.mapping().contains(code)) {
                return SYSTEM_ERROR;
            } else {
                return OTHER_ERROR;
            }
        }
        return SYSTEM_ERROR;
    }

}

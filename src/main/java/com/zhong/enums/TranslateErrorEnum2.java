package com.zhong.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 2022/7/20 20:50
 */
public enum TranslateErrorEnum2 implements ErrorEnumInterface {


    SYSTEM_ERROR(50000, 50001, "系统开小差，请稍后再试") {
        public List<String> mapping() {
            return new ArrayList<String>() {
                {
                    add("s");
                    add("ss");
                }
            };

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

    TRANSLATION_FAILED {
        public List<String> mapping() {
            return new ArrayList<String>() {
                private static final long serialVersionUID = 1L;

                {
                    add("ss");
                    add("ff");
                }
            };
        }

        @Override
        public String getErrorMessage() {
            return null;
        }

        @Override
        public int getErrorCode() {
            return 0;
        }

        @Override
        public int getParentCode() {
            return 0;
        }
    },

    other_error {
        @Override
        public String getErrorMessage() {
            return null;
        }

        @Override
        public int getErrorCode() {
            return 0;
        }

        @Override
        public int getParentCode() {
            return 0;
        }
    },

    ;

    private int errorCode;
    private int parentCode;
    private String errorMessage;

    TranslateErrorEnum2(int parentCode, int errorCode, String errorMessage) {
        this.parentCode = parentCode;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    TranslateErrorEnum2() {

    }


    public List<String> mapping() {
        return new ArrayList<>();
    }

}

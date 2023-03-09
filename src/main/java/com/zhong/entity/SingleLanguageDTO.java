package com.zhong.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Locale;

/**
 * @author haiqu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SingleLanguageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Locale locale;

    private String value;
}

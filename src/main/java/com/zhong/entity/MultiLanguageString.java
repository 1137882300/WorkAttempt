package com.zhong.entity;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author haiquan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class MultiLanguageString implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<Locale, String> languageStringMap = new HashMap<>();
    private final Locale defaultLocale = new Locale("in", "ID");

    public void registerLanguage(Locale locale, String value) {
        languageStringMap.put(locale, value);
    }

    public void registerDefaultLanguage(String value) {
        languageStringMap.put(defaultLocale, value);
    }

    public void registerAll(Map<Locale, String> languageMap) {
        languageStringMap = languageMap;
    }

    public static MultiLanguageString of(Map<Locale, String> languageMap) {
        if (languageMap == null || languageMap.isEmpty()) {
            return null;
        }
        MultiLanguageString string = new MultiLanguageString();
        string.registerAll(languageMap);
        return string;
    }

    /**
     * 获取 默认语言对应的值
     * 如果没有获取英语
     * 还没有从列表中获取第一个语言
     * @return
     */
    public String getDefaultLanguageString() {
        String defaultLanguage = languageStringMap.get(defaultLocale);
        if (StringUtils.isBlank(defaultLanguage)) {
            defaultLanguage = languageStringMap.get(Locale.US);
            if (StringUtils.isBlank(defaultLanguage)) {
                for (Map.Entry<Locale, String> localeStringEntry : languageStringMap.entrySet()) {
                    defaultLanguage = localeStringEntry.getValue();
                    break;
                }
            }
        }
        return defaultLanguage;
    }

    public String getLanguageString(Locale locale) {
        return languageStringMap.get(locale);
    }

    public Map<Locale, String> getAllLanguageString() {
        return languageStringMap;
    }

    public Locale getDefaultLocale() {
        return defaultLocale;
    }


}

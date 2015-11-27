package com.runningfish.spmk.framework.plugin;

public class I18nUtils {

    private static I18nUtils i18nUtils;
    private SpringContextUtil springContextUtil;

    public void init() {
        i18nUtils = this;
        i18nUtils.springContextUtil = this.springContextUtil;
    }

    public static String getMessage(String code, Object[] args) {
        return i18nUtils.springContextUtil.getMessage(code, args);
    }

    public static String getMessage(String code) {
        return i18nUtils.springContextUtil.getMessage(code, new Object[]{});
    }

    public void setSpringContextUtil(SpringContextUtil springContextUtil) {
        this.springContextUtil = springContextUtil;
    }
}

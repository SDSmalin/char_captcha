package com.lin.charcaptcha.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "captcha")
public class CaptchaConfig {

    /**
     * 图片宽度
     */
    private int imgWidth;

    /**
     * 图片高度
     */
    private int imgHeight;

    /**
     * 字体大小
     */
    private int fontSize;

    /**
     * 字体范围
     */
    private int fontRange;

    /**
     * 字体
     */
    private String fontFamily;

    /**
     * 文字数量
     */
    private int fontCount;

    public int getFontCount() {
        return fontCount;
    }

    public void setFontCount(int fontCount) {
        this.fontCount = fontCount;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public int getFontRange() {
        return fontRange;
    }

    public void setFontRange(int fontRange) {
        this.fontRange = fontRange;
    }
}

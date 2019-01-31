package com.lin.charcaptcha.controller;

import com.lin.charcaptcha.config.CaptchaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Controller
public class CharCaptchaController {

    /**
     * 图片配置
     */
    @Autowired
    private CaptchaConfig captchaConfig;

    private Random random = new Random();

    @RequestMapping("/captchaImg")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int height = captchaConfig.getImgHeight();
        int width = captchaConfig.getImgWidth();
        //创建图片对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //获取背景图片
        Image bgImage = ImageIO.read(getClass()
                .getClassLoader()
                .getResourceAsStream("images/bg.jpg")).getScaledInstance(width, height, Image.SCALE_SMOOTH);
        Graphics graphics = image.getGraphics();
        //绘制背景图片
        graphics.drawImage(bgImage, 0, captchaConfig.getFontSize(), width, height, null);
        //绘制边框
        graphics.drawRect(0, 0, width - 1 , height - 1);
        //设置字体
        graphics.setFont(new Font(captchaConfig.getFontFamily(), Font.BOLD, getFontSize()));
        //设置颜色

        //避免图片重叠，将图片4等分，字在一个等分位置中随机出现
        String[] chars = getStringArray();
        int[][] xys = new int[captchaConfig.getFontCount()][];
        for (int i = 0; i < chars.length ; i++){
            graphics.setColor(getRandColor());
            int[] xy = getFontXPosition();
            xys[i] = xy;
            graphics.drawString(chars[i], xy[0], xy[1]);
        }
        //绘制干扰线
        drawInterferingLine(height, width, graphics);
        graphics.setFont(new Font(captchaConfig.getFontFamily(), Font.PLAIN, 20));
        graphics.setColor(Color.WHITE);
        graphics.drawString("请选中图片中的\"" + chars[0] + "\"字", 0, 20);
        request.getSession().setAttribute("charPosition", xys[0]);
        OutputStream out = response.getOutputStream();
        //释放资源
        graphics.dispose();
        //输出图片
        ImageIO.write(image, "jpg", out);
        out.flush();
    }

    /**
     * 获取字体坐标
     * @return
     */
    private int[] getFontXPosition() {
        int[] xy = {random.nextInt(captchaConfig.getImgWidth() - captchaConfig.getFontSize() * 2), random.nextInt(captchaConfig.getImgHeight() - captchaConfig.getFontSize() * 3) + captchaConfig.getFontSize() * 2};
        return xy;
    }

    /**
     * 绘制干扰线
     * @param height
     * @param width
     * @param graphics
     */
    private void drawInterferingLine(int height, int width, Graphics graphics) {
        //绘制干扰线
        for (int i = 0; i < 255; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height) + captchaConfig.getFontSize();
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            graphics.setColor(getRandColor());
            graphics.drawLine(x, y, x + xl, y + yl);
        }
    }

    /**
     * 获取随机字符
     * @return
     */
    private char getRandomHan() {
        return (char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1)));
    }

    /**
     * 获取字体大小
     * @return
     */
    private int getFontSize(){
        int fontRange = captchaConfig.getFontRange();
        return captchaConfig.getFontSize() + (fontRange - random.nextInt(fontRange * 2));
    }

    /**
     * 获取字符串数组
     * @return
     */
    private String[] getStringArray(){
        Set<String> chars = new HashSet<>();
        while(chars.size() < captchaConfig.getFontCount()){
            chars.add(getRandomHan()+"");
        }
        return chars.toArray(new String[4]);
    }

    /**
     * 获取随机颜色
     * @return
     */
    private Color getRandColor(){
        Random random = new Random();
        //保证颜色偏深
        int r = random.nextInt(236);
        int g = random.nextInt(236);
        int b = random.nextInt(236);
        return new Color(r, g, b);
    }
}

package com.lin.charcaptcha.controller;

import com.lin.charcaptcha.config.CaptchaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Autowired
    private CaptchaConfig captchaConfig;

    @RequestMapping("/loginPage")
    public ModelAndView loginPage(){
        return new ModelAndView("captcha");
    }

    @RequestMapping("/checkCode")
    @ResponseBody
    public boolean checkCode(HttpServletRequest request, int x, int y){
        int[] charPositions = (int[]) request.getSession().getAttribute("charPosition");
        int absX = Math.abs(charPositions[0] - x);
        int absY = Math.abs(charPositions[1] - y);
        if(absX < captchaConfig.getFontSize() / 2 && absY < captchaConfig.getFontSize() / 2){
            return true;
        }else{
            return false;
        }
    }
}

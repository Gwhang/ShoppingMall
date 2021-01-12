package com.gwh.sell.controller;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

/**
 * 微信
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    /**
     * 微信授权认证
     * @param returnUrl
     */
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        //1.配置微信appid
        // 调用接口
        String url="http://sell.ngrok2.xiaomiqiu.cn/sell/wechat/userInfo";
        // 构造网页授权url
        String redirectUrl =this.wxMpService.getOAuth2Service().buildAuthorizationUrl(returnUrl, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
        return "redirect:"+redirectUrl;
    }

    @GetMapping("userInfo")
    public String  userInfo(@RequestParam("code")String code,@RequestParam("state")String state) throws Exception{
        log.info("获取用户信息code={}",code);
        // 获得access token
        WxOAuth2AccessToken accessToken = wxMpService.getOAuth2Service().getAccessToken(code);
        // 获得用户基本信息
        WxOAuth2UserInfo userInfo = wxMpService.getOAuth2Service().getUserInfo(accessToken, null);

        String openid = userInfo.getOpenid();

        return "redirect:"+state +"?openid="+openid;
    }

}

package com.ruoyi.web.controller.common;

import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.common.utils.uuid.UUID;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RestController
@Api(value = "手机发送短信验证码")
@RequestMapping(value = "/phone")
public class MessageCodeController {

    @Resource
    private RedisCache redisCache;

    @ApiOperation(value = "获取短信验证码")
    @RequestMapping(value = "/getCode", method = RequestMethod.GET)
    public AjaxResult getPhoneMessageCode(@RequestParam(value = "phone") String phone) {
        //校验手机号码
        if(!checkPhone(phone)){
            return AjaxResult.error(MessageUtils.message("user.phone.error"));
        }
        String uuid = IdUtils.simpleUUID();

        String verifyKey = CacheConstants.PHONE_CODE_KEY + uuid;
        Random random = new Random();
        // 生成4位随机数
        int randomNumber = random.nextInt(9000) + 1000;

        redisCache.setCacheObject(verifyKey, randomNumber, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("uuid",uuid);
        ajax.put("code",randomNumber);
        return ajax;
    }

    private boolean checkPhone(String phone) {
        // 定义手机号码的正则表达式
        String regex = "^(\\+\\d{1,3})?1[34578]\\d{9}$";

        // 编译正则表达式
        Pattern pattern = Pattern.compile(regex);

        // 创建Matcher对象
        Matcher matcher = pattern.matcher(phone);

        // 返回匹配结果
        return matcher.matches();
    }
}

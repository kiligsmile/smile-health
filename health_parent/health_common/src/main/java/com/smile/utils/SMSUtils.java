package com.smile.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.Properties;

import static com.smile.utils.ValidateCodeUtils.createSimpleMail;

/**
 * 短信发送工具类
 */
public class SMSUtils {
    public static final String VALIDATE_CODE = "SMS_159620392";//发送短信验证码
    public static final String ORDER_NOTICE = "SMS_159771588";//体检预约成功通知

    /**
     * 发送短信
     * @throws ClientException
     */
    public static void sendMessage(String receiveMail,String code){
        Properties prop=new Properties();
        prop.setProperty("mail.debug","true");
        prop.setProperty("mail.host","smtp.qq.com");
        prop.setProperty("mail.smtp.auth","true");
        prop.setProperty("mail.transport.protocol","smtp");
        try{
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.ssl.socketFactory", sf);
            Session session = Session.getInstance(prop);
            Transport ts = session.getTransport();
            ts.connect("smtp.qq.com", "kiligsmile@qq.com", "mziwirkzbmamdceh");
//            邮件接收者
            Message message = createSimpleMail(session, receiveMail,code);
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
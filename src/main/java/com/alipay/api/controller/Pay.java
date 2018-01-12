package com.alipay.api.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zwd
 * @date 2018/1/11 18:56
 */

@RestController
public class Pay {

    private static String URL = "https://openapi.alipay.com/gateway.do";

    private static String APP_ID = "2018011101775959";

    private static String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCpWs" +
            "Czrlb6QxTJEZSA6lnaw4nM2I5aib5oBcL6qFASO/p11cAzyQI0iTjtLLUztN2072JwAVSSZVE/GozzTNHrdl17Ksz4C" +
            "+kgGXdoa6bzY3DBkH6jr8YzjY8sDK2xhpUa+eCXctcWlWCP6CWIDHRKUY2qrSzhxuNv/KD6/7z37TIem60/+m6TSO8Vkc6cZQYmwcb9" +
            "Do2GFRexkwK1LP54BVlMD6CMUICMKeCF2A9QWD0bSrYtKseD0tX+BrfGb7mPaEchodatrdMxycdAyNSNqGEqkskcy86aN7vmhX5tZuf6sUqy3z" +
            "2CxRd2aquxBfwcnAOZjTEeSc0ZilR5nAxjAgMBAAECggEBAIjmlfQqMKFp7LmFfpif2Tcg7porTRgfVU5dqfpaaS/0UElFG3RBP4kcjGodw/Uidj" +
            "63bMnCKY2WXZE3c02Sgfdn3bWdKcgySKfsLjy5GWKStimjFtxXUU+HVX90WEyz0IDYEhgbe4gEBOUP8pcCClexiWW5TBjPYDe87p04cpe" +
            "vUMlt2NomgeZiuCC9eTHok3W6jZAxy8atYvbMc8vMXea+mwQyIGqF1bW+5sq22F9GzeIzfFkhrwhmZMQiMpy4TTtdbB9B1q" +
            "Gjk9tz9gsRfgrPVuwT6sCnMHjcA2qd/cKmSfzAEd+Z33TNYX30x4tLXDi/s/Aa8/XONSbkIYYcr8ECgYEA8DXYini5Wz" +
            "ygMkhLlMNgxetS25idBTRW8wWUNibBdqF9ohF6cmfKuPmqeKMnr7+JBOlSYxljc1eKvAS7nzeSpJ+mSlRaag4Sp" +
            "ZbdbolzE+B0afMQd2c2cna6BNtwu56SBYIQ3vfSdPvyJ2eH2qxIj5K0xlbQX8KUMVKE859vgZMCgYEAtHyTdca" +
            "v5Ze3gJD66WUJDqIPMnhnTsZ2r73UNIjGdtyqIbx3cxrHl8slujLvI2UDsQiq3xeJtTZzjALT0vhToBr5R6sp" +
            "9UfLZW5t4/SoYzEEwaZicSeL00zhnU3RCm/yRV8cxP4X0J9FVWvlD5/DFHnrMskRej/bF5Ayyg6/S/ECgYAeo" +
            "H7viNz5SQ2T26vnV9Hq6koyPY8nrri3W6q+DMr1+TkvhNL760fptcuKfiL9QYqS2bzrbb1EY5EI0AGsF0XXP5" +
            "PQPlkY1JqECmxJP7Uwf5nYOGmND9fdWE33eJMzGNC9awwDoc1vpQAHDihANV87DxWFhGQlEYYbk83n0hhBiQKBgCTU30rDlxWlVl84Ntv2MFj6HVvRUlOzEHlgpbNqzgVnlph323Bz4JNLc5GB32c7r/sqp6QLK6D9Uasa0JtW7cnYd1J2zYMueYVjea1xOgOz9yKFj8VDqrceE1SEksoseiZJ+JcCu11pgz6hXAdKNQcyshqVCaoG6fDvDk5JoCQhAoGBAJluFnBESzmQkGNZa8gs4c9mL9XAFu3YjbmNeDkQMtd4GE/Evqi0vozEsuttUXOEgfEovFsBWa1o4Wbp+7jlKwI9EXv4owT744YighE9xb6s2GjKq2GmRrAf4ZxEcc6S/L7lU4JiwvBa/og34gdDaFhZ/WKYp82YPlJJOSLZ4ib9"
            ;
    private static String FORMAT = "json";

    private static String CHARSET = "utf-8";

    private static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2EDpqjPWThW7Z0fD/1dG0ueb7O1dL1tU5nlJBla1V0SIwqlZa6xXMcxq/YV6FVLbjCWnZtaCMZIc09HssLNs2efn1oC7zMfQso7eSERSB+zCL5YW8Ranf7jUgzJSyVJohL781TIxgGs/c42AOm6LGpr09wj3pDEEvRs+62MR++bwolRsWa+wviYPM/I2NXKGa7gBTB7N7MFcUPjoJzbXCvsLjxeS0SuWNw9SwczRPlhDdz/fRtODF0bP5HVQz1Lav4Ab9td6nqlsrZG/FDhUQXOKK6mhdQ3MTdt4CSEk9kTOIedZja2egpyrd1ny9bZIHl8slQlvh1JNXJm3dCC7CwIDAQAB";

    private static String SIGN_TYPE = "RSA2";

    @RequestMapping(value = "/pay")
    public void alipay(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
                throws ServletException,IOException{

        AlipayClient alipayClient = new DefaultAlipayClient(URL,APP_ID,APP_PRIVATE_KEY,FORMAT,CHARSET,ALIPAY_PUBLIC_KEY,SIGN_TYPE);

        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
        alipayRequest.setNotifyUrl("http://domain.com/CallBack/notify_url.jsp");//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"20150320010101001\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":88.88," +
                "    \"subject\":\"Iphone6 16G\"," +
                "    \"body\":\"Iphone6 16G\"," +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }"+
                "  }");//填充业务参数
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }
}

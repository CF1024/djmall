package com.dj.mall.model.util;

import com.alipay.api.*;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AliPayUtils {

	// 支付宝公钥
	static final String ALIPAYPUBLICKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm4piAcyRmBSmDsBZc7YBtij4iLHo+SObOcdHf4nU6D3F8I/VoY8AeJu3wlgH/IJtZ/IACZxPd8fApNkIPsezkN4ilOVtBBK42x6+OreG7SRTvCHvrITglRUyMYQUklAencx/f2guquP0a7TYdnqoYsrdKuAQysMAZCNkFSYD5gT0/HNsR2SRl9PlLUftcMHHW0Ru0dvfWZ3iC19aHeA6enKaG2GpGDoFeCjxqI71qF/Jf+S2Oezh28yNB8NNgT7cb7wFIdZZVPP0teB2odvrkWEOfa0XJseOfJYO9Z3e/WTwJYJ/Wnb4Al3kKAKUlEjUOPgPRi5XjgIplFndvzBR/wIDAQAB";
	// 应用私钥
	static final String ALIPAYAPPPRIVATEKEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCAduMRBvrN1oqk4tdc+fBpCiLIePL1Q9IYdPN8GztK/B2BwLBBNjMOX9QPI/dst4BXOBQ1z8tTcliqJCWe+TxlGEjOWxqBCbnxfSSeXOiDlczHzQrSp5JZUK1dhiCpFgYX/UGuLr6KdOkbC6SrzfVuBg9QtPZhkPl0szR25P8UPiOU9SgxrJ+V8GF3EwxMdAXoSKZY2yDNKsBoYn0MhRpeK5TKGm8EO6ZyhwINlOxNAIIk+FeODma14pxykm77t6mM4Fl4vqq10eJsPlL8lNSGvUZFie9zgroE5wB/fz4vRyOZAJQGh65Pk4d/K+zI2OilFtvl3RPs710m2yqegvFfAgMBAAECggEAETlfeoH4EK8qSa9Mdt/gFwVP4pcs5CuiuLq9hq9UBNrGzgXa2/N7Zvr8QMduvjngUFFpjEl/FoInVzCfhrAeI+PcpyZZ6GE4eCNyTHVii3oDSeq4/8OOAMGrbPW13VXhnUfMjaEg1QmV05V9D7f+pzDSztxkiHJasAXzC9nqRXlau4BDPO1sy6/RkBXoltD8y25VASvepMDZ94upLLTB7OyJDMKSnvGFBwPfva/dPmuM4v/BtagAnNds8NkXtHE9ysq8xmw7ALk78ir+FnrpqmxjIh0rGEN94mG1+W5QFmwJIEecrKSJ+An7kewxfdiw3kGdJ/D2fjnjWuJA0BPC0QKBgQC8eSEwSY17Dk63HapJgDyh5YVLteCfoh9OnzUpnEslvVLSwsv2HJPyTwlWCMGKGgI/GLh3A2o7OuTW45tDRCH6CHgckc4VuujbGN5WCnfS1sYzl8ELqk9q1Jyc+yQ/LAeLUgbIkMTlExZKUXK1t3zrnpF7eEXghOvfQYOroKo1qQKBgQCufbfcmkkaiJqqf2pQg2TSYxr57ShAEJOf7CYM8+J6IRwstV0dlYW7/T2r+S8DChhbY0hzc7ikpy+2sTJpwcEHtGeNIeCplEOz/aon2KZ00J9r/Sp7gMGf1ch/zUxqABG41v4UK8EjAqFvSrCFaGf/+DPCrkO4KgjdWGc1RehDxwKBgCRzfOK85vsMulaaCywp/BAHtu78nxPgpBNtoTeJ/dKvugv/Frljr41mkdSs3tmZzCNt4YdLBsmry0RwQsAQtk2IQ8qqvGPhgVWWirGzVpdVaIHj8LcT2kY8FooZ3FnHZzIfzCYQMbRoK6B1/ix871EWmuZ5TXJt4ryMDC4uZZz5AoGAHqR7RmVd0ygMKUbo76yzZvL6iMtqFx4SHrKE2B94NURdQ0iySoC4lJPbYbkH1XvPXTUpQwUeduPZUDb8CHB7KpQ23yUE5CLiZNh2Kjs0uCzu4jL6Y+fvmNcdN7xg3/iVJCW1RuuMJk7O9NFkAHmeUehPhq+9uri/B8f/j+5v5H8CgYA4cKze9yrcbB3mUcjFaMGFCK+NQAFoO3pJRAg3c1jany38jQaqvzYzit+c0yU1LDMEWvnZySVnKkLBcJ/CQwbJJsV6cyebSK666uSIc4HP6GdMpTXY+nU2/oCJbmkKvv23cN5egD8uXjo6AMDl1CFhAfSzgWV9aM+WhQxGncv4ZQ==";
	// APPID
	static final String APPID = "2021000120602339";
	/**
	 * 支付 阿里
	 * @param orderNum 订单号
	 * @param totalAmount	总金额
	 * @param subject 商品名称
	 * @return
	 * @throws Exception
	 */
	public static String toAliPay(String orderNum, Double totalAmount, String subject) throws Exception {
		String aliPayGateWayUrl = "https://openapi.alipaydev.com/gateway.do";
		AlipayClient alipayClient = new DefaultAlipayClient(aliPayGateWayUrl, APPID, ALIPAYAPPPRIVATEKEY,
				AlipayConstants.FORMAT_JSON, AlipayConstants.CHARSET_UTF8, ALIPAYPUBLICKEY,
				AlipayConstants.SIGN_TYPE_RSA2); 
		AlipayRequest alipayRequest = new AlipayTradeWapPayRequest();
		AlipayTradeWapPayModel alipayTradeWapPayModel = new AlipayTradeWapPayModel();
		alipayTradeWapPayModel.setOutTradeNo(orderNum);// 订单号
		alipayTradeWapPayModel.setTotalAmount(String.valueOf(totalAmount));// 总金额
		alipayTradeWapPayModel.setSubject(URLEncoder.encode(subject,"utf-8"));// 商品名称
		alipayTradeWapPayModel.setProductCode("QUICK_WAP_PAY"); // WAP：手机APP和浏览器
		alipayRequest.setBizModel(alipayTradeWapPayModel);
		alipayRequest.setReturnUrl("http://3o3e701659.picp.vip/order/aliPaySuccess");// 同步url地址 支付宝成功后返回的页面
		alipayRequest.setNotifyUrl("http://3o3e701659.picp.vip/order/aliPayCallBack"); // 异步url地址  支付宝回调函数 修改数据库内自己的订单状态
		return alipayClient.pageExecute(alipayRequest).getBody();
	}
	/**
	 *	WAIT_BUYER_PAY	交易创建，等待买家付款
	 *	TRADE_CLOSED	未付款交易超时关闭，或支付完成后全额退款
	 *	TRADE_SUCCESS	交易支付成功
	 *	TRADE_FINISHED	交易结束，不可退款
	 */
	/**
	 * 程序执行完后必须打印输出“success”（不包含引号）。
	 * 如果商户反馈给支付宝的字符不是success这7个字符，
	 * 支付宝服务器会不断重发通知，
	 * 直到超过24小时22分钟。在25小时内完成6~10次通知
	 * （通知频率：5s,2m,10m,15m,1h,2h,6h,15h）。
	 * */
	public static Map<String, String> aliPayCallBack(HttpServletRequest request) throws AlipayApiException {
		Map<String, String> parameterMap = getParameterMap(request);
		// 该请求是否来源于阿里  验签
		boolean signVerified = AlipaySignature
	          .rsaCheckV1(parameterMap, ALIPAYPUBLICKEY, AlipayConstants.CHARSET_UTF8, AlipayConstants.SIGN_TYPE_RSA2);
		System.out.println(parameterMap);
		// 判断订单状态是否成功
		//	支付时传入的商户订单号 out_trade_no
		//支付宝28位交易号 trade_no
		if(signVerified && "TRADE_SUCCESS".equals(parameterMap.get("trade_status"))) {
			 Map<String,String> map = new HashMap<String,String>();
			 // 商家订单号
			String merchant  = parameterMap.get("out_trade_no");
			// 支付宝的订单号
			String aliPay = parameterMap.get("trade_no");
			map.put("out_trade_no", merchant);
			map.put("ali_trade_no", aliPay);
			map.put("status", parameterMap.get("trade_status"));
			return map;
		}
		// return fail/success;
		return null;
	}
	
	/**
	 * 将request中的数据转换成map
	 * @param request
	 * @return
	 */
	public static Map<String,String> getParameterMap(HttpServletRequest request) {
        Map paramsMap = request.getParameterMap(); 
        Map<String,String> returnMap = new HashMap();
        Iterator entries = paramsMap.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if(null == valueObj){
                value = "";
            }else if(valueObj instanceof String[]){
                String[] values = (String[])valueObj;
                for(int i=0;i<values.length;i++){
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length()-1);
            }else{
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }
}

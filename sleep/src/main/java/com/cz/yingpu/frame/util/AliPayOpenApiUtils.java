package com.cz.yingpu.frame.util;

import java.util.HashMap;
import java.util.Map;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransOrderQueryRequest;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;

public class AliPayOpenApiUtils {

	
	public static String sign_type = "RSA2";
	public static String input_charset = "utf-8";
	//public static String Pid = "2017042406935652";
	public static String Pid = "2018031302365299";
	public static String app_ali_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDctoke7nBiEkaoqjzl9Xrshj0zQa5E1LiXnNHOgLj8PJuJ541+PhTZFc7PA+LRCrhEtFTihbYEOKvKaVJnCrTBA2O8vT7wRKnsevNS9XIu7nKYbvwLexXl2FSiGTICVJkNJfDy9xSF1sThOA7eax15ERY2gRiXufDOHVnpz1n2mBx1AMKER18csod4P99AGPCWzzV6a5smDH/N6fSPEBjIKV0pRn/qLoYkQO4Ivi+P0dstd7LmJre08ovnQRr0vZphXUPqo7Vc7erWb8ooR6wA1EnrOoB8/yC+KHYLs6bRWNPVMCmvVL00bHJIR9oKIW5F0KHBxyfMnDCnaUYUHAeBAgMBAAECggEAHbvlmyoqL7wgksZkb+JzTfwDsK0PiixSNfKlfTlwMmMD3T85m6FHc7txYgg2/UyBaJgWu3GiBM2RrzIjLMiPc1U+kYW5PesyLS6fKzTvzgzHmWuSPwW0/IL5W+EjEJdPCytqW4EX3bQCUAkaT7KEhPPdVzhrFL2zCEGCBHKvvv2c8ucpyxqOQXyx+rRPhhmzhVbIJ1xjV7eiz8UuYQ+jWwicmKPWwmIt17wGIBNq4qlJp7hIlzq0hDHRlkOgAfpJQBcGicaFvF4JPl8H+i6pjY9E/SeR7CV7B1ShssvNgQdpZ7fEH4WBkBEe3IPqyAWxuqvGxSysc8FTbcq4QxqewQKBgQD4x32/winX/R4gpkIXccKDzsuij0OIPzrwYoZPbHpBj7RI/GlC0Ic5rnLoyj7eneQOhxv+OVzGz1kxvUthXsFtHkIlgqV/fSTYBd93il4glEUB6PJ33+Zy8JGpOGv/AlRlakstd9hqhE/oy5RKuonUiXjNCyXSa3ozLekqBdD1iQKBgQDjHoDfuWCrr21U1OHoynIY9F46ZEEiaT2GCs5QjHJlc8hkqLhCDU2jcuFOdFrewlBCKvStHw0LK2j+iULug5CCq+qIiPV/PFD0/uPvauQSel3bd40oBhscBRiVHOJz+Ee3ikMyHFtS5z2m3rZORq1Ea7ipoLJkw3Zj6WsOsa98OQKBgBG+GIfOjzFqSfXGJbyKTnccDoXfdc+HwupV/II5mFaIqzDxX4h2Ws6QVH2pvuYdmKA4npR4/tbm/tN/Tpr5KQ5hNBJ8yNTY1gHaGV/rLF9ic7F5LaHDBCp6YckRzxH21pINDKOHgpXGLfTKYB8cQqw3ybfF7cKeC68OvSxUEd0RAoGAcm186rsgXH+vKu1ywHG3GKGOFAdM1cSGLdGl7ulD/Z9fGG/1HbpMIoyPvZJb8h2eiCfSTKL3VGhTpuKLKVZ56YwqRw4R+j/uXfFJ7+c/75tdTGSoZsDkybI7l20NHva5U1zCzC2DSXqeEkPlP/zV8UIAsN3441GjhWUl/ipByukCgYA3Cw6zWzpVmmOXHOXhI+RFohjzzt3Zb1vmbpkW+AJuysrRDpymm9shQT6jxVV/9ZcDYICKCol9w7LDV6ZhZYvDwWhmRjs+cgAercKoc1tMADtVlnBxxes8VqVqpIEODdhloiZhCMqbsOpQWMg0S09wQzWHtYWUycIKeA2P/MgxnQ==";
	public static String ali_application_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDFoA6MrdwMmJCpEDKEkL+nANLrQwdzMMdLGOq8upRbqkxDCl0UGLUc3vrIekocdQRRwsXVsz0LJes1PFDTFvSlY6RpKq+1Bd3uhaY/ObCumuoa7RLhWkwTXTd+x0Yq+wDYuDsQpx6EfaaK7/uqDrHqRa7BgVyHwRp+ykSSilnVJwIDAQAB";
	public static String ali_public_key="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiG7eGIjDWUnBKjK0F75DhkxuaoPetSw1IojBc++BIs8rE3beGHtvHCZkPoNnNHkipuYNgVGjcdVcRjgvK2HTOfzl2ynXIq5M7KvX73enZ02w8G/ho5CyDYdlyCHTUi3MMc+0qyhzwEEnTYvm3F7NpAsocGvBFLL8ECezVXw11mIFFHTqmT2FLduFMJIrx89UTSp1QtpXrUJvWWF64ai3WtkqWdpuWmJ8ZWhIn6f42Krb5MFzrE0vnxVGXvWvZgxsDhEN8FHHzI2N6zFkfwAv+XAoHhiQhA4bGFNS3NTqDbZ+g3P0X7yOesc3xQNPj5UukWo9ZapC+qUB+dTwpMYWmQIDAQAB";
	//public static String app_ali_private_key="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKoVLJFo5jESfzXndmYLOL6BHlzElBosxpv4/zJz3soHdZ78C9be/eK4jMHbDa6CKjarQX1DcENdkibf6+7h4vyVcmuDmRQU55DZ6bdFpIH9SM/uBAx9gH+Zk9A6Mrbs3mWvYpMgMWjyTojr015Gt/VJazINuvtDcg4LikIrvoa5AgMBAAECgYAsDxohfOwMkD8V5qTcIFINv4LvAxlegnTcmK77EdsHuND2Y1Jp0hAwuB/2Y9lyeFOZH9nyKhMd0FJ48Y67rhZPbahtA0oFofTQ+clA+gMRQ+DHz+t/eabvcIf3ig9uvvBbn5xHFin7FCoS9jyEv5Im72RifQ09A1TPzqS+Fn88JQJBAOKxazF+a/nlslicSntnS6chWXyWR1xJnQLEVDn1aYYCOb34o9k+RtSdodWASF9KdtdeB5cJjSQWpYazKm2iypsCQQDAEjFY+s9+JXzAobiuAphaMdeDN7R+vqB9ZVc1DB3pZjqCkWLxdu/mZKkENQhr/Z0QLrQw0fCjwVtZDpLBXE87AkEAi5ok4CLxjcBVd1owSBSRs0fHWwEUyxGOPpD3KYYnSN5//qxrArLmXbMBYYCRkRVDYFfIpsu7/nhv0SbuvvucdwJAFhJBH3N64okWQ+CRo1VjAoJVvJtrH2FyedZqZgslaMu5j4V7dDnxqW0JyxT5CcfUA6cKR83oNDkk8R/ul0+vPwJBAKWKX7jvHK66kMYQLMLMmIeVunwOSFh4AwOE3AXHmc3QVIU5MMFLwDJb/dDXJUy9XN6KyLvSU7MIXUd+H/dnxfI=";
	//public static String ali_public_key="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	/**
	 *
	 * 支付宝转账
	 * 
	 * @param tradeNo 流水号
	 * @param remark 备注
	 * @param total 金额
	 * @param payee_account收款方账户
	 * @param realname真实姓名
	 * @author dc
	 * @return
	 */
	public static AlipayFundTransToaccountTransferResponse ALiPayZZ(String tradeNo, String remark, String total,
			String payee_account,String realname) {
		try {
			String content="{" +
					"    \"out_biz_no\":\""+tradeNo+"\"," +
					"    \"payee_type\":\"ALIPAY_LOGONID\"," +
					"    \"payee_account\":\""+payee_account+"\"," +
					"    \"amount\":\""+total+"\"," +
					"    \"payer_show_name\":\""+remark+"\"," +
					"    \"payee_real_name\":\""+realname+"\"," +
					"    \"remark\":\"转账备注\"" +
					"  }";
			
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",Pid
					,app_ali_private_key,"json",input_charset,ali_public_key,sign_type);
			AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
			request.setBizContent(content);
			AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
			return response;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	public static String ALiPayZZ(String tradeNo, String remark, String total,
			String payee_account) {
		try {
			String content="{" +
					"    \"out_biz_no\":\""+tradeNo+"\"," +
					"    \"payee_type\":\"ALIPAY_LOGONID\"," +
					"    \"payee_account\":\""+payee_account+"\"," +
					"    \"amount\":\""+total+"\"," +
					"    \"payer_show_name\":\""+remark+"\"," +
					"    \"remark\":\"转账备注\"" +
					"  }";
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",Pid
					,app_ali_private_key,"json",input_charset,ali_public_key,sign_type);
			AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
			request.setBizContent(content);
			AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
			System.out.println(response.getBody());
			if(response.isSuccess()){
				return response.getOrderId();
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *查询支付宝转账结果
	 * 
	 * @param tradeNo 系统流水号
	 * @param 
	 * @author dc
	 * @return
	 */
	public static Boolean ALiPayZZSelect(String tradeNo, String aliordernumber) {
		try {
		

			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",Pid
					,app_ali_private_key,"json",input_charset,ali_public_key,sign_type);
			AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
			request.setBizContent("{" +
			//"    \"out_biz_no\":\""+tradeNo+"\"," +
			"    \"order_id\":\""+aliordernumber+"\"" +
			"  }");
			AlipayFundTransOrderQueryResponse response = alipayClient.execute(request);
			System.out.println(response.getBody());
			if(response.isSuccess()){
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	
	/**
	 *支付宝退款
	 * 
	 * @param out_trade_no 商户订单号
	 * @param refund_amount 需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数
	 * @param refund_reason 退款原因
	 * @author dc
	 * @return
	 */
	public static Map<String, String> ALiPayRefund(String out_trade_no, Double refund_amount,
			String refund_reason) {
		AlipayTradeRefundResponse response=new AlipayTradeRefundResponse();
		Map<String, String> map=new HashMap<String, String>();
		try {
			String content="{" +
					"\"out_trade_no\":\""+out_trade_no+"\"," +
					"\"refund_amount\":"+refund_amount+"," +
					"\"refund_currency\":\"CNY\"," +
					"\"refund_reason\":\"押金退款\"" +
					"}";
			
			
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",Pid
					,app_ali_private_key,"json",input_charset,ali_public_key,sign_type);
			AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
			request.setBizContent(content);
		
			 response = alipayClient.execute(request);
			 //返回状态码等于10000代表正常退款
			 if(response.getCode().equals("10000")){
				 map.put("status", "SUCCESS");
				 //本次退款是否发生了资金变化 Y
				 map.put("fund_change", response.getFundChange());
				 map.put("sub_code", response.getSubCode());
				 map.put("refund_fee", response.getRefundFee());
				
			 }else{
				 map.put("status", "ERROR");
				 map.put("code", "20000");
				 map.put("msg", response.getMsg());
				 map.put("sub_code", response.getSubCode());
				 map.put("sub_msg", response.getSubMsg());
			 }
			return map;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return map;
	}
	
	public static void main(String[] args) {
		AliPayOpenApiUtils.ALiPayZZ("20170010021125dfasd2waa22", "测试", "0.1", "18792546455");
		// String rootPath=AliPayUtils.class.getClass().getResource("/").getFile().toString();  
		 //System.out.println(rootPath);
		//AliPayUtils.	ALiPayZZSelect("","20170412110070001502600004089061");
		try {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

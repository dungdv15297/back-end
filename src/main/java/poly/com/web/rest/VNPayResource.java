package poly.com.web.rest;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poly.com.config.VNPayConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/pay")
public class VNPayResource {

	 @PostMapping("/checkout")
		public String checkout(HttpServletRequest req) throws UnsupportedEncodingException {
			int a = 1000000;
	        Map<String, String> vnp_Params = new HashMap<>();
	        vnp_Params.put("vnp_Version", "2.0.0");
	        vnp_Params.put("vnp_Command", "pay");
	        vnp_Params.put("vnp_TmnCode", VNPayConfig.vnp_TmnCode);
	        vnp_Params.put("vnp_Amount", String.valueOf(a*100));
	        vnp_Params.put("vnp_CurrCode", "VND");
	        vnp_Params.put("vnp_BankCode", "NCB");
	        vnp_Params.put("vnp_TxnRef", VNPayConfig.getRandomNumber(8));
	        vnp_Params.put("vnp_OrderInfo", "1111");
	        vnp_Params.put("vnp_OrderType", "billpayment");
	        vnp_Params.put("vnp_Locale", "vn");
	        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_Returnurl);
	        vnp_Params.put("vnp_IpAddr", VNPayConfig.getIpAddress(req));
	        vnp_Params.put("vnp_CreateDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

	        //Build data to hash and querystring
	        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
	        Collections.sort(fieldNames);
	        StringBuilder hashData = new StringBuilder();
	        StringBuilder query = new StringBuilder();
	        Iterator<String> itr = fieldNames.iterator();
	        while (itr.hasNext()) {
	            String fieldName = (String) itr.next();
	            String fieldValue = (String) vnp_Params.get(fieldName);
	            if ((fieldValue != null) && (fieldValue.length() > 0)) {
	                //Build hash data
	                hashData.append(fieldName);
	                hashData.append('=');
	                hashData.append(fieldValue);
	                //Build query
	                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
	                query.append('=');
	                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

	                if (itr.hasNext()) {
	                    query.append('&');
	                    hashData.append('&');
	                }
	            }
	        }
	        String queryUrl = query.toString();
	        String vnp_SecureHash = VNPayConfig.Sha256(VNPayConfig.vnp_HashSecret + hashData.toString());
	        queryUrl += "&vnp_SecureHashType=SHA256&vnp_SecureHash=" + vnp_SecureHash;
	        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
//	        JSONPObject job = new JSONPObject();
//	        job.addProperty("code", "00");
//	        job.addProperty("message", "success");
//	        job.addProperty("data", paymentUrl);

	        return  paymentUrl;
		}
}

package src.main.java.poly.com.web.rest;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class VNPayResource {
	 @PostMapping("/updateByUser")
    public String updateDetail(@RequestBody AccountDetailDto param) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accountRepository.findByUserName(username);

        AccountDetail oldDetail = accountDetailRepository.findById(account.getId());

        oldDetail.setGender(param.getGender());
        oldDetail.setName(param.getName());
        oldDetail.setBirthday(Instant.parse(param.getBirthday()));
        oldDetail.setEmail(param.getEmail());
        oldDetail.setAddress(param.getAddress());
        oldDetail.setLastModifiedBy(username);
        oldDetail.setLastModifiedDate(Instant.now());
        accountDetailRepository.save(oldDetail);
        return oldDetail.getId();
    }
	 
	 @PostMapping("/checkout")
		public String checkout(HttpServletRequest req, @CurrentUser UserPrincipal userPrincipal, ModelMap model) throws UnsupportedEncodingException {

			User user = userPrincipal.getCurrentUser();
			List<CartItem> cartItems = cartItemService.findByUser(user);

			OrderWeb orderWeb = new OrderWeb();
			orderWeb.setConsignee(req.getParameter("name"));
			orderWeb.setConsigneePhone(req.getParameter("phone"));
			orderWeb.setDeliveryAddress(req.getParameter("address"));
			orderWeb.setUser(user);

			String paymentMethod = req.getParameter("paymentMethod");
			if (paymentMethod.equalsIgnoreCase(PaymentMethod.COD)) {
				orderWeb.setPaymentMethod(PaymentMethod.COD);

			} else if (paymentMethod.equalsIgnoreCase(PaymentMethod.ATM)) {
				orderWeb.setPaymentMethod(PaymentMethod.ATM);
			}
			orderWebService.save(orderWeb);

			long totalAmount = 0;
			for (CartItem cartItem : cartItems) {
				OrderWebDetail orderWebDetail = new OrderWebDetail();
				ProductSize productSize = cartItem.getProductSize();
				long price = productSize.getProduct().getPrice();
				int quantity = cartItem.getQuantity();

				orderWebDetail.setOrderWeb(orderWeb);
				orderWebDetail.setProductSize(productSize);
				orderWebDetail.setPrice(price);
				orderWebDetail.setQuantity(quantity);
				orderWebDetail.setTotalAmount(price * quantity);
				orderWebDetailService.save(orderWebDetail);

				totalAmount += price * quantity;
			}
			orderWeb.setTotalAmount(totalAmount);
			orderWeb.setPaymentStatus(PaymentStatus.PENDING_ATM);
			orderWeb.setDeliveryStatus(DeliveryStatus.NOT_APPROVED);
			orderWeb = orderWebService.save(orderWeb);

			if (orderWeb.getPaymentMethod().equalsIgnoreCase(PaymentMethod.COD)) {
				orderWeb.setPaymentStatus(PaymentStatus.UNPAID);

				orderWebService.save(orderWeb);
		        cartItemService.deleteByUser(user);
				return "redirect:/order-result/" + orderWeb.getId();
			}

	        Map<String, String> vnp_Params = new HashMap<>();
	        vnp_Params.put("vnp_Version", "2.0.0");
	        vnp_Params.put("vnp_Command", "pay");
	        vnp_Params.put("vnp_TmnCode", VNPayConfig.vnp_TmnCode);
	        vnp_Params.put("vnp_Amount", String.valueOf(totalAmount*100));
	        vnp_Params.put("vnp_CurrCode", "VND");
	        vnp_Params.put("vnp_BankCode", "NCB");
	        vnp_Params.put("vnp_TxnRef", VNPayConfig.getRandomNumber(8));
	        vnp_Params.put("vnp_OrderInfo", String.valueOf(orderWeb.getId()));
	        vnp_Params.put("vnp_OrderType", "billpayment");
	        vnp_Params.put("vnp_Locale", "vn");
	        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_Returnurl);
	        vnp_Params.put("vnp_IpAddr", VNPayConfig.getIpAddress(req));
	        vnp_Params.put("vnp_CreateDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

	        //Build data to hash and querystring
	        List<String> fieldNames = new ArrayList<String>(vnp_Params.keySet());
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
	        JsonObject job = new JsonObject();
	        job.addProperty("code", "00");
	        job.addProperty("message", "success");
	        job.addProperty("data", paymentUrl);

	        return "redirect:" + paymentUrl;
		}
}

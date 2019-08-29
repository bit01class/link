package com.bit.oauth01;

import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class HomeController {
	
	private String client_ID="a34223c096389c3b5f63";
	private String client_Secret="5ffed104018a862a07e85bae8700253aee3aea3d";
	private String url="https://github.com/login/oauth/authorize";
	private String callback="http://localhost:8080/oauth01/callback";
	private String scope="user public_repo";
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "home";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void login(Model model) {
		String url=this.url;
		url+="?client_id="+client_ID;
		url+="&redirect_uri="+callback;
		url+="&scope="+scope;
		model.addAttribute("gitpath", url);
	}
	
	@RequestMapping(value = "/callback", method = RequestMethod.GET,produces = "text/html; charset=utf-8")
	public @ResponseBody ResponseEntity<String> callback(String code) {
		logger.info(code);
		String url="https://github.com/login/oauth/access_token";
		RestTemplate template=new RestTemplate();
		
		
		HttpEntity<MultiValueMap<String, String>> request=null;
		MultiValueMap<String, String> body=new LinkedMultiValueMap<String, String>();
		body.add("client_id", client_ID);
		body.add("client_secret", client_Secret);
		body.add("code", code);
		MultiValueMap<String, String> headers=new LinkedMultiValueMap<String, String>();
//		headers.set("content-type", "application/x-www-form-urlencoded");
		headers.set("Accept", "application/json");
		request=new HttpEntity<MultiValueMap<String,String>>(body, headers);
		ResponseEntity<Map> result = template.postForEntity(url, request, Map.class);
		logger.info(result.getBody().get("access_token").toString());
		String access_token=result.getBody().get("access_token").toString();
		String token_type=result.getBody().get("token_type").toString();
		url="https://api.github.com/user";
		template=new RestTemplate();
		
		MultiValueMap<String, String> uriVar=new LinkedMultiValueMap();
		uriVar.add("Authorization", token_type+" "+access_token);
		HttpEntity requestEntity=new HttpEntity(uriVar);
		result= template.exchange(url, HttpMethod.GET, requestEntity, Map.class);
		return new ResponseEntity<String>(result.getBody().get("login").toString()+"님 환영합니다",HttpStatus.OK);
		
	}
		
}
































package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.velocity.runtime.directive.Break;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.frame.Service;
import com.vo.UserVO;

import freemarker.core.StopException;

@Controller
public class MainController {
	MyMqtt_Pub_client client;
	
	private Logger work_log = 
			Logger.getLogger("work"); 
	private Logger user_log = 
			Logger.getLogger("user"); 
	private Logger data_log = 
			Logger.getLogger("data"); 
	
	public  MainController() {
		client = new MyMqtt_Pub_client();
	}
	
	@Resource(name="uservice")
	Service<String, UserVO> service;
	
	@RequestMapping("/main.mc")
	public ModelAndView main() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("main");
		return mv;
	}
	
	@RequestMapping("/iot1.mc")
	@ResponseBody
	public void iotdata(HttpServletRequest request) throws IOException {
		String temp = request.getParameter("temp");
		String humi = request.getParameter("humi");
		int f_temp = Integer.parseInt(temp);
		int f_humi = Integer.parseInt(humi);
		System.out.println("온도 : "+f_temp+" : "+"습도 : "+f_humi);
//		if(f_temp > 30) {
//			try {
//				FcmUtil.sendServer(f_temp, f_humi);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		//data_log.debug(f_temp+" : "+f_humi);
		
	}
	
	@RequestMapping("/iot2.mc")
	@ResponseBody
	public void iotdata2(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String temp = request.getParameter("temp");
		double f_temp = Double.parseDouble(temp);
		System.out.println(f_temp);
		work_log.debug(f_temp);
		
		if(f_temp >= 0 && f_temp <= 20 ) {
			out.print("0");	
		}else if(f_temp >= 21 && f_temp <= 30) {
			out.print("2");
		}else if(f_temp >= 31 && f_temp <= 40) {
			out.print("3");
		}
		
		out.close();
		
	}
	
	
	@RequestMapping("/uu.mc")
	@ResponseBody
	public void uu(HttpServletResponse response) throws IOException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		ArrayList<UserVO> list = null;
		try {
			list = service.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONArray ja = new JSONArray();
		for(UserVO u:list) {
			JSONObject jo = new JSONObject();
			jo.put("id", u.getId());
			jo.put("pwd", u.getPwd());
			jo.put("name", u.getName());
			ja.add(jo);
		}
		out.print(ja.toJSONString());
		out.close();
	}
	
	@RequestMapping("/login.mc")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("center", "login");
		mv.setViewName("main");
		return mv;
	}
	@RequestMapping("/logout.mc")
	public ModelAndView logout(
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = 
				request.getSession();
		if(session != null) {
			session.invalidate();
		}
		mv.setViewName("main");
		return mv;
	}
	
	
	@RequestMapping("/loginimpl.mc")
	public ModelAndView loginimpl(
			HttpServletRequest request) {
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		ModelAndView mv = new ModelAndView();
		
		UserVO dbuser = null;
		try {
			dbuser = service.get(id);
			if(dbuser.getPwd().equals(pwd)) {
				mv.addObject("center", "ok");
				HttpSession session 
				= request.getSession();
				session.setAttribute("loginid", id);
			}else {
				mv.addObject("center", "fail");
			}
		} catch (Exception e) {
			mv.addObject("center", "fail");
			e.printStackTrace();
		}
		
		
		mv.setViewName("main");
		return mv;
	}
	
	@RequestMapping("/led.mc")
	@ResponseBody
	   public ModelAndView led_show(HttpServletRequest request){
	      String LED = request.getParameter("LED");
	      System.out.println(LED);
	   //   data_log.debug(LED);
	      ModelAndView mv = new ModelAndView();
	      HttpSession session = request.getSession();
	      session.setAttribute("status", LED);
	      mv.setViewName("ledtest/led_check");
	      client.send("led","led_"+LED );
	     if(LED==null) {
	    	 return mv;
	     }else if(LED.equals("on")) {
				try {
					FcmUtil.sendServer(LED);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	      return mv;
	   }
	     
}






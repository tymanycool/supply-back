package net.shopin.supply.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class LogoutController {
	@RequestMapping("/logout")
	public String logout(Model model, HttpServletRequest request,
			HttpServletResponse response){

		HttpServletRequest servletRequest=(HttpServletRequest) request;
		HttpSession session=servletRequest.getSession();
		session.removeAttribute("username");
		session.removeAttribute("password");
		session.invalidate();
		return "redirect:"+"/";
	}
	
}

package net.shopin.supply.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexController {

	@RequestMapping("/index")
	public String index(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpSession session = servletRequest.getSession();
		String username = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		if (username == null && password == null) {
			return "login";
		}
		model.addAttribute("userName", username);
		return "desktop";
	}

	@RequestMapping("/index_iframe/{iframesrc}")
	public String index_iframe(Model model, HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("iframesrc") String iframesrc) {
		return "index_iframe/" + iframesrc;
	}

}

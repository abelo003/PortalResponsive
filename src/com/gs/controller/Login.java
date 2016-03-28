package com.gs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class Login {

	@RequestMapping(value = "/login")
	public ModelAndView login(){
		return new ModelAndView("login");
	}
	
	@RequestMapping(value = "/inicio")
	public ModelAndView inicio(){
		return new ModelAndView("inicio");
	}
}

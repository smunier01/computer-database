package com.excilys.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.excilys.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.core.model.User;

@Controller
public class UserController {

	@Autowired
	private IUserService userService;

	/**
	 * Display the users.
	 *
	 * @param model
	 *            Spring ModelMap.
	 * @return servlet name
	 */
	@RequestMapping(value = "/admin")
	public String adminDashboard(ModelMap model, HttpServletRequest request) {
		// Add the information "isAdmin" to the model
		model.addAttribute("isAdmin", request.isUserInRole("ROLE_ADMIN"));

		// Set the user list
		model.addAttribute("list", userService.listAllUser());

		return "admin";
	}

	@RequestMapping(value = "/user/add", method = RequestMethod.GET)
	public String addOrEditUser(ModelMap model, @RequestParam(required = false, name = "userUserName") String username) {
		// If a username is specify : edit mode
		if (username != null) {
			User user = userService.findByName(username);
			if (user != null) {
				model.addAttribute("user", user);
			}
		}
		return "addUser";
	}

	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public String addOrEditUserPOST(ModelMap model, @Valid User user) {
		userService.create(user);
		
		return "redirect:/admin";
	}
}

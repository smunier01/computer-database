package com.excilys.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.core.model.User;
import com.excilys.service.service.IUserService;

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

	@RequestMapping(value = "/user/addEdit", method = RequestMethod.GET)
	public String editUser(ModelMap model, @RequestParam(required = false, name = "id") Integer id) {
		// If an id is specify : edit mode
		if (id != null) {
			User user = userService.find(id);
			if (user != null) {
				model.addAttribute("user", user);
			}
		}
		return "addEditUser";
	}
	
	@RequestMapping(value = "/user/addEdit", method = RequestMethod.POST)
	public String editUserPOST(ModelMap model, @Valid User user) {
		// Case 1 : Add user
		if (user.getId() == null) {
			userService.create(user);
		}
		else {
			System.out.println(user);
			userService.edit(user);
		}
		
		return "redirect:/admin";
	}
}

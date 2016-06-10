package com.excilys.webapp.controller;

import com.excilys.core.model.User;
import com.excilys.service.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
			user.setPassword(encodePassword(user.getPassword()));
			userService.create(user);
		}
		// Case 2 : Edit user
		else {
			// Change the password only if modified
			if(user.getPassword().isEmpty()) {
				user.setPassword(userService.find(user.getId()).getPassword());
			} else {
				user.setPassword(encodePassword(user.getPassword()));
			}
			userService.edit(user);
		}
		
		return "redirect:/admin";
	}

	private static String encodePassword(String password){
		return org.apache.commons.codec.digest.DigestUtils.sha256Hex(password); 
	}
	
	@RequestMapping(value = "/user/delete", method = RequestMethod.POST)
	public String deleteUser(ModelMap model, String selection) {
		String[] array = selection.split(",");
		for(String st : array) {
			Integer i = Integer.parseInt(st);
			userService.remove(i);
		}
		return "redirect:/admin";
	}
}

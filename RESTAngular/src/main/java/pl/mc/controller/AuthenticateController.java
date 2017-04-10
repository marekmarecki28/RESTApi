package pl.mc.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.mc.dao.UserDAO;
import pl.mc.dao.impl.LoginDAO;
import pl.mc.model.User;
import pl.mc.service.AuthenticationService;

@RestController
@RequestMapping(value = "/api")
public class AuthenticateController {

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/authenticate",method = RequestMethod.POST)
    public User authenticate(@RequestBody LoginDAO loginDAO, HttpServletResponse response) throws Exception{
    	System.out.println("XXX: " + loginDAO.getLogin() + " " + loginDAO.getPassword());
    	return authenticationService.authenticate(loginDAO,response);
    }
}
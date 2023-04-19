package ar.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@GetMapping("/home")
	public String homePage(){
		return "Hello";
	}
	
	@GetMapping("/admin")
	public String adminPage(){
		return "Hello Admin";
	}
	
	@GetMapping(value="/dba")
	public String dbaPage(){
		return "Hello DBAdmin";
	}

}

package com.example.doancuoiki.controller;


import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.doancuoiki.service.IUserServices;

@Controller
public class RegisterController {

	@Autowired
    private IUserServices userService;
	
	@GetMapping("/register")
    public String showRegisterForm() {
        return "register"; // Trả về trang đăng ký
    }
	
	@PostMapping("/register")
    public String registerUser(@RequestParam String username, 
                               @RequestParam String password, 
                               @RequestParam String email, 
                               @RequestParam String fullname, 
                               @RequestParam String phone, 
                               Model model,
                               RedirectAttributes redirectAttributes) {
        boolean isSuccess = userService.register(username, password, email, fullname, phone);
        if (isSuccess) {
        	redirectAttributes.addFlashAttribute("alert", "Đăng ký thành công!");
            return "login"; // Chuyển hướng sang trang đăng nhập
        } else {
        	 model.addAttribute("popup", "Đăng ký thất bại! Tên đăng nhập, email hoặc số điện thoại đã tồn tại.");
            return "register"; // Quay lại trang đăng ký
        }
        
        
        
	}
	
}

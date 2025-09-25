package com.example.doancuoiki.controller;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.doancuoiki.service.IUserServices;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Controller
public class RegisterController {

    @Autowired
    private IUserServices userService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String email,
                               @RequestParam String fullname,
                               @RequestParam String phone,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        // Hash password using SHA-256
        String hashedPassword = hashSHA256(password);

        // Gọi userService.register, giả sử nó trả về một Map chứa thông tin lỗi
        Map<String, String> result = userService.register(username, hashedPassword, email, fullname, phone);
        boolean isSuccess = Boolean.parseBoolean(result.get("success"));

        if (isSuccess) {
            redirectAttributes.addFlashAttribute("alert", "Đăng ký thành công!");
            return "redirect:/login";
        } else {
            // Xây dựng thông báo lỗi chi tiết
            StringBuilder errorMessage = new StringBuilder("Đăng ký thất bại! ");
            if (result.containsKey("username") && result.get("username").equals("exists")) {
                errorMessage.append("Tên đăng nhập đã tồn tại. ");
            }
            if (result.containsKey("email") && result.get("email").equals("exists")) {
                errorMessage.append("Email đã tồn tại. ");
            }
            if (result.containsKey("phone") && result.get("phone").equals("exists")) {
                errorMessage.append("Số điện thoại đã tồn tại.");
            }

            // Gửi lại các giá trị đã nhập và thông báo lỗi
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            model.addAttribute("fullname", fullname);
            model.addAttribute("phone", phone);
            model.addAttribute("popup", errorMessage.toString());
            return "register";
        }
    }

    // SHA-256 hash function
    private String hashSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
}

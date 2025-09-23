package com.example.doancuoiki.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.doancuoiki.entity.Booking;
import com.example.doancuoiki.entity.San;

import com.example.doancuoiki.respository.BookingRepository;

import com.example.doancuoiki.service.ISanService;

import com.example.doancuoiki.utils.Constant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
@Controller
public class ConfirmController { 
	@Autowired
    private ISanService sanService;
	 @Autowired
	    private BookingRepository bookingRepository;
	 
	 
	 
	 
	 @GetMapping("/confirm")
	 public String confirmPage(HttpSession session, HttpServletRequest request, Model model) {
		
		    // Chặn nếu URL chứa query string (lỗ hổng)
		    if (request.getQueryString() != null) {
		        return "redirect:/booknow"; // từ chối nếu có người cố gắng nhập URL trực tiếp
		    }
		    
		    
		    
		    
		    
		    
		 
	     // Lấy lại dữ liệu từ session (nếu cần)
	     String fieldName = (String) session.getAttribute(Constant.SESSION_FIELDNAME);
	     String bookingDate = (String) session.getAttribute(Constant.SESSION_BOOKINGDATE);
	     String fullname = (String) session.getAttribute(Constant.SESSION_FULLNAME);
	     String user_id = (String) session.getAttribute(Constant.SESSION_USERID);
	     
	     if (fieldName == null || bookingDate == null) {
	         return "redirect:/booknow";
	     }
	     model.addAttribute("fieldName", fieldName);
	     model.addAttribute("bookingDate", bookingDate);
	     model.addAttribute("fullname", fullname);
	     model.addAttribute("user_id", user_id);

	     San san = sanService.FindBySanName(fieldName);
	     Long sanID = san.getSan_id();
	     String san_id = sanID.toString();
	     model.addAttribute("san_id", san_id);

	     List<Booking> bookingsOnDate = bookingRepository.findByDateAndSanid(bookingDate, san_id);
	     List<String> disabledTimes = bookingsOnDate.stream()
	             .map(Booking::getTime)
	             .collect(Collectors.toList());
	     model.addAttribute("disabledTimes", disabledTimes);

	     if ("Sân 5-1".equals(fieldName)) {
	         model.addAttribute("fieldImage", "/images/anh1.jpg");
	     } else if ("Sân 5-2".equals(fieldName)) {
	         model.addAttribute("fieldImage", "/images/anh6.jpg");
	     } else if ("Sân 7".equals(fieldName)) {
	         model.addAttribute("fieldImage", "/images/anh3.jpg");
	     }

	     return "confirmation";
	 }
	 
	 
	 
	 
	 
	 //controller trung gian 
	 @PostMapping("/pre-confirm")
	 public String saveToSessionAndRedirect(
	         @RequestParam String fieldName,
	         @RequestParam String bookingDate,
	         HttpSession session
	 ) {
	     session.setAttribute(Constant.SESSION_FIELDNAME, fieldName);
	     session.setAttribute(Constant.SESSION_BOOKINGDATE, bookingDate);
	     return "redirect:/confirm";
	 }

	 
	 
	 
	 
	 
	 
	 
	 @PostMapping("/confirma")
	 public String bookingsave(
	         HttpSession session,
	         San san,
	         Booking booking,
	         @RequestParam("bookingDate") String bookingDate,
	         @RequestParam("bookingTime") String time,
	         Model model
	 ) {
	     String fieldName = (String) session.getAttribute(Constant.SESSION_FIELDNAME);
	     String userid = (String) session.getAttribute(Constant.SESSION_USERID);

	     // Kiểm tra dữ liệu hợp lệ
	     if (fieldName == null || userid == null || !isValidDate(bookingDate) || !ALLOWED_TIMES.contains(time)) {
	         model.addAttribute("message", "Thông tin không hợp lệ.");
	         return "redirect:/booknow";
	     }

	     // Tìm sân theo tên
	     san = sanService.FindBySanName(fieldName);
	     if (san == null) {
	         model.addAttribute("message", "Không tìm thấy sân.");
	         return "redirect:/booknow";
	     }

	     Long sanID = san.getSan_id();
	     String sanid = sanID.toString();

	     booking.setDate(bookingDate);
	     booking.setSanid(sanid);
	     booking.setTime(time);
	     booking.setUserid(userid);
	     booking.setPrice("100k");

	     bookingRepository.save(booking);

	     session.setAttribute(Constant.SESSION_FIELDNAME, fieldName);
	     session.setAttribute(Constant.SESSION_BOOKINGDATE, bookingDate);
	     model.addAttribute("booking", booking);
	     model.addAttribute("message", "Booking successfully saved!");

	     return "redirect:/confirm";
	 }


	 
	 
	 
	 
	 
	 
	 
	 private boolean isValidDate(String dateStr) {
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    sdf.setLenient(false); // Không cho phép ngày sai (VD: 2025-02-31)
		    try {
		        sdf.parse(dateStr);
		        return true;
		    } catch (ParseException e) {
		        return false;
		    }
		}
	 
	 private static final List<String> ALLOWED_TIMES = List.of(
			    "06h-07h", "07h-08h", "08h-09h", "09h-10h",
			    "10h-11h", "13h-14h", "14h-15h", "15h-16h",
			    "16h-17h", "17h-18h", "18h-19h", "19h-20h",
			    "20h-21h", "21h-22h", "22h-23h"
			);
}

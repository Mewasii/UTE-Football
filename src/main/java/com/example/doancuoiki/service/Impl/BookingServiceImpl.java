package com.example.doancuoiki.service.Impl;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.doancuoiki.entity.Booking;
import com.example.doancuoiki.respository.BookingRepository;
import com.example.doancuoiki.service.IBookingService;



@Service
public class BookingServiceImpl implements IBookingService {
	 
	 @Autowired
	    private BookingRepository bookingRepository;

	 public UserModel FindByUserName(String username) {
        return userRepository.findByUsername(username);
    }
    
    // Thêm phương thức lấy danh sách booking theo user_id
    public List<Booking> getBookingsByUserId(String userId) {
        return bookingRepository.findByUseridOrderByBookingDateDesc(userId);
    }
    
    // Hoặc nếu bạn muốn xử lý Optional
    public Optional<Booking> getBookingByUserId(String userId) {
        return bookingRepository.findByUserid(userId);
    }
	 
	 
}

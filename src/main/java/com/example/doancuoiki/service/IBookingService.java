package com.example.doancuoiki.service;

import java.util.List;
import java.util.Optional;

import com.example.doancuoiki.entity.Booking;

public interface IBookingService {

	
public List<Booking> getBookingsByUserId(String userId);
	 

}

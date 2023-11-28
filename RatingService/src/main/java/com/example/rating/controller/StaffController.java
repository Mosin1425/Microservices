package com.example.rating.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/staffs")
public class StaffController {

	@GetMapping("/getStaffs")
	public ResponseEntity<String[]> getStaff(){
		String[] list = {"A", "B", "C"};
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
}

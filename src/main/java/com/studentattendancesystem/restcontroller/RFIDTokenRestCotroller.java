package com.studentattendancesystem.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentattendancesystem.model.RFIDToken;
import com.studentattendancesystem.service.RFIDTokenService;

@RestController
@RequestMapping("/rfid")
public class RFIDTokenRestCotroller {

	@Autowired
	private RFIDTokenService rfidTokenService;
	
	@PostMapping("/addRFIDToken/{dId}")
	public ResponseEntity<RFIDToken> saveRFIDToken(@RequestBody RFIDToken rfidToken, @PathVariable("dId") Long dId){
		rfidToken = rfidTokenService.saveRfidToken(rfidToken, dId);
		return ResponseEntity.ok().body(rfidToken);
	}
	
	@GetMapping("/getAllUnassignedTokens/{dId}")
	public List<RFIDToken> getAllNonAssignedTokens(@PathVariable("dId") Long dId){
		return rfidTokenService.getAllNonAssignedTokens(dId);
	}
	
	@GetMapping("/getAllTokens/{dId}")
	public List<RFIDToken> getAllTokens(@PathVariable("dId") Long dId){
		return rfidTokenService.getAllTokens(dId);
	}
	
	@GetMapping("/getAllTokens/{dId}/{currentPage}/{pageSize}")
	public List<RFIDToken> getAllTokensPage(@PathVariable("dId") Long dId, @PathVariable("currentPage") Integer currentPage, @PathVariable("pageSize") Integer pageSize){
		System.out.println("called");
		return rfidTokenService.getAllTokens(dId, currentPage, pageSize);
	}

	
	
	@DeleteMapping("/delete/{rfidId}")
	public ResponseEntity<Map<String, Boolean>> deleteRFIDTokenWithId(@PathVariable("rfidId") Long rfidId){
		Map<String, Boolean> response = new HashMap();
		response.put("Value Deleted", rfidTokenService.deleteRFIDToken(rfidId));
		return ResponseEntity.ok().body(response);
	}

	
	
	
}

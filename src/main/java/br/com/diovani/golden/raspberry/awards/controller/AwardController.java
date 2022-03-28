package br.com.diovani.golden.raspberry.awards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.diovani.golden.raspberry.awards.data.AwardInterval;
import br.com.diovani.golden.raspberry.awards.services.AwardService;

@RestController
@RequestMapping("/award")
public class AwardController {

	@Autowired
	private AwardService awardService;

	@GetMapping("/range")
	public ResponseEntity<AwardInterval> getAwardRange() {
		AwardInterval awardInterval = awardService.findAwardInterval();
		return new ResponseEntity<AwardInterval>(awardInterval, HttpStatus.OK);
	}
}

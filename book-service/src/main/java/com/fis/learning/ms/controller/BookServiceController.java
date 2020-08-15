package com.fis.learning.ms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookServiceController {
	@Autowired
	private Environment environment;
	
	@Autowired
	private BookRepository bookStore;
	
	
	@GetMapping("/books")
  public List<ExchangeValue> retrieveExchangeValue() {
		List<ExchangeValue> ev = evRepository.findAll();
	  //ExchangeValue ret = new ExchangeValue(1001L,"USD","INR",BigDecimal.valueOf(80));
		for(ExchangeValue evalue:ev) {
	  evalue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		}
	  return ev ;
  }

}

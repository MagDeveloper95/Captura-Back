package com.iesFrancisco.captura.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iesFrancisco.captura.Services.FotoService;

@RestController
@RequestMapping("/foto")
public class FotoController {
	
	@Autowired
	FotoService service;
	
	/**
	 * RELLENAR A PARTIR DE AQUI
	 */
}

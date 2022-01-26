package com.iesFrancisco.captura.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iesFrancisco.captura.Services.RegistroService;

@RestController
@RequestMapping("/registro")
public class RegistroController {
	
	@Autowired
	RegistroService service;
	
	/**
	 * RELLENAR A PARTIR DE AQUI
	 */
}

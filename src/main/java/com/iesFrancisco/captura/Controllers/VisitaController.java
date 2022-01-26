package com.iesFrancisco.captura.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iesFrancisco.captura.Services.VisitaService;

@RestController
@RequestMapping("/visita")
public class VisitaController {
	
	@Autowired
	VisitaService service;
	
	/**
	 * RELLENAR A PARTIR DE AQUI
	 */
}

package com.iesFrancisco.captura.Controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iesFrancisco.captura.Model.Registro;
import com.iesFrancisco.captura.Services.RegistroService;

@RestController
@RequestMapping("/registro")
public class RegistroController {
	
	@Autowired
	RegistroService service;

	@GetMapping
	public ResponseEntity<List<Registro>> getAllNotes() {
		List<Registro> all = service.getAllRegistros();
		// Devolvemos la lista y respuestas de http
		return new ResponseEntity<List<Registro>>(all, new HttpHeaders(), HttpStatus.OK);
	}
	/**
	 * Metodo que devuelve nota por id
	 * @param id lo vinculamos con el getmapping ("/{id}") delante de la variable Long id
	 * @return json con la respuesta
	 */
	@GetMapping("/{id}") //Para meterle el id 
	public ResponseEntity<Registro> getNotesById(@PathVariable("id")Long id){
		Registro noteById = service.getRegistroById(id);
		
		return new ResponseEntity<Registro>(noteById,new HttpHeaders(),HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<Registro> create(@Valid @RequestBody Registro note) {
		Registro creatUp = service.creaRegistro(note);
		return new ResponseEntity<Registro>(creatUp,new HttpHeaders(),HttpStatus.OK);
	}
}

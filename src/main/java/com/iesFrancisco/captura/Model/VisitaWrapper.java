package com.iesFrancisco.captura.Model;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

public class VisitaWrapper {

	private Long id;
	private MultipartFile foto;
	private LocalDate fecha;
	private String nota;
	private Obra obra;
}

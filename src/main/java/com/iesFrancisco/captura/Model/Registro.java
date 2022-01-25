package com.iesFrancisco.captura.Model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name="registro")
public class Registro implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
	private Long id;
	@Column(name="titlle", length = 256)
	private String descripcion;
	@Column(name="fecha")
	private LocalDate fecha;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idUser")
	private Usuario usuario;
	
	public Registro() {
		this(-1L,"Por defecto",LocalDate.now(),new Usuario());
	}

	public Registro(Long id, String descripcion, LocalDate fecha, Usuario usuario) {
		this.id = id;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.usuario = usuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Registro [id=" + id + ", descripcion=" + descripcion + ", fecha=" + fecha + ", usuario=" + usuario
				+ "]";
	}
}

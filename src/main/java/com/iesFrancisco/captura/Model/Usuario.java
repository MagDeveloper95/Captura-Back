	package com.iesFrancisco.captura.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	@Column(name = "nombre", length = 256)
	private String nombre;
	@Column(name = "email", length = 256)
	private String email;
	@Column(name = "keyLogueo", length = 256)
	private String key;
	@Column(name = "foto", length = 256)
	private String foto;
	@Column(name = "datos", length = 256)
	private String datos;
	
	@JsonIgnoreProperties(value= "usuario", allowSetters = true)
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Registro> registros = new ArrayList<Registro>();
	
		@JsonIgnoreProperties(value = {"usuario"}, allowSetters = true)
		@ManyToMany(mappedBy = "usuario")
	    private List<Obra> obra;
	

	
	public Usuario(Long id, String nombre, String email, String key, String foto, String datos, List<Registro> registros,List<Obra> obras) {
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.key = key;
		this.foto = foto;
		this.datos = datos;
		this.registros = registros;
		this.obra = obras;
	}

	public Usuario(String nombre, String email, String key, String foto, String datos) {
		this.nombre = nombre;
		this.email = email;
		this.key = key;
		this.foto = foto;
		this.datos = datos;
	}

	public Usuario() {
		this(-1L,"Por defecto","Por defecto","Por defecto","Por defecto","Por defecto",new ArrayList<Registro>(), new ArrayList<Obra>());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getDatos() {
		return datos;
	}

	public void setDatos(String datos) {
		this.datos = datos;
	}

	public List<Registro> getRegistro() {
		return registros;
	}

	public void setRegistro(List<Registro> registros) {
		//this.registros = registros;
		this.registros.clear();
		if(registros!=null) {
			this.registros.addAll(registros);
		}else{
			System.out.println("Error al setear registro en Usuario");
		}
	}

	public List<Obra> getObra() {
		return obra;
	}

	public void setObra(List<Obra> obras) {
		//this.obra = obras;
		this.obra.clear();
		if(obras!=null) {
			this.obra.addAll(obras);
		}else {
			System.out.println("Error al setear obras en Usuario");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", email=" + email + ", key=" + key + ", foto=" + foto
				+ ", datos=" + datos + ", registro=" + registros + "]";
	}	
}

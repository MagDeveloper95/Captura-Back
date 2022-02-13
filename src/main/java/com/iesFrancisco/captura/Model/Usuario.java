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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(notes = "Identificador del usuario", name = "id", required = true, value = "1")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	@ApiModelProperty(notes = "Nombre del usuario", name = "nombre", required = true, value = "Nombre del usuario")
	@Column(name = "nombre", length = 256)
	private String nombre;
	@ApiModelProperty(notes = "Email del usuario", name = "email", required = true, value = "Email del usuario")
	@Column(name = "email", length = 256)
	private String email;
	@ApiModelProperty(notes = "Contraseña del usuario", name = "contrasena", required = true, value = "Contraseña del usuario")
	@Column(name = "keyLogueo", length = 256)
	private String key;
	@ApiModelProperty(notes = "Foto del usuario", name = "Foto", required = true, value = "Foto del usuario")
	@Column(name = "foto", length = 256)
	private String foto;
	@ApiModelProperty(notes = "Datos del usuario", name = "Datos", required = true, value = "Datos del usuario")
	@Column(name = "datos", length = 256)
	private String datos;
	
	@JsonIgnoreProperties(value= "usuario", allowSetters = true)
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Registro> registros = new ArrayList<Registro>();
	
	@JoinTable(
	        name = "usuarioObra",
	        joinColumns = @JoinColumn(name = "idUsuario", nullable = false),
	        inverseJoinColumns = @JoinColumn(name="idObra", nullable = false)
	    )
		@JsonIgnoreProperties(value = {"usuario"}, allowSetters = true)
    	@ManyToMany(    cascade = {
    	        CascadeType.PERSIST, 
    	        CascadeType.MERGE
    	    })
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

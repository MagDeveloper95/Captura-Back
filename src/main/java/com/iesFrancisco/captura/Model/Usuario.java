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
	@Column(name = "key", length = 256)
	private String key;
	@Column(name = "foto", length = 256)
	private String foto;
	@Column(name = "datos", length = 256)
	private String datos;
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	protected List<Registro> registro = new ArrayList<Registro>();
	
	
	@JoinTable(
	        name = "usuarioObra",
	        joinColumns = @JoinColumn(name = "idUsuario", nullable = false),
	        inverseJoinColumns = @JoinColumn(name="idObra", nullable = false)
	    )
    	@ManyToMany(cascade = CascadeType.ALL)
	    private List<Obra> obras;
	

	
	public Usuario(Long id, String nombre, String email, String key, String foto, String datos, List<Registro> registro) {
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.key = key;
		this.foto = foto;
		this.datos = datos;
		this.registro = registro;
	}

	public Usuario(String nombre, String email, String key, String foto, String datos) {
		this.nombre = nombre;
		this.email = email;
		this.key = key;
		this.foto = foto;
		this.datos = datos;
		this.registro = new ArrayList<Registro>();
	}

	public Usuario() {
		this(-1L,"Por defecto","Por defecto","Por defecto","Por defecto","Por defecto",new ArrayList<Registro>());
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
		return registro;
	}

	public void setBooks(List<Registro> registro) {
		this.registro = registro;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", email=" + email + ", key=" + key + ", foto=" + foto
				+ ", datos=" + datos + ", registro=" + registro + "]";
	}	
}

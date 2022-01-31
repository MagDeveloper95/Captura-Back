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

@Entity
@Table (name="obra")
	
public class Obra implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;	
	@Column(name = "nombre", length = 256)
	private String nombre;
	@Column(name = "latLong", length = 256, nullable = false)
	private Coordenada latLong;
	@Column(name = "datos", length = 256)
	private String datos;
	
	@ManyToMany(mappedBy = "obra")
	private List<Usuario> usuario;
	
	@OneToMany(mappedBy ="obra", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Visita> visita;
	
	private Obra(Long id, String nombre, Coordenada latLong, String datos, List<Usuario> usuario,
			List<Visita> visita) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.latLong = latLong;
		this.datos = datos;
		this.usuario = usuario;
		this.visita = visita;
	}


	public Obra(Long id, String nombre, Coordenada latLong, String datos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.latLong = latLong;
		this.datos = datos;
		this.usuario = new ArrayList<Usuario>();
	}

	public Obra() {
		this(-1L,"Por defecto",new Coordenada(),"Por defecto",new ArrayList<Usuario>(), new ArrayList<Visita>());
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


	public String getDatos() {
		return datos;
	}

	public void setDatos(String datos) {
		this.datos = datos;
	}

	public List<Usuario> getUsuario() {
		return usuario;
	}

	public void setUsuario(List<Usuario> usuario) {
		this.usuario = usuario;
	}

	public Coordenada getLatLong() {
		return latLong;
	}

	public void setLatLong(Coordenada latLong) {
		this.latLong = latLong;
	}
	
	public List<Visita> getVisita() {
		return visita;
	}


	public void setVisita(List<Visita> visita) {
		this.visita = visita;
	}
	
	@Override
	public String toString() {
		return "Obra [id=" + id + ", nombre=" + nombre + ", latLong=" + latLong + ", datos=" + datos + ", usuario="
				+ usuario + "]";
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
		Obra other = (Obra) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	

	
	

}

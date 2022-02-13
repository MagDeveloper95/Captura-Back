package com.iesFrancisco.captura.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;


@Entity
@Table(name = "foto")
public class Foto implements Serializable {
	
    private static final long serialVersionUID = 1L;
	@ApiModelProperty(notes ="Identificador de la foto",name = "id", required = true, value = "1")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
    private Long id;
	@ApiModelProperty(notes ="URL de la foto",name = "URL", required = true, value = "URL de la foto")
    @Column(name = "url", length = 256)
    private String url;
	@ApiModelProperty(notes ="Descripcion de la foto",name = "descripcion", required = true, value = "Descripcion de la foto")
    @Column(name = "comentario", length = 256)
    private String comentario;
	@ApiModelProperty(notes ="Visita de la foto",name = "visita", required = true, value = "Visita de la foto")
    @JsonIgnoreProperties(value="fotos", allowSetters = true)
    @ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idVisita")
    private Visita visita;
    
	public Foto() {
		this(-1L,"Por defecto", "Por defecto", new Visita());
	}

	public Foto(Long id, String url, String comentario, Visita visita) {
		super();
		this.id = id;
		this.url = url;
		this.comentario = comentario;
		this.visita = visita;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Visita getVisita() {
		return visita;
	}

	public void setVisita(Visita visita) {
		this.visita = visita;
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
		Foto other = (Foto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Foto [id=" + id + ", url=" + url + ", comentario=" + comentario + ", visita=" + visita + "]";
	}
	
    
}

package com.iesFrancisco.captura.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "visita")
public class Visita implements Serializable{
	
    private static final long serialVersionUID = 1L;
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
    @Column(name = "header", length = 256)
    private String header;
    @Column(name = "fecha")
    private LocalDate fecha;
    @Column(name = "nota", length = 256)
    private String nota;
    @OneToMany(mappedBy = "visita", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Foto> fotos = new ArrayList<Foto>();
    @ManyToOne(fetch=FetchType.EAGER)
    private Obra obra;
    
	public Visita() {
		this(-1L,"Por defecto",LocalDate.now(),"Por defecto", new ArrayList<Foto>(),new Obra());
	}
	

	public Visita(Long id, String header, LocalDate fecha, String nota, List<Foto> fotos, Obra obra) {
		super();
		this.id = id;
		this.header = header;
		this.fecha = fecha;
		this.nota = nota;
		this.fotos = fotos;
		this.obra = obra;
	}



	public Visita(Long id, String header, LocalDate fecha, String nota, List<Foto> fotos) {
		this.id = id;
		this.header = header;
		this.fecha = fecha;
		this.nota = nota;
		this.fotos = fotos;
	}

	public Visita(Long id, String header, LocalDate fecha) {
		super();
		this.id = id;
		this.header = header;
		this.fecha = fecha;
		this.nota = "Por defecto";
		this.fotos = new ArrayList<Foto>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public List<Foto> getFotos() {
		return fotos;
	}

	public void setFotos(List<Foto> fotos) {
		this.fotos = fotos;
	}

	public Obra getObra() {
		return obra;
	}


	public void setObra(Obra obra) {
		this.obra = obra;
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
		Visita other = (Visita) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Visita [id=" + id + ", header=" + header + ", fecha=" + fecha + ", nota=" + nota + ", fotos=" + fotos
				+ "]";
	}  
    
}

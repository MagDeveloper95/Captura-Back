package com.iesFrancisco.captura.Model;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = "visita")
public class Visita {
    private static final long serialVersionUID = 1L;
    @Id
    protected Long id;
    @Column(name = "header")
    protected String header;
    @Column(name = "fecha")
    protected Date fecha;
    @Column(name = "nota")
    protected String nota;
    @Column(name = "idObra")
    protected Long idObra;

    public Visita() {
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Long getIdObra() {
        return idObra;
    }

    public void setIdObra(Long idObra) {
        this.idObra = idObra;
    }
}

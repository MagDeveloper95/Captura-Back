package com.iesFrancisco.captura.Model;

import org.springframework.web.multipart.MultipartFile;

public class FotoWrapper {

    private Long id;
    private String url;
    private String comentario;
    private Visita visita;
    private MultipartFile file;

    
    public FotoWrapper() {
		this(-1L,"None","None",new Visita(),null);
	}
    
	public FotoWrapper(Long id, String url, String comentario, Visita visita, MultipartFile file) {
		super();
		this.id = id;
		this.url = url;
		this.comentario = comentario;
		this.visita = visita;
		this.file = file;
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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

	@Override
	public String toString() {
		return "FotoWrapper [id=" + id + ", url=" + url + ", comentario=" + comentario + ", visita=" + visita
				+ ", file=" + file + "]";
	}
    
}
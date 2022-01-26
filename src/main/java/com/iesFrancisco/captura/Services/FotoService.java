package com.iesFrancisco.captura.Services;

import com.iesFrancisco.captura.Exception.RecordNotFoundException;
import com.iesFrancisco.captura.Model.Foto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iesFrancisco.captura.Repositories.FotoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FotoService {

    @Autowired // instanciar el repositorio
    FotoRepository repository;

    public List<Foto> getAllFoto() {
        List<Foto> result = repository.findAll();
        return result;
    }

    public Foto getFotoById(Long id) throws RecordNotFoundException {
        Optional<Foto> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new RecordNotFoundException("Foto no encontrada", id);
        }

    }

    public Foto createOrUpdateFoto(Foto foto) throws RecordNotFoundException {
        if (foto.getId() != null && foto.getId() > 0) {
            Optional<Foto> f = repository.findById(foto.getId());
            if (f.isPresent()) {
                Foto newFoto = f.get();
                newFoto.setId(foto.getId());
                newFoto.setUrl(foto.getUrl());
                newFoto.setComentario(foto.getComentario());
                newFoto.setVisita(foto.getVisita());
                newFoto = repository.save(newFoto);
                return newFoto;
            } else {
                foto.setId(null);
                foto = repository.save(foto);
                return foto;
            }
        } else {
            foto = repository.save(foto);
            return foto;
        }

    }

    public void deleteFotoById(Long id) throws RecordNotFoundException {
        Optional<Foto> foto = repository.findById(id);
        if (foto.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("La foto no encontrada", id);
        }
    }

}

package com.iesFrancisco.captura.Services;

import com.iesFrancisco.captura.Exception.RecordNotFoundException;
import com.iesFrancisco.captura.Model.Visita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iesFrancisco.captura.Repositories.VisitaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VisitaService {

    @Autowired // instanciar el repositorio
    VisitaRepository repository;

    public List<Visita> getAllVisita() {
        List<Visita> result = repository.findAll();
        return result;
    }

    public Visita getVisitaById(Long id) throws RecordNotFoundException {
        Optional<Visita> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new RecordNotFoundException("Visita no encontrada", id);
        }
    }

    public Visita createOrUpdateVisita(Visita visita) throws RecordNotFoundException {
        if (visita.getId() != null && visita.getId() > 0) {
            Optional<Visita> v = repository.findById(visita.getId());
            if (v.isPresent()) {
                Visita newVisita = v.get();
                newVisita.setId(visita.getId());
                newVisita.setHeader(visita.getHeader());
                newVisita.setFecha(visita.getFecha());
                newVisita.setNota(visita.getNota());
                newVisita.setFotos(visita.getFotos());
                newVisita = repository.save(newVisita);
                return newVisita;
            } else {
                visita.setId(null);
                visita = repository.save(visita);
                return visita;
            }
        } else {
            visita = repository.save(visita);
            return visita;
        }
    }

    public void deleteVisita(Long id) throws RecordNotFoundException {
        Optional<Visita> visita = repository.findById(id);
        if (visita.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Visita no encontrada", id);
        }
    }
}

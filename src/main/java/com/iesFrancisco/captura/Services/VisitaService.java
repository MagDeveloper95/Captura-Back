package com.iesFrancisco.captura.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iesFrancisco.captura.Exception.RecordNotFoundException;
import com.iesFrancisco.captura.Model.Visita;
import com.iesFrancisco.captura.Repositories.VisitaRepository;

@Service
public class VisitaService {

    @Autowired 
    VisitaRepository repository;
    
    /**
     * Metodo que devuelve una lista 
     * con todas las visitas almacenadas
     * @return
     */
    public List<Visita> getAllVisita() {
        List<Visita> result = repository.findAll();
        return result;
    }
    /**
     * Metodo que devuelve una visita ingresando su id
     * @param id identificador de la visita.
     * @return la visita que ha encontrado.
     * @throws RecordNotFoundException en caso de no encontrarla lanza la excepción 404
     */
    public Visita getVisitaById(Long id) throws RecordNotFoundException {
        Optional<Visita> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new RecordNotFoundException("Visita no encontrada", id);
        }
    }
    /**
     * Método que usamos para crear o editar una visita. 
     * @param visita que queremos crear o updatear.
     * @return la visita en caso de encontrarla y sino la crea y la devuelve
     * @throws RecordNotFoundException en caso de no encontrarla lanza la excepción 404
     */
    public Visita createOrUpdateVisita(Visita visita) throws RecordNotFoundException {
        if (visita.getId() != null && visita.getId() > 0) {
            Optional<Visita> visitaDummy = repository.findById(visita.getId());
            if (visitaDummy.isPresent()) {
                Visita newVisita = visitaDummy.get();
                newVisita.setId(visita.getId());
                newVisita.setHeader(visita.getHeader());
                newVisita.setFecha(visita.getFecha());
                newVisita.setNota(visita.getNota());
                newVisita.setFotos(visita.getFotos());
                newVisita = repository.save(newVisita);
                return newVisita;
            } else {
            	 throw new RecordNotFoundException("Visita no encontrada", visita.getId());
            }
        } else {
            visita = repository.save(visita);
            return visita;
        }
    }
    /**
     * Método que usamos para borrar una visita introducida por su id
     * @param id identificador de la visita.
     * @throws RecordNotFoundException en caso de no encontrarla lanza la excepción 404
     */
    public void deleteVisita(Long id) throws RecordNotFoundException {
        Optional<Visita> visita = repository.findById(id);
        if (visita.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Visita no encontrada", id);
        }
    }
}

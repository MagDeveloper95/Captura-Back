package com.iesFrancisco.captura.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iesFrancisco.captura.Exception.RecordNotFoundException;
import com.iesFrancisco.captura.Exception.RecordOK;
import com.iesFrancisco.captura.Exception.ResquestUnauthourized;
import com.iesFrancisco.captura.Model.Registro;
import com.iesFrancisco.captura.Repositories.RegistroRepository;

@Service
public class RegistroService {

	@Autowired // instanciar el repositorio
	RegistroRepository repository;

	public List<Registro> getAllRegistros() throws RecordNotFoundException, ResquestUnauthourized, RecordOK {

		List<Registro> result;
		// METER AQUI WATCHDOG
		result = repository.findAll();
		if (result != null) {
			return result;
		} else {
			throw new RecordNotFoundException("No hay registros en la base de datos");
		}

	}

	public Registro getRegistroById(Long id) throws RecordNotFoundException, ResquestUnauthourized, RecordOK {
		// METER AQUI WATCHDOG
		Optional<Registro> result = repository.findById(id);
		if (result.isPresent()) {
			return result.get();
		} else {
			throw new RecordNotFoundException("La nota no existe", id);
		}
	}

	public Registro creaRegistro(Registro registro) throws RecordNotFoundException, ResquestUnauthourized, RecordOK {
		Registro nuevoRegistro = null;
		// METER AQUI WATCHDOG
		if (registro.getId() != null && registro.getId() > 0) {
			Optional<Registro> dummy = repository.findById(registro.getId());
			if (dummy.isPresent()) {
				Registro returnRegistro = dummy.get();
				return returnRegistro;
			} else {
				try {
					nuevoRegistro = registro;
					nuevoRegistro.setId(registro.getId());
					nuevoRegistro.setDescripcion(registro.getDescripcion());
					nuevoRegistro.setFecha(registro.getFecha());
					nuevoRegistro.setUsuario(registro.getUsuario());
					nuevoRegistro = repository.save(nuevoRegistro);
					return nuevoRegistro;
				} catch (Exception e) {
				}

				throw new RecordNotFoundException("El registro no existe");
			}
		}
		// METER AQUI WATCHDOG
		return nuevoRegistro;
	}

	public List<Registro> getRegistroPorFecha(LocalDate fecha)
			throws RecordNotFoundException, ResquestUnauthourized, RecordOK {
		List<Registro> returnReg = null;
		if (!fecha.isBefore(LocalDate.now()) && fecha != null) {
			returnReg = repository.getRegistroPorFecha(fecha); // Prueba
			if (returnReg != null) {
				return returnReg;
			} else {
				throw new RecordNotFoundException("El registro no existe");
			}
		}
		return returnReg;
	}

	List<Registro> getRegistroPorUsuario(Long usuario) throws RecordNotFoundException, ResquestUnauthourized, RecordOK {
		List<Registro> returnReg = null;
		if (!usuario.equals(-1L)) {
			returnReg = repository.getRegistroPorUsuario(usuario); // Prueba
			if (returnReg != null) {
				return returnReg;
			} else {
				throw new RecordNotFoundException("El registro no existe");
			}
		}
		return returnReg;
	}
}

package co.edu.uniandes.dse.parcialprueba.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EspecialidadService {
    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Transactional
    public EspecialidadEntity createEspecialidad(EspecialidadEntity especialidad) throws IllegalOperationException {
        log.info("inicia el proceso de crear especialidad");
        if (especialidad.getDescripcion().length() < 10) {
            throw new IllegalOperationException("descripción debe tener mas de 10 caracteres");
        }
        return especialidadRepository.save(especialidad);
    }

}

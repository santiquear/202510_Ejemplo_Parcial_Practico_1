package co.edu.uniandes.dse.parcialprueba.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadRepository;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MedicoEspecialidadService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Transactional
    public EspecialidadEntity addEspecialidad(Long especialidadId, Long medicoId) throws EntityNotFoundException {
        log.info("inicia esa monda");

        Optional<EspecialidadEntity> especialidadentity = especialidadRepository.findById(especialidadId);
        if (especialidadentity.isEmpty()) {
            throw new EntityNotFoundException("Especilidad no encontrada");
        }
        Optional<MedicoEntity> medicoentity = medicoRepository.findById(medicoId);
        if (medicoentity.isEmpty()) {
            throw new EntityNotFoundException("Medico no encontrado");
        }

        MedicoEntity medico = medicoentity.get();
        EspecialidadEntity especialidad = especialidadentity.get();

        medico.getEspecialidad().add(especialidad);
        medicoRepository.save(medico);

        return especialidad;
    }

}

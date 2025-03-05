package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(MedicoService.class)
class MedicoServiceTest {

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<MedicoEntity> medicoList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("DELETE FROM MedicoEntity").executeUpdate();
    }

    private void insertData() {

        for (int i = 0; i < 3; i++) {
            MedicoEntity medicoEntity = factory.manufacturePojo(MedicoEntity.class);
            medicoEntity.setRegistro("RM" + i);
            entityManager.persist(medicoEntity);
            medicoList.add(medicoEntity);
        }

        EspecialidadEntity especialidadEntity = factory.manufacturePojo(EspecialidadEntity.class);
        entityManager.persist(especialidadEntity);
        especialidadEntity.getMedicos().add(medicoList.get(0));
        medicoList.get(0).getEspecialidad().add(especialidadEntity);
    }

    @Test
    void testcreatemedicos() throws IllegalOperationException {
        MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
        newEntity.setRegistro("RM01");
        MedicoEntity result = medicoService.createMedico(newEntity);
        assertNotNull(result);
        assertNotNull(entityManager.find(MedicoEntity.class, result.getId()));
    }

    @Test
    void testcreatemedicoinvalidrm() throws IllegalOperationException {
        assertThrows(IllegalOperationException.class, () -> {
            MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
            newEntity.setRegistro("AC22");
            medicoService.createMedico(newEntity);
        });
    }

}

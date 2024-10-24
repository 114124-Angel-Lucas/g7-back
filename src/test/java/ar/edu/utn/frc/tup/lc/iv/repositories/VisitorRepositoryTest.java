package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.models.DocumentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class VisitorRepositoryTest {

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private TestEntityManager testEntityManager;


    @Test
    void findByDocNumber() {
        VisitorEntity visitorEntity =
                new VisitorEntity(1L, "Joaquin", "Perez", DocumentType.DNI ,46222977L, LocalDate.now(), true);

        // se usa merge en lugar de persist si la entidad ya puede existir en la base de datos
        testEntityManager.merge(visitorEntity);
        testEntityManager.flush();

        VisitorEntity visitorEntityResult = visitorRepository.findByDocNumber(46222977L);
        assertEquals(visitorEntityResult.getName(), "Joaquin");
    }
}
package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.AccessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing visit access records from the database.
 */
@Repository
public interface VisitAccessRepository extends JpaRepository<AccessEntity, Long> {

}

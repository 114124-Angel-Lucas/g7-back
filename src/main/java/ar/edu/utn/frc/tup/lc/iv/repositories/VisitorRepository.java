package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository of Authorized.
 */
@Repository
public interface VisitorRepository extends JpaRepository<VisitorEntity, Long> {
    /**
     * Finds a visitor entity by document number.
     * @param docNumber the document number of the authorized person.
     * @return the visitor entity with the specified document number.
     */
    VisitorEntity findByDocNumber(Long docNumber);

    /**
     * fetch visitor by docNumber.
     *
     * @param isActive if the visitor is active.
     * @param pageable pageable.
     * @return VisitorEntity list.
     */
/*    @Query("SELECT v FROM VisitorEntity v WHERE v.isActive = :isActive")
    Page<VisitorEntity> findAllByActive(@Param("isActive") boolean isActive, Pageable pageable);*/


    List<VisitorEntity> findByActive(boolean isActive);
}

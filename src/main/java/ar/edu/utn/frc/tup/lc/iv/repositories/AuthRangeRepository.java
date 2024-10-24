package ar.edu.utn.frc.tup.lc.iv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthRangeEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * repository for authorized ranges.
 */
@Repository
public interface AuthRangeRepository extends JpaRepository<AuthRangeEntity, Long> {

    // find by auth id

    /**
     * find by auth id.
     * @param authId auth entity
     * @return auth range list
     */
    List<AuthRangeEntity> findByAuthId(AuthEntity authId);
    List<AuthRangeEntity> findByAuthId_ExternalID(Long externalID);

}

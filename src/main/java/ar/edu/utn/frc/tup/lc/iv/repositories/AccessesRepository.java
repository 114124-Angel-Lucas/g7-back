package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.AccessEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.models.ActionTypes;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing visit access records from the database.
 */
@Repository
public interface AccessesRepository extends JpaRepository<AccessEntity, Long> {

    List<AccessEntity> findByAuth(AuthEntity authEntity);
    List<AccessEntity> findByAction(ActionTypes actionType);
    List<AccessEntity> findByAuth_Visitor_docNumber(Long docNumber);
    List<AccessEntity> searchByAuth_Visitor_NameOrAuth_Visitor_LastName(String name, String lastName);
    List<AccessEntity> findByAuth_VisitorType(VisitorType visitorType);
    List<AccessEntity> findByAuth_VisitorTypeAndAuth_ExternalID(VisitorType visitorType, Long externalID);
}


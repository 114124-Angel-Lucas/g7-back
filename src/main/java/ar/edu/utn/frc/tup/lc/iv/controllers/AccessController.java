package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.models.ActionTypes;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import ar.edu.utn.frc.tup.lc.iv.services.IAccessesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing authorized persons.
 */
@RestController
@RequestMapping("/access")
public class AccessController {

    @Autowired
    private IAccessesService accessesService;


    @GetMapping
    public List<AccessDTO> getAllAccess(@RequestParam(required = false)VisitorType visitorType,
                                        @RequestParam(required = false) Long externalID) {
        if (visitorType != null){
            if(externalID != null){
                return accessesService.getAllAccessByTypeAndExternalID(visitorType,externalID);
            }

            return accessesService.getAllAccessByType(visitorType);
        }

        return accessesService.getAllAccess();

    }
    @GetMapping("/entries")
    public List<AccessDTO> getAllEntries() {
        return accessesService.getAllEntries();
    }
    @GetMapping("/exits")
    public List<AccessDTO> getAllExits() {
        return accessesService.getAllExits();
    }
    @GetMapping("/check-access")
    public Boolean checkAccess(@RequestParam String carPlate, @RequestParam ActionTypes action) {
        return accessesService.canDoAction(carPlate, action);
    }
}

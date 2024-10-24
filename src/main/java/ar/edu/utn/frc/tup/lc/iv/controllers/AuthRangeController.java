package ar.edu.utn.frc.tup.lc.iv.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.tup.lc.iv.services.imp.AuthRangeService;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeDTO;

import java.util.List;

/**
 * Controller class for managing authorized ranges.
 */
@RestController
@RequestMapping("/auth-ranges")
public class AuthRangeController {

    /**
     * Service for managing authorized ranges.
     */
    @Autowired
    private AuthRangeService authRangeService;

    /**
     * Get all authorized ranges.
     *
     * @return List<AuthRangeDTO>
     */
    @GetMapping
    public List<AuthRangeDTO> getAuthRanges() {
        return authRangeService.getAllAuthRanges();
    }

}

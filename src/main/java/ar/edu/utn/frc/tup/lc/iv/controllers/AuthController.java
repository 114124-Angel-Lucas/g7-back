package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;
import ar.edu.utn.frc.tup.lc.iv.services.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthDTO;

import java.util.List;

/**
 * Controller class for managing authorized persons.
 */
@RestController
@RequestMapping("/auths")
public class AuthController {

    /**
     * Service for managing authorized persons.
     */
    @Autowired
    private IAuthService authService;

    /**
     * Retrieve authorizations for authorized persons by document number.
     *
     * @param docNumber document number.
     * @return list of authorizations.
     */
    @GetMapping
    public List<AuthDTO> getAuth(@RequestParam(required = false) Long docNumber, @RequestParam(required = false) Long id) {
        if (docNumber != null) {
            return authService.getAuthsByDocNumber(docNumber);
        }
        if (id != null) {
            return authService.getAuthsById(id);
        }
        return authService.getAllAuths();
    }

    /**
     * retrieve valid authorizations for authorized persons
     * by document number.
     *
     * @param docNumber number of document.
     * @return list of valid authorizations.
     */
    @GetMapping("/valids/{docNumber}")
    public List<AuthDTO> getValidAuths(@PathVariable Long docNumber) {
        return authService.getValidAuthsByDocNumber(docNumber);
    }

    /**
     * Authorize visitor with authorized ranges.
     *
     * @param visitorAuthRequest request.
     * @return authorization created.
     */
    @PostMapping("/authorize")
    public ResponseEntity<AccessDTO> authorizeVisitor(@RequestBody AccessDTO accessDTO, @RequestHeader("x-user-id") Long userId) {
        AccessDTO access = authService.authorizeVisitor(accessDTO,userId);
        return access == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(access);
    }

    /**
     * Authorize visitor with authorized ranges.
     *
     * @param visitorAuthRequest request.
     * @return authorization created.
     */
    @PostMapping("/authorization")
    public ResponseEntity<AuthDTO> createAuthorization(@RequestBody VisitorAuthRequest visitorAuthRequest, @RequestHeader("x-user-id") Long userId) {
        return ResponseEntity.ok(authService.createAuthorization(visitorAuthRequest, userId));
    }

    @PutMapping("/authorization")
    public ResponseEntity<AuthDTO> updateAuthorization(@RequestBody VisitorAuthRequest visitorAuthRequest, @RequestHeader("x-user-id") Long userId) {
        return ResponseEntity.ok(authService.updateAuthorization(visitorAuthRequest, userId));
    }

    @DeleteMapping("/authorization")
    public ResponseEntity<AuthDTO> updateAuthorization(@RequestHeader("auth-id") Long authId, @RequestHeader("x-user-id") Long userId) {
        return ResponseEntity.ok(authService.deleteAuthorization(authId, userId));
    }

    @PutMapping("/authorization/activate")
    public ResponseEntity<AuthDTO> activateAuthorization(@RequestHeader("auth-id") Long authId, @RequestHeader("x-user-id") Long userId) {
        return ResponseEntity.ok(authService.activateAuthorization(authId, userId));
    }

    /**
     * check if the visitor have access.
     *
     * @param docNumber request.
     * @return authorization created.
     */
    @GetMapping("/authorization/{docNumber}")
    public ResponseEntity<Boolean> haveAutorization(@PathVariable Long docNumber) {
        return ResponseEntity.ok(authService.isAuthorized(docNumber));
    }

}

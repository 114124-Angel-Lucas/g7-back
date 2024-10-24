package ar.edu.utn.frc.tup.lc.iv.clients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class to model the type of object expected to be provided by another service.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    /**
     * id of the user.
     */
    private Long id;

    /**
     * name of user.
     */
    private String userName;

}

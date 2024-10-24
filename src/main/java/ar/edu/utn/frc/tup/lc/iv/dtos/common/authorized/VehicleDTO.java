package ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized;

import ar.edu.utn.frc.tup.lc.iv.models.VehicleTypes;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehicleDTO {

    private VehicleTypes vehicleType;

    private String vehicleReg;

    private String vehicleDescription;


}

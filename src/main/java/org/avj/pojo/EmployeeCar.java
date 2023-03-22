package org.avj.pojo;

import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor()
public class EmployeeCar {

    private int carId;
    @NonNull
    private String regNum;
    @NonNull
    private String brandModel;
    @NonNull
    private int employeeId;
}

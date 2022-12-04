package com.semika.aws.employee.model;

import com.semika.aws.common.model.BaseDto;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDto extends BaseDto {
    private String id;
    private String first;
    private String phone;
    private String startDate;
    private String email;
}

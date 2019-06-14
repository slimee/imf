package com.eigaas.imf.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class SpyMissionsDTO {
    private String codename;
    private LocalDate lastModifiedDate;
}

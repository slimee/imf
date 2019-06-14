package com.eigaas.imf.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionForm {
    private String missionCodeName;
    private String spyCodeName;
}

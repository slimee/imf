package com.eigaas.imf.web;

public class MissionNotFoundException extends RuntimeException {
    public MissionNotFoundException() {
    }

    public MissionNotFoundException(Long missionId ) {
        super("Mission: " +missionId +" not found.");
    }
}

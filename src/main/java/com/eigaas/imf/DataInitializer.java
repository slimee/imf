package com.eigaas.imf;

import com.eigaas.imf.domain.Mission;
import com.eigaas.imf.domain.Spy;
import com.eigaas.imf.repository.SpyRepository;
import com.eigaas.imf.repository.MissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Autowired
    MissionRepository missions;

    @Autowired
    SpyRepository spies;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.debug("initializing spies data...");
        final Spy slim = this.spies.save(getSpy("slim", "password", Arrays.asList("ROLE_USER")));
        this.spies.save(getSpy("cédric", "password", Arrays.asList("ROLE_USER", "ROLE_ADMIN")));

        log.debug("initializing missions data...");
        this.missions.saveAndFlush(Mission.builder().codename("write IMF").spy(slim).build());
        this.missions.saveAndFlush(Mission.builder().codename("kill DT").spy(slim).build());

        log.debug("printing all spies...");
        this.spies.findAll().forEach(v -> log.debug(" Spy :" + v.toString()));
        log.debug("printing all missions...");
        this.missions.findAll().forEach(v -> log.debug(" Mission :" + v.toString()));

    }

    private Spy getSpy(final String slim, final String password, final List<String> role_user) {
        return Spy.builder()
                .username(slim)
                .password(this.passwordEncoder.encode(password))
                .roles(role_user)
                .build();
    }
}

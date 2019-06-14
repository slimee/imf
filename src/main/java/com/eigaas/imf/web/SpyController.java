package com.eigaas.imf.web;

import com.eigaas.imf.domain.Spy;
import com.eigaas.imf.exception.MissionNotFoundException;
import com.eigaas.imf.repository.SpyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;

import static org.springframework.http.ResponseEntity.*;

@RestController()
@RequestMapping("/v1/spies")
public class SpyController {

    @Autowired
    PasswordEncoder passwordEncoder;

    private SpyRepository spies;

    public SpyController(SpyRepository spies) {
        this.spies = spies;
    }

    @GetMapping("")
    public ResponseEntity all() {
        return ok(this.spies.findAll());
    }

    @PostMapping("")
    public ResponseEntity save(@RequestBody SpyForm form, HttpServletRequest request) {
        final Spy saved = this.spies.save(Spy.builder()
                .username(form.getCodename())
                .password(this.passwordEncoder.encode(form.getPassword()))
                .roles(Arrays.asList("ROLE_USER"))
                .build()
        );
        return created(
                ServletUriComponentsBuilder
                        .fromContextPath(request)
                        .path("/v1/spies/{id}")
                        .buildAndExpand(saved.getId())
                        .toUri())
                .build();
    }

    @DeleteMapping("/{codename}")
    public ResponseEntity delete(@PathVariable("codename") String codename) {
        Spy existed = this.spies.findByUsername(codename).orElseThrow(MissionNotFoundException::new);
        this.spies.delete(existed);
        return noContent().build();
    }

}

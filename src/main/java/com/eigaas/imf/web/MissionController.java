package com.eigaas.imf.web;

import com.eigaas.imf.domain.Mission;
import com.eigaas.imf.domain.Spy;
import com.eigaas.imf.dto.SpyMissionsDTO;
import com.eigaas.imf.exception.SpyNotFoundException;
import com.eigaas.imf.repository.MissionRepository;
import com.eigaas.imf.repository.SpyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/missions")
public class MissionController {

    private ModelMapper modelMapper;

    private MissionRepository missions;

    private SpyRepository spies;

    public MissionController(ModelMapper modelMapper, MissionRepository missions, SpyRepository spies) {
        this.missions = missions;
        this.spies = spies;
        this.modelMapper = modelMapper;
    }


    @GetMapping("")
    public ResponseEntity all(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails instanceof Spy) {
            return ok(
                    this.missions
                            .findAllBySpyId(((Spy) userDetails).getId())
                            .stream()
                            .map(this::toSpyMissionsDTO)
                            .collect(Collectors.toList())
            );
        } else {
            return null;
        }
    }

    private SpyMissionsDTO toSpyMissionsDTO(final Mission mission) {
        return modelMapper.map(mission, SpyMissionsDTO.class);
    }

    @PostMapping("")
    public ResponseEntity save(@RequestBody MissionForm form, HttpServletRequest request) {
        final Optional<Spy> spy = this.spies.findByUsername(form.getSpyCodeName());
        if (spy.isPresent()) {
            final Mission saved = this.missions.save(Mission.builder()
                    .codename(form.getMissionCodeName())
                    .spy(spy.get())
                    .build()
            );
            return created(
                    ServletUriComponentsBuilder
                            .fromContextPath(request)
                            .path("/v1/missions/{id}")
                            .buildAndExpand(saved.getId())
                            .toUri())
                    .build();
        } else {
            throw new SpyNotFoundException(form.getSpyCodeName());
        }
    }
}

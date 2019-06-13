package com.eigaas.imf.web;

import com.eigaas.imf.domain.Mission;
import com.eigaas.imf.domain.Spy;
import com.eigaas.imf.repository.MissionRepository;
import com.eigaas.imf.repository.SpyRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/missions")
public class MissionController {

    private MissionRepository missions;

    private SpyRepository spies;

    public MissionController(MissionRepository missions, SpyRepository spies) {
        this.missions = missions;
        this.spies = spies;
    }


    @GetMapping("")
    public ResponseEntity all(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails instanceof Spy) {
            return ok(this.missions.findAllBySpy((Spy)userDetails));
        } else {
            return null;
        }
    }

    @PostMapping("")
    public ResponseEntity save(@RequestBody MissionForm form, HttpServletRequest request) {
        final Mission saved = this.missions.save(Mission.builder()
                .codename(form.getName())
                .spy(this.spies.getOne(form.getSpyId()))
                .build()
        );
        return created(
                ServletUriComponentsBuilder
                        .fromContextPath(request)
                        .path("/v1/missions/{id}")
                        .buildAndExpand(saved.getId())
                        .toUri())
                .build();
    }

//    @GetMapping("/{id}")
//    public ResponseEntity get(@PathVariable("id") Long id) {
//        return ok(this.missions.findById(id).orElseThrow(MissionNotFoundException::new));
//    }
//
//
//    @PutMapping("/{id}")
//    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody MissionForm form) {
//        Mission existed = this.missions.findById(id).orElseThrow(MissionNotFoundException::new);
//        existed.setCodename(form.getName());
//
//        this.missions.save(existed);
//        return noContent().build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity delete(@PathVariable("id") Long id) {
//        Mission existed = this.missions.findById(id).orElseThrow(MissionNotFoundException::new);
//        this.missions.delete(existed);
//        return noContent().build();
//    }
}

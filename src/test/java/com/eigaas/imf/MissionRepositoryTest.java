package com.eigaas.imf;

import com.eigaas.imf.domain.Mission;
import com.eigaas.imf.repository.MissionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MissionRepositoryTest {

    @Autowired
    private MissionRepository missions;

    @Test
    public void mapping() {
        Mission saved = this.missions.save( Mission.builder().codename("test").build());
        Mission v = this.missions.getOne(saved.getId());
        assertThat(v.getCodename()).isEqualTo("test");
        assertThat(v.getId()).isNotNull();
        assertThat(v.getId()).isGreaterThan(0);
    }
}

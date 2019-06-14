package com.eigaas.imf;

import com.eigaas.imf.domain.Mission;
import com.eigaas.imf.domain.Spy;
import com.eigaas.imf.repository.MissionRepository;
import com.eigaas.imf.repository.SpyRepository;
import com.eigaas.imf.web.MissionController;
import com.eigaas.imf.web.MissionForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MissionController.class, secure = false)
@RunWith(SpringRunner.class)
public class MissionControllerTest {

    @MockBean
    MissionRepository missions;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ModelMapper modelmapper;

    @MockBean
    SpyRepository spyRepository;

    @Autowired
    MockMvc mockMvc;

    @Before
    public void setUp() {
        given(this.missions.save(any(Mission.class)))
            .willReturn(Mission.builder().codename("test").build());

        given(this.spyRepository.findByUsername("slim"))
                .willReturn(ofNullable(Spy.builder().username("slim").build()));
    }

    @Test
    public void testSave() throws Exception {
        this.mockMvc
            .perform(
                post("/v1/missions")
                    .content(this.objectMapper.writeValueAsBytes(MissionForm.builder().missionCodeName("test").spyCodeName("slim").build()))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated());

        verify(this.missions, times(1)).save(any(Mission.class));
        verifyNoMoreInteractions(this.missions);
    }

}

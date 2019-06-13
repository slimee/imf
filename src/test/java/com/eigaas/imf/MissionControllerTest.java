package com.eigaas.imf;

import com.eigaas.imf.domain.Mission;
import com.eigaas.imf.repository.MissionRepository;
import com.eigaas.imf.web.MissionController;
import com.eigaas.imf.web.MissionForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MissionController.class, secure = false)
@RunWith(SpringRunner.class)
public class MissionControllerTest {

    @MockBean
    MissionRepository missions;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Before
    public void setUp() {
        given(this.missions.findById(1L))
            .willReturn(Optional.of(Mission.builder().codename("test").build()));

        given(this.missions.findById(2L))
            .willReturn(Optional.empty());

        given(this.missions.save(any(Mission.class)))
            .willReturn(Mission.builder().codename("test").build());

        doNothing().when(this.missions).delete(any(Mission.class));
    }

    @Test
    public void testGetById() throws Exception {

        this.mockMvc
            .perform(
                get("/v1/missions/{id}", 1L)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("test"));

        verify(this.missions, times(1)).findById(any(Long.class));
        verifyNoMoreInteractions(this.missions);
    }

    @Test
    public void testGetByIdNotFound() throws Exception {

        this.mockMvc
            .perform(
                get("/v1/missions/{id}", 2L)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNotFound());

        verify(this.missions, times(1)).findById(any(Long.class));
        verifyNoMoreInteractions(this.missions);
    }

    @Test
    public void testSave() throws Exception {

        this.mockMvc
            .perform(
                post("/v1/missions")
                    .content(this.objectMapper.writeValueAsBytes(MissionForm.builder().name("test").build()))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated());

        verify(this.missions, times(1)).save(any(Mission.class));
        verifyNoMoreInteractions(this.missions);
    }

    @Test
    public void testUpdate() throws Exception {

        this.mockMvc
            .perform(
                put("/v1/missions/1")
                    .content(this.objectMapper.writeValueAsBytes(MissionForm.builder().name("test").build()))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());

        verify(this.missions, times(1)).findById(any(Long.class));
        verify(this.missions, times(1)).save(any(Mission.class));
        verifyNoMoreInteractions(this.missions);
    }

    @Test
    public void testDelete() throws Exception {

        this.mockMvc
            .perform(
                delete("/v1/missions/1")
            )
            .andExpect(status().isNoContent());

        verify(this.missions, times(1)).findById(any(Long.class));
        verify(this.missions, times(1)).delete(any(Mission.class));
        verifyNoMoreInteractions(this.missions);
    }

}

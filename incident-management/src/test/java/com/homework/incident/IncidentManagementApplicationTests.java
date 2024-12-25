package com.homework.incident;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.incident.controller.IncidentInfoController;
import com.homework.incident.entity.Incident;
import com.homework.incident.service.IncidentInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(IncidentInfoController.class)
class IncidentManagementApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IncidentInfoService incidentInfoService;

    @InjectMocks
    private IncidentInfoController incidentInfoController;

    @Autowired
    private ObjectMapper objectMapper;

    private Incident incident;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        incident = new Incident();
        incident.setId(1l);
        incident.setIncidentName("测试用例");
        incident.setIncidentType("测试");
    }

    @Test
    public void testCreateIncident() throws Exception {
        Mockito.when(incidentInfoService.addIncident(any(Incident.class))).thenReturn(incident);

        mockMvc.perform(post("/api/incident-info")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(incident)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.incidentType").value("测试"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.incidentName").value("测试用例"));
    }

    @Test
    public void testGetAllIncidents() throws Exception {
        Mockito.when(incidentInfoService.getAllIncidents()).thenReturn(Collections.singletonList(incident));

        mockMvc.perform(get("/api/incident-info"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].incidentName").value("测试用例"));
    }

    @Test
    public void testGetIncidentById() throws Exception {
        Mockito.when(incidentInfoService.getIncidentById(anyLong())).thenReturn(Optional.of(incident));

        mockMvc.perform(get("/api/incident-info/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.incidentName").value("测试用例"));
    }

    @Test
    public void testUpdateIncident() throws Exception {
        Mockito.when(incidentInfoService.updateIncident(any(Incident.class))).thenReturn(incident);

        incident.setIncidentName("更新测试");

        mockMvc.perform(put("/api/incident-info/{id}", 1l)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(incident)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.incidentName").value("更新测试"));
    }

    @Test
    public void testDeleteIncident() throws Exception {
        Mockito.when(incidentInfoService.getIncidentById(anyLong())).thenReturn(Optional.of(incident));

        mockMvc.perform(delete("/api/incident-info/{id}", 1l))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"));
    }

    @Test
    public void testGetPageIncidents() throws Exception {
        Page<Incident> page = new PageImpl<>(Collections.singletonList(incident));
        Mockito.when(incidentInfoService.getPageIncidents(any(), any())).thenReturn(page);

        mockMvc.perform(get("/api/incident-info/getPageIncidents")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].incidentName").value("测试用例"));
    }
}

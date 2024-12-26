package com.homework.incident.controller;
import com.homework.incident.common.ResponseCode;
import com.homework.incident.common.ResponseWrapper;
import com.homework.incident.entity.Incident;
import com.homework.incident.exception.BusinessException;
import com.homework.incident.service.IncidentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 *
 * 事件管理信息表 前端控制器
 * @author maoxuan
 * @since 2024-12-24
 */

@RestController
@RequestMapping("/api/incident-info")
@Validated
public class IncidentInfoController {
    @Autowired
    private IncidentInfoService incidentInfoService;

    @PostMapping
    public ResponseWrapper<Incident> createIncident(@Valid @RequestBody Incident incident) {
        Incident incidentInfo = incidentInfoService.addIncident(incident);
        if(incidentInfo == null ){
            return  new ResponseWrapper.Builder<Incident>().code(ResponseCode.FORBIDDEN.getCode()).message(ResponseCode.FORBIDDEN.getMessage()).build();
        }
        return new ResponseWrapper.Builder<Incident>().code(ResponseCode.SUCCESS.getCode()).message(ResponseCode.SUCCESS.getMessage()).data(incidentInfo).build();
    }

    @GetMapping
    public ResponseWrapper<List<Incident>> getAllIncidents() {
        List<Incident> list =  incidentInfoService.getAllIncidents();
        return new ResponseWrapper.Builder<List<Incident>>().code(ResponseCode.SUCCESS.getCode()).message(ResponseCode.SUCCESS.getMessage()).data(list).build();
    }

    @GetMapping("/{id}")
    public ResponseWrapper<Incident> getIncidentById(@PathVariable Long id) {
        Optional<Incident> incident = incidentInfoService.getIncidentById(id);
        return new ResponseWrapper.Builder<Incident>().code(ResponseCode.SUCCESS.getCode()).message(ResponseCode.SUCCESS.getMessage()).data(incident.get()).build();

    }

    @PutMapping("/{id}")
    public ResponseWrapper<Incident> updateIncident(@PathVariable Long id, @RequestBody Incident incident) {
        incident.setId(id);
        Incident incident1 = incidentInfoService.updateIncident(incident);
        return new ResponseWrapper.Builder<Incident>().code(ResponseCode.SUCCESS.getCode()).message(ResponseCode.SUCCESS.getMessage()).data(incident1).build();

    }

    @DeleteMapping("/{id}")
    public ResponseWrapper<String> deleteIncident(@PathVariable Long id) {
        Optional<Incident> incident = incidentInfoService.getIncidentById(id);
        if(!incident.isPresent()){
            throw new BusinessException(ResponseCode.NOTEXIST.getCode(),ResponseCode.NOTEXIST.getMessage());
        }
        incidentInfoService.deleteIncident(id);
        return new ResponseWrapper.Builder<String>().code(ResponseCode.SUCCESS.getCode()).message(ResponseCode.SUCCESS.getMessage()).build();
    }

    @GetMapping("/getPageIncidents")
    public ResponseWrapper<Page<Incident>> getPageIncidents(@RequestParam(value = "incidentName", required = false) String incidentName,
                                                                    @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                    @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        Page<Incident>  pageInfo = incidentInfoService.getPageIncidents(incidentName, PageRequest.of(page, size, Sort.by("createTime").descending()));
        return new ResponseWrapper.Builder<Page<Incident>>().code(ResponseCode.SUCCESS.getCode()).message(ResponseCode.SUCCESS.getMessage()).data(pageInfo).build();
    }
}

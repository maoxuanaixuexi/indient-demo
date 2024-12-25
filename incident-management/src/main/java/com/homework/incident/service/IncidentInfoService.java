package com.homework.incident.service;

import com.homework.incident.entity.Incident;
import com.homework.incident.repository.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @auther maoxuan
 * @create 2024-12-24
 * @describe 事件管理信息表服务类
 */

public interface IncidentInfoService  {


    Incident addIncident(Incident user);

    List<Incident> getAllIncidents();

    Optional<Incident> getIncidentById(Long id);

    Incident updateIncident(Incident user);

    void deleteIncident(Long id);

    Page<Incident> getPageIncidents(String incidentName, Pageable pageable);
}

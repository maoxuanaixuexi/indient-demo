package com.homework.incident.service.impl;

import com.google.common.hash.BloomFilter;
import com.homework.incident.common.ResponseCode;
import com.homework.incident.entity.Incident;
import com.homework.incident.exception.BusinessException;
import com.homework.incident.repository.IncidentRepository;
import com.homework.incident.service.IncidentInfoService;
import com.homework.incident.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class IncidentServiceImpl implements IncidentInfoService {
    @Autowired
    IncidentRepository incidentRepository;
    @Autowired
    private RedisTemplate<String, Incident> redisTemplate;

    @Autowired
    private BloomFilter bloomFilter;
    @Override
    public Incident addIncident(Incident incident) {
        Incident in = incidentRepository.save(incident);
        bloomFilter.put(in.getId().toString());
        return in;
    }
    @Override
    public List<Incident> getAllIncidents() {
        List<Incident> cacheList = redisTemplate.opsForList().range(Consts.CACHE_VALUE, 0, -1);
        if(CollectionUtils.isEmpty(cacheList)){
            cacheList = incidentRepository.findAll();
            if(!CollectionUtils.isEmpty(cacheList)){
                redisTemplate.opsForList().rightPushAll(Consts.CACHE_VALUE, cacheList);
                redisTemplate.expire(Consts.CACHE_VALUE, 100, java.util.concurrent.TimeUnit.SECONDS);
            }
        }
        return cacheList;
    }
    @Override
    public Optional<Incident> getIncidentById(Long id) {
        if(!bloomFilter.mightContain(id.toString())){
            throw new BusinessException(ResponseCode.NOTEXIST.getCode(), ResponseCode.NOTEXIST.getMessage());
        }
        Incident incident = redisTemplate.opsForValue().get(Consts.CACHE_VALUE + id);
        if(incident == null){
            Optional<Incident> info = incidentRepository.findById(id);
            redisTemplate.opsForValue().set(Consts.CACHE_VALUE + id,info.get(),100, TimeUnit.SECONDS);
            return info;
        }
        return Optional.of(incident);
    }
    @Override
    public Incident updateIncident(Incident user) {
        redisTemplate.delete(Consts.CACHE_VALUE + user.getId());
        redisTemplate.delete(Consts.CACHE_VALUE);
        return incidentRepository.save(user);  // 如果 id 存在则更新
    }
    @Override
    public void deleteIncident(Long id) {
        redisTemplate.delete(Consts.CACHE_VALUE + id);
        redisTemplate.delete(Consts.CACHE_VALUE);
        incidentRepository.deleteById(id);
    }

    @Override
     public Page<Incident> getPageIncidents(String incidentName, Pageable pageable) {
        if (!StringUtils.hasLength(incidentName)) {
            return incidentRepository.findAll(pageable);
        }
        return incidentRepository.findByIncidentNameContainingIgnoreCase(incidentName, pageable);
    }
}

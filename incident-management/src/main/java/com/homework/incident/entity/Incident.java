package com.homework.incident.entity;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;


/**
 * @Package: com.homework.incident.domain
 * @Author maoxuan
 * @CreateDate 2024-12-24
 * @describe 事件管理信息表实体类
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Incident implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    /**
     * 事件类型
     */
    @NotNull(message = "incidentType cannot be null")  // 事件类型不能为空
    @Column(name = "incident_type", nullable = false, length = 10)
    private String incidentType;
    /**
     * 事件名称
     */
    @NotNull(message = "incidentName cannot be null")  // 事件名称不能为空
    @Size(max = 50, message = "incidentName must be less then 50 characters")
    @Column(name = "incident_name", nullable = false, length = 50)
    private String incidentName;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date beginTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    /**
     * 事件描述
     */
    private String incidentDesc;
    /**
     * 创建事件
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 更新事件
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    /**
     * 事件状态
     */
    private String status;

    public void setId(Long id) {
        this.id = id;
    }

    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    public void setIncidentName(String incidentName) {
        this.incidentName = incidentName;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setIncidentDesc(String incidentDesc) {
        this.incidentDesc = incidentDesc;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public String getIncidentType() {
        return incidentType;
    }

    public String getIncidentName() {
        return incidentName;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getIncidentDesc() {
        return incidentDesc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public String getStatus() {
        return status;
    }
}
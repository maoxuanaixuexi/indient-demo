# indient-demo
事件管理DEMO

***

## 系统介绍

一个与事件管理相关的简单前后端分离的应用。包含事件的增删改查功能。


***

## 设计方案


### **技术栈**:

* **后端**: Java 17, Spring Boot，Spring Data JPA,redis

* **前端**: Vue2+Elememnt-ui

* **构建工具**: Maven

* **容器化**: Docker, Docker-compose (待部署，只准备了配置文件，待验证)
***

### **前端功能**

*   事件查询分页显示

*   事件添加

*   事件编辑

*   事件删除

    ***

### **数据设计**：

数据库：使用轻量级H2 数据库，可以在内存模式下运行

主要实体: Incident

字段 :

*   `id`: 唯一标识符
*   `incidentType`: 事件类型
*   `incidentName`: 事件名称
*   `incidentDesc`: 事件状态
*   `status`: 事件状态
*   `createTime`: 创建时间
*   `updateTime`: 修改时间
*   `beginTime`: 事件开始时间
*   `endTime`: 事件结束时间
    
    ***

### **API 设计**：

#### **1.接口设计**:

*   GET /api/incident-info/getPageIncidents

    事件分页列表：

    *   支持参数: 分页（`page` 和 `size`）、模糊查询（`incidentName`）
    *   返回: 事件分页列表
*   GET /api/incident-info/{id}

    事件详情：

    *   支持参数: 事件 ID
    *   返回: 事件详情
*   POST /api/incident-info

    创建事件：

    *   请求体: `Incident` 对象
    *   返回: 创建的事件
*   PUT /api/incident-info/{id}

    修改事件：

    *   参数: 事件 ID
    *   请求体: 更新的 `Incident` 对象
    *   返回: 更新后的事件
*   DELETE /api/incident-info/{id}

    删除事件：

    *   参数: 事件 ID
    *   返回: 操作结果状态

#### 2.**验证和异常处理**:

    *   统一的异常处理机制，自定义异常、封装统一返回结果，返回标准化格式的错误响应。
***
### **缓存机制**：

    *   使用redis作为缓存中间件

### **全面测试**：

#### 1、单元测试： 
    使用 JUnit 和 Mockito 对控制器层进行单元测试

#### 2、压力测试：
    使用 Apache JMeter 进行接口性能测试（未做）

### **本地运行**：
#### 后端运行:
	*启动redis服务，修改配置文件为自己的redis地址
	*启动incident-management服务
#### 前端运行:	
	*确保安装过node,yarn,安装过程参考官方网站
	*yarn add 安装项目依赖
	*yarn serve 启动项目
	

##  后期扩展方向
#### 技术方向:
    *   数据存数到数据库mysql等数据库中。
    *   部署到服务器上，根据项目规模使用docker或者docker+k8s部署
    *   添加统一鉴权，走网关，方便和其他服务相互调用
    *   引入日志记录功能，可以使用简单的日志注解记录，也可以使用elk专业的日志记录框架。
    *   引入监控系统，如grafana+Prometheus。
#### 业务方向:
	*   引入事件处理流程，以及告警通知功能。

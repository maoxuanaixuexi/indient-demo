import axios from 'axios';

// 设置基础URL
const api = axios.create({
  baseURL: 'http://127.0.0.1:8080/api/', // 替换成你的实际地址
  timeout: 10000,
});

// 获取事件列表
export const getIncidents = (size,page) => api.get('/incident-info/getPageIncidents?size='+size+"&page="+page);

// 创建事件
export const createIncident = (incident) => api.post('/incident-info', incident);

// 更新事件
export const updateIncident = (id, incident) => api.put(`/incident-info/${id}`, incident);

// 删除事件
export const deleteIncident = (id) => api.delete(`/incident-info/${id}`);

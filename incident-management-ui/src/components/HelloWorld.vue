<template>
  <div>
    <el-button type="primary" @click="handleCreate">新增事件</el-button>
    <el-button type="primary" @click="handleSearch">查询事件</el-button>
    <el-table :data="incidents" style="width: 100%; margin-top: 20px;">
      <el-table-column label="序号" width="100" align="center">
            <template slot-scope="scope">
              <div class="xuhao_number">
                {{ scope.$index + 1 }}
              </div>
            </template>
          </el-table-column>
      <el-table-column label="事件名称" prop="incidentName" />
      <el-table-column label="编号" prop="id" v-if="false"/>
      <el-table-column label="事件类型" prop="incidentType" />
      <el-table-column type="text" label="事件描述" prop="incidentDesc" />
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button @click="handleEdit(scope.row)" size="small" type="warning">编辑</el-button>
          <el-button @click="handleDelete(scope.row.id)" size="small" type="danger">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :total="total"
      :page-size="pageSize"
      :current-page="currentPage"
      layout="total, prev, pager, next, jumper"
      @current-change="handlePageChange"
    ></el-pagination>

    <!-- 事件编辑弹窗 -->
    <el-dialog :visible.sync="dialogVisible" title="编辑事件">
      <el-form :model="currentIncident">
        <el-form-item label="事件名">
          <el-input v-model="currentIncident.incidentName" />
        </el-form-item>
        <el-form-item label="事件类型">
          <el-input v-model="currentIncident.incidentType" />
        </el-form-item>
        <el-form-item label="事件描述">
          <el-input v-model="currentIncident.incidentDesc" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </span>
    </el-dialog>
  </div>
</template>


<script>
import { getIncidents, createIncident, updateIncident, deleteIncident } from '../api/api.js';
export default {
  data() {
    return {
      incidents: [], // 事件列表
      dialogVisible: false, // 编辑框显示控制
      total: 0,           // 数据总数
      size: 10,         // 每页显示条数
      page: 0,
      currentIncident: { // 当前编辑的事件
        incidentName: '',
        incidentType: '',
        incidentDesc: ''
      },
    };
  },
  created() {
    this.fetchIncidents(); // 组件创建时获取事件列表
  },
  methods: {
    async fetchIncidents() {
      try {
        const response = await getIncidents(this.size,this.page);
        console.log(JSON.stringify(response)+"===========")
        this.incidents = response.data.data.content;
        this.total = response.data.data.numberOfElements;
      } catch (error) {
        this.$message.error('获取事件列表失败');
      }
    },
    handleCreate() {
      this.currentIncident = {incidentName: '', incidentType: '',incidentDesc:'' }; // 清空表单
      this.dialogVisible = true; // 显示编辑框
    },
    async handleEdit(incident) {
      this.currentIncident = { ...incident }; // 填充表单数据
      this.dialogVisible = true;
    },
    async handleDelete(id) {
      try {
        await deleteIncident(id);
        this.$message.success('事件删除成功');
        this.fetchIncidents(); // 删除成功后刷新事件列表
      } catch (error) {
        this.$message.error('删除失败');
      }
    },
    async handleSave() {
      try {
        if (this.currentIncident.id) {
          // 如果事件已经存在，更新事件
          await updateIncident(this.currentIncident.id, this.currentIncident);
          this.$message.success('事件更新成功');
        } else {
          // 否则创建新事件
          await createIncident(this.currentIncident);
          this.$message.success('事件创建成功');
        }
        this.fetchIncidents(); // 保存后刷新事件列表
        this.dialogVisible = false; // 关闭编辑框
      } catch (error) {
        this.$message.error('保存失败');
      }
    },
    async handleSearch(){
      this.fetchIncidents();
    },
     // 处理分页变化，更新当前页面的数据
     handlePageChange(page) {
      this.page = page;
      this.fetchIncidents();
    },

    // 根据当前页和每页大小更新显示的数据
    updateCurrentData() {
      const start = (this.page - 1) * this.size;
      const end = this.page * this.size;
      this.incidents = this.allData.slice(start, end);
    }
  },
};
</script>

<style>
#app {
  padding: 20px;
}
</style>
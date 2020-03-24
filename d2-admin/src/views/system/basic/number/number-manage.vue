<template>
  <d2-container>
    <el-row>
      <el-col style="margin-bottom: 20px;">
        <el-row style="display: flex;align-items: center;">
          <el-col :span="16">
            <div style="display: flex;flex-direction: row;">
              <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
                <div style="font-size: 18px;width: 20%;text-align: right;margin-right: 20px;">号码:</div>
                <el-input placeholder="请输入号码" v-model="searchInput.id" clearable style="width: 80%;">
                </el-input>
              </div>
              <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
                <div style="font-size: 18px;width: 20%;text-align: right;margin-right: 20px;">状态:</div>
                <el-input placeholder="请输入号码状态" v-model="searchInput.flag" clearable style="width: 80%;">
                </el-input>
              </div>

            </div>
          </el-col>
          <el-col :span="8">
            <div style="display: flex;align-items: center;justify-content: left;">
              <el-button type="primary">查询</el-button>
              <el-button @click="createBtnClick()" type="danger" style="margin-left: 40px;">新增号码</el-button>
            </div>
          </el-col>
        </el-row>
      </el-col>
      <hr />
      <el-col style="margin-top: 20px;">
        <el-table :data="tableData" border style="width: 100%">
          <el-table-column prop="date" label="编号">
          </el-table-column>
          <el-table-column prop="name" label="号码">
          </el-table-column>
          <el-table-column prop="address" label="状态">
          </el-table-column>
          <el-table-column fixed="right" label="操作">
            <template slot-scope="scope">
              <el-button @click="handleClick(scope.row)" type="text" size="small">查看机主</el-button>
              <el-button type="text" size="small">编辑</el-button>
              <el-button @click="handleDeleteClick(scope.row)" type="text" size="small">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>

    </el-row>

    <el-dialog title="收货地址" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="活动名称" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="活动区域" :label-width="formLabelWidth">
          <el-select v-model="form.region" placeholder="请选择活动区域">
            <el-option label="区域一" value="shanghai"></el-option>
            <el-option label="区域二" value="beijing"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
      </div>
    </el-dialog>
  </d2-container>

</template>

<script>
export default {
  methods: {
    createBtnClick () {
      this.dialogFormVisible = true
    },
    handleClick (row) {
      console.log(row)
      this.$router.push({
        path: 'client-detail',
        query: {
          name: '张三'
        }
      })
    },
    handleDeleteClick (row) {
      this.$confirm('确认删除？')
        .then(_ => {
          console.log('删除')
        })
        .catch(_ => {
          console.log('取消')
        })
    }

  },
  data () {
    return {

      // 显示删除
      dialogVisible: false,

      // 显示添加表单
      dialogFormVisible: false,
      formLabelWidth: '120px',
      form: {
        name: '',
        region: ''
      },
      searchInput: {
        id: '',
        flag: ''
      },
      tableData: [{
        id: '000001',
        number: '18756235478',
        flag: 'white'
      }]
    }
  }
}
</script>

<style>
</style>

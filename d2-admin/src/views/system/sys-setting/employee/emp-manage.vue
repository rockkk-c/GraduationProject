<template>
  <d2-container>
    <el-row>
      <el-col style="margin-bottom: 20px;">
        <el-row style="display: flex;align-items: center;">
          <el-col :span="16">
            <div style="display: flex;flex-direction: row;">
              <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
                <div style="font-size: 18px;width: 20%;text-align: right;margin-right: 20px;">编号:</div>
                <el-input placeholder="请输入员工编号" v-model="searchInput.empId" clearable style="width: 80%;">
                </el-input>
              </div>
              <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
                <div style="font-size: 18px;width: 20%;text-align: right;margin-right: 20px;">姓名:</div>
                <el-input placeholder="请输入员工姓名" v-model="searchInput.empName" clearable style="width: 80%;">
                </el-input>
              </div>
              <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
                <div style="font-size: 18px;width: 20%;text-align: right;margin-right: 20px;">角色:</div>
                <el-input placeholder="请输入员工角色" v-model="searchInput.empRole" clearable style="width: 80%;">
                </el-input>
              </div>

            </div>
          </el-col>
          <el-col :span="8">
            <div style="display: flex;align-items: center;justify-content: left;">
              <el-button type="primary" @click="checkBtnClick()">查询</el-button>
              <el-button @click="createBtnClick()" type="danger" style="margin-left: 20px;" v-if="role=='admin'">新增员工</el-button>
            </div>
          </el-col>
        </el-row>
      </el-col>
      <hr />
      <el-col style="margin-top: 20px;">
        <el-table :data="tableData" border style="width: 100%">
          <el-table-column prop="id" label="编号">
          </el-table-column>
          <el-table-column prop="name" label="姓名">
          </el-table-column>
          <el-table-column label="角色">
              <template slot-scope="scope">
               {{scope.row.role == 'admin'?"管理员":"员工"}}
              </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作">
            <template slot-scope="scope">
              <!--             <el-button @click="handleClick(scope.row)" type="text" size="small">查看机主</el-button>-->
              <el-button type="text" size="small">编辑</el-button>
              <el-button @click="handleDeleteClick(scope.row)" type="text" size="small">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>

    </el-row>

    <el-dialog title="新增员工" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="员工姓名" :label-width="formLabelWidth">
          <el-input v-model="form.empName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="账号密码" :label-width="formLabelWidth">
          <el-input v-model="form.empPwd" autocomplete="off" value="123456" placeholder="默认密码为：123456，此项可以不填写"></el-input>
        </el-form-item>
        <el-form-item label="员工角色" :label-width="formLabelWidth">
          <el-select v-model="form.empRole" placeholder="请选择员工角色">
            <el-option label="管理员" value="admin"></el-option>
            <el-option label="普通员工" value="staff"></el-option>
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
import gql from 'graphql-tag'
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
    },
    checkBtnClick () {
      let This = this
      this.$apollo.query({
        // Query
        query: gql`query{
                loadListOfEmployee(phone:{
                       id: $id
                       name: $name
                        role: $role
                }){
                    id,
                    name,
                    role
                }
         }`,
        variables: {
          id: this.empId,
          name: this.empName,
          role: this.empRole
        }
      }).then(res => {
        This.tableData = res.data.loadListOfEmployee
      }).catch(error => {
        console.log(error)
      })
    }
  },

  created () {
    let This = this
    this.role = this.$store.state.role
    this.$apollo.query({
      // Query
      query: gql`query{
                selectAllEmployee{
                    id,
                    name,
                    role
                }
         }`,
      variables: {
        // role: this.role,
      }
    }).then(res => {
      This.tableData = res.data.selectAllEmployee
    }).catch(error => {
      console.log(error)
    })
  },

  data () {
    return {

      // 显示删除
      dialogVisible: false,

      // 显示添加表单
      dialogFormVisible: false,
      formLabelWidth: '120px',
      form: {
        empId: '',
        empName: '',
        empRole: ''
      },
      // 角色
      role: '',
      searchInput: {
        empId: '',
        empName: '',
        empRole: ''
      },
      tableData: [{
        id: '',
        name: '李',
        role: 'admi'
      }]
    }
  }
}
</script>

<style>
</style>

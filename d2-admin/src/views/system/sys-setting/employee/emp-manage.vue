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
                <el-select v-model="searchInput.empRole" placeholder="请选择员工角色">
                  <el-option label="管理员" value="admin"></el-option>
                  <el-option label="普通员工" value="staff"></el-option>
                </el-select>
              </div>

            </div>
          </el-col>
          <el-col :span="8">
            <div style="display: flex;align-items: center;justify-content: left;">
              <el-button type="primary" @click="search()">查询</el-button>
              <el-button type="primary" @click="resetClick()">重置</el-button>
              <el-button @click="createBtnClick()" type="danger" style="margin-left: 20px;">新增员工</el-button>
            </div>
          </el-col>
        </el-row>
      </el-col>
      <hr />
      <el-col style="margin-top: 20px;">
        <el-table :key="Math.random()" :data="tableData" border style="width: 100%">
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
              <el-button @click="editEmp(scope.row)" type="text" size="small">编辑</el-button>
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
        <el-button type="primary" @click="createBtn()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改员工" :visible.sync="dialogUpdateFormVisible">
      <el-form :model="updateForm">
        <el-form-item label="员工姓名" :label-width="formLabelWidth">
          <el-input v-model="updateForm.empName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="更改密码" :label-width="formLabelWidth">
          <el-input v-model="updateForm.empPwd" type="password" autocomplete="off" value="123456" placeholder="员工密码"></el-input>
        </el-form-item>
        <el-form-item label="员工角色" :label-width="formLabelWidth">
          <el-select v-model="updateForm.empRole" placeholder="请选择员工角色">
            <el-option label="管理员" value="admin"></el-option>
            <el-option label="普通员工" value="staff"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogUpdateFormVisible = false">取 消</el-button>
        <el-button type="warning" @click="updateBtn()">修 改</el-button>
      </div>
    </el-dialog>

  </d2-container>

</template>

<script>
import gql from 'graphql-tag'
export default {
  methods: {
    search () {
      let This = this
      this.$apollo.query({
        // Query
        query: gql`query($empId: String!,$empName: String!,$role: String!){
                   loadListOfEmployee(employee:{
                         id:$empId
                        name:$empName
                        role:$role
                         }){
                          id,
                         name,
                         role
                     }
           }`,
        variables: {
          empId: This.searchInput.empId,
          empName: This.searchInput.empName,
          role: This.searchInput.empRole
        }
      }).then(res => {
        console.log(res)
        This.tableData = res.data.loadListOfEmployee
      }).catch(error => {
        console.log(error)
      })
    },
    createBtnClick () {
      this.dialogFormVisible = true
    },
    //  重置
    resetClick () {
      this.refreshTable()
      this.searchInput.empId = ''
      this.searchInput.empName = ''
      this.searchInput.empRole = ''
    },
    refreshTable () {
      let This = this

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
    handleClick (row) {
      console.log(row)
      this.$router.push({
        path: 'client-detail',
        query: {
          name: '张三'
        }
      })
    },
    editEmp (row) {
      console.log(row)
      this.updateForm.empId = row.id
      this.updateForm.empName = row.name
      this.updateForm.empRole = row.role
      this.dialogUpdateFormVisible = true
    },
    updateBtn () {
      let This = this
      if (this.$store.state.role === 'admin' && This.updateForm.empRole && This.$store.state.id !== This.updateForm.empId) {
        This.$message({
          message: '管理员无法修改除自己外的管理员信息',
          type: 'error'
        })
        return 0
      }
      this.$apollo.mutate({
        // Query
        mutation: gql`mutation ($id: String!,$empName: String!,$pwd: String!,$role: String!) {
                updateEmp(employee:{
                       id:$id,
                       name:$empName,
                       pwd:$pwd,
                       role:$role
                       }){
                           code,
                           message
                       }
              }`,
        // Parameters
        variables: {
          id: This.updateForm.empId,
          empName: This.updateForm.empName,
          pwd: This.updateForm.empPwd === '' ? '123456' : This.updateForm.empPwd,
          role: This.updateForm.empRole
        }
      }).then(res => {
        console.log(res)
        if (res.data.updateEmp.code === 0) {
          This.updateTableData(This.updateForm.empId, This.updateForm.empName, This.updateForm.empRole)
          This.$message({
            message: '员工修改成功',
            type: 'success'
          })
          This.dialogUpdateFormVisible = false
        }
      }).catch(error => {
        console.log(error)
      })
    },
    createBtn () {
      let This = this
      console.log(this.form)
      if (this.form.empName === '') {
        this.$message({
          message: '姓名不能为空',
          type: 'warning'
        })
        this.dialogFormVisible = false
      } else {
        this.$apollo.mutate({
          // Query
          mutation: gql`mutation ($empName: String!,$pwd: String!,$role: String!) {
                   addEmployee(employee:{
                          name:$empName,
                          pwd:$pwd,
                          role:$role}){
                              code,
                              message
                          }
                 }`,
          // Parameters
          variables: {
            empName: This.form.empName,
            pwd: This.form.empPwd,
            role: This.form.empRole
          }
        }).then(res => {
          if (res.data.addEmployee.code === 0) {
            This.$message({
              message: '员工添加成功',
              type: 'success'
            })
            This.refreshTable()
            This.dialogFormVisible = false
          } else {
            This.$message({
              message: '员工添加失败,原因' + res.data.addEmployee.message,
              type: 'error'
            })
            This.dialogFormVisible = false
          }
        }).catch(error => {
          console.log(error)
          This.$message({
            message: '服务器接口异常',
            type: 'error'
          })
          This.dialogFormVisible = false
        })
      }
    },

    handleDeleteClick (row) {
      console.log(row)
      let This = this
      this.$confirm('确认删除？')
        .then(_ => {
          this.$apollo.mutate({
            // Query
            mutation: gql`mutation ($id: String!) {
                    deleteEmp(employee:{
                           id:$id}){
                               code,
                               message
                           }
                  }`,
            // Parameters
            variables: {
              id: row.id + ''
            }
          }).then(res => {
            if (res.data.deleteEmp.code === 0) {
              This.deleteTableData(row.id)
              This.$message({
                message: '员工删除成功',
                type: 'success'
              })
            }
          }).catch(error => {
            console.log(error)
          })
        })
        .catch(_ => {
          console.log('取消')
        })
    },
    test () {
      console.log(this.$store.state.role)
    },
    deleteTableData (id) {
      for (let i = 0; i < this.tableData.length; i++) {
        if (this.tableData[i].id === id) {
          this.tableData.splice(i, 1)
          break
        }
      }
    },
    updateTableData (id, name, role) {
      let This = this
      for (let i = 0; i < This.tableData.length; i++) {
        if (This.tableData[i].id === id) {
          console.log('更新')
          This.tableData[i].name = name
          This.tableData[i].role = role
        }
      }
    }
  },

  created () {
    this.role = this.$store.state.role
    this.refreshTable()
  },
  data () {
    return {
      // 显示删除
      dialogVisible: false,
      // 显示编辑
      dialogUpdateFormVisible: false,
      // 显示添加表单
      dialogFormVisible: false,
      formLabelWidth: '120px',
      form: {
        empName: '',
        empPwd: '',
        empRole: 'admin'
      },
      updateForm: {
        empId: '',
        empName: '',
        empPwd: '',
        empRole: 'admin'
      },
      // 角色
      role: '',
      searchInput: {
        empId: '',
        empName: '',
        empRole: 'admin'
      },
      tableData: []
    }
  }
}
</script>

<style>
</style>

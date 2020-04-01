<template>
  <d2-container>
    <el-row>
      <el-col style="margin-bottom: 20px;">
        <el-row style="display: flex;align-items: center;" :gutter="20">
          <el-col :span="18">
            <div style="display: flex;flex-direction: row;">
              <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
                <div style="font-size: 18px;width: 20%;text-align: right;margin-right: 20px;">ID:</div>
                <el-input placeholder="请输入ID" v-model="searchInput.id" clearable style="width: 80%;">
                </el-input>
              </div>
              <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
                <div style="font-size: 18px;width: 20%;text-align: right;margin-right: 20px;">姓名:</div>
                <el-input placeholder="请输入姓名" v-model="searchInput.name" clearable style="width: 80%;">
                </el-input>
              </div>
              <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
                <div style="font-size: 18px;width: 40%;text-align: right;margin-right: 20px;">性别:</div>
                <el-select v-model="searchInput.sex" placeholder="请选择性别">
                  <el-option label="男" value="MALE"></el-option>
                  <el-option label="女" value="FEMALE"></el-option>
                </el-select>
              </div>
              <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
                <div style="font-size: 18px;width: 20%;text-align: right;margin-right: 20px;">电话:</div>
                <el-input placeholder="请输入电话" v-model="searchInput.phone" clearable style="width: 80%;">
                </el-input>
              </div>
              <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
                <div style="font-size: 18px;width: 40%;text-align: right;margin-right: 20px;">状态:</div>
                <el-select v-model="searchInput.blackList" placeholder="请选择员工角色">
                  <el-option label="白名单" value="WHITE"></el-option>
                  <el-option label="黑名单" value="BLACK"></el-option>
                </el-select>
              </div>
            </div>
          </el-col>
          <el-col :span="6">
            <div style="display: flex;align-items: center;justify-content: left;">
              <el-button type="primary" @click="search()">查询</el-button>
              <el-button @click="createBtnClick()" type="danger" style="margin-left: 20px;">新增客户</el-button>
            </div>
          </el-col>
        </el-row>
      </el-col>
      <hr/>
      <el-col style="margin-top: 20px;">
        <el-table :data="tableData" border  style="width: 100%">
          <el-table-column prop="id"   label="ID">
          </el-table-column>
          <el-table-column prop="name" label="姓名">
          </el-table-column>
          <el-table-column label="性别">
            <template slot-scope="scope">
                {{scope.row.sex == 'MALE'?'男':'女'}}
              </template>
          </el-table-column>
          <el-table-column prop="number" label="电话">
          </el-table-column>
          <el-table-column label="状态">
             <template slot-scope="scope">
                 {{scope.row.flag == 'WHITE'?'白名单':'黑名单'}}
               </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作">
            <template slot-scope="scope">
              <el-button @click="handleClick(scope.row)" type="text" size="small">他的进件</el-button>
              <el-button @click="editClient(scope.row)" type="text" size="small">编辑</el-button>
              <el-button @click="handleDeleteClick(scope.row)" type="text" size="small">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>

    </el-row>

    <el-dialog title="添加新的客户" label-position="right" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="createFrom">
        <el-form-item label="客户ID" :label-width="180">
          <el-input v-model="createFrom.id" style="width: 200px;"  placeholder="客户ID"></el-input>
        </el-form-item>
        <el-form-item label="客户姓名" :label-width="80">
          <el-input v-model="createFrom.name" style="width: 200px;"  placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item label="客户性别" :label-width="80">
           <el-radio v-model="createFrom.sex" label="MALE">男</el-radio>
           <el-radio v-model="createFrom.sex" label="FEMALE">女</el-radio>
        </el-form-item>
        <el-form-item label="客户电话" :label-width="80">
          <el-input v-model="createFrom.phone" style="width: 200px;"  placeholder="电话"></el-input>
        </el-form-item>
         <el-form-item label="客户状态" :label-width="80" style="display: flex;align-items: center;">
            <el-switch
              style="display: block"
              v-model="createFrom.state"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="白名单"
              inactive-text="黑名单">
            </el-switch>
         </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="clickCreate()" >确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改客户信息" :visible.sync="dialogUpdateFormVisible">
      <el-form :model="updateForm">

        <el-form-item label="ID" :label-width="formLabelWidth">
          <el-input v-model="updateForm.clientId" autocomplete="off" readonly="readonly"></el-input>
        </el-form-item>
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="updateForm.clientName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" :label-width="formLabelWidth">
          <el-select v-model="updateForm.clientSex" placeholder="请选择客户性别">
            <el-option label="男" value="MALE"></el-option>
            <el-option label="女" value="FEMALE"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="电话" :label-width="formLabelWidth">
          <el-input v-model="updateForm.clientPhone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="状态" :label-width="formLabelWidth">
          <el-select v-model="updateForm.clientFlag" placeholder="客户状态">
            <el-option label="黑名单" value="BLACK"></el-option>
            <el-option label="白名单" value="WHITE"></el-option>
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
        query: gql`query($id:String!,$name:String!,$sex:String!,$number:String!,$flag:String!){
                selectPerson(person:{
                 id:$id,
                 name:$name,
                 sex:$sex,
                 number:$number,
                 flag:$flag,
                })
                 {
                    id,
                    name,
                    sex,
                    number,
                    flag
                 }
       }`,
        variables: {
          id: this.searchInput.id,
          name: this.searchInput.name,
          sex: this.searchInput.sex,
          number: this.searchInput.phone,
          flag: this.searchInput.blackList
        }
      }).then(res => {
        console.log(res)
        This.tableData = res.data.selectPerson
      }).catch(error => {
        console.log(error)
      })
    },
    editClient (row) {
      console.log(row)
      this.updateForm.clientId = row.id
      this.updateForm.clientName = row.name
      this.updateForm.clientSex = row.sex
      this.updateForm.clientPhone = row.number
      this.updateForm.clientFlag = row.flag
      this.dialogUpdateFormVisible = true
    },
    updateBtn () {
      let This = this
      this.$apollo.mutate({
        // Query
        mutation: gql`mutation ($id: String!,$name: String!,$sex: String!,$number: String!,$flag: String!) {
                updatePersonById(person:{
                       id:$id,
                       name:$name,
                       sex:$sex,
                       number:$number,
                       flag: $flag
                       }){
                           code,
                           message
                       }
              }`,
        // Parameters
        variables: {
          id: this.updateForm.clientId,
          name: this.updateForm.clientName,
          sex: this.updateForm.clientSex,
          number: this.updateForm.clientPhone,
          flag: this.updateForm.clientFlag
        }
      }).then(res => {
        console.log(res)
        if (res.data.updatePersonById.code === 0) {
          This.updateTableData(this.updateForm.clientId, this.updateForm.clientName, this.updateForm.clientSex, this.updateForm.clientPhone, this.updateForm.clientFlag)
          This.$message({
            message: '客户修改成功',
            type: 'success'
          })
          This.dialogUpdateFormVisible = false
        }
      }).catch(error => {
        console.log(error)
      })
    },
    updateTableData (id, name, sex, number, flag) {
      let This = this
      for (let i = 0; i < This.tableData.length; i++) {
        if (This.tableData[i].id === id) {
          This.tableData[i].name = name
          This.tableData[i].sex = sex
          This.tableData[i].number = number
          This.tableData[i].flag = flag
        }
      }
    },
    clickCreate () {
      this.$apollo.mutate({
        // Query
        mutation: gql`mutation($id:String!,$name:String!,$sex:String!,$number:String!,$flag:String!){
               addPerson(person:{
                 id:$id,
                 name:$name,
                 sex:$sex,
                 number:$number,
                 flag:$flag,
               })
               {
                   code,
                   message
               }
       }`,
        variables: {
          id: this.createFrom.id,
          name: this.createFrom.name,
          sex: this.createFrom.sex,
          number: this.createFrom.phone,
          flag: this.createFrom.state ? 'WHITE' : 'BLACK'
        }
      }).then(res => {
        console.log(res)
        if (res.data.addPerson.code === 0) {
          this.$message({
            message: '客户添加成功',
            type: 'success'
          })

          this.tableData.push({
            id: this.createFrom.id,
            name: this.createFrom.name,
            sex: this.createFrom.sex,
            number: this.createFrom.phone,
            flag: this.createFrom.state ? 'WHITE' : 'BLACK'
          })
        }
      }).catch(error => {
        console.log(error)
      })
      this.dialogFormVisible = false
    },
    createBtnClick () {
      this.dialogFormVisible = true
    },
    handleClick (row) {
      this.$router.push({
        path: 'apply-detail',
        query: {
          id: row.id
        }
      })
    },
    handleDeleteClick (row) {
      let This = this
      this.$confirm('确认删除？')
        .then(_ => {
          this.$apollo.mutate({
            // Query
            mutation: gql`mutation($id:String!){
                     deletePersonById(id:$id)
                     {
                         code,
                         message
                     }
             }`,
            variables: {
              id: row.id
            }
          }).then(res => {
            if (res.data.deletePersonById.code === 0) {
              This.deleteTableData(row.id)
              This.$message({
                message: '删除成功',
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
    deleteTableData (id) {
      for (let i = 0; i < this.tableData.length; i++) {
        if (this.tableData[i].id === id) {
          this.tableData.splice(i, 1)
          break
        }
      }
    }

  },
  created () {
    this.$apollo.query({
      // Query
      query: gql`query{
             selectAllPerson
                    {
                        id,
                          name,
                          sex,
                          number,
                          flag
                    }
     }`,
      variables: {
        // role: this.role,
      }
    }).then(res => {
      this.tableData = res.data.selectAllPerson
    }).catch(error => {
      console.log(error)
    })
  },
  data () {
    return {

      // 显示删除
      dialogVisible: false,
      dialogUpdateFormVisible: false,
      // 显示添加表单
      dialogFormVisible: false,
      formLabelWidth: '120px',
      createFrom: {
        id: '',
        name: '',
        sex: 'MALE',
        phone: '',
        state: true

      },
      //  修改客户信息表单参数
      updateForm: {
        clientId: '',
        clientName: '',
        clientSex: '',
        clientPhone: '',
        clientFlag: ''
      },
      searchInput: {
        id: '',
        name: '',
        sex: '',
        phone: '',
        blackList: ''
      },
      tableData: []
    }
  }
}
</script>

<style>
</style>

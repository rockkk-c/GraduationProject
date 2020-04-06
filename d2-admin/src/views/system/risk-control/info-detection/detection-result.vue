<template >
  <d2-container>

    <el-row>

      <el-col style="margin-top: 20px;">

        <el-table :data="tableData" border style="width: 100%"   v-loading="loading" element-loading-text="拼命加载中" element-loading-background="rgba(0, 0, 0, 0.3)">
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
    },
    fetachData () {
      let This = this
      this.$apollo.query({
        // Query
        query: gql`query($id:String!){
                   fakeInfoCheck(id:$id)
                      {
                          id,
                          name,
                          sex,
                          number,
                          flag
                      }
       }`,
        variables: {
          id: this.$route.query.id
        }
      }).then(res => {
        console.log(res)
        This.tableData = res.data.fakeInfoCheck
        this.loading = false
      }).catch(error => {
        console.log(error)
      })
    }

  },
  created () {
    this.fetachData()
  },
  data () {
    return {

      // 显示删除
      dialogVisible: false,
      dialogUpdateFormVisible: false,
      loading: true,
      formLabelWidth: '120px',
      //  修改客户信息表单参数
      updateForm: {
        clientId: '',
        clientName: '',
        clientSex: '',
        clientPhone: '',
        clientFlag: ''
      },

      tableData: []
    }
  }
}
</script>

<style>
</style>

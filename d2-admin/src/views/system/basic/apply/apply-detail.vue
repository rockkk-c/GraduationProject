<template>
  <d2-container>
      <hr/>
      <el-col style="margin-top: 20px;">
        <el-table :data="tableData" border style="width: 100%">
          <el-table-column prop="id" label="进件号">
          </el-table-column>
          <el-table-column prop="amount" label="金额">
          </el-table-column>
          <el-table-column prop="term" label="期数">
          </el-table-column>
          <el-table-column prop="job" label="工作">
          </el-table-column>
          <el-table-column prop="city" label="城市">
          </el-table-column>
          <el-table-column prop="parent_phone" label="父母电话">
          </el-table-column>
          <el-table-column prop="colleague_phone" label="同事电话">
          </el-table-column>
          <el-table-column prop="company_phone" label="公司电话">
          </el-table-column>
          <el-table-column prop="applicant" label="申请人">
          </el-table-column>
          <el-table-column prop="status" label="进件状态">
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="200">
            <template slot-scope="scope">
              <el-button @click="handleClick(scope.row)" type="text" size="small">查看申请人</el-button>
              <el-button type="text" size="small" @click="editApply(scope.row)">编辑</el-button>
              <el-button @click="handleDeleteClick(scope.row)" type="danger" size="small">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>

    <el-dialog title="修改进件信息" :visible.sync="dialogUpdateFormVisible">
      <el-form :model="updateForm">

        <el-form-item label="进件号" :label-width="formLabelWidth">
          <el-input v-model="updateForm.id" autocomplete="off" readonly="readonly"></el-input>
        </el-form-item>
        <el-form-item label="金额" :label-width="formLabelWidth">
          <el-input v-model="updateForm.amount" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="期数" :label-width="formLabelWidth">
          <el-input v-model="updateForm.term" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="工作" :label-width="formLabelWidth">
          <el-input v-model="updateForm.job" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="城市" :label-width="formLabelWidth">
          <el-input v-model="updateForm.city" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="父母电话" :label-width="formLabelWidth">
          <el-input v-model="updateForm.parent_phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="同事电话" :label-width="formLabelWidth">
          <el-input v-model="updateForm.colleague_phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="公司电话" :label-width="formLabelWidth">
          <el-input v-model="updateForm.company_phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="申请人" :label-width="formLabelWidth">
          <el-input v-model="updateForm.applicant" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="状态" :label-width="formLabelWidth">
          <el-select v-model="updateForm.status" placeholder="进件状态">
            <el-option label="空" value="NULL"></el-option>
            <el-option label="无状态属性（针对新添加的未做任何修改的进件）" ></el-option>
            <el-option label="申请中" value="IN_PROGREESS"></el-option>
            <el-option label="已还清" value="PAIDOFF"></el-option>
            <el-option label="还款中" value="RETURNING"></el-option>
            <el-option label="逾期" value="OVERDUE"></el-option>
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
    createBtnClick () {
      this.dialogFormVisible = true
    },
    handleClick (row) {
      console.log(row)
      this.$router.push({
        path: 'client-detail',
        query: {
          id: row.id
        }
      })
    },

    editApply (row) {
      this.updateForm.id = row.id
      this.updateForm.amount = row.amount
      this.updateForm.term = row.term
      this.updateForm.job = row.job
      this.updateForm.parent_phone = row.parent_phone
      this.updateForm.colleague_phone = row.colleague_phone
      this.updateForm.company_phone = row.company_phone
      this.updateForm.applicant = row.applicant
      this.updateForm.status = row.status
      this.dialogUpdateFormVisible = true
    },
    updateBtn () {
      let This = this
      this.$apollo.mutate({
        // Query
        mutation: gql`mutation ($id:String!,$amount:String!,$term:String!,$job:String!,$city:String!,$parent_phone:String,$colleague_phone:String,$company_phone:String,$applicant:String!,$status:String) {
                updateApplicant(applicant:{
                    id:$id,
                    amount:$amount,
                    term:$term,
                    job:$job,
                    city:$city,
                    parent_phone:$parent_phone,
                    colleague_phone:$colleague_phone,
                    company_phone:$company_phone,
                    applicant:$applicant,
                    status:$status
        }){
                           code,
                           message
                       }
              }`,
        // Parameters
        variables: {
          id: this.updateForm.id,
          amount: this.updateForm.amount,
          term: this.updateForm.term,
          job: this.updateForm.job,
          city: this.updateForm.city,
          parent_phone: this.updateForm.parent_phone,
          colleague_phone: this.updateForm.colleague_phone,
          company_phone: this.updateForm.company_phone,
          applicant: this.updateForm.applicant,
          status: this.updateForm.status
        }
      }).then(res => {
        console.log(res)
        if (res.data.updateApplicant.code === 0) {
          This.updateTableData(this.updateForm.id, this.updateForm.amount, this.updateForm.term, this.updateForm.job, this.updateForm.city, this.updateForm.parent_phone,
            this.updateForm.colleague_phone, this.updateForm.company_phone, this.updateForm.applicant, this.updateForm.status)
          This.$message({
            message: '进件修改成功',
            type: 'success'
          })
          This.dialogUpdateFormVisible = false
        }
      }).catch(error => {
        console.log(error)
      })
    },
    handleDeleteClick (row) {
      let This = this
      this.$confirm('确认删除？')
        .then(_ => {
          this.$apollo.mutate({
            // Query
            mutation: gql`mutation($id:String!){
                     deleteApplicantById(id:$id)
                     {
                         code,
                         message
                     }
             }`,
            variables: {
              id: row.id
            }
          }).then(res => {
            if (res.data.deleteApplicantById.code === 0) {
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
    updateTableData (id, amount, term, job, city, parentPhone, colleaguePhone, companyPhone, applicant, status) {
      let This = this
      for (let i = 0; i < This.tableData.length; i++) {
        if (This.tableData[i].id === id) {
          This.tableData[i].amount = amount
          This.tableData[i].term = term
          This.tableData[i].job = job
          This.tableData[i].city = city
          This.tableData[i].parent_phone = parentPhone
          This.tableData[i].colleague_phone = colleaguePhone
          This.tableData[i].company_phone = companyPhone
          This.tableData[i].applicant = applicant
          This.tableData[i].status = status
        }
      }
    }

  },

  created () {
    let This = this
    this.$apollo.query({
      // Query
      query: gql`query($id:String!){
               selecApplicantByPId(id:$id)
                      {
                           id,
                           amount,
                           term,
                           job,
                           city,
                           parent_phone,
                           colleague_phone,
                           company_phone,
                           applicant,
                           status
                      }
       }`,
      variables: {
        id: this.$route.query.id
      }
    }).then(res => {
      console.log(res)
      This.tableData = res.data.selecApplicantByPId
    }).catch(error => {
      console.log(error)
    })
  },
  data () {
    return {

      // 显示删除
      dialogVisible: false,
      dialogUpdateFormVisible: false,
      formLabelWidth: '120px',
      form: {
        name: '', region: ''
      },

      updateForm: {
        id: '',
        amount: '',
        term: '',
        job: '',
        city: '',
        parent_phone: '',
        colleague_phone: '',
        company_phone: '',
        applicant: '',
        status: ''
      },
      tableData: []
    }
  }
}
</script>

<style>
</style>

<template>
  <d2-container>
    <el-row>
      <el-col style="margin-bottom: 20px;">
        <el-row style="display: flex;align-items: center;">
          <el-col :span="16">
            <div style="display: flex;flex-direction: row;">
              <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
                <div style="font-size: 18px;width: 30%;text-align: right;margin-right: 20px;">ID:</div>
                <el-input placeholder="请输入ID" v-model="searchInput.id" clearable style="width: 80%;">
                </el-input>
              </div>
              <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
                <div style="font-size: 18px;width: 40%;text-align: right;margin-right: 20px;">金额:</div>
                <el-input placeholder="请输入金额" v-model="searchInput.amount" clearable style="width: 80%;">
                </el-input>
              </div>
              <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
                <div style="font-size: 18px;width: 40%;text-align: right;margin-right: 20px;">期数:</div>
                <el-input placeholder="请输入期数" v-model="searchInput.term" clearable style="width: 80%;">
                </el-input>
              </div>
              <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
                <div style="font-size: 18px;width: 40%;text-align: right;margin-right: 20px;">工作:</div>
                <el-input placeholder="请输入工作" v-model="searchInput. job" clearable style="width: 80%;">
                </el-input>
              </div>
              <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
                <div style="font-size: 18px;width: 40%;text-align: right;margin-right: 20px;">城市:</div>
                <el-input placeholder="请输入城市" v-model="searchInput. city" clearable style="width: 80%;">
                </el-input>
              </div>
              <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
                <div style="font-size: 18px;width: 40%;text-align: right;">父母电话:</div>
                <el-input placeholder="请输入父母电话" v-model="searchInput. parent_phone" clearable style="width: 80%;">
                </el-input>
              </div>

            </div>
          </el-col>
        </el-row>
        <el-row style="display: flex;align-items: center;">
          <el-col :span="16">
            <div style="display: flex;flex-direction: row;">
              <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
                <div style="font-size: 18px;width: 40%;text-align: right;margin-right: 20px;">同事电话:</div>
                <el-input placeholder="请输入同事电话" v-model="searchInput. colleague_phone" clearable style="width: 80%;margin-right: 20px;">
                </el-input>
              </div>
              <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
                <div style="font-size: 18px;width: 40%;text-align: right;margin-right: 20px;">公司电话:</div>
                <el-input placeholder="请输入公司电话" v-model="searchInput. company_phone" clearable style="width: 80%;margin-right: 20px;">
                </el-input>
              </div>
         <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
              <div style="font-size: 18px;width: 40%;text-align: right;margin-right: 20px;">状态:</div>
              <el-input placeholder="请输入状态" v-model="searchInput. status" clearable style="width: 80%;margin-right: 20px;">
              </el-input>
            </div>

            </div>
          </el-col>
          <el-col :span="8">
            <div style="display: flex;align-items: center;justify-content: left;">
              <el-button type="primary" @click="search()">查询</el-button>
              <el-button type="primary" @click="resetClick()">重置</el-button>
              <el-button @click="createBtnClick()" type="danger" style="margin-left: 20px;">新增进件</el-button>
            </div>
          </el-col>
        </el-row>
      </el-col>
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
        <d2-crud
          :data="tableData"
          :pagination="pagination"
          @pagination-current-change="paginationCurrentChange"/>
      </el-col>


    </el-row>

    <el-dialog title="添加新的进件"  :visible.sync="dialogFormVisible" width="30%">
      <el-form label-position="right" label-width="80px" :model="createFrom" :inline="true">
        <el-form-item label="进件号">
          <el-input v-model="createFrom.id" style="width: 200px;"  placeholder="进件号"></el-input>
        </el-form-item>

        <el-form-item label="金额">
          <el-input v-model="createFrom.amount" style="width: 200px;"  placeholder="金额"></el-input>
        </el-form-item>

        <el-form-item label="期数">
          <el-input v-model="createFrom.term" style="width: 200px;"  placeholder="期数"></el-input>
        </el-form-item>

        <el-form-item label="工作">
          <el-input v-model="createFrom.job" style="width: 200px;"  placeholder="工作"></el-input>
        </el-form-item>

        <el-form-item label="城市">
          <el-input v-model="createFrom.city" style="width: 200px;"  placeholder="城市"></el-input>
        </el-form-item>

        <el-form-item label="父母电话">
          <el-input v-model="createFrom.parent_phone" style="width: 200px;"  placeholder="父母电话"></el-input>
        </el-form-item>

        <el-form-item label="同事电话">
          <el-input v-model="createFrom.colleague_phone" style="width: 200px;"  placeholder="同事电话"></el-input>
        </el-form-item>

        <el-form-item label="公司电话">
          <el-input v-model="createFrom.company_phone" style="width: 200px;"  placeholder="公司电话"></el-input>
        </el-form-item>

        <el-form-item label="申请人">
          <el-input v-model="createFrom.applicant" style="width: 200px;"  placeholder="申请人"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="createBtn()">确 定</el-button>
      </div>
    </el-dialog>

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
    search () {
      console.log('按条件查询进件')
      let This = this
      this.$apollo.query({
        // Query
        query: gql`query($id:String,$amount:String,$term:String,$job:String,$city:String,$parent_phone:String,$colleague_phone:String,$company_phone:String,$status:String,$currentPage:Int!){
                  selectApplicant(applicant:{
                    id:$id,
                    amount:$amount,
                    term:$term,
                    job:$job,
                    city:$city,
                    parent_phone:$parent_phone,
                    colleague_phone:$colleague_phone,
                    company_phone:$company_phone,
                    status:$status,
                    currentPage:$currentPage
            }){
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
          id: this.searchInput.id,
          amount: this.searchInput.amount,
          job: this.searchInput.job,
          city: this.searchInput.city,
          parent_phone: this.searchInput.parent_phone,
          colleague_phone: this.searchInput.colleague_phone,
          company_phone: this.searchInput.company_phone,
          status: this.searchInput.status,
          currentPage: this.pagination.currentPage
        }
      }).then(res => {
        console.log(res)
        This.tableData = res.data.selectApplicantcontent
      }).catch(error => {
        console.log(error)
      })
    },
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

    createBtn () {
      var myreg = /^[1,0][0-9][0-9]{9}$/
      if (!myreg.test(this.createFrom.company_phone)) {
        this.$message({
          message: '公司电话输入格式有误',
          type: 'error'
        })
        this.createFrom.company_phone = ''
        return
      }
      if (!myreg.test(this.createFrom.colleague_phone)) {
        this.$message({
          message: '同事电话输入格式有误',
          type: 'error'
        })
        this.createFrom.colleague_phone = ''
        return
      }
      if (!myreg.test(this.createFrom.parent_phone)) {
        this.$message({
          message: '父母电话输入格式有误',
          type: 'error'
        })
        this.createFrom.parent_phone = ''
        return
      }
      this.$apollo.mutate({

        mutation: gql`mutation($id:String!,$amount:String!,$term:String!,$job:String!,$city:String!,$parent_phone:String,$colleague_phone:String,$company_phone:String,$applicant:String!){
                   addApplicant(applicant:{
                    id:$id,
                    amount:$amount,
                    term:$term,
                    job:$job,
                    city:$city,
                    parent_phone:$parent_phone,
                    colleague_phone:$colleague_phone,
                    company_phone:$company_phone,
                    applicant:$applicant
        })
        {
            code,
            message
        }
}`,
        variables: {
          id: this.createFrom.id,
          amount: this.createFrom.amount,
          term: this.createFrom.term,
          job: this.createFrom.job,
          city: this.createFrom.city,
          parent_phone: this.createFrom.parent_phone,
          colleague_phone: this.createFrom.colleague_phone,
          company_phone: this.createFrom.company_phone,
          applicant: this.createFrom.applicant
        }
      }).then(res => {
        if (res.data.addApplicant.code === 0) {
          this.$message({
            message: '进件添加成功',
            type: 'success'
          })
        } else {
          this.$message({
            message: '进件添加失败',
            type: 'error'
          })
        }
      }).catch(error => {
        console.log(error)
      })
      this.dialogFormVisible = false
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
    },
    //  重置
    resetClick () {
      this.refreshTable()
      this.searchInput.id = ''
      this.searchInput.amount = ''
      this.searchInput.job = ''
      this.searchInput.city = ''
      this.searchInput.parent_phone = ''
      this.searchInput.colleague_phone = ''
      this.searchInput.company_phone = ''
      this.searchInput.status = ''
    },
    refreshTable () {
      let This = this

      this.$apollo.query({
        // Query
        query: gql`query($currentPage:Int!){
               selectAllApplicant(currentPage:$currentPage)
                      {
                          content{
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
                         },
                          pageable{
                              pageNumber,
                              pageSize
                          },
                          totalElements,
                          totalPages
                      }

       }`,
        variables: {
          currentPage: this.pagination.currentPage
        }
      }).then(res => {
        This.tableData = res.data.selectAllApplicant.content
      }).catch(error => {
        console.log(error)
      })
    },
    addTableData (id, amount, term, job, city, parentPhone, colleaguePhone, companyPhone, applicant) {
      let This = this
      let i = This.tableData.length
      This.tableData[i].id = id
      This.tableData[i].amount = amount
      This.tableData[i].term = term
      This.tableData[i].job = job
      This.tableData[i].city = city
      This.tableData[i].parent_phone = parentPhone
      This.tableData[i].colleague_phone = colleaguePhone
      This.tableData[i].company_phone = companyPhone
      This.tableData[i].applicant = applicant
    },
    paginationCurrentChange (currentPage) {
      this.pagination.currentPage = currentPage
      this.fetchData()
    },
    fetchData () {
      this.loading = true
      this.$apollo.query({
        // Query
        query: gql`query($currentPage:Int!){
               selectAllApplicant(currentPage:$currentPage)
                      {
                          content{
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
                         },
                          pageable{
                              pageNumber,
                              pageSize
                          },
                          totalElements,
                          totalPages
                      }

       }`,
        variables: {
          currentPage: this.pagination.currentPage
        }
      }).then(res => {
        this.tableData = res.data.selectAllApplicant.content
        this.pagination.total = res.data.selectAllApplicant.totalElements
        this.loading = false
      }).catch(error => {
        console.log(error)
        this.loading = false
      })
    }

  },

  created () {
    this.fetchData()
    // let This = this
    // this.$apollo.query({
    //   // Query
    //   query: gql`query{
    //            selectAllApplicant
    //                   {
    //                        id,
    //                        amount,
    //                        term,
    //                        job,
    //                        city,
    //                        parent_phone,
    //                        colleague_phone,
    //                        company_phone,
    //                        applicant,
    //                        status
    //                   }
    //    }`,
    //   variables: {
    //     // role: this.role,
    //   }
    // }).then(res => {
    //   console.log(res)
    //   This.tableData = res.data.selectAllApplicant
    // }).catch(error => {
    //   console.log(error)
    // })
  },
  data () {
    return {

      // 显示删除
      dialogVisible: false,
      dialogUpdateFormVisible: false,
      // 显示添加表单
      dialogFormVisible: false,
      formLabelWidth: '120px',
      form: {
        name: '', region: ''
      },
      searchInput: {
        id: '',
        amount: '',
        term: '',
        job: '',
        city: '',
        parent_phone: '',
        colleague_phone: '',
        company_phone: '',
        status: ''
      },
      createFrom: {
        id: '',
        amount: '',
        term: '',
        job: '',
        city: '',
        parent_phone: '',
        colleague_phone: '',
        company_phone: '',
        applicant: ''
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
      tableData: [],
      loading: false,
      pagination: {
        currentPage: 1,
        pageSize: 10,
        total: 0
      }
    }
  }
}
</script>

<style>
</style>

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
                <div style="font-size: 18px;width: 30%;text-align: right;margin-right: 20px;">金额:</div>
                <el-input placeholder="请输入金额" v-model="searchInput.amount" clearable style="width: 80%;">
                </el-input>
              </div>
              <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
                <div style="font-size: 18px;width: 30%;text-align: right;margin-right: 20px;">期数:</div>
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
                <div style="font-size: 18px;width: 45%;text-align: right;margin-right: 20px;">申请人:</div>
                <el-input placeholder="请输入申请人" v-model="searchInput. applicant" clearable style="width: 80%;margin-right: 20px;">
                </el-input>
              </div>

            </div>
          </el-col>
          <el-col :span="8">
            <div style="display: flex;align-items: center;justify-content: left;">
              <el-button type="primary" @click="search()">查询</el-button>
              <el-button type="primary" @click="resetClick()">重置</el-button>
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
              <el-button @click="handleClick(scope.row)" type="text" size="small">风险检测</el-button>
              <el-button type="danger"  size="small" @click="handleUpdateClick(scope.row)">允许贷款</el-button>
            </template>
          </el-table-column>
        </el-table>

        <d2-crud
          :data="tableData"
          :pagination="pagination"
          @pagination-current-change="paginationCurrentChange"/>
      </el-col>

    </el-row>
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
        query: gql`query($id:String,$amount:String,$term:String,$job:String,$city:String,$applicant:String,$currentPage:Int!){
                  selectApplicant(applicant:{
                  id:$id,
                  amount:$amount,
                  term:$term,
                  job:$job,
                  city:$city,
                  applicant:$applicant
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
          term: this.searchInput.term,
          job: this.searchInput.job,
          city: this.searchInput.city,
          status: this.searchInput.status,
          currentPage: this.pagination.currentPage
        }
      }).then(res => {
        console.log(res)
        This.tableData = res.data.selectApplicant.content
      }).catch(error => {
        console.log(error)
      })
    },
    handleClick (row) {
      console.log(row.id)
      this.$router.push({
        path: 'predict-result',
        query: {
          id: row.id
        }
      })
    },
    handleUpdateClick (row) {
      this.$confirm('确认通过风险检测并允许贷款（确认后不可修改）？')
        .then(_ => {
          this.$apollo.mutate({
            // Query
            mutation: gql`mutation($id:String!){
                     updateApplyRiskStatus(id:$id)
                     {
                         code,
                         message
                     }
             }`,
            variables: {
              id: row.id
            }
          }).then(res => {
            if (res.data.updateApplyRiskStatus.code === 0) {
              this.deleteTableData(row.id)
              this.$message({
                message: '通过风险检测',
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
    //  重置
    resetClick () {
      this.refreshTable()
      this.searchInput.id = ''
      this.searchInput.amount = ''
      this.searchInput.term = ''
      this.searchInput.job = ''
      this.searchInput.city = ''
      this.searchInput.status = ''
    },
    refreshTable () {
      let This = this
      this.$apollo.query({
        // Query
        query: gql`query($currentPage:Int!){
               selectThroughInfoTest(currentPage:$currentPage)
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
        This.tableData = res.data.selectThroughInfoTest.content
      }).catch(error => {
        console.log(error)
      })
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
               selectThroughInfoTest(currentPage:$currentPage)
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
        this.tableData = res.data.selectThroughInfoTest.content
        this.pagination.total = res.data.selectThroughInfoTest.totalElements
        this.loading = false
      }).catch(error => {
        console.log(error)
        this.loading = false
      })
    }

  },

  created () {
    this.fetchData()
  },
  data () {
    return {

      // 显示预测结果
      dialogPredictResultFormVisible: false,
      // 显示添加表单
      dialogFormVisible: false,
      formLabelWidth: '80px',
      searchInput: {
        id: '',
        amount: '',
        term: '',
        job: '',
        city: '',
        applicant: ''
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

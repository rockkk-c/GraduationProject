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
                <div style="font-size: 18px;width: 40%;text-align: right;margin-right: 20px;">申请人:</div>
                <el-input placeholder="请输入申请人" v-model="searchInput. applicant" clearable style="width: 80%;margin-right: 20px;">
                </el-input>
              </div>

            </div>
          </el-col>
          <el-col :span="8">
            <div style="display: flex;align-items: center;justify-content: left;">
              <el-button type="primary" @click="search()">查询</el-button>
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
              <el-button @click="handleClick(scope.row)" type="text" size="small">信息检测</el-button>
              <el-button type="danger"  size="small" @click="handleUpdateClick(scope.row)">通过检测</el-button>
            </template>
          </el-table-column>
        </el-table>
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
        query: gql`query($id:String!,$amount:String!,$term:String!,$job:String!,$city:String!,$applicant:String!){
                  selectApplicant(applicant:{
                  id:$id,
                  amount:$amount,
                  term:$term,
                  job:$job,
                  city:$city,
                  applicant:$applicant
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
          status: this.searchInput.status
        }
      }).then(res => {
        console.log(res)
        This.tableData = res.data.selectApplicant
      }).catch(error => {
        console.log(error)
      })
    },
    handleClick (row) {
      console.log(row)
      this.$router.push({
        path: 'detection-result',
        query: {
          id: row.id
        }
      })
    },
    handleUpdateClick (row) {
      this.$confirm('确认通过信息检测（确认后不可修改）？')
        .then(_ => {
          this.$apollo.mutate({
            // Query
            mutation: gql`mutation($id:String!){
                     updateApplyInfoTest(id:$id)
                     {
                         code,
                         message
                     }
             }`,
            variables: {
              id: row.id
            }
          }).then(res => {
            if (res.data.updateApplyInfoTest.code === 0) {
              this.$message({
                message: '通过信息检测',
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
    }
  },
  created () {
    let This = this
    this.$apollo.query({
      query: gql`query{
               allNullStatus
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
        // role: this.role,
      }
    }).then(res => {
      console.log(res)
      This.tableData = res.data.allNullStatus
    }).catch(error => {
      console.log(error)
    })
  },
  data () {
    return {

      // 显示更新
      dialogUpdateFormVisible: false,
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
      tableData: []
    }
  }
}
</script>
<style>
</style>

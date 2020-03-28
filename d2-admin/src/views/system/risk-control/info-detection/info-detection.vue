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
            </template>
          </el-table-column>
        </el-table>
      </el-col>

    </el-row>

    <el-dialog title="虚假信息检测"  :visible.sync="dialogFormVisible" width="30%">
      <el-form label-position="right" label-width="80px" :model="createFrom" :inline="true">

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
    search () {
      let This = this
      this.$apollo.query({
        // Query
        query: gql`query($flag:String!,$number:String!){
                  loadListOfPhone(flag:$flag,number:$number)
                         {
                             number,
                             flag
                         }
         }`,
        variables: {
          number: this.searchInput.number,
          flag: this.searchInput.flag
        }
      }).then(res => {
        console.log(res)
        This.tableData = res.data.loadListOfPhone
      }).catch(error => {
        console.log(error)
      })
    },
    handleClick (row) {
      console.log(row)
      this.$router.push({
        path: 'number-detail',
        query: {
          name: '机主'
        }
      })
    }
  },
  created () {
    let This = this
    this.$apollo.query({
      // Query
      query: gql`query{
               selectAllApplicant
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
      This.tableData = res.data.selectAllApplicant
    }).catch(error => {
      console.log(error)
    })
  },
  data () {
    return {

      // 编辑电话
      updateNumber: '',
      // 编辑状态
      updateState: true,
      // 显示删除
      dialogVisible: false,
      // 显示更新
      dialogUpdateFormVisible: false,
      // 显示添加表单
      dialogFormVisible: false,
      formLabelWidth: '80px',
      form: {
        number: '',
        state: true
      },
      searchInput: {
        number: '',
        flag: ''
      },
      tableData: []
    }
  }
}
</script>
<style>
</style>

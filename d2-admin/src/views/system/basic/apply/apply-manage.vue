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
                <div style="font-size: 18px;width: 40%;text-align: right;margin-right: 20px;">父母电话:</div>
                <el-input placeholder="请输入父母电话" v-model="searchInput. parent_phone" clearable style="width: 80%;margin-right: 20px;">
                </el-input>
              </div>
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
                <div style="font-size: 18px;width: 40%;text-align: right;margin-right: 20px;">申请人:</div>
                <el-input placeholder="请输入申请人" v-model="searchInput. applicant" clearable style="width: 80%;margin-right: 20px;">
                </el-input>
              </div>           <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
              <div style="font-size: 18px;width: 40%;text-align: right;margin-right: 20px;">状态:</div>
              <el-input placeholder="请输入状态" v-model="searchInput. status" clearable style="width: 80%;margin-right: 20px;">
              </el-input>
            </div>

            </div>
          </el-col>
          <el-col :span="8">
            <div style="display: flex;align-items: center;justify-content: left;">
              <el-button type="primary" @click="search()">查询</el-button>
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
              <el-button type="text" size="small" @click="editState(scope.row)">编辑</el-button>
              <el-button @click="handleDeleteClick(scope.row)" type="text" size="small">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
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
                  selectApplicant(applicant:{
                    id:$id,
                    amount:$amount,
                    term:$term,
                    job:$job,
                    city:$city,
                    parent_phone:$parent_phone,
                    colleague_phone:$colleague_phone,
                    company_phone:$company_phone,
                    status:$status
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
          applicant: this.searchInput.applicant,
          status: this.searchInput.status
        }
      }).then(res => {
        console.log(res)
        This.tableData = res.data.selectApplicant
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
        path: 'apply-detail',
        query: {
          name: 'xxx'
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
  createBtn () {
    var myreg = /^[1][3,4,5,7,8][0-9]{9}$/
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

      mutation: gql`mutation($id:String!,$amount:String!,$term:String!,$job:String!,$city:String!,$parent_phone:String!,$colleague_phone:String!,$company_phone:String!,$applicant:String!){
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

      // 显示删除
      dialogVisible: false,

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
        applicant: '',
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
      tableData: []
    }
  }
}
</script>

<style>
</style>

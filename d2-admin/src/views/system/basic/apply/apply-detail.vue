<template>
  <d2-container>
    <h1 @click="test()">查看进件</h1>
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

  </d2-container>

</template>

<script>
import gql from 'graphql-tag'
export default {

  methods: {
    test () {
      let id = this.$route.query.id
      console.log(id)
    },
    created () {
      let This = this
      console.log('xxxxxxxxxxxxxxxxxxxxxxxxxx')
      console.log(this.$route.query.id)
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
    }
  },

  data () {
    return {
      tableData: [],
      id: ''
    }
  }
}
</script>

<style>
</style>

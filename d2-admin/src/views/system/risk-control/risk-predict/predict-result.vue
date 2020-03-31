<template xmlns:font-size="http://www.w3.org/1999/xhtml">
  <d2-container>
    <h1>信息检测结果</h1>
    <h3  >预测结果（0代表高风险，1代表低风险）：{{predictResult}}</h3>
    <h3 v-if="clientBF===1">此客户处于黑名单状态</h3>
    <h3 v-if="clientPhoneBF===1">此客户的手机号处于黑名单状态</h3>
    <h3 align="center" style="color:red"  font-size:50px v-if="overdueDetails!=null">此客户之前的进件逾期详情</h3>
    <el-table :data="overdueDetails" v-if="overdueDetails!=null" border style="width: 100%">
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
    </el-table>
    <h3 align="center" style="color:red"  font-size:50px v-if="OneDimenRelationshipBFDetails!=null">一维关系中触碰黑名单客户</h3>
    <el-table :data="OneDimenRelationshipBFDetails" border v-if="OneDimenRelationshipBFDetails!=null"  style="width: 100%">
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
    </el-table>

    <h3 align="center" style="color:red"  font-size:50px v-if="TwoDimenRelationshipBFDetails!=null">二维关系中触碰黑名单客户</h3>
    <el-table :data="TwoDimenRelationshipBFDetails" border v-if="TwoDimenRelationshipBFDetails!=null" style="width: 100%">
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
    </el-table>
    <h3 align="center" style="color:red"  font-size:50px v-if="OneDimenRelationshipPhoneBFDetails!=null">一维关系中触碰黑名单的电话</h3>
    <el-table :data="OneDimenRelationshipPhoneBFDetails" border v-if="OneDimenRelationshipPhoneBFDetails!=null" style="width: 100%">
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
    </el-table>
    <h3 align="center" style="color:red"  font-size:50px v-if="TwoDimenRelationshipPhoneBFDetals!=null">二维关系中触碰黑名单电话</h3>
    <el-table :data="TwoDimenRelationshipPhoneBFDetals" border v-if="TwoDimenRelationshipPhoneBFDetals!=null" style="width: 100%">
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
    </el-table>
  </d2-container>
</template>

<script>
import gql from 'graphql-tag'

export default {

  methods: {

  },
  created () {
  //  let applyId = this.$route.query.id
    console.log('id:' + this.$route.query.id)
    this.$apollo.query({
      // Query

      query: gql`query($id:String!){
             resultDetails(id:$id)
     }`,
      variables: {
        id: '100007'
      }
    }).then(res => {
      this.predictResult = res.data.resultDetails.predictResult.predictResult
      this.clientBF = res.data.resultDetails.clientBF
      this.clientPhoneBF = res.data.resultDetails.clientPhoneBF
      this.overdueDetails = res.data.resultDetails.overdueDetails
      this.OneDimenRelationshipBFDetails = res.data.resultDetails.OneDimenRelationshipBFDetails
      this.OneDimenRelationshipPhoneBFDetails = res.data.resultDetails.OneDimenRelationshipPhoneBFDetails
      this.TwoDimenRelationshipBFDetails = res.data.resultDetails.TwoDimenRelationshipBFDetails
      this.TwoDimenRelationshipPhoneBFDetals = res.data.resultDetails.TwoDimenRelationshipPhoneBFDetals
      console.log('clientBF:' + this.clientBF)
      console.log('clientPhoneBF:' + this.clientPhoneBF)
    }).catch(error => {
      console.log(error)
    })
  },
  data () {
    return {
      predictResult: '',
      clientBF: '',
      clientPhoneBF: '',
      overdueDetails: [],
      OneDimenRelationshipBFDetails: [],
      OneDimenRelationshipPhoneBFDetails: [],
      TwoDimenRelationshipBFDetails: [],
      TwoDimenRelationshipPhoneBFDetals: []
    }
  }
}
</script>

<style>
</style>

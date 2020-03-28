<template>
  <d2-container>
    <el-row :gutter="20">
      <el-col :span="16">
        <el-row style="margin-bottom: 80px;">
          <el-col :span="24">
            <div class="" style="text-align: center;font-size: 30px;">
              基于用户信息知识图谱的风险控制平台
            </div>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-card shadow="always" style="text-align: center;background-color: #909399;color: #FFFFFF;">
              <div class="text item">
                <d2-count-up :start="0" :end="countOfPerson" style="font-size:38px;" />
              </div>
              <div class="text item" style="font-size: 24px;">客户数量</div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="always" style="text-align: center;background-color: #67C23A;color: #FFFFFF;">
              <div class="text item">
                <d2-count-up :start="0" :end="countOfApplicant" style="font-size:38px;" />
              </div>
              <div class="text item" style="font-size: 24px;">进件数量</div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="always" style="text-align: center;background-color: #F56C6C;color: #FFFFFF;">
              <div class="text item">
                <d2-count-up :start="0" :end="countOfPhone" style="font-size:38px;" />
              </div>
              <div class="text item" style="font-size: 24px;">号码数量</div>
            </el-card>
          </el-col>
        </el-row>

        <el-row style="margin-top: 60px;">
          <el-col :span="24">
            <ve-histogram :data="chartData" :extend="extend"></ve-histogram>
          </el-col>

        </el-row>

      </el-col>

      <el-col :span="8">
        <el-calendar v-model="value" style="height: 200px;">
        </el-calendar>
      </el-col>

    </el-row>
  </d2-container>
</template>

<script>
import gql from 'graphql-tag'
export default {

  created () {
    let This = this
    // eslint-disable-next-line no-unused-expressions
    this.$apollo.query({
      // Query
      query: gql`query{
                   countOfPerson
             }`,
      variables: {
        // role: this.role,
      }
    }).then(res => {
      console.log(res)
      This.countOfPerson = res.data.countOfPerson
    }).catch(error => {
      console.log(error)
      // eslint-disable-next-line no-sequences
    }),
    this.$apollo.query({
      // Query
      query: gql`query{
                   countOfApplicant
             }`,
      variables: {
        // role: this.role,
      }
    }).then(res => {
      console.log(res)
      This.countOfApplicant = res.data.countOfApplicant
    }).catch(error => {
      console.log(error)
    }),
    this.$apollo.query({
      // Query
      query: gql`query{
                   countOfPhone
             }`,
      variables: {
        // role: this.role,
      }
    }).then(res => {
      console.log(res)
      This.countOfPhone = res.data.countOfPhone
    }).catch(error => {
      console.log(error)
    })
  },

  data () {
    return {
      // 客户数量
      countOfPerson: '',
      // 进件数量
      countOfApplicant: '',
      // 号码数量
      countOfPhone: '',
      // chartData
      chartData: {
        columns: ['日期', '信息检测', '风险指数'],
        rows: [
          { '日期': '2019/11/1', '信息检测': 1393, '风险指数': 1093 },
          { '日期': '2019/11/2', '信息检测': 3530, '风险指数': 3230 },
          { '日期': '2019/11/3', '信息检测': 2923, '风险指数': 2623 },
          { '日期': '2019/11/4', '信息检测': 1723, '风险指数': 1423 },
          { '日期': '2019/11/5', '信息检测': 3792, '风险指数': 3492 },
          { '日期': '2019/11/6', '信息检测': 4593, '风险指数': 4293 }
        ]
      }
    }
  }
}

</script>
<style>
</style>

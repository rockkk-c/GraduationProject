<template>
  <d2-container>
    <h1>信息检测结果</h1>
    <h3 >预测结果（1代表风险低，0代表风险高）：{{predictResult}}</h3>

  </d2-container>
</template>

<script>
import gql from 'graphql-tag'
export default {
  methods: {

  },
  created () {
  //  let applyId = this.$route.query.id
    this.$apollo.query({
      // Query

      query: gql`query($applyId:String!){
             resultDetails(applyId:$applyId)
     }`,
      variables: {
        applyId: '100007'
      }
    }).then(res => {
      this.predictResult = res.data.resultDetails.predictResult
      this.clientBF = res.data.resultDetails.clientBF
      this.clientPhoneBF = res.data.resultDetails.clientPhoneBF
      this.overdueDetails = res.data.resultDetails.overdueDetails
      this.OneDimenRelationshipBFDetails = res.data.resultDetails.OneDimenRelationshipBFDetails
      this.OneDimenRelationshipPhoneBFDetails = res.data.resultDetails.OneDimenRelationshipPhoneBFDetails
      this.TwoDimenRelationshipBFDetails = res.data.resultDetails.TwoDimenRelationshipBFDetails
      this.TwoDimenRelationshipPhoneBFDetals = res.data.resultDetails.TwoDimenRelationshipPhoneBFDetals
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

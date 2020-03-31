<template>
  <d2-container>
    <h1>信息检测结果</h1>

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
      this.overdueDetails = res.data.resultDetails.overdueDetails
    }).catch(error => {
      console.log(error)
    })
  },
  data () {
    return {
      overdueDetails: [],



    }
  }
}
</script>

<style>
</style>

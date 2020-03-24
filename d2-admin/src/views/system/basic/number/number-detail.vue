<template>
  <d2-container>
    <h1 @click="test()">查看机主</h1>
  </d2-container>

</template>

<script>
export default {

  methods: {
    test () {
      let s = this.$route.query.name
      console.log(s)
    }
  }
}
</script>

<style>
</style>

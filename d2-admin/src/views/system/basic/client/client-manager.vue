<template>
  <d2-container>
    <el-row>
      <el-col style="margin-bottom: 20px;">
         <el-row :gutter="10" style="display: flex;align-items: center;">
           <el-col :span="2">
             <div style="font-size: 18px;text-align: right;font-weight: bold;">客户搜索:</div>
           </el-col>
           <el-col :span="6">

             <el-input v-model="searchInputValue" placeholder="请输入内容"></el-input>
           </el-col>
           <el-col :span="2">
             <el-button icon="el-icon-search" circle></el-button>
           </el-col>
         </el-row>
      </el-col>
      <hr />
      <el-col style="margin-top: 20px;">
          aaaa
      </el-col>

    </el-row>
  </d2-container>
</template>

<script>
  export default {
    data () {
      return {
        searchInputValue:""
      }
    }
  }
</script>

<style>
</style>

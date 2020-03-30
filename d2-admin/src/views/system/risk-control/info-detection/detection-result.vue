<template>
  <d2-container>
<!--    <h1 @click="test()">查看详情</h1>-->
    <h1>信息检测结果</h1>
    <el-col style="margin-top: 20px;">
      <el-table :data="tableData" border  style="width: 100%">
        <!--        <el-table-column prop="date" label="日期" width="180">
                </el-table-column>
                <el-table-column prop="name" label="姓名" width="180">
                </el-table-column>
                <el-table-column prop="address" label="地址">
                </el-table-column> -->
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
        <el-table-column fixed="right" label="操作">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row)" type="text" size="small">他的进件</el-button>
            <el-button type="text" size="small">编辑</el-button>
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
    handleClick (row) {
      this.$router.push({
        path: 'client-detail',
        query: {
          id: row.id
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
  created () {
    let id = this.$route.query.id
    this.$apollo.query({
      // Query

      query: gql`query($id:String!){
             fakeInfoCheck(id:$id)
                    {
                          id,
                          name,
                          sex,
                          number,
                          flag
                    }
     }`,
      variables: {
        id: id
      }
    }).then(res => {
      this.tableData = res.data.fakeInfoCheck
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
      createFrom: {
        id: '',
        name: '',
        sex: 'MALE',
        phone: '',
        state: true

      },
      searchInput: {
        id: '',
        name: '',
        sex: '',
        phone: '',
        blackList: ''
      },
      tableData: [],
      id: ''
    }
  }
}
</script>

<style>
</style>

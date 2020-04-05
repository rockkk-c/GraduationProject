<template>
  <d2-container>
    <div>

    </div>
    <el-row>
      <el-col style="margin-bottom: 20px;">
        <el-row style="display: flex;align-items: center;">
          <el-col :span="16">
            <div style="display: flex;flex-direction: row;">
              <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
                <div style="font-size: 18px;width: 20%;text-align: right;margin-right: 20px;">号码:</div>
                <el-input placeholder="请输入号码" v-model="searchInput.number" clearable style="width: 80%;">
                </el-input>
              </div>
              <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
                <div style="font-size: 18px;width: 20%;text-align: right;margin-right: 20px;">状态:</div>
                <el-select v-model="searchInput.flag" placeholder="请选择号码状态">
                  <el-option label="白名单" value="WHITE"></el-option>
                  <el-option label="黑名单" value="BLACK"></el-option>
                </el-select>
              </div>

            </div>
          </el-col>
          <el-col :span="8">
            <div style="display: flex;align-items: center;justify-content: left;">
              <el-button type="primary" @click="search()">查询</el-button>
              <el-button type="primary" @click="resetClick()">重置</el-button>
              <el-button @click="createBtnClick()" type="danger" style="margin-left: 20px;">新增号码</el-button>
            </div>
          </el-col>
        </el-row>
      </el-col>
      <hr />
      <el-col style="margin-top: 20px;">

        <el-table :data="tableData" border style="width: 100%">
          <el-table-column type="index" label="序号"></el-table-column>
          <el-table-column prop="number" label="号码">
          </el-table-column>
          <el-table-column label="状态" prop="flag">
            <template slot-scope="scope">
              {{scope.row.flag == 'WHITE'?"白名单":"黑名单"}}
            </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作">
            <template slot-scope="scope">
              <el-button @click="handleClick(scope.row)" type="text" size="small">查看机主</el-button>
              <el-button type="text" size="small" @click="editState(scope.row)">编辑</el-button>
              <el-button @click="handleDeleteClick(scope.row)" type="text" size="small">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <d2-crud
          :data="tableData"
          :pagination="pagination"
          @pagination-current-change="paginationCurrentChange"/>

      </el-col>

    </el-row>

    <el-dialog title="新增号码" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="号码" :label-width="formLabelWidth">
          <el-input v-model="form.number" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item :label-width="formLabelWidth">
          <el-switch style="display: block" v-model="form.state" active-color="#13ce66" inactive-color="#ff4949"
                     active-text="白名单" inactive-text="黑名单">
          </el-switch>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="createBtn()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="状态更改" :visible.sync="dialogUpdateFormVisible">
      <el-form>
        <el-form-item label="号码" :label-width="formLabelWidth">
          <el-input v-model="updateNumber" disabled autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item :label-width="formLabelWidth">
          <el-switch style="display: block" v-model="updateState" active-color="#13ce66" inactive-color="#ff4949"
                     active-text="白名单" inactive-text="黑名单">
          </el-switch>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogUpdateFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateBtn()">确 定</el-button>
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
        query: gql`query($flag:String,$number:String,$currentPage:Int!){
                  loadListOfPhone(flag:$flag,number:$number,currentPage:$currentPage)
                         {
                           content{
                             number,
                             flag
                         },
                          pageable{
                              pageNumber,
                              pageSize
                          },
                          totalElements,
                          totalPages
                         }
         }`,
        variables: {
          number: this.searchInput.number,
          flag: this.searchInput.flag,
          currentPage: this.pagination.currentPage
        }
      }).then(res => {
        console.log(res)
        This.tableData = res.data.loadListOfPhone.content
        this.pagination.total = res.data.loadListOfPhone.totalElements
        this.loading = false
      }).catch(error => {
        console.log(error)
      })
    },
    updateBtn () {
      let This = this
      this.$apollo.mutate({
        // Query
        mutation: gql`mutation($number:String!,$flag:String!){
                    updatePhoneByNumber(number:$number,flag:$flag)
                     {
                         code,
                         message
                     }
           }`,
        variables: {
          number: this.updateNumber,
          flag: this.updateState ? 'WHITE' : 'BLACK'
        }
      }).then(res => {
        if (res.data.updatePhoneByNumber.code === 0) {
          this.$message({
            message: '状态修改成功',
            type: 'success'
          })
          for (let i = 0; i < This.tableData.length; i++) {
            if (This.tableData[i].number === This.updateNumber) {
              // This.tableData[i] = This.updateState;
              break
            }
          }
        }
        this.dialogUpdateFormVisible = false
      }).catch(error => {
        console.log(error)
      })
    },
    createBtn () {
      var myreg = /^[1][3,4,5,7,8][0-9]{9}$/
      if (!myreg.test(this.form.number)) {
        this.$message({
          message: '号码输入格式有误',
          type: 'error'
        })
        this.form.number = ''
        return
      }
      this.$apollo.mutate({
        // Query
        mutation: gql`mutation($number:String!,$flag:String!){
                 addPhone(phone:{
                   number:$number
                   flag:$flag
                 })
                 {
                     code,
                     message
                 }
         }`,
        variables: {
          number: this.form.number,
          flag: this.form.state ? 'WHITE' : 'BLACK'
        }
      }).then(res => {
        if (res.data.addPhone.code === 0) {
          this.$message({
            message: '号码添加成功',
            type: 'success'
          })
        }
      }).catch(error => {
        console.log(error)
      })
      this.dialogFormVisible = false
    },
    createBtnClick () {
      this.dialogFormVisible = true
    },
    handleClick (row) {
      console.log(row)
      this.$router.push({
        path: 'number-detail',
        query: {
          number: row.number
        }
      })
    },
    editState (row) {
      console.log(row)
      this.updateNumber = row.number
      this.updateState = row.flag === 'WHITE'
      this.dialogUpdateFormVisible = true
    },
    handleDeleteClick (row) {
      let This = this
      this.$confirm('确认删除？')
        .then(_ => {
          this.$apollo.mutate({
            // Query
            mutation: gql`mutation($number:String!){
                     deletePhoneByNumber(number:$number)
                     {
                         code,
                         message
                     }
             }`,
            variables: {
              number: row.number
            }
          }).then(res => {
            this.$message({
              message: '删除成功',
              type: 'success'
            })
            for (let i = 0; i < This.tableData.length; i++) {
              if (This.tableData[i].number === row.number) {
                This.tableData.splice(i, 1)
                break
              }
            }
          }).catch(error => {
            console.log(error)
          })
        })
        .catch(_ => {
          console.log('取消')
        })
    },
    //  重置
    resetClick () {
      this.fetchData()
      this.searchInput.number = ''
      this.searchInput.flag = ''
    },
    // refreshTable () {
    //   this.$apollo.query({
    //     // Query
    //     query: gql`query($currentPage:Int!){
    //            selectAllPhone(currentPage:$currentPage)
    //                   {
    //                       content{
    //                          number,
    //                          flag
    //                      },
    //                       pageable{
    //                           pageNumber,
    //                           pageSize
    //                       },
    //                       totalElements,
    //                       totalPages
    //                   }
    //
    //    }`,
    //     variables: {
    //       currentPage: this.pagination.currentPage
    //     }
    //   }).then(res => {
    //     this.tableData = res.data.test.content
    //     this.pagination.total = res.data.selectAllPhone.totalElements
    //     this.loading = false
    //   }).catch(error => {
    //     console.log(error)
    //     this.loading = false
    //   })
    // },
    paginationCurrentChange (currentPage) {
      this.pagination.currentPage = currentPage
      this.fetchData()
    },
    fetchData () {
      this.loading = true
      this.$apollo.query({
        // Query
        query: gql`query($currentPage:Int!){
               selectAllPhone(currentPage:$currentPage)
                      {
                          content{
                             number,
                             flag
                         },
                          pageable{
                              pageNumber,
                              pageSize
                          },
                          totalElements,
                          totalPages
                      }

       }`,
        variables: {
          currentPage: this.pagination.currentPage
        }
      }).then(res => {
        this.tableData = res.data.selectAllPhone.content
        this.pagination.total = res.data.selectAllPhone.totalElements
        this.loading = false
      }).catch(error => {
        console.log(error)
        this.loading = false
      })
    }
  },
  created () {
    this.fetchData()
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
      tableData: [],
      loading: false,
      pagination: {
        currentPage: 1,
        pageSize: 10,
        total: 0
      }

      // 分页 --star

      // 分页--end
    }
  }
}
</script>

<style>
</style>

<template>
  <d2-container>
    <el-row>
      <el-col style="margin-bottom: 20px;">
        <el-row style="display: flex;align-items: center;">
          <el-col :span="16">
            <div style="display: flex;flex-direction: row;">
              <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
                <div style="font-size: 18px;width: 20%;text-align: right;margin-right: 20px;">ID:</div>
                <el-input placeholder="请输入ID" v-model="searchInput.id" clearable style="width: 80%;">
                </el-input>
              </div>
              <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
                <div style="font-size: 18px;width: 20%;text-align: right;margin-right: 20px;">姓名:</div>
                <el-input placeholder="请输入姓名" v-model="searchInput.name" clearable style="width: 80%;">
                </el-input>
              </div>
              <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
                <div style="font-size: 18px;width: 20%;text-align: right;margin-right: 20px;">电话:</div>
                <el-input placeholder="请输入电话" v-model="searchInput.phone" clearable style="width: 80%;">
                </el-input>
              </div>
              <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
                <div style="font-size: 18px;width: 40%;text-align: right;margin-right: 20px;">黑名单:</div>
                <el-input placeholder="请输入黑名单" v-model="searchInput.blackList" clearable style="width: 80%;">
                </el-input>
              </div>
            </div>
          </el-col>
          <el-col :span="8">
            <div style="display: flex;align-items: center;justify-content: left;">
              <el-button type="primary">查询</el-button>
              <el-button @click="createBtnClick()" type="danger" style="margin-left: 40px;">新增进件</el-button>
            </div>
          </el-col>
        </el-row>
      </el-col>
      <hr />
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
              <el-button type="text" size="small">编辑</el-button>
              <el-button @click="handleDeleteClick(scope.row)" type="text" size="small">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>

    </el-row>

    <el-dialog title="收货地址" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="活动名称" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="活动区域" :label-width="formLabelWidth">
          <el-select v-model="form.region" placeholder="请选择活动区域">
            <el-option label="区域一" value="shanghai"></el-option>
            <el-option label="区域二" value="beijing"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
      </div>
    </el-dialog>
  </d2-container>






</template>

<script>
  export default {
    methods: {
      createBtnClick() {
        this.dialogFormVisible = true;
      },
      handleClick(row) {
        console.log(row);
        this.$router.push({
          path: 'client-detail',
          query: {
            name: '张三'
          }
        });
      },
      handleDeleteClick(row) {
        this.$confirm('确认删除？')
          .then(_ => {
            console.log("删除");
          })
          .catch(_ => {
            console.log("取消");
          });
      }

    },
    data() {
      return {

        // 显示删除
        dialogVisible: false,

        // 显示添加表单
        dialogFormVisible: false,
        formLabelWidth: '120px',
        form: {
          name: "",
          region: ""
        },
        searchInput: {
          id: "",
          name: "",
          phone: "",
          blackList: ""
        },
        // tableData: [{
        //   date: '2016-05-02',
        //   name: '王小虎',
        //   address: '上海市普陀区金沙江路 1518 弄',
        // }, {
        //   date: '2016-05-04',
        //   name: '王小虎',
        //   address: '上海市普陀区金沙江路 1518 弄'
        // }, {
        //   date: '2016-05-01',
        //   name: '王小虎',
        //   address: '上海市普陀区金沙江路 1518 弄',
        // }, {
        //   date: '2016-05-03',
        //   name: '王小虎',
        //   address: '上海市普陀区金沙江路 1518 弄'
        // }],
		 tableData: [{
			 id: '2000001',
			 amount: '10000',
			 term: '5',
			 job: '工程师',
			 city: '北京市',
			 parent_phone: '15963265478',
			 colleague_phone: '156323654',
			 company_phone: '15632548745',
			 applicant: '100001',
			 status: 'InProgress'
		}],
      }
    }
  }
</script>

<style>
</style>

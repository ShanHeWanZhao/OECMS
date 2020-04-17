<@override name="title">所有登录信息</@override>
<@override name="data">
    <div id="vm">
        <el-row type="flex" id="searchCondition">
            <el-col :span="4" :offset="3">
                <el-form >
                    <!--下拉选择框-->
                    <el-form-item label="用户类型">
                        <el-select v-model="searchForm.userType" clearable placeholder="请选择">
                            <el-option v-for="item in typeOptions" :key="item.value" :label="item.label" :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
            </el-col>
            <el-col :span="4">
                <el-form >
                    <el-form-item label="用户状态">
                        <el-select v-model="searchForm.userStatus" clearable placeholder="请选择">
                            <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
            </el-col>
            <el-col :span="4">
                <el-form :inline="true">
                    <el-form-item label="用户账号">
                        <el-input v-model="searchForm.accountNumber" placeholder="请输入用户账号" clearable></el-input>
                    </el-form-item>
                </el-form>
            </el-col>
            <el-col :span="4">
                <el-form :inline="true">
                    <el-form-item label="用户姓名">
                        <el-input v-model="searchForm.userName" placeholder="请输入用户姓名" clearable></el-input>
                    </el-form-item>
                </el-form>
            </el-col>
            <el-col :span="4">
                <el-form>
                    <el-form-item>
                        <el-button type="primary" icon="el-icon-search" @click="searchLoginInfo">查询</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
        <el-dialog title="编辑用户" :visible.sync="dialogFormVisible" @close="resetForm" width="35%" :close-on-click-modal="false">
            <el-form v-if="dialogFormVisible" :model="editLoginInfoForm" ref="editLoginInfoForm">
                <!-- 脚本名输入框 -->
                <el-form-item   label="用户姓名" prop="userName" :label-width="formLabelWidth" style="width: 58%;" clearable>
                    <el-input v-model="editLoginInfoForm.userName" auto-complete="off" placeholder="请输入新的用户姓名"></el-input>
                </el-form-item>
                <!-- 所属项目下拉框 -->
                <el-form-item label="用户状态" prop="userStatus" :label-width="formLabelWidth"  clearable>
                    <el-select v-model="editLoginInfoForm.userStatus" placeholder="请选择">
                        <el-option
                                v-for="item in statusOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <!-- 备注框 -->
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="resetForm">重 置</el-button>
                <el-button type="primary" @click="updateLoginInfo">保 存</el-button>
            </div>
        </el-dialog>
        <el-table ref="multipleTable" border fit :data="tableData" highlight-current-row style="width: 100%;font-size: 12px;">
            <el-table-column type="index" width="50" label="行号"></el-table-column>
            <el-table-column prop="userId" label="用户id" v-if='show'></el-table-column>
            <el-table-column prop="userName" sortable label="用户姓名"></el-table-column>
            <el-table-column prop="accountNumber" sortable label="账号"></el-table-column>
            <el-table-column prop="createTime" sortable label="创建时间" :formatter="dateFormat"></el-table-column>
            <el-table-column prop="userStatus" sortable label="用户状态" :formatter="userStatusFormat"></el-table-column>
            <el-table-column prop="userType" sortable label="用户类型" :formatter="userTypeFormat"></el-table-column>
            <el-table-column prop="className" sortable label="用户班级" :formatter="userClassNameFormat"></el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button @click="handleClick(scope.row)" type="text" icon="el-icon-edit">编辑</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-col class="toolbar" style="padding:10px;">
            <el-pagination @current-change="findAll" :current-page="currentPage" :page-size="pageSize" background
                           layout="total, prev, pager, next, jumper" :total="total" style="float:right">
            </el-pagination>
        </el-col>
    </div>
</@override>
<@extends name="../layout.ftl"/>
<script>
    Vue.http.options.emulateJSON = true;
    Vue.http.options.emulateHTTP = true;
    new Vue({
        el: '#vm',
        data() {
            return {
                tableData: [],
                currentPage: 1,
                total: 0,
                pageSize: 7,
                dialogFormVisible: false,
                formLabelWidth: '150px',
                editLoginInfoForm:{
                    userId: null,
                    userName: '',
                    userStatus: 0
                },
                searchForm:{
                    userStatus: null,
                    userType: null,
                    accountNumber: null,
                    userName: null
                },
                statusOptions:[
                    {value: '0', label:'正常'},
                    {value: '1', label:'禁用'}
                ],
                typeOptions:[
                    {value: '0', label:'学生'},
                    {value: '1', label:'教师'}
                ],
            };
        },
        mounted: function () {
            this.findAll();
        },
        methods: {
            // 分页查询
            findAll: function (currentPage) {
                if (!isNaN(currentPage)) {
                    this.currentPage = currentPage;
                }
                var params_ = {
                    pageNum: this.currentPage,
                    pageSize: this.pageSize
                };
                if (this.searchForm.userStatus) {
                    params_['userStatus'] = this.searchForm.userStatus;
                }
                if (this.searchForm.userType) {
                    params_['userType'] = this.searchForm.userType;
                }
                if (this.searchForm.accountNumber) {
                    params_['accountNumber'] = this.searchForm.accountNumber;
                }
                if (this.searchForm.userName) {
                    params_['userName'] = this.searchForm.userName;
                }

                this.$http.get("/admin/list", {
                    params: params_
                }).then(function (response) {
                    this.total = response.data.count;
                    this.tableData = [];
                    for (var key in response.data.data) {
                        this.$set(this.tableData, key, response.data.data[key]);
                    }
                }).catch(function (response) {
                    console.error(response);
                });
            },
            // 按条件搜索
            searchLoginInfo: function(){
                this.findAll(1);
            },
            // 单行数据编辑
            handleClick: function (row) {
                this.editLoginInfoForm.userId = row.userId;
                this.editLoginInfoForm.userName = row.userName;
                // 注意：先把数字转换为String
                this.editLoginInfoForm.userStatus = String(row.userStatus);
                this.dialogFormVisible = true;
            },
            // 重置表单
            resetForm: function(){
                this.$refs['editLoginInfoForm'].resetFields();
            },
            // 更新用户信息
            updateLoginInfo: function(){
                this.$http.post('/admin/updateLoginInfo', this.editLoginInfoForm)
                    .then((response) => {
                        if (response.data.code===0) {
                            this.$message.success(response.data.data);
                            this.findAll(this.currentPage);
                        }else {
                            this.$message.error('更新失败！' + response.data.msg);
                        }
                    });
                this.dialogFormVisible = false;
            },
            // 格式化日期
            dateFormat: function (row) {
                var date = row.createTime;
                if (date == undefined) {
                    return "";
                }
                return moment(date).format("YYYY-MM-DD HH:mm:ss");
            },
            // 格式化用户状态
            userStatusFormat: function (row) {
                switch (row.userStatus) {
                    case 0:
                        return '正常';
                    case 1:
                        return '禁用';
                }
            },
            // 格式化用户类型
            userTypeFormat: function (row) {
                switch (row.userType) {
                    case 0:
                        return '学生';
                    case 1:
                        return '教师';
                }
            },
            // 格式化用户类型
            userClassNameFormat: function (row) {
                if (!row.className){
                    return '教师没有班级';
                }
                return row.className;
            }
        }
    })
</script>
<style>
    #vm {
        position: relative;
        top: 100px;
    }
    #searchCondition {
        position: relative;
        bottom: 50px;
    }
</style>

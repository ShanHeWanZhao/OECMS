<@override name="title">查看实验课程信息</@override>
<@override name="data">
    <div id="vm">
        <div style="width:100%;text-align:center">
        <el-form :inline="true" id="searchCondition" lable-width="150px">
            <!--下拉选择框-->
            <el-form-item label="完成情况">
                <el-select v-model="searchForm.courseTaskStatus" clearable placeholder="请选择">
                    <el-option v-for="item in courseTaskStatusOptions" :key="item.value" :label="item.label" :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="实验名称">
                <el-input v-model="searchForm.expCourseName" placeholder="请输入实验名称" clearable></el-input>
            </el-form-item>
            <el-form-item label="学生姓名">
                <el-input v-model="searchForm.studentName" placeholder="请输入学生姓名" clearable></el-input>
            </el-form-item>
            <el-form-item label="上课班级">
                <el-input v-model="searchForm.className" placeholder="请输入上课班级" clearable></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" @click="searchCourseTask">查询</el-button>
            </el-form-item>
        </el-form>
        </div>
        <#--对话框-->
        <el-dialog title="打分" :visible.sync="dialogFormVisible" @close="resetForm" :close-on-click-modal="false">
            <el-form v-if="dialogFormVisible" :model="editCourseTaskForm" ref="editCourseTaskForm" style="width: 60%;">
                <el-form-item label="实验得分" prop="expCourseGrade" :label-width="formLabelWidth" clearable>
                    <el-input-number v-model="editCourseTaskForm.expCourseGrade"
                                     :precision="2"
                                     :min="0"
                                     :max="100" clearable></el-input-number>
                </el-form-item>
                <!-- 备注框 -->
                <el-form-item  label="实验评语" prop="courseTaskComment" :label-width="formLabelWidth" clearable>
                    <el-input type="textarea" :rows="6"
                              placeholder="请输入实验评语"
                              v-model="editCourseTaskForm.courseTaskComment"
                              maxlength="510"
                              show-word-limit></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="resetForm">重 置</el-button>
                <el-button type="primary" @click="submitResult">保 存</el-button>
            </div>
        </el-dialog>
        <el-table ref="multipleTable" border fit :data="tableData" :row-class-name="tableRowClassName" highlight-current-row style="width: 100%;font-size: 12px;">
            <el-table-column type="index" width="50" label="行号"></el-table-column>
            <el-table-column prop="courseTaskId" label="课程任务ID" v-if='show'></el-table-column>
            <el-table-column prop="expCourseName" label="实验名称"></el-table-column>
            <el-table-column prop="userName" sortable label="学生姓名"></el-table-column>
            <el-table-column prop="className" sortable label="学生班级"></el-table-column>
            <el-table-column prop="courseTaskStatus" sortable label="完成情况" :formatter="expCourseStatusFormat"></el-table-column>
            <el-table-column prop="courseTaskCreateTime" sortable label="任务创建时间"></el-table-column>
            <el-table-column prop="showResult" sortable label="查看结果">
                <template slot-scope="scope">
                    <el-button type="danger"
                               size="small"
                               v-else
                               v-if='scope.row.courseTaskStatus < 2'
                               disabled>待 提 交</el-button>
                    <el-button @click="showResultData(scope.row)"
                               type="success"
                               size="small"
                               icon="el-icon-search"
                               v-else>查 看</el-button>
                </template>
            </el-table-column>
            <el-table-column prop="expCourseGrade" label="得分情况" :formatter="expCourseGradeFormat"></el-table-column>
            <el-table-column prop="courseTaskComment" label="评语" :formatter="courseTaskCommentFormat"></el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button @click="handleClick(scope.row)"
                               type="warning"
                               icon="el-icon-edit"
                               size="small"
                               v-if='scope.row.courseTaskStatus > 1 && scope.row.courseTaskStatus < 3'>打 分</el-button>
                    <el-button type="success"
                               icon="el-icon-check"
                               v-else-if='scope.row.courseTaskStatus == 3'
                               size="small"
                               disabled>完 成</el-button>
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
                formLabelWidth: '100px',
                editCourseTaskForm:{
                    courseTaskComment:'',
                    expCourseGrade: 0,
                    courseTaskId: null,
                },
                searchForm:{
                    courseTaskStatus: null,
                    expCourseName: '',
                    studentName: '',
                    className: '',
                    teacherId: "${loginInfo.userId}"
                },
                courseTaskStatusOptions:[
                    {value: '0', label:'未开始'},
                    {value: '1', label:'进行中'},
                    {value: '2', label:'已提交'},
                    {value: '3', label:'已完成'}
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
                    pageSize: this.pageSize,
                    teacherId: this.searchForm.teacherId
                };
                if (this.searchForm.courseTaskStatus) {
                    params_['courseTaskStatus'] = this.searchForm.courseTaskStatus;
                }
                if (this.searchForm.expCourseName) {
                    params_['expCourseName'] = this.searchForm.expCourseName;
                }
                if (this.searchForm.studentName) {
                    params_['studentName'] = this.searchForm.studentName;
                }
                if (this.searchForm.className) {
                    params_['className'] = this.searchForm.className;
                }

                this.$http.post("/teacher/listCourseTask",  params_).then(function (response) {
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
            searchCourseTask: function(){
                this.findAll(1);
            },
            // 学生查看实验结果
            showResultData: function(row){
                window.open("/student/show?expCourseResultData="+row.expCourseResultData, "_blank")
                console.log(row);
            },
            // 单行数据编辑
            handleClick: function (row) {
                this.editCourseTaskForm.courseTaskComment = row.courseTaskComment;
                this.editCourseTaskForm.expCourseGrade = row.expCourseGrade;
                this.editCourseTaskForm.courseTaskId = row.courseTaskId;
                this.dialogFormVisible = true;
            },
            // 重置表单
            resetForm: function(){
                this.$refs['editCourseTaskForm'].resetFields();
            },
            // 提交打分
            submitResult: function(){
                this.$http.post('/teacher/submitResult', this.editCourseTaskForm)
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
            // 格式化课程状态
            expCourseStatusFormat: function (row) {
                switch (row.courseTaskStatus) {
                    case 0:
                        return '未开始';
                    case 1:
                        return '进行中';
                    case 2:
                        return '已提交';
                    case 3:
                        return '已完成';
                }
            },
            // 格式化 实验得分
            expCourseGradeFormat: function (row) {
                if (!row.expCourseGrade){
                    return '未打分';
                }
                return row.expCourseGrade;
            },
            // 格式化 实验评语
            courseTaskCommentFormat: function (row) {
                if (!row.courseTaskComment){
                    return '待评价';
                }
                return row.courseTaskComment;
            },
            tableRowClassName({row, rowIndex}) {
                switch (row.courseTaskStatus) {
                    case 0:
                        return 'notbegin-row';
                    case 1:
                        return 'process-row';
                    case 2:
                        return 'submit-row';
                    case 3:
                        return 'success-row';
                }
                return '';
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
    .inlineDisplay{
        display: inline;
    }
</style>

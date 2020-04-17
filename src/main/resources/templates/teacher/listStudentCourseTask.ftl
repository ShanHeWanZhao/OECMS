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

        <el-dialog title="打分" :visible.sync="dialogFormVisible" @close="resetForm" :close-on-click-modal="false">
            <el-form v-if="dialogFormVisible" :model="editExpCourseForm" ref="editExpCourseForm" style="width: 60%;">
                <el-form-item label="完成情况" prop="courseTaskStatus" :label-width="formLabelWidth" clearable>
                    <el-select v-model="editExpCourseForm.courseTaskStatus" placeholder="请选择">
                        <el-option
                                v-for="item in courseTaskStatusOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <!-- 备注框 -->
                <el-form-item  label="评语" prop="expCourseDescription" :label-width="formLabelWidth" clearable>
                    <el-input type="textarea" :rows="4" placeholder="请输入备注信息" v-model="editExpCourseForm.expCourseDescription"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="resetForm">重 置</el-button>
                <el-button type="primary" @click="updateExpCourse">保 存</el-button>
            </div>
        </el-dialog>
        <el-table ref="multipleTable" border fit :data="tableData" highlight-current-row style="width: 100%;font-size: 12px;">
            <el-table-column type="index" width="50" label="行号"></el-table-column>
            <el-table-column prop="expCourseName" label="实验名称"></el-table-column>
            <el-table-column prop="studentName" sortable label="学生姓名"></el-table-column>
            <el-table-column prop="className" sortable label="学生班级"></el-table-column>
            <el-table-column prop="courseTaskStatus" sortable label="完成情况" :formatter="expCourseStatusFormat"></el-table-column>
            <el-table-column prop="courseTaskCreateTime" sortable label="任务创建时间"></el-table-column>
            <el-table-column prop="showResult" sortable label="查看结果">
                <template slot-scope="scope">
                    <el-button @click="showResultData(scope.row)" type="primary" icon="el-icon-search">查 看</el-button>
                </template>
            </el-table-column>
            <el-table-column prop="expCourseGrade" label="得分情况"></el-table-column>
            <el-table-column prop="courseTaskComment" label="评语"></el-table-column>
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
                formLabelWidth: '100px',
                editExpCourseForm:{
                    expCourseName: null,
                    studentName: '',
                    courseTaskStatus: 0,
                    expCourseDescription:'',
                    expCourseTime: null
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
                    console.log(response);
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
            // 查看讲义内容
            showTeachMaterial: function(row){
                window.open("/teacher/show?expCourseMaterial="+row.expCourseMaterial, "_blank")
                console.log(row);
            },
            // 单行数据编辑
            handleClick: function (row) {
                this.editExpCourseForm.expCourseName = row.expCourseName;
                this.editExpCourseForm.studentName = row.studentName;
                this.editExpCourseForm.studentName = row.studentName;
                // 注意：先把数字转换为String
                this.editExpCourseForm.courseTaskStatus = String(row.courseTaskStatus);
                this.editExpCourseForm.expCourseTime = row.expCourseTime;
                this.editExpCourseForm.expCourseDescription = row.expCourseDescription;
                this.dialogFormVisible = true;
            },
            // 重置表单
            resetForm: function(){
                this.$refs['editExpCourseForm'].resetFields();
            },
            // 更新实验课程信息
            updateExpCourse: function(){
                this.$http.post('/teacher/updateExpCourse', this.editExpCourseForm)
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

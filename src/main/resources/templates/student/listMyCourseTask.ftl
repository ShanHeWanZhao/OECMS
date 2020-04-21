<@override name="title">查看实验课程任务信息</@override>
<@override name="data">
    <div id="vm">
        <div style="width:100%;text-align:center">
            <el-form :inline="true" id="searchCondition" lable-width="150px">
            <!--下拉选择框-->
            <el-form-item label="实验名称">
                <el-input v-model="searchForm.expCourseName" placeholder="请输入实验名称" clearable></el-input>
            </el-form-item>
            <el-form-item label="实验地点">
                <el-input v-model="searchForm.expCourseLocation" placeholder="请输入实验地点" clearable></el-input>
            </el-form-item>
            <el-form-item label="上课时间">
                <el-date-picker
                        size="medium"
                        style="width:150px;"
                        v-model="searchForm.startTime"
                        type="date"
                        auto-complete="off"
                        value-format="yyyy-MM-dd"
                        clearable>
                </el-date-picker>
            </el-form-item>
            <label class="el-form-item__label">—</label>
            <el-form-item label="" lable-width="300px">
                <el-date-picker
                        size="medium"
                        style="width:150px;"
                        v-model="searchForm.endTime"
                        type="date"
                        auto-complete="off"
                        value-format="yyyy-MM-dd"
                        clearable>
                </el-date-picker>
            </el-form-item>
            <el-form-item label="任课老师">
                <el-input v-model="searchForm.teacherName" placeholder="请输入实验地点" clearable></el-input>
            </el-form-item>
            <el-form-item label="完成情况">
                <el-select v-model="searchForm.courseTaskStatus" clearable placeholder="请选择">
                    <el-option v-for="item in courseTaskStatusOptions" :key="item.value" :label="item.label" :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" @click="searchCourseTask">查询</el-button>
            </el-form-item>
        </el-form>
        </div>

        <el-table ref="multipleTable" border fit :data="tableData" highlight-current-row style="width: 100%;font-size: 12px;">
            <el-table-column type="index" width="50" label="行号"></el-table-column>
            <el-table-column prop="courseTaskId" label="课程任务ID" v-if='show'></el-table-column>
            <el-table-column prop="materialUploadCount" label="讲义上传次数" v-if='show'></el-table-column>
            <el-table-column prop="expCourseName" label="实验名称"></el-table-column>
            <el-table-column prop="expCourseLocation" label="实验地点"></el-table-column>
            <el-table-column prop="expCourseTime" sortable label="上课时间"></el-table-column>
            <el-table-column prop="teacherName" label="任课老师"></el-table-column>
            <el-table-column prop="showMaterials" label="查看讲义">
                <template slot-scope="scope">
                    <el-button @click="showTeachMaterial(scope.row)"
                               type="primary"
                               v-if='scope.row.materialUploadCount > 0'
                               icon="el-icon-search">查看</el-button>
                    <el-button type="warning"
                               v-else disabled>老师还未上传讲义</el-button>
                </template>
            </el-table-column>
            <el-table-column prop="expCourseStatus" label="实验课程状态" :formatter="expCourseStatusFormat"></el-table-column>
            <el-table-column prop="courseTaskStatus" sortable label="我的完成情况" :formatter="courseTaskStatusFormat"></el-table-column>
            <el-table-column prop="resultDataUploadCount" sortable label="上传次数（最多上传3次）"></el-table-column>
            <el-table-column prop="showResult" sortable label="上传/查看结果">
                <template slot-scope="scope">
<#--                    实验课已取消-->
                    <el-button type="info"
                               v-if='scope.row.expCourseStatus == 2'
                               disabled>实验已取消</el-button>
<#--                    任务状态为 未开始，什么都不允许-->
                    <el-button type="danger"
                               v-if='scope.row.courseTaskStatus == 0 && scope.row.expCourseStatus == 0'
                               disabled>实验未开始</el-button>
<#--                    任务状态为进行中 且 上传次数小于三次 且 实验课程状态为进行中 才允许上传-->
                    <el-upload
                            class="inlineDisplay"
                            action="/student/uploadResultData"
                            name="multipartFile"
                            accept="application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/msword"
                            :data="{courseTaskId:scope.row.courseTaskId}"
                            :show-file-list="false"
                            :on-progress="onProgress"
                            :before-upload="beforeUpload"
                            :on-success="handleSuccess">
                         <el-button type="primary"
                                    size="small"
                                   v-if='scope.row.courseTaskStatus > 0
                                   && scope.row.courseTaskStatus < 3
                                   && scope.row.resultDataUploadCount < 3
                                   && scope.row.expCourseStatus == 1'
                                   icon="el-icon-upload">上传</el-button>
                    </el-upload>
<#--                    有上传次数才允许查看-->
                    <el-button @click="showResultData(scope.row)"
                               type="success"
                               size="small"
                               v-if='scope.row.resultDataUploadCount > 0'
                               icon="el-icon-search"
                               v-else>查看</el-button>
                </template>
            </el-table-column>
            <el-table-column prop="expCourseGrade" label="得分情况" :formatter="expCourseGradeFormat"></el-table-column>
            <el-table-column prop="courseTaskComment" label="评语" :formatter="courseTaskCommentFormat"></el-table-column>
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
                    startTime: null,
                    endTime: null,
                    courseTaskStatus: null,
                    expCourseName: '',
                    teacherName: '',
                    studentId: "${loginInfo.userId}"
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
                let params_ = {
                    pageNum: this.currentPage,
                    pageSize: this.pageSize,
                    studentId: this.searchForm.studentId
                };
                if (this.searchForm.startTime) {
                    params_['startTime'] = this.searchForm.startTime;
                }
                if (this.searchForm.endTime) {
                    params_['endTime'] = this.searchForm.endTime;
                }
                if (this.searchForm.expCourseName) {
                    params_['expCourseName'] = this.searchForm.expCourseName;
                }
                if (this.searchForm.courseTaskStatus) {
                    params_['courseTaskStatus'] = this.searchForm.courseTaskStatus;
                }
                if (this.searchForm.teacherName) {
                    params_['teacherName'] = this.searchForm.teacherName;
                }
                if (this.searchForm.expCourseLocation) {
                    params_['expCourseLocation'] = this.searchForm.expCourseLocation;
                }
                this.$http.post("/student/listCourseTask",  params_).then(function (response) {
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
            // 查看讲义内容
            showTeachMaterial: function(row){
                window.open("/teacher/show?expCourseMaterial="+row.expCourseMaterial, "_blank")
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
            // 格式化课程任务状态
            courseTaskStatusFormat: function (row) {
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
            // 格式化实验课程状态
            expCourseStatusFormat: function (row) {
                switch (row.expCourseStatus) {
                    case 0:
                        return '未开始';
                    case 1:
                        return '进行中';
                    case 2:
                        return '与取消';
                    case 3:
                        return '已结束';
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
                    return '老师还未评价';
                }
                return row.courseTaskComment;
            },
            // 文件上传前的判断（类型和大小约束）
            beforeUpload(file) {
                console.log('beforeUpload');
                console.log(file.type);
                const fileSize = file.size;
                console.log(fileSize);
                const isText =(file.type === 'application/msword');
                const isTextComputer = file.type === ('application/vnd.openxmlformats-officedocument.wordprocessingml.document');
                if (fileSize >= 5*1024*1024){
                    this.$message.error("文件大小不能不超过5M");
                }
                if (!isText && !isTextComputer){
                    this.$message.error("只能上传doc和docx格式的文件，且大小不超过5M");
                }
                return isText || isTextComputer || (fileSize < 5*1024*1024);
            },
            uploadLoading: function(isStop){
                const loading = this.$loading({
                    lock: true,
                    text: '上传中，请等待',
                    spinner: 'el-icon-loading',
                    background: 'rgba(240,245,254,0.7)'
                });
                if (isStop){
                    loading.close();
                }
            },
            onProgress(event, file, fileList){
                this.uploadLoading(false);
            },
            // 文件上传成功
            handleSuccess(response, file, fileList) {
                this.uploadLoading(true);
                if (response.code === 0){
                    // 上传成功，刷新页面
                    this.$message.success(response.data);
                    this.findAll(this.currentPage);
                }else {
                    this.$message.error(response.msg);
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

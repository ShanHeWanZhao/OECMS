<@override name="title">查看实验课程信息</@override>
<@override name="data">
    <div id="vm">
        <div style="width:100%;text-align:center">
        <el-form :inline="true" id="searchCondition" lable-width="150px">
            <!--下拉选择框-->
            <el-form-item label="课程状态">
                <el-select v-model="searchForm.expCourseStatus" clearable placeholder="请选择">
                    <el-option v-for="item in classStatusOptions" :key="item.value" :label="item.label" :value="item.value">
                    </el-option>
                </el-select>
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
            <el-form-item label="上课班级">
                <el-input v-model="searchForm.className" placeholder="请输入班级名称" clearable></el-input>
            </el-form-item>
            <el-form-item label="实验地点">
                <el-input v-model="searchForm.expCourseLocation" placeholder="请输入实验地点" clearable></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" @click="searchExpCourse">查询</el-button>
            </el-form-item>
        </el-form>
        </div>

        <el-dialog title="编辑课程" :visible.sync="dialogFormVisible" @close="resetForm" :close-on-click-modal="false">
            <el-form v-if="dialogFormVisible"  :model="editExpCourseForm" ref="editExpCourseForm" style="width: 60%;">
                <el-form-item   label="实验课程名称" prop="expCourseName" :label-width="formLabelWidth">
                    <el-input v-model="editExpCourseForm.expCourseName" placeholder="请输入新的实验名称" clearable></el-input>
                </el-form-item>
                <el-form-item   label="实验地点" prop="expCourseLocation" :label-width="formLabelWidth">
                    <el-input v-model="editExpCourseForm.expCourseLocation"  placeholder="请输入新的实验地点" clearable></el-input>
                </el-form-item>
                <el-form-item   label="上课时间" prop="expCourseTime" :label-width="formLabelWidth"  clearable>
                    <el-date-picker
                            v-model="editExpCourseForm.expCourseTime"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            type="datetime"
                            placeholder="请选择上课时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="课程状态" prop="expCourseStatus" :label-width="formLabelWidth" clearable>
                    <el-select v-model="editExpCourseForm.expCourseStatus" placeholder="请选择">
                        <el-option
                                v-for="item in classStatusOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                                :disabled="item.disabled">
                        </el-option>
                    </el-select>
                </el-form-item>
                <!-- 备注框 -->
                <el-form-item  label="课程简单描述" prop="expCourseDescription" :label-width="formLabelWidth" clearable>
                <el-input type="textarea"
                          :rows="5"
                          placeholder="请对该课程进行简单地描述！"
                          v-model="editExpCourseForm.expCourseDescription"
                          maxlength="510"
                          show-word-limit></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="resetForm">重 置</el-button>
                <el-button type="primary" @click="updateExpCourse">保 存</el-button>
            </div>
        </el-dialog>
        <el-table ref="multipleTable" border fit :data="tableData" highlight-current-row style="width: 100%;font-size: 12px;">
            <el-table-column type="index" width="50" label="行号"></el-table-column>
            <el-table-column prop="expCourseId" label="实验课程ID" v-if='show'></el-table-column>
            <el-table-column prop="expCourseName" sortable label="实验名称"></el-table-column>
            <el-table-column prop="expCourseLocation" width="100" sortable label="实验地点"></el-table-column>
            <el-table-column prop="expCourseTime" sortable label="上课时间"></el-table-column>
            <el-table-column prop="className" sortable label="上课班级" ></el-table-column>
            <el-table-column prop="expCourseStatus" sortable label="课程状态" :formatter="expCourseStatusFormat"></el-table-column>
            <el-table-column prop="materialUploadCount" label="上传次数（最多上传3次）"></el-table-column>
            <el-table-column prop="userClassName" sortable label="上传/查看实验讲义">
                <template slot-scope="scope" >
                    <el-row>
                        <el-upload
                                class="inlineDisplay"
                                action="/teacher/uploadTeachMaterials"
                                name="multipartFile"
                                accept="application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/msword"
                                :data="{expCourseId:scope.row.expCourseId}"
                                :show-file-list="false"
                                :on-progress="onProgress"
                                :before-upload="beforeUpload"
                                :on-success="handleSuccess">
                            <el-button  type="primary"
                                        size="small"
                                        v-if='scope.row.materialUploadCount < 3'>上传</el-button>
                        </el-upload>
                        <el-button @click="showTeachMaterial(scope.row)"
                                   type="success"
                                   size="small"
                                   v-if='scope.row.materialUploadCount > 0'>查看</el-button>
                        </el-row>
                </template>
            </el-table-column>
            <el-table-column prop="expCourseCreateTime" sortable label="创建时间" ></el-table-column>
            <el-table-column prop="expCourseDescription" sortable label="简单描述" ></el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button @click="handleClick(scope.row)" type="text" icon="el-icon-edit">修改</el-button>
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
                formLabelWidth: '125px',
                editExpCourseForm:{
                    expCourseId: null,
                    expCourseName: '',
                    expCourseLocation: '',
                    expCourseStatus: 0,
                    expCourseDescription:'',
                    expCourseTime: null
                },
                searchForm:{
                    startTime: null,
                    endTime: null,
                    expCourseStatus: null,
                    className: null,
                    expCourseLocation: null,
                    teacherId: "${loginInfo.userId}"
                },
                classStatusOptions:[
                    {value: '0', label:'未开始', disabled: true},
                    {value: '1', label:'进行中'},
                    {value: '2', label:'已取消'},
                    {value: '3', label:'已结束'}
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
                if (this.searchForm.startTime) {
                    params_['startTime'] = this.searchForm.startTime;
                }
                if (this.searchForm.endTime) {
                    params_['endTime'] = this.searchForm.endTime;
                }
                if (this.searchForm.expCourseStatus) {
                    params_['expCourseStatus'] = this.searchForm.expCourseStatus;
                }
                if (this.searchForm.className) {
                    params_['className'] = this.searchForm.className;
                }
                if (this.searchForm.expCourseLocation) {
                    params_['expCourseLocation'] = this.searchForm.expCourseLocation;
                }

                this.$http.post("/teacher/list",  params_).then(function (response) {
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
            searchExpCourse: function(){
                this.findAll(1);
            },
            // 查看讲义内容
            showTeachMaterial: function(row){
                window.open("/teacher/show?expCourseMaterial="+row.expCourseMaterial, "_blank")
                console.log(row);
            },
            // 单行数据编辑
            handleClick: function (row) {
                this.editExpCourseForm.expCourseId = row.expCourseId;
                this.editExpCourseForm.expCourseName = row.expCourseName;
                this.editExpCourseForm.expCourseLocation = row.expCourseLocation;
                // 注意：先把数字转换为String
                this.editExpCourseForm.expCourseStatus = String(row.expCourseStatus);
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
        top: 30px;
    }
    #searchCondition {
        position: relative;
        bottom: 10px;
    }
    .inlineDisplay{
        display: inline;
    }
</style>

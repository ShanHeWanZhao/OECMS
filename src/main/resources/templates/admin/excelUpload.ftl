<@override name="title">excel文件上传</@override>
<@override name="data">
<div id="vm">
    <el-upload
            action="/excel/upload"
            multiple
            drag
            :limit="5"
            :before-upload="beforeUpload"
            :on-exceed="handleExceed">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">只能上传xls或xlsx文件</div>
    </el-upload>
</div>
</@override>
<@extends name="../layout.ftl"/>
<script>
    Vue.http.options.emulateJSON = true;
    Vue.http.options.emulateHTTP = true;
    const vm = new Vue({
        el: '#vm',
        data() {
            return {
            }
        },
        methods:{
            beforeUpload(file) {
                console.log('beforeUpload');
                console.log(file.type);
                const isText =(file.type === 'application/vnd.ms-excel');
                const isTextComputer = file.type === ('application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');
                return (isText || isTextComputer);
            },
            handleExceed(files, fileList) {
                this.$message.warning('限制上传 5 个文件!!');
            }
        }
    });
</script>
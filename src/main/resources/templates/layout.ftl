<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title class="title"><@block name="title">光学实验课程管理系统</@block></title>
    <link rel="stylesheet" href="/css/element-ui.css"/>
    <link rel="stylesheet" href="/css/common.css" />
    <link rel="icon" type="image/x-icon" href="/static/favicon.ico">
    <script src="/js/vue.js"></script>
    <script src="/js/jquery-3.2.1.min.js"></script>
    <script src="/js/element-ui.js"></script>
    <script src="/js/vue-resource.js"></script>
    <script src="/js/moment.min.js"></script>
<body style="margin: 0px;" id="layout_body">
<div id="menu">
    <el-menu
            :default-active="activeIndex"
            mode="horizontal"
            @select="handleSelect"
            background-color="#7A5C73"
            text-color="#fff"
            active-text-color="#00000">
        <el-menu-item index="/loginInfo/success">首页</el-menu-item>
        <#if loginInfo.userType == 0>
            <el-submenu index="/huodong">
                <template slot="title">上课日常活动</template>
                    <el-menu-item index="/zoushen">走神</el-menu-item>
                    <el-menu-item index="/listen">听歌</el-menu-item>
                    <el-menu-item index="/playGames">玩游戏</el-menu-item>
            </el-submenu>
            <el-menu-item index="/homework">交作业</el-menu-item>
            <el-menu-item index="/expCourse">上实验课</el-menu-item>
        </#if>
        <#if loginInfo.userType == 1>
            <el-submenu index="/class">
                <template slot="title">课堂</template>
                <el-menu-item index="/teach">讲课</el-menu-item>
                <el-menu-item index="/assignHomework">布置作业</el-menu-item>
            </el-submenu>
            <el-menu-item index="/handleHomework">处理作业</el-menu-item>
            <el-menu-item index="/seeExpCourse">查看实验课</el-menu-item>
        </#if>
        <#if loginInfo.userType == 2>
            <el-submenu index="/manage">
                <template slot="title">管理大家</template>
                <el-menu-item index="/checkUser">查看用户</el-menu-item>
            </el-submenu>
            <el-menu-item index="/excel/index">发布账号</el-menu-item>
            <el-menu-item index="/uploadCourse">上传课程</el-menu-item>
        </#if>
        <el-submenu index="logout" style="float:right;margin-right:1%">
            <template slot="title"><#if loginInfo??>${loginInfo.userName}</#if></template>
            <el-menu-item index="/modifyPassword">
                <el-button type="text" @click="dialogFormVisible = true" style="color: white">修改密码</el-button>
            </el-menu-item>
            <el-menu-item index="/logout">退出</el-menu-item>
        </el-submenu>
    </el-menu>
    <el-dialog title="修改密码（提示：密码修改成功后，将会重新登录）" :visible.sync="dialogFormVisible" width="30%" :close-on-click-modal="false">
        <el-form v-if="dialogFormVisible" :model="modifyPasswordForm" :rules="rules" ref="passwordForm">
            <el-form-item  label="旧密码" prop="oldPassword" :label-width="formLabelWidth" style="width: 70%">
                <el-input type="password" v-model="modifyPasswordForm.oldPassword" auto-complete="off" placeholder="请输入旧密码"></el-input>
            </el-form-item>
            <el-form-item v-if="visible" label="新密码" prop="newPassword" :label-width="formLabelWidth" style="width: 70%">
                <el-input type="password" v-model="modifyPasswordForm.newPassword" placeholder="请输入新密码">
                    <i slot="suffix" title="显示密码" @click="changePass('show')" style="cursor:pointer;"
                       class="el-icon-circle-plus-outline"></i>
                </el-input>
            </el-form-item>
            <el-form-item v-else label="新密码" label="新密码" prop="newPassword" :label-width="formLabelWidth" style="width: 70%">
                <el-input type="text" v-model="modifyPasswordForm.newPassword" placeholder="请输入新密码">
                    <i slot="suffix" title="隐藏密码" @click="changePass('hide')" style="cursor:pointer;"
                       class="el-icon-remove-outline"></i>
                </el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="resetForm">重 置</el-button>
            <el-button type="primary" @click="updatePassword">保 存</el-button>
        </div>
    </el-dialog>
</div>
<@block name="data"></@block>
</body>
<script>
    Vue.http.options.emulateJSON = true;
    Vue.http.options.emulateHTTP = true;
    const Main = {
        data() {
            return {
                activeIndex: window.location.pathname,
                dialogFormVisible: false,
                visible: true,
                formLabelWidth: '160px',
                modifyPasswordForm:{
                    userId: '${loginInfo.userId}',
                    oldPassword: '',
                    newPassword: ''
                },
                rules: {
                    oldPassword: [
                        // trigger: change -> 修改就触发; blur ->焦点移开触发
                        { required: true, message: "请输入旧密码", trigger: "change" },
                        // 自定义校验
                       // { validator: checkOldPassword, trigger: "blur" }
                    ],
                    newPassword: [
                        { required: true, message: "请输入新密码", trigger: "change" },
                        { min: 8, message: "密码长度不小于8个字符", trigger: "change" },
                        { pattern: "^[A-Za-z0-9]+$", message: '密码只能由数字，英文组成' },
                       // { validator: checkNewPassword, trigger: "change" }
                    ]
                },
            };
        },
        methods: {
            handleSelect(key, keyPath) {
                if (key !== "/modifyPassword"){
                    location.href=key;
                }
            },
            // 密码可见
            changePass(value) {
                this.visible = !(value === 'show');
            },
            // 表单重置
            resetForm(){
                    this.modifyPasswordForm.oldPassword = '';
                    this.modifyPasswordForm.newPassword = ''
            },
            // 确认修改密码
            updatePassword () {
                this.$refs["passwordForm"].validate((valid) => {
                    if (valid) {
                        this.$http.post('/modifyPassword', this.modifyPasswordForm)
                            .then((response) => {
                                if (response.data.code===0) {
                                    this.$message.success('修改成功！');
                                    location.href = response.data.data;
                                }else {
                                    this.$message.error('修改失败！' + response.data.msg);
                                }
                            });
                    } else {
                        this.$message.error('请正确填写表单！！');
                        return false;
                    }
                    });
                this.dialogFormVisible = false;
            }
        }
    };
    const Ctor = Vue.extend(Main);
    new Ctor().$mount('#menu');
</script>
</html>
<style>
    .el-dialog__title{
        line-height:28px;
        font-size:20px;
        color: #ffbd16;
    }
    .el-menu-item.is-active {
        background-color: #bc9cb5 !important;
    }
    /*.el-aside {*/
    /*    color: #f64117;*/
    /*}*/
</style>
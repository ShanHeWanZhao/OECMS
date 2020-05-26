<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title class="title">登录页面</title>
    <link rel="stylesheet" href="/css/element-ui.css"/>
    <link rel="stylesheet" href="/css/common.css" />
    <link rel="icon" type="image/x-icon" href="/static/favicon.ico">
    <script src="/js/vue.js"></script>
    <script src="/js/element-ui.js"></script>
    <script src="/js/vue-resource.js"></script>
</head>
<body style="margin: 0px;" class="login">
    <div id="vm">
        <div class="login-wrap" >
            <el-row type="flex" justify="center">
                <el-form ref="loginForm" :model="user" label-width="50px" label-position="left" @keyup.enter.native="doLogin">
                    <h5>欢迎访问实验课程管理系统</h5>
                    <el-form-item prop="accountNum" label-width="80px" label="账 号" class="item">
                        <el-input v-model="user.accountNum" placeholder="请输入账号" style="width: 250px"></el-input>
                    </el-form-item>
                    <el-form-item prop="password" label="密 码" label-width="80px" class="item">
                        <el-input type="password" v-model="user.password" placeholder="请输入密码" style="width: 250px"></el-input>
                    </el-form-item>
                    <template>
                        <el-radio-group v-model="user.type">
                            <el-radio :label="0">学生</el-radio>
                            <el-radio :label="1">教师</el-radio>
                            <el-radio :label="2">管理员</el-radio>
                        </el-radio-group>
                    </template>
                    <el-row :gutter="50">
                        <el-col :span="8">
                            <el-form-item>
                                <el-button type="primary" plain round icon="el-icon-upload2" @click="doLogin">登 录</el-button>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item>
                                <el-button type="primary" round plain icon="el-icon-refresh" @click="resetForm('loginForm')">重 置</el-button>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
            </el-row>
        </div>
    </div>
</body>
</html>
<script>
    Vue.http.options.emulateJSON = true;
    Vue.http.options.emulateHTTP = true;
    new Vue({
        el: '#vm',
        data() {
            return {
                user: {
                    accountNum: "",
                    password: "",
                    type: 0
                }
            };
        },
        methods: {
            doLogin() {
                if (!this.user.accountNum) {
                    this.$message.error("请输入账号！");
                    return;
                } else if (!this.user.password) {
                    this.$message.error("请输入密码！");
                    return;
                }
                this.$http.post('/login', this.user)
                    .then((response) => {
                        // success callback
                        if (response.data.code === 0){
                            // 登陆成功，页面跳转
                            window.location = response.data.data;
                        }else{
                            this.$message.error(response.data.msg);
                        }
                    });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        }
    })
</script>
<style scoped>
    .login {
        background: url("../static/img/bk_8.jpg") no-repeat fixed;
        background-size: cover;
        overflow: auto;
        width: 100%;
        height: 100%;
    }
    .login-wrap {
        background: url("../static/img/bk_one.jpg") no-repeat;
        background-size: cover;
        width: 500px;
        height: 370px;
        margin: 200px auto;
        overflow: hidden;
        padding-top: 10px;
        line-height: 40px;
    }
    h5 {
        color: rgba(18, 185, 189, 0.72);
        font-size: 27px;
    }
    .item .el-form-item__label{
        color: #2295f5;
        font-size: 22px;
        font-family: "Microsoft YaHei", serif;
    }
</style>
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
</head>
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
        <#if menus??>
            <#list menus as menu>
                <#if !menu.children??>
                    <el-menu-item index="${menu.path}">${menu.name}</el-menu-item>
                </#if>
                <#if menu.children??>
                    <el-submenu index="${menu.path}">
                        <template slot="title">${menu.name}</template>
                        <#list menu.children as mc>
                            <el-menu-item index="${mc.path}">${mc.name}</el-menu-item>
                        </#list>
                    </el-submenu>
                </#if>
            </#list>
        </#if>
        <el-submenu index="logout" style="float:right;margin-right:1%">
            <template slot="title"><#if loginInfo??>${loginInfo.userName}</#if></template>
            <el-menu-item index="/logout">退出</el-menu-item>
        </el-submenu>
    </el-menu>
</div>
<@block name="data"></@block>
</body>
<script>
    const Main = {
        data() {
            return {
                activeIndex: window.location.pathname
            };
        },
        methods: {
            handleSelect(key, keyPath) {
                location.href=key;
            }
        }
    };
    const Ctor = Vue.extend(Main);
    new Ctor().$mount('#menu');
</script>
</html>
<style>
    .el-menu-item.is-active {
        background-color: #1474a8 !important;
    }
    .el-aside {
        color: #f64117;
    }
</style>
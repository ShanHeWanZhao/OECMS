<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title class="title"><@block name="title">光学实验课程管理系统</@block></title>
    <link rel="stylesheet" href="/css/element-ui.css" rel="external nofollow"/>
    <link rel="stylesheet" href="/css/common.css" />
    <link rel="icon" type="image/x-icon" href="/static/favicon.ico">
    <script src="/js/vue.js"></script>
    <script src="/js/element-ui.js"></script>
    <script src="/js/vue-resource.js"></script>
    <script src="/js/moment.min.js"></script>
</head>
<body style="margin: 0px;">
<@block name="data"></@block>
</body>
<@block name="script">
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
    </script>
</@block>
</html>
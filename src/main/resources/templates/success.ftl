<@override name="title">信息</@override>
<@override name="data">
    <div id="vm">
        <h1 style="color:#197daf;text-align: center;">${loginInfo.userName},欢迎访问课程管理系统</h1>
    </div>
</@override>
<@extends name="layout.ftl"/>
<script>
    Vue.http.options.emulateJSON = true;
    Vue.http.options.emulateHTTP = true;
    new Vue({
        el: '#vm',
        data() {
            return {

            };
        },
        methods: {

        }
    })
</script>

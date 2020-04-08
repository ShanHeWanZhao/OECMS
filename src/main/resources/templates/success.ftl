<@override name="title">信息</@override>
<@override name="data">
    <div id="vm">
        <#if loginInfo??>
            ${loginInfo.getUserName()},欢迎访问课程管理系统
        </#if>
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

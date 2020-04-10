<!doctype html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link rel="icon" type="image/x-icon" href="/static/favicon.ico">
        <title>出错啦</title>
    </head>
    <body>
    <div class="error-container">
        <img src="../static/img/accessDeny.png" alt="别看，小伙子"/>
        <div class="font_container">
                ${userName!'你怎么没名字？？'}，${msg!'也没有错误提示！！'}
        </div>
    </div>
    </body>
</html>
<style>
    body {
        background-color: #F5F5F5;
        min-height: 100%;
    }

    .error-container {
        text-align: center;
        color: rgba(0, 0, 0, 0.85);
        font-size: 14px;
    }

    .error-container img {
        margin: 100px 0 16px;
    }
    .font_container {
        color: #bf2b3e;
        font-size: x-large;
        font-family: "Microsoft YaHei", serif;
        font-weight:bold;
    }
</style>
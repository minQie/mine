//为了更新 js 缓存，可忽略
layui.config({
    version: '1578698027194'
});

layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element', 'slider'], function(){
    //底部信息
    var footerTpl = lay('#footer')[0].innerHTML;
    lay('#footer').html(layui.laytpl(footerTpl).render({}))
        .removeClass('layui-hide');

    //建立WebSocket通讯
    //注意：如果你要兼容ie8+，建议你采用 socket.io 的版本。下面是以原生WS为例
    if('WebSocket' in window){
        var socket = new WebSocket('ws://localhost:8080/log');
    }else{
        layer.msg("您的浏览器不支持websocket!");
    }

    //连接成功时触发
    socket.onopen = function(){
        socket.send('I am client...'); 
        //更多情况下，一般是传递一个JSON
        // socket.send(JSON.stringify({
        //     type: '', //随便定义，用于在服务端区分消息类型
        //     data: {}
        // })); 
    };

    //监听收到的消息
    socket.onmessage = function(res){
        //res为接受到的MessageEvent，结构如 {"emit": "messageName", "data": {}}
        //emit即为发出的事件名，用于区分不同的消息
        layer.msg("server: " + res.data);
    };
});
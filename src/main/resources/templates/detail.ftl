<!doctype html>
<html lang="en">
  <head>
    <title></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <!-- CSS -->
    <link rel="stylesheet" href="/webjars/bootstrap/5.1.3/dist/css/bootstrap.min.css">
    <style>
      [v-cloak] {
          display: none;
      }
    </style>
  </head>
  <body>
    <div class="container" id="app" v-cloak>
        <div class="row">
            <div class="col-md-12">
                <h3>{{channelId}}</h3>
            </div>
        </div>
        <div class="row">
            <div class="input-group">
                <div class="input-group-prepend">
                    <label class="input-group-text">message</label>
                </div>
                <input type="text" class="form-control" v-model="message" @keyup.enter="sendMessage">
                <div class="input-group-append">
                    <button class="btn btn-primary" type="button" @click="sendMessage">send message</button>
                </div>
            </div>
        </div>
        <div class="row">
            <ul class="list-group">
                <li class="list-group-item" v-for="m in history">
                    {{m.sender}} : {{m.message}}
                </li>
            </ul>
        </div>
    </div>
    <!-- JavaScript -->
    <script src="/webjars/vue/2.5.16/dist/vue.min.js"></script>
    <script src="/webjars/axios/0.21.1/dist/axios.min.js"></script>
    <script src="/webjars/bootstrap/5.1.3/dist/js/bootstrap.min.js"></script>
    <script src="/webjars/sockjs-client/1.5.1/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/2.3.4/stomp.min.js"></script>
    <script>
        var sock = new SockJS('/ws_hs')
        var ws = Stomp.over(sock)

        var vm = new Vue({
            el: '#app',
            data: {
                channelId: '',
                sender: '',
                message: '',
                history: [],
            },
            created() {
                this.sender = localStorage.getItem('channel.sender')
                this.channelId = localStorage.getItem('channel.id')
                this.connect()
            },
            methods: {
                sendMessage: function() {
                    ws.send('/app/msg.'+this.channelId, {}, JSON.stringify({channelId:this.channelId, sender:this.sender, message:this.message, state:'TRANSMIT'}))
                    this.message = ''
                },
                recvMessage: function(r){
                    var body = JSON.parse(r.body)
                    this.history.unshift({sender:body.sender, message:body.message})
                },
                connect: function(){
                    ws.connect({},
                    (f)=>{
                        // ws.subscribe('/queue/msg.'+this.channelId, this.recvMessage)
                        // queue 를 사용시, 하나의 클라이언트에게만 전달됨
                        ws.subscribe('/exchange/channel.exchange/msg.'+this.channelId, this.recvMessage)
                        // exchange 에게서 메시지를 받아 "복사" 해 옴.
                        // 클라이언트 갯수마다 queue가 생성이 됨.
                        // ws.subscribe('/topic/msg.'+this.channelId, this.recvMessage)
                        // ws.subscribe('/amq/queue/channel.queue/msg.'+this.channelId, this.recvMessage)



                        ws.send('/app/msg.'+this.channelId, {}, JSON.stringify({channelId:this.channelId, sender:this.sender, message:'', state:'JOIN'}))
                    },
                    (e)=>{
                        console.log(e)
                    })
                },
                disconnect: function(){
                    if(ws){
                        ws.send('/app/msg.'+this.channelId, {}, JSON.stringify({channelId:this.channelId, sender:this.sender, message:'', state:'LEAVE'}))
                        ws.disconnect()
                    }
                }
            }
        });
    </script>
  </body>
</html>
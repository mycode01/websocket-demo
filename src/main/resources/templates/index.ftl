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
                <h3>채널 리스트</h3>
            </div>
        </div>
        <div class="row">
            <button type="button" class="btn btn-primary btn-lg btn-block" @click="create">create</button>
        </div>
        <div class="row">
            <ul class="list-group">
                <li class="list-group-item list-group-item-action" v-for="channelId in channels" v-bind:key="channelId" v-on:click="join(channelId)">
                    {{channelId}}
                </li>
            </ul>
        </div>
    </div>
    <!-- JavaScript -->
    <script src="/webjars/vue/2.5.16/dist/vue.min.js"></script>
    <script src="/webjars/axios/0.21.1/dist/axios.min.js"></script>
    <script src="/webjars/bootstrap/5.1.3/dist/js/bootstrap.min.js"></script>
    <script src="/webjars/sockjs-client/1.5.1/sockjs.min.js"></script>
    <script>
        var vm = new Vue({
            el: '#app',
            data: {
                empty: '',
                channels: []
            },
            created() {
                this.find();
            },
            methods: {
                find: function() {
                    axios.get('/channels').then(r => {
                        this.channels = r.data;
                         }); // find all channel
                },
                create: function() {
                        axios.post('/channels/create') // create channel
                        .then(r => {
                                this.find();
                            }
                        )
                        .catch( r => { alert("failed at create channel."); } );
                },
                join: function(channelId) {
                    var sender = prompt('sender name; ');
                    if(!sender){
                        return
                    }
                    localStorage.setItem('channel.sender',sender);
                    localStorage.setItem('channel.id',channelId);
                    location.href="/detail/"+channelId; //go to ch detail
                }
            }
        });
    </script>
  </body>
</html>
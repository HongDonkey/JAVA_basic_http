
let express = require('express'); //
let http = require('http');
let app = express();
let server = http.createServer(app).listen(80);
//80번 포트에서 서버 리퀘스 리스닝
//기본 http와 포트번호 설정


let bodyParser = require('body-parser')
//POST방식으로 사용할때는 bodyParser를 임포트 해줌
app.use(bodyParser.json());
//application/josn 방식의 Content-Type 데이터를 받아준다
app.use(bodyParser.urlencoded({
  limit:'50mb'
  }));

  app.get('/test', function(req, res) {
  res.sendfile("api/test.html");
});

app.get('/requestApi', function(req, res) {
    console.log("22");
  });

  app.get('/test2', function(req, res) {
    res.send("hello world2");
  });

  //콘솔 찍는거부터 해서 api 리퀘스트 리스폰스 테스트하기
  
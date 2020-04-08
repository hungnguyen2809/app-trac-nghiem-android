var express = require("express");
var app = express();
var server = require("http").createServer(app);
var io = require("socket.io").listen(server);
var fs = require("fs");
server.listen(process.env.PORT || 3000);

console.log('Server is running..........\n');

// function Student(msv, name, lop, count) {
//     this.MSV = msv;
//     this.Name = name;
//     this.Lop = lop;
//     this.Count = count;
// }
// var dsStudent = [];

io.sockets.on('connection', function(sk) {
    console.log('One device is connect to server !\n');

    sk.on('client-send-all-student', function(dataStudent) {
        var student = JSON.stringify(dataStudent);
        console.log(student);
        //io.sockets.emit('server-send-all-student', { 'data-student': student });
    });

    sk.on('client-send-all-question', function(dataQuestion) {
        var question = JSON.stringify(dataQuestion);
        console.log(question);
        //io.sockets.emit('server-send-all-question', { 'data-question': question });
    });

});
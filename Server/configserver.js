var express = require("express");
var app = express();
var server = require("http").createServer(app);
var io = require("socket.io").listen(server);
var fs = require("fs");
server.listen(process.env.PORT || 3000);

console.log('Server is running..........\n');


io.sockets.on('connection', function(sk) {
    console.log('One device is connect to server !\n');

    sk.on('client-send-all-student', function(dataStudent) {
        console.log(dataStudent);
        var res = JSON.stringify(dataStudent);
        console.log(res);
        //io.sockets.emit('server-send-all-student', { 'data-student': dataStudent });
    });

    sk.on('client-send-all-question', function(dataQuestion) {
        //var res = JSON.stringify(dataQuestion);
        var arrQuestion = [];
        arrQuestion.push(dataQuestion);
        console.log(arrQuestion);
        io.sockets.emit('server-send-all-question', { 'data-question': arrQuestion });
    });

    sk.on('delete-all-question', function(query) {
        console.log('Delete all data question');
        io.sockets.emit('server-accept-delete-question', { 'content': query });
    });

    sk.on('delete-all-student', function(query) {
        console.log("Delete all data student");
        io.sockets.emit('server-accept-delete-student', { 'content': query });
    });

});
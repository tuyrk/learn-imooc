/**
 * http
 * createServer
 */
let fs = require("fs");
let path = require("path");
let http = require("http");

http.createServer((request, response) => {
    /*console.log("我来了。");*/

    /*response.write("index");
    response.end();*/

    console.log(request.url);
    let url = request.url;
    if (path.extname(url) === ".html") {
        url = path.resolve(__dirname, "../", "html") + url;
    }
    if (path.extname(url) === ".jpeg") {
        url = path.resolve(__dirname, "../") + url;
    }
    console.log(url);

    fs.readFile(url, (err, data) => {
        if (err) {
            console.log(err);
            response.writeHead(404);
            response.end("404 not found");
        } else {
            response.writeHead(200);
            response.end(data);
        }
    });
}).listen(8888);
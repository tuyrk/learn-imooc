/**
 * url
 * 1. parse
 */
let http = require("http");
let url = require("url");

http.createServer(((req, res) => {
    console.log(req.url);
    // 1. 无url模块，原始处理方法
    /*let [url, query] = req.url.split('?');
    console.log(url, query);*/

    // 2. 使用url模块处理路径与参数
    let {pathname, query} = url.parse(req.url, true);
    console.log(pathname, query);

})).listen(8888);
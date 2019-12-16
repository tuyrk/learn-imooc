/**
 * request
 * 1. on data
 * 2. on end
 *
 * Buffer
 * 1. concat
 *
 * querystring
 * 1. parse
 */
let http = require("http");
let querystring = require("querystring");

http.createServer(((req, res) => {
    let result = [];
    req.on("data", buffer => {
        result.push(buffer);// 分段传输数据，多次执行添加
    });

    req.on("end", () => {// 数据处理完成
        let data = Buffer.concat(result).toString();// 数组转buffer
        console.log(querystring.parse(data));// 处理queryString
    })
})).listen(8888);
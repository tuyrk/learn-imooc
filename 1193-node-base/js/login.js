/**
 * 登陆、注册
 *
 */
const http = require("http");
const url = require("url");
const querystring = require("querystring");
const fs = require("fs");
const path = require("path");

let user = {
    admin: "123456"
};

http.createServer(((req, res) => {
    console.log(req.method);

    // 获取数据
    let uri, get, post;
    if (req.method === "GET") {
        let {pathname, query} = url.parse(req.url, true);
        uri = pathname, get = query;
        complete();
    } else if (req.method === "POST") {
        let arr = [];
        uri = req.url;
        req.on("data", buffer => {
            arr.push(buffer);
        });
        req.on("end", () => {
            post = querystring.parse(Buffer.concat(arr).toString());
            complete();
        });
    }

    // 业务操作
    function complete() {
        // 登陆
        if (uri === "/login") {
            setHead();
            login();
        } else if (uri === "/reg") {// 注册
            setHead();
            reg();
        } else {// 访问页面
            view();
        }
    }

    // 登陆
    function login() {
        let {username, password} = get;
        if (!user[username]) {
            res.end(JSON.stringify({
                err: 1,
                msg: "用户名不存在！"
            }));
        } else if (user[username] !== password) {
            res.end(JSON.stringify({
                err: 1,
                msg: "密码错误！"
            }));
        } else {
            res.end(JSON.stringify({
                err: 0,
                msg: "成功！"
            }));
        }
    }

    // 注册
    function reg() {
        let {username, password} = post;
        if (user[username]) {
            res.end(JSON.stringify({
                err: 1,
                msg: "账户已经存在！"
            }));
        } else {
            user[username] = password;
            res.end(JSON.stringify({
                err: 0,
                msg: "注册成功！"
            }));
        }
    }

    // 访问页面
    function view() {
        if (path.extname(uri) === ".html") {
            uri = path.resolve(__dirname, "../", "html" + uri);
        }
        const exts = [".js", ".css", "jpeg"];
        if (exts.includes(path.extname(uri).toString())) {
            uri = path.resolve(__dirname, "../" + uri);
        }
        fs.readFile(uri, ((err, data) => {
            if (err) {
                console.log(err);
                res.end("404");
            } else {
                res.end(data);
            }
        }));
    }

    // 设置状态码、编码
    function setHead() {
        res.writeHead(200, {
            "Content-Type": "text/plain;charset=utf-8"
        });
    }
})).listen(8888);
/*
* path
* 1. dirname
* 2. basename
* 3. extname
* 4. resolve
* */
var path = require("path");

console.log(path.dirname("/node/a/c/c/1.jpg"));// 文件夹名称
console.log(path.basename("/node/a/c/c/1.jpg"));// 文件名称
console.log(path.extname("/node/a/c/c/1.jpg"));// 文件后缀名称

console.log(path.resolve("/node/a/b/c", "../../", "d"));// /node/a/d
console.log(path.resolve("/node/a/b/c", "/", "d"));// /node/a/d
console.log(path.resolve(__dirname, "index.js"));// /Develop/learn-imooc/1193-node-base/index.js

console.log(path);
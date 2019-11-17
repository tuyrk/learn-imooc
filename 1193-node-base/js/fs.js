/*
* fs
* 1. readFile
* 2. writeFile
* 3. appendFile
* 4. readFileSync
* 5. writeFileSync
* */
var fs = require("fs");

// 写文件
fs.writeFile("fs.write.txt", "刚下飞机", function (err) {
    if (err) {
        throw err;
    }
});

// 读文件
fs.readFile("fs.write.txt", (err, data) => {
        if (err) {
            console.log(err);
        } else {
            console.log(data.toString());
        }
    }
);

// 追加文件
fs.writeFile("fs.write.txt", "，年入百万", {flag: "a"}, function (err) {
    if (err) {
        throw err;
    }
});
fs.appendFile("fs.write.txt", "泻药", function (err) {
    if (err) {
        throw err;
    }
});

// 同步写文件
fs.writeFileSync("fs.writeSync.txt", "分割线");

// 同步读文件
var data = fs.readFileSync("fs.writeSync.txt");
console.log(data.toString());


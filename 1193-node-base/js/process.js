/*
* 1. process.env
* 2. process.argv
* 3. __dirname
* 4. process
* */
console.log(process.env);

if (process.env.logname === "tuyuankun") {
    console.log("我的名字是：" + process.env.logname);
}

console.log("==================");

console.log(process.argv);

/*简易计算器*/
var num1 = parseInt(process.argv[2]);
var num2 = parseInt(process.argv[3]);
console.log(num1 + num2);

console.log(__dirname);

console.log(process);
/* mod.js */
// 1. 参数
/*exports.a = 1;
exports.b = 2;

var c = 3;*/

// 2. 对象
/*module.exports = {
    a: 1,
    b: 2
};*/

// 3. 方法
/*module.exports = function () {
    console.log("module.exports function");
};*/

// 4. 类
module.exports = class {
    constructor(name) {
        this.name = name;
    }

    show() {
        console.log(this.name);
    }
};
function getUrlPrefix(checkInputUndefined){
    if (checkInputUndefined !== undefined) {
        return checkInputUndefined;
    }
    //获取地址栏URL端口号后的内容
    var pathStr = window.location.pathname;
    console.log("window.location.pathname - " + pathStr);
    if (pathStr.length === 0) {
        pathStr = "/"
    }

    //找到非首字母的"/"符号，截取之前的子字符串（当通过打war包部署时，这个字符串是即war包的命名）
    //当长度为1时，第一次即不满足条件，不循环，pathStr.substr();执行后得到本身可能是"/"或者war包包名只有一个字符
    var i;
    for (i = 1; i < pathStr.length; i++) {
        if (pathStr[i] === "/") {
            break;
        }
    }
    pathStr = pathStr.substr(0, i);
    console.log("string from first gap - " + pathStr);

    //如果为一个"/"，则直接将pathStr省略为""
    //如果截取后的结尾名为".html"，说明首个分隔符即为"***.html"（这里默认".html"安全，即不会出现形如"***.html.war"的部署）
    //  说明直接运行到了8080端口，而非war包之中，则直接将pathStr省略为""
    if (pathStr === "/") {
        pathStr = "";
    } else if (pathStr.length >= 5 && pathStr.substr(pathStr.length - 5, 5) === ".html") {
        pathStr = "";
    }
    console.log("pathStr - " + pathStr);

    var urlPrefix = window.location.protocol + "//" + window.location.host + pathStr;
    console.log("urlPrefix - " + urlPrefix);
    return(urlPrefix);
}
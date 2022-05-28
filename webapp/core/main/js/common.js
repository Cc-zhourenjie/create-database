
//接收地址栏参数
window.request = function (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

//引入js
window.importjs = function (fileName) {
    for (var i = 0; i < fileName.length; i++) {
        document.write("<script type=\"text/javascript\" src=" + fileName[i] + "?time_ver=" + new Date().getTime() + "></script>");
    }
}

//引入css
window.importcss = function (fileName) {
    for (var i = 0; i < fileName.length; i++) {
        document.write("<link rel=\"stylesheet\"  type=\"text\/css\" href=" + fileName[i] + " ></link>");
    }
}



//打开框架
window.openhtml = function (title, weight, height, url, condition) {
    layer.open({
        type: 2,
        title: title,
        area: [weight, height],
        fix: false, // 
        content: url + "?var=" + Math.random() + (condition == undefined ? "" : condition),
        end: function () {
            // dataTable.reloadTable();
        }
    });
}
//ajax请求后端
window.ajax = function (type, url, data) {
    var value;
    if (type == "get") {
        $.ajax({
            type: type,
            url: back_url(url),
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            async: false,
            success: function (result) {
                value = result;
            }
        });
    } else {
        $.ajax({
            type: type,
            url: back_url(url),
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            async: false,
            data: JSON.stringify(data),
            success: function (result) {
                value = result;
            }
        });
    }

    return value;
}

//后端请求地址
window.default_url = function () {
    return "http://localhost:10020";
}

//传入后端请求地址
window.back_url = function (url) {
    return default_url() + url;
}


//查看图片大图
window.lookFile = function (fileId) {
    layer.photos({
        photos: {
            "data": [{
                "src": "http://localhost:10020/api/oss/file/brw/" + fileId + "/user"
            }]
        }
    });
}


//引入基础js
window.importjs([
    "/js/jquery-1.8.2.js",
    "/core/main/js/main.js"
]);

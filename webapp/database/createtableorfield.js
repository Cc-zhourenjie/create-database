$(function () {
    //注意：选项卡 依赖 element 模块，否则无法进行功能性操作
    layui.use('element', function () {
        var element = layui.element;
    
        element.on('tab(database_tab)', function (data) {
            if (this.textContent == "创建物理表") {
                $("#tab_url").attr("src", "/database/createdatabase.html");
            }
            else if (this.textContent == "创建字段") {
                $("#tab_url").attr("src", "/database/createfield.html");
            }
        });
    });

})









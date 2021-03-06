

$(function () {
    //控制表单控件是否隐藏
    layui.use('form', function () {
        var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
        form.render();
        //下拉框赋事件
        form.on('select(field_type)', function (data) {
            resetField(data.value);
        });

    });
    $(".layui-form-label").attr("style", "width: 100px;text-align: left;")


    //提交
    $("#submit").click(function () {
        //获取表单区域所有值
        var form = layui.form;
        var datas = form.val("table_form");
        var result = ajax("post", "/table/info/createtable", datas);
        if (result.data) {
            //添加成功
            layer.open({
                title: '响应结果',
                content: '添加成功，请刷新页面'
            });
        } else {
            //添加失败
            $.messager.confirm('确认', result.message, function (ok) {

            });
        }
    })

    //添加字段
    $("#field").click(function () {
        var url = "/database/createfield.html";
        window.open(url);
    })

    resetField($("#field_type").val())

})


//重置字段长度
function resetField(value) {
    if (value == "varchar") {
        $("#field_length").show();
        $("#field_decimal").hide();
    }
    else if (value == "int") {
        $("#field_length").show();
        $("#field_decimal").hide();
    }
    else if (value == "decimal") {
        $("#field_length").show();
        $("#field_decimal").show();
    }
    else if (value == "text") {
        $("#field_length").hide();
        $("#field_decimal").hide();
    }
    else {
        $("#field_length").hide();
        $("#field_decimal").hide();
    }


}









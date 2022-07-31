

$(function () {
    //控制表单控件是否隐藏
    layui.use('form', function () {
        var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
        form.render();
        //下拉框赋事件
        form.on('select(lay_field_type)', function (data) {
            resetField(data.value);
        });

    });
    $(".layui-form-label").attr("style", "width: 100px;text-align: left;")


    //提交
    $("#submit").click(function () {
        //获取表单区域所有值
        var form = layui.form;
        var datas = form.val("table_form");
        var result = ajax("post", "/table/field/createfield", datas);
        if (result.data) {
            //添加成功
            $.messager.confirm('确认', '添加成功，请刷新页面', function (ok) {
                if (ok) {
                    location.reload();
                }
            });
        } else {
            //添加失败
            $.messager.confirm('确认', result.message, function (ok) {

            });
        }
    })
    resetField($("#field_type").val())

})


/**
 * 重置字段长度
 * @param {字段类型值} value 
 */
function resetField(value) {
    if (value == "varchar") {
        $("#field_length").show();
        $("#file_precision").hide();
    }
    else if (value == "int") {
        $("#field_length").show();
        $("#file_precision").hide();
    }
    else if (value == "decimal") {
        $("#field_length").show();
        $("#file_precision").show();
    }
    else if (value == "text") {
        $("#field_length").hide();
        $("#file_precision").hide();
    }
    else {
        $("#field_length").hide();
        $("#file_precision").hide();
    }


}

/**
 * 校验字段类型和长度是否匹配
 * @param {表单数据} data 
 */
function checkFieldType(data) {
    if (data.field_type == "varcahr" && data.field_length == "" || data.field_length == 0) {

    }

}









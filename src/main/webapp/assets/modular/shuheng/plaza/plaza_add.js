
layui.use(['layer', 'form', 'admin', 'laydate', 'ax','upload'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var upload = layui.upload;

    //普通图片上传
    var uploadInst = upload.render({
        elem: '#uploadImg'
        ,url: Feng.ctxPath + '/upload/uploadImg'
        ,accept: 'images' //普通文件
        ,acceptMime: 'images/*'
        ,size: '102400'
        ,exts:'jpg|jpeg|png|gif|bmp'
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#imgShow').attr('src', result); //图片链接（base64）
            });
        }
        ,done: function(res){
            //如果上传失败
            if(res.code > 0){
                return layer.msg('上传失败' + res.msg);
            }
            //上传成功
            $("#plazaImg").val(res.img_path);
        }
        ,error: function(){
            //演示失败状态，并实现重传
            var demoText = $('#imgText');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function(){
                uploadInst.upload();
            });
        }
    });

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    // 添加表单验证方法
    form.verify({

    });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/plaza/add", function (data) {
            Feng.success("添加成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("添加失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();
    });
});
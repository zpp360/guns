layui.use(['layer', 'form', 'admin', 'laydate', 'ax','upload','element'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var upload = layui.upload;
    var laydate = layui.laydate;
    var element = layui.element;

    progressHide()

    //根据不同模型控制显示隐藏项
    var newsModel = $("#newsModel").val()
    if(newsModel=="news"){
        $("#modelVideo").hide()
        $("#modelDownload").hide()
    }

    if(newsModel=="video"){
        $("#modelDownload").hide()
    }

    if(newsModel=="download"){
        $("#modelVideo").hide()
    }

    // 渲染时间选择框
    laydate.render({
        elem: '#releaseTime'
        ,trigger : 'click',
        type:"datetime"
    });

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
            $("#newsImg").val(res.img_path);
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

    var timer = null;

    upload.render({
        elem: '#newsVideoUpload'
        ,url: Feng.ctxPath + '/upload/uploadVideo'
        ,accept: 'video' //视频
        ,before:function () {
            uploadBtnDisable("newsVideoUpload")
            setProgress(0)
            progressShow()
            timer = setInterval(function () {
                var ajax = new $ax(Feng.ctxPath + "/upload/uploadStatus", function (result) {
                    console.log(result)
                    element.progress("progress",result+"%")
                })
                ajax.start()
            },500)
        }
        ,done: function(res){
            clearInterval(timer)
            //上传成功清除interval
            //如果上传失败
            if(res.code > 0){
                setProgress(0)
                progressHide()
                uploadBtnAble("newsVideoUpload")
                return layer.msg('上传失败' + res.msg);
            }
            //上传成功 newsVideoSpan
            $("#newsVideoSpan").text(res.video_name);
            $("#newsVideoName").val(res.video_name);
            $("#newsVideoPath").val(res.video_path);
            setProgress(100)
            uploadBtnAble("newsVideoUpload")
        }
        ,error:function () {
            setProgress(0)
            progressHide()
            uploadBtnAble("newsVideoUpload")
        }
    });


    upload.render({
        elem: '#newsFileUpload'
        ,url: Feng.ctxPath + '/upload/uploadFile'
        ,accept: 'file' //文件
        ,exts:'txt|zip|rar|doc|docx|pdf|ppt|pptx|xls|xlsx'
        ,before:function () {
            uploadBtnDisable("newsFileUpload")
            setProgress(0)
            progressShow()
            timer = setInterval(function () {
                var ajax = new $ax(Feng.ctxPath + "/upload/uploadStatus", function (result) {
                    console.log(result)
                    element.progress("progress",result+"%")
                })
                ajax.start()
            },500)
        }
        ,done: function(res){
            clearInterval(timer)
            //上传成功清除interval
            //如果上传失败
            if(res.code > 0){
                setProgress(0)
                progressHide()
                uploadBtnAble("newsFileUpload")
                return layer.msg('上传失败' + res.msg);
            }
            //上传成功 newsVideoSpan
            $("#newsFileSpan").text(res.file_name);
            $("#newsFileName").val(res.file_name);
            $("#newsFilePath").val(res.file_path);
            setProgress(100)
            uploadBtnAble("newsFileUpload")
        }
        ,error:function () {
            setProgress(0)
            progressHide()
            uploadBtnAble("newsFileUpload")
        }
    });

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    // 添加表单验证方法
    form.verify({
        sort: [/^[\d]{1,3}$/, '排序号为1-3位正整数']
    });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/news/add", function (data) {
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

    /**
     * 显示进度条
     */
    function progressShow(){
        $(".layui-progress").show()
    }

    /**
     * 隐藏进度条
     */
    function progressHide() {
        $(".layui-progress").hide()
    }

    /**
     * 设计进度
     * @param num
     */
    function setProgress(num){
        element.progress("progress",num+"%")
    }

    function uploadBtnDisable(id){
        $("#"+id).addClass("layui-btn-disabled")
        $("#"+id).attr("disabled","disabled")
    }

    function uploadBtnAble(id){
        $("#"+id).removeClass("layui-btn-disabled")
        $("#"+id).removeAttr("disabled")
    }
});
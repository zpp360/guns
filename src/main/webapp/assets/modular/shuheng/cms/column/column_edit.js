/**
 * 对话框
 */
var ColumnInfoDlg = {
    data: {
        parentId: "",
        parentName: ""
    }
};

layui.use(['layer', 'form', 'admin', 'laydate', 'ax','upload'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    // 点击父级菜单
    $('#parentName').click(function () {
        var plazaId = $("#plazaId").val()
        var formName = encodeURIComponent("parent.ColumnInfoDlg.data.parentName");
        var formId = encodeURIComponent("parent.ColumnInfoDlg.data.parentId");
        var treeUrl;
        if(plazaId!=null && plazaId!=""){
            treeUrl = encodeURIComponent("/column/selectColumnTreeList?plazaId="+plazaId);
        }else{
            treeUrl = encodeURIComponent("/column/selectColumnTreeList");
        }

        layer.open({
            type: 2,
            title: '父级菜单',
            area: ['300px', '400px'],
            content: Feng.ctxPath + '/system/commonTree?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
            end: function () {
                if(ColumnInfoDlg.data.parentId!="" && ColumnInfoDlg.data.parentName!=""){
                    $("#parentId").val(ColumnInfoDlg.data.parentId);
                    $("#parentName").val(ColumnInfoDlg.data.parentName);
                }
            }
        });
    });

    //查询所有纪念馆
    var ajax = new $ax(Feng.ctxPath + "/plaza/selectPlaza", function (data) {
        $("#plazaId").append(data)
    })
    ajax.start();
    //重新渲染select
    form.render('select');

    var ajax = new $ax(Feng.ctxPath + "/column/getColumnInfo?columnId=" + Feng.getUrlParam("columnId"));
    var result = ajax.start();
    form.val('columnForm', result.data);


    //查询所有纪念馆
    var ajax = new $ax(Feng.ctxPath + "/plaza/selectPlaza", function (data) {
        $("#plazaId").append(data)
    })
    ajax.start();
    //重新渲染select
    form.render('select');

    // 添加表单验证方法
    form.verify({
        sort: [/^[\d]{1,3}$/, '排序号为1-3位正整数']
    });

    // 表单提交事件
    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/column/edit", function (data) {
        Feng.success("修改成功！");

        //传给上个页面，刷新table用
        admin.putTempData('formOk', true);

        //关掉对话框
        admin.closeThisDialog();
        }, function (data) {
            Feng.error("修改成功！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();
    });
});
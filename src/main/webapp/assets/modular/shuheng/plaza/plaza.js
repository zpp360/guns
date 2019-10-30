layui.use(['table', 'ax','admin'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
    * 基础字典管理
    */
    var Plaza = {
        tableId: "plazaTable"
    };

    /**
    * 初始化表格的列
    */
    Plaza.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'plazaId', hide: true, title: 'ID'},
            {field: 'plazaName', sort: false, title: '纪念馆名称'},
            {field: 'plazaTel', sort: false, title: '电话'},
            {field: 'plazaAddres', sort: false, title: '地址'},
            {field: 'plazaDesc', sort: false, title: '纪念馆简介'},
            {field: 'updateTime', sort: false, title: '更新时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };



    /**
    * 渲染表格
    */
    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Plaza.tableId,
        url: Feng.ctxPath + '/plaza/list',
        page: true,
        height: "full-98",
        cellMinWidth: 100,
        cols: Plaza.initColumn()
    });
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Plaza.openAdd();
    });
    //搜索
    $('#btnSearch').click(function () {
        Plaza.search()
    })
    //添加
    Plaza.openAdd = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加',
            content: Feng.ctxPath + '/plaza/plaza_add',
            end: function () {
                admin.getTempData('formOk') && table.reload(Plaza.tableId);
            }
        });
    }
    //修改
    Plaza.onEdit = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '编辑',
            content: Feng.ctxPath + '/plaza/plaza_edit?plazaId=' + data.plazaId,
            end: function () {
                admin.getTempData('formOk') && table.reload(Plaza.tableId);
            }
        });
    }
    //查询
    Plaza.search = function () {
        var queryData = {};
        queryData['plazaName'] = $("#plazaName").val();
        table.reload(Plaza.tableId, {where: queryData});
    }

    //删除
    Plaza.onDelete = function (data) {
        var operation = function () {
        var ajax = new $ax(Feng.ctxPath + "/plaza/delete", function () {
            Feng.success("删除成功!");
            Plaza.search()
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("plazaId", data.plazaId);
        ajax.start();
        };
        Feng.confirm("是否删除菜单" + data.plazaName + "?", operation);
    }

    // 工具条点击事件
    table.on('tool(' + Plaza.tableId + ')', function (obj) {
        var data = obj.data;
            var layEvent = obj.event;
        if (layEvent === 'edit') {
            Plaza.onEdit(data);
        } else if (layEvent === 'delete') {
            Plaza.onDelete(data);
        }
    });


});

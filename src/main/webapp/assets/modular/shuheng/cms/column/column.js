layui.use(['layer', 'form', 'ztree', 'laydate', 'admin', 'ax', 'table', 'treetable'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var $ZTree = layui.ztree;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var admin = layui.admin;
    var table = layui.table;
    var treetable = layui.treetable;
    var $ = layui.jquery;

    /**
    * 基础字典管理
    */
    var Column = {
        tableId: "columnTable",
        condition: {
            columnId: "",
            columnName: "",
            plazaId: ""
        }
    };

    //渲染纪念馆下拉框
    var ajax = new $ax(Feng.ctxPath + "/plaza/selectPlaza", function (data) {
        $("#plazaId").append(data)
    })
    ajax.start();
    form.render('select');

    Column.condition.plazaId = $("#plazaId").val()
    //
    // form.on('select(plazaId)',function () {
    //     Column.condition.plazaId = $("#plazaId").val()
    // })

    /**
    * 初始化表格的列
    */
    Column.initColumn = function () {
        return [[
            {type: 'numbers'},
            {field: 'columnId', hide: true, title: ''},
            {field: 'columnName', sort: false, title: '栏目名称'},
            {field: 'plazaName', sort: false, title: '纪念馆'},
            {field: 'parentName', sort: false, title: '父栏目'},
            {field: 'imgWidth', sort: false, title: '图片宽度'},
            {field: 'imgHeight', sort: false, title: '图片高度'},
            {field: 'sort', sort: false, title: '排序号码'},
            {field: 'updateTime', sort: false, title: '更新时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };



    /**
    * 渲染表格
    */
    Column.initTable = function (columnId,data) {
        return treetable.render({
            elem: '#' + columnId,
            url: Feng.ctxPath + '/column/listTree',
            where: data,
            page: false,
            height: "full-98",
            cellMinWidth: 100,
            cols: Column.initColumn(),
            treeColIndex: 2,
            treeSpid: "0",
            treeIdName: 'columnId',
            treePidName: 'parentId',
            treeDefaultClose: false,
            treeLinkage: true
        })
    }
    // 渲染表格
    Column.initTable(Column.tableId,Column.condition);
    $('#expandAll').click(function () {
        treetable.expandAll('#' + Column.tableId);
    });
    $('#foldAll').click(function () {
        treetable.foldAll('#' + Column.tableId);
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Column.openAdd();
    });
    //搜索
    $('#btnSearch').click(function () {
        Column.search()
    })
    //添加
    Column.openAdd = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加',
            content: Feng.ctxPath + '/column/column_add?plazaId='+Column.condition.plazaId,
            end: function () {
                admin.getTempData('formOk') && Column.initTable(Column.tableId,Column.condition);
            }
        });
    }
    //修改
    Column.onEdit = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '编辑菜单',
            content: Feng.ctxPath + '/column/column_edit?columnId=' + data.columnId + "&plazaId="+Column.condition.plazaId,
            end: function () {
                admin.getTempData('formOk') && Column.initTable(Column.tableId,Column.condition);
            }
        });
    }
    //查询
    Column.search = function () {
        var queryData = {};
        queryData['columnName'] = $("#columnName").val();
        queryData['plazaId'] = $("#plazaId").val()
        Column.initTable(Column.tableId,  queryData);
    }

    //删除
    Column.onDelete = function (data) {
        var operation = function () {
        var ajax = new $ax(Feng.ctxPath + "/column/delete", function () {
            Feng.success("删除成功!");
            Column.initTable(Column.tableId,Column.condition)
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("columnId", data.columnId);
        ajax.start();
        };
        Feng.confirm("是否删除菜单" + data.columnName + "?", operation);
    }

    // 工具条点击事件
    table.on('tool(' + Column.tableId + ')', function (obj) {
        var data = obj.data;
            var layEvent = obj.event;
        if (layEvent === 'edit') {
            Column.onEdit(data);
        } else if (layEvent === 'delete') {
            Column.onDelete(data);
        }
    });


});

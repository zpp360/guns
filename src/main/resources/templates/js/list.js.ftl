layui.use(['table', 'ax','admin'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
    * 基础字典管理
    */
    var ${entity} = {
        tableId: "${table.entityPath}Table"
    };

    /**
    * 初始化表格的列
    */
    ${entity}.initColumn = function () {
        return [[
            {type: 'checkbox'},
            <#list table.fields as field>
            <#if field.keyFlag>
            {field: '${field.propertyName}', hide: true, title: '${field.comment}'},
            <#else>
            {field: '${field.propertyName}', sort: false, title: '${field.comment}'},
            </#if>
            </#list>
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };



    /**
    * 渲染表格
    */
    // 渲染表格
    var tableResult = table.render({
        elem: '#' + ${entity}.tableId,
        url: Feng.ctxPath + '/${table.entityPath}/list',
        page: true,
        height: "full-98",
        cellMinWidth: 100,
        cols: ${entity}.initColumn()
    });
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        ${entity}.openAdd();
    });
    //搜索
    $('#btnSearch').click(function () {
        ${entity}.search()
    })
    //添加
    ${entity}.openAdd = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加',
            content: Feng.ctxPath + '/${table.entityPath}/${table.entityPath}_add',
            end: function () {
                admin.getTempData('formOk') && table.reload(${entity}.tableId);
            }
        });
    }
    //修改
    ${entity}.onEdit = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '编辑',
            content: Feng.ctxPath + '/${table.entityPath}/${table.entityPath}_edit?${table.entityPath}Id=' + data.${table.entityPath}Id,
            end: function () {
                admin.getTempData('formOk') && table.reload(${entity}.tableId);
            }
        });
    }
    //查询
    ${entity}.search = function () {
        var queryData = {};
        queryData['${table.entityPath}Name'] = $("#${table.entityPath}Name").val();
        table.reload(${entity}.tableId, {where: queryData});
    }

    //删除
    ${entity}.onDelete = function (data) {
        var operation = function () {
        var ajax = new $ax(Feng.ctxPath + "/${table.entityPath}/delete", function () {
            Feng.success("删除成功!");
            ${entity}.search()
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("${table.entityPath}Id", data.${table.entityPath}Id);
        ajax.start();
        };
        Feng.confirm("是否删除菜单" + data.${table.entityPath}Name + "?", operation);
    }

    // 工具条点击事件
    table.on('tool(' + ${entity}.tableId + ')', function (obj) {
        var data = obj.data;
            var layEvent = obj.event;
        if (layEvent === 'edit') {
            ${entity}.onEdit(data);
        } else if (layEvent === 'delete') {
            ${entity}.onDelete(data);
        }
    });


});

layui.use(['table', 'ax','admin'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 基础字典管理
     */
    var Floor = {
        tableId: "floorTable"
    };

    /**
     * 初始化表格的列
     */
    Floor.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'floorId', hide: true, title: '楼层ID'},
            {field: 'floorName', sort: true, title: '楼层名称'},
            {field: 'sort', sort: true, title: '排序'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };



    /**
     * 渲染表格
     */
        // 渲染表格
    var tableResult = table.render({
            elem: '#' + Floor.tableId,
            url: Feng.ctxPath + '/floor/list',
            page: true,
            height: "full-98",
            cellMinWidth: 100,
            cols: Floor.initColumn()
        });
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Floor.openAddFloor();
    });
    //搜索
    $('#btnSearch').click(function () {
        Floor.search()
    })

    Floor.openAddFloor = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加楼层',
            content: Feng.ctxPath + '/floor/floor_add',
            end: function () {
                admin.getTempData('formOk') && table.reload(Floor.tableId);
            }
        });
    }
    //修改
    Floor.onEdit = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '编辑楼层',
            content: Feng.ctxPath + '/floor/floor_edit?floorId=' + data.floorId,
            end: function () {
                admin.getTempData('formOk') && table.reload(Floor.tableId);
            }
        });
    }
    //查询
    Floor.search = function () {
        var queryData = {};
        queryData['floorName'] = $("#floorName").val();
        table.reload(Floor.tableId, {where: queryData});
    }

    //删除
    Floor.onDelete = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/floor/delete", function () {
                Feng.success("删除成功!");
                Floor.search()
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("floorId", data.floorId);
            ajax.start();
        };
        Feng.confirm("是否删除菜单" + data.floorName + "?", operation);
    }

    // 工具条点击事件
    table.on('tool(' + Floor.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'edit') {
            Floor.onEdit(data);
        } else if (layEvent === 'delete') {
            Floor.onDelete(data);
        }
    });


});

layui.use(['table', 'ax','admin'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 基础字典管理
     */
    var House = {
        tableId: "houseTable"
    };

    /**
     * 初始化表格的列
     */
    House.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'houseId', hide: true, title: '库房ID'},
            {field: 'houseName', sort: false, title: '库房名称'},
            {field: 'floorName', sort: true, title: '楼层'},
            {field: 'machineName', sort: false, title: '设备'},
            {field: 'ip', sort: false, title: 'IP'},
            {field: 'port', sort: false, title: '端口'},
            {field: 'sort', sort: true, title: '排序'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };



    /**
     * 渲染表格
     */
        // 渲染表格
    var tableResult = table.render({
            elem: '#' + House.tableId,
            url: Feng.ctxPath + '/house/list',
            page: true,
            height: "full-98",
            cellMinWidth: 100,
            cols: House.initColumn()
        });
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        House.openAdd();
    });
    //搜索
    $('#btnSearch').click(function () {
        House.search()
    })

    House.openAdd = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加楼层',
            content: Feng.ctxPath + '/house/house_add',
            end: function () {
                admin.getTempData('formOk') && table.reload(house.tableId);
            }
        });
    }
    //修改
    House.onEdit = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '编辑楼层',
            content: Feng.ctxPath + '/house/house_edit?houseId=' + data.houseId,
            end: function () {
                admin.getTempData('formOk') && table.reload(house.tableId);
            }
        });
    }
    //查询
    House.search = function () {
        var queryData = {};
        queryData['houseName'] = $("#houseName").val();
        table.reload(house.tableId, {where: queryData});
    }

    //删除
    House.onDelete = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/house/delete", function () {
                Feng.success("删除成功!");
                House.search()
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("houseId", data.houseId);
            ajax.start();
        };
        Feng.confirm("是否删除菜单" + data.houseName + "?", operation);
    }

    // 工具条点击事件
    table.on('tool(' + House.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'edit') {
            House.onEdit(data);
        } else if (layEvent === 'delete') {
            House.onDelete(data);
        }
    });


});

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
        Floor.openAddUser();
    });

    Floor.openAddUser = function () {
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

    // 工具条点击事件
    table.on('tool(' + Floor.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            MgrUser.onEditUser(data);
        } else if (layEvent === 'delete') {
            MgrUser.onDeleteUser(data);
        } else if (layEvent === 'roleAssign') {
            MgrUser.roleAssign(data);
        } else if (layEvent === 'reset') {
            MgrUser.resetPassword(data);
        }
    });


});

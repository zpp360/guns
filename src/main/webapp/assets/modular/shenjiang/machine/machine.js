layui.use(['table', 'ax','admin'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
    * 基础字典管理
    */
    var Machine = {
        tableId: "machineTable"
    };

    /**
    * 初始化表格的列
    */
    var fucStrArr = ['加热','制冷','除湿','加湿','消毒','净化']
    Machine.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'machineId', hide: false, sort:true, title: 'ID'},
            {field: 'machineName', sort: false, title: '设备名称'},
            {field: 'machineFunction', sort: false, title: '设备功能',templet:function (data) {
                var fuc = data.machineFunction
                var fucArr = fuc.split(",")
                var fucStr = "";
                for(var i =0;i<fucArr.length;i++){
                    if(fucArr[i]=="1"){
                        fucStr = fucStr + fucStrArr[i] + ","
                    }
                }
                return fucStr.substring(0,fucStr.length-1)
            }},
            {field: 'machineImg', sort: false, title: '设备图标',templet:function (data) {
                return '<img src="'+data.machineImg+'" width="30px" height="30px">'
            }},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };



    /**
    * 渲染表格
    */
    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Machine.tableId,
        url: Feng.ctxPath + '/machine/list',
        page: true,
        height: "full-98",
        cellMinWidth: 100,
        cols: Machine.initColumn()
    });
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Machine.openAdd();
    });
    //搜索
    $('#btnSearch').click(function () {
        Machine.search()
    })
    //添加
    Machine.openAdd = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加',
            content: Feng.ctxPath + '/machine/machine_add',
            end: function () {
                admin.getTempData('formOk') && table.reload(Machine.tableId);
            }
        });
    }
    //修改
    Machine.onEdit = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '编辑',
            content: Feng.ctxPath + '/machine/machine_edit?machineId=' + data.machineId,
            end: function () {
                admin.getTempData('formOk') && table.reload(Machine.tableId);
            }
        });
    }
    //查询
    Machine.search = function () {
        var queryData = {};
        queryData['machineName'] = $("#machineName").val();
        table.reload(Machine.tableId, {where: queryData});
    }

    //删除
    Machine.onDelete = function (data) {
        var operation = function () {
        var ajax = new $ax(Feng.ctxPath + "/machine/delete", function () {
            Feng.success("删除成功!");
            Machine.search()
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("machineId", data.machineId);
        ajax.start();
        };
        Feng.confirm("是否删除菜单" + data.machineName + "?", operation);
    }

    // 工具条点击事件
    table.on('tool(' + Machine.tableId + ')', function (obj) {
        var data = obj.data;
            var layEvent = obj.event;
        if (layEvent === 'edit') {
            Machine.onEdit(data);
        } else if (layEvent === 'delete') {
            Machine.onDelete(data);
        }
    });


});

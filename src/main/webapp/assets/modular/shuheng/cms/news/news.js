layui.use(['table', 'admin', 'ax', 'ztree'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var $ZTree = layui.ztree;

    /**
    * 基础字典管理
    */
    var News = {
        tableId: "newsTable",
        condition:{
            columnId:""
        }
    };

    //初始化左侧栏目树
    var ztree = new $ZTree("columnTree", "/column/selectColumnTreeList");
    ztree.bindOnClick(News.onClickColumn);
    ztree.init();

    /**
     * 选择栏目时
     */
    News.onClickColumn = function (e, treeId, treeNode) {
        News.condition.columnId = treeNode.id;
        News.search();
    };

    /**
    * 初始化表格的列
    */
    News.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'newsId', hide: true, title: 'ID'},
            {field: 'newsTitle', sort: false, title: '标题'},
            {field: 'newsSource', sort: false, title: '来源'},
            {field: 'releaseTime', sort: false, title: '发布时间'},
            {field: 'newsModel', sort: false, title: '模型'},
            {field: 'columnId', sort: false, title: '栏目'},
            {field: 'topLevel', sort: false, title: '固定级别'},
            {field: 'topLevelTime', sort: false, title: '固定到期时间'},
            {field: 'newsPush', sort: false, title: '推荐'},
            {field: 'updateTime', sort: false, title: '更新时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };



    /**
    * 渲染表格
    */
    // 渲染表格
    var tableResult = table.render({
        elem: '#' + News.tableId,
        url: Feng.ctxPath + '/news/list',
        page: true,
        height: "full-98",
        cellMinWidth: 100,
        cols: News.initColumn()
    });
    // 新闻添加按钮点击事件
    $('#btnAddNews').click(function () {
        News.openAdd("news");
    });
    // 新闻添加按钮点击事件
    $('#btnAddVideo').click(function () {
        News.openAdd("video");
    });
    // 新闻添加按钮点击事件
    $('#btnAddDownload').click(function () {
        News.openAdd("download");
    });
    //搜索
    $('#btnSearch').click(function () {
        News.search()
    })
    //添加
    News.openAdd = function (model) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加',
            area:['900px'],
            content: Feng.ctxPath + '/news/news_add?newsModel='+model,
            end: function () {
                admin.getTempData('formOk') && table.reload(News.tableId);
            }
        });
    }
    //修改
    News.onEdit = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '编辑',
            content: Feng.ctxPath + '/news/news_edit?newsId=' + data.newsId,
            end: function () {
                admin.getTempData('formOk') && table.reload(News.tableId);
            }
        });
    }
    //查询
    News.search = function () {
        var queryData = {};
        queryData['newsName'] = $("#newsName").val();
        queryData['columnId'] = News.condition.columnId;
        table.reload(News.tableId, {where: queryData});
    }

    //删除
    News.onDelete = function (data) {
        var operation = function () {
        var ajax = new $ax(Feng.ctxPath + "/news/delete", function () {
            Feng.success("删除成功!");
            News.search()
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("newsId", data.newsId);
        ajax.start();
        };
        Feng.confirm("是否删除菜单" + data.newsName + "?", operation);
    }

    // 工具条点击事件
    table.on('tool(' + News.tableId + ')', function (obj) {
        var data = obj.data;
            var layEvent = obj.event;
        if (layEvent === 'edit') {
            News.onEdit(data);
        } else if (layEvent === 'delete') {
            News.onDelete(data);
        }
    });


});

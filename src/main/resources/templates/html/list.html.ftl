@layout("/common/_container.html",{js:["/assets/modular/shenjiang/${table.entityPath}/${table.entityPath}.js?v=1"]}){

<div class="layui-body-header">
    <span class="layui-body-header-title">管理</span>
</div>

<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-sm12 layui-col-md12 layui-col-lg12">
            <div class="layui-card">
                <div class="layui-card-body">
                    <div class="layui-form toolbar">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <input id="${table.entityPath}Name" class="layui-input" type="text" placeholder="名称" autocomplete="off"/>
                            </div>
                            <div class="layui-inline">
                                <button id="btnSearch" class="layui-btn icon-btn"><i class="layui-icon">&#xe615;</i>搜索</button>
                                <button id="btnAdd" class="layui-btn icon-btn"><i class="layui-icon">&#xe654;</i>添加</button>
                            </div>
                        </div>
                    </div>
                    <table class="layui-table" id="${table.entityPath}Table" lay-filter="${table.entityPath}Table"></table>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/html" id="tableBar">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">修改</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
</script>
@}
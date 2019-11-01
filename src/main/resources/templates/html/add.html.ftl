@layout("/common/_container.html",{bg:"bg-white",js:["/assets/modular/shuheng/${table.entityPath}/${table.entityPath}_add.js"]}){
<style type="text/css">

</style>

<form id="${table.entityPath}Form" lay-filter="${table.entityPath}Form" class="layui-form model-form" style="max-width: 700px;">

<#list table.fields as field>
    <#if field.keyFlag>
        <input type="hidden" name="${table.entityPath}Id"/>
    <#else>
    <#if field.name?ends_with("_img")>
        <div class="layui-form-item">
            <label class="layui-form-label"><#if field.comment!?length gt 0>${field.comment}</#if><span style="color: red;">*</span></label>
            <div class="layui-upload">
                <button type="button" class="layui-btn" id="uploadImg">上传<#if field.comment!?length gt 0>${field.comment}</#if></button>
                <div class="layui-upload-list">
                    <img class="layui-upload-img" id="imgShow">
                    <p id="imgText"></p>
                </div>
            </div>
            <input type="hidden" id="${table.entityPath}Img" name="${table.entityPath}Img" placeholder="" type="text" class="layui-input" lay-verify="" />
        </div>
    <#elseif "sort"==field.name>
        <div class="layui-form-item">
            <label class="layui-form-label">排序<span style="color: red;">*</span></label>
            <div class="layui-input-block">
                <input id="sort" name="sort" placeholder="请输入排序号码" style="width:130px" maxlength="3" type="number" class="layui-input" lay-verify="sort">
            </div>
        </div>
    <#elseif "create_time"==field.name || "create_user"==field.name || "update_time"==field.name ||"update_user"==field.name || "del_flag"==field.name>
    <#else>
        <div class="layui-form-item">
            <label class="layui-form-label">${field.comment}<span style="color: red;">*</span></label>
            <div class="layui-input-block">
                <input id="${field.propertyName}" name="${field.propertyName}" maxlength="30" placeholder="请输入${field.comment}" type="text" class="layui-input"
                       lay-verify="required" required/>
            </div>
        </div>
    </#if>
    </#if>
</#list>



    <div class="layui-form-item text-right">
        <button class="layui-btn" lay-filter="btnSubmit" lay-submit>保存</button>
        <button class="layui-btn layui-btn-primary" type="button" ew-event="closeDialog" id="backupPage">返回</button>
    </div>
</form>


@}
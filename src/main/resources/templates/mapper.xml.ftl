<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

<#if enableCache>
    <!-- 开启二级缓存 -->
    <cache type="org.mybatis.caches.ehcache.LoggingEhcache"/>

</#if>
<#if baseResultMap>
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${package.Entity}.${entity}">
<#list table.fields as field>
<#if field.keyFlag><#--生成主键排在第一位-->
        <id column="${field.name}" property="${field.propertyName}" />
</#if>
</#list>
<#list table.commonFields as field><#--生成公共字段 -->
    <result column="${field.name}" property="${field.propertyName}" />
</#list>
<#list table.fields as field>
<#if !field.keyFlag><#--生成普通字段 -->
        <result column="${field.name}" property="${field.propertyName}" />
</#if>
</#list>
    </resultMap>

</#if>

<#if baseColumnList>
    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
<#list table.fields as field>
    <#if !field_has_next>
        ${field.name} as ${field.propertyName}
    <#else>
        ${field.name} as ${field.propertyName},
    </#if>
</#list>
    </sql>

</#if>

    <select id="select${entity}" resultType="map">
        select
        <include refid="Base_Column_List"/>
        from m_${table.entityPath}
        where 1=1
        <if test="${table.entityPath}Name != null and ${table.entityPath}Name != ''">
            and ${table.entityPath}_name like CONCAT('%',${r'#'}{${table.entityPath}Name},'%')
        </if>
        <#list table.commonFields as field>
        <#if field.name=='sort'>
        order by sort ASC
        </#if>
        </#list>
    </select>
</mapper>
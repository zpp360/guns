package cn.stylefeng.guns.core.generator;


import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * mybaits-plus代码生成器
 * </p>
 */
public class Generator {

    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        // 选择 freemarker 引擎，默认 Veloctiy
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setAuthor("zhengpp");
        gc.setOutputDir("F://");
        gc.setFileOverride(false);// 是否覆盖同名文件，默认是false
        gc.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        //设置entity主键生成策略
        gc.setIdType(IdType.ID_WORKER);
        /* 自定义文件命名，注意 %s 会自动填充表实体属性！ */
        gc.setMapperName("%sMapper");
        // gc.setXmlName("%sDao");
        gc.setServiceName("%sService");
        //gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return super.processTypeConvert(fieldType);
            }
        });
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("sh123456");
        dsc.setUrl("jdbc:mysql://192.168.0.116:3306/guns?useUnicode=true&characterEncoding=utf8");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        strategy.setTablePrefix(new String[] { "m_" });// 表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(new String[] { "m_column"}); // 需要生成的表
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        // 自定义实体父类
        // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "id", "age" });
        // 自定义 mapper 父类
         strategy.setSuperMapperClass("com.baomidou.mybatisplus.core.mapper.BaseMapper");
        // 自定义 service 父类
         strategy.setSuperServiceClass("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl");
        // 自定义 service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
         strategy.setSuperControllerClass("cn.stylefeng.roses.core.base.controller.BaseController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // strategy.setEntityBuilderModel(true);
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("cn.stylefeng.guns.modular.shuheng");
        pc.setController("controller");
        pc.setXml("mapper.mapping");
//        pc.setModuleName("test");
        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };
//
//        // 自定义 xxList.jsp 生成
        List<FileOutConfig> focList = new ArrayList<>();
        //列表页面
        focList.add(new FileOutConfig("templates/html/list.html.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return "F://html//" + tableInfo.getEntityName().toLowerCase() + ".html";
            }
        });
        //添加页面
        focList.add(new FileOutConfig("templates/html/add.html.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return "F://html//" + tableInfo.getEntityName().toLowerCase() + "_add.html";
            }
        });
        //更新页面
        focList.add(new FileOutConfig("templates/html/edit.html.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return "F://html//" + tableInfo.getEntityName().toLowerCase() + "_edit.html";
            }
        });

        //列表js
        focList.add(new FileOutConfig("templates/js/list.js.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return "F://html//" + tableInfo.getEntityName().toLowerCase() + ".js";
            }
        });

        //添加js
        focList.add(new FileOutConfig("templates/js/add.js.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return "F://html//" + tableInfo.getEntityName().toLowerCase() + "_add.js";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        //修改js
        focList.add(new FileOutConfig("templates/js/edit.js.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return "F://html//" + tableInfo.getEntityName().toLowerCase() + "_edit.js";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
//
//        // 调整 xml 生成目录演示
//        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return "/develop/code/xml/" + tableInfo.getEntityName() + ".xml";
//            }
//        });
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);
//
//        // 关闭默认 xml 生成，调整生成 至 根目录
//        TemplateConfig tc = new TemplateConfig();
//        tc.setXml(null);
//        mpg.setTemplate(tc);

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
         TemplateConfig tc = new TemplateConfig();
         tc.setController("templates/controller.java");
         tc.setEntity("templates/entity.java");
         tc.setMapper("templates/mapper.java");
         tc.setXml("templates/mapper.xml");
         tc.setService("templates/service.java");
         tc.setServiceImpl(null);
//         如上任何一个模块如果设置 空 OR Null 将不生成该模块。
         mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();

        // 打印注入设置【可无】
//        System.err.println(mpg.getCfg().getMap().get("abc"));
    }

}

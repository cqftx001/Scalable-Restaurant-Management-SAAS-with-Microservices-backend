package com.itheima.restkeeper.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.itheima.restkeeper.utils.EmptyUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @Description：代码生成器
 */
public class CodeGenerator {

    public static void autoGenerator(){

        //用来获取generrator.properties文件的配置信息
        final ResourceBundle rb = ResourceBundle.getBundle("generrator");

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath =rb.getString("projectPath");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(rb.getString("author"));
        gc.setOpen(false);
        gc.setFileOverride(true);
        //指定时间处理类型
        gc.setDateType(DateType.ONLY_DATE);
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        //数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(rb.getString("url"));
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername(rb.getString("userName"));
        dsc.setPassword(rb.getString("password"));
        mpg.setDataSource(dsc);

        //包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(rb.getString("moduleName"));
        pc.setParent(rb.getString("parent"));
        pc.setController("web");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setEntity("pojo");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
//        focList.add(new FileOutConfig(templatePath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()+"/sqlmap"
//                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
//            }
//        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        if ("true".equals(rb.getString("entity"))){
            String entityFtlPath = rb.getString("entity.ftl.path");
            if (!EmptyUtil.isNullOrEmpty(entityFtlPath)){
                templateConfig.setEntity(entityFtlPath);
            }
        }else {
            templateConfig.setEntity(null);
        }

        if ("true".equals(rb.getString("mapper"))){
            String mapperFtlPath = rb.getString("mapper.ftl.path");
            if (!EmptyUtil.isNullOrEmpty(mapperFtlPath)){
                templateConfig.setMapper(mapperFtlPath);
            }
        }else {
            templateConfig.setMapper(null);
        }

        if ("true".equals(rb.getString("service"))){
            String serviceFtlPath = rb.getString("service.ftl.path");
            if (!EmptyUtil.isNullOrEmpty(serviceFtlPath)){
                templateConfig.setService(serviceFtlPath);
            }
        }else {
            templateConfig.setService(null);
        }

        if ("true".equals(rb.getString("serviceImp"))){
            String serviceImpFtlPath = rb.getString("serviceImp.ftl.path");
            if (!EmptyUtil.isNullOrEmpty(serviceImpFtlPath)){
                templateConfig.setServiceImpl(serviceImpFtlPath);
            }
        }else {
            templateConfig.setServiceImpl(null);
        }

        if ("true".equals(rb.getString("controller"))){
            String controllerFtlPath = rb.getString("controller.ftl.path");
            if (!EmptyUtil.isNullOrEmpty(controllerFtlPath)){
                templateConfig.setController(controllerFtlPath);
            }
        }else {
            templateConfig.setController(null);
        }
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(rb.getString("SuperEntityClass"));
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 写于父类中的公共字段
        String[] SuperEntityColumns = rb.getString("superEntityColumns").split(",");
        strategy.setSuperEntityColumns(SuperEntityColumns);
        strategy.setInclude(rb.getString("tableName").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        String tablePrefix = rb.getString("tablePrefix");
        if (tablePrefix!=null){
            strategy.setTablePrefix(tablePrefix);
        }
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();

    }
}

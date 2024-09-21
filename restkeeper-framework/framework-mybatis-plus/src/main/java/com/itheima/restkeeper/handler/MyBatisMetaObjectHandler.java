package com.itheima.restkeeper.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.itheima.restkeeper.constant.SuperConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @Description：自动填充
 */
@Slf4j
public class MyBatisMetaObjectHandler implements MetaObjectHandler {

    @Autowired
    IdentifierGenerator identifierGenerator;

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始插入填充.....");
        Object shardingId = getFieldValByName("shardingId", metaObject);
        if (metaObject.hasSetter("shardingId")&&shardingId==null) {
            Long shardingIdVal = (Long) identifierGenerator.nextId(metaObject);
            this.setFieldValByName("shardingId",shardingIdVal,metaObject);
        }
        Object enableFlag = getFieldValByName("enableFlag", metaObject);
        if (metaObject.hasSetter("enableFlag")&&enableFlag==null) {
            this.setFieldValByName("enableFlag", SuperConstant.YES,metaObject);
        }
        if (metaObject.hasSetter("createdTime")) {
            this.setFieldValByName("createdTime",new Date(),metaObject);
        }
        if (metaObject.hasSetter("updatedTime")) {
            this.setFieldValByName("updatedTime",new Date(),metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("开始更新填充.....");
        if (metaObject.hasSetter("updatedTime")) {
            this.setFieldValByName("updatedTime",new Date(),metaObject);
        }
    }
}

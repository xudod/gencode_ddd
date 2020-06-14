package com.xudod.gen_code.config;

import java.sql.Timestamp;

import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * 填充器
 * 目前填充新建时间和更新时间
 * Created by xuyuzhao on 2020/04/11.
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyMetaObjectHandler.class);

	/*代码分界head TODO*/

	
    @Override
    public void insertFill(MetaObject metaObject) {
    	if (metaObject.hasSetter("createTime")) {
	        //避免使用metaObject.setValue()
	        this.setInsertFieldValByName("createTime", new Timestamp(System.currentTimeMillis()), metaObject);
    	}
    	if (metaObject.hasSetter("version")) {
	        this.setInsertFieldValByName("version", 1, metaObject);
    	}
    	if (metaObject.hasSetter("deleted")) {
	        this.setInsertFieldValByName("deleted", "0", metaObject);
    	}
    	if (metaObject.hasSetter("public_able")) {
	        this.setInsertFieldValByName("public_able", "0", metaObject);
    	}
    	if (metaObject.hasSetter("shared_able")) {
	        this.setInsertFieldValByName("shared_able", "0", metaObject);
    	}
    	if (metaObject.hasSetter("status")) {
	        this.setInsertFieldValByName("status", "0", metaObject);
    	}
    }

    @Override
    public void updateFill(MetaObject metaObject) {
    	if (metaObject.hasSetter("modifyTime")) {
	        //避免使用metaObject.setValue()
	        this.setInsertFieldValByName("modifyTime", new Timestamp(System.currentTimeMillis()), metaObject);
    	}
    }
    
    /*代码分界end TODO*/


}



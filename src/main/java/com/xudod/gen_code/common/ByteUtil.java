package com.xudod.gen_code.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ByteUtil {

	/**
	 * 对象转化为字节码
	 * <p>Title: getBytesFromObject</p>  
	 * <p>Description: </p>  
	 * @param obj
	 * @return
	 * @throws Exception
	 */
    public static byte[] getBytesFromObject(Serializable obj) throws Exception {
        if (obj == null) {
            return null;
        }
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);
        return bo.toByteArray();
    }
    
    /**
     * 字节码转化为对象
     * <p>Title: getObjectFromBytes</p>  
     * <p>Description: </p>  
     * @param objBytes
     * @return
     * @throws Exception
     */
    public static Object getObjectFromBytes(byte[] objBytes) throws Exception {
        if (objBytes == null || objBytes.length == 0) {
            return null;
        }
        ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
        ObjectInputStream oi = new ObjectInputStream(bi);
        return oi.readObject();
    }
	
}

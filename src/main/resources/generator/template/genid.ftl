package ${basePackageValue}.common;

import java.util.UUID;

public class GenId {
	  
    public static String getUUID() {
    	String uuidStr = UUID.randomUUID().toString().replace("-", "").substring(0,11);
    	SnowId snowId = new SnowId();
    	String nextId = "" + snowId.nextId();
        return  uuidStr + nextId;  
    } 
    
    public static String getUUID(long workerId, long datacenterId) {
    	String uuidStr = UUID.randomUUID().toString().replace("-", "").substring(0,11);
    	SnowId snowId = new SnowId(workerId, datacenterId);
    	String nextId = "" + snowId.nextId();
        return  uuidStr + nextId;
    } 
}
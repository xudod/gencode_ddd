package ${basePackageValue}.common;

public class BusinessUtils {

	public static void addUpDelResCheck(int res, String msg){
		if(res < 1){
			throw new BusinessException(res, msg);
		}
	}
	
}

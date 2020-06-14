package ${basePackageValue}.common;

public class DataCorrectCheck {

	/**
	 * 校验数据实体对象是否为null，如果为null，则抛异常
	 * <p>
	 * Title: PojoIsNotNullCheck
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param pojo
	 * @param msg
	 */
	public static void PojoIsNotNullCheck(Object pojo, String msg) {
		if (null == pojo) {
			throw new BusinessException(455, msg);
		}
	}

	public static void PojoIsNotNullCheck(String msg, Object... pojo) {
		for (int i = 0; i < pojo.length; i++) {
			if (null == pojo[i] || "" == pojo[i]) {
				throw new BusinessException(455, msg);
			}
		}
	}
}

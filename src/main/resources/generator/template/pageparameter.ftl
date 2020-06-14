package ${basePackageValue}.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 简单分页模型
 * <p>Title: PageParameter</p>  
 * <p>Description: </p>  
 * @author 许宇召
 * @date 2019年10月27日
 */
@ApiModel(value = "分页实体")
public class PageParameter<T> implements Serializable {

    /** serialVersionUID*/  
	private static final long serialVersionUID = -5472254992397417135L;
	
	/**
	 * 查询条件
	 */
	@ApiModelProperty(example = "查询条件", position = 0)
	private String query;
	
	/**
     * 查询数据列表
     */
	@ApiModelProperty(example = "查询结果", position = 1)
    private List<T> records = Collections.emptyList();

    /**
     * 总数
     */
	@ApiModelProperty(hidden = true)
    private long total = 0;
    
    /**
     * 每页显示条数，默认 10
     */
    @ApiModelProperty(example = "每页显示条数，默认 10", position = 2)
    private long size = 10;

    /**
     * 当前页
     */
    @ApiModelProperty(example = "当前页，默认 1", position = 3)
    private long current = 1;

    /**
     * 排序字段信息
     */
	@ApiModelProperty(hidden = true)
    private List<OrderItem> orders = new ArrayList<>();

    /**
     * 自动优化 COUNT SQL
     */
	@ApiModelProperty(value = "bu", hidden = true, example = "不")
    private boolean optimizeCountSql = true;
    /**
     * 是否进行 count 查询
     */
	@ApiModelProperty(value = "bu", hidden = true, example = "不")
    private boolean isSearchCount = true;

    public PageParameter() {
    }

    /**
     * 分页构造函数
     *
     * @param current 当前页
     * @param size    每页显示条数
     */
    public PageParameter(long current, long size) {
        this(current, size, 0);
    }

    public PageParameter(long current, long size, long total) {
        this(current, size, total, true);
    }

    public PageParameter(long current, long size, boolean isSearchCount) {
        this(current, size, 0, isSearchCount);
    }

    public PageParameter(long current, long size, long total, boolean isSearchCount) {
        if (current > 1) {
            this.current = current;
        }
        this.size = size;
        this.total = total;
        this.isSearchCount = isSearchCount;
    }

    /**
     * 是否存在上一页
     *
     * @return true / false
     */
    public boolean hasPrevious() {
        return this.current > 1;
    }

    /**
     * 是否存在下一页
     *
     * @return true / false
     */
    public boolean hasNext() {
        return this.current < this.geetPages();
    }

    /**
     * 当前分页总页数
     */
    public long geetPages() {
        if (getSize() == 0) {
            return 0L;
        }
        long pages = getTotal() / getSize();
        if (getTotal() % getSize() != 0) {
            pages++;
        }
        return pages;
    }
    
    public List<T> getRecords() {
        return this.records;
    }

    public PageParameter<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    public long getTotal() {
        return this.total;
    }

    public PageParameter<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    public long getSize() {
        return this.size;
    }

    public PageParameter<T> setSize(long size) {
        this.size = size;
        return this;
    }

    public long getCurrent() {
        return this.current;
    }

    public PageParameter<T> setCurrent(long current) {
        this.current = current;
        return this;
    }

    /**
     * 添加新的排序条件，构造条件可以使用工厂：{@link OrderItem#build(String, boolean)}
     *
     * @param items 条件
     * @return 返回分页参数本身
     */
    public PageParameter<T> addOrder(OrderItem... items) {
        orders.addAll(Arrays.asList(items));
        return this;
    }

    /**
     * 添加新的排序条件，构造条件可以使用工厂：{@link OrderItem#build(String, boolean)}
     *
     * @param items 条件
     * @return 返回分页参数本身
     */
    public PageParameter<T> addOrder(List<OrderItem> items) {
        orders.addAll(items);
        return this;
    }
    
    public List<OrderItem> orders() {
        return getOrders();
    }

    public List<OrderItem> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderItem> orders) {
        this.orders = orders;
    }

    public boolean optimizeCountSql() {
        return optimizeCountSql;
    }

    public boolean iisSearchCount() {
        if (total < 0) {
            return false;
        }
        return isSearchCount;
    }

    public PageParameter<T> seetSearchCount(boolean isSearchCount) {
        this.isSearchCount = isSearchCount;
        return this;
    }

    public PageParameter<T> seetOptimizeCountSql(boolean optimizeCountSql) {
        this.optimizeCountSql = optimizeCountSql;
        return this;
    }

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
}


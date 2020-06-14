package ${basePackageValue}.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 目前配置了分页插件
 * Created by ${author} on ${date}.
 */
@Configuration
@MapperScan("${basePackageValue}.*.domain.mapper")
public class MybatisPlusConfig {

	/*代码分界head TODO*/
	
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        // 开启 count 的 join 优化,只针对 left join !!!
        return new PaginationInterceptor().setCountSqlParser(new JsqlParserCountOptimize(true));
    }
    
    /**
     * 乐观锁插件
     * <p>Title: optimisticLockerInterceptor</p>  
     * <p>Description: </p>  
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
    	return new OptimisticLockerInterceptor();
    }
    
    
    /*代码分界end TODO*/
}




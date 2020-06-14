server:
  port: ${projectPort}
eureka:
  client:
    serviceUrl:
      defaultZone: http://eureka:eureka@${eurekaIp}:${eurekaPort}/eureka/
  instance:
    prefer-ip-address: true
spring:
  profiles: ${runType}
  application:
    name: ${eurekaName}
  datasource:
    driver-class-name: ${databaseDriver}
    url: ${databaseUrl}
    username: ${databaseUserName}
    password: ${databasePassword}
mybatis-plus:
  mapper-locations: classpath:mapper/*.xml  #注意：一定要对应mapper映射xml文件的所在路径
  type-aliases-package: ${basePackageValue}.*.${mapperPackageName}  # 注意：对应实体类的路径
logging:
  level:
    ${basePackageValue}.*.${mapperPackageName}:
      debug
feign:
  client:
    config:                                         
    # 默认为所有的feign client做配置(注意和上例github-client是同级的)
      default:                                      
        connectTimeout: 5000                        # 连接超时时间
        readTimeout: 5000                           # 读超时时间设置 
snowid:
  workerId: 1     #工作机器ID
  datacenterId: ${projectDatacenterId}   # 序列号

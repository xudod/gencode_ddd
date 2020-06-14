package com.xudod.gen_code.core_process.domain.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xudod.gen_code.column_info.domain.entity.po.ColumnInfo;
import com.xudod.gen_code.column_info.domain.service.ColumnInfoService;
import com.xudod.gen_code.common.DbUtil;
import com.xudod.gen_code.core_process.domain.entity.GenDBInfo;
import com.xudod.gen_code.domain_info.domain.entity.po.DomainInfo;
import com.xudod.gen_code.domain_info.domain.service.DomainInfoService;
import com.xudod.gen_code.project_info.domain.entity.po.ProjectInfo;
import com.xudod.gen_code.project_info.domain.service.ProjectInfoService;
import com.xudod.gen_code.table_base_info.domain.entity.po.TableBaseInfo;
import com.xudod.gen_code.table_base_info.domain.service.TableBaseInfoService;

@Service("genDBService")
public class GenDBImp implements GenDBService {
	
	@Autowired
	private ProjectInfoService projectInfoService;
	
	@Autowired
	private DomainInfoService domainInfoService;
	
	@Autowired
	private TableBaseInfoService tableBaseInfoService;
	
	@Autowired
	private ColumnInfoService columnInfoService;

	@Override
	public boolean genDB(String projectId) {
		
		GenDBInfo genDBInfo = new GenDBInfo(projectId);
		getProjectInfo(genDBInfo);
		
		//当项目生成数据库后，则不允许再次批量生成数据库。要给一个标记，表示数据库初始化完成。
		//生成数据库时，先判断是否存在库，如果存在库则给出提示
		//数据库生成只针对开发环境，其他环境数据库，要用运维的方式进行处理。
		//生成数据表时，先判断是否存在表，如果存在表，则生成表更新语句
		
		// 将该项目所要生成的所有sql进行生成
		// 一个领域一个库，领域里生成所有该领域的表。
		//show databases like 'db_name';
		//CREATE DATABASE 数据库名;
		
		genOnlyDb(genDBInfo);
		genTable(genDBInfo);
		return false;
	}

	private void genOnlyDb(GenDBInfo genDBInfo) {
		try {
			ProjectInfo p = genDBInfo.getProjectInfo();
			String dbUrl = DbUtil.createDatabaseUrl(p.getDbIp(), p.getDbPort(), "init_project");
			Connection conn = DbUtil.getConnection(dbUrl, p.getDbUser(), p.getDbPassword());
			Statement stat = conn.createStatement();
			stat = (Statement) conn.createStatement();
			String existDbStr = "";
			List<DomainInfo> domainInfoList = genDBInfo.getDomainInfoList();
			for (DomainInfo domainInfo : domainInfoList) {
				ResultSet resultSet = stat.executeQuery("show databases like \"" + domainInfo.getDbName() + "_new\"");
				if (resultSet.next()) {
					existDbStr += domainInfo.getDbName();
				}
			}
			if(!"".equals(existDbStr)) {
				stat.close();
				DbUtil.closeConnection(conn);
				throw new RuntimeException("目标库中已经存在：" + existDbStr + "数据库！请检查！");
			}
			for (DomainInfo domainInfo : domainInfoList) {
				stat.executeUpdate("CREATE DATABASE " + domainInfo.getDbName() + "_new");
			}
			stat.close();
			DbUtil.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void genTable(GenDBInfo genDBInfo) {
			List<DomainInfo> domainInfoList = genDBInfo.getDomainInfoList();
			List<TableBaseInfo> tableBaseInfoList = genDBInfo.getTableBaseInfoList();
			List<ColumnInfo> columnInfoList = genDBInfo.getColumnInfoList();
			for (DomainInfo domainInfo : domainInfoList) {
				//用领域中的数据库信息连接数据库
				String dbUrl = DbUtil.createDatabaseUrl(domainInfo.getDbIp(), domainInfo.getDbPort(), domainInfo.getDbName() + "_new");
				Connection conn = DbUtil.getConnection(dbUrl, domainInfo.getDbUser(), domainInfo.getDbPassword());
				Statement stat = null;
				try {
					stat = conn.createStatement();
					String id = domainInfo.getId();
					List<TableBaseInfo> tableList = tableBaseInfoList.stream().filter(x-> (id.equals(x.getDomainId()))).collect(Collectors.toList());
					for (TableBaseInfo tableBaseInfo : tableList) {
						String tableId = tableBaseInfo.getId();
						List<ColumnInfo> ColumnList = columnInfoList.stream().filter(x-> (tableId.equals(x.getTableId()))).collect(Collectors.toList());
						if(null == ColumnList || ColumnList.size() == 0) {
							continue;
						}
						//根据表名和字段信息，生成创建表sql
						String createTableStr = "CREATE TABLE IF NOT EXISTS `" + tableBaseInfo.getTableName() + "`  (\r\n";
							for (ColumnInfo columnInfo : ColumnList) {
								boolean a = (columnInfo.getColumnType().equals("char") || columnInfo.getColumnType().equals("varchar"));
								boolean b = "1".equals(columnInfo.getNullIs());
								createTableStr += 
										"`" + columnInfo.getName() + "` " +
										columnInfo.getColumnType() + "(" + 
										columnInfo.getColumnLen() + ")" + 
										(a ? " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci " : "") +
										(b ? "NOT NULL" : "NULL DEFAULT NULL") + ",\r\n";
							}
							createTableStr += 
							"  PRIMARY KEY (`id`) USING BTREE\r\n" + 
							") ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;";
						stat.executeUpdate(createTableStr);
					}
					DbUtil.closeStatement(stat);
					DbUtil.closeConnection(conn);
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					DbUtil.closeStatement(stat);
					DbUtil.closeConnection(conn);
				}
			}
		
	}
	
	// TODO 待改进，其中for循环能不能抽成一个方法
	// TODO 有很多个类继承了GenProjectInfo类，能不能将这个方法抽成公共方法。
	private void getProjectInfo(GenDBInfo genDBInfo) {
		String projectId = genDBInfo.getProjectId();
		ProjectInfo getbykey = projectInfoService.getbykey(projectId);
		List<DomainInfo> domainList = domainInfoService.getDomainListByProjectId(projectId);
		List<String> domainIds = new ArrayList<String>();
		for (DomainInfo domainInfo : domainList) {
			domainIds.add(domainInfo.getId());
		}
		List<TableBaseInfo> tableList = tableBaseInfoService.getTableByDomainIds(domainIds);
		List<ColumnInfo> columnList = columnInfoService.getTableColumnByTableIds(tableList);
		genDBInfo.setProjectInfo(getbykey);
		genDBInfo.setDomainInfoList(domainList);
		genDBInfo.setTableBaseInfoList(tableList);
		genDBInfo.setColumnInfoList(columnList);
	}
	
}

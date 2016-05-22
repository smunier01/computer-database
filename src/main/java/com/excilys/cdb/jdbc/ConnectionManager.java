package com.excilys.cdb.jdbc;

import java.sql.Connection;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

@Component
public class ConnectionManager {

    @Resource(name="HikariDatasource")
    private DataSource dataSource;

    public Connection getConnection() {
        return DataSourceUtils.getConnection(this.dataSource);
    }

}

package it.unicam.cs.ids;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

@RestController
public class TestController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/db-info")
    public String getDbInfo() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            return String.format("""
                Database Connection Successful!
                ================================
                URL: %s
                Username: %s
                Database: %s %s
                Driver: %s %s
                Isolation Level: %d
                Read-Only: %b
                """,
                    metaData.getURL(),
                    metaData.getUserName(),
                    metaData.getDatabaseProductName(),
                    metaData.getDatabaseProductVersion(),
                    metaData.getDriverName(),
                    metaData.getDriverVersion(),
                    conn.getTransactionIsolation(),
                    conn.isReadOnly());
        }
    }
}
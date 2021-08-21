package com.fred.code.generator.util;

import com.fred.code.generator.pojo.FieldInfo;
import com.fred.code.generator.pojo.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxiaokun
 * @version 1.0.0
 * @date 2021/8/19 15:06
 */
@Slf4j
@Component
public class DatabaseUtil {


    private static final String SQL = "SELECT * FROM ";

    static {
//        try {
//            Class.forName("DRIVER");
//        } catch (ClassNotFoundException e) {
//            log.error("can not load jdbc driver", e);
//        }
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("URL", "USERNAME", "PASSWORD");
        } catch (SQLException e) {
            log.error("get connection failure", e);
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     *
     * @param conn
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                log.error("close connection failure", e);
            }
        }
    }

    /**
     * 获取数据库下的所有表名
     */
    public static List<TableInfo> getTableInfos() {
        List<TableInfo> tableInfos = new ArrayList<>();
        Connection conn = getConnection();
        ResultSet rs = null;
        try {
            //获取数据库的元数据
            DatabaseMetaData db = conn.getMetaData();
            //从元数据中获取到所有的表名
            rs = db.getTables(null, "%", "%", new String[]{"TABLE"});
            while (rs.next()) {
                TableInfo tableInfo = new TableInfo();
                String tableName = rs.getString("TABLE_NAME");
                tableInfo.setName(tableName);
                tableInfo.setComment(getCommentByTableName(conn, tableName));
                tableInfos.add(tableInfo);
            }
        } catch (SQLException e) {
            log.error("getTableNames failure", e);
        } finally {
            try {
                rs.close();
                closeConnection(conn);
            } catch (SQLException e) {
                log.error("close ResultSet failure", e);
            }
        }
        return tableInfos;
    }

    /**
     * 获取表中字段的所有注释
     *
     * @param tableName
     * @return
     */
    public static List<FieldInfo> getColumnInfo(String tableName) {
        Connection conn = getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        //列名注释集合
        List<FieldInfo> fields = new ArrayList<>();
        ResultSet rs = null;
        try {
            pStemt = conn.prepareStatement(tableSql);
            rs = pStemt.executeQuery("show full columns from " + tableName);
            while (rs.next()) {
                FieldInfo field = new FieldInfo();
                String name = underlineToCamel(rs.getString("Field"));
                field.setName(name);

                fillFieldTypeAndLength(field, rs.getString("Type"));

                String aNull = rs.getString("Null");
                field.setComment(rs.getString("Comment"));

                if ("YES".equals(aNull)) {
                    field.setNullable(Boolean.TRUE);
                } else if ("NO".equals(aNull)) {
                    field.setNullable(Boolean.FALSE);
                }
                fields.add(field);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    log.error("getColumnComments close ResultSet and connection failure", e);
                }
            }
        }
        return fields;
    }

    public static String getCommentByTableName(Connection conn, String tableName) {
        Statement stmt = null;
        String comment = "";
        try {
            stmt = conn.createStatement();

        Map<String, String> map = new HashMap<String, String>();
        ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE " + tableName);
        if (rs != null && rs.next()) {
            String create = rs.getString(2);
            comment = parse(create);
        }
        rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return comment;
    }

    private static String parse(String all) {
        String comment = null;
        int index = all.indexOf("COMMENT='");
        if (index < 0) {
            return "";
        }
        comment = all.substring(index + 9);
        comment = comment.substring(0, comment.length() - 1);
        return new String(comment.getBytes(Charset.defaultCharset()));
    }

    public static String tableNameToEntityName(String tableName) {
        tableName = tableName.toLowerCase();

        if (tableName.contains("t_")) {
            tableName = tableName.substring(2, tableName.length());
        }
        String camelName = "";
        if (tableName.contains("_")) {
            String[] words = tableName.split("_");

            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                if (word.length() > 0) {
                    camelName += StringUtil.upperFirstCase(word);
                }
            }
        } else {
            camelName = tableName;
        }
        return StringUtil.upperFirstCase(camelName);
    }

    private static String underlineToCamel(String name) {
        name = name.toLowerCase();
        String camelName = "";
        if (name.contains("_")) {
            String[] words = name.split("_");

            for (int i = 0; i < words.length; i++) {
                String word = words[i];

                if (i == 0) {
                    camelName = word;
                } else {
                    if (word.length() > 0) {
                        camelName += StringUtil.upperFirstCase(word);
                    }
                }
            }
        } else {
            camelName = name;
        }
        return StringUtil.lowerFirstCase(camelName);
    }

    public static void main1(String[] args) {
        System.out.println(underlineToCamel("cn_name"));
        System.out.println(underlineToCamel("device_name"));
    }

    private static void fillFieldTypeAndLength(FieldInfo field, String type) {
        if (type.startsWith("varchar")) {
            String length = type.substring("varchar(".length(), type.length() - 1);
            type = "String";
            field.setLength(length);
        } else if (type.startsWith("int")) {
            String length = type.substring("int(".length(), type.length() - 1);
            type = "Integer";
            field.setLength(length);
        } else if (type.startsWith("tinyint")) {
            String length = type.substring("tinyint(".length(), type.length() - 1);
            // tinyint(1) 映射为Boolean类型， 大于1的映射为Integer
            if ("1".equals(length)) {
                type = "Boolean";
            } else {
                type = "Integer";
            }
        } else if (type.startsWith("bigint")) {
            String length = type.substring("bigint(".length(), type.length() - 1);
            type = "Long";
            field.setLength(length);
        } else if (type.startsWith("decimal")) {
            type = "BigDecimal";
        } else if ("double".equals(type)) {
            type = "Double";
        } else if ("date".equals(type)) {
            type = "LocalDate";
        } else if ("time".equals(type)) {
            type = "LocalTime";
        } else if ("datetime".equals(type)) {
            type = "LocalDateTime";
        }
        field.setType(type);
    }

    public static void main(String[] args) {
        List<TableInfo> tableInfos = getTableInfos();
        System.out.println("tableInfos:" + tableInfos);
        for (TableInfo tableInfo : tableInfos) {
            for (FieldInfo field : getColumnInfo(tableInfo.getName())) {
                System.out.println("column:" + field);

            }
        }
    }
}

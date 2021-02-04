package cn.bluetech.gragas.utils;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

/**
 * @author xu
 * @date 2020/3/31 18:26
 */
public class ExpandMySQL5Dialect extends MySQL5Dialect {

    public ExpandMySQL5Dialect(){
        super();

        registerFunction("json_extract",
                new SQLFunctionTemplate(StandardBasicTypes.STRING, "JSON_EXTRACT(?1, ?2)"));
        registerFunction("json_extract_long",
                new SQLFunctionTemplate(StandardBasicTypes.LONG, "JSON_EXTRACT(?1, ?2)"));
        registerFunction("json_extract_integer",
                new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "JSON_EXTRACT(?1, ?2)"));
        registerFunction("json_extract_double",
                new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "JSON_EXTRACT(?1, ?2)"));

        registerFunction("json_contains",
                new StandardSQLFunction("JSON_CONTAINS", StandardBasicTypes.BOOLEAN));

        registerFunction("json_contains_path",
                new StandardSQLFunction("JSON_CONTAINS_PATH", StandardBasicTypes.BOOLEAN));

        registerFunction("match_query",
                new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "MATCH (?1) AGAINST (?2)"));
    }

}

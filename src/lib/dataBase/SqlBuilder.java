package lib.dataBase;

public class SqlBuilder {
    private String sql;
    private final String entityName;

    public SqlBuilder(String entityName) {
        this.entityName = entityName;
        sql = "";
    }

    public SqlBuilder insert(String[] fields, String[] values){
        String fieldsConcat = valuesConcat(fields, "\"");
        String valuesConcat = valuesConcat(values, "");
        addCondition("INSERT INTO "+entityString(entityName) +
                " ("+ fieldsConcat + ") " +
                "VALUES("+ valuesConcat +")");
        return this;
    }

    public SqlBuilder selectField(String field){
        addCondition("SELECT "+field+" FROM "+entityString(entityName));
        return this;
    }

    public SqlBuilder selectAll(){
        addCondition("SELECT * FROM "+entityString(entityName));
        return this;
    }

    public SqlBuilder delete(){
        addCondition("DELETE FROM "+entityString(entityName));
        return this;
    }

    public SqlBuilder update(String field, String value){
        addCondition("UPDATE "+entityString(entityName)+" set "+ field +" = "+ value);
        return this;
    }

    public SqlBuilder join(String entityJoin, String fieldPrimary, String fieldJoin){
        addCondition(" JOIN "+entityString(entityJoin)+" ON "+
                entityString(entityName)+"."+fieldPrimary+" = "+entityString(entityJoin)+"."+fieldJoin);
        return this;
    }

    public SqlBuilder joinEqualsFieldName(String entityJoin, String field){
        return join(entityJoin, field, field);
    }

    public SqlBuilder joinEntityIsNameOfField(String entityJoin){
        return joinEqualsFieldName(entityJoin, entityJoin+"_id");
    }

    public SqlBuilder whereId(Integer id){
        return where(entityName+"_id", id.toString());
    }

    public SqlBuilder where(String field, String value){
        addCondition(" WHERE "+ field + " = " + value);
        return this;
    }

    public SqlBuilder whereAnd(String... conditions){
        return whereConditions(" AND ", conditions);
    }

    public SqlBuilder whereOr(String... conditions){
        return whereConditions(" OR ", conditions);
    }

    @Override
    public String toString() {
        System.out.println(sql);
        return sql+";";
    }

    private SqlBuilder whereConditions(String operator, String... conditions){
        String stream = String.join(operator, conditions);
        addCondition(" WHERE " + stream);
        return this;
    }

    private void addCondition(String part){
        sql = sql.concat(part);
    }

    private String valuesConcat(String[] values, String asp){
        String valuesConcat = "";
        for (String value : values) {
            valuesConcat = valuesConcat.concat(asp+value+asp+",");
        }
        return valuesConcat.substring(0, valuesConcat.length()-1);
    }

    private String entityString(String entityName){
        return "public.\""+ entityName +"\"";
    }
}

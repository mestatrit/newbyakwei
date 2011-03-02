package com.hk.frame.dao.query;

import java.util.ArrayList;
import java.util.List;

import com.hk.frame.dao.query.criteria.Expression;
import com.hk.frame.dao.query.criteria.ExpressionOp;
import com.hk.frame.dao.query.impl.QueryTable;
import com.hk.frame.dao.query.partition.Partition;
import com.hk.frame.dao.query.partition.PartitionTable;
import com.hk.frame.util.P;

public class TableHelper {

	private static String EQ = "=";

	private static String GE = ">=";

	private static String GT = ">";

	private static String LE = "<=";

	private static String LT = "<";

	private static String NOTEQ = "!=";

	private static String LIKE = "like";

	private static final String SELECT = "select ";

	private static final String FROM = " from ";

	private static final String WHERE = " where ";

	private static final String ORDERBY = " order by ";

	private static final String GROUPBY = " group by ";

	private static Expression lt(String field, Object value) {
		Expression e = ExpressionOp.lt(field, value);
		return e;
	}

	private static Expression like(String field, Object value) {
		Expression e = ExpressionOp.like(field, value);
		return e;
	}

	private static Expression eq(String field, Object value) {
		Expression e = ExpressionOp.eq(field, value);
		return e;
	}

	private static Expression notEq(String field, Object value) {
		Expression e = ExpressionOp.notEq(field, value);
		return e;
	}

	private static Expression ge(String field, Object value) {
		Expression e = ExpressionOp.ge(field, value);
		return e;
	}

	private static Expression le(String field, Object value) {
		Expression e = ExpressionOp.le(field, value);
		return e;
	}

	private static Expression gt(String field, Object value) {
		Expression e = ExpressionOp.gt(field, value);
		return e;
	}

	private static Expression[] createExpressions(String whereExpression) {
		String s = TableHelper.filterWhereExpression(whereExpression);
		String[] ex = s.split(":");
		Expression[] exps = new Expression[ex.length];
		int i = 0;
		for (String e : ex) {
			exps[i++] = TableHelper.createExpression(e);
		}
		return exps;
	}

	/**
	 * 返回表达式对对象
	 * 
	 * @param e param=value方式 ,使用preparedstatment时value是?
	 * @return
	 */
	private static Expression createExpression(String e) {
		Expression expression = null;
		if (e.indexOf(EQ) != -1) {
			String[] f_v = e.split(EQ);
			expression = eq(f_v[0], f_v[1]);
		}
		else if (e.indexOf(GT) != -1) {
			String[] f_v = e.split(GT);
			expression = gt(f_v[0], f_v[1]);
		}
		else if (e.indexOf(LT) != -1) {
			String[] f_v = e.split(LT);
			expression = lt(f_v[0], f_v[1]);
		}
		else if (e.indexOf(GE) != -1) {
			String[] f_v = e.split(GE);
			expression = ge(f_v[0], f_v[1]);
		}
		else if (e.indexOf(LE) != -1) {
			String[] f_v = e.split(LE);
			expression = le(f_v[0], f_v[1]);
		}
		else if (e.indexOf(NOTEQ) != -1) {
			String[] f_v = e.split(NOTEQ);
			expression = notEq(f_v[0], f_v[1]);
		}
		else if (e.indexOf(LIKE) != -1) {
			String[] f_v = e.split(LIKE);
			expression = like(f_v[0].trim(), f_v[1].trim());
		}
		return expression;
	}

	/**
	 * 给表达式中的value赋值
	 * 
	 * @param expressions
	 * @param paramList
	 */
	private static void buildExpressions(Expression[] expressions,
			List<Object> paramList) {
		int k = 0;
		for (Object v : paramList) {
			expressions[k++].setValue(v);
		}
	}

	public static List<Expression> createExpressionList(String whereExpression,
			List<Object> paramList) {
		Expression[] expressions = createExpressions(whereExpression);
		buildExpressions(expressions, paramList);
		return toExpressionList(expressions);
	}

	private static List<Expression> toExpressionList(Expression[] expressions) {
		List<Expression> whereFieldlist = new ArrayList<Expression>(
				expressions.length);
		for (int i = 0; i < expressions.length; i++) {
			whereFieldlist.add(expressions[i]);
		}
		return whereFieldlist;
	}

	private static String filterWhereExpression(String whereExpression) {
		String s = whereExpression.replaceAll(" and | or ", ":");
		char[] ch = s.toCharArray();
		StringBuilder nWhere = new StringBuilder();
		for (int i = 0; i < ch.length; i++) {
			if (ch[i] != ' ') {
				nWhere.append(ch[i]);
			}
		}
		return nWhere.toString();
	}

	public static String createDataSourceName(String tableName,
			QueryManager queryManager) {
		PartitionTable pt = buildPartitionTable(tableName, queryManager);
		return pt.getDatabase();
	}

	public static PartitionTable buildPartitionTable(String tableName,
			QueryManager queryManager) {
		Partition partition = queryManager.getPartitionFromTableName(tableName);
		if (partition == null) {
			throw new RuntimeException("no table [ " + tableName + " ] config");
		}
		QueryTable queryTable = new QueryTable(tableName);
		return buildPartitionTable(partition, queryTable, null);
	}

	public static String createDataSourceName(List<QueryTable> list,
			List<Expression> allFieldlist, QueryManager queryManager) {
		String ds = null;
		for (QueryTable t : list) {
			Partition partition = queryManager.getPartitionFromTableName(t
					.getName());
			PartitionTable pt = buildPartitionTable(partition, t, allFieldlist);
			t.setName(pt.getTable());
			t.setDatabase(pt.getDatabase());
			if (ds == null) {
				ds = pt.getDatabase();
			}
		}
		return ds;
	}

	public static PartitionTable buildPartitionTable(QueryTable t,
			List<Expression> allFieldlist, QueryManager queryManager) {
		Partition partition = queryManager.getPartitionFromTableName(t
				.getName());
		return buildPartitionTable(partition, t, allFieldlist);
	}

	private static PartitionTable buildPartitionTable(Partition partition,
			QueryTable queryTable, List<Expression> allFieldlist) {
		if (partition == null) {
			throw new RuntimeException("no table [ " + queryTable.getName()
					+ " ] config");
		}
		return partition.parse(queryTable, allFieldlist, null);
	}

	public static List<QueryTable> parseQueryTableList(String tableSql) {
		String[] local = tableSql.split(",");
		List<QueryTable> list = new ArrayList<QueryTable>(local.length);
		for (int i = 0; i < local.length; i++) {
			list.add(getQueryTable(local[i]));
		}
		return list;
	}

	private static QueryTable getQueryTable(String s) {
		String[] t = s.split(" ");
		String name = null;
		String alias = null;
		for (int i = 0; i < t.length; i++) {
			if (!t[i].equals(" ")) {
				if (name == null) {
					name = t[i];
				}
				else if (alias == null) {
					alias = t[i];
				}
				else {
					throw new RuntimeException("table name error [ " + s + " ]");
				}
			}
		}
		return new QueryTable(name, alias);
	}

	/**
	 * 分析select sql语句，返回分析后的SqlInfo<br/>
	 * 不支持嵌套select方式
	 * 
	 * @param sql
	 * @return
	 */
	public static SqlInfo parseSqlInfoForSelect(String sql) {
		int selectIdx = sql.indexOf(SELECT);
		if (selectIdx == -1) {
			throw new RuntimeException("sql error :: no select [ " + sql + " ]");
		}
		int fromIdx = sql.indexOf(FROM);
		if (fromIdx == -1) {
			throw new RuntimeException("sql error :: no from [ " + sql + " ]");
		}
		int whereIdx = sql.indexOf(WHERE);
		if (whereIdx == -1) {
			throw new RuntimeException("sql error ::no where [ " + sql + " ]");
		}
		String select = sql.substring(selectIdx + SELECT.length(), fromIdx);
		String from = sql.substring(fromIdx + FROM.length(), whereIdx);
		StringBuilder sb = new StringBuilder();
		sb.append(sql.substring(whereIdx + WHERE.length()));
		int idx = sb.indexOf(ORDERBY);
		if (idx != -1) {
			sb.delete(idx, sb.length() - 1);
		}
		idx = sb.indexOf(GROUPBY);
		if (idx != -1) {
			sb.delete(idx, sb.length() - 1);
		}
		SqlInfo sqlInfo = new SqlInfo();
		sqlInfo.setSelect(select);
		sqlInfo.setFrom(from);
		sqlInfo.setWhere(sb.toString());
		return sqlInfo;
	}

	public static void main(String[] args) {
		String sql = "select * from user u,friend f where u.userid=f.userid and u.userid=? order by friendid desc group by friendid limit 10";
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(1);
		SqlInfo sqlInfo = TableHelper.parseSqlInfoForSelect(sql);
		P.println(sqlInfo.getSelect());
		P.println(sqlInfo.getFrom());
		P.println(sqlInfo.getWhere());
	}
}
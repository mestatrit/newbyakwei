package com.hk.frame.dao.query2;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.springframework.jdbc.core.RowMapper;

/**
 * 自动创建数据对象对应的rowmapper对象 暂时只支持
 * long,int,byte,short,float,char,double,String,java.util.Date类型的值
 * 
 * @author akwei
 */
public class RowMapperCreater extends ClassLoader implements Opcodes {

	private static final String TYPE_LONG = "long";

	private static final String TYPE_INT = "int";

	private static final String TYPE_BYTE = "byte";

	private static final String TYPE_SHORT = "short";

	private static final String TYPE_FLOAT = "float";

	private static final String TYPE_DOUBLE = "double";

	private static final String TYPE_CHAR = "char";

	private static final String TYPE_STRING = String.class.getName();

	private static final String TYPE_DATE = Date.class.getName();

	protected RowMapperCreater(ClassLoader parent) {
		super(parent);
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> createRowMapperClass(
			ObjectSqlInfo<T> objectSqlInfo) {
		ClassWriter classWriter = new ClassWriter(0);
		String mapperName = createMapperClassName(objectSqlInfo.getClazz());
		String signName = mapperName.replaceAll("\\.", "/");
		classWriter.visit(V1_5, ACC_PUBLIC, signName, null, "java/lang/Object",
				new String[] { Type.getInternalName(RowMapper.class) });
		// 构造方法
		MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC,
				"<init>", "()V", null, null);
		methodVisitor.visitMaxs(1, 1);
		methodVisitor.visitVarInsn(ALOAD, 0);
		methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object",
				"<init>", "()V");
		methodVisitor.visitInsn(RETURN);
		methodVisitor.visitEnd();
		methodVisitor = classWriter
				.visitMethod(
						ACC_PUBLIC,
						"mapRow",
						"(Ljava/sql/ResultSet;I)"
								+ Type.getDescriptor(Object.class),
						null,
						new String[] { Type.getInternalName(SQLException.class) });
		methodVisitor.visitMaxs(3, 4);
		methodVisitor.visitTypeInsn(NEW, Type.getInternalName(objectSqlInfo
				.getClazz()));
		methodVisitor.visitInsn(DUP);
		methodVisitor.visitMethodInsn(INVOKESPECIAL, Type
				.getInternalName(objectSqlInfo.getClazz()), "<init>", "()V");
		methodVisitor.visitVarInsn(ASTORE, 3);
		methodVisitor.visitVarInsn(ALOAD, 3);
		for (Field field : objectSqlInfo.getAllfieldList()) {
			createResultSetGetValue(methodVisitor, objectSqlInfo, field);
		}
		methodVisitor.visitInsn(ARETURN);
		methodVisitor.visitEnd();
		byte[] code = classWriter.toByteArray();
		RowMapperCreater rowMapperCreater = new RowMapperCreater(Thread
				.currentThread().getContextClassLoader());
		try {
			rowMapperCreater.loadClass(RowMapper.class.getName());
			Class<T> mapperClass = (Class<T>) rowMapperCreater.defineClass(
					mapperName, code, 0, code.length);
			return mapperClass;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static <T> void createResultSetGetValue(
			MethodVisitor methodVisitor, ObjectSqlInfo<T> objectSqlInfo,
			Field field) {
		String[] info = createMethodNameAndDesc(field);
		methodVisitor.visitVarInsn(ALOAD, 1);
		methodVisitor.visitLdcInsn(objectSqlInfo.getTableName() + "."
				+ objectSqlInfo.getColumn(field.getName()));
		methodVisitor.visitMethodInsn(INVOKEINTERFACE, Type
				.getInternalName(ResultSet.class), info[0], info[1]);
		methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type
				.getInternalName(objectSqlInfo.getClazz()), info[2], info[3]);
		methodVisitor.visitVarInsn(ALOAD, 3);
	}

	private static String[] createMethodNameAndDesc(Field field) {
		FieldTypeUtil.checkFieldType(field);
		String type = field.getType().getName();
		String fieldName = field.getName();
		String[] a = new String[4];
		a[2] = createSetMethodString(fieldName);
		if (type.equals(TYPE_INT)) {
			a[0] = "getInt";
			a[1] = "(Ljava/lang/String;)I";
			a[3] = "(I)V";
		}
		if (type.equals(TYPE_SHORT)) {
			a[0] = "getShort";
			a[1] = "(Ljava/lang/String;)S";
			a[3] = "(S)V";
		}
		if (type.equals(TYPE_BYTE)) {
			a[0] = "getByte";
			a[1] = "(Ljava/lang/String;)B";
			a[3] = "(B)V";
		}
		if (type.equals(TYPE_LONG)) {
			a[0] = "getLong";
			a[1] = "(Ljava/lang/String;)J";
			a[3] = "(J)V";
		}
		if (type.equals(TYPE_FLOAT)) {
			a[0] = "getFloat";
			a[1] = "(Ljava/lang/String;)F";
			a[3] = "(F)V";
		}
		if (type.equals(TYPE_DOUBLE)) {
			a[0] = "getDouble";
			a[1] = "(Ljava/lang/String;)D";
			a[3] = "(D)V";
		}
		if (type.equals(TYPE_CHAR)) {
			a[0] = "getChar";
			a[1] = "(Ljava/lang/String;)C";
			a[3] = "(C)V";
		}
		if (type.equals(TYPE_STRING)) {
			a[0] = "getString";
			a[1] = "(Ljava/lang/String;)Ljava/lang/String;";
			a[3] = "(Ljava/lang/String;)V";
		}
		if (type.equals(TYPE_DATE)) {
			a[0] = "getTimestamp";
			a[1] = "(Ljava/lang/String;)Ljava/sql/Timestamp;";
			a[3] = "(" + Type.getDescriptor(Date.class) + ")V";
		}
		return a;
	}

	private static String createSetMethodString(String fieldName) {
		return "set" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
	}

	private static String createMapperClassName(Class<?> clazz) {
		int idx = clazz.getName().lastIndexOf(".");
		String shortName = clazz.getName().substring(idx + 1);
		String pkgName = clazz.getName().substring(0, idx);
		return pkgName + "." + shortName + "AsmMapper";
	}
}

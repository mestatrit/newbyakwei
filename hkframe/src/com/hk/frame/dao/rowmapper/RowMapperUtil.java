package com.hk.frame.dao.rowmapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.springframework.jdbc.core.RowMapper;

import com.hk.frame.dao.query.ObjectSqlData;

public class RowMapperUtil extends ClassLoader implements Opcodes {

	private static final String TYPE_LONG = "long";

	private static final String TYPE_INT = "int";

	private static final String TYPE_BYTE = "byte";

	private static final String TYPE_SHORT = "short";

	private static final String TYPE_FLOAT = "float";

	private static final String TYPE_DOUBLE = "double";

	private static final String TYPE_STRING = String.class.getName();

	private static final String TYPE_DATE = Date.class.getName();

	protected RowMapperUtil(ClassLoader parent) {
		super(parent);
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> createRowMapperClass(ObjectSqlData objectSqlData) {
		ClassWriter classWriter = new ClassWriter(0);
		String mapperName = createMapperClassName(objectSqlData.getClazz());
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
		methodVisitor.visitTypeInsn(NEW, Type.getInternalName(objectSqlData
				.getClazz()));
		methodVisitor.visitInsn(DUP);
		methodVisitor.visitMethodInsn(INVOKESPECIAL, Type
				.getInternalName(objectSqlData.getClazz()), "<init>", "()V");
		methodVisitor.visitVarInsn(ASTORE, 3);
		methodVisitor.visitVarInsn(ALOAD, 3);
		for (Field field : objectSqlData.getAllfieldList()) {
			createResultSetGetValue(methodVisitor, objectSqlData.getClazz(),
					field, objectSqlData.getColumn(field.getName()));
		}
		methodVisitor.visitInsn(ARETURN);
		methodVisitor.visitEnd();
		byte[] code = classWriter.toByteArray();
		RowMapperUtil rowMapperUtil = new RowMapperUtil(Thread.currentThread()
				.getContextClassLoader());
		Class mapperClass;
		try {
			rowMapperUtil.loadClass(RowMapper.class.getName());
			mapperClass = rowMapperUtil.defineClass(mapperName, code, 0,
					code.length);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return mapperClass;
	}

	private static void createResultSetGetValue(MethodVisitor methodVisitor,
			Class<?> clazz, Field field, String columnName) {
		String[] info = createMethodNameAndDesc(field);
		methodVisitor.visitVarInsn(ALOAD, 1);
		methodVisitor.visitLdcInsn(columnName);
		methodVisitor.visitMethodInsn(INVOKEINTERFACE, Type
				.getInternalName(ResultSet.class), info[0], info[1]);
		methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type
				.getInternalName(clazz), info[2], info[3]);
		methodVisitor.visitVarInsn(ALOAD, 3);
	}

	private static String[] createMethodNameAndDesc(Field field) {
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
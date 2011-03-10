package com.hk.frame.dao.query2;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.springframework.jdbc.core.RowMapper;

public class SqlUpdateMapperCreater extends ClassLoader implements Opcodes {

	protected SqlUpdateMapperCreater(ClassLoader parent) {
		super(parent);
	}

	private static String createMapperClassName(Class<?> clazz) {
		int idx = clazz.getName().lastIndexOf(".");
		String shortName = clazz.getName().substring(idx + 1);
		String pkgName = clazz.getName().substring(0, idx);
		return pkgName + "." + shortName + "AsmSqlUpdateMapper";
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> createSqlUpdateMapperClass(
			ObjectSqlInfo<T> objectSqlInfo) {
		ClassWriter classWriter = new ClassWriter(0);
		String mapperName = createMapperClassName(objectSqlInfo.getClazz());
		String signName = mapperName.replaceAll("\\.", "/");
		classWriter.visit(V1_5, ACC_PUBLIC, signName, null, "java/lang/Object",
				new String[] { Type.getInternalName(SqlUpdateMqpper.class) });
		// 构造方法
		MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC,
				"<init>", "()V", null, null);
		methodVisitor.visitMaxs(1, 1);
		methodVisitor.visitVarInsn(ALOAD, 0);
		methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object",
				"<init>", "()V");
		methodVisitor.visitInsn(RETURN);
		methodVisitor.visitEnd();
		visitGetIdParam(classWriter, methodVisitor, objectSqlInfo.getIdField(),
				objectSqlInfo);
		visitGetParamsForInsert(classWriter, methodVisitor, objectSqlInfo);
		visitGetParamsForUpdate(classWriter, methodVisitor, objectSqlInfo);
		byte[] code = classWriter.toByteArray();
		SqlUpdateMapperCreater sqlUpdateMapperCreater = new SqlUpdateMapperCreater(
				Thread.currentThread().getContextClassLoader());
		try {
			sqlUpdateMapperCreater.loadClass(RowMapper.class.getName());
			Class<T> mapperClass = (Class<T>) sqlUpdateMapperCreater
					.defineClass(mapperName, code, 0, code.length);
			return mapperClass;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static <T> void visitGetIdParam(ClassWriter classWriter,
			MethodVisitor methodVisitor, Field field,
			ObjectSqlInfo<T> objectSqlInfo) {
		methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "getIdParam", "("
				+ Type.getDescriptor(objectSqlInfo.getClazz()) + ")", null,
				null);
		methodVisitor.visitMaxs(1, 2);
		methodVisitor.visitVarInsn(ALOAD, 2);
		visitGetIdParamInvokeForField(methodVisitor, field, objectSqlInfo);
		methodVisitor.visitInsn(ARETURN);
		methodVisitor.visitEnd();
	}

	private static String getGetMethodName(Field field) {
		String fieldName = field.getName();
		return "get" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
	}

	/**
	 * id目前只支持int long
	 * 
	 * @param <T>
	 * @param methodVisitor
	 * @param field
	 * @param objectSqlInfo
	 */
	private static <T> void visitGetIdParamInvokeForField(
			MethodVisitor methodVisitor, Field field,
			ObjectSqlInfo<T> objectSqlInfo) {
		String type = getFieldReturnType(field);
		String methodName = getGetMethodName(objectSqlInfo.getIdField());
		if (type.equals("int")) {
			methodVisitor
					.visitMethodInsn(INVOKEVIRTUAL, Type
							.getDescriptor(objectSqlInfo.getClazz()),
							methodName, "()J");
			methodVisitor.visitMethodInsn(INVOKESTATIC, Type
					.getInternalName(Integer.class), "valueOf", "(I)"
					+ Type.getDescriptor(Integer.class));
		}
		if (type.equals("long")) {
			methodVisitor
					.visitMethodInsn(INVOKEVIRTUAL, Type
							.getDescriptor(objectSqlInfo.getClazz()),
							methodName, "()J");
			methodVisitor.visitMethodInsn(INVOKESTATIC, Type
					.getInternalName(Long.class), "valueOf", "(J)"
					+ Type.getDescriptor(Long.class));
		}
		if (type.equals("java.lang.String")) {
			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type
					.getDescriptor(objectSqlInfo.getClazz()), methodName,
					"()Ljava/lang/String;");
		}
	}

	private static <T> void visitGetParamsForInsert(ClassWriter classWriter,
			MethodVisitor methodVisitor, ObjectSqlInfo<T> objectSqlInfo) {
		methodVisitor = classWriter.visitMethod(ACC_PUBLIC,
				"getParamsForInsert", "(" + objectSqlInfo.getClazz() + ")"
						+ Type.getDescriptor(Object[].class), null, null);
		methodVisitor.visitMaxs(3, 3);
		methodVisitor.visitTypeInsn(NEW, Type.getInternalName(ArrayList.class));
		methodVisitor.visitInsn(DUP);
		methodVisitor.visitMethodInsn(INVOKESPECIAL, Type
				.getInternalName(ArrayList.class), "<init>", "()V");
		methodVisitor.visitVarInsn(ASTORE, 2);
		int i = 0;
		for (Field f : objectSqlInfo.getAllfieldList()) {
			visitGetParamsForInsertAndUpdate(methodVisitor, f, objectSqlInfo);
			i++;
		}
		methodVisitor.visitVarInsn(ALOAD, 2);
		methodVisitor.visitVarInsn(ALOAD, 2);
		methodVisitor.visitMethodInsn(INVOKEINTERFACE, Type
				.getInternalName(List.class), "size", "()I");
		methodVisitor.visitTypeInsn(NEWARRAY, Type
				.getInternalName(Object.class));
		methodVisitor.visitMethodInsn(INVOKEINTERFACE, Type
				.getInternalName(List.class), "toArray",
				"([Ljava.lang.Object;)[Ljava.lang.Object;");
		methodVisitor.visitInsn(ARETURN);
		methodVisitor.visitEnd();
	}

	private static <T> void visitGetParamsForUpdate(ClassWriter classWriter,
			MethodVisitor methodVisitor, ObjectSqlInfo<T> objectSqlInfo) {
		methodVisitor = classWriter.visitMethod(ACC_PUBLIC,
				"getParamsForUpdate", "(" + objectSqlInfo.getClazz() + ")"
						+ Type.getDescriptor(Object[].class), null, null);
		methodVisitor.visitMaxs(3, 3);
		methodVisitor.visitTypeInsn(NEW, Type.getInternalName(ArrayList.class));
		methodVisitor.visitInsn(DUP);
		methodVisitor.visitMethodInsn(INVOKESPECIAL, Type
				.getInternalName(ArrayList.class), "<init>", "()V");
		methodVisitor.visitVarInsn(ASTORE, 2);
		int i = 0;
		for (Field f : objectSqlInfo.getAllfieldList()) {
			visitGetParamsForInsertAndUpdate(methodVisitor, f, objectSqlInfo);
			i++;
		}
		methodVisitor.visitVarInsn(ALOAD, 2);
		methodVisitor.visitVarInsn(ALOAD, 2);
		methodVisitor.visitMethodInsn(INVOKEINTERFACE, Type
				.getInternalName(List.class), "size", "()I");
		methodVisitor.visitTypeInsn(NEWARRAY, Type
				.getInternalName(Object.class));
		methodVisitor.visitMethodInsn(INVOKEINTERFACE, Type
				.getInternalName(List.class), "toArray",
				"([Ljava.lang.Object;)[Ljava.lang.Object;");
		methodVisitor.visitInsn(ARETURN);
		methodVisitor.visitEnd();
	}

	private static <T> void visitGetParamsForInsertAndUpdate(
			MethodVisitor methodVisitor, Field field,
			ObjectSqlInfo<T> objectSqlInfo) {
		methodVisitor.visitVarInsn(ALOAD, 2);
		methodVisitor.visitVarInsn(ALOAD, 1);
		String type = getFieldReturnType(field);
		if (type.equals("int")) {
			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type
					.getInternalName(objectSqlInfo.getClazz()),
					getGetMethodName(field), "()I");
			methodVisitor.visitMethodInsn(INVOKESTATIC, Type
					.getInternalName(Integer.class), "valueOf", "(I)"
					+ Type.getDescriptor(Integer.class));
		}
		else if (type.equals("long")) {
			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type
					.getInternalName(objectSqlInfo.getClazz()),
					getGetMethodName(field), "()J");
			methodVisitor.visitMethodInsn(INVOKESTATIC, Type
					.getInternalName(Long.class), "valueOf", "(J)"
					+ Type.getDescriptor(Long.class));
		}
		else if (type.equals("short")) {
			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type
					.getInternalName(objectSqlInfo.getClazz()),
					getGetMethodName(field), "()S");
			methodVisitor.visitMethodInsn(INVOKESTATIC, Type
					.getInternalName(Short.class), "valueOf", "(S)"
					+ Type.getDescriptor(Short.class));
		}
		else if (type.equals("boolean")) {
			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type
					.getInternalName(objectSqlInfo.getClazz()),
					getGetMethodName(field), "()Z");
			methodVisitor.visitMethodInsn(INVOKESTATIC, Type
					.getInternalName(Boolean.class), "valueOf", "(Z)"
					+ Type.getDescriptor(Boolean.class));
		}
		else if (type.equals("char")) {
			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type
					.getInternalName(objectSqlInfo.getClazz()),
					getGetMethodName(field), "()C");
			methodVisitor.visitMethodInsn(INVOKESTATIC, Type
					.getInternalName(Character.class), "valueOf", "(S)"
					+ Type.getDescriptor(Character.class));
		}
		else if (type.equals("byte")) {
			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type
					.getInternalName(objectSqlInfo.getClazz()),
					getGetMethodName(field), "()B");
			methodVisitor.visitMethodInsn(INVOKESTATIC, Type
					.getInternalName(Byte.class), "valueOf", "(B)"
					+ Type.getDescriptor(Byte.class));
		}
		else if (type.equals("float")) {
			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type
					.getInternalName(objectSqlInfo.getClazz()),
					getGetMethodName(field), "()F");
			methodVisitor.visitMethodInsn(INVOKESTATIC, Type
					.getInternalName(Float.class), "valueOf", "(F)"
					+ Type.getDescriptor(Float.class));
		}
		else if (type.equals("double")) {
			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type
					.getInternalName(objectSqlInfo.getClazz()),
					getGetMethodName(field), "()D");
			methodVisitor.visitMethodInsn(INVOKESTATIC, Type
					.getInternalName(Double.class), "valueOf", "(D)"
					+ Type.getDescriptor(Double.class));
		}
		else {
			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type
					.getInternalName(objectSqlInfo.getClazz()),
					getGetMethodName(field), "()"
							+ Type.getDescriptor(field.getType()));
		}
		methodVisitor.visitInsn(POP);
	}

	private static String getFieldReturnType(Field field) {
		return field.getType().getName();
	}
}

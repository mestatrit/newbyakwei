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
		String mapperClassName = mapperName.replaceAll("\\.", "/");
		// Ljava/lang/Object;Lcom/hk/frame/dao/query2/SqlUpdateMapper<Lsqlupdatemapper/TestUser;>;
		String signature = Type.getDescriptor(Object.class)
				+ Type.getInternalName(SqlUpdateMapper.class) + "<"
				+ Type.getDescriptor(objectSqlInfo.getClazz()) + ">;";
		classWriter.visit(V1_5, ACC_PUBLIC, mapperClassName, signature,
				"java/lang/Object", new String[] { Type
						.getInternalName(SqlUpdateMapper.class) });
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
		visitBridgeGetIdParam(classWriter, methodVisitor, mapperClassName,
				objectSqlInfo);
		visitBridgeGetParamsForInsert(classWriter, methodVisitor,
				mapperClassName, objectSqlInfo);
		visitBridgeGetParamsForUpdate(classWriter, methodVisitor,
				mapperClassName, objectSqlInfo);
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
				+ Type.getDescriptor(objectSqlInfo.getClazz())
				+ ")Ljava/lang/Object;", null, null);
		methodVisitor.visitMaxs(2, 2);
		methodVisitor.visitVarInsn(ALOAD, 1);
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
	 * id目前只支持int long String
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
		methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type
				.getInternalName(objectSqlInfo.getClazz()), methodName, "()"
				+ Type.getDescriptor(field.getType()));
		if (type.equals("int")) {
			methodVisitor.visitMethodInsn(INVOKESTATIC, Type
					.getInternalName(ParamListUtil.class), "toObject",
					"(I)Ljava/lang/Object;");
		}
		if (type.equals("long")) {
			methodVisitor.visitMethodInsn(INVOKESTATIC, Type
					.getInternalName(ParamListUtil.class), "toObject",
					"(J)Ljava/lang/Object;");
		}
		if (type.equals("java.lang.String")) {
			methodVisitor.visitMethodInsn(INVOKESTATIC, Type
					.getInternalName(ParamListUtil.class), "toObject",
					"(Ljava/lang/String;)Ljava/lang/Object;");
		}
	}

	private static <T> void visitGetParamsForInsert(ClassWriter classWriter,
			MethodVisitor methodVisitor, ObjectSqlInfo<T> objectSqlInfo) {
		methodVisitor = classWriter.visitMethod(ACC_PUBLIC,
				"getParamsForInsert", "("
						+ Type.getDescriptor(objectSqlInfo.getClazz()) + ")"
						+ Type.getDescriptor(Object[].class), null, null);
		methodVisitor.visitMaxs(3, 3);
		methodVisitor.visitTypeInsn(NEW, Type
				.getInternalName(ParamListUtil.class));
		methodVisitor.visitInsn(DUP);
		methodVisitor.visitMethodInsn(INVOKESPECIAL, Type
				.getInternalName(ParamListUtil.class), "<init>", "()V");
		methodVisitor.visitVarInsn(ASTORE, 2);
		methodVisitor.visitVarInsn(ALOAD, 2);
		int i = 0;
		for (Field f : objectSqlInfo.getAllfieldList()) {
			visitGetParamsForInsertAndUpdate(methodVisitor, f, objectSqlInfo);
			i++;
		}
		methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type
				.getInternalName(ParamListUtil.class), "toObjects",
				"()[Ljava/lang/Object;");
		methodVisitor.visitInsn(ARETURN);
		methodVisitor.visitEnd();
	}

	private static <T> void visitGetParamsForUpdate(ClassWriter classWriter,
			MethodVisitor methodVisitor, ObjectSqlInfo<T> objectSqlInfo) {
		methodVisitor = classWriter.visitMethod(ACC_PUBLIC,
				"getParamsForUpdate", "("
						+ Type.getDescriptor(objectSqlInfo.getClazz()) + ")"
						+ Type.getDescriptor(Object[].class), null, null);
		methodVisitor.visitMaxs(3, 3);
		methodVisitor.visitTypeInsn(NEW, Type
				.getInternalName(ParamListUtil.class));
		methodVisitor.visitInsn(DUP);
		methodVisitor.visitMethodInsn(INVOKESPECIAL, Type
				.getInternalName(ParamListUtil.class), "<init>", "()V");
		methodVisitor.visitVarInsn(ASTORE, 2);
		methodVisitor.visitVarInsn(ALOAD, 2);
		int i = 0;
		List<Field> list = new ArrayList<Field>(objectSqlInfo.getFieldList());
		list.add(objectSqlInfo.getIdField());
		for (Field f : list) {
			visitGetParamsForInsertAndUpdate(methodVisitor, f, objectSqlInfo);
			i++;
		}
		methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type
				.getInternalName(ParamListUtil.class), "toObjects",
				"()[Ljava/lang/Object;");
		methodVisitor.visitInsn(ARETURN);
		methodVisitor.visitEnd();
	}

	private static <T> void visitGetParamsForInsertAndUpdate(
			MethodVisitor methodVisitor, Field field,
			ObjectSqlInfo<T> objectSqlInfo) {
		methodVisitor.visitVarInsn(ALOAD, 1);
		String type = getFieldReturnType(field);
		methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type
				.getInternalName(objectSqlInfo.getClazz()),
				getGetMethodName(field), "()"
						+ Type.getDescriptor(field.getType()));
		if (type.equals("int")) {
			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type
					.getInternalName(ParamListUtil.class), "addInt", "(I)V");
		}
		else if (type.equals("long")) {
			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type
					.getInternalName(ParamListUtil.class), "addLong", "(J)V");
		}
		else if (type.equals("short")) {
			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type
					.getInternalName(ParamListUtil.class), "addShort", "(S)V");
		}
		else if (type.equals("boolean")) {
			methodVisitor
					.visitMethodInsn(INVOKEVIRTUAL, Type
							.getInternalName(ParamListUtil.class),
							"addBoolean", "(Z)V");
		}
		else if (type.equals("char")) {
			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type
					.getInternalName(ParamListUtil.class), "addChar", "(C)V");
		}
		else if (type.equals("byte")) {
			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type
					.getInternalName(ParamListUtil.class), "addByte", "(B)V");
		}
		else if (type.equals("float")) {
			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type
					.getInternalName(ParamListUtil.class), "addFloat", "(F)V");
		}
		else if (type.equals("double")) {
			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type
					.getInternalName(ParamListUtil.class), "addDouble", "(D)V");
		}
		else if (type.equals("java.lang.String")) {
			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type
					.getInternalName(ParamListUtil.class), "addString",
					"(Ljava/lang/String;)V");
		}
		else if (type.equals("java.util.Date")) {
			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type
					.getInternalName(ParamListUtil.class), "addDate",
					"(Ljava/util/Date;)V");
		}
		methodVisitor.visitVarInsn(ALOAD, 2);
	}

	private static <T> void visitBridgeGetIdParam(ClassWriter classWriter,
			MethodVisitor methodVisitor, String mapperClassName,
			ObjectSqlInfo<T> objectSqlInfo) {
		methodVisitor = classWriter.visitMethod(ACC_PUBLIC + ACC_BRIDGE
				+ ACC_SYNTHETIC, "getIdParam",
				"(Ljava/lang/Object;)Ljava/lang/Object;", null, null);
		methodVisitor.visitMaxs(2, 2);
		methodVisitor.visitVarInsn(ALOAD, 0);
		methodVisitor.visitVarInsn(ALOAD, 1);
		methodVisitor.visitTypeInsn(CHECKCAST, Type
				.getInternalName(objectSqlInfo.getClazz()));
		methodVisitor.visitMethodInsn(INVOKEVIRTUAL, mapperClassName,
				"getIdParam", "("
						+ Type.getDescriptor(objectSqlInfo.getClazz())
						+ ")Ljava/lang/Object;");
		methodVisitor.visitInsn(ARETURN);
	}

	private static <T> void visitBridgeGetParamsForUpdate(
			ClassWriter classWriter, MethodVisitor methodVisitor,
			String mapperClassName, ObjectSqlInfo<T> objectSqlInfo) {
		methodVisitor = classWriter.visitMethod(ACC_PUBLIC + ACC_BRIDGE
				+ ACC_SYNTHETIC, "getParamsForUpdate",
				"(Ljava/lang/Object;)[Ljava/lang/Object;", null, null);
		methodVisitor.visitMaxs(2, 2);
		methodVisitor.visitVarInsn(ALOAD, 0);
		methodVisitor.visitVarInsn(ALOAD, 1);
		methodVisitor.visitTypeInsn(CHECKCAST, Type
				.getInternalName(objectSqlInfo.getClazz()));
		methodVisitor.visitMethodInsn(INVOKEVIRTUAL, mapperClassName,
				"getParamsForUpdate", "("
						+ Type.getDescriptor(objectSqlInfo.getClazz())
						+ ")[Ljava/lang/Object;");
		methodVisitor.visitInsn(ARETURN);
	}

	private static <T> void visitBridgeGetParamsForInsert(
			ClassWriter classWriter, MethodVisitor methodVisitor,
			String mapperClassName, ObjectSqlInfo<T> objectSqlInfo) {
		methodVisitor = classWriter.visitMethod(ACC_PUBLIC + ACC_BRIDGE
				+ ACC_SYNTHETIC, "getParamsForInsert",
				"(Ljava/lang/Object;)[Ljava/lang/Object;", null, null);
		methodVisitor.visitMaxs(2, 2);
		methodVisitor.visitVarInsn(ALOAD, 0);
		methodVisitor.visitVarInsn(ALOAD, 1);
		methodVisitor.visitTypeInsn(CHECKCAST, Type
				.getInternalName(objectSqlInfo.getClazz()));
		methodVisitor.visitMethodInsn(INVOKEVIRTUAL, mapperClassName,
				"getParamsForInsert", "("
						+ Type.getDescriptor(objectSqlInfo.getClazz())
						+ ")[Ljava/lang/Object;");
		methodVisitor.visitInsn(ARETURN);
	}

	private static String getFieldReturnType(Field field) {
		return field.getType().getName();
	}
}

<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${respValue==null}">
	<script>
	parent.${functionName}('${error}','${error_msg}','${op_func}','${obj_id_param}');
	</script>
</c:if>
<c:if test="${respValue!=null}">
	<script>
	parent.${functionName}('${error}','${error_msg}','${op_func}','${obj_id_param}','${respValue}');
	</script>
</c:if>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.web.util.HkWebUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%HkWebUtil.loadCmpKindInfo(request);%>
<hk:select name="parentKindId" checkedvalue="${selected_parentKindId}" onchange="initchild(this.value)">
	<hk:option value="0" data="请选择"/>
	<c:forEach var="parent" items="${parentlist}">
		<hk:option value="${parent.kindId}" data="${parent.name}"/>
	</c:forEach>
</hk:select>
<hk:select name="kindId" oid="sel_kindId">
	<hk:option value="0" data="请选择"/>
</hk:select>
<script type="text/javascript">
var kind=new Array();
<c:forEach var="kind" items="${kindlist}" varStatus="idx">
kind[${idx.index }]=new Array(${kind.kindId },${kind.parentId },'${kind.name }');
</c:forEach>
function initchild(parentId){
	var oo=getObj("sel_kindId");
	oo.options.length=0;
	oo.options[0]=new Option("请选择",0);
	for(var i=0;i<kind.length;i++){
		if(kind[i][1]==parseInt(parentId)){
			oo.options[oo.options.length]=new Option(kind[i][2],kind[i][0]);
		}
	}
}
</script>
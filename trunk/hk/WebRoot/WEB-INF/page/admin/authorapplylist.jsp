<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.AuthorApply"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title">查看<hk:data key="authorapply.checkflg_${checkflg}"/>的数据</c:set>
<hk:wap title="${view_title} - 俱乐部申请" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">
		<hk:form method="get" action="/admin/authorapply_list.do">
			查看<hk:data key="authorapply.checkflg_${checkflg}"/>的数据<br/>
			姓名:<br/><hk:text name="name" value="${name}"/><br/><br/>
			<hk:select name="checkflg" checkedvalue="${checkflg}">
				<hk:option value="<%=AuthorApply.CHECKFLG_N %>" data="authorapply.checkflg_0" res="true"/>
				<hk:option value="<%=AuthorApply.CHECKFLG_FAIL %>" data="authorapply.checkflg_1" res="true"/>
				<hk:option value="<%=AuthorApply.CHECKFLG_Y %>" data="authorapply.checkflg_2" res="true"/>
			</hk:select>
			<hk:submit value="搜索"/>
		</hk:form>
	</div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="a" items="${list}" varStatus="idx">
			<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				<hk:a href="/admin/authorapply.do?oid=${a.oid}&search_checkflg=${checkflg }">${a.name}</hk:a>
			</div>
		</c:forEach>
		<hk:simplepage clazz="page" href="/admin/authorapply_list.do?checkflg=${checkflg}&name=${enc_name }"/>
	</c:if>
	<c:if test="${fn:length(list)==0}"><div class="hang"><hk:data key="nodatalist"/></div></c:if>
	<div class="hang"><hk:a href="/more.do"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>
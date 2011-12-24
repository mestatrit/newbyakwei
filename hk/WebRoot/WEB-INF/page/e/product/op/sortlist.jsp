<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="view.managecompany"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<hk:form action="/e/op/product/op_addsort.do">
			<hk:hide name="companyId" value="${companyId}"/>
			<hk:data key="view.createsort"/>(<span class="ruo s"><hk:data key="cmpproductsort.name.tip"/></span>):<br/>
			<hk:text name="name" value="${o.name}"/><br/>
			<hk:submit value="view.submit" res="true"/>
		</hk:form>
	</div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="s" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				${s.name } 
				<hk:a href="/e/op/product/op_toeditsort.do?companyId=${companyId}&sortId=${s.sortId }"><hk:data key="view.edit2"/></hk:a> 
				<hk:a href="/e/op/product/op_delsort.do?companyId=${companyId}&sortId=${s.sortId }"><hk:data key="view.delete"/></hk:a>
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${fn:length(list)==0}"><hk:data key="nodataview"/></c:if>
	<div class="hang"><hk:a href="/e/op/op_toedit.do?companyId=${companyId}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../../../inc/foot.jsp"></jsp:include>
</hk:wap>
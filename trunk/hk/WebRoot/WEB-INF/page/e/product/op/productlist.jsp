<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="view.managecompany"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<hk:a href="/e/op/product/op_toaddproduct.do?companyId=${companyId}"><hk:data key="view.createcmpproduct"/></hk:a>
	</div>
	<div class="hang even">
		<hk:form method="get" action="/e/op/product/op_productlist.do">
			<hk:hide name="companyId" value="${companyId}"/>
			<hk:select name="sortId" checkedvalue="${sortId}">
				<hk:option value="0" data="view.all" res="true"/>
				<c:forEach var="s" items="${sortlist}">
				<hk:option value="${s.sortId}" data="${s.name}"/>
				</c:forEach>
			</hk:select><br/>
			<hk:text name="name" value="${name}"/><br/>
			<hk:submit value="view.search" res="true"/>
		</hk:form>
	</div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="p" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				${p.name } 
				<hk:a href="/e/op/product/op_toeditproduct.do?companyId=${companyId}&pid=${p.productId }" page="true"><hk:data key="view.edit2"/></hk:a> 
				<hk:a href="/e/op/product/op_delproduct.do?companyId=${companyId}&pid=${p.productId }" page="true"><hk:data key="view.delete"/></hk:a>
			</div>
		</c:forEach>
		<hk:simplepage href="/e/op/product/op_productlist.do?companyId=${companyId}&sortId=${sortId }&name=${enc_name }"/>
	</c:if>
	<c:if test="${fn:length(list)==0}"><hk:data key="nodataview"/></c:if>
	<div class="hang"><hk:a href="/e/op/op_toedit.do?companyId=${companyId}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../../../inc/foot.jsp"></jsp:include>
</hk:wap>
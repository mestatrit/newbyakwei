<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:if test="${companyReview!=null}">
<c:set var="score" value="${companyReview.score}"/>
</c:if>
<hk:form name="labafrm" onsubmit="return confirmCreate()" action="${review_form_action }">
	<hk:hide name="labaId" value="${labaId}"/>
	<hk:hide name="companyId" value="${companyId}"/>
	打分:<br/>
	<hk:select name="score" checkedvalue="${score}" forcecheckedvalue="${userscore}">
		<hk:option value="0" data="未打分" />
		<hk:option value="3" data="很好" />
		<hk:option value="2" data="好" />
		<hk:option value="1" data="一般" />
		<hk:option value="-1" data="差" />
		<hk:option value="-2" data="很差" />
	</hk:select><br/>
	写下你的评论:<br/>
	<hk:textarea oid="status" name="content" clazz="ipt2" rows="2" value="${companyReview.content}"/><br/>
	<hk:submit value="view.submit" res="true"/> <span id="remaining" class="ruo s">140</span><span class="ruo s">字</span>
</hk:form>
<jsp:include page="labainputjs.jsp"></jsp:include>
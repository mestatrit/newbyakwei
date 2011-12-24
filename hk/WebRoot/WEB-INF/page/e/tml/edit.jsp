<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="修改模板 - ${o.name}" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<c:if test="${cmpTemplate!=null}">
		<div class="hang even"><a href="http://vip.huoku.com/${companyId}">查看企业网站</a></div>
	</c:if>
	<c:if test="${can_add_new_module}">
		<div class="hang"><hk:a href="/e/op/tml/op_toaddcmpmodule.do?companyId=${companyId}">添加新项目</hk:a></div>
	</c:if>
	<div class="hang">已经添加的项目</div>
	<c:forEach var="cm" items="${cmpmoduleList}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="hang ${clazz_var }">
			${cm.title }<c:if test="${!cm.show}"><span class="s">(已隐藏)</span></c:if>|<hk:a href="/e/op/tml/op_toeditcmpmodule.do?companyId=${companyId}&sysId=${cm.sysId }"><hk:data key="view.update"/></hk:a><br/>
			<c:if test="${not empty cm.intro}"><span class="s">${cm.intro }</span></c:if>
		</div>
	</c:forEach>
	<div class="hang"><hk:a href="/e/op/tml/op_toadd.do?companyId=${companyId}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>
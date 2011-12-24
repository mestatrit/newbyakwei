<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="利用火酷平台快速发布独立的企业站点 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<c:if test="${not empty o.logopath}">
			<img src="${o.logo48}"/><br/>
		</c:if>
		<c:if test="${not empty o.tel}">
		${o.tel }<br/>
		</c:if>
		<c:if test="${not empty o.addr}">
		${o.addr }<br/>
		</c:if>
	</div>
	<c:if test="${not empty o.headPath}">
		<div><img src="${o.head240}"/><br/></div>
	</c:if>
	<c:forEach var="cm" items="${list}">
		<div class="hang odd">
			<hk:a href="${cm.tmlModule.funcurl}?companyId=${companyId }">${cm.title }</hk:a>|<hk:a href="/e/op/tml/op_toeditcmpmodule.do?companyId=${companyId}&sysId=${cm.sysId }"><hk:data key="view.update"/></hk:a><br/>
			<c:if test="${not empty cm.intro}">
				${cm.intro }
			</c:if>
		</div>
		<div class="hang even"><br/>
		</div>
	</c:forEach>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>
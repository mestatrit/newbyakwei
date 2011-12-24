<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="利用火酷平台快速发布独立的企业站点 - ${o.name}" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">利用火酷平台快速发布独立的企业站点</div>
	<c:if test="${cmpTemplate!=null}">
		<div class="hang even"><hk:a href="/e/op/tml/op_toedit.do?companyId=${companyId}">修改当前模板</hk:a></div>
		<div class="hang even"><a href="http://vip.huoku.com/${companyId}">查看企业网站</a></div>
	</c:if>
	<div class="hang even">
		<hk:form action="/e/op/tml/op_add.do">
			<hk:hide name="companyId" value="${companyId}"/>
			<c:if test="${cmpTemplate!=null}">
				当前模板:<br/>
				${template.name }<br/><br/>
			</c:if>
			模版选择:<br/>
			<hk:select name="templateId" checkedvalue="${cmpTemplate.templateId}">
				<hk:option value="0" data=""/>
				<c:forEach var="t" items="${list}">
					<hk:option value="${t.templateId}" data="${t.name}"/>
				</c:forEach>
			</hk:select><br/>
			<hk:submit value="按照模板生成站点"/>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/e/op/op_toedit.do?companyId=${companyId}"><hk:data key="view.return"/></hk:a>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>
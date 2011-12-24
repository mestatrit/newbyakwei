<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.frame.util.i18n.HkI18n"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="填写网站域名 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">填写网站域名	</div>
	<div class="hang">
		<hk:form action="/e/op/authcmp/cmpinfo_add.do">
			<hk:hide name="companyId" value="${companyId}"/>
			网站域名:<br/>
			http://<hk:text name="domain" value="${cmpInfo.domain}" maxlength="50"/><br/><br/>
			语言:<br/>
			<hk:select name="language" checkedvalue="${cmpInfo.language}">
				<hk:option value="<%=HkI18n.SIMPLIFIED_CHINESE %>" data="中文"/>
				<hk:option value="<%=HkI18n.EN %>" data="英文"/>
			</hk:select><br/>
			<hk:submit value="view.submit" res="true"/>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/e/op/op_toedit.do?companyId=${companyId}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>
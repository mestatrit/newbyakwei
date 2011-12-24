<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="关于${hkObj.name }的文章 - 火酷" rm="false" style="/page/css/b.css">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">关于${hkObj.name }的文章</div>
	<div class="hang">
		<hk:form action="/op/authorapply_edit.do">
			<hk:hide name="oid" value="${oid}"/>
			姓名:<br/>
			<hk:text name="name" value="${o.name}"/><br/><br/>
			联系电话:<br/>
			<hk:text name="tel" value="${o.tel}"/><br/><br/>
			博客地址:<br/>
			<hk:text name="blog" value="${o.blog}"/><br/>
			可以联系我的E-mail:<br/>
			<hk:text name="email" value="${o.email}"/><br/><br/>
			申请内容:<br/>
			<hk:textarea name="content" value="${o.content}"/><br/>
			<hk:submit value="修改"/>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/more.do"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>
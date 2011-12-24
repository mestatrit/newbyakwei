<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.HkObjArticle"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="关于${hkObj.name }的文章 - 火酷" rm="false" style="/page/css/b.css">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">关于${hkObj.name }的文章</div>
	<div class="hang">
		<hk:form action="/op/article_edit.do">
			<hk:hide name="articleId" value="${articleId}"/>
			文章标题:<br/>
			<hk:text name="title" value="${o.title}"/><br/><br/>
			文章链接:<br/>
			<hk:text name="url" value="${o.url}"/><br/><br/>
			是否是原创:<br/>
			<hk:radioarea name="authorflg" checkedvalue="${o.authorflg}">
				<hk:radio value="<%=HkObjArticle.AUTHORFLG_N %>" data="hkobjarticle.authorflg_0" res="true"/>
				<hk:radio value="<%=HkObjArticle.AUTHORFLG_Y %>" data="hkobjarticle.authorflg_1" res="true"/>
			</hk:radioarea><br/><br/>
			可以联系我的E-mail:<br/>
			<hk:text name="email" value="${o.email}"/><br/><br/>
			作者姓名:<br/>
			<hk:text name="author" value="${o.author}"/><br/><br/>
			联系电话:<br/>
			<hk:text name="tel" value="${o.tel}"/><br/><br/>
			博客地址:<br/>
			<hk:text name="blog" value="${o.blog}"/><br/>
			<hk:submit value="修改"/>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/op/article_my.do?repage=${repage}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>
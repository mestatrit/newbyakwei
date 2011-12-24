<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="创建徽章 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<hk:form enctype="multipart/form-data" action="/admin/badge_createnolimit.do">
			<hk:hide name="ch" value="1"/>
			报到次数:<br/>
			<hk:text name="num" value="${num}"/><br/><br/>
			名称:<br/>
			<hk:text name="name" value="${o.name}"/><br/><br/>
			说明:<br/>
			<hk:textarea name="intro" value="${o.intro}"/><br/><br/>
			图片:<br/>
			<hk:file name="f"/><br/>
			<hk:submit value="提交"/>
		</hk:form>
	</div>
	<div class="hang">
	<hk:a href="/admin/badge_sel.do">返回</hk:a>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>
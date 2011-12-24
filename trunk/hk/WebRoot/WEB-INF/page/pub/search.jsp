<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="搜索 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">
		<hk:form method="get" action="/user/search.do">
			<hk:hide name="sfrom" value="ias"/>
			搜索会员,请输入昵称<br/>
			<hk:text name="sw" maxlength="10"/><br/>
			<hk:submit value="搜索"/>
		</hk:form>
	</div>
	<div class="hang">
		<hk:form method="get" action="/laba/search.do">
			<hk:hide name="sfrom" value="ias"/>
			搜索喇叭内容<br/>
			<hk:text name="sw" maxlength="10"/><br/>
			<hk:submit value="搜索"/>
		</hk:form>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>
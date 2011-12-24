<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="修改短信开箱暗号 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<hk:form action="/box/op/op_updateBoxKey.do">
			<hk:hide name="boxId" value="${boxId}"/>
			<hk:hide name="fromadmin" value="${fromadmin}"/>
			<hk:hide name="repage" value="${repage}"/>
			<hk:hide name="t" value="${t}"/>
			${box.name}<br/><br/>
			短信开箱口令:<br/>
			<hk:text name="boxKey" maxlength="10" value="${box.boxKey}"/><br/>
			<span class="ruo s"><hk:data key="box.boxKey.tip"/></span>
			<div class="hang">
				<hk:submit value="保存" clazz="sub"/>
			</div>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/box/op/op_getbox.do?boxId=${boxId }&repage=${repage}&t=${t}&fromadmin=${fromadmin }">返回</hk:a>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>
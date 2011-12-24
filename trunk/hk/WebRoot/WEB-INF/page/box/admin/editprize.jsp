<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="修改宝箱的开箱物品 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">修改宝箱的开箱物品</div>
	<div class="hang">
		<hk:form enctype="multipart/form-data" action="/box/admin/adminbox_updateprize.do">
			<hk:hide name="boxId" value="${boxId}"/>
			<hk:hide name="prizeId" value="${prizeId}"/>
			<div class="hang">
				物品名称(不可超过15个字符)<br/>
				<hk:text name="name" maxlength="15" value="${prize.name}"/>
			</div>
			<div class="hang">
				物品描述(不超过50个汉字)<br/>
				<hk:text name="tip" maxlength="50" value="${prize.tip}"/>
			</div>
			<div class="hang">
				物品数量<br/>
				<hk:text name="pcount" clazz="number" maxlength="5" value="${prize.pcount}"/>
			</div>
			<div class="hang"><hk:submit value="保存"/></div>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/box/admin/adminbox_getprebox.do?boxId=${boxId}">返回</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>
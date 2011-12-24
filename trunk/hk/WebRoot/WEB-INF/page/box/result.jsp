<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="开箱结果 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">${box.name }奖品信息</div>
	<div class="hang">${prize.name}</div>
	<div class="hang odd">
		${prize.tip}<br/>
		<c:if test="${prize.useSignal}">
			兑现方法：<br/>
			序列号：<span class="b">${userBoxPrize.prizeNum }</span><br/>
			暗号：<span class="b">${userBoxPrize.prizePwd }</span><br/>
		</c:if>
	</div>
	<c:if test="${equipment!=null}">
		<div class="hang">${box.name }开箱副产品</div>
		<div class="hang odd">
			${equipment.name }<br/>
			${equipment.intro }
		</div>
	</c:if>
	<div class="hang">
		<hk:form action="/box/box.do?boxId=${box.boxId}">
			<hk:submit value="返回继续开宝箱"/>
		</hk:form>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.BoxPrize"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="设定${box.name }宝箱的开箱物品 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">设定${box.name }宝箱的开箱物品</div>
	<c:if test="${box.virtual}">
	<div class="hang"><hk:a href="/box/op/op_selequ.do?boxId=${boxId}" needreturnurl="true">选择道具</hk:a></div>
	</c:if>
	<div>
		<hk:form action="/box/op/op_createPrize.do">
			<hk:hide name="boxId" value="${boxId}"/>
			<hk:hide name="eid" value="${o.eid}"/>
			<div class="hang">
				物品名称<span class="ruo s">(不可超过15个字符)</span> <br/>
				<hk:text name="name" maxlength="15" value="${o.name}"/>
			</div>
			<div class="hang">
				物品提示语<span class="ruo s">(不超过50个汉字)</span> <br/>
				<hk:textarea name="tip" value="${o.tip}"/>
			</div>
			<div class="hang">
				物品数量<br/>
				<hk:text name="pcount" clazz="number" maxlength="5" value="${o.pcount}"/>
			</div>
			<div class="hang">
				是否支持兑换<br/>
				<hk:radioarea name="signal" checkedvalue="${o.signal}">
					<hk:radio value="<%=BoxPrize.SIGNAL_N %>"/>否
					<hk:radio value="<%=BoxPrize.SIGNAL_Y %>"/>是<br/>
				</hk:radioarea>
			</div>
			<div>
				<hk:submit name="add" value="完成并继续提交物品" clazz="sub"/>
				<hk:submit name="complete" value="完成"/>
			</div>
		</hk:form>
	</div>
	<div class="hang">
		<c:forEach var="p" items="${list}" varStatus="idx">
			<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="c4" /></c:if>
			<c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="c3" /></c:if>
			<div class="${clazz_var}">
				<span class="fg">${p.name}</span>
				<span class="fg">${p.pcount}</span>
				<span><hk:a href="/box/op/op_delPrize.do?boxId=${boxId}&prizeId=${p.prizeId }">删</hk:a></span>
			</div>
		</c:forEach>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>
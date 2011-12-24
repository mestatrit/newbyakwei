<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.BoxPrize"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="修改${box.name }宝箱的开箱物品 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">修改${box.name }宝箱的开箱物品</div>
	<div>
		<hk:form action="/box/op/op_updateprize.do">
			<hk:hide name="ch" value="1"/>
			<hk:hide name="prizeId" value="${prizeId}"/>
			<hk:hide name="boxId" value="${boxId}"/>
			<hk:hide name="fromadmin" value="${fromadmin}"/>
			<hk:hide name="t" value="${t}"/>
			<div class="hang">
				物品名称<span class="ruo s">(不可超过15个字符)</span> <br/>
				<hk:text name="name" maxlength="15" value="${prize.name}"/>
			</div>
			<div class="hang">
				物品提示语<span class="ruo s">(不超过50个汉字)</span> <br/>
				<hk:text name="tip" maxlength="50" value="${prize.tip}"/>
			</div>
			<div class="hang">
				物品数量<br/>
				<hk:text name="pcount" clazz="number" maxlength="5" value="${prize.pcount}"/>
			</div>
			<div class="hang">
				是否支持兑换<br/>
				<hk:radioarea name="signal" checkedvalue="${prize.signal}">
					<hk:radio value="<%=BoxPrize.SIGNAL_N %>"/>否
					<hk:radio value="<%=BoxPrize.SIGNAL_Y %>"/>是<br/>
				</hk:radioarea>
			</div>
			<div>
				<hk:submit value="提交"/>
			</div>
		</hk:form>
	</div>
	<div class="hang">
	<hk:a href="/box/op/op_getbox.do?boxId=${boxId}&t=${t }&fromadmin=${fromadmin }">返回</hk:a>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>
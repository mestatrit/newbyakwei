<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="确认订单" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang reply">个人信息
	<c:if test="${maininfo!=null}"><hk:a href="/op/orderformuser_toupdatewap.do?oid=${maininfo.oid}&fromorderform=1" clazz="s split-r">修改</hk:a></c:if>
	<hk:a clazz="s" href="/op/orderformuser_tocreatewap.do?fromorderform=1">添加</hk:a>
	<div><c:if test="${maininfo==null}"><hk:a href="/op/">添加个人信息</hk:a></c:if></div>
	</div>
	<div class="hang odd">
		<c:if test="${fn:length(infolist)>1}">
			<hk:form action="/op/orderformuser_setdef.do?fromorderform=1">
				联系方式：<br/>
				<hk:select name="oid">
				<c:forEach var="info" items="${infolist}"><hk:option value="${info.oid}" data="${info.title}"/></c:forEach>
				</hk:select><br/>
				<hk:submit value="选择"/>
			</hk:form>
		</c:if>
		<c:if test="${maininfo!=null}">
		<c:if test="${not empty maininfo.name}">
			联系人：<br/>
			${maininfo.name }<br/>
		</c:if>
		<c:if test="${not empty maininfo.mobile}">
			手机号码：<br/>
			${maininfo.mobile }<br/>
		</c:if>
		<c:if test="${not empty maininfo.tel}">
			座机：<br/>
			${maininfo.tel }<br/>
		</c:if>
		<c:if test="${not empty maininfo.email}">
			E-mail：<br/>
			${maininfo.email }
		</c:if>
		</c:if>
	</div>
	<div class="hang reply">购物车 <hk:a clazz="s" href="/shoppingcard_wap.do">修改</hk:a></div>
	<c:forEach var="vo" items="${productvolist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
	<div class="hang ${clazz_var }">
	<hk:a href="/product_wap.do?pid=${vo.cmpProduct.productId}">${vo.cmpProduct.name }</hk:a> ${vo.count }份
	<br/>
	单价：￥${vo.cmpProduct.money } 小计：￥${vo.cmpProduct.money*vo.count }
	</div>
	</c:forEach>
	<hk:form action="/op/orderform_createwap.do">
		<hk:hide name="info_oid" value="${maininfo.oid}"/>
		<div class="hang">金额总计：￥${totalPrice }</div>
		<div class="hang reply">预订时间</div>
		<div class="hang odd">
			日期：<span class="ruo s">格式为yyyy-MM-dd</span><br/>
			<hk:text name="date" maxlength="10"/><br/>
			时间：<span class="ruo s">格式为HH:mm</span><br/>
			<hk:text name="time" maxlength="5"/><br/>
		</div>
		<div class="hang reply">给店家留言<span class="ruo s">150字</span></div>
		<div class="hang">
		<hk:textarea name="content" clazz="ipt2"/>
		<hk:submit value="提交订单"/>
		</div>
	</hk:form>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>
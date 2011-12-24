<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${box.name} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<hk:hide name="boxId" value="${boxId}"/><hk:hide name="repage" value="${repage}"/>
	<div class="hang">
		宝箱名称:<br/>
		${box.name}<br/><br/>
		<c:if test="${box.virtual}">
		虚拟宝箱<br/><br/>
		</c:if>
		<c:if test="${box.otherPrize}">
		可以开副产品<br/><br/>
		</c:if>
		地区:<br/>
		<c:if test="${box.cityId>0}">${zoneName }</c:if>
		<c:if test="${box.cityId==0}">全球</c:if><br/><br/>
		宝箱介绍:<br/>
		${box.intro}<br/><br/>
		宝箱数量:<br/>
		${box.totalCount}<br/><br/>
		剩余数量:<br/>
		${box.totalCount-box.openCount}<br/><br/>
		时间:<br/>
		<fmt:formatDate value="${box.beginTime}" pattern="yyyy-MM-dd HH:mm"/>开始<br/>
		<fmt:formatDate value="${box.endTime}" pattern="yyyy-MM-dd HH:mm"/>结束
		<br/><br/>
		短信开箱暗号:<br/>
		${box.boxKey}<br/><br/>
		宝箱类别:
		${boxType.name }<br/><br/>
		开箱限制:
		<c:if test="${box.pretype==0}">不限制</c:if>
		<c:if test="${box.pretype!=0}">${box.precount }个/每人每${boxPretype.name }</c:if>
		<br/><br/>
		参与方式:<br/>
		<hk:rmBlankLines rm="true">
			<c:if test="${box.opentype==0}">网站和短信</c:if>
			<c:if test="${box.opentype==1}">短信</c:if>
			<c:if test="${box.opentype==2}">网站</c:if>
		</hk:rmBlankLines>
		<br/><br/>	
		奖品列表:
		<c:if test="${!overdue && pause }">
		<hk:a href="/box/op/op_createprize2.do?boxId=${boxId}&t=${t }&fromadmin=${fromadmin }">添加新奖品</hk:a>
		</c:if>
		<br/>
		<c:forEach var="p" items="${list}" varStatus="idx">
			<div class="hang">
				<span class="split-r">
					<c:if test="${not empty p.path}">
						<img src="${p.h_0Pic }"/>
					</c:if>
				 ${p.name}/${p.pcount}个/剩余${p.remain }个</span>
				<c:if test="${(!overdue && pause) || box.unChecked}">
					<hk:a clazz="split-r" href="/box/op/op_updateprize.do?prizeId=${p.prizeId}&boxId=${boxId }&t=${t }&fromadmin=${fromadmin }">改</hk:a>
					<c:if test="${p.pcount==p.remain}">
						<hk:a clazz="split-r" href="/box/op/op_delprize2.do?prizeId=${p.prizeId}&boxId=${boxId }&t=${t }&fromadmin=${fromadmin }">删</hk:a>
					</c:if>
					<c:if test="${not empty p.path}">
						<hk:a clazz="split-r" href="/box/op/op_selprizepic.do?prizeId=${p.prizeId}&boxId=${boxId }&t=${t }&fromadmin=${fromadmin }">更换图片</hk:a>
					</c:if>
					<c:if test="${empty p.path}">
						<hk:a clazz="split-r" href="/box/op/op_selprizepic.do?prizeId=${p.prizeId}&boxId=${boxId }&t=${t }&fromadmin=${fromadmin }">添加图片</hk:a>
					</c:if>
				</c:if>
			</div>
		</c:forEach><br/>
			<hk:form action="/box/op/op_optbox.do">
				<hk:hide name="fromadmin" value="${fromadmin}"/>
				<hk:hide name="boxId" value="${boxId}"/>
				<hk:hide name="repage" value="${repage}"/>
				<hk:hide name="t" value="${t}"/>
				<c:if test="${normal && !overdue}">
					<hk:submit clazz="sub" name="pause" value="停止"/>
					<hk:submit clazz="sub" name="stop" value="作废"/>
					<hk:submit name="editkey" value="修改开箱暗号"/>
				</c:if>
				<c:if test="${pause && !overdue}">
					<hk:submit clazz="sub" name="cont" value="运行"/>
					<hk:submit clazz="sub" name="stop" value="作废"/>
					<hk:submit name="editkey" value="修改开箱暗号"/>
				</c:if>
			</hk:form>
		<c:if test="${!overdue }">
		<div class="hang"><hk:a href="/box/op/op_toeditbox.do?boxId=${boxId}&t=${t }&repage=${repage }&fromadmin=${fromadmin }">修改箱子信息</hk:a></div>
		</c:if>
	</div>
	<div class="hang"><hk:a href="/box/op/op_back.do?t=${t}&repage=${repage }&fromadmin=${fromadmin }">返回</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>
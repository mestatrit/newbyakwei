<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">${box.name }</c:set>
<c:set var="body_hk_content" scope="request">
	<table class="frame-3" cellpadding="0" cellspacing="0">
		<tr>
			<td class="l">
				<jsp:include page="../inc/userleftnav_inc.jsp"></jsp:include>
			</td>
			<td class="mid">
				<div class="mid_con">
					<div class="mod">
						<c:set var="nav_2_short_content" scope="request">
						<li><a class="nav-a" href="<%=path %>/box_list.do">宝箱市场</a></li>
						<li><a class="nav-a" href="<%=path %>/box.do?boxId=${box.boxId }">${box.name }</a></li>
						</c:set>
						<jsp:include page="../inc/nav-2-short2.jsp"></jsp:include>
					</div>
					<div class="mod">
						<h2 class="line">${box.name }</h2>
						<div><img src="<%=path %>/webst3/img/bx60.gif"/></div>
						<div class="text_14">
							${box.intro}<br/>
							${box.name}的奖品有:<br/>
							<c:forEach var="p" items="${list}" varStatus="idx">
								<div class="${clazz_var}">${p.name}${p.pcount}个</div>
							</c:forEach>
							<c:if test="${onlysmsopen}">
								<div class="yzm">提示:本宝箱仅仅支持短信开箱,请在活动现场通过短信参与.参与暗号请通过现场获得</div>
							</c:if>
							<c:if test="${!onlysmsopen && begin && !stop}">
								<span id="openboxcon"><hk:button clazz="btn" oid="openbtn" value="开箱子" onclick="openbox()"/></span>
							</c:if>
							<div id="openinfo"></div>
						</div>
					</div>
				</div>
			</td>
			<td class="r">
				<div class="f_r"></div>
			</td>
		</tr>
	</table>
	<hk:form oid="openfrm" action="/op/openbox.do" onsubmit="return openbox()" clazz="hide" target="hideframe">
		<hk:hide name="boxId" value="${boxId}"/>
		<hk:hide name="ajax" value="1"/>
	</hk:form>
<script type="text/javascript">
function openbox(){
	setHtml('openboxcon','正在开箱子，请稍后 ... ...');
	setHtml('openinfo','');
	getObj('openfrm').submit();
}
function afterSubmit(error,error_msg,op_func,obj_id_param){
	if(error!="0"){
		alert(error_msg);
	}
}
function getResult(award,info){
	setHtml('openboxcon','<hk:button clazz="btn" oid="openbtn" value="开箱子" onclick="openbox()"/>');
	if(award){
		getObj('openinfo').className="ok";
	}
	else{
		getObj('openinfo').className="error";
	}
	setHtml('openinfo',info);
}
</script>

</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>
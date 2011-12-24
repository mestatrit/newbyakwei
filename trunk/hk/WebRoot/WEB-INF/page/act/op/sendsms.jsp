<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="view.sendgroupactsms"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even" onkeydown="submitLaba(event)">
		<hk:form name="labafrm" onsubmit="return confirmCreate();" action="/act/op/act_sendsms.do">
			<c:forEach var="id" items="${useridset.useridSet}">
				<hk:hide name="userId" value="${id}"/>
			</c:forEach>
			<hk:hide name="actId" value="${actId}"/>
			<hk:textarea oid="status" name="content" value="${userSms.content}" clazz="ipt2" rows="2"/>
			<hk:submit value="view.submit" res="true"/>  <span id="remaining" class="ruo s">70</span><span class="ruo s"><hk:data key="view.char"/></span>
		</hk:form>
<script type="text/javascript">
	function updateCount() {
		document.getElementById("remaining").innerHTML = (70 - document.getElementById("status").value.length);
		setTimeout(updateCount, 500);
	}
	function confirmCreate() {
		var content=document.getElementById("status").value;
		var len = content.length;
		if (len >70 ){
			alert("<hk:data key="99"/>");
			return false;
		}
		return true;
	}
	updateCount();
	function submitLaba(event){
		if((event.ctrlKey)&&(event.keyCode==13)){
			if(confirmCreate()){
				document.labafrm.submit();
			}
		}
	}
</script>
	</div>
	<div class="hang"><hk:a href="/act/act.do?actId=${actId}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>
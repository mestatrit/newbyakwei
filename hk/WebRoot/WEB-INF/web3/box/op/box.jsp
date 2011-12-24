<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="${box.name }" /></c:set>
<c:set var="mgr_content" scope="request">
<div class="text_14">
	<h2 class="line">${box.name }</h2>
		宝箱数量：
		${box.totalCount}<br/>
		剩余数量：
		${box.totalCount-box.openCount}<br/><br/>
		时间：
		<fmt:formatDate value="${box.beginTime}" pattern="yyyy-MM-dd HH:mm"/>开始 
		<fmt:formatDate value="${box.endTime}" pattern="yyyy-MM-dd HH:mm"/>结束
		<br/><br/>
		短信开箱暗号：<span id="boxkey_lable">${box.boxKey}</span>
		<br/><br/>
		宝箱类别：
		${boxType.name }<br/><br/>
		开箱限制：
		<c:if test="${box.pretype==0}">不限制</c:if>
		<c:if test="${box.pretype!=0}">${box.precount }个/每人每${boxPretype.name }</c:if>
		<br/><br/>
		参与方式：
		<hk:data key="box.opentype${box.opentype}"/>
		<br/><br/>
		宝箱介绍：<br/>
		${box.intro}<br/>
		奖品列表：<br/>
		<c:forEach var="p" items="${list}" varStatus="idx">
			${idx.count }. ${p.name}/${p.pcount}个/剩余${p.remain }个<br/>
		</c:forEach><br/>
		<c:if test="${normal && !overdue}">
			<hk:button value="停止" clazz="btn" onclick="pausebox()"/> 
			<hk:button value="作废" clazz="btn" onclick="stopbox()"/> 
			<hk:button value="修改开箱暗号" clazz="btn2" onclick="toeditkey()"/> 
		</c:if>
		<c:if test="${pause && !overdue}">
			<hk:button value="运行" clazz="btn" onclick="runbox()"/> 
			<hk:button value="作废" clazz="btn" onclick="stopbox()"/> 
			<hk:button value="修改开箱暗号" clazz="btn2" onclick="toeditkey()"/> 
		</c:if>
		<c:if test="${!overdue }">
		<hk:button value="修改箱子信息" clazz="btn2" onclick="toedit()"/>
		</c:if>
</div>
<div>
	<a class="return" href="<%=path %>/box/op/op_my.do">返回</a>
</div>
<script type="text/javascript">
var boxId=${boxId};
var isPause=${box.pause};
function pausebox(){
	if(window.confirm("确实要停止宝箱？")){
		tourl("<%=path %>/box/op/op_pauseweb.do?boxId="+boxId);
	}
}
function stopbox(){
	if(window.confirm("确实要作废宝箱？")){
		tourl("<%=path %>/box/op/op_stopweb.do?boxId="+boxId);
	}
}
function runbox(){
	tourl("<%=path %>/box/op/op_contweb.do?boxId="+boxId);
}
function toeditkey(){
	createBg();
	createCenterWindow('key_win',500,200,'修改开箱暗号','<div> <hk:form target="hideframe" method="post" onsubmit="return subkeyfrm(this.id)" action="/box/op/op_updateboxkeyweb.do"> <hk:hide name="boxId" value="${boxId}"/> <table class="infotable" cellpadding="0" cellspacing="0"> <tr> <td width="90px">开箱暗号</td> <td> <div class="f_l"> <input type="text" id="boxkey" name="boxKey" class="text" maxlength="20" /> <br /> <div class="error" id="boxkey_error"></div> </div> <div class="clr"></div> </td> </tr> <tr> <td></td> <td> <div class="form_btn"> <hk:submit value="保存" clazz="btn"/> </div> </td> </tr> </table> </hk:form> </div>',"hideWindow('key_win');clearBg();");
	getObj('boxkey').value=getHtml('boxkey_lable');
}
function subkeyfrm(frmid){
	validateClear('boxkey');
	showSubmitDiv('key_win');
	return true;
}
function afterSubmit(error,error_msg,op_func,obj_id_param){
	if(error!="0"){
		validateErr("boxkey",error_msg);
		hideSubmitDiv();
	}
	else{
		setHtml('boxkey_lable',getObj('boxkey').value);
		setHtml('key_win_content','修改成功');
		hideSubmitDiv();
		delay('updatekeyok()',2000);
	}
}
function updatekeyok(){
	hideWindow('key_win');
	clearBg();
}
function toedit(){
	if(!isPause){
		if(window.confirm('修改信息需要停止宝箱，确定要停止？')){
			tourl('<%=path %>/box/op/op_toeditboxweb.do?boxId=${boxId}');
		}
	}
	else{
		tourl('<%=path %>/box/op/op_toeditboxweb.do?boxId=${boxId}');
	}
}
</script>
</c:set>
<jsp:include page="../../inc/usermgr_inc.jsp"></jsp:include>
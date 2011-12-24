<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_body_content" scope="request">
<div class="mod">
	<h1 class="s1">${cmpOrgNav.name }
	</h1>
	<div class="mod_content">
		<c:if test="${adminorg}">
		<a href="<%=path %>/epp/web/org/msg_list.do?companyId=${companyId}&orgId=${orgId}&orgnavId=${orgnavId}">留言管理</a>
		</c:if>
		<div>
		<form id="msgfrm" method="post" onsubmit="return submsgfrm(this.id)" action="<%=path %>/epp/web/org/msg.do" target="hideframe">
			<hk:hide name="ch" value="1"/>
			<hk:hide name="companyId" value="${companyId}"/>
			<hk:hide name="orgId" value="${orgId}"/>
			<div class="divrow">
				<table class="nt all" cellpadding="0" cellspacing="0">
					<tr>
						<td>* <hk:data key="epp.cmporgmsg.name"/></td>
						<td><input type="text" class="text" name="name" maxlength="20"/>
						<div class="infowarn" id="_name"></div>
						</td>
					</tr>
					<tr>
						<td>* <hk:data key="epp.cmporgmsg.tel"/></td>
						<td><input type="text" class="text" name="tel" maxlength="20"/>
						<div class="infowarn" id="_tel"></div>
						</td>
					</tr>
					<tr>
						<td>* <hk:data key="epp.cmporgmsg.email"/></td>
						<td><input type="text" class="text" name="email" maxlength="50"/>
						<div class="infowarn" id="_email"></div>
						</td>
					</tr>
					<tr>
						<td>* <hk:data key="epp.cmporgmsg.im"/></td>
						<td><input type="text" class="text" name="im" maxlength="50"/>
						<div class="infowarn" id="_im"></div>
						</td>
					</tr>
					<tr>
						<td>* <hk:data key="epp.cmporgmsg.content"/></td>
						<td>
							<hk:data key="epp.cmpmsg.content.tip"/><br/>
							<textarea name="content" style="width:580px;height: 100px;"></textarea>
							<div class="infowarn" id="_content"></div>
						</td>
					</tr>
					<tr>
						<td align="right">*验证码：</td>
						<td>
							<hk:text name="imgv" maxlength="4"/>
							<img id="codeimg" style="vertical-align: middle;" src="<%=path %>/pub/epp/imgv.do?v=<%=Math.random() %>"/>
							<a href="javascript:showimg()"><hk:data key="epp.getnewvalidatecode"/></a>
							<div class="infowarn" id="_imgv"></div>
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<hk:submit clazz="btn" value="epp.submit" res="true"/>
						</td>
					</tr>
				</table>
			</div>
		</form>
		</div>
	</div>
</div>
<script type="text/javascript">
var err_code_<%=Err.CMPORGMSG_NAME_ERROR %>={objid:"_name"};
var err_code_<%=Err.CMPORGMSG_TEL_ERROR %>={objid:"_tel"};
var err_code_<%=Err.CMPORGMSG_IM_ERROR %>={objid:"_im"};
var err_code_<%=Err.CMPORGMSG_EMAIL_ERROR %>={objid:"_email"};
var err_code_<%=Err.CMPORGMSG_CONTENT_ERROR %>={objid:"_content"};
var err_code_<%=Err.IMG_VALIDATE_CODE_ERROR %>={objid:"_imgv"};
function submsgfrm(frmid){
	setHtml('_name','');
	setHtml('_tel','');
	setHtml('_im','');
	setHtml('_email','');
	setHtml('_content','');
	setHtml('_imgv','');
	showGlass(frmid);
	return true;
}
function createok(error,msg,v){
	refreshurl();
}
function createerror(error,msg,v){
	setHtml(getoidparam(error),msg);hideGlass();
}
function showimg(){
	getObj('codeimg').src="<%=path %>/pub/epp/imgv.do?v="+Math.random();
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>
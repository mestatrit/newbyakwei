<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view2.user.setting_domain"/></c:set>
<c:set var="html_body_content" scope="request">
<link rel="stylesheet" type="text/css" href="<%=path %>/webst4/css/op.css" />
<div class="hcenter" style="width: 800px">
<div class="userop_l">
	<jsp:include page="set_inc.jsp"></jsp:include>
</div>
<div class="userop_r">
<div>
	<form id="infofrm" method="post" onsubmit="return subinfofrm(this.id)" action="<%=path %>/h4/op/user/set_setdomain.do" target="hideframe">
		<hk:hide name="ch" value="1"/>
		<table class="reg nt all" cellpadding="0" cellspacing="0">
			<tr>
				<td width="600px;">
					<div>
						<hk:data key="view2.user.domain"/>：<span class=""><hk:data key="view2.user.domain.tip"/></span>
					</div>
					<div class="f_l">
						http://www.huoku.com/<input type="text" name="domain" value="${user.domain}" class="text" maxlength="20" style="margin-bottom: 0;"/>
					</div>
					<div id="_domain" class="f_l infowarn"></div>
					<div class="clr"></div>
				</td>
				<td>
				</td>
			</tr>
			<tr>
				<td align="center"><hk:submit value="view2.submit" res="true" clazz="btn"/>
				</td>
				<td>
				</td>
			</tr>
		</table>
	</form>
</div>
</div>
<div class="clr"></div>
</div>
<script type="text/javascript">
var err_code_<%=Err.USER_DOMAIN_DUPLICATE %>={objid:"_domain"};
var err_code_<%=Err.USER_DOMAIN_ERROR2 %>={objid:"_domain"};
function subinfofrm(frmid){
	showGlass(frmid);
	setHtml('_domain','');
	return true;
}
function domainok(error,msg,v){
	refreshurl();
}
function domainerror(error,msg,v){
	setHtml('_domain',msg);
	hideGlass();
}
</script>
</c:set>
<jsp:include page="../../inc/frame.jsp"></jsp:include>
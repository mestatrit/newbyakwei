<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.bean.CmpTip"%><%@page import="com.hk.web.util.HkWebUtil"%>
<%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath();%>
<c:set var="html_title" scope="request"><hk:data key="view2.website.title"/></c:set>
<c:set var="js_value" scope="request">
<script src="<%=path %>/webst4/js/js4/createvenue.js" type="text/javascript"></script>
</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 700px;">
<table class="nt reg" cellpadding="0" cellspacing="0">
<tr>
	<td><h1 style="display:inline"><hk:data key="view2.create_new_venue"/></h1></td>
</tr>
</table>
<hr/>
<br/>
<div>
	<form id="sfrm" method="post" onsubmit="return subvenuefrm()" action="/venue/create" target="hideframe">
		<input type="hidden" name="marker_x" id="_marker_x"/>
		<input type="hidden" name="marker_y" id="_marker_y"/>
		<hk:hide name="ch" value="1"/>
		<table class="nt reg" cellpadding="0" cellspacing="0">
			<tr>
				<td width="90px" align="right"><hk:data key="company.name"/></td>
				<td width="260px;">
					<input id="ipt_name" name="name" value="${name }" maxlength="30" class="text"/>
				</td>
				<td>
					<div class="ruo"><hk:data key="company.name.tip"/></div>
					<div id="_name" class="infowarn"></div>
				</td>
			</tr>
			<tr>
				<td align="right"></td>
				<td>
					<input id="doneflg_a" type="radio" name="doneflg" value="<%=CmpTip.DONEFLG_DONE %>" checked="checked"/>
					<label for="doneflg_a"><hk:data key="view2.i_did_this_and_it_was_awesome"/></label><br/>
					<input id="doneflg_b" type="radio" name="doneflg" value="<%=CmpTip.DONEFLG_TODO %>"/>
					<label for="doneflg_b"><hk:data key="view2.add_this_to_my_todo_list"/></label>
				</td>
				<td>
				</td>
			</tr>
			<tr>
				<td align="right"><hk:data key="view2.usertip"/></td>
				<td>
					<textarea id="_content" name="content" style="height: 100px;width: 270px;margin-bottom: 10px;"/></textarea>
				</td>
				<td>
					<div class="ruo"><hk:data key="view2.cmptip.content.tip"/></div>
				</td>
			</tr>
			<tr>
				<td align="right"><hk:data key="company.addr"/></td>
				<td>
					<hk:text name="addr" maxlength="300" clazz="text"/>
				</td>
				<td>
					<div id="_addr" class="infowarn"></div>
				</td>
			</tr>
			<tr>
				<td align="right"><hk:data key="company.tel"/></td>
				<td>
					<hk:text name="tel" maxlength="300" clazz="text"/>
				</td>
				<td>
					<div id="_tel" class="infowarn"></div>
				</td>
			</tr>
			<tr>
				<td align="right"><hk:data key="company.zone"/></td>
				<td>
					<div style="margin-bottom: 10px">
					<jsp:include page="../../inc/zonesel.jsp"></jsp:include>
					<script type="text/javascript">
					initselected(${pcityId});
					</script>
					</div>
				</td>
				<td>
					<div id="_zoneName" class="infowarn"></div>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<hk:submit oid="subbtn" clazz="btn2" value="view2.submit" res="true"/>
				</td>
			</tr>
		</table>
	</form>
</div>
</div>
<script type="text/javascript">
var err_code_<%=Err.COMPANY_NAME_ERROR %>={objid:"_name"};
var err_code_<%=Err.COMPANY_NAME_DUPLICATE %>={objid:"_name"};
var err_code_<%=Err.COMPANY_ADDR_LEN_TOOLONG %>={objid:"_addr"};
var err_code_<%=Err.COMPANY_TEL_LEN_TOOLONG %>={objid:"_tel"};
var err_code_<%=Err.ZONE_ERROR %>={objid:"_zoneName"};
var companyId=0;
var doneflg=<%=CmpTip.DONEFLG_DONE %>;
getObj('subbtn').disabled=false;
</script>
</c:set><jsp:include page="../../inc/frame.jsp"></jsp:include>
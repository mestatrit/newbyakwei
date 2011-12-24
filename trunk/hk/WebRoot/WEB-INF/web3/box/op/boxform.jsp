<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.hk.svr.pub.BoxTypeUtil"%>
<%@page import="com.hk.svr.pub.BoxOpenTypeUtil"%>
<%@page import="com.hk.svr.pub.BoxPretypeUtil"%>
<%@page import="com.hk.bean.BoxType"%>
<%@page import="com.hk.bean.BoxOpenType"%>
<%@page import="com.hk.bean.BoxPretype"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); 
List<BoxType> typeList = BoxTypeUtil.getTypeList();
List<BoxOpenType> opentypelist = BoxOpenTypeUtil.getTypeList();
List<BoxPretype> prelist = BoxPretypeUtil.getList();
request.setAttribute("prelist", prelist);
request.setAttribute("typeList", typeList);
request.setAttribute("opentypelist", opentypelist);%>
<script type="text/javascript" src="<%=path %>/webst3/js/date/date.js"></script>
<script type="text/javascript" src="<%=path %>/webst3/js/date/jquery.datePicker.min-2.1.2.js"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<%=path %>/webst3/js/date/datePicker.css">
<form id="boxfrmid" method="post" onsubmit="return subboxfrm(this.id)" action="${box_form_action }" target="hideframe">
	<hk:hide name="boxId" value="${boxId}"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<div id="hidev"></div>
	<table cellpadding="0" cellspacing="0" class="infotable">
		<tr>
			<td width="90px">宝箱名称</td>
			<td>
				<div class="f_l">
					<hk:text name="name" value="${o.name}" maxlength="15" clazz="text"/><br/>
					<div id="box_name_error" class="error"></div>
				</div>
				<div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td width="90px">宝箱数量</td>
			<td>
				<div class="f_l">
					<hk:text name="totalCount" value="${o.totalCount}" maxlength="8" clazz="text"/><br/>
					<div id="box_totalCount_error" class="error"></div>
				</div>
				<div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td width="90px">开始时间</td>
			<td>
				<div class="f_l">
				<c:set var="begint"><fmt:formatDate value="${o.beginTime}" pattern="yyyy-MM-dd"/></c:set>
				<c:set var="begint_hour"><fmt:formatDate value="${o.beginTime}" pattern="HH"/></c:set>
				<c:set var="begint_min"><fmt:formatDate value="${o.beginTime}" pattern="mm"/></c:set>
					<hk:text name="begint" value="${begint}" maxlength="12" clazz="text_short_1 date-pick1"/>
					<hk:text name="begint_hour" maxlength="2" value="${begint_hour}" clazz="text_short_4"/>时
					<hk:text name="begint_min" maxlength="2" value="${begint_min}" clazz="text_short_4"/>分
					<br/>
					<div id="box_beginTime_error" class="error"></div>
				</div>
				<div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td width="90px">结束时间</td>
			<td>
				<div class="f_l">
					<c:set var="endt"><fmt:formatDate value="${o.endTime}" pattern="yyyy-MM-dd"/></c:set>
					<c:set var="endt_hour"><fmt:formatDate value="${o.endTime}" pattern="HH"/></c:set>
					<c:set var="endt_min"><fmt:formatDate value="${o.endTime}" pattern="mm"/></c:set>
					<hk:text name="endt" value="${endt}" maxlength="12" clazz="text_short_1 date-pick1"/>
					<hk:text name="endt_hour" maxlength="2" value="${endt_hour}" clazz="text_short_4"/>时
					<hk:text name="endt_min" maxlength="2" value="${endt_min}" clazz="text_short_4"/>分
					<br/>
					<div id="box_endTime_error" class="error"></div>
				</div>
				<div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td width="90px">短信开箱指令</td>
			<td>
				<div class="f_l">
					<hk:text name="boxKey" maxlength="10" value="${o.boxKey}" clazz="text"/><br/>
					<div id="box_boxKey_error" class="error"></div>
				</div>
				<div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td width="90px">宝箱介绍</td>
			<td>
				<div class="f_l">
					<hk:textarea name="intro" value="${o.intro}" clazz="text_area"/><br/>
					<div id="box_intro_error" class="error"></div>
				</div>
				<div class="clr"></div>
			</td>
		</tr>
		<c:if test="${boxId>0}">
			<tr>
				<td width="90px">宝箱类别</td>
				<td>
					<div class="f_l">
						<hk:select name="boxType" checkedvalue="${o.boxType}">
							<hk:option value="0" data=""/>
							<c:forEach var="t" items="${typeList}">
								<hk:option value="${t.typeId}" data="${t.name}"/>
							</c:forEach>
						</hk:select><br/>
						<div id="box_tpye_error" class="error"></div>
					</div>
					<div class="clr"></div>
				</td>
			</tr>
		</c:if>
		<tr>
			<td width="90px">每用户周期内开箱限制</td>
			<td>
				<div class="f_l">
					<hk:text name="precount" size="5" clazz="text_short_1" value="${o.precount}"/>个/
					<hk:select name="pretype" checkedvalue="${o.pretype}">
						<hk:option value="0" data="不限"/>
						<c:forEach var="pre" items="${prelist}">
						<hk:option value="${pre.typeId}" data="${pre.name}"/>
						</c:forEach>					
					</hk:select><br/>
					<div id="box_precount_error" class="error"></div>
				</div>
				<div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td width="90px">参与方式</td>
			<td>
				<div class="f_l">
					<hk:select name="opentype" checkedvalue="${o.opentype}">
						<c:forEach var="t" items="${opentypelist}">
							<hk:option value="${t.typeId}" data="${t.name}"/>
						</c:forEach>
					</hk:select><br/>
				</div>
			</td>
		</tr>
		<c:if test="${boxId==0 || boxId==null}">
		<tr>
			<td>开箱物品</td>
			<td>
				<div id="prize">
				</div>
				<hk:button value="添加开箱物品" onclick="createprizewin()" clazz="btn2"/>
			</td>
		</tr>
		</c:if>
		<tr>
			<td></td>
			<td>
				<div class="form_btn"><hk:submit value="保存" clazz="btn"/></div>
			</td>
		</tr>
	</table>
</form>
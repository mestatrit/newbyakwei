<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpTable"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<script type="text/javascript" src="<%=path %>/webst3/js/date/date.js"></script>
<script type="text/javascript" src="<%=path %>/webst3/js/date/jquery.datePicker.min-2.1.2.js"></script>
<form id="frm" onsubmit="return subcmpordertablefrm(this.id)" action="<%=path %>/e/op/auth/table_updatecmpordertable.do" target="hideframe">
	<input type="hidden" name="oid" value="${oid }" />
	<input type="hidden" name="companyId" value="${companyId }" />
	<table class="infotable" cellpadding="0" cellspacing="0">
		<tr>
			<td width="80px">姓名</td>
			<td>
				<div class="f_l">
					<input name="name" type="text" class="text" value="${o.name }"/><br/>
					<div id="name_error" class="error"></div>
				</div>
				<div id="name_flag" class="flag"></div><div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td width="80px">联系电话</td>
			<td>
				<div class="f_l">
					<input name="tel" type="text" class="text" value="${o.tel }"/><br/>
					<div id="tel_error" class="error"></div>
				</div>
				<div id="tel_flag" class="flag"></div><div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td width="80px">人数</td>
			<td>
				<div class="f_l">
					<input name="personNum" type="text" class="text" value="${o.personNum }"/><br/>
					<div id="personNum_error" class="error"></div>
				</div>
				<div id="personNum_flag" class="flag"></div><div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td width="80px">餐桌</td>
			<td>
				<div class="f_l">
					<hk:select name="sortId" checkedvalue="${cmpTable.sortId}" onchange="loadtablelistbysortid(this.value)">
						<hk:option value="0" data="选择分类"/>
						<c:forEach var="sort" items="${sortlist}">
							<hk:option value="${sort.sortId}" data="${sort.name}"/>
						</c:forEach>
					</hk:select>
					<span id="edit_tablelist">
					<hk:select name="tableId" checkedvalue="${cmpTable.tableId}">
						<hk:option value="0" data="选择餐桌"/>
						<c:forEach var="table" items="${tablelist}">
							<hk:option value="${table.tableId}" data="${table.tableNum}"/>
						</c:forEach>
					</hk:select>
					</span>
					<br/>
					<div id="tableId_error" class="error"></div>
				</div>
				<div id="tableId_flag" class="flag"></div><div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td width="80px">预约时间</td>
			<td>
				<div class="f_l">
					从<br/>
					<c:set var="bd"><fmt:formatDate value="${o.beginTime}" pattern="yyyy-MM-dd"/></c:set>
					<c:set var="bt"><fmt:formatDate value="${o.beginTime}" pattern="HH:mm"/></c:set>
					日期：<input name="bd" type="text" class="text_short_1 date-pick1" value="${bd }"/>
					时间：<input name="bt" type="text" class="text_short_1" value="${bt }"/><br/>
					到<br/>
					<c:set var="ed"><fmt:formatDate value="${o.endTime}" pattern="yyyy-MM-dd"/></c:set>
					<c:set var="et"><fmt:formatDate value="${o.endTime}" pattern="HH:mm"/></c:set>
					日期：<input name="ed" type="text" class="text_short_1 date-pick2" value="${ed }"/>
					时间：<input name="et" type="text" class="text_short_1" value="${et }"/><br/>
					<div id="time_error" class="error"></div>
				</div>
				<div id="time_flag" class="flag"></div><div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td width="80px">备注</td>
			<td>
				<div class="f_l">
					<hk:textarea clazz="text_area" name="remark" value="${o.remark}"/><br/>
					<div id="remark_error" class="error"></div>
				</div>
				<div id="remark_flag" class="flag"></div><div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<div align="center">
				<input type="submit" value="提交" class="btn split-r">
				<input type="submit" name="meal" value="提交并落座" class="btn split-r">
				</div>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
$(function()
{
	$('.date-pick1').datePicker({clickInput:true,createButton:false,startDate:new Date().asString()});
	$('.date-pick2').datePicker({clickInput:true,createButton:false,startDate:new Date().asString()});
});
</script>
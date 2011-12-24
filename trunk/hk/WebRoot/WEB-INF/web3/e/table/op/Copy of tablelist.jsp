<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpTable"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<form id="frm" onsubmit="return subeditfrm(this.id)" action="<%=path %>/e/op/product/op_updatetable.do" target="hideframe">
	<input type="hidden" name="oid" value="${oid }" />
	<input type="hidden" name="companyId" value="${companyId }" />
	<table class="infotable" cellpadding="0" cellspacing="0">
		<tr>
			<td width="80px">姓名</td>
			<td>
				<div class="f_l">
					<input name="tableNum" type="text" class="text"/><br/>
					<div id="name_error" class="error"></div>
				</div>
				<div id="name_flag" class="flag"></div><div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td width="80px">联系电话</td>
			<td>
				<div class="f_l">
					<input name="tel" type="text" class="text"/><br/>
					<div id="tel_error" class="error"></div>
				</div>
				<div id="tel_flag" class="flag"></div><div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td width="80px">人数</td>
			<td>
				<div class="f_l">
					<input name="personNum" type="text" class="text"/><br/>
					<div id="personNum_error" class="error"></div>
				</div>
				<div id="personNum_flag" class="flag"></div><div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td width="80px">备注</td>
			<td>
				<div class="f_l">
					<hk:textarea clazz="text_area" name="remark"/><br/>
					<div id="remark_error" class="error"></div>
				</div>
				<div id="remark_flag" class="flag"></div><div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<div align="center"><input type="submit" value="提交" class="btn"></div>
			</td>
		</tr>
	</table>
</form>
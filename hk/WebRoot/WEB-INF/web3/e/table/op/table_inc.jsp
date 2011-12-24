<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpTable"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<form id="frm" onsubmit="return subupdatetablefrm(this.id)" action="<%=path %>/e/op/auth/table_updatetable.do" target="hideframe">
	<input type="hidden" name="tableId" value="${tableId }" />
	<input type="hidden" name="companyId" value="${companyId }" />
	<table class="infotable" cellpadding="0" cellspacing="0">
		<tr>
			<td width="100px">台号</td>
			<td>
				<div class="f_l">
					<input name="tableNum" type="text" class="text" value="${o.tableNum }"/><br/>
					<div id="tableNum_error" class="error"></div>
				</div>
				<div id="tableNum_flag" class="flag"></div><div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td>显示顺序号</td>
			<td>
				<div class="f_l">
					<input name="orderflg" type="text" class="text" value="${o.orderflg }"/><br/>
					<div id="orderflg_error" class="error"></div>
				</div>
				<div id="orderflg_flag" class="flag"></div><div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td>分类</td>
			<td>
				<div class="f_l">
					<hk:select oid="id_sortid" name="sortId" checkedvalue="${o.sortId}">
						<c:forEach var="s" items="${sortlist}">
							<hk:option value="${s.sortId}" data="${s.name}"/>
						</c:forEach>
					</hk:select><br/>
					<div id="srot_error" class="error"></div>
				</div>
				<div id="sort_flag" class="flag"></div><div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td>理想就餐人数</td>
			<td>
				<div class="f_l">
					<input name="bestPersonNum" type="text" class="text" value="${o.bestPersonNum }"/><br/>
					<div id="bestPersonNum_error" class="error"></div>
				</div>
				<div id="bestPersonNum_flag" class="flag"></div><div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td>最多就餐人数</td>
			<td>
				<div class="f_l">
					<input name="mostPersonNum" type="text" class="text" value="${o.mostPersonNum }"/><br/>
					<div id="mostPersonNum_error" class="error"></div>
				</div>
				<div id="mostPersonNum_flag" class="flag"></div><div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td>责任人</td>
			<td>
				<div class="f_l">
					<input name="opname" type="text" class="text" value="${o.opname }"/><br/>
					<div id="opname_error" class="error"></div>
				</div>
				<div id="opname_flag" class="flag"></div><div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td>客户网络预订标志</td>
			<td>
				<div class="f_l">
				<hk:radioarea name="netOrderflg" checkedvalue="${o.netOrderflg}">
					<hk:radio value="<%=CmpTable.NETORDERFLG_Y %>" data="view.company.table.netorderflg1" res="true"/><br/>
					<hk:radio value="<%=CmpTable.NETORDERFLG_N %>" data="view.company.table.netorderflg0" res="true"/><br/>
				</hk:radioarea>
					<div id="netOrderflg_error" class="error"></div>
				</div>
				<div id="netOrderflg_flag" class="flag"></div><div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td>描述</td>
			<td>
				<div class="f_l">
					<hk:textarea clazz="text_area" name="intro" value="${o.intro}"/><br/>
					<div id="intro_error" class="error"></div>
				</div>
				<div id="intro_flag" class="flag"></div><div class="clr"></div>
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
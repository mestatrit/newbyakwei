<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<form id="frm" onsubmit="return subeditfrm(this.id)" action="<%=path %>/e/op/product/op_editproductweb.do" target="hideframe">
	<input type="hidden" name="pid" value="${pid }" />
	<input type="hidden" name="companyId" value="${companyId }" />
	<table class="infotable" cellpadding="0" cellspacing="0">
		<tr>
			<td width="80px"><label>名称</label></td>
			<td>
				<div class="f_l">
					<input name="name" type="text" class="text" value="${o.name }"/><br/>
					<div id="cmpproduct_name_error" class="error"></div>
				</div>
				<div id="cmpproduct_name_flag" class="flag"></div><div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td width="80px"><label>编号</label></td>
			<td>
				<div class="f_l">
					<input name="pnum" type="text" class="text" value="${o.pnum }"/><br/>
					<div id="cmpproduct_pnum_error" class="error"></div>
				</div>
				<div id="cmpproduct_pnum_flag" class="flag"></div><div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td width="80px"><label>缩写</label></td>
			<td>
				<div class="f_l">
					<input name="shortName" type="text" class="text" value="${o.shortName }"/><br/>
					<div id="cmpproduct_shortName_error" class="error"></div>
				</div>
				<div id="cmpproduct_shortName_flag" class="flag"></div><div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td width="80px"><label>分类</label></td>
			<td>
				<div class="f_l">
					<hk:select oid="id_sortid" name="sortId" checkedvalue="${o.sortId}">
						<c:forEach var="s" items="${sortlist}">
							<hk:option value="${s.sortId}" data="${s.name}"/>
						</c:forEach>
					</hk:select><br/>
					<div id="cmpproduct_sort_error" class="error"></div>
				</div>
				<div id="cmpproduct_sort_flag" class="flag"></div><div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td width="80px"><label>价格</label></td>
			<td>
				<div class="f_l">
					<input name="money" type="text" class="text" value="${o.money }"/><br/>
					<div id="cmpproduct_money_error" class="error"></div>
				</div>
				<div id="cmpproduct_money_flag" class="flag"></div><div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td width="80px"><label>折扣</label></td>
			<td>
				<div class="f_l">
					<input name="rebate" type="text" class="text" value="${o.rebate }"/><br/>
					<div id="cmpproduct_rebate_error" class="error"></div>
				</div>
				<div id="cmpproduct_rebate_flag" class="flag"></div><div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td width="80px"><label>介绍</label></td>
			<td>
				<div class="f_l">
					<hk:textarea clazz="text_area" name="intro" value="${o.intro}"/><br/>
					<div id="cmpproduct_intro_error" class="error"></div>
				</div>
				<div id="cmpproduct_intro_flag" class="flag"></div><div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<div align="center"><input type="submit" value="保存" class="btn"></div>
			</td>
		</tr>
	</table>
</form>
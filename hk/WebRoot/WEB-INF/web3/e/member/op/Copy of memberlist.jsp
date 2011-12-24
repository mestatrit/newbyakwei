<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.company.productsort.mgr.mgr"/></c:set>
<c:set var="mgr_content" scope="request">
<div>
	<h3>创建会员</h3>
	<hk:form target="hideframe" oid="addmemberfrm" onsubmit="return subaddmemberfrm(this.id)" action="/e/op/auth/member_create.do">
		<hk:hide name="companyId" value="${companyId}"/>
		<table cellpadding="0" cellspacing="0" class="infotable">
			<tr>
				<td width="80px">级别</td>
				<td>
				<hk:select name="gradeId">
					<hk:option value="0" data="无"/>
					<c:forEach var="g" items="${gradelist}">
					<hk:option value="${g.gradeId}" data="${g.name}"/>
					</c:forEach>
				</hk:select>
				</td>
			</tr>
			<tr>
				<td width="80px">姓名</td>
				<td><hk:text name="name" clazz="text"/></td>
			</tr>
			<tr>
				<td width="80px">手机</td>
				<td><hk:text name="mobile" clazz="text"/></td>
			</tr>
			<tr>
				<td width="80px">E-mail</td>
				<td><hk:text name="email" clazz="text"/></td>
			</tr>
			<tr>
				<td></td>
				<td align="center">
				<hk:submit value="提交" clazz="btn"/>
				</td>
			</tr>
		</table>
	</hk:form>
</div>
<div class="bdbtm"></div>
<div>
	<table class="infotable" cellpadding="0" cellspacing="0">
	<c:forEach var="p" items="${list}">
		<tr>
			<td width="100px"><span id="p_${p.sortId }">${p.name }</span> </td>
			<td width="100px">
			<a id="edit_${p.sortId }" href="javascript:toedit(${p.sortId })">修改</a>
			/
			<a id="del_${p.sortId }" href="javascript:todel(${p.sortId })">删除</a>
			</td>
		</tr>
	</c:forEach>
</table>
</div>
<hk:form oid="del_frm" clazz="hide" action="/e/op/product/op_delsortweb.do" target="hideframe">
<hk:hide name="companyId" value="${companyId}"/><input id="del_sortid" name="sortId"/>
</hk:form>
<script type="text/javascript">
</script>
</c:set>
<jsp:include page="../../inc/mgr_inc.jsp"></jsp:include>
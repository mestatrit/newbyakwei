<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<div>
	<form method="post" action="${form_action}">
	<input type="hidden" name="ch" value="1"/>
	<input type="hidden" name="domainid" value="${domainid}"/>
	名称：
	<input type="text" name="name" size="50" value="${o.name}" style="width: 300px" />
	<br />
	描述:
	<br />
	<textarea name="descr" style="width: 500px; height: 200px;">${o.descr}</textarea>
	<br />
	<input type="submit" value="提交" class="btn split-r" />
	<a href="<%=request.getContextPath() %>/yuming/ymmgr/domain_list.do">返回</a>
	</form>
</div>
<script>
function toupdate(domainid) {
	tourl('<%=request.getContextPath() %>/yuming/ymmgr/domain_update.do?domainid=' + domainid);
}
function todel(domainid) {
	if (window.confirm("确实要删除？")) {
		tourl('<%=request.getContextPath() %>/yuming/ymmgr/domain_delete.do?domainid=' + domainid);
	}
}
</script>
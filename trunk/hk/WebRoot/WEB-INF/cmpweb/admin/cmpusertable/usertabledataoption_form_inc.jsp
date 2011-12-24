<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpUserTableField"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="updateoptionfrm" method="post" onsubmit="return subupdateoptionfrm(this.id)" action="<%=path %>/epp/web/op/webadmin/cmpusertable_updateusertabledataoption.do" target="hideframe">
<hk:hide name="ch" value="1"/>
<hk:hide name="companyId" value="${companyId}"/>
<input type="hidden" name="optionId" value="${optionId }"/>
<hk:text name="data" value="${cmpUserTableDataOption.data}" clazz="text" maxlength="20"/>
<hk:submit value="view2.submit" res="true" clazz="btn"/>
<div class="infowarn" id="_updateoptioninfo"></div>
</form>

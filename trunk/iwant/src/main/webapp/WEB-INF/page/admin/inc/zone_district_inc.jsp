<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:actioninvoke mappinguri="/syscnf_districtlist"/>
<c:set var="syscnf_districtlist" scope="request">
<hk:select oid="did_" name="did" checkedvalue="${did}">
<hk:option value="0" data="请选择"/>
<c:forEach var="sys_cnf_dis" items="${syscnf_districtlist}">
<hk:option value="${sys_cnf_dis.did}" data="${sys_cnf_dis.name}"/>
</c:forEach>
</hk:select>
</c:set>
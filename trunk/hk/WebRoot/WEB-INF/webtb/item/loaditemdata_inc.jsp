<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><hk:actioninvoke mappinguri="/tb/item_loaditemdata"/>
<c:if test="${tbItem!=null}">
<div class="mod">
<div align="center">
<img alt="${tbItem.title }" title="${tbItem.title }" src="${tbItem.pic_url }_120x120.jpg"/>
</div>
${tbItem.title }
</div>
</c:if>
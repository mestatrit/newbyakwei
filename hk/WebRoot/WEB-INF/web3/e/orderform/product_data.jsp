<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
[
<hk:rmstr value=",">
<c:if test="${st==0}">
<c:forEach var="p" items="${list}">
    {oid:${p.productId },text:'${p.name }', content:'点击添加'},
</c:forEach>
</c:if>
<c:if test="${st==1}">
<c:forEach var="p" items="${list}">
    {oid:${p.productId },text:'${p.pnum } [${p.name }]', content:'点击添加'},
</c:forEach>
</c:if>
<c:if test="${st==2}">
<c:forEach var="p" items="${list}">
    {oid:${p.productId },text:'${p.shortName } [${p.name }]', content:'点击添加'},
</c:forEach>
</c:if>
</hk:rmstr>
]
<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
photo=new Array();
map=new Array();
<c:forEach var="p" items="${list}" varStatus="idx">
photo[${idx.index }]=new Array(${p.photoId },'${p.name }','${p.pic240 }','${p.pic800 }','${p.path }');
map[${idx.index }]=${p.photoId };
</c:forEach>

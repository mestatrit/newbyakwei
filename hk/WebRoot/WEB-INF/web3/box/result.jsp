<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:if test="${award}">
<span id="result">${prize.tip }</span>
</c:if>
<c:if test="${!award}">
<span id="result"><hk:data key="func.box.open.noprize" arg0="${box.name}"/></span>
</c:if>
<script type="text/javascript">
var info=document.getElementById('result').innerHTML;
parent.getResult(${award},info);
</script>
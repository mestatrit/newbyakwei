<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<div class="foot">
<a href="/m"><hk:data key="epp.returnhome"/></a>|<a href="#"><hk:data key="epp.gototop"/></a>
</div>
<div class="foot2">
<c:if test="${loginUser!=null}"><hk:a href="/epp/logout.do?companyId=${companyId}"><hk:data key="epp.logout"/></hk:a> |</c:if>
<c:if test="${loginUser==null}"><hk:a href="/epp/login.do?companyId=${companyId}"><hk:data key="epp.login"/></hk:a> |</c:if>
<a href="/index"><hk:data key="epp.topc"/></a>
</div>
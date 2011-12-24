<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="hang">
<a href="/index">切换到电脑标准版</a><br/>
<c:if test="${loginUser!=null}"><hk:a href="/epp/logout.do?companyId=${companyId}">退出</hk:a>|</c:if>本站为<a href="http://www.huoku.com" target="_blank">火酷网</a>会员网站，支持统一登录</div>
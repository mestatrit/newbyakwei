<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<div class="foot">
<c:if test="${cmpUnion_loginUser!=null}"><hk:a href="/union/home.do?uid=${uid }&userId=${cmpUnion_loginUser.userId}">我的主页</hk:a>|<hk:a href="/union/op/msg/pvtlist.do?uid=${uid}">私信</hk:a>|<hk:a href="/union/op/userset.do?uid=${uid}">设置</hk:a>|</c:if><hk:a href="/union/s.do?uid=${uid}">搜索</hk:a>|<a href="#">回顶部</a>
</div>
<div class="foot2">
<c:if test="${sys_pcbrowse}"><hk:a href="/union/union_setmobile.do?uid=${uid}">手机版</hk:a></c:if>
<c:if test="${!sys_pcbrowse}"><hk:a href="/union/union_setpc.do?uid=${uid}">电脑版</hk:a></c:if>
</div>
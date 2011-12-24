<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${o.name} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="inc/top.jsp"></jsp:include>
	<div class="hang reply">
		${o.name }<br/>
		<c:if test="${not empty o.tel}">
			${o.tel }<br/>
		</c:if>
		<c:if test="${not empty o.addr}">
			${o.addr }<br/>
		</c:if>
	</div>
	<div class="odd">
	<img src="http://ditu.google.cn/staticmap?center=${pointX },${pointY }&zoom=${zoom }&size=300x300&markers=${markerX },${markerY }&format=jpg&maptype=mobile&sensor=false"/>
	</div>
	<div class="hang">
		<hk:a href="/epp/info_map.do?companyId=${companyId}&pointX=${pointX }&pointY=${pointY-0.012 }&zoom=${zoom }"><hk:data key="view.mvleft"/></hk:a>
		<hk:a href="/epp/info_map.do?companyId=${companyId}&pointX=${pointX }&pointY=${pointY+0.012 }&zoom=${zoom }"><hk:data key="view.mvright"/></hk:a>
		<hk:a href="/epp/info_map.do?companyId=${companyId}&pointX=${pointX-0.012 }&pointY=${pointY }&zoom=${zoom }"><hk:data key="view.mvdown"/></hk:a>
		<hk:a href="/epp/info_map.do?companyId=${companyId}&pointX=${pointX+0.012 }&pointY=${pointY }&zoom=${zoom }"><hk:data key="view.mvup"/></hk:a>
		<hk:a href="/epp/info_map.do?companyId=${companyId}&pointX=${pointX }&pointY=${pointY }&zoom=${zoom+1 }"><hk:data key="view.blowup"/></hk:a>
		<hk:a href="/epp/info_map.do?companyId=${companyId}&pointX=${pointX }&pointY=${pointY }&zoom=${zoom-1 }"><hk:data key="view.reduce"/></hk:a>
		<hk:a href="/epp/info_map.do?companyId=${companyId}"><hk:data key="view.reset"/></hk:a>
	</div>
	<div class="hang even"><hk:a href="/epp/index_wap.do?companyId=${companyId }"><hk:data key="view.returhome"/></hk:a></div>
	<jsp:include page="inc/foot.jsp"></jsp:include>
</hk:wap>
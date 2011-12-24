<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang odd">
	此链接是pc网址，建议<a href="http://www.google.com/gwt/n?u=${enc_url}">转为手机网址打开</a><br/>
	<a href="${shortUrl.url}">直接打开网址</a>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>
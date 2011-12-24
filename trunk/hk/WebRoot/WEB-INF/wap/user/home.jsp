<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="${user.nickName }的首页" rm="false" bodyId="thepage">
<jsp:include page="../inc/top.jsp"></jsp:include>
<div>
<table class="list"><tbody>
<tr class="hang odd"><td>
	<table>
		<tbody>
			<tr>
				<td><img src="${user.head }"/></td>
				<td>
					${user.nickName }<br/>
					<c:if test="${not empty user.intro}">
						${user.intro }<br/>
					</c:if>
				</td>
			</tr>
		</tbody>
	</table>
</td></tr>
</tbody></table>
</div>
<div class="hang even"><hk:a href="/epp/index.do?companyId=${companyId }"><hk:data key="view.returhome"/></hk:a></div>
<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>
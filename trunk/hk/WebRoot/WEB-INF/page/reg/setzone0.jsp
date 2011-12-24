<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">输入所在地区号</div>
	<div class="hang odd">
		<hk:form method="get" action="/next/op/op_tosetzone2.do">
			<hk:text name="code" maxlength="10"/><br/>
			<hk:submit value="提交"/>
		</hk:form>
	</div>
	<div class="hang even">如果不知道区号,可以从下面地区中选择</div>
	<div class="hang odd">
		<hk:form method="get" action="/next/op/op_tosetzone0.do">
			<hk:select name="countryId" checkedvalue="${countryId}">
				<c:forEach var="c" items="${clist}">
					<hk:option value="${c.countryId}" data="${c.country}"/>
				</c:forEach>
			</hk:select>
			<hk:submit value="提交"/>
		</hk:form>
	</div>
	<div class="hang odd">
		<c:forEach var="p" items="${plist}">
			<hk:a href="/next/op/op_tosetzone1.do?countryId=${countryId}&provinceId=${p.provinceId }">${p.province}</hk:a> 
		</c:forEach>
	</div>
	<div class="hang">
		<hk:form action="/next/op/op_toAddFollow.do">
			<hk:submit value="跳过"/>
		</hk:form>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>
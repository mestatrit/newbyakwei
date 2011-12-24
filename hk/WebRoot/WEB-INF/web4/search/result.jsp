<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.bean.CmpTip"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request"><hk:data key="view2.website.title"/></c:set>
<c:set var="html_body_content" scope="request">
<div>
<c:if test="${ch==0}">
<form method="get" action="/search">
	<hk:hide name="ch" value="1"/>
	<strong><hk:data key="view2.search.tip"/>:</strong>
	<input type="text" class="text" name="key"/>
	<hk:submit value="view2.search" res="true" clazz="btn"/>
</form>
</c:if>
<c:if test="${ch==1}">
	<c:if test="${fn:length(cmplist)>0}">
		<div><h1 class="title3">${city.city}</h1></div>
		<c:forEach var="c" items="${cmplist}">
			<div class="list-1" onmouseover="this.className='list-1 bg2';" onmouseout="this.className='list-1';">
				<a href="/venue/${c.companyId }/" class="b">${c.name }</a><br/>
				${c.addr }
			</div>
		</c:forEach>
		<c:if test="${cmp_more}">
			<div><a href="<%=path %>/s_cmpname.do?key=${enc_key}"><hk:data key="view2.more"/></a></div>
		</c:if>
	</c:if>
	<c:if test="${fn:length(othercmplist)>0}">
		<div>
			<h1 class="title3"><hk:data key="view2.searchvenue_on_other_city"/></h1>
		</div>
		<c:forEach var="c" items="${othercmplist}">
			<div class="list-1" onmouseover="this.className='list-1 bg2';" onmouseout="this.className='list-1';">
				<a href="/venue/${c.companyId }/" class="b">${c.name }</a><br/>
				${c.addr }
			</div>
		</c:forEach>
		<c:if test="${cmp_more}">
			<div><a href="<%=path %>/s_othercmpname.do?key=${enc_key}"><hk:data key="view2.more"/></a></div>
		</c:if>
	</c:if>
	<c:if test="${fn:length(refcmplist)>0}">
		<div><h1 class="title3"><hk:data key="view2.tagvenuelist_incity" arg0="${city.city}" arg1="${key}"/></h1></div>
		<c:forEach var="ref" items="${refcmplist}">
			<div class="list-1" onmouseover="this.className='list-1 bg2';" onmouseout="this.className='list-1';">
				<a href="/venue/${ref.company.companyId }/" class="b">${ref.company.name }</a><br/>
				${ref.company.addr }
			</div>
		</c:forEach>
		<c:if test="${refcmp_more}">
			<div><a href="<%=path %>/s_reftagname.do?key=${enc_key}&page=2"><hk:data key="view2.more"/></a></div>
		</c:if>
	</c:if>
	<c:if test="${fn:length(otherrefcmplist)>0}">
		<div>
			<h1 class="title3"><hk:data key="view2.searchvenue_on_other_city"/>标签</h1>
		</div>
		<c:forEach var="c" items="${otherrefcmplist}">
			<div class="list-1" onmouseover="this.className='list-1 bg2';" onmouseout="this.className='list-1';">
				<a href="/venue/${c.companyId }/" class="b">${c.name }</a><br/>
				${c.addr }
			</div>
		</c:forEach>
		<c:if test="${cmp_more}">
			<div><a href="<%=path %>/s_otherreftagname.do?key=${enc_key}"><hk:data key="view2.more"/></a></div>
		</c:if>
	</c:if>
	<c:if test="${fn:length(cmplist)==0 && fn:length(othercmplist)==0 && fn:length(refcmplist)==0 && fn:length(otherrefcmplist)==0}">
		<strong><hk:data key="view2.nosearchdata"/></strong><br/>
		<a class="more2" href="/venue/create?name=${enc_key }"><hk:data key="view2.create_new_venue"/>${key }</a>
	</c:if>
	<c:if test="${fn:length(userlist)>0}">
		<div><h1 class="title3"><hk:data key="view2.user_search_result"/></h1></div>
		<ul class="smallhead48">
		<c:forEach var="u" items="${userlist}">
		<li><a href="/user/${u.userId }/"><img title="${u.nickName }" src="${u.head48Pic }" alt="${u.nickName }" /></a></li>
		</c:forEach>
		</ul>
		<div class="clr"></div>
		<c:if test="${user_more}">
			<div><a href="<%=path %>/s_nickname.do?key=${enc_key}&page=2"><hk:data key="view2.more"/></a></div>
		</c:if>
	</c:if>
</c:if>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>
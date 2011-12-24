<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${o.name} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">
		<c:if test="${not empty o.headPath}">
			<img src="${o.head48Pic }" alt="${o.name}"/><br/>
		</c:if>
	</div>
	<div class="hang even"><hk:data key="company.name"/></div>
	<div class="hang odd">${o.name}</div>
	<div class="hang"><hk:data key="company.tel"/></div>
	<div class="hang odd">${o.tel}</div>
	<div class="hang"><hk:data key="company.opname"/></div>
	<div class="hang odd">${o.opname}</div>
	<div class="hang"><hk:data key="company.optel"/></div>
	<div class="hang odd">${o.optel}</div>
	<div class="hang"><hk:data key="company.kindId"/></div>
	<div class="hang odd">${o.kindData}</div>
	<div class="hang"><hk:data key="company.addr"/></div>
	<div class="hang odd">${o.addr}</div>
	<div class="hang"><hk:data key="company.traffic"/></div>
	<div class="hang odd">${o.traffic}</div>
	<div class="hang"><hk:data key="company.sign"/></div>
	<div class="hang odd">${o.sign}</div>
	<div class="hang"><hk:data key="company.intro"/></div>
	<div class="hang odd">${o.intro}</div>
	<hk:form method="get" action="/e/op/op_toeditcompany.do">
		<hk:hide name="cid" value="${cid}"/>
		<hk:submit value="e.op.viewcompany.update" res="true"/>
	</hk:form>
	<c:if test="${fn:length(tlist)>0}">
		<div class="hang"><hk:data key="e.op.viewcompany.tag"/></div>
		<div class="hang odd">
			<c:forEach var="t" items="${tlist}">
				${t.name }<hk:a clazz="s" href="/e/op/op_deltagforcompany.do?cid=${cid}&tid=${t.tagId }">x</hk:a> 
			</c:forEach> 
			<hk:a href="/e/op/op_toaddtagforcompany.do?cid=${cid}"><hk:data key="e.op.viewcompany.addtag"/></hk:a>
		</div>
	</c:if>
	<c:if test="${cadmin}">
		<div class="hang"><hk:a href="/e/op/op_employee.do?cid=${cid}"><hk:data key="e.op.viewcompany.manageremployee"/></hk:a></div>
	</c:if>
	<div class="hang"><hk:a href="/e/op/op_clist.do"><hk:data key="view.return"/></hk:a></div>
	<c:if test="${adminUser!=null}">
	<div class="hang"><hk:a href="/e/admin/admin_clist.do"><hk:data key="view.returnadmin"/></hk:a></div>
	</c:if>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>
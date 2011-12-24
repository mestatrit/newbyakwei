<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CompanyUserStatus"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${vo.company.name}" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang reply">
		${vo.company.name }<c:if test="${vo.company.stop}">(<hk:data key="company.stopflg_1"/>)</c:if><br/>
		<c:if test="${not empty vo.company.tel}">
			${vo.company.tel }<br/>
		</c:if>
		<c:if test="${not empty vo.company.addr}">
			${vo.company.addr }
			<c:if test="${vo.company.markerX!=0}">
				<hk:a href="/e/cmp_map.do?companyId=${companyId}"><hk:data key="view.map"/></hk:a>
			</c:if><br/>
		</c:if>
		<c:if test="${not empty vo.company.traffic}">${vo.company.traffic }<br/></c:if>
		<div>
			<hk:form action="/op/cmp_checkin.do">
				<hk:hide name="companyId" value="${companyId}"/>
				<hk:submit value="view2.checkin" res="true"/>
			</hk:form>
		</div>
	</div>
	<c:if test="${not empty vo.company.headPath}">
		<div class="reply"><img src="${vo.company.head240}"/><br/></div>
	</c:if>
	<div class="hang">
		<hk:rmBlankLines rm="true">
			<c:if test="${hasphoto}">
				<hk:a href="/e/photo.do?companyId=${companyId}"><hk:data key="view.morephoto"/></hk:a>
			</c:if>
			<c:if test="${caneiteimg}">
				<c:if test="${hasphoto}">|</c:if><hk:a href="/e/op/photo/photo_toadd.do?companyId=${companyId}"><hk:data key="view.company.continueuploadimage"/></hk:a>
			</c:if>
			<c:if test="${caneditcompany || sys_isadmin_user }">
				|<hk:a href="/e/op/op_toedit.do?companyId=${companyId}"><hk:data key="view.company.manage"/></hk:a>
			</c:if>
			<c:if test="${userLogin}">
				| <hk:a href="/op/award_selcmpequ.do?companyId=${companyId}">使用道具</hk:a>
			</c:if>
		</hk:rmBlankLines>
	</div>
	<div class="hang">
		<hk:form action="/cmpuserstatus/op/op.do">
			<hk:hide name="companyId" value="${companyId}"/>
			<c:set var="companyuserstatus_want"><%=CompanyUserStatus.USERSTATUS_WANT %></c:set>
			<c:set var="companyuserstatus_done"><%=CompanyUserStatus.USERSTATUS_DONE %></c:set>
			<c:if test="${companyUserStatus!=null}">
				<c:if test="${companyUserStatus.done}">
					√<hk:data key="view2.didthis"/><hk:a href="/cmpuserstatus/op/op_del.do?companyId=${companyId}&status=${companyuserstatus_done }">x</hk:a>
				</c:if>
				<c:if test="${!companyUserStatus.done}">
					<hk:submit name="done" value="view2.didthis" res="true"/> 
				</c:if>
				<c:if test="${companyUserStatus.want}">
					√<hk:data key="view2.wantto"/><hk:a href="/cmpuserstatus/op/op_del.do?companyId=${companyId}&status=${companyuserstatus_want }">x</hk:a>
				</c:if>
				<c:if test="${!companyUserStatus.want}">
					<hk:submit name="want" value="view2.wantto" res="true"/>
				</c:if>
			</c:if>
			<c:if test="${companyUserStatus==null}">
				<hk:submit name="done" value="view2.didthis" res="true"/> 
				<hk:submit name="want" value="view2.wantto" res="true"/>
			</c:if>
		</hk:form>
	</div>
	<c:if test="${fn:length(productvolist)>0}">
		<div class="hang odd">产品</div>
		<jsp:include page="../inc/productvo_inc.jsp"></jsp:include>
		<c:if test="${hasmoreproduct}"><div class="hang"><hk:a href="/product_listwap.do?companyId=${companyId}">更多</hk:a></div></c:if>
	</c:if>
	<c:if test="${cmptipvolist!=null || mycmptipvolist!=null}">
		<div class="hang even"><hk:data key="view2.people.tips"/></div>
		<jsp:include page="../inc/cmptiplist_inc.jsp"></jsp:include>
		<c:if test="${more_tip}">
			<div class="hang">
			<hk:a href="/e/cmp_tips.do?companyId=${companyId}&doneflg=${dongflg }&page=2"><hk:data key="view2.more"/></hk:a>
			</div>
		</c:if>
	</c:if>
	<c:set var="addlabastr" value="companyId=${companyId}" scope="request"/>
	<c:if test="${fn:length(labavolist)>0}">
		<div class="hang even"><hk:data key="view.companyreflaba"/></div>
		<jsp:include page="../inc/labavo2.jsp"></jsp:include>
		<c:if test="${morereflaba}">
			<div class="hang">
			<hk:a href="/e/cmp_reflaba.do?companyId=${companyId}"><hk:data key="view.more"/></hk:a>
			</div>
		</c:if>
	</c:if>
	<div class="hang even"><hk:data key="view.companylaba" arg0="${vo.company.name }"/></div>
	<c:if test="${fn:length(cmtvolist)>0}">
		<c:set var="addcmtstr" value="companyId=${companyId}" scope="request"/>
		<jsp:include page="../inc/cmpcommentvo.jsp"></jsp:include>
		<c:if test="${morecmt}">
			<div class="hang">
			<hk:a href="/e/cmp_cmt.do?companyId=${companyId}"><hk:data key="view.more"/></hk:a>
			</div>
		</c:if>
	</c:if>
	<div class="hang">
		<hk:form action="/cmt/op/op_addcmt.do">
			<hk:hide name="companyId" value="${companyId}"/>
			<hk:hide name="return_url" value="/e/cmp.do?companyId=${companyId}"/>
			<input type="hidden" name="lastUrl" value="/e/cmp.do?companyId=${companyId}"/>
			<hk:text name="content" clazz="ipt" maxlength="140"/>
			<hk:submit value="view.companylaba.submit.value" res="true"/> 
		</hk:form>
	</div>
	<c:if test="${!authed && vo.company.checkSuccess}">
		<c:set var="url" value="/e/op/op_toApplyAuth.do?companyId=${companyId}"/>
		<div class="hang"><hk:data key="view.authcompany.tip" arg0="${url}"/></div>
	</c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>
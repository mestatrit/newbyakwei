<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${vo.company.name} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<c:if test="${not empty return_url}">
			<hk:a href="${return_url}" decode="true"><hk:data key="view.return"/></hk:a> | 
		</c:if>
		<c:if test="${empty return_url}">
			<hk:a href="/e/admin/admin_clist.do"><hk:data key="view.return"/></hk:a> | 
		</c:if>
		<hk:a href="/e/admin/admin_checkok.do?companyId=${companyId}" needreturnurl="true">通过</hk:a> |
		<hk:a href="/e/admin/admin_delcmp.do?companyId=${companyId}" needreturnurl="true">删除</hk:a> |
		<c:if test="${vo.company.freeze}">
			<hk:a href="/e/admin/admin_chgunfreeze.do?companyId=${vo.company.companyId}" needreturnurl="true"><hk:data key="func.company.setunfreeze"/></hk:a>|
		</c:if>
		<c:if test="${!vo.company.freeze}">
			<hk:a href="/e/admin/admin_chgfreeze.do?companyId=${vo.company.companyId}" needreturnurl="true"><hk:data key="func.company.setfreeze"/></hk:a>|
		</c:if>
		<hk:a href="/e/admin/admin_toeditstopflg.do?companyId=${vo.company.companyId}" needreturnurl="true"><hk:data key="view.company.setstopflg"/></hk:a>
		| <hk:a href="/e/admin/admin_chgcmpuser.do?companyId=${companyId}">转移企业</hk:a>
		| <hk:a href="/e/admin/admin_chgcmpflg.do?companyId=${companyId}">修改企业类型</hk:a>
		| <hk:a href="/e/admin/admin_chgcmptml.do?companyId=${companyId}">修改网站模板</hk:a>
	</div>
	<div class="hang reply">
		<c:if test="${vo.company.stop}"><hk:data key="company.stopflg_1"/><br/></c:if>
		现在状态：<hk:data key="view.companystatus_${vo.company.companyStatus}"/><br/>
		<hk:a href="/e/cmp.do?companyId=${companyId}">${vo.company.name }</hk:a><br/>
		企业类型：<hk:data key="view2.company.cmpflg${vo.company.cmpflg}"/><br/>
		<c:if test="${vo.company.cmpFlgE_COMMERCE}">
			产品属性：
			<c:if test="${vo.company.openProductattrflg}">
				已开启
				<hk:a href="/e/admin/admin_setproductattrflg.do?companyId=${companyId }&productattrflg=0">关闭</hk:a><br/>
			</c:if>
			<c:if test="${!vo.company.openProductattrflg}">
				已关闭
				<hk:a href="/e/admin/admin_setproductattrflg.do?companyId=${companyId }&productattrflg=1">开启</hk:a><br/>
			</c:if><br/>
		</c:if>
		<c:if test="${vo.company.cmpFlgEnterprise}">
			文件系统：
			<c:if test="${cmpSvrCnf.openFile}">
				已开启
				<hk:a href="/e/admin/admin_setsvrfileflg.do?companyId=${companyId }&fileflg=0">关闭</hk:a><br/>
			</c:if>
			<c:if test="${!cmpSvrCnf.openFile}">
				已关闭
				<hk:a href="/e/admin/admin_setsvrfileflg.do?companyId=${companyId }&fileflg=1">开启</hk:a><br/>
			</c:if>
			视频系统：
			<c:if test="${cmpSvrCnf.openVideo}">
				已开启
				<hk:a href="/e/admin/admin_setsvrvideoflg.do?companyId=${companyId }&videoflg=0">关闭</hk:a><br/>
			</c:if>
			<c:if test="${!cmpSvrCnf.openVideo}">
				已关闭
				<hk:a href="/e/admin/admin_setsvrvideoflg.do?companyId=${companyId }&videoflg=1">开启</hk:a><br/>
			</c:if>
		</c:if>
		<hk:form action="/e/admin/admin_setfilesize.do">
			<hk:hide name="companyId" value="${companyId}"/>
			企业文件空间:<hk:text name="size" value="${cmpOtherWebInfo.totalFileSize/1024}"/>M
			<hk:submit value="提交"/>
		</hk:form>
		<c:if test="${user!=null}">
		所有人：${user.nickName }<br/>
		</c:if>
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
		<c:if test="${not empty vo.company.intro}">
			${vo.company.intro }<br/>
		</c:if>
	</div>
	<c:if test="${not empty vo.company.headPath}">
		<div class="reply"><img src="${vo.company.head240}"/><br/></div>
	</c:if>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>
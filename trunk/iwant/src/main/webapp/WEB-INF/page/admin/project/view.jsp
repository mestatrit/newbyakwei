<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request"><hk:value value="${project.name}"/></c:set>
<c:set scope="request" var="mgr_body_content">
<div class="mod">
	<div class="mod_title">
		查看项目 - <hk:value value="${project.name}"/>
	</div>
	<div class="mod_content">
		<div>
			<table class="formt">
				<tr>
					<td width="90" align="right">名称</td>
					<td>
						<hk:value value="${project.name }" onerow="true"/>
					</td>
				</tr>
				<tr>
					<td width="90" align="right">均价</td>
					<td>
						<hk:value value="${project.avrprice }" onerow="true"/>
					</td>
				</tr>
				<tr>
					<td width="90" align="right">电话</td>
					<td>
						<hk:value value="${project.tel }" onerow="true"/>
					</td>
				</tr>
				<tr>
					<td width="90" align="right">地址</td>
					<td>
						<hk:value value="${project.addr }" onerow="true"/>
					</td>
				</tr>
				<tr>
					<td width="90" align="right">描述</td>
					<td>
						<hk:value value="${project.descr}"/>
					</td>
				</tr>
				<tr>
					<td width="90" align="right">容积率</td>
					<td>
						<hk:value value="${project.rongjilv}"/>
					</td>
				</tr>
				<tr>
					<td width="90" align="right">绿化率</td>
					<td>
						<hk:value value="${project.lvhualv}"/>
					</td>
				</tr>
				<tr>
					<td width="90" align="right">物业费</td>
					<td>
						<hk:value value="${project.mrate}"/>
					</td>
				</tr>
				<tr>
					<td width="90" align="right">车位</td>
					<td>
						<hk:value value="${project.carspace}"/>
					</td>
				</tr>
				<tr>
					<td width="90" align="right">建筑类表</td>
					<td>
						<hk:value value="${project.buildtype}"/>
					</td>
				</tr>
				<tr>
					<td width="90" align="right">物业类型</td>
					<td>
						<hk:value value="${project.mtype}"/>
					</td>
				</tr>
				<tr>
					<td width="90" align="right">交通</td>
					<td>
						<hk:value value="${project.traffic}"/>
					</td>
				</tr>
				<tr>
					<td width="90" align="right">周边设施</td>
					<td>
						<hk:value value="${project.neardescr}"/>
					</td>
				</tr>
				<tr>
					<td width="90" align="right"></td>
					<td>
						<a href="javascript:viewslidelist()" class="split-r">图片管理</a>
						<a href="javascript:toupdate()" class="split-r">修改</a>
						<a href="javascript:toupdatelatlng()" class="split-r">地图设置</a>
						<c:set var="lastUrl" value="${backUrl.lastUrl }"/>
						<c:if test="${not empty lastUrl }">
							<a href="${lastUrl }">返回</a>
						</c:if>
						<c:if test="${empty lastUrl }">
							<a href="${appctx_path }/mgr/project.do">返回</a>
						</c:if>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
<script type="text/javascript">
function toupdate(){
	tourl('${appctx_path}/mgr/project_update.do?projectid=${project.projectid}');
}
function toupdatelatlng(){
	tourl('${appctx_path}/mgr/project_updatelatlng.do?projectid=${project.projectid}');
}
function viewslidelist(){
	tourl('${appctx_path}/mgr/slide.do?projectid=${project.projectid}');
}
</script>
</c:set><jsp:include page="../inc/mgrframe.jsp"></jsp:include>
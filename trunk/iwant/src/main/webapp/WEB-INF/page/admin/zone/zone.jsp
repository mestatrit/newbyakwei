<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="page_title" scope="request">
切换城市
</c:set>
<c:set var="html_head_title" scope="request">
${page_title }
</c:set>
<c:set scope="request" var="mgr_body_content">
	<div class="mod">
		<div class="mod_title">
			${page_title }
		</div>
		<div class="mod_content">
			<div>
				<form id="frm" method="post" action="${appctx_path }/mgr/zone_changecity.do">
					<table class="formt">
						<tr>
							<td width="90" align="right">
							选择城市
							</td>
							<td>
								<div id="area"></div>
							</td>
						</tr>
						<tr>
							<td width="90" align="right"></td>
							<td>
								<input type="submit" value="提交" class="btn split-r" />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
<jsp:include page="../inc/zone_inc.jsp"></jsp:include>
<script type="text/javascript">
var zone=new Zone('area','_pid','_cid');
zone.init();
</script>
</c:set><jsp:include page="../inc/mgrframe.jsp"></jsp:include>
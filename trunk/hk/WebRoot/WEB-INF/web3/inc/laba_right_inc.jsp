<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@page import="com.hk.web.util.Hkcss2Util"%>
<%String path=request.getContextPath();
JspDataUtil.loadLabaRightIncData(request);%>
<div class="f_r">
	<div class="mod">
		<div class="mod-3 r_mod3">
			<%=Hkcss2Util.rd_bg%>
			<div class="cont">
				<div>
					<table>
						<c:if test="${user!=null}">
						<tr>
							<td>
								<div class="f_l">
									<img src="${user.head48Pic }"/>
								</div>
								<div class="f_l">
									${user.nickName }<br/>
									<c:if test="${user.cityId>0}">
									${user.location }
									</c:if>
								</div>
							</td>
						</tr>
						</c:if>
						<tr>
							<td>
								<ul class="smalluserinfo">
									<li>
									<a href="<%=path %>/friend.do?userId=${userId}"><strong>${user.friendCount }</strong><br/>
										关注</a>
									</li>
									<li><a href="<%=path %>/followed.do?userId=${userId}"><strong>${user.fansCount }</strong><br/>
										粉丝</a>
									</li>
									<li class="end"><a href="<%=path %>/laba_userlaba.do?userId=${userId}"><strong>${labaCount }</strong><br/>
										喇叭</a>
									</li>
								</ul>
							</td>
						</tr>
					</table>
				</div>
				<div class="search_3">
					<hk:form oid="s_frm" method="get" action="/laba_s.do">
						<input id="s_s_id" name="sw" type="text" class="text_s_3 f_l" onfocus="sets_3_value(this)" onblur="sets_3_empty_value(this)"/>
						<a class="btn_s_3" onclick="subsearch()">搜索</a>
						<div class="clr"></div>
					</hk:form>
					<script type="text/javascript">
					function sets_3_value(o){
						if(o.value=='搜索喇叭内容...'){
							o.value='';
						}
					}
					function sets_3_empty_value(o){
						if(o.value.length==0){
							o.value="搜索喇叭内容...";
						}
					}
					function subsearch(){
						getObj('s_frm').submit();
					}
					sets_3_empty_value(getObj('s_s_id'));
					</script>
				</div>
			</div>
			<%=Hkcss2Util.rd_bg_bottom%>
		</div>
		<div class="clr"></div>
	</div>
</div>
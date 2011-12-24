<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.EppViewUtil"%><%@page import="java.util.Calendar"%><%@page import="com.hk.bean.CmpInfo"%>
<%@page import="com.hk.frame.util.MessageUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><%String path = request.getContextPath();%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>${title_value} ${o.name }</title>
		<link rel="stylesheet" type="text/css" href="<%=path%>/cmpwebst4/mod/0/1/css/a.css?v=1" />
		<script type="text/javascript" language="javascript" src="<%=path%>/cmpwebst4/mod/pub/js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" language="javascript" src="<%=path%>/cmpwebst4/mod/pub/js/pub.js"></script>
		<script type="text/javascript" language="javascript" src="<%=path%>/cmpwebst4/mod/pub/js/png2.js"></script>
		<script type="text/javascript">var path="<%=path %>";</script>
		<c:if test="${epp_other_value!=null}">${epp_other_value}</c:if>
		<c:if test="${home_cmpNav==null && parent_cmpNav!=null}">
			<c:set var="cmpNavPageCssObj" value="${parent_cmpNav.cmpNavPageCssObj}"></c:set>
		</c:if>
		<c:if test="${home_cmpNav!=null}">
			<c:set var="cmpNavPageCssObj" value="${home_cmpNav.cmpNavPageCssObj}"></c:set>
		</c:if>
		<style type="text/css">
			<c:if test="${home_cmpNav!=null && !home_cmpNav.bgNone}">
				<c:if test="${home_cmpNav.bgDefault }">
					<c:if test="${not empty cmpInfo.bgPicPath}">
						#bg{
							background-image:url(${cmpInfo.bgPicUrl });
						}
					</c:if>
				</c:if>
				<c:if test="${home_cmpNav.bgSet }">
					<c:if test="${not empty home_cmpNav.bgPicPath}">
						#bg{
							background-image:url(${home_cmpNav.bgPicUrl });
						}
					</c:if>
				</c:if>
			</c:if>
			<c:if test="${home_cmpNav==null && parent_cmpNav!=null && !parent_cmpNav.bgNone}">
				<c:if test="${parent_cmpNav.bgDefault }">
					<c:if test="${not empty cmpInfo.bgPicPath}">
						#bg{
							background-image:url(${cmpInfo.bgPicUrl });
						}
					</c:if>
				</c:if>
				<c:if test="${parent_cmpNav.bgSet }">
					<c:if test="${not empty parent_cmpNav.bgPicPath}">
						#bg{
							background-image:url(${parent_cmpNav.bgPicUrl });
						}
					</c:if>
				</c:if>
			</c:if>
		</style>
		<c:if test="${cmpNavPageCssObj!=null}">
			<style type="text/css">
				<c:if test="${not empty cmpNavPageCssObj.bgColor}">
				body{background-color:#${cmpNavPageCssObj.bgColor }; }
				</c:if>
				<c:if test="${not empty cmpNavPageCssObj.navLinkColor}">
				ul.nav li.f {
				    border-bottom: 1px solid #${cmpNavPageCssObj.navLinkColor };
				}
				ul.nav li a {
				    color: #${cmpNavPageCssObj.navLinkColor };
				}
				ul.nav li.f {
					border-bottom: 1px solid #${cmpNavPageCssObj.navLinkColor };
				}
				.nav_txt .nav_intro {
					color: #${cmpNavPageCssObj.navLinkColor };
				}
				</c:if>
				<c:if test="${not empty cmpNavPageCssObj.linkColor}">
				.main a {
				    color: #${cmpNavPageCssObj.linkColor };
				}
				</c:if>
			</style>
		</c:if>
	</head>
	<body><iframe id="hideframe" name="hideframe" class="hide"></iframe>
	<div id="bg"></div>
		<div class="hk">
			<div class="top">
				<div class="logo">
					<c:if test="${not empty o.logopath}">
					<a href="http://<%=request.getServerName() %>"><img src="${o.logoPic }" onload="fixPNG(this)"/></a>
					</c:if>
				</div>
				<div class="navlist"><%EppViewUtil.loadAllCmpNav(request,true); %>
					<c:forEach var="vo" items="${cmpnavvolist}">
						<ul class="nav">
							<li class="f">${vo.cmpNav.name }</li>
							<c:forEach var="child" items="${vo.children}">
								<c:if test="${child.urlLink}">
									<c:if test="${not empty child.url}">
										<li><a href="/column/${companyId }/${child.oid}">${child.name }</a></li>
									</c:if>
									<c:if test="${empty child.url}">
										<li>${child.name }</li>
									</c:if>
								</c:if>
								<c:if test="${!child.urlLink}">
									<c:if test="${child.loginAndRefFunc}">
										<c:if test="${loginUser!=null}">
											<c:if test="${sys_cmpadminuser}">
												<li><a href="<%=path %>/epp/web/op/webadmin/admincmpnav.do?companyId=${companyId}">站点管理</a></li>
											</c:if>
											<c:if test="${!sys_cmpadminuser}">
												<li><a href="<%=path %>/epp/logout_web.do?companyId=${companyId}">退出</a></li>
											</c:if>
										</c:if>
										<c:if test="${loginUser==null}">
											<li><a href="/column/${companyId }/${child.oid}">${child.name }</a></li>
										</c:if>
									</c:if>
									<c:if test="${!child.loginAndRefFunc}">
										<li><a href="/column/${companyId }/${child.oid}">${child.name }</a></li>
									</c:if>
								</c:if>
							</c:forEach>
						</ul>
					</c:forEach>
					<div class="clr"></div>
				</div>
			</div>
			<c:if test="${home_cmpNav==null}">
				<c:if test="${not empty cmpNav.title || not empty cmpNav.intro}">
					<div class="nav_txt">
						<h1>${cmpNav.title }</h1>
						<div class="nav_intro">${cmpNav.intro }</div>
					</div>
				</c:if>
			</c:if>
			<c:if test="${home_cmpNav!=null}">
				<c:if test="${not empty home_cmpNav.title || not empty home_cmpNav.intro}">
					<div class="nav_txt">
						<h1>${home_cmpNav.title }</h1>
						<div class="nav_intro">${home_cmpNav.intro }</div>
					</div>
				</c:if>
			</c:if>
			<div class="main">
				<div class="content">
					<%String msg = MessageUtil.getMessage(request);%>
					<%if(msg!=null){ %><div class="alerts_notice"><%=msg %></div><%} %>
					${html_body_content }
					<div class="clr"></div>
				</div>
				<div class="foot">
					<div class="navlist">
						<ul class="fnav">
							<li class="l">
								<div>
									<div class="logo">
										<c:if test="${not empty o.logo2path}">
										<a href="http://<%=request.getServerName() %>"><img src="${o.logo2Pic }" onload="fixPNG(this)"/></a>
										</c:if>
									</div>
									<p class="copyright">
									<c:if test="${not empty cmpInfo.cpinfo}">
									${cmpInfo.cpinfo }
									</c:if>
									<c:if test="${empty cmpInfo.cpinfo}">
									<%Calendar now=Calendar.getInstance();%>
									* Copyright ® <%=now.get(Calendar.YEAR) %> <span><a href="http://<%=request.getServerName() %>">${cmpInfo.domainName }</a></span> All rights reserved<br/>
									</c:if>
									</p>
									<div>
										<a href="/m"><hk:data key="epp.view.tomobile2"/></a> | 
										<c:if test="${loginUser!=null}">
											<c:if test="${sys_cmpadminuser}"><a href="<%=path %>/epp/web/op/webadmin/admincmpnav.do?companyId=${companyId}">站点管理</a> | </c:if>
											<a href="<%=path %>/epp/logout_web.do?companyId=${companyId}"><hk:data key="epp.logout"/></a>
										</c:if>
										<c:if test="${loginUser==null}">
											<a href="<%=path %>/epp/login_web.do?companyId=${companyId}"><hk:data key="epp.login"/></a>
										</c:if>
									</div>
								</div>
							</li>
						</ul>
						<c:set var="lastidx" value="${fn:length(cmpnavvolist)-1}"></c:set>
						<c:forEach var="vo" items="${cmpnavvolist}" varStatus="idx">
							<ul class="fnav<c:if test="${idx.index==lastidx}"> l</c:if>">
								<li class="f">${vo.cmpNav.name }</li>
								<c:forEach var="child" items="${vo.children}">
									<c:if test="${child.urlLink}">
										<c:if test="${not empty child.url}">
											<li><a href="/column/${companyId }/${child.oid}">${child.name }</a></li>
										</c:if>
										<c:if test="${empty child.url}">
											<li>${child.name }</li>
										</c:if>
									</c:if>
									<c:if test="${!child.urlLink}">
										<c:if test="${child.loginAndRefFunc}">
											<c:if test="${loginUser!=null}">
												<c:if test="${sys_cmpadminuser}">
													<li><a href="<%=path %>/epp/web/op/webadmin/admincmpnav.do?companyId=${companyId}">站点管理</a></li>
												</c:if>
												<c:if test="${!sys_cmpadminuser}">
													<li><a href="<%=path %>/epp/logout_web.do?companyId=${companyId}">退出</a></li>
												</c:if>
											</c:if>
											<c:if test="${loginUser==null}">
												<li><a href="/column/${companyId }/${child.oid}">${child.name }</a></li>
											</c:if>
										</c:if>
										<c:if test="${!child.loginAndRefFunc}">
											<li><a href="/column/${companyId }/${child.oid}">${child.name }</a></li>
										</c:if>
									</c:if>
								</c:forEach>
							</ul>
						</c:forEach>
						<div class="clr"></div>
					</div>
					<%EppViewUtil.loadCmpFrLinkList(request); %>
					<div class="frlink"><c:forEach var="frlink" items="${foot_cmpfrlist}"><a target="_blank" href="http://${frlink.url }">${frlink.name }</a></c:forEach>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
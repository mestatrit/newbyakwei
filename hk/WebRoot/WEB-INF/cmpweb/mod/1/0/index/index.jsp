<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="epp_other_value" scope="request"><link rel="stylesheet" type="text/css" href="<%=path %>/cmpwebst4/mod/1/0/css/index.css" /></c:set>
<c:set scope="request" var="html_body_content">
	<div class="mod">
		<div class="fl">
			<c:if test="${fn:length(cmphomepicadlist)>0}">
				<div class="slides">
					<ul class="slide-pic">
						<c:forEach var="picad" items="${cmphomepicadlist}" varStatus="idx">
							<li style="display: none;" id="homepic${idx.index }">
								<a target="_blank" href="http://${picad.url }"><img src="${picad.picUrl }" alt="${picad.title }" /></a>
							</li>
						</c:forEach>
					</ul>
					<ul class="slide-li op">
						<li class=""></li>
						<li class=""></li>
						<li class=""></li>
						<li class=""></li>
					</ul>
					<ul class="slide-li slide-txt">
						<c:forEach var="picad" items="${cmphomepicadlist}">
							<li><a target="_blank" href="http://${picad.url }">${picad.title }</a></li>
						</c:forEach>
					</ul>
				</div>
				<script type="text/javascript">
				getObj('homepic0').className='cur';
				$('#homepic0').css('display','list-item');
				</script>
			</c:if>
		</div>
		<div class="fr" style="width: 265px;overflow: hidden;">
			<c:if test="${fn:length(pinkarticlelist)>0}">
				<div class="news">
					<div class="title"><hk:data key="epp.news"/></div>
					<div class="con">
						<ul>
							<c:forEach var="article" items="${pinkarticlelist}">
							<li>
								<a href="<%=path %>/epp/web/cmparticle_view.do?companyId=${companyId}&oid=${article.oid}">${article.title }</a>
							</li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</c:if>
		</div>
		<div class="clr"></div>
	</div>
	<c:forEach var="sortvo" items="${cmpproductsortvolist}">
	<c:if test="${fn:length(sortvo.pinkList)>0 || fn:length(sortvo.pinkCmpArticleList)>0 || fn:length(sortvo.cmpProductSortFileList)>0}">
		<div class="mod">
			<div class="pkind">
				<div class="pkind_title">
					<div>
						<a href="<%=path %>/epp/web/product.do?companyId=${companyId}&sortId=${sortvo.cmpProductSort.sortId}">${sortvo.cmpProductSort.name }</a>
					</div>
				</div>
				<div class="pkind_other">
					<p class="details">
						<a href="<%=path %>/epp/web/product.do?companyId=${companyId}&sortId=${sortvo.cmpProductSort.sortId}"><hk:data key="epp.more"/> &gt;&gt;</a>
					</p>
				</div>
				<div class="clr"></div>
			</div>
			<div class="p_area">
				<c:if test="${fn:length(sortvo.cmpProductSortFileList)>0}">
					<div class="p_img">
						<c:forEach var="file" items="${sortvo.cmpProductSortFileList}" varStatus="idx">
						<div class="img${idx.index }">
							<a href="http://${file.url }"><img src="${file.picUrl }" title="${file.name }" alt="${file.name }"/></a>
						</div>
						</c:forEach>
						<div class="clr"></div>
					</div>
				</c:if>
				<c:if test="${fn:length(sortvo.pinkCmpArticleList)>0}">
					<div class="p_txt">
						<ul>
							<c:set var="aticlelendidx" value="${fn:length(sortvo.pinkCmpArticleList)-1}"/>
							<c:forEach var="article" items="${sortvo.pinkCmpArticleList}" varStatus="idx">
							<li <c:if test="${idx.index==aticlelendidx}">class="end"</c:if>><a href="<%=path %>/epp/web/cmparticle_view.do?companyId=${companyId}&oid=${article.oid}">${article.title }</a></li>
							</c:forEach>
						</ul>
					</div>
				</c:if>
				<div class="clr"></div>
			</div>
			<c:if test="${fn:length(sortvo.pinkList)>0}">
				<div class="plist">
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<ul>
									<c:forEach var="pink" items="${sortvo.pinkList}">
										<li><c:set var="product_url"><%=path %>/epp/web/product_view.do?companyId=${companyId}&productId=${pink.productId}</c:set>
											<c:if test="${empty pink.headPath}">
												<a href="${product_url }"><span class="nopic">暂无图片</span></a>
											</c:if>
											<c:if test="${not empty pink.headPath}">
												<div class="imgbox"><a href="${product_url }"><img src="${pink.head120 }" title="${pink.name }" alt="${pink.name }"/></a></div>
											</c:if>
											<p class="n"><a href="${product_url }">${pink.name }</a></p> 
											<p class="n"><hk:data key="epp.product.price"/>￥${pink.money }</p>
										</li>
									</c:forEach>
								</ul>
								<div class="clr"></div>
							</td>
						</tr>
					</table>
					<a class="more" href="<%=path %>/epp/web/product.do?companyId=${companyId}&sortId=${sortvo.cmpProductSort.sortId}"><hk:data key="epp.more"/>&gt;&gt;</a>
				</div>
			</c:if>
		</div>
	</c:if>
	</c:forEach>
	<c:if test="${fn:length(cmphomepicadlist)>1}">
		<script type="text/javascript">
			var defaultOpts = {
			    interval: 5000,
			    fadeInTime: 300,
			    fadeOutTime: 200
			};
			//Iterate over the current set of matched elements
			var _titles = $("ul.slide-txt li");
			var _titles_bg = $("ul.op li");
			var _bodies = $("ul.slide-pic li");
			var _count = _titles.length;
			var _current = 0;
			var _intervalID = null;
			var stop = function(){
			    window.clearInterval(_intervalID);
			};
			var slide = function(opts){
			    if (opts) {
			        _current = opts.current || 0;
			    }
			    else {
			        _current = (_current >= (_count - 1)) ? 0 : (++_current);
			    };
			    _bodies.filter(":visible").fadeOut(defaultOpts.fadeOutTime, function(){
			        _bodies.eq(_current).fadeIn(defaultOpts.fadeInTime);
			        _bodies.removeClass("cur").eq(_current).addClass("cur");
			    });
			    _titles.removeClass("cur").eq(_current).addClass("cur");
			    _titles_bg.removeClass("cur").eq(_current).addClass("cur");
			}; //endof slide
			var go = function(){
			    stop();
			    _intervalID = window.setInterval(function(){
			        slide();
			    }, defaultOpts.interval);
			}; //endof go
			var itemMouseOver = function(target, items){
			    stop();
			    var i = $.inArray(target, items);
			    slide({
			        current: i
			    });
			}; //endof itemMouseOver
			_titles.hover(function(){
			    if ($(this).attr('class') != 'cur') {
			        itemMouseOver(this, _titles);
			    }
			    else {
			        stop();
			    }
			}, go);
			//_titles_bg.hover(function() { itemMouseOver(this, _titles_bg); }, go);
			_bodies.hover(stop, go);
			//trigger the slidebox
			go();
		</script>
	</c:if>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>
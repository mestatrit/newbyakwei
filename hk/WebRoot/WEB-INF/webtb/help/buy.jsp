<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request">什么是返利
</c:set><c:set var="html_head_value">
<meta name="description" content="本站是淘宝的签约推广站点。我们通过让用户来推广那些值得购买的好商品来获得淘宝网的广告宣传费用" />
<meta name="keywords"  content="返利|顾问家|淘宝|商品|问答" />
</c:set><c:set var="html_body_content" scope="request">
<div class="mod">
<div class="mod_title">什么是返利</div>
<div class="mod_content">
本站是淘宝的签约推广站点。我们通过让用户来推广那些值得购买的好商品来获得淘宝网的广告宣传费用。<br/><br/>

对于淘宝的很多商品，我们拿出一些获得的广告费用让利给用户，以感谢他们能在微博分享那些他们购买的商品（我们相信不好你不会买的）。<br/><br/>

但是您必须满足如下要求：<br/><br/>

1、通过新浪微博登录本站<br/>
2、通过返利按钮进入淘宝并勾选了自动分享选项。<br/>
3、按照实际交易价格成功交易后，你将可以在淘宝网的确认收货之后通过本站的“财务查询”看到本站标明的返利。<br/>
4、点击提取红包，我们将把钱划入您的支付宝帐号。<br/>

为什么您通过本站过去但是无法拿到红包：<br/>

1、您通过绿色通道进入后，又点击了搜索或者其他链接丢失了本站的源信息。你可以再即将交易的时候，再把链接提交到我们的站点，一保证万无一失。<br/><br/>

2、您不是本站的登录用户，我们支持微博帐号自动登录（我们无法保存您的帐号和密码，绝对安全）。<br/><br/>

3、您购买了商品后，还没有确认收货或者进行了退款操作。<br/><br/>

4、淘宝还没有把您的购买记录传输给我们，您可以下月来查看。如果您留下email，我们将会再返利到帐后，email通知您。<br/><br/>
<a href="${denc_return_url }" class="more2">返回</a>
</div>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>
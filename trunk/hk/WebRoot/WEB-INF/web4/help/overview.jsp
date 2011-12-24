<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view2.help"/></c:set>
<c:set var="html_body_content" scope="request">
<div>
	<div class="overview" style="margin-left: auto; margin-right: auto; width: 800px;">
		<h1 class="title1">火酷欢迎你</h1>
 				<h3>你好，让我们看看你能在火酷做什么？</h3>
 				<div class="bar">足迹和报到</div>
	    <p>如果你告诉我们你在哪里，我们将可以告诉你的朋友和就在附近的朋友。我们管这个动作叫“报到”。当你到了一个地方，你可以报到，可以是公园、酒吧、咖啡厅、餐厅，甚至是一个车站，我们管这些地方叫“足迹”。目前的报到方式有2种：</p>
	    <ul>
	      <li>你可以通过电脑访问huoku.com报到！</li>
	      <li>你也可以通过手机访问huoku.com进行报到。</li>
	    </ul>
	    <p>
		我们将自动判断你的上网方式，为您提供电脑版或是手机版。我们推荐你拥有一部带有WIFI的手机，这样你将获得完全不同的感受。</p>
	    <div class="bar">抢地皮做地主</div>
	    <p>如果一个地方，你最先报到并超过3次，你将可以获得地主的荣誉。享受这里至高无上的荣誉和商家的实际奖励。如果这地方还没有在火酷发布，那就赶快去发布并报到吧！</p>
	    <div class="bar">徽章</div>
	    <p>你的报到和其他动作，将会赢得徽章，我们的徽章很多，等待你在火酷去研究和发现，例如想在火酷获得一个桃花运的徽章可是要看你的“桃花运”了。</p>
	    <div class="bar">分享这个城市</div>
	    <p>你在火酷的所有一切，将是在参与分享这个城市。面对这个尔虞我诈、伪劣假冒横行的世道，告诉大家地方是值得去的，哪些地方是不值得去的，将是非常有意义的事情。而在分享这一切的时候，你会发现将可以得到来自商家的奖励。</p>
	    <div class="bar">什么是点数，点数可以做什么？<a name="points"></a></div>
	   	<p> 对一个你去过的地方报到，可以获得报到点数。</p>
		<p>在一个地方最少获得8个点数，你将成为这里的地主。如果这个地方已经和火酷签约，地主身份将有可能获得极大的优惠，甚至免费。</p>
		<p>每个地方一天只能报到2次，如果你想多报到，只能通过开宝箱获得道具卡。这些道具卡将会让你获得更多的点数或者减少某个人的点数，甚至直接让你当上某个地方的地主。</p>
		<p>当然开宝箱是消耗总点数（你所有地方点数的合计），但是不影响你在一个地方的点数。</p>
	    <a class="more2" href="/index/">到首页</a>
	</div>
</div>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>
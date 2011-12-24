<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<hk:web title="购买关键词 - ${company.name}">
<link type="text/css" rel="stylesheet" href="<%=path %>/webst/css/intro_order.css" />
<jsp:include page="../../inc/top.jsp"></jsp:include>
<div class="hk">
	<div id="main">
		<div class="lbp_header">
			<div class="lbp_header_left">
				<div class="lbp_header_large">Browse</div>
				<div class="lbp_header_med">Browse for logo designs!</div>
				<div class="lbp_header_small">It’s free and easy.</div>
			</div>
			<div class="lbp_header_center">
				<div class="lbp_header_large">Buy</div>
				<div class="lbp_header_med">Buy directly from designers!</div>
				<div class="lbp_header_small">From all over the world.</div>
			</div>
			<div class="lbp_header_right">
				<div class="lbp_header_large">Download</div>
				<div class="lbp_header_med">Download your logo instantly!</div>
				<div class="lbp_header_small">There’s not need to wait.</div>
			</div>
		</div>
		<div id="lbp_body" class="lbp_body">
			<div id="id_lbp_body_left" class="lbp_body_left">
				<ul>
					<li class="expander" id="expand_1">
						<strong>Search by keyword, industry, price</strong>
						<div id="hide_expand_1" style="display: none;">Refine
your search based on exactly what your need is. Is price important to
you? Do you need something specific to your industry?</div>
					</li>
					<li class="expander" id="expand_2">
						<strong>Find logos paired with domains</strong>
						<div id="hide_expand_2" style="display: none;">Buy
a package to start up your idea with a relevant domain. We save you the
trouble of looking for one because we're cool like that.</div>
					</li>
					<li class="expander" id="expand_3">
						<strong>Brandstack Favs let you know the ones we love</strong>
						<div id="hide_expand_3" style="display: none;">Our
design experts have some favorites. Maybe you'll agree. If you do, we
should totally hang out because we like the same stuff.</div>
					</li>
					<li class="expander" id="expand_4">
						<strong>Designers often are willing to customize logos to your liking.</strong>
						<div id="hide_expand_4" style="display: none;">If
you find a logo you 99% like, let our designers fix that 1% for you.
They might even give you extra and you'll end up with 110%.</div>
					</li>
					<li class="expander" id="expand_5">
						<strong>Springs, Favs and Views inform you how a logo fares</strong>
						<div id="hide_expand_5" style="display: none;">Our
voting system of "Springs," our designated favorites and unique view
tally can inform you on a design's popularity in our community.</div>
					</li>
					<li class="expander" id="expand_6">
						<strong>Comments on designs give insight to public opinion</strong>
						<div id="hide_expand_6" style="display: none;">Read
through posted opinions on the designs that interest you. You might
agree, you might not, but either way it's good research.</div>
					</li>
					<li class="lbp_list_last expander" id="expand_7">
						<strong>View available variations on designs</strong>
						<div id="hide_expand_7" style="display: none;">Designers provide a few different color schemes or tweaked takes on their original concept to show you the design's potential.</div>
					</li>
				</ul>
			</div>
			<div class="lbp_body_center">
				<ul>
					<li class="expander" id="expand_8">
						<strong>Work with a designer or don't. It's your choice.</strong>
						<div id="hide_expand_8" style="display: none;">Buy straight from us with a basic e-shopping cart. No collaboration needed if you don't want to.</div>
					</li>
					<li class="expander" id="expand_9">
						<strong>Make offers on designer-set prices</strong>
						<div id="hide_expand_9" style="display: none;">Our
designers name their own price, but we provide you with the ability to
make an offer and leave it to the designer's discretion if they're
willing to compromise.</div>
					</li>
					<li class="expander" id="expand_10">
						<strong>Ask designers for alterations of logos</strong>
						<div id="hide_expand_10" style="display: none;">When
you've decided on a logo, ask your designer for any last minute changes
you'd like before the sale is final. They'll gladly oblige.</div>
					</li>
					<li class="expander" id="expand_11">
						<strong>Secure transactions</strong>
						<div id="hide_expand_11" style="display: none;">We offer worry-free secure transactions. Buy with confidence.</div>
					</li>
					<li class="expander lbp_list_last" id="expand_12">
						<strong>Immediate transfer of exclusive ownership.</strong>
						<div id="hide_expand_12" style="display: none;">Once
your sale is final, the logo is yours. It will not be resold and it is
now yours to do with as you please. Give it a good home, please.</div>
					</li>
				</ul>
			</div>
			<div class="lbp_body_right">
				<ul>
					<li class="expander" id="expand_13">
						<strong>Immediately download design files.</strong>
						<div id="hide_expand_13" style="display: none;">After
making your purchase, you are prompted to download your design files
right away. You don't HAVE TO download them right away, but we give you
that option.</div>
					</li>
					<li class="expander" id="expand_14">
						<strong>Know what types of files you are getting before you make a decision.</strong>
						<div id="hide_expand_14" style="display: none;">On
logo design pages, you can see a listing of the types of design files
the designer has submitted. It's a courtesy we offer to make sure you
don't get design files you don't have the programs for.</div>
					</li>
					<li class="expander" id="expand_15">
						<strong>Maintain relationships with your logo designer.</strong>
						<div id="hide_expand_15" style="display: none;">It's
not out of the ordinary for our logo buyers to form a partnership with
the designer who made their logo. Don't be afraid to contact them.
They're quite friendly.</div>
					</li>
					<li class="expander lbp_list_last" id="expand_16">
						<strong>Your file will stay stored here in case you need to download it again.</strong>
						<div id="hide_expand_16" style="display: none;">If
for some reason you ever need to get your design files again, we keep a
record of your sale on our site and copies of the design files only for
you.</div>
					</li>
				</ul>
			</div>
			<div class="clr"></div>
		</div>
	</div>
</div>
<script type="text/javascript">
var expand_count=16;
$(document).ready(function(){
    for(var i=1;i<=expand_count;i++){
    	$('#expand_'+i).bind(
    		"click",
    		function(){
    			var oid=this.id;
    			var hide_oid="hide_"+oid;
    			var s=getObj(hide_oid).style.display;
    			if(s=='none'){
    				$('#'+hide_oid).css("display","block");
    				getObj(oid).className="expander active";
    			}
    			else{
    				$('#'+hide_oid).css("display","none");
    				getObj(oid).className="expander";
    			}
    		}
    	);
    }
});
</script>
<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:web>
<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.web.util.Hkcss2Util"%>
<%@page import="com.hk.bean.Company"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<jsp:include page="../../inc/pub_inc.jsp"></jsp:include>
<c:set var="html_title" scope="request"><hk:data key="view.company.orderform.addproduct"/></c:set>
<c:set var="script_content" scope="request">
<script type="text/javascript">
var autoComplete = null;
var dataSource=[];
onload= function pageLoadHdle() {
	var configuration = {
		instanceName : "autoComplete",
		textbox : document.getElementById("s_key")
	};
	autoComplete = new neverModules.modules.autocomplete(configuration);
	autoComplete.callback = function(oid, autocompleteValue,autocompleteContent) {
		additem(oid);
	}
	autoComplete.useContent = true;
	autoComplete.useSpaceMatch = true;
	autoComplete.ignoreWhere = true;
	autoComplete.create();
	autoComplete.showClose=true;
	autoComplete.showFooterHtml='<div style="position:absolute;bottom:0px;left:10px;"><a style="text-decoration: underline;" href="<%=path %>/e/op/orderform_help.do?companyId=${companyId}&oid=${oid}&return_url=${return_url}"><hk:data key="view.company.product.notfound"/></a></div>';
}
</script>
</c:set>
<c:set var="body_hk_content" scope="request">
<style>
.searchpad{
padding-left: 30px;
}
html{
overflow-y: scroll;
}
/* this is the autocomplete container style, summary style */
.neverModules-auto { 
  border:1px solid #000;
  background-color:#fff;
  width:100%;
  margin:0;
  padding:0;
}

/* this the autocomplete property useContent is not true, 
the style is the item css, default style or onmouseout style */
.neverModules-auto .out {
  width:100%;
  color:#000;
}

/* this the autocomplete property useContent is not true, 
the style is the item css, highlight (onmouseover) style */
.neverModules-auto .over {
font-size:14px;
width:100%;
color:highlighttext;
background-color:#3366CC;
}

/* -----------------------------------------------------------------
if autocomplete property useContent is true, using folowing style
 ----------------------------------------------------------------- */

/* text style, in the left of the autocomplete container */
.neverModules-auto .autot {
  color:#000;
  text-align:left;
  padding-left:5px;
  width:50%;
}

.neverModules-auto .autot-over {
  color:#fff;
  text-align:left;
  padding-left:5px;
  width:50%;
}

/* autocomplete right content default */
.neverModules-auto .autoc {
  color:#008000;
  text-align:right;
  padding-right:5px;
  width:50%;
}

.neverModules-auto .autoc-over {
  color:#fff;
  text-align:right;
  padding-right:5px;
  width:50%;
}
</style>
	<div class="mod_left">
		<div class="mod">
			<div class="mod-5 simple_nav">
				<%=Hkcss2Util.rd_bg %>
				<div class="cont">
					<div class="pad">
						<ul class="userset">
							<li>
								<a class="n1" href="<%=path %>/e/op/orderform_productlist.do?companyId=${companyId }&oid=${oid}&return_url=${return_url}"><hk:data key="view.all"/></a>
							</li>
							<c:forEach var="k" items="${cmpproductsortlist}">
							<li>
								<a class="n1" href="<%=path %>/e/op/orderform_productlist.do?companyId=${companyId }&oid=${oid}&sortId=${k.sortId }&return_url=${return_url}">${k.name }<span>(${k.productCount })</span></a>
							</li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<%=Hkcss2Util.rd_bg_bottom %>
			</div>
			<div class="clr"></div>
		</div>
	</div>
	<div class="mod_primary">
		<c:set var="nav_2_path_content" scope="request">
			<ul>
				<li><a class="home" href="http://<%=HkWebConfig.getWebDomain()%>"></a></li>
				<li><a class="nav-a" href="#">${company.name }</a></li>
				<li><a class="nav-a" href="<%=path %>/e/op/orderform_productlist.do?companyId=${companyId}&oid=${oid}&return_url=${return_url}">产品</a></li>
				<c:if test="${cmpProductSort!=null}">
				<li><a class="nav-a" href="<%=path %>/cmp.do?companyId=${companyId}&sortId=${cmpProductSort.sortId}&oid=${oid}&return_url=${return_url}">${cmpProductSort.name }</a></li>
				</c:if>
				<li><a class="nav-a" href="${denc_return_url}">返回</a></li>
			</ul>
		</c:set>
		<jsp:include page="../../inc/nav-2.jsp"></jsp:include>
		<div class="mod_primary_l">
			<div>
				<br class="linefix"/>
				<h3 class="title3"><hk:data key="view.company.orderform.addproduct"/></h3>
				<hk:form action="" onsubmit="return addfirstitem(event)">
					<hk:hide name="companyId" value="${companyId}"/>
					<hk:hide name="oid" value="${oid}"/>
					<hk:hide name="sortId" value="${sortId}"/>
					<hk:hide name="return_url" value="${denc_return_url}"/>
					<span class="text_16">您正在使用<span id="searchType"><hk:data key="view.company.searchproduct_type${company.psearchType}"/></span>进行搜索</span><br/>
					<input id="s_key" type="text" class="text" onkeyup="clientEvt(event,this.value)"/>
					<hk:button value="搜索" clazz="btn" onclick="clientEvt(event,getObj('s_key').value)"/>
					<span id="searchtip" class="tip3"></span><br/>
					<div class="text_14 searchpad">
						<span class="split-r"><input id="search_<%=Company.PSEARCHTYPE_NAME %>" type="radio" name="stype" value="<%=Company.PSEARCHTYPE_NAME %>" onchange="selsearch(this.value)" /><span id="searchname_<%=Company.PSEARCHTYPE_NAME %>"><hk:data key="view.company.searchproduct_type0"/></span></span> 
						<span class="split-r"><input id="search_<%=Company.PSEARCHTYPE_PNUM %>" type="radio" name="stype" value="<%=Company.PSEARCHTYPE_PNUM %>" onchange="selsearch(this.value)" /><span id="searchname_<%=Company.PSEARCHTYPE_PNUM %>"><hk:data key="view.company.searchproduct_type1"/></span></span>
						<span class="split-r"><input id="search_<%=Company.PSEARCHTYPE_SHORTNAME %>" type="radio" name="stype" value="<%=Company.PSEARCHTYPE_SHORTNAME %>" onchange="selsearch(this.value)" /><span id="searchname_<%=Company.PSEARCHTYPE_SHORTNAME %>"><hk:data key="view.company.searchproduct_type2"/></span></span> 
						
					</div>
					<script type="text/javascript">
					var _checked=${company.psearchType};
					getObj("search_"+_checked).checked=true;
					function selsearch(v){
						setHtml('searchType',getHtml('searchname_'+v));
						_checked=v;
					}
					</script>
				</hk:form>
			</div>
			<div>
				<h3 class="title">已添加的商品</h3>
				<div id="itemlist">
				</div>
				<div align="center" class="pad">
					<hk:button value="返回" clazz="btn" onclick="tourl('${denc_return_url}')"/>
				</div>
			</div>
		</div>
		<div class="mod_primary_r"></div>
		<div class="clr"></div>
	</div>
	<div class="clr"></div>
<script type="text/javascript" language="javascript" src="<%=path%>/webst3/js/autocomplete/neverModules-autoComplete-hk.js"></script>
<script type="text/javascript" language="javascript" src="<%=path%>/webst3/js/jquery-ui-1.7.2.custom.min.js"></script>
<script type="text/javascript">
function addfirstitem(event){
	if(dataSource.length>0){
		additem(dataSource[0].oid);
		autoComplete.close();
	}
	return false;
}
function additem(pid){
	setHtml('searchtip','正在添加 ... ...');
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/orderform_additem.do?companyId=${companyId}&pid='+pid+"&oid=${oid}",
		cache:false,
    	dataType:"html",
		success:function(data){
			setHtml('searchtip','添加成功');
			$('#searchtip').effect('highlight',{},1000,function(){});
			loaditemlist();
		}
	});
}
function clientEvt(event,key) {
	if (autoComplete.isValidKey(event) == false ) {
		autoComplete.hdleEvent(event);
		return;
	}
	loadProduct(event,key);
}
function loadProduct(event,key){
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/orderform_findproduct.do',
		data: 'companyId=${companyId}&ajax=1&key='+key+'&st='+_checked,
		cache:false,
    	dataType:"html",
		success:function(data){
			dataSource = eval(data);
			if(dataSource.length==0){
				autoComplete.showFooter=true;
			}
			else{
				autoComplete.showFooter=false;
			}
			autoComplete.setDataSource(dataSource);
			autoComplete.hdleEvent(event);
		}
	});
}
function loaditemlist(){
	setHtml('itemlist','正在加载订单商品 ... ...');
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/orderform_loaditemlist.do',
		data: 'companyId=${companyId}&oid=${oid}',
		cache:false,
    	dataType:"html",
		success:function(data){
			setHtml('itemlist',data);
		}
	});
}
function delitem(id){
	setHtml('itemtip'+id,'正在删除 ... ...');
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/orderform_delitem.do',
		data: 'companyId=${companyId}&itemId='+id,
		cache:false,
    	dataType:"html",
		success:function(data){
			loaditemlist();
		}
	});
}
function decreaseitem(id){
	setHtml('itemtip'+id,'正在操作 ... ...');
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/orderform_decreaseitem.do',
		data: 'companyId=${companyId}&itemId='+id,
		cache:false,
    	dataType:"html",
		success:function(data){
			loaditemlist();
		}
	});
}
loaditemlist();
</script>
</c:set>
<jsp:include page="../../inc/cmpmgrframe.jsp"></jsp:include>
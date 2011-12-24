function showGlass(objId){
	showSubmitDiv(objId);
}
function showObjGlass(objId){
	showSubmitDivForObj(objId);
}
function hideGlass(){
	hideSubmitDiv();
}
var isIE  = (navigator.appVersion.indexOf("MSIE") != -1) ? true : false;
Array.prototype.del=function(n){if(n<0){return this;}else{return this.slice(0,n).concat(this.slice(n+1,this.length));}}
var win_pop_show=false;
var win_pop_show_id=null;
function trim(v){
	var vv=v.replace(/(^\s*)|(\s*$)/g, '');
	return vv;
}
function toHtml(v){
	return v.replace(/\r/g, '<br/>').replace(/\n/g, '<br/>');
}
function toText(v){
	return v.replace(/<br\/>/g, '\n')
	.replace(/&lt;/g, '<')
	.replace(/&gt;/g, '>')
	.replace(/&nbsp;/g, ' ')
	.replace(/&amp;/g, '&')
	;
}
function isEmpty(v){
	if(trim(v)===''){
		return true;
	}
	return false;
}
function getObj(id){
	return document.getElementById(id);
}
function tourl(url){
	document.location.href=url;
}
function setHtml(id,v){
	$('#'+id).html(v);
}
function getHtml(id){
	return getObj(id).innerHTML;
}
/**
 * 返回创建的阴影层id
 * @param objId
 * @param large 是否放大阴影层
 * @return
 */
function addGlass(objId,large){
	var add_width=0;
	var add_height=0;
	var add_top=0;
	var add_left=0;
	if(large){
		add_width=100;
		add_height=100;
		add_top=-50;
		add_left=-50;
	}
	var width = $('#'+objId).width()+add_width;
	var height = $('#'+objId).height()+add_height;
	var mid_height=height/2;
	var top = $('#'+objId).offset().top+add_top;
	var left= $('#'+objId).offset().left+add_left;
	var newdivid='hidediv_hk_'+objId;
	var newdiv=$('<div id="'+newdivid+'" style="background: rgb(238, 238, 238) none repeat scroll 0% 0%; height: '+height+'px; width: '+width+'px; -moz-background-clip: border; -moz-background-origin: padding; -moz-background-inline-policy: continuous; position: absolute; top: '+top+'px; left: '+left+'px; z-index: 99999999; opacity: 0.5; filter: alpha(opacity=50);"><img src="'+path+'/webst4/img/blue-loading.gif" style="margin: '+mid_height+'px auto 0px;display: block;"/></div>');
	$('body').append(newdiv);
	return newdivid;
}
function removeGlass(glassid){
	$('#'+glassid).remove();
}
function showSubmitDiv(objId){
	var width = $('#'+objId).width();
	var height = $('#'+objId).height();
	var mid_height=height/2;
	var top = $('#'+objId).offset().top;
	var left= $('#'+objId).offset().left;
	var newdiv=$('<div id="hidediv" style="background: rgb(238, 238, 238) none repeat scroll 0% 0%; height: '+height+'px; width: '+width+'px; -moz-background-clip: border; -moz-background-origin: padding; -moz-background-inline-policy: continuous; position: absolute; top: '+top+'px; left: '+left+'px; z-index: 99999999; opacity: 0.5; filter: alpha(opacity=50);"><img src="'+path+'/webst4/img/blue-loading.gif" style="margin: '+mid_height+'px auto 0px;display: block;"/></div>');
	$('body').append(newdiv);
}
function showSubmitDivForObj(objId){
	var width = $('#'+objId).width()+100;
	var height = $('#'+objId).height()+100;
	var mid_height=height/2;
	var top = $('#'+objId).offset().top-50;
	var left= $('#'+objId).offset().left-50;
	var newdiv=$('<div id="hidediv" style="background: rgb(238, 238, 238) none repeat scroll 0% 0%; height: '+height+'px; width: '+width+'px; -moz-background-clip: border; -moz-background-origin: padding; -moz-background-inline-policy: continuous; position: absolute; top: '+top+'px; left: '+left+'px; z-index: 99999999; opacity: 0.5; filter: alpha(opacity=50);"><img src="'+path+'/webst4/img/blue-loading.gif" style="margin: '+mid_height+'px auto 0px;display: block;"/></div>');
	$('body').append(newdiv);
}
function hideSubmitDiv(){
	$('#hidediv').remove();
}
function createSimpleWindow(win_id,width,height,left,top,title,html,hideEvent){
	if(win_pop_show){
		hideWindow(win_pop_show_id);
	}
	var con=$('<div id="'+win_id+'" class="winab" style="left:'+left+'px;top:'+top+'px;display:none"> <div class="win2" style="width:'+width+'px;height:'+height+'px"><div class="innerwin" style="height:'+(height-50)+'px"> <div class="handle"></div> <div id="'+win_id+'_title" class="title">'+title+'<input type="text" id="popwin_close_btn"  style="height:0;width:0;margin:0;padding:0;line-height:0;font-size:0;border:none"/></div> <div id="'+win_id+'_content" class="content">'+html+'</div> </div> <div id="id_winbtm" class="winbtm"><div id="id_winbtm_content"></div><a href="javascript:'+hideEvent+'">关闭</a> <div class="clr"></div> </div> </div> </div>');
	$('body').append(con);
	var newtop=document.documentElement.scrollTop + (document.documentElement.clientHeight - $('#'+win_id).height())/2;
	var newleft=(document.documentElement.clientWidth - $('#'+win_id).width())/2
	$('#'+win_id).css("top",newtop);
	$('#'+win_id).css("left",newleft);
	$('#'+win_id).css("display","block");
	win_pop_show=true;
	win_pop_show_id=win_id;
}
function createWin(win_id,width,height,title,html,hideEvent){
	var left=document.documentElement.scrollLeft+(document.documentElement.clientWidth)/2-180;
	var top=document.documentElement.scrollTop+(document.documentElement.clientHeight)/2-200;
	createSimpleWindow(win_id,width,height,left,top,title,html,hideEvent);
}
function createImgWin(win_id,width,height,title,html,hideEvent){
}
function hideWindow(win_id){
	win_pop_show=false;
	$('#'+win_id).remove();
	win_pop_show_id=null;
}
function delay(func,t){window.setTimeout(func,t);}
function validateClear(id_ref){
	setHtml(id_ref+"_error","");
	if(getObj(id_ref+"_flag")!=null){
		getObj(id_ref+"_flag").className="flag";
	}
}
function validateErr(id_ref,msg){
	setHtml(id_ref+"_error",msg);
	if(getObj(id_ref+"_flag")!=null){
		getObj(id_ref+"_flag").className="flag errorflg";	
	}
}
function refreshurl(){
	var url=document.location.href;
	var i=url.lastIndexOf('#');
	if(i!=-1){
		url=url.substring(0,i);
	}
	tourl(url);
}
function delObj(objId){
	$('#'+objId).remove();
}
function insertObjBefore(html,beforeObjId){
	$(html).insertBefore('#'+beforeObjId);
}
function insertObjAfter(html,afterObjId){
	$(html).insertAfter('#'+afterObjId);
}
function appendObj(html,objId){
	$('#'+objId).append($(html));
}
function createBg(){
	var h=document.body.scrollHeight;
	var screenh=document.documentElement.clientHeight;
	if(h<screenh){
		h=screenh;
	}
	$('body').append($('<div id="hk_al_bg" class="floatBoxBg" style="height:'+h+'px"></div>'));
}
function clearBg(){
	delObj("hk_al_bg");
}
function filtertxt(str){var filterStr=str.replace(/(^\s*)|(\s*$)/g, '').replace(/&/g,"&amp;").replace(/</g,"&lt;").replace(/>/g,"&gt;").replace(/\n/g,"<br/>").replace(/\r/g,"");return filterStr;}
function isNumber(t){
	t=filtertxt(t);
	if(t==""){
		return false;
	}
	if(/\D/.test(t)){
		return false;
	}
	return true;
}
function encodeURL(url){
	return encodeURIComponent(url);
}
function encodeLocalURL(){
	return encodeURIComponent(document.location.href);
}
function getoidparam(error){
	var err_objid="";
	eval("err_objid=err_code_"+error+".objid;");
	return err_objid;
}
function getmsgfromlist(error,json){
	eval("var errormap="+json+";");
	eval("var msg=errormap.error_"+error+";");
	return msg;
	
}
function getObjsByName(name){
	return document.getElementsByName(name);
}
// parent.afterSubmit(error,error_msg,op_func,obj_id_param);
// parent.functionName(error,error_msg,op_func,obj_id_param,respValue);
//
// 怎样用javascript脚本来控制跳到锚点
// window.location.hash = "1";//1为锚点的名字
function topos(v){
	window.location.hash=v;
}
function doAjax(_url,_func){
	$.ajax({
		type:"POST",
		url:_url,
		cache:false,
    	dataType:"html",
		success:_func
	});
}
/**
 * 自动根据当前时间生成元素id
 * @param begin_id
 * @return
 */
function createObjId(begin_id){
	var date = new Date();
	return begin_id + date.getTime();
}
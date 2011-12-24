function hidemapstatus_info(){
	setHtml('mapstatus_info','');
}

function findByAddr(address,address2,editmap) {
	if (geocoder) {
		geocoder.getLatLng(address,function(point){
			if (!point) {
				geocoder.getLatLng(address2,function(point){
					if(!point){
						setmapdata(new GLatLng(39.917, 116.397),14);
					}
					else{
						setmapdata(point,editmap);
					}
					
				});
			}
			else {
				setmapdata(point,editmap);
			}
		});
	}
}
function setmapdata(point,editmap){
	last_point=point;
	map.setCenter(point, 13);
	map.addControl(new GSmallMapControl());
	if(marker!=null){
		marker.remove();
	}
	if(isShowBig){
		marker = new GMarker(point,markerOptions);
	}
	else{
		marker = new GMarker(point,markerOptions2);
	}
	map.addOverlay(marker);
	if(editmap){
		GEvent.addListener(marker, "dragstart", function() {
			map.closeInfoWindow();
		});
		GEvent.addListener(marker, "dragend", function() {
			var current =marker.getLatLng();
			last_point=current;
			updateCompanyLocation(current);
		});
	}
}
function findMaker(){
	if(marker!=null){
		marker.remove();
	}
	marker = new GMarker(map.getCenter(),markerOptions);
	map.addOverlay(marker);
	GEvent.addListener(marker, "dragstart", function() {
		map.closeInfoWindow();
	});
	GEvent.addListener(marker, "dragend", function() {
		var current =marker.getLatLng();
		last_point=current;
		updateCompanyLocation(current);
	});
}
function subtagfrm(frmid){
	showGlass(frmid);
	return true;
}
function createtagok(e,s,tagId){
	hideGlass();
	var html='<div class="tagbox"><a href="/venue/tag/'+tagId+'" class="b">'+getObj('tagipt').value+'</a> [<a href="javascript:deltag('+tagId+')">X</a>]</div>';
	insertObjBefore(html,'tagline');
	getObj('tagipt').value='';
}
function createtagerror(e,s,v){
	hideGlass();
	alert(s);
}
function deltag(id){
	if(window.confirm("确实要删除？")){
		$.ajax({
			type:"POST",
			url:path+"/h4/op/user/venue_deltag.do?companyId="+companyId+"&tagId="+id,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function subcheckinfrm(frmid){
	if(!userLogin){
		alertLoginAndReg();
		return false;
	}
	showGlass(frmid);
	getObj('checkin_tip').style.display='none';
	return true;
}
function toofast(error,error_msg,respValue){
	getObj('checkin_tip').style.display='none';
}
function checkinok(error,error_msg,respValue){
	refreshurl();
}
function checkinerror(error,error_msg,respValue){
	hideGlass();
	setHtml('checkin_tip',error_msg);
	getObj('checkin_tip').style.display='block';
	setTimeout(function(){
		setHtml('checkin_tip','');
		getObj('checkin_tip').style.display='none';
	}, 3000);
}
function updateCompanyLocation(o){
	setHtml('mapstatus_info','保存位置 ... ...');
	marker_x=o.lat();
	marker_y=o.lng();
	$.ajax({
		type:"POST",
		url:"/updatecompanylocation?companyId="+companyId+"&marker_x="+marker_x+"&marker_y="+marker_y,
		cache:false,
    	dataType:"html",
		success:function(data){
			var html='';
			setHtml('mapstatus_info','保存成功');
			delay("hidemapstatus_info()",2000);
		}
	});
}
function setcmpuserstatusdone(obj){
	if(!userLogin){
		alertLoginAndReg();
		return;
	}
	var acturl='';
	if(obj.checked==true){
		acturl=path+"/h4/op/user/venue_updateusercmpstatus.do?companyId="+companyId+"&status="+CompanyUserStatus_USERSTATUS_DONE;
	}
	else{
		acturl=path+"/h4/op/user/venue_deleteusercmpstatus.do?companyId="+companyId+"&status="+CompanyUserStatus_USERSTATUS_DONE;
	}
	$.ajax({
		type:"POST",
		url:acturl,
		cache:false,
    	dataType:"html",
		success:function(data){
		changestatusdonehtml(obj);
		}
	});
}
function changestatusdonehtml(obj){
	if(obj.checked==true){
		getObj('div_cmpuserstatus_done').className='tip_checked';
	}
	else{
		getObj('div_cmpuserstatus_done').className='tip_todo_unchecked';
	}
}
function setcmpuserstatuswant(obj){
	if(!userLogin){
		alertLoginAndReg();
		return;
	}
	var acturl='';
	if(obj.checked==true){
		acturl=path+"/h4/op/user/venue_updateusercmpstatus.do?companyId="+companyId+"&status="+CompanyUserStatus_USERSTATUS_WANT;
	}
	else{
		acturl=path+"/h4/op/user/venue_deleteusercmpstatus.do?companyId="+companyId+"&status="+CompanyUserStatus_USERSTATUS_WANT;
	}
	$.ajax({
		type:"POST",
		url:acturl,
		cache:false,
    	dataType:"html",
		success:function(data){
			changestatuswanthtml(obj);
		}
	});
}
function changestatuswanthtml(obj){
	if(obj.checked==true){
		getObj('div_cmpuserstatus_want').className='tip_checked';
	}
	else{
		getObj('div_cmpuserstatus_want').className='tip_todo_unchecked';
	}
}
function showBigMap(){
	isShowBig=true;
	var html='<div style="position: relative;">';
	if(canedit){
		html+='<div class="mapstatus" style="top:5px;bottom:auto;">拖动蓝色图钉来修改位置。找不到图钉？<a href="javascript:findMaker()" style="color:#2398C9;">让它过来！</a><span id="mapstatus_info"></span></div>';
	}
	else{
		html+='<div class="mapstatus" style="top:5px;bottom:auto;"><span id="mapstatus_info"></span></div>';
	}
	html+='<div id="map_canvas_2" style="height:420px;overflow: hidden;">'+getHtml('map_canvas')+'</div>';
	html+='<div id="mapinfo"></div>';
	html+='</div>';
	createBg();
	var title='<span style="font-size:22px">'+companyname+'</span><a style="position:absolute;font-size:20px;right:5px;top:5px;" href="javascript:closeBigMap()">关闭</a>';
	createCenterWindow("bigmap",850,550,title,html,"closeBigMap()");
	id_winbtm_html='<div style="padding:3px;position:absolute;width:530px;left:15%; color:#ffffff;font-size:20px;font-weight:bold;">'+view2_pleast_input_otherbuilding_for_search+'</div>';
	setHtml('id_winbtm_content',id_winbtm_html);
	map = new GMap2(document.getElementById("map_canvas_2"));
	setmapdata(last_point,canedit);
	// bind a search control to the map, suppress result list
    map.addControl(new google.maps.LocalSearch(), new GControlPosition(G_ANCHOR_BOTTOM_RIGHT, new GSize(10,20)));
    GEvent.addListener(map,"moveend", function(overlay,latlng) {     
       
      });

    GSearch.setOnLoadCallback(initialize);
}
function closeBigMap(){
	isShowBig=false;
	hideWindow('bigmap');
	clearBg();
	map = new GMap2(document.getElementById("map_canvas"));
	setmapdata(last_point,false);
}
function editMap(){
	showBigMap();
}
function showmoretraffic(){
	setHtml('venue_traffic',getHtml('moretraffic'));
}
function hidemoretraffic(){
	setHtml('venue_traffic',getHtml('simpletraffic'));
}
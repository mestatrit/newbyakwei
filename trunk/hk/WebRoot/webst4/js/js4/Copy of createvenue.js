var marker_x=-1;
var marker_y=-1;
var map = null;
var marker=null;
var geocoder = null;
var googleok=false;
var tipwrite=false;
var submitfrm=false;
function subvenuefrm(){
	if(submitfrm){
		return false;
	}
	submitfrm=true;
	showGlass("sfrm");
	setHtml('_name','');
	setHtml('_addr','');
	setHtml('_tel','');
	setHtml('_zoneName','');
	if(trim(getObj('_content').value).length>0){
		tipwrite=true;
	}
	getObj('subbtn').disabled=true;
	if(googleok){
		var key1=null;
		var key2=null;
		if(parseInt(getObj('id_pcityId').value)>0 && getObj('ipt_name').value!=''){
			key1=getCityName(parseInt(getObj('id_pcityId').value))+" "+getObj('ipt_name').value;
		}
		if(parseInt(getObj('id_pcityId').value)>0 && getObj('sfrm').addr.value!=''){
			key2=getCityName(parseInt(getObj('id_pcityId').value))+" "+getObj('sfrm').addr.value;
		}
		if(key1!=null){
			findByAddr(key1,key2);
			return false;
		}
	}
	return true;
}
function createvenueok(error,error_msg,id){
	if(marker_x==-1){
		tourl('/venue/'+id);
	}
	if(tipwrite){
		tourl('/venue/'+id);
	}
	else{
		tourl('/createtip?companyId='+id+"&doneflg="+doneflg);
	}
}
function createvenueerror(error,error_msg,v){
	submitfrm=false;
	setHtml(getoidparam(error),error_msg);
	hideGlass();
	getObj('subbtn').disabled=false;
}
function setzonename(id){
	if(id>0){
		for(var i=0;i<city.length;i++){
			if(city[i][0]==parseInt(id)){
				getObj('ipt_zoneName').value=city[i][3];
			}
		}
	}
}
function findByAddr(address,address2) {
	if (geocoder) {
		geocoder.getLatLng(address,function(point){
			if (!point) {
				if(address2!=''){
					geocoder.getLatLng(address2,function(point2){
						if(point2){
							getLocation(point2);
						}
						getObj('sfrm').submit();
					});
				}
				else{
					getObj('sfrm').submit();
				}
			}
			else {
				getLocation(point);
				getObj('sfrm').submit();
			}
		});
	}
}
function getLocation(o){
	marker_x=o.lat();
	marker_y=o.lng();
	getObj('_marker_x').value=marker_x;
	getObj('_marker_y').value=marker_y;
}


function initialize() {
	if (GBrowserIsCompatible()) {
		googleok=true;
		geocoder = new GClientGeocoder();
	}
}
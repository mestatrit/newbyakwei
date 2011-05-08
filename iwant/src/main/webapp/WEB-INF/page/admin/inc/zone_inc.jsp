<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:actioninvoke mappinguri="/syscnf_zone"/>
<script type="text/javascript">
function Zone(eid,_pid,_cid){
	this.pid=_pid;
	this.cid=_cid;
	var self=this;
	this.init=function(){
		var v='<select id="'+this.pid+'" name="provinceid" class="split-r"><\/select><select id="'+this.cid+'" name="cityid"><\/select>';
		$('#'+eid).html(v);
		var p_select_el=getObj(self.pid);
		p_select_el.options.length=0;
		p_select_el.options[0]=new Option('请选择',0);
		for(var i=0;i<parr.length;i++){
			p_select_el.options[p_select_el.options.length]=new Option(parr[i][1],parr[i][0]);
		}
		$('#'+self.pid).change(function(){
			var sel_pidvalue=getObj(self.pid).value;
			var city_select_el=getObj(self.cid);
			city_select_el.options.length=0;
			for(var i=0;i<carr.length;i++){
				if(sel_pidvalue==carr[i][1]){
					city_select_el.options[city_select_el.options.length]=new Option(carr[i][2],carr[i][0]);
				}
			}
			if(city_select_el.options.length==1){
				city_select_el.options[0].selected=true;
			}
		});
	}
}

var parr=new Array();
var carr=new Array();
<c:forEach var="p" items="${syscnf_provincelist}" varStatus="idx">
parr[${idx.index}]=new Array(${p.provinceid},'<hk:value value="${p.name}" onerow="true"/>');
</c:forEach>
<c:forEach var="c" items="${syscnf_citylist}" varStatus="idx">
carr[${idx.index}]=new Array(${c.cityid} , ${c.provinceid} , '<hk:value value="${c.name}" onerow="true"/>');
</c:forEach>


</script>
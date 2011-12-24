<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CmpOrgStudyAdUser"%>
<%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.User"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="title_value" scope="request">
${cmpOrgStudyAd.title }
</c:set>
<c:set var="html_body_content" scope="request">
<div class="mod">
	<c:if test="${cmpOrgNav!=null}">
		<div class="tit">${cmpOrgNav.name }
			<a class="more" href="/edu/${companyId }/${orgId}/column/${orgnavId}">返回</a>
		</div>
	</c:if>
	<div class="content2">
		<c:if test="${adminorg}">
		<div class="divrow">
			<a class="split-r" href="<%=path %>/epp/web/org/studyad_update.do?companyId=${companyId}&orgId=${orgId}&orgnavId=${orgnavId}&adid=${adid}">修改</a>
			<a href="javascript:delstudyad()">删除</a>
		</div>
		</c:if>
		<c:if test="${fn:length(nextlist)>0}">
			<div class="fr" style="font-weight: bold;font-size: 30px;">
				<c:forEach var="next" items="${nextlist}">
				<a href="/edu/${companyId }/${orgId}/zhaosheng/${orgnavId}/${next.adid }.html">&gt;&gt;</a>
				</c:forEach>
			</div>
		</c:if>
		<div style="margin-right: 50px;text-align: center;"><h1>${cmpOrgStudyAd.title }</h1></div>
		课程名称：${cmpOrgStudyAd.title }<br/>
		专业：${cmpStudyKind.name}<br/>
		授课学校：${cmpOrgStudyAd.schoolName }<br/>
		授课方式：${cmpOrgStudyAd.teachType }<br/>
		培训费用：${cmpOrgStudyAd.price }<br/>
		开课时间：<fmt:formatDate value="${cmpOrgStudyAd.beginTime}" pattern="yyyy-MM-dd"/><br/>
		有效日期：<fmt:formatDate value="${cmpOrgStudyAd.availableTime}" pattern="yyyy-MM-dd"/>${cmpOrgStudyAd.title }<br/>
		上课地点：${cmpOrgStudyAd.studyAddr }<br/>
		招生对象：${cmpOrgStudyAd.studyUser }<br/>
		<div>内容简介：<br/>${cmpOrgStudyAdContent.content }</div>
		<div class="baoming" style="margin-top: 20px;">
			<span class="b" style="font-size: 18px">我要报名</span><br/>
			<c:if test="${last_bm_CmpOrgStudyAdUser!=null}">
			<div style="color: red">您已经在<fmt:formatDate value="${last_bm_CmpOrgStudyAdUser.createTime}" pattern="yyyy-MM-dd"/>报过名 </div>
			</c:if>
			<span class="b">带 * 为必填项</span><br/>
			<form id="bmfrm" onsubmit="return subbmfrm(this.id)" method="post" action="<%=path %>/epp/web/org/studyad_pubbm.do" target="hideframe">
				<hk:hide name="companyId" value="${companyId}"/>
				<hk:hide name="orgId" value="${orgId}"/>
				<hk:hide name="adid" value="${adid}"/>
				<table class="nt all" cellpadding="0" cellspacing="0">
					<tr>
						<td width="90" align="right">*姓名：</td>
						<td>
							<hk:text name="name" clazz="text" maxlength="20" value="${userOtherInfo.name}"/>
							<div class="infowarn" id="_name"></div>
						</td>
					</tr>
					<tr>
						<td align="right">*性别：</td>
						<td>
							<hk:radioarea name="sex" checkedvalue="${loginUser.sex}" forcecheckedvalue="0">
								<hk:radio oid="sex_male" value="<%=User.SEX_MALE %>"/><label for="sex_male">男</label>
								<hk:radio oid="sex_female" value="<%=User.SEX_MALE %>"/><label for="sex_female">女</label>
							</hk:radioarea>
							<div class="infowarn" id="_sex"></div>
						</td>
					</tr>
					<tr>
						<td align="right">*联系电话：</td>
						<td>
							<hk:text name="tel" clazz="text" maxlength="20"/>
							<div>手机号与联系电话须任选一个填写</div>
							<div class="infowarn" id="_tel"></div>
						</td>
					</tr>
					<tr>
						<td align="right">*手机号码：</td>
						<td>
							<hk:text name="mobile" clazz="text" maxlength="20" value="${userOtherInfo.mobile}"/>
							<div>手机号与联系电话须任选一个填写</div>
							<div class="infowarn" id="_mobile"></div>
						</td>
					</tr>
					<tr>
						<td align="right">*E-mail：</td>
						<td>
							<hk:text name="email" clazz="text" maxlength="50" value="${userOtherInfo.email}"/>
							<div class="infowarn" id="_email"></div>
						</td>
					</tr>
					<tr>
						<td align="right">QQ/MSN：</td>
						<td>
							<hk:text name="im" clazz="text" maxlength="50"/>
							<div class="infowarn" id="_im"></div>
						</td>
					</tr>
					<tr>
						<td align="right">*所在城市：</td>
						<td>
							<hk:text name="city" clazz="text" maxlength="50" value="${loginUser.pcity.name}"/>
							<div class="infowarn" id="_city"></div>
						</td>
					</tr>
					<tr>
						<td align="right">报名留言：</td>
						<td>
							<hk:textarea name="msg" style="width:300px;height:100px"/>
							<div class="infowarn" id="_msg"></div>
						</td>
					</tr>
					<tr>
						<td align="right">*验证码：</td>
						<td>
							<hk:text name="imgv" maxlength="4"/>
							<img id="codeimg" style="vertical-align: middle;" src="<%=path %>/pub/epp/imgv.do?v=<%=Math.random() %>"/>
							<a href="javascript:showimg()"><hk:data key="epp.getnewvalidatecode"/></a>
							<div class="infowarn" id="_imgv"></div>
						</td>
					</tr>
					<tr>
						<td align="right"></td>
						<td>
							<hk:submit value="view.submit" clazz="btn" res="true"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript">
var err_code_<%=Err.NICKNAME_ERROR %>={objid:"_name"};
var err_code_<%=Err.NICKNAME_ERROR2 %>={objid:"_name"};
var err_code_<%=Err.EMAIL_ERROR %>={objid:"_email"};
var err_code_<%=Err.EMAIL_ALREADY_EXIST %>={objid:"_email"};
var err_code_<%=Err.MOBILE_ERROR %>={objid:"_mobile"};
var err_code_<%=Err.MOBILE_ALREADY_EXIST %>={objid:"_mobile"};
var err_code_<%=Err.SEX_ERROR %>={objid:"_sex"};
var err_code_<%=Err.CMPORGSTUDYADUSER_NAME_ERROR %>={objid:"_name"};
var err_code_<%=Err.CMPORGSTUDYADUSER_TEL_ERROR %>={objid:"_tel"};
var err_code_<%=Err.CMPORGSTUDYADUSER_TEL_MOBILE_ERROR %>={objid:"_mobile"};
var err_code_<%=Err.CMPORGSTUDYADUSER_IM_ERROR %>={objid:"_im"};
var err_code_<%=Err.CMPORGSTUDYADUSER_CITY_ERROR %>={objid:"_city"};
var err_code_<%=Err.CMPORGSTUDYADUSER_MSG_ERROR %>={objid:"_msg"};
var err_code_<%=Err.IMG_VALIDATE_CODE_ERROR %>={objid:"_imgv"};
function delstudyad(){
	if(window.confirm('确实要删除招生简章？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/org/studyad_del.do?companyId=${companyId}&orgId=${orgId}&adid=${adid}",
			cache:false,
	    	dataType:"html",
			success:function(data){
				tourl('<%=path%>/epp/web/org/studyad.do?companyId=${companyId}&orgId=${orgId}&orgnavId=${orgnavId}');
			}
		});
	}
}
function subbmfrm(frmid){
	showGlass(frmid);
	setHtml('_name','');
	setHtml('_sex','');
	setHtml('_tel','');
	setHtml('_mobile','');
	setHtml('_email','');
	setHtml('_im','');
	setHtml('_city','');
	setHtml('_msg','');
	setHtml('_imgv','');
	return true;
}
function createok(error,msg,v){
	refreshurl();
}
function createerror(error,msg,v){
	setHtml(getoidparam(error),msg);hideGlass();
}
function createerrorlist(json,errorlist){
	var errorcode_arr=errorlist.split(',');
	for(var i=0;i<errorcode_arr.length;i++){
		setHtml(getoidparam(errorcode_arr[i]),getmsgfromlist(errorcode_arr[i],json));
	}
	hideGlass();
}
function showimg(){
	getObj('codeimg').src="<%=path %>/pub/epp/imgv.do?v="+Math.random();
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>
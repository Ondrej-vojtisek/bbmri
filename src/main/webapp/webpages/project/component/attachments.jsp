<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="projectBean" beanclass="cz.bbmri.action.project.ProjectActionBean"/>

<table class="table table-hover table-striped">
           <thead>
           <tr>
               <th><f:message key="cz.bbmri.entities.Attachment.name"/></th>
               <th><f:message key="cz.bbmri.entities.Attachment.unit"/></th>
               <th><f:message key="cz.bbmri.entities.Attachment.importance"/></th>
               <th></th>
           </tr>
           </thead>
           <tbody>

           <c:if test="${empty projectBean.attachments}">
               <tr>
                   <td colspan="4"><f:message key="empty"/></td>
               </tr>
           </c:if>

           <c:forEach items="${projectBean.attachments}" var="attachment" varStatus="loop">
               <tr>
                   <td>${attachment.fileName}</td>
                   <td>${attachment.size}</td>
                   <td><f:message key="AttachmentType.${attachment.attachmentType}"/></td>
                   <td class="action">
                       <security:allowed bean="projectBean" event="downloadAttachment">
                           <div class="tableAction">
                               <s:link beanclass="cz.bbmri.action.project.ProjectActionBean"
                                       event="downloadAttachment"
                                       class="btn btn-info btnMargin">
                                   <s:param name="attachment.id" value="${attachment.id}"/>
                                   <f:message key="download"/>
                               </s:link>
                           </div>
                       </security:allowed>

                       <f:message var="question" key="cz.bbmri.action.project.ProjectActionBean.questionDeleteAttachment"/>

                       <security:allowed bean="projectBean" event="deleteAttachment">
                           <s:form beanclass="${projectBean.name}">
                       <div class="tableAction">
                               <s:submit name="deleteAttachment"
                                         class="btn btn-danger"
                                         onclick="return confirm('${question}')">
                                   <s:param name="attachmentId" value="${attachment.id}"/>
                                   <s:param name="id" value="${projectBean.id}"/>
                               </s:submit>
                                   </div>
                           </s:form>
                       </security:allowed>

                   </td>
               </tr>
           </c:forEach>
           </tbody>
       </table>
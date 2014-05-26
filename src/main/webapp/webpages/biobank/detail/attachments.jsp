<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="attachments">

    <s:layout-component name="body">

        <security:allowed event="addAttachment">
                         <div class="form-actions">

                             <s:link beanclass="cz.bbmri.action.biobank.BiobankAttachmentsActionBean"
                                     event="addAttachment"
                                     class="btn btn-primary">
                                 <s:param name="biobankId" value="${actionBean.biobankId}"/>
                                 <f:message key="cz.bbmri.action.biobank.BiobankAttachmentsActionBean.addAttachment"/>
                             </s:link>

                         </div>
                     </security:allowed>

              <s:layout-render name="/webpages/biobank/detail/attachmentTable.jsp"/>

    </s:layout-component>
</s:layout-render>

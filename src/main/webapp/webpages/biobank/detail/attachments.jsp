<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="attachments">

    <stripes:layout-component name="body">

        <security:allowed event="addAttachment">
                         <div class="form-actions">

                             <stripes:link beanclass="cz.bbmri.action.biobank.BiobankAttachmentsActionBean"
                                     event="addAttachment"
                                     class="btn btn-primary">
                                 <stripes:param name="biobankId" value="${actionBean.biobankId}"/>
                                 <format:message key="cz.bbmri.action.biobank.BiobankAttachmentsActionBean.addAttachment"/>
                             </stripes:link>

                         </div>
                     </security:allowed>

              <stripes:layout-render name="/webpages/biobank/detail/attachmentTable.jsp"/>

    </stripes:layout-component>
</stripes:layout-render>

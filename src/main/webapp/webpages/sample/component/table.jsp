<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean var="sampleSearchActionBean" beanclass="cz.bbmri.action.SampleSearchActionBean"/>

<table class="table table-hover table-striped">
             <stripes:layout-render name="${component.header.sample}"/>

             <tbody>
             <stripes:layout-render name="${component.table.emptyTable}" collection="${sampleSearchActionBean.samples}"/>
             <core:forEach var="item" items="${sampleSearchActionBean.samples}">
                 <tr>
                     <stripes:layout-render name="${component.row.sample}" item="${item}"/>
                     <td class="action">
                                <span class="pull-right">
                                        <div class="tableAction">
                                            <stripes:link beanclass="cz.bbmri.action.SampleActionBean" event="detail"
                                                    class="btn btn-info btnMargin">
                                                <stripes:param name="id" value="${item.id}"/>
                                                <format:message key="detail"/>
                                            </stripes:link>
                                        </div>
                                </span>
                     </td>
                 </tr>
             </core:forEach>
             </tbody>
         </table>

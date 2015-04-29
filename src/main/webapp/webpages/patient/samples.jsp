<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<core:set var="pagination" value="${actionBean.samplePagination}"/>
<stripes:useActionBean var="sampleActionBean" beanclass="cz.bbmri.action.SampleActionBean"/>

<stripes:layout-render name="${component.layout.content}">

    <stripes:layout-component name="body">

        <stripes:layout-render name="${component.menu.patient}" active="samples"/>


        <table class="table table-hover table-striped">
            <stripes:layout-render name="${component.header.sample}" pagination="${pagination}"/>

                <%--Set authBiobankId of AuthotizationActionBean to enable security tag--%>
            <core:set target="${sampleActionBean}" property="authBiobankId" value="${actionBean.patient.biobank.id}"/>

            <tbody>
            <stripes:layout-render name="${component.table.emptyTable}" collection="${pagination.myPageList}"/>
            <core:forEach var="item" items="${pagination.myPageList}">
                <tr>
                    <stripes:layout-render name="${component.row.sample}" item="${item}"/>
                    <td class="action">
                        <span class="pull-right">
                            <div class="tableAction">
                                <security:allowed bean="sampleActionBean" event="detail">
                                    <stripes:link beanclass="cz.bbmri.action.SampleActionBean"
                                                  event="detail"
                                                  class="btn btn-info btnMargin">
                                        <stripes:param name="id" value="${item.id}"/>
                                        <format:message key="detail"/>
                                    </stripes:link>
                                </security:allowed>
                            </div>
                        </span>
                    </td>
                </tr>
            </core:forEach>
            </tbody>
        </table>

        <%--show pagination only if list contains some data--%>
        <core:if test="${not empty pagination.myPageList}">
            <stripes:layout-render name="${component.pager}" pagination="${pagination}"/>
        </core:if>

    </stripes:layout-component>

</stripes:layout-render>
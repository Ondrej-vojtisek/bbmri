<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<core:set var="withdrawPagination" value="${actionBean.withdrawPagination}"/>

<stripes:useActionBean var="withdrawActionBean" beanclass="cz.bbmri.action.WithdrawActionBean"/>

<stripes:layout-render name="${component.layout.content}"
                       primarymenu="biobank">

    <stripes:layout-component name="body">

        <stripes:layout-render name="${component.menu.biobank}" active="withdraws"/>

        <security:allowed event="add" bean="withdrawActionBean">
            <div class="form-actions">

                <stripes:link beanclass="cz.bbmri.action.WithdrawActionBean"
                              event="add"
                              class="btn btn-primary">
                    <stripes:param name="biobankId" value="${actionBean.biobank.id}"/>
                    <format:message key="cz.bbmri.entity.Withdraw.add"/>
                </stripes:link>

            </div>
        </security:allowed>

        <table class="table table-hover table-striped">
            <stripes:layout-render name="${component.header.withdraw}" pagination="${withdrawPagination}"/>

            <tbody>
            <stripes:layout-render name="${component.table.emptyTable}"
                                   collection="${withdrawPagination.myPageList}"/>
            <core:forEach var="item" items="${withdrawPagination.myPageList}">
                <tr>
                    <stripes:layout-render name="${component.row.withdraw}" item="${item}"/>
                    <td class="action">
                        <span class="pull-right">
                        <div class="tableAction">
                            <stripes:link beanclass="cz.bbmri.action.WithdrawActionBean" event="detail"
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

        <%--show pagination only if list contains some data--%>
        <core:if test="${not empty withdrawPagination.myPageList}">
            <stripes:layout-render name="${component.pager}" pagination="${withdrawPagination}"/>
        </core:if>

    </stripes:layout-component>
</stripes:layout-render>
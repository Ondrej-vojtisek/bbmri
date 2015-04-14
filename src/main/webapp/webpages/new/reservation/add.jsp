<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean var="biobankActionBean" beanclass="cz.bbmri.action.BiobankActionBean"/>

<stripes:layout-render name="${component.layout.content}">

    <stripes:layout-component name="body">

        <stripes:form beanclass="cz.bbmri.action.ReservationActionBean">
            <stripes:label for="cz.bbmri.entity.Reservation.selectBiobank"/>
            <table class="table">
                <core:forEach var="item" items="${biobankActionBean.biobanks}">
                    <tr>
                        <td>
                            <stripes:checkbox name="biobanks" value="${item.id}"/>
                        </td>
                        <td>
                                ${item.name}
                        </td>
                    </tr>
                </core:forEach>
            </table>

            <stripes:label for="cz.bbmri.entity.Reservation.specification"/>
            <stripes:textarea class="form-control" name="reservation.specification"/>


            <div class="form-actions">
                <stripes:submit name="confirmAdd" class="btn btn-primary btnMargin"/>
            </div>
        </stripes:form>

    </stripes:layout-component>
</stripes:layout-render>

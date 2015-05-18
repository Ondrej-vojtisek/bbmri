<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<stripes:layout-render name="${component.layout.errorInside}"
                 title="unspecified error">

    <stripes:layout-component name="body">
    </stripes:layout-component>

</stripes:layout-render>
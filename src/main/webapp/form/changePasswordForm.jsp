<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:errors/>
<table>
    <tr>
        <th><s:label for="z1" name="password"/></th>
        <td><s:password id="z1" name="password"/></td>
    </tr>

    <tr>
        <th><s:label for="z2" name="password2"/></th>
        <td><s:password id="z2" name="password2"/></td>
    </tr>
</table>
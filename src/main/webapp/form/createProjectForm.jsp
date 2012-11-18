<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:errors/>

<table>
    <tr>
        <th><s:label for="z1" name="project.name"/></th>
        <td><s:text id="z1" name="project.name"/></td>
    </tr>
     <tr>
        <th><s:label for="z2" name="project.fundingOrganization"/></th>
        <td><s:text id="z2" name="project.fundingOrganization"/></td>
    </tr>
    <tr>
        <td colspan="2">
            <p><s:label for="z3" name="project.description"/></p>
            <s:textarea id="z3" name="project.description"></s:textarea>
        </td>
    </tr>

</table>




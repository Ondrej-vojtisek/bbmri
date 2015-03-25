package cz.bbmri.action;

import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.dao.AttachmentTypeDAO;
import cz.bbmri.entity.AttachmentType;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

import java.util.List;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@UrlBinding("/attachmentType/{$event}/{id}")
public class AttachmentTypeActionBean extends ComponentActionBean {

    @SpringBean
    private AttachmentTypeDAO attachmentTypeDAO;

    public List<AttachmentType> getBiobankAttachmentType() {
        return AttachmentType.getBiobankAttachmentType();
    }

    public List<AttachmentType> getProjectAttachmentType() {
        return AttachmentType.getProjectAttachmentType();
    }

    public List<AttachmentType> getAll(){
        return attachmentTypeDAO.all();
    }

}

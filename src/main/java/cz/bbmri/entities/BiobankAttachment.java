package cz.bbmri.entities;

import cz.bbmri.entities.enumeration.BiobankAttachmentType;

import javax.persistence.*;

/**
 * Attachment of biobank - for instance
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Entity
@Table
public class BiobankAttachment extends Attachment {

     /**
     * Type of attachment - flag to make attachment list more clear
     */
    @Enumerated(EnumType.STRING)
    private BiobankAttachmentType biobankAttachmentType;

    @ManyToOne
    private Biobank biobank;

    public BiobankAttachmentType getBiobankAttachmentType() {
        return biobankAttachmentType;
    }

    public void setBiobankAttachmentType(BiobankAttachmentType biobankAttachmentType) {
        this.biobankAttachmentType = biobankAttachmentType;
    }

    public Biobank getBiobank() {
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

}

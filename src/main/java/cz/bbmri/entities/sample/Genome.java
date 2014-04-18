package cz.bbmri.entities.sample;

import cz.bbmri.entities.Sample;

import javax.persistence.Entity;

/**
 * Specialized type of sample which represents e.g. genome blood
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Entity
public class Genome extends Sample {
}

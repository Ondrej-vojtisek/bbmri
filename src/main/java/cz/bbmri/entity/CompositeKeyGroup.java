package cz.bbmri.entity;

/**
 * Internal extension for database entities with
 * composite primary table key. Additional methods
 * may be implemented in such cases.
 *
 * @author Jan Sochor
 * @version 0.1
 */
public abstract class CompositeKeyGroup {

	// Special definition flag attributes
	private boolean recordSavedFlag = false;

	/**
	 * Changes the definition
	 * flag value into true.
	 */
	public void onSave() {
		recordSavedFlag = true;
	}

	/**
	 * Changes the definition
	 * flag value into true.
	 */
	public void onLoad() {
		recordSavedFlag = true;
	}

	/**
	 * Returns the current value
	 * of the definition flag.
	 *
	 * @return Flag value
	 */
	public boolean isSaved() {
		return recordSavedFlag;
	}

}

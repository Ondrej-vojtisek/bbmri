package cz.bbmri.action.map;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public enum Component {

    /**
     * Contains the instance.
     */
    INSTANCE;

    public SortableHeader getSortableHeader() {
        // Retrieve the instance
        return SortableHeader.INSTANCE;
    }

    public Header getHeader() {
        // Retrieve the instance
        return Header.INSTANCE;
    }

    public Row getRow() {
        // Retrieve the instance
        return Row.INSTANCE;
    }

    public Table getTable() {
        // Retrieve the instance
        return Table.INSTANCE;
    }

    public Ribbon getRibbon() {
        // Retrieve the instance
        return Ribbon.INSTANCE;
    }

    public String getPager() {
        // Retrieve the instance
        return Pager.INSTANCE.getPager();
    }

    public String getDate() {
        // Retrieve the instance
        return cz.bbmri.action.map.Date.INSTANCE.getDate();
    }

    public Layout getLayout() {
        // Retrieve the instance
        return Layout.INSTANCE;
    }

    public Menu getMenu() {
        // Retrieve the instance
        return Menu.INSTANCE;
    }

}

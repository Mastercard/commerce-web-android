package com.us.masterpass.merchantapp.domain.model;

/**
 * Created by Sebastian Farias on 11-10-17.
 * <p>
 * Object used on settings list, and settings detail
 */
public class SettingsVO {

    private String type;
    private String name;
    private String saveOption;
    private boolean selected;
    private String imageName;
    private String description;

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets save option.
     *
     * @return the save option
     */
    public String getSaveOption() {
        return saveOption;
    }

    /**
     * Sets save option.
     *
     * @param saveOption the save option
     */
    public void setSaveOption(String saveOption) {
        this.saveOption = saveOption;
    }

    /**
     * Is selected boolean.
     *
     * @return the boolean
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets selected.
     *
     * @param selected the selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Gets image name.
     *
     * @return the image name
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * Sets image name.
     *
     * @param imageName the image name
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}

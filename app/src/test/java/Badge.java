public class Badge {
    private int badgeID;
    private String description;
    private String name;
    private String icon;

    public int getBadgeID() {
        return badgeID;
    }

    public void setBadgeID(int badgeID) {
        this.badgeID = badgeID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Badge(int badgeID, String description, String name, String icon) {
        this.badgeID = badgeID;
        this.description = description;
        this.name = name;
        this.icon = icon;
    }
}
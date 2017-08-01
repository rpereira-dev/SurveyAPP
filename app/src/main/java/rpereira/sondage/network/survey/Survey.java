package rpereira.sondage.network.survey;

/**
 * Created by Romain on 06/04/2017.
 */

public abstract class Survey {

    /** different survey types */
    public enum SurveyType {
        BINARY(), //yes or no
        MCQ(), //multiple choice questionnary
        PREFERENCES(); //list preferences
    }

    /** the preview image address */
    private String previewImage;

    /** the icon of the user that posted this survey */
    private String iconImage;

    /** the survey title */
    private String title;

    /** the username of the one who posted this survey */
    private String username;

    /** the date when it was posted */
    private String date;

    public Survey() {
    }

    public String getTitle() {
        return (this.title);
    }

    public String getDate() {
        return (date);
    }

    public String getUsername() {
        return (username);
    }

    public String getIconImage() {
        return (iconImage);
    }

    public String getPreviewImage() {
        return (previewImage);
    }
}

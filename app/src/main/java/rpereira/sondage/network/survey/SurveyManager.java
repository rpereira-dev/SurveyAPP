package rpereira.sondage.network.survey;

import java.util.HashMap;

/**
 * Created by Romain on 13/04/2017.
 */

public class SurveyManager {

    /** the hash map of loaded surveys: HashMap<UID, SURVEY> */
    private HashMap<String, Survey> surveys;

    public SurveyManager() {
        this.surveys = new HashMap<String, Survey>();
    }
}

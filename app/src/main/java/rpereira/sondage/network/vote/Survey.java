package rpereira.sondage.network.vote;

/**
 * Created by Romain on 06/04/2017.
 */

public abstract class Survey {

    public enum SurveyID {
        BINARY(), //yes or no
        MCQ(), //multiple choice questionnary
        PREFERENCES(); //list preferences
    }
}

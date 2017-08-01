package rpereira.sondage.activities.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import rpereira.sondage.R;
import rpereira.sondage.network.survey.Survey;
import rpereira.sondage.util.Logger;
import rpereira.sondage.util.ViewUtils;

public class VotePreview extends RelativeLayout {

    /**  the survey UID */
    private Survey survey;

    /** the preview image address */
    private ImageView previewImage;

    /** the icon of the user that posted this survey */
    private ImageView iconImage;

    /** the survey title */
    private TextView title;

    /** the username and the date */
    private TextView username_and_date;

    public VotePreview(Context context) {
        super(context);
        this.init(null, 0);
    }

    public VotePreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(attrs, 0);
    }

    public VotePreview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        //instantiate components
        this.previewImage = new ImageView(this.getContext());
        this.iconImage = new ImageView(this.getContext());
        this.title = new TextView(this.getContext());
        this.username_and_date = new TextView(this.getContext());

        //add them to the layout
        this.addView(this.previewImage);
        this.addView(this.iconImage);
        this.addView(this.title);
        this.addView(this.username_and_date);

        //generate view ids
        ViewUtils.generateViewId(this.previewImage, this.iconImage, this.title, this.username_and_date);

        this.previewImage.setImageResource(R.drawable.com_facebook_auth_dialog_background);
        this.iconImage.setImageResource(R.drawable.ic_action_swallow_black);
        this.title.setText("TITRE DU SONDAGE ICI");
        this.username_and_date.setText("Romain PEREIRA 13-02-2012");

        int dp2 = ViewUtils.convertDPToPX(this.getContext(), 2.0f);
        int dp8 = ViewUtils.convertDPToPX(this.getContext(), 8.0f);

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        this.previewImage.setLayoutParams(layoutParams);

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams.addRule(RelativeLayout.BELOW, this.previewImage.getId());
        layoutParams.setMargins(dp8, dp8, 0, 0);
        this.iconImage.setLayoutParams(layoutParams);

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.BELOW, this.previewImage.getId());
        layoutParams.addRule(RelativeLayout.RIGHT_OF, this.iconImage.getId());
        layoutParams.setMargins(dp8, dp8, 0, 0);
        this.title.setLayoutParams(layoutParams);

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.BELOW, this.title.getId());
        layoutParams.addRule(RelativeLayout.RIGHT_OF, this.iconImage.getId());
        layoutParams.setMargins(dp8, dp2, 0, 0);
        this.username_and_date.setLayoutParams(layoutParams);
    }
}

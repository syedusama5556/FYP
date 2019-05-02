package usama.utech.firebasepractice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class PostYourTravel extends AppCompatActivity {

    private Toolbar myToolbar;
    private LinearLayout mainLayout;
    private LinearLayout offerLiftLayout;
    private ImageView routeArrow;
    private LinearLayout startPointLayout;
    private TextView startPointTx;
    private TextView offerLiftStartName;
    private TextView offerLiftStartAddress;
    private LinearLayout endPointLayout;
    private TextView endPointTx;
    private TextView offerLiftEndName;
    private TextView offerLiftEndAddress;
    private LinearLayout daysLayout;
    private Switch regularTripsw;
    private LinearLayout offerdaysLayout;
    private ToggleButton sat;
    private ToggleButton sun;
    private ToggleButton mon;
    private ToggleButton tue;
    private ToggleButton wed;
    private ToggleButton thu;
    private ToggleButton fri;
    private LinearLayout startDateTimeLayout;
    private TextView offerdeparturedate;
    private TextView offerdeparturedateHeader;
    private TextView offerdeparturedateDetail;
    private Switch offerRoundTripSw;
    private LinearLayout returnTimeLayout;
    private TextView offerReturnTimetx;
    private TextView offerreturndateHeader;
    private TextView offerreturndateDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__your__travel);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        offerLiftLayout = (LinearLayout) findViewById(R.id.offer_lift_layout);
        routeArrow = (ImageView) findViewById(R.id.route_arrow);
        startPointLayout = (LinearLayout) findViewById(R.id.start_point_layout);
        startPointTx = (TextView) findViewById(R.id.startPointTx);
        offerLiftStartName = (TextView) findViewById(R.id.offer_lift_start_name);
        offerLiftStartAddress = (TextView) findViewById(R.id.offer_lift_start_address);
        endPointLayout = (LinearLayout) findViewById(R.id.end_point_layout);
        endPointTx = (TextView) findViewById(R.id.endPointTx);
        offerLiftEndName = (TextView) findViewById(R.id.offer_lift_end_name);
        offerLiftEndAddress = (TextView) findViewById(R.id.offer_lift_end_address);
        daysLayout = (LinearLayout) findViewById(R.id.days_layout);
        regularTripsw = (Switch) findViewById(R.id.regularTripsw);
        offerRoundTripSw = (Switch) findViewById(R.id.offerRoundTripSw);
        offerdaysLayout = (LinearLayout) findViewById(R.id.offerdaysLayout);
        sat = (ToggleButton) findViewById(R.id.Sat);
        sun = (ToggleButton) findViewById(R.id.Sun);
        mon = (ToggleButton) findViewById(R.id.Mon);
        tue = (ToggleButton) findViewById(R.id.Tue);
        wed = (ToggleButton) findViewById(R.id.Wed);
        thu = (ToggleButton) findViewById(R.id.Thu);
        fri = (ToggleButton) findViewById(R.id.Fri);
        startDateTimeLayout = (LinearLayout) findViewById(R.id.startDateTimeLayout);
        offerdeparturedate = (TextView) findViewById(R.id.offerdeparturedate);
        offerdeparturedateHeader = (TextView) findViewById(R.id.offerdeparturedate_header);
        offerdeparturedateDetail = (TextView) findViewById(R.id.offerdeparturedate_detail);

        returnTimeLayout = (LinearLayout) findViewById(R.id.return_time_layout);
        offerReturnTimetx = (TextView) findViewById(R.id.offerReturnTimetx);
        offerreturndateHeader = (TextView) findViewById(R.id.offerreturndate_header);
        offerreturndateDetail = (TextView) findViewById(R.id.offerreturndate_detail);

        findViewById(R.id.offerLiftConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //switches
        regularTripsw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b){
                    offerdaysLayout.setVisibility(View.VISIBLE);
                }
                else {
                    offerdaysLayout.setVisibility(View.GONE);

                }

            }
        });

        offerRoundTripSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b){
                    returnTimeLayout.setVisibility(View.VISIBLE);
                }
                else {
                    returnTimeLayout.setVisibility(View.GONE);

                }

            }
        });

        //textview to select location
        startPointTx.setSelected(true);
        startPointTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),Select_location_for_Post.class);

                intent.putExtra("start","start");

                startActivityForResult(intent,2);

            }
        });
        endPointTx.setSelected(true);
        endPointTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),Select_location_for_Post.class);

                intent.putExtra("start","end");

                startActivityForResult(intent,3);

            }
        });
        //toggle btns

toggleClickListners();
    }

    public void toggleClickListners(){
        sat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    sat.setTextColor(getResources().getColor(R.color.white));
                }
                else{

                    sat.setTextColor(getResources().getColor(R.color.black_text_color));

                }

            }
        });
        sun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    sun.setTextColor(getResources().getColor(R.color.white));
                }
                else{

                    sun.setTextColor(getResources().getColor(R.color.black_text_color));

                }
            }
        });
        mon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mon.setTextColor(getResources().getColor(R.color.white));
                }
                else{

                    mon.setTextColor(getResources().getColor(R.color.black_text_color));

                }
            }
        });
        tue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    tue.setTextColor(getResources().getColor(R.color.white));
                }
                else{

                    tue.setTextColor(getResources().getColor(R.color.black_text_color));

                }
            }
        });
        wed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    wed.setTextColor(getResources().getColor(R.color.white));
                }
                else{

                    wed.setTextColor(getResources().getColor(R.color.black_text_color));

                }
            }
        });
        thu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    thu.setTextColor(getResources().getColor(R.color.white));
                }
                else{

                    thu.setTextColor(getResources().getColor(R.color.black_text_color));

                }
            }
        });
        fri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    fri.setTextColor(getResources().getColor(R.color.white));
                }
                else{

                    fri.setTextColor(getResources().getColor(R.color.black_text_color));

                }
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2){

           String loc =  data.getStringExtra("locationtxt");

           startPointTx.setText(loc+"");
        }
        if (requestCode == 3){
            String loc =  data.getStringExtra("locationtxt");

            endPointTx.setText(loc+"");
        }
    }
}

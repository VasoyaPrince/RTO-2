package anon.rtoinfo.rtovehical.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import anon.rtoinfo.rtovehical.Adapter.RTOSymbolAdapter;
import anon.rtoinfo.rtovehical.R;

import java.util.Arrays;
import java.util.Collections;

public class RTOSymbolsDetailActivity extends AppCompatActivity {
    int[] Mandatory_images = {R.drawable.mandatory_1, R.drawable.mandatory_2, R.drawable.mandatory_3, R.drawable.mandatory_4, R.drawable.mandatory_5, R.drawable.mandatory_6, R.drawable.mandatory_7, R.drawable.mandatory_8, R.drawable.mandatory_9, R.drawable.mandatory_10, R.drawable.mandatory_11, R.drawable.mandatory_12, R.drawable.mandatory_13, R.drawable.mandatory_14, R.drawable.mandatory_15, R.drawable.mandatory_16, R.drawable.mandatory_17, R.drawable.mandatory_18, R.drawable.mandatory_19, R.drawable.mandatory_20, R.drawable.mandatory_21, R.drawable.mandatory_22, R.drawable.mandatory_23, R.drawable.mandatory_24, R.drawable.mandatory_25};
    ImageView back_btn;
    int[] cautionary_images = {R.drawable.cautionary_1, R.drawable.cautionary_2, R.drawable.cautionary_3, R.drawable.cautionary_4, R.drawable.cautionary_5, R.drawable.cautionary_6, R.drawable.cautionary_7, R.drawable.cautionary_8, R.drawable.cautionary_9, R.drawable.cautionary_10, R.drawable.cautionary_11, R.drawable.cautionary_12, R.drawable.cautionary_13, R.drawable.cautionary_14, R.drawable.cautionary_15, R.drawable.cautionary_16, R.drawable.cautionary_17, R.drawable.cautionary_18, R.drawable.cautionary_19, R.drawable.cautionary_20, R.drawable.cautionary_21};
    String[] cautionary_text = {"Right Hair Pin Bend", "Left Hair Pin Bend", "Right Hand Curve", "Left Hand Curve", "Left Reverse Bend", "Right Reverse Bend", "Side Road Left", "Side Road Right", "T-intersection", "Major Road Ahead", "Staggered Intersection", "Staggered Intersection", "Cross Road Ahead", "Right Y-Intersection", "Left Y-Intersection", "Two Way Traffic", "Y-Intersection", "Roundabout Ahead", "Gap In Median", "Pedestrian Crossing", "Narrow Bridge Ahead"};
    int[] driving_rules_images = {R.drawable.driver_hand_1, R.drawable.driver_hand_4, R.drawable.driver_hand_2, R.drawable.driver_hand_3, R.drawable.driver_hand_5};
    String[] driving_rules_text = {"I intend to move  in to the left or turn left.", "I intend to slow down.", "I intend to move out of the right or changing the lane or turn right.", "I intend  to stop.", "Indicating the car following you to over take."};
    int[] informatory_images = {R.drawable.informatory_1, R.drawable.informatory_2, R.drawable.informatory_3, R.drawable.informatory_4, R.drawable.informatory_5, R.drawable.informatory_6, R.drawable.informatory_7, R.drawable.informatory_8, R.drawable.informatory_9, R.drawable.informatory_10, R.drawable.informatory_11, R.drawable.informatory_12, R.drawable.informatory_13, R.drawable.informatory_14};
    String[] informatory_text = {"Public telephone", "Petrol pump", "Hospital", "Eating Place", "Light Refreshment", "No Through Road", "No Through Side Road", "First Aid Post", "Park This Side", "Parking Both Sides", "Parking Lot Bikes", "Parking Lot Cycles", "Parking Lot Taxis", "Parking Lot Auto"};
    String[] mandatory_text = {"Speed Limit", " No Entry", "One Way Sign, Entry allowed", "Right Turn Prohibited", "Left Turn Prohibited", "One Way, Entry Prohibited", "Load Limit", "Vehicle Prohibited in Both Directions", "Horn Prohibited", "U-Turn Prohibited", "Overtaking Prohibited", "No Parking", "Width Limit", "Height Limit", "No Stopping or Standing", "Restriction Ends", "Stop", "Compulsory Turn Right", "Compulsory Turn Left", "Compulsory Ahead Only", "Compulsory Keep Left", "Compulsory Ahead or Turn Left", "Compulsory Ahead or Turn Right", "Compulsory Sound Horn", "Give Way"};
    int[] road_images = {R.drawable.road_5, R.drawable.road_2, R.drawable.road_1, R.drawable.road_3, R.drawable.road_4, R.drawable.road_6, R.drawable.road_7, R.drawable.road_8, R.drawable.road_9, R.drawable.road_10, R.drawable.road_11, R.drawable.road_12};
    String[] road_text = {"Double white/yellow lines:\nUsed where visibility is restricted in both directions. Neither stream of traffic is allowed to cross the lines.\n", "Lane line and broken centre line", "Centre line marking for a two lane road", "Centre barrier line marking for a four lane road", "Centre barrier line marking for a six lane road", "Combination of solid and broken lines:\nIf the line on your side is broken, you may cross or straddle it. Overtake - but only if it is safe to do so.\nIf the line on your side is continuous you must not cross or straddle it.", "Stop line:\nA stop line is a single solid transverse line painted before the intersecting edge of the road junction/ intersection.", "Give Way Line:\nThe give way line is usually a double dotted line marked transversely at junctions.", "Border or Edge Lines:\nThese are continuous lines at the edge of the carriageway and mark the limits of the main carriageway upto which a driver can safely venture.", "Parking Prohibited Lines:\nA solid continuous yellow line painted on the kerb or edge of the carriageway along with a \"No-parking\" sign indicates the extent of no-parking area.", "Yellow Box Junctions or Keep Clear:\nThese are yellow crossed diagonal lines within the box. The vehicles should cross it only if they have a clear space available ahead of the yellow box. In this marked area vehicles must not stop even briefly.", "Pedestrian Crossings\nThese are alternate black and white stripes painted parallel to the road generally known as zebra crossing. Pedestrians must cross only at the point where these lines are provided and when the signal is in their favour at controlled crossings.You must stop and give way to pedestrians at these crossings. Pedestrian crossings are marked to facilitate and give the right of way to pedestrians."};
    RTOSymbolAdapter rtoSymbolAdapter;
    RecyclerView rto_list_recycler;
    ImageView symbole_logo;
    TextView title_text;
    int[] traffic_police_images = {R.drawable.trafic_police_1, R.drawable.trafic_police_2, R.drawable.trafic_police_3, R.drawable.trafic_police_4, R.drawable.trafic_police_5, R.drawable.trafic_police_6, R.drawable.trafic_police_7, R.drawable.trafic_police_8, R.drawable.trafic_police_9};
    String[] traffic_police_text = {"To stop vehicles approaching simultaneously from front and behind", "To allow vehicles coming from right and turing right by stopping traffic approaching from the left", "To beckon the vehicles approaching from right", "To beckon the vehicles approaching from left", "To stop vehicles approaching from left and waiting to turn right", "To stop vehicles coming from front", "To stop vehicles approaching from behind", "To stop vehicles approaching from right to allow vehicles from the left to turn right", "Warning signal closing all vehicles"};

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_rtosymbols_detail);

        FrameLayout adMobView = (FrameLayout) findViewById(R.id.adMobView);
//        showFBBanner(adMobView);
        FrameLayout adMobView1 = (FrameLayout) findViewById(R.id.adMobView1);
//        showBanner(adMobView1);

        this.back_btn = (ImageView) findViewById(R.id.back_btn);
        this.symbole_logo = (ImageView) findViewById(R.id.symbole_logo);
        this.back_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RTOSymbolsDetailActivity.this.onBackPressed();
            }
        });
        this.title_text = (TextView) findViewById(R.id.tv_title);
        this.title_text.setText(getIntent().getStringExtra("passvalue"));
        symbole_logo.setImageResource(getIntent().getIntExtra("passlogo", 0));
        if (this.title_text.getText().equals("Mandatory")) {
            Collections.reverse(Arrays.asList(this.mandatory_text));
            changeorder(this.Mandatory_images);
            this.rtoSymbolAdapter = new RTOSymbolAdapter(this.mandatory_text, this.Mandatory_images);
        } else if (this.title_text.getText().equals("Cautionary")) {
            Collections.reverse(Arrays.asList(this.cautionary_text));
            changeorder(this.cautionary_images);
            this.rtoSymbolAdapter = new RTOSymbolAdapter(this.cautionary_text, this.cautionary_images);
        } else if (this.title_text.getText().equals("Informatory")) {
            Collections.reverse(Arrays.asList(this.informatory_text));
            changeorder(this.informatory_images);
            this.rtoSymbolAdapter = new RTOSymbolAdapter(this.informatory_text, this.informatory_images);
        } else if (this.title_text.getText().equals("Road & Signals")) {
            this.rtoSymbolAdapter = new RTOSymbolAdapter(this.road_text, this.road_images);
        } else if (this.title_text.getText().equals("Driving Rules")) {
            this.rtoSymbolAdapter = new RTOSymbolAdapter(this.driving_rules_text, this.driving_rules_images);
        } else if (this.title_text.getText().equals("Traffic Police Signals")) {
            Collections.reverse(Arrays.asList(this.traffic_police_text));
            changeorder(this.traffic_police_images);
            this.rtoSymbolAdapter = new RTOSymbolAdapter(this.traffic_police_text, this.traffic_police_images);
        }
        this.rto_list_recycler = (RecyclerView) findViewById(R.id.rto_list_recycler);
        this.rto_list_recycler.setLayoutManager(new LinearLayoutManager(this));
        this.rto_list_recycler.setAdapter(this.rtoSymbolAdapter);
    }

    public void changeorder(int[] iArr) {
        for (int i = 0; i < iArr.length / 2; i++) {
            int i2 = iArr[i];
            iArr[i] = iArr[(iArr.length - i) - 1];
            iArr[(iArr.length - i) - 1] = i2;
        }
    }
}

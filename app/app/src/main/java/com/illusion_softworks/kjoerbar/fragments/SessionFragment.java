package com.illusion_softworks.kjoerbar.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.calculation.Calculations;
import com.illusion_softworks.kjoerbar.datahandler.UserDataHandler;
import com.illusion_softworks.kjoerbar.model.AlcoholUnit;
import com.illusion_softworks.kjoerbar.model.Beverage;
import com.illusion_softworks.kjoerbar.model.Session;
import com.illusion_softworks.kjoerbar.model.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SessionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SessionFragment extends Fragment {
    private static final User user = new User(100, 90, 20, "Male", "Geir");
    Session session;
    private static final long HOUR_IN_MS = 0; // 1800000
    private CountDownTimer countDownTimer;
    TextView textTimer, textCurrentPerMill, textCurrentTime;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private BottomNavigationView bottomnavigation;
    private MaterialButton addAlcoholUnitButton, removeAlcoholUnitButton;

    public SessionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SessionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SessionFragment newInstance(String param1, String param2) {
        SessionFragment fragment = new SessionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_session, container, false);
        requireActivity().setTitle(getString(R.string.session));

        textTimer = view.findViewById(R.id.textTimer);
        textCurrentPerMill = view.findViewById(R.id.textCurrentPerMill);
        textCurrentTime = view.findViewById(R.id.textCurrentTime);

        //Log.d("USER", user.toString());
        Map<String, Object> mapUser = new HashMap<>();
        /*mapUser.put("weight", user.getWeight());
        mapUser.put("height", user.getWeight());
        mapUser.put("gender", user.getGender());
        mapUser.put("age", user.getAge());
        mapUser.put("username", user.getUsername());*/

        if (user.getCurrentSession() != null)
            updateCountDownTimer(view);

        addAlcoholUnitButton = view.findViewById(R.id.add_beverage_button);
        addAlcoholUnitButton.setOnClickListener(view1 -> {
            if (user.getCurrentSession() == null) {
                session = new Session(user.getWeight(), user.getGender());
                user.setCurrentSession(session);
                mapUser.put("currentSession", session);
                user.getCurrentSession().addAlcoholUnit(new AlcoholUnit(new Beverage("Grevens Pære", "Hansa", "Cider", 0.5, 4.7)));
            } else {
                user.getCurrentSession().addAlcoholUnit(new AlcoholUnit(new Beverage("Grevens Pære", "Hansa", "Cider", 0.5, 4.7)));
                Log.d("calculateTimeUntilSober", String.valueOf(Calculations.calculateTimeUntilSober(user.getCurrentSession().getCurrentPerMill())));

            }
            updateCountDownTimer(view);
            UserDataHandler.updateUserOnFireStore(mapUser);
        });

        removeAlcoholUnitButton = view.findViewById(R.id.remove_beverage_button);
        removeAlcoholUnitButton.setOnClickListener(view1 -> {
            if (user.getCurrentSession().getAlcoholUnits().size() > 0) {
                AlcoholUnit alcoholUnit = user.getCurrentSession().getAlcoholUnits().get(user.getCurrentSession().getAlcoholUnits().size()-1);
                user.getCurrentSession().removeAlcoholUnit(alcoholUnit);
                user.getCurrentSession().setMaxPerMill(user.getCurrentSession().getMaxPerMill() - Calculations.calculatePerMillPerUnit(user, alcoholUnit.getBeverage(), 0));
                updateCountDownTimer(view);
            }
            UserDataHandler.updateUserOnFireStore(mapUser);
        });

        requireActivity().setTitle(getString(R.string.session));
        return view;
    }

    private void updateCountDownTimer(View view) {
        updatePerMill();
        long countDownPeriod = Calculations.calculateTimeUntilSober(user.getCurrentSession().getCurrentPerMill()) + HOUR_IN_MS;

        if (countDownTimer != null)
            countDownTimer.cancel();

        countDownTimer = new CountDownTimer(countDownPeriod, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long millisBetween = ChronoUnit.MILLIS.between(user.getCurrentSession().getStartDateTime(), LocalDateTime.now());

                textTimer.setText(String.format(Locale.ENGLISH,
                        "%s: %02d:%02d:%02d",
                        view.getContext().getString(R.string.time_left),
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

                textCurrentPerMill.setText(String.format(Locale.ENGLISH, "%s: %.3f", view.getContext().getString(R.string.current_per_mill), user.getCurrentSession().getCurrentPerMill()));

                // millisUntilFinished -= HOUR_IN_MS;
                textCurrentTime.setText(String.format(Locale.ENGLISH,
                        "%s: %02d:%02d:%02d",
                        view.getContext().getString(R.string.time_elapsed),
                        TimeUnit.MILLISECONDS.toHours(millisBetween),
                        TimeUnit.MILLISECONDS.toMinutes(millisBetween) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisBetween)),
                        TimeUnit.MILLISECONDS.toSeconds(millisBetween) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisBetween))));

                updatePerMill();
            }

            @Override
            public void onFinish() {
                textTimer.setText("done!");
                user.getCurrentSession().setEndDateTime(LocalDateTime.now());
                UserDataHandler.addSessionToHistory(session);
                user.setCurrentSession(null);
            }
        }.start();
    }

    public void updatePerMill() {
        user.getCurrentSession().setCurrentPerMill(Calculations.calculateCurrentPerMill(user.getCurrentSession().getAlcoholUnits(), user, LocalDateTime.now()));
        Log.d("currentPerMill", String.valueOf(user.getCurrentSession().getCurrentPerMill()));

        user.getCurrentSession().setMaxPerMill(Calculations.calculateMaxPerMill(user.getCurrentSession().getMaxPerMill(),user.getCurrentSession().getCurrentPerMill()));
    }
}
package com.illusion_softworks.kjoerbar.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.adapter.BeverageRecyclerAdapter;
import com.illusion_softworks.kjoerbar.calculation.Calculations;
import com.illusion_softworks.kjoerbar.datahandler.UserDataHandler;
import com.illusion_softworks.kjoerbar.interfaces.OnItemClickListener;
import com.illusion_softworks.kjoerbar.model.AlcoholUnit;
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
 * create an instance of this fragment.
 */
public class SessionFragment extends Fragment implements OnItemClickListener {
    private static final User user = new User(115, 188, 20, "Male", "Geir");

    private static Session session;
    private static CountDownTimer countDownTimer;
    private static Map<String, Object> mapUser;
    private BeverageRecyclerAdapter adapter;
    private TextView textTimer, textCurrentPerMill, textCurrentTime;
    private View view;
    private RecyclerView recyclerView;
    private BottomNavigationView bottomnavigation;
    private MaterialButton addAlcoholUnitButton, removeAlcoholUnitButton;

    public SessionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static User getUser() {
        return user;
    }

    public static void startNewSession() {
        if (user.getCurrentSession() == null) {
            session = new Session(user.getWeight(), user.getGender());
            user.setCurrentSession(session);
            mapUser.put("currentSession", session);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_session, container, false);
        requireActivity().setTitle(getString(R.string.session));
        mapUser = new HashMap<>();

        mapUser.put("weight", user.getWeight());
        mapUser.put("height", user.getHeight());
        setUpViews();
        setUpButtons();
        updateCountdown();
        return view;
    }

    private void setUpButtons() {
        addAlcoholUnitButton = view.findViewById(R.id.add_beverage_button);
        addAlcoholUnitButton.setOnClickListener(view -> {
            Navigation.findNavController(requireActivity(), R.id.nav_host).navigate(R.id.action_sessionFragment_to_addBeverageFragment);
        });

        removeAlcoholUnitButton = view.findViewById(R.id.remove_beverage_button);
        removeAlcoholUnitButton.setOnClickListener(view1 -> {
            if (user.getCurrentSession().getAlcoholUnits() != null) {
                if (user.getCurrentSession().getAlcoholUnits().size() > 0) {
                    AlcoholUnit alcoholUnit = user.getCurrentSession().getAlcoholUnits().get(user.getCurrentSession().getAlcoholUnits().size() - 1);
                    user.getCurrentSession().removeAlcoholUnit(alcoholUnit);
                    user.getCurrentSession().setMaxPerMill(user.getCurrentSession().getMaxPerMill() - Calculations.calculatePerMillPerUnit(user, alcoholUnit.getBeverage(), 0));
                    updateCountDownTimer();
                    UserDataHandler.updateUserOnFireStore(mapUser);
                    setupRecyclerView();
                }
            }
        });
    }

    private void setUpViews() {
        textTimer = view.findViewById(R.id.textTimer);
        textCurrentPerMill = view.findViewById(R.id.textCurrentPerMill);
        textCurrentTime = view.findViewById(R.id.textCurrentTime);
        recyclerView = view.findViewById(R.id.beverageRecyclerView);
    }

    private void setupRecyclerView() {
        adapter = new BeverageRecyclerAdapter(view.getContext(), user.getCurrentSession().getBeverages(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        // Maybe handle what part of the beverage entry was clicked here?
        Log.d("TAG", "onItemClick: " + user.getCurrentSession().getBeverages().get(position));
    }

    private void updateCountdown() {
        if (user.getCurrentSession() != null) {
            if (user.getCurrentSession().getAlcoholUnits().size() > 0) {
                mapUser.put("currentSession", session);
                UserDataHandler.updateUserOnFireStore(mapUser);
                updateCountDownTimer();
                setupRecyclerView();
            }
        }
    }

    private void updateCountDownTimer() {
        if (countDownTimer != null)
            countDownTimer.cancel();

        updatePerMill();
        long countDownPeriod = Calculations.calculateTimeUntilSober(user.getCurrentSession().getCurrentPerMill());

        Log.d("TAG", String.format(Locale.ENGLISH,
                "%s: %02d:%02d:%02d",
                view.getContext().getString(R.string.time_left),
                TimeUnit.MILLISECONDS.toHours(countDownPeriod),
                TimeUnit.MILLISECONDS.toMinutes(countDownPeriod) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(countDownPeriod)),
                TimeUnit.MILLISECONDS.toSeconds(countDownPeriod) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(countDownPeriod))));

        countDownTimer = new CountDownTimer(countDownPeriod, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long millisBetween = ChronoUnit.MILLIS.between(user.getCurrentSession().getStartDateTime(), LocalDateTime.now());
                Log.d("UserTick", user.getCurrentSession().toString());

                Log.d("millisUntilFinished", String.format(Locale.ENGLISH,
                        "%s: %02d:%02d:%02d",
                        view.getContext().getString(R.string.time_left),
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

                textTimer.setText(String.format(Locale.ENGLISH,
                        "%s: %02d:%02d:%02d",
                        view.getContext().getString(R.string.time_left),
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

                textCurrentPerMill.setText(String.format(Locale.ENGLISH, "%s: %.3f", view.getContext().getString(R.string.current_per_mill), user.getCurrentSession().getCurrentPerMill()));

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
                textTimer.setText(R.string.you_are_sober);
                textCurrentPerMill.setText(view.getContext().getString(R.string.current_per_mill_format));
                confirmFinishDialog();
            }
        }.start();
    }

    private void confirmFinishDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("You are sober. \nDo you want to end and save the session?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        user.getCurrentSession().setEndDateTime(LocalDateTime.now());
                        UserDataHandler.addSessionToHistory(session);
                        user.setCurrentSession(null);
                        Toast.makeText(getContext(),
                                "The session was saved", Toast.LENGTH_SHORT)
                                .show();
                    }
                });

        builder1.setNegativeButton(
                "Continue drinking",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void updatePerMill() {
        user.getCurrentSession().setCurrentPerMill(Calculations.calculateCurrentPerMill(user.getCurrentSession().getAlcoholUnits(), user, LocalDateTime.now()));
        Log.d("currentPerMill", String.valueOf(user.getCurrentSession().getCurrentPerMill()));
        user.getCurrentSession().setMaxPerMill(Calculations.calculateMaxPerMill(user.getCurrentSession().getMaxPerMill(), user.getCurrentSession().getCurrentPerMill()));
    }
}
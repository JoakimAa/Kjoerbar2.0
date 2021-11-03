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

import com.google.android.material.button.MaterialButton;
import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.adapter.BeverageInListRecyclerAdapter;
import com.illusion_softworks.kjoerbar.calculation.Calculations;
import com.illusion_softworks.kjoerbar.datahandler.UserDataHandler;
import com.illusion_softworks.kjoerbar.interfaces.OnItemClickListener;
import com.illusion_softworks.kjoerbar.model.AlcoholUnit;
import com.illusion_softworks.kjoerbar.model.Beverage;
import com.illusion_softworks.kjoerbar.model.Session;
import com.illusion_softworks.kjoerbar.model.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
    private static final ArrayList<AlcoholUnit> alcoholUnits = new ArrayList<>();
    private static RecyclerView recyclerView;
    private static boolean isBeverageAdded = false;
    private TextView textTimer, textCurrentPerMill, textCurrentTime;
    private View view;
    private BeverageInListRecyclerAdapter adapter;

    public SessionFragment() {
        // Required empty public constructor
    }

    public static void startNewSession() {
        if (session == null) {
            session = new Session(user.getWeight(), user.getGender());
        }
    }

    public static void addBeverage(Beverage beverage) {
        alcoholUnits.add(new AlcoholUnit(beverage));
        isBeverageAdded = true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setUpViews() {
        textTimer = view.findViewById(R.id.textTimer);
        textCurrentPerMill = view.findViewById(R.id.textCurrentPerMill);
        textCurrentTime = view.findViewById(R.id.textCurrentTime);
        recyclerView = view.findViewById(R.id.beverageRecyclerView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /*TODO: REMOVE THIS*//*
        UserDataHandler.getSessionHistory();*/

        view = inflater.inflate(R.layout.fragment_session, container, false);
        requireActivity().setTitle(getString(R.string.session));
        mapUser = new HashMap<>();

        mapUser.put("weight", user.getWeight());
        mapUser.put("height", user.getHeight());
        mapUser.put("gender", user.getGender());
        mapUser.put("username", user.getUsername());
        mapUser.put("age", user.getAge());

        setUpViews();
        setUpButtons();
        updateCountdown();
        setupRecyclerView();
        notifyAdapterAfterAddedBeverage();
        return view;
    }

    private void notifyAdapterAfterAddedBeverage() {
        if (isBeverageAdded) {
            adapter.notifyItemInserted(alcoholUnits.size() - 1);
            isBeverageAdded = false;
        }
    }

    private void setUpButtons() {
        MaterialButton addAlcoholUnitButton = view.findViewById(R.id.add_beverage_button);
        addAlcoholUnitButton.setOnClickListener(this::navigateToAddBeverageFragment);

        MaterialButton removeAlcoholUnitButton = view.findViewById(R.id.remove_beverage_button);
        removeAlcoholUnitButton.setOnClickListener(view1 -> {
            if (alcoholUnits.size() > 0) {
                AlcoholUnit alcoholUnit = alcoholUnits.get(alcoholUnits.size() - 1);
                alcoholUnits.remove(alcoholUnit);
                session.setMaxPerMill(session.getMaxPerMill() - Calculations.calculatePerMillPerUnit(user, alcoholUnit.getBeverage(), 0));
                updateCountDownTimer();
                UserDataHandler.updateUserOnFireStore(mapUser);
                adapter.notifyItemRemoved(alcoholUnits.size());
            }
        });
    }

    public void setupRecyclerView() {
        adapter = new BeverageInListRecyclerAdapter(view.getContext(), alcoholUnits, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void updateCountDownTimer() {
        if (countDownTimer != null)
            countDownTimer.cancel();

        updatePerMill();
        long countDownPeriod = Calculations.calculateTimeUntilSober(session.getCurrentPerMill());

        Log.d("TAG", String.format(Locale.ENGLISH,
                "%s: %02d:%02d:%02d",
                view.getContext().getString(R.string.time_left),
                TimeUnit.MILLISECONDS.toHours(countDownPeriod),
                TimeUnit.MILLISECONDS.toMinutes(countDownPeriod) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(countDownPeriod)),
                TimeUnit.MILLISECONDS.toSeconds(countDownPeriod) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(countDownPeriod))));

        countDownTimer = new CountDownTimer(countDownPeriod, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long millisBetween = ChronoUnit.MILLIS.between(session.getStartDateTime(), LocalDateTime.now());

                formatToHours(millisUntilFinished, textTimer, R.string.time_left);
                textCurrentPerMill.setText(String.format(Locale.ENGLISH, "%s: %.3f", view.getContext().getString(R.string.current_per_mill), session.getCurrentPerMill()));
                formatToHours(millisBetween, textCurrentTime, R.string.time_elapsed);

                Log.d("UserTick", session.toString());

                Log.d("millisUntilFinished", String.format(Locale.ENGLISH,
                        "%s: %02d:%02d:%02d",
                        view.getContext().getString(R.string.time_left),
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

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

    private void formatToHours(long millisUntilFinished, TextView textTimer, int p) {
        textTimer.setText(String.format(Locale.ENGLISH,
                "%s: %02d:%02d:%02d",
                view.getContext().getString(p),
                TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
    }

    private void updateCountdown() {
        if (session != null) {
            updateCountDownTimer();
        }
    }

    private void confirmFinishDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("You are sober. \nDo you want to end and save the session?")
                .setCancelable(true)
                .setPositiveButton(
                        "Yes",
                        this::createPositivButton)
                .setNegativeButton(
                        "Continue drinking",
                        (dialog, id) -> dialog.cancel());

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void updatePerMill() {
        session.setCurrentPerMill(Calculations.calculateCurrentPerMill(alcoholUnits, user, LocalDateTime.now()));
        Log.d("currentPerMill", String.valueOf(session.getCurrentPerMill()));
        session.setMaxPerMill(Calculations.calculateMaxPerMill(session.getMaxPerMill(), session.getCurrentPerMill()));
    }

    @Override
    public void onItemClick(int position) {
        AlcoholUnit alcoholUnit = alcoholUnits.remove(position);
        adapter.notifyItemRemoved(position);
        updateCountdown();
        Log.d("onItemClickSession", "onItemClick: " + alcoholUnit.getBeverage().getName() + " pos:" + position);
    }

    @Override
    public void onItemClick(String view) {
        // Maybe handle what part of the beverage entry was clicked here?
        if (view.equals("beverageDetailFragment"))
            Navigation.findNavController(requireActivity(), R.id.nav_host).navigate(R.id.action_sessionFragment_to_beverageDetailFragment);
    }

    private void navigateToAddBeverageFragment(View view) {
        Navigation.findNavController(requireActivity(), R.id.nav_host).navigate(R.id.action_sessionFragment_to_addBeverageFragment);
    }

    private void createPositivButton(DialogInterface dialog, int id) {
        session.setName(session.getStartDateTime().toString());
        textCurrentTime.setText(view.getContext().getString(R.string.time_elapsed_format));
        int size = alcoholUnits.size();
        session.addAlcoholUnits(alcoholUnits);
        session.setEndDateTime(LocalDateTime.now());
        UserDataHandler.addSessionToHistory(session);
        alcoholUnits.clear();
        adapter.notifyItemRangeRemoved(0, size);
        Toast.makeText(SessionFragment.this.getContext(),
                "The session was saved", Toast.LENGTH_SHORT)
                .show();
    }
}
package com.illusion_softworks.kjoerbar.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.adapter.DrinkInListRecyclerAdapter;
import com.illusion_softworks.kjoerbar.calculation.Calculations;
import com.illusion_softworks.kjoerbar.handler.UserDataHandler;
import com.illusion_softworks.kjoerbar.interfaces.OnItemClickListener;
import com.illusion_softworks.kjoerbar.model.AlcoholUnit;
import com.illusion_softworks.kjoerbar.model.Drink;
import com.illusion_softworks.kjoerbar.model.Session;
import com.illusion_softworks.kjoerbar.model.User;
import com.illusion_softworks.kjoerbar.utilities.Notifications;
import com.illusion_softworks.kjoerbar.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SessionFragment extends Fragment implements OnItemClickListener {
    private static User user;
    private static Session session;
    private static CountDownTimer countDownTimer;
    private static final ArrayList<AlcoholUnit> alcoholUnits = new ArrayList<>();
    private static RecyclerView recyclerView;
    private static boolean isBeverageAdded = false;
    private TextView textTimer, textCurrentPerMill, textCurrentTime;
    private View view;
    private DrinkInListRecyclerAdapter mAdapter;
    private ProgressBar mSessionTimer;


    public SessionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_session, container, false);
        requireActivity().setTitle(getString(R.string.session));

        setUpViews();
        updateCountdown();
        setUpRecyclerView();
        notifyAdapterAfterAddedBeverage();
        setUpUserViewModel();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton openBottomSheetFAB = view.findViewById(R.id.fab_bottom_sheet_session);
        openBottomSheetFAB.setOnClickListener(v -> openBottomSheetDialog());

        mSessionTimer = view.findViewById(R.id.session_timer);
        mSessionTimer.setProgress(0);
    }

    public static void startNewSession(String name) {
        if (session == null) {
            session = new Session(name, user.getWeight(), user.getGender());
        }
    }

    private void setUpUserViewModel() {
        UserViewModel mViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        mViewModel.init();
        mViewModel.getUser().observeForever( mUser -> user = mUser);
    }

    private void setUpRecyclerView() {
        mAdapter = new DrinkInListRecyclerAdapter(view.getContext(), alcoholUnits, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(mAdapter);
    }

    public static void addDrink(Drink drink) {
        alcoholUnits.add(new AlcoholUnit(drink));
        isBeverageAdded = true;
    }

    private void setUpViews() {
        textTimer = view.findViewById(R.id.textTimer);
        textCurrentPerMill = view.findViewById(R.id.textCurrentPerMill);
        textCurrentTime = view.findViewById(R.id.textCurrentTime);
        recyclerView = view.findViewById(R.id.beverageRecyclerView);
    }

    private void openBottomSheetDialog() {
        if (session == null) {
            StartNewSessionDialogFragment startNewSessionDialogFragment = StartNewSessionDialogFragment.newInstance(requireActivity());
            startNewSessionDialogFragment.show(getParentFragmentManager(), StartNewSessionDialogFragment.TAG);
        } else {
            DrinkListDialogFragment drinkListDialogFragment = DrinkListDialogFragment.newInstance(requireActivity());
            drinkListDialogFragment.show(getParentFragmentManager(), DrinkListDialogFragment.TAG);
        }
    }

    private void updateTimer(long remainingTimeInMilliSeconds, long elapsedTimeInMilliSeconds) {
        long progress = (long)((float)elapsedTimeInMilliSeconds / remainingTimeInMilliSeconds * 100);
        mSessionTimer.setProgress(Math.toIntExact(progress));
    }

    private void notifyAdapterAfterAddedBeverage() {
        if (isBeverageAdded) {
            mAdapter.notifyItemInserted(alcoholUnits.size() - 1);
            isBeverageAdded = false;
        }
    }

    private void updateCountdown() {
        if (session != null) {
            updateCountDownTimer();
        }
    }

    private void updateCountDownTimer() {
        if (countDownTimer != null)
            countDownTimer.cancel();

        updatePerMill();
        long countDownPeriod = Calculations.calculateTimeUntilSober(session.getCurrentPerMill());

        Log.d("countDownperiod_currentPerMill", String.valueOf(session.getCurrentPerMill()));
        Log.d("countDownperiod", String.valueOf(countDownPeriod));
        LogTime(countDownPeriod, "TAG");

        countDownTimer = new CountDownTimer(countDownPeriod, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long millisBetween = System.currentTimeMillis() - session.getStartTime();

                updateTimer(countDownPeriod, millisUntilFinished);

                formatToHours(millisUntilFinished, textTimer, R.string.time_left);
                textCurrentPerMill.setText(String.format(Locale.ENGLISH, "%s: %.3f", view.getContext().getString(R.string.current_per_mill), session.getCurrentPerMill()));
                formatToHours(millisBetween, textCurrentTime, R.string.time_elapsed);

                Log.d("UserTick", session.toString());
                LogTime(millisUntilFinished, "millisUntilFinished");

                updatePerMill();
            }

            @Override
            public void onFinish() {
                textTimer.setText(R.string.you_are_sober);
                textCurrentPerMill.setText(view.getContext().getString(R.string.current_per_mill_format));
                confirmFinishDialog();
                Notifications.showNotification(Notifications.SESSION_COMPLETE);
            }
        }.start();
    }

    private void LogTime(long countDownPeriod, String tag) {
        Log.d(tag, String.format(Locale.ENGLISH,
                "%s: %02d:%02d:%02d",
                view.getContext().getString(R.string.time_left),
                TimeUnit.MILLISECONDS.toHours(countDownPeriod),
                TimeUnit.MILLISECONDS.toMinutes(countDownPeriod) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(countDownPeriod)),
                TimeUnit.MILLISECONDS.toSeconds(countDownPeriod) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(countDownPeriod))));
    }

    private void formatToHours(long millisUntilFinished, TextView textTimer, int p) {
        textTimer.setText(String.format(Locale.ENGLISH,
                "%s: %02d:%02d:%02d",
                view.getContext().getString(p),
                TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
    }

    private void confirmFinishDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage(R.string.finish_dialog_text)
                .setCancelable(true)
                .setPositiveButton(
                        R.string.yes,
                        this::createPositiveButton)
                .setNegativeButton(
                        R.string.continue_drinking,
                        (dialog, id) -> dialog.cancel());

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void createPositiveButton(DialogInterface dialog, int id) {
        endSession();
        resetAdapter();
        Toast.makeText(SessionFragment.this.getContext(),
                "The session was saved", Toast.LENGTH_SHORT)
                .show();

    }

    private void resetAdapter() {
        int size = alcoholUnits.size();
        alcoholUnits.clear();
        mAdapter.notifyItemRangeRemoved(0, size);
    }

    private void endSession() {
        // Update views
        mSessionTimer.setProgress(0);
        textCurrentTime.setText(view.getContext().getString(R.string.time_elapsed_format));

        // Save and reset session
        session.setName(String.valueOf(session.getStartTime()));
        session.addAlcoholUnits(alcoholUnits);
        session.setEndTime(System.currentTimeMillis());
        UserDataHandler.addSessionToHistory(session);
        session = null;
    }

    public void updatePerMill() {
        session.setCurrentPerMill(Calculations.calculateCurrentPerMill(alcoholUnits, user, System.currentTimeMillis()));
        Log.d("currentPerMill_currentPerMill", String.valueOf(session.getCurrentPerMill()));
        Log.d("currentPerMill_alcoholunits", String.valueOf(alcoholUnits));
        Log.d("currentPerMill_user", String.valueOf(user));
        Log.d("currentPerMill_System.currentTimeMillis()", String.valueOf(System.currentTimeMillis()));
        session.setMaxPerMill(Calculations.calculateMaxPerMill(session.getMaxPerMill(), session.getCurrentPerMill()));
    }

    @Override
    public void onItemClick(int position) {
        AlcoholUnit alcoholUnit = alcoholUnits.remove(position);
        mAdapter.notifyItemRemoved(position);
        updateCountdown();
        Log.d("onItemClickSession", "onItemClick: " + alcoholUnit.getDrink().getName() + " pos:" + position);
    }

    @Override
    public void onItemClick(String view) {
        if (view.equals("beverageDetailFragment")) {
            Navigation.findNavController(requireActivity(), R.id.nav_host).navigate(R.id.action_sessionFragment_to_drinkDetailFragment);
        }
    }
}
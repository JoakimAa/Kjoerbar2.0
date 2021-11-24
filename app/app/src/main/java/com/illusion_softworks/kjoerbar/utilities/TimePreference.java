package com.illusion_softworks.kjoerbar.utilities;
/*
The MIT License (MIT)

Copyright (c) 2015 Brandon Sara (http://bsara.github.io)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/


/* This file is taken from https://gist.github.com/bsara/69c2d4aff09d7ae21b00#file-timepreference-java */

import android.content.Context;
import android.preference.DialogPreference;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Calendar;


public class TimePreference extends DialogPreference {

    private TimePicker _timeView;

    private Calendar _value;
    private boolean _isValueSet;


    // region Constructors


    public TimePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }


    public TimePreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }


    private void init() {
        _isValueSet = false;
        _value = null;
    }


    // endregion


    // region Event Handlers


    @Override
    protected View onCreateDialogView() {
        _timeView = new TimePicker(this.getContext());
        _timeView.setSaveFromParentEnabled(false);
        _timeView.setSaveEnabled(true);
        _timeView.setIs24HourView(DateFormat.is24HourFormat(this.getContext()));

        return _timeView;
    }


    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        _timeView.setCurrentHour(_value.get(Calendar.HOUR_OF_DAY));
        _timeView.setCurrentMinute(_value.get(Calendar.MINUTE));
    }


    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        if (defaultValue == null || !(defaultValue instanceof Long)) {
            defaultValue = null;
        }


        if (restorePersistedValue) {
            _value = Calendar.getInstance();
            _value.setTimeInMillis(this.getPersistedLong((Long) defaultValue));
            _isValueSet = true;
            return;
        }


        Calendar newValue = Calendar.getInstance();

        if (defaultValue == null) {
            newValue.set(Calendar.HOUR_OF_DAY, 12);
            newValue.set(Calendar.MINUTE, 0);
        } else {
            newValue.setTimeInMillis((Long) defaultValue);
        }

        this.setValue(_value);
    }


    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            Calendar newValue = Calendar.getInstance();

            newValue.set(Calendar.HOUR_OF_DAY, _timeView.getCurrentHour());
            newValue.set(Calendar.MINUTE, _timeView.getCurrentMinute());

            if (this.callChangeListener(newValue)) {
                this.setValue(newValue);
            }
        }
    }


    // endregion


    // region Getters/Setters


    public Calendar getValue() {
        return _value;
    }


    public void setValue(Calendar newValue) {
        final boolean wasChanged = !newValue.equals(_value);

        if (wasChanged || !_isValueSet) {
            _value = newValue;
            _isValueSet = true;

            this.persistLong(_value.getTimeInMillis());

            if (wasChanged) {
                this.notifyChanged();
            }
        }
    }


    @Override
    public CharSequence getSummary() {
        return (_value == null) ? null : DateFormatUtils.ISO_TIME_FORMAT.format(_value);
    }


    // endregion
}
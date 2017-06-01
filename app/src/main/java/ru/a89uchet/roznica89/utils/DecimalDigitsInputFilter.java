package ru.a89uchet.roznica89.utils;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Босс
 * Date: 25.06.12
 * Time: 18:08
 * To change this template use File | Settings | File Templates.
 * USE editText.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});
 */
public class DecimalDigitsInputFilter implements InputFilter {
    Pattern mPattern;

    public DecimalDigitsInputFilter(int digitsBeforeZero,int digitsAfterZero) {
        mPattern= Pattern.compile("[0-9]{0," + (digitsBeforeZero-1) + "}+((\\.[0-9]{0," + (digitsAfterZero-1) + "})?)||(\\.)?");
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        Matcher matcher=mPattern.matcher(dest);
        if(!matcher.matches())
            return "";
        return null;
    }
}

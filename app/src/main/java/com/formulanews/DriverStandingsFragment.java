package com.formulanews;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DriverStandingsFragment extends Fragment {


    public DriverStandingsFragment() {
        this.mDrivers = new ArrayList<>();

        Driver d1 = new Driver();
        d1.setFirstName("Lewis");
        d1.setSurname("Hamilton");
        d1.setCountry("England");
        d1.setConstructor(new Constructor("Mercedes"));
        d1.setPoints("250");

        Driver d2 = new Driver();
        d2.setFirstName("Valtteri");
        d2.setSurname("Bottas");
        d2.setCountry("Finland");
        d2.setConstructor(new Constructor("Mercedes"));
        d2.setPoints("189");

        Driver d3 = new Driver();
        d3.setFirstName("Sebastian");
        d3.setSurname("Vettel");
        d3.setCountry("Germany");
        d3.setConstructor(new Constructor("Ferrari"));
        d3.setPoints("181");

        Driver d4 = new Driver();
        d4.setFirstName("Charles");
        d4.setSurname("Leclerc");
        d4.setCountry("Monaco");
        d4.setConstructor(new Constructor("Ferrari"));
        d4.setPoints("153");

        this.mDrivers.add(d1);
        this.mDrivers.add(d2);
        this.mDrivers.add(d3);
        this.mDrivers.add(d4);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_driver_standings, container, false);

        WebView webView = view.findViewById(R.id.webview_driver_standings);
        webView.loadData(this.generateHtml(), "text/html", "utf-8");

        return view;
    }

    private String generateHtml() {
        String html = "";

        try {
            InputStream inputStream = getResources().openRawResource(getResources().getIdentifier("driver_standings_template", "raw", this.getActivity().getPackageName()));
            html = readTextFile(inputStream);
        } catch(Exception e) {
            e.printStackTrace();
        }

        StringBuilder stringBuilder = new StringBuilder();

        int position = 1;
        for(Driver d:this.mDrivers) {
            if(position%2 != 0) {
                stringBuilder.append("<tr class='standings_odd'>");
            } else {
                stringBuilder.append("<tr class='standings_even'>");
            }

            stringBuilder.append("<td class='standings_position'>"+position+"</td><td class='standings_driver'>"+d.getFirstName()+" "+d.getSurname()+"</td><td class='standings_points'>"+d.getPoints()+" pts</td></tr>");
            position++;
        }

        html = html.replaceAll("_DRIVER_STANDINGS_", stringBuilder.toString());

        return html;
    }

    public String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }

    private List<Driver> mDrivers;
}

package com.example.nomad.fragments;


import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nomad.R;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.ReportDTO;
import com.example.nomad.helper.Consts;
import com.example.nomad.services.AccomodationsService;
import com.example.nomad.services.AuthService;
import com.example.nomad.services.ReportService;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HostReportsFragment extends Fragment {

    AccomodationsService accomodationsService = new AccomodationsService();
    private ArrayList<AccommodationDTO> accommodations;
    ArrayList<String> accommodationNames = new ArrayList<>();
    private RadioGroup radioGroup;

    LinearLayout allAccommodationsForm;
    LinearLayout oneAccommodationForm;
    private Button submitAllAccommodationsReport;
    private Button submitOneAccommodationReport;
    private Spinner spinnerAccommodationNames;
    private AccommodationDTO selectedAccommodation;
    private NumberPicker yearPicker;
    private int selectedYear;
    MaterialCalendarView materialCalendarView;
    List<CalendarDay> selectedDates;
    private String startDate = "";
    private String endDate = "";

    private Button downloadMonthlyReportButton;
    private Button downloadDateRangeReportButton;

    //chart
    private BarChart barChartProfit;
    private BarChart barChartReservations;

    //line chart
    private LineChart lineChart;

    private ReportService reportService = new ReportService();
    private ArrayList<ReportDTO> monthlyReports;
    private ArrayList<ReportDTO> dateRangeReports;

    public HostReportsFragment() {
        // Required empty public constructor
    }

    public static HostReportsFragment newInstance() {
        HostReportsFragment fragment = new HostReportsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_host_reports, container, false);

        radioGroup = view.findViewById(R.id.reportTypeRadioGroup);
        allAccommodationsForm = view.findViewById(R.id.allAccommodationsForm);
        oneAccommodationForm = view.findViewById(R.id.oneAccommodationForm);
        submitAllAccommodationsReport = view.findViewById(R.id.submitAllAccommodationsButton);
        onClickAllAccommodationsButton();
        submitOneAccommodationReport = view.findViewById(R.id.submitOneAccommodationButton);
        onClickOneAccommodationButton();
        spinnerAccommodationNames = view.findViewById(R.id.spinnerAccommodationsNames);
        setSpinner();
        yearPicker = view.findViewById(R.id.yearPicker);
        setYearPicker();
        materialCalendarView = view.findViewById(R.id.materialCalendarView);
        setCalendar();

        lineChart = view.findViewById(R.id.monthlyReportChart);
        barChartProfit = view.findViewById(R.id.dateRangeProfitChart);
        barChartReservations = view.findViewById(R.id.dateRangeReservationChart);

        downloadDateRangeReportButton = view.findViewById(R.id.downloadDateRangeReportButton);
        downloadMonthlyReportButton = view.findViewById(R.id.downMonthlyReportButton);

        setDownloadButtons();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = view.findViewById(checkedId);
                if(checkedRadioButton.getText().equals("All accommodations")) {
                    allAccommodationsForm.setVisibility(View.VISIBLE);
                    oneAccommodationForm.setVisibility(View.INVISIBLE);
                }else{
                    allAccommodationsForm.setVisibility(View.INVISIBLE);
                    oneAccommodationForm.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

    public void setSpinner() {
        accomodationsService.getAccommodationsForHost(AuthService.id);
        accomodationsService.getHostAccommodations().observe(getActivity(), new Observer<List<AccommodationDTO>>() {
            @Override
            public void onChanged(List<AccommodationDTO> objects) {
                accommodations = (ArrayList<AccommodationDTO>) objects;
                for (AccommodationDTO accommodation : accommodations) {
                    accommodationNames.add(accommodation.getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, accommodationNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerAccommodationNames.setAdapter(adapter);

                spinnerAccommodationNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedAccommodation = accommodations.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Log.i("Nothing selected", "No accommodation is selected");
                    }
                });
            }
        });
    }

    public void setYearPicker() {
        yearPicker.setMinValue(2000);
        yearPicker.setMaxValue(2024);
        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                selectedYear = yearPicker.getValue();
                Log.d("picker value", selectedYear + "");
            }
        });
    }

    public void setCalendar() {
        materialCalendarView.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {
                if (dates.size() >= 2) {
                    CalendarDay start = dates.get(0);
                    CalendarDay end = dates.get(dates.size()-1);

                    int year = start.getYear();
                    int month = start.getMonth();
                    int day = start.getDay();

                    LocalDate localDate = LocalDate.of(year, month, day);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    startDate = localDate.format(formatter);

                    year = end.getYear();
                    month = end.getMonth();
                    day = end.getDay();

                    localDate = LocalDate.of(year, month, day);
                    endDate = localDate.format(formatter);

                    Log.d("SelectedDates", "Start Date: " + startDate + " End Date: " + endDate);
                }
            }
        });
    }

    public void onClickAllAccommodationsButton() {
        submitAllAccommodationsReport.setOnClickListener(v -> {
            reportService.getReportForDateRange(AuthService.id, startDate, endDate);
            reportService.getDateRangeReports().observe(getActivity(), new Observer<List<ReportDTO>>() {
                @Override
                public void onChanged(List<ReportDTO> reportDTOS) {
                    dateRangeReports = (ArrayList<ReportDTO>) reportDTOS;
                    generateDateRangeReport();
                }
            });
        });
    }

    public void onClickOneAccommodationButton() {
        submitOneAccommodationReport.setOnClickListener(v -> {
            reportService.getReportForAccommodation(AuthService.id, selectedAccommodation.getId(), selectedYear);
            reportService.getMonthlyReports().observe(getActivity(), new Observer<List<ReportDTO>>() {
                @Override
                public void onChanged(List<ReportDTO> reportDTOS) {
                    monthlyReports = (ArrayList<ReportDTO>) reportDTOS;
                    generateMonthlyReport();
                }
            });
        });
    }

    public void generateDateRangeReport() {
        barChartProfit.setPinchZoom(true);
        barChartProfit.setDragEnabled(true);
        barChartProfit.setExtraBottomOffset(50f);

        barChartReservations.setPinchZoom(true);
        barChartReservations.setDragEnabled(true);
        barChartReservations.setExtraBottomOffset(50f);

        List<BarEntry> entriesProfit = new ArrayList<>();
        List<BarEntry> entriesReservations = new ArrayList<>();

        Toast.makeText(getActivity(), "report duzina: "+ dateRangeReports.size(), Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), "report duzina: "+ accommodationNames.size(), Toast.LENGTH_SHORT).show();

        for (int i = 0; i < dateRangeReports.size(); i++) {
            float profitValue = dateRangeReports.get(i).getProfit().floatValue();
            int reservationNumber = dateRangeReports.get(i).getReservationNumber();

            Log.d("profit", i + " " + profitValue);
            Log.d("reservation", i + " " + reservationNumber);
            entriesProfit.add(new BarEntry(i, profitValue));
            entriesReservations.add(new BarEntry(i, reservationNumber));
        }

        BarDataSet dataSetProfit = new BarDataSet(entriesProfit, "Profit");
        BarDataSet dataSetReservations = new BarDataSet(entriesReservations, "Reservations");

        dataSetProfit.setColor(Color.CYAN);
        dataSetReservations.setColor(Color.MAGENTA);

        BarData barDataProfit = new BarData(dataSetProfit);
        BarData barDataReservations = new BarData(dataSetReservations);

        barChartProfit.setData(barDataProfit);
        barChartReservations.setData(barDataReservations);

        XAxis xAxis = barChartProfit.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(45f);
        xAxis.setLabelCount(accommodationNames.size());
        xAxis.setValueFormatter(new XAxisValueFormatter(accommodationNames));

        XAxis xAxis2 = barChartReservations.getXAxis();
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setLabelRotationAngle(45f);
        xAxis2.setLabelCount(accommodationNames.size());
        xAxis2.setValueFormatter(new XAxisValueFormatter(accommodationNames));


        YAxis yAxis = barChartProfit.getAxisLeft();
        yAxis.setAxisMinimum(0f);

        YAxis yAxis2 = barChartReservations.getAxisLeft();
        yAxis2.setAxisMinimum(0f);

        Legend legend = barChartProfit.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);

        Legend legend2 = barChartReservations.getLegend();
        legend2.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);

        barChartProfit.invalidate();
        barChartReservations.invalidate();
    }

    public void generateMonthlyReport() {
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);

        float maxProfit = 0;

        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            float value = 0;
            if(i < monthlyReports.size()) {
                value = monthlyReports.get(i).getProfit().floatValue();
                if(value > maxProfit) { maxProfit = value; }
            }

            entries.add(new Entry(i, value));
        }

        ArrayList<Entry> entriesReservations = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            float value = 0;
            if(i < monthlyReports.size()) {
                value = monthlyReports.get(i).getReservationNumber();
            }

            entriesReservations.add(new Entry(i, value));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Profits");
        dataSet.setColors(Color.CYAN);
        dataSet.setDrawValues(true);

        LineDataSet dataSetReservations = new LineDataSet(entriesReservations, "Reservations");
        dataSetReservations.setColors(Color.MAGENTA);
        dataSetReservations.setDrawValues(true);

        LineData lineData = new LineData(dataSet, dataSetReservations);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new MonthAxisValueFormatter());

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(maxProfit+200);

        Legend legend = lineChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);

        lineChart.invalidate();
    }


    private void setDownloadButtons() {
        downloadMonthlyReportButton.setOnClickListener(v -> {
            if(isValidToGenerateMonthlyReport()) {
                String uriString = Consts.BASEURL + "/api/reports/generate-pdf/accommodation/"
                        +AuthService.id+"/"+selectedAccommodation.getId()+"/"+selectedYear;
                Log.e("URL", uriString);
                Uri uri = Uri.parse(uriString);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setTitle("Month report");
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "monthReport.pdf");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                long reference = downloadManager.enqueue(request);
                Toast.makeText(getActivity(), "Report is successfully downloaded. You can find it in the Downloads folder.", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(getActivity(), "Please select accommodation and year.", Toast.LENGTH_SHORT).show();
            }
        });

        downloadDateRangeReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidToGenerateDateRangeReport()) {
                    String uriString = Consts.BASEURL + "/api/reports/generate-pdf/date-range/"
                            +AuthService.id+"?from="+ startDate+"&to="+endDate;
                    Log.e("URL", uriString);
                    Uri uri = Uri.parse(uriString);
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setTitle("Date range report");
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "dateRangeReport.pdf");
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                    DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                    long reference = downloadManager.enqueue(request);
                    Toast.makeText(getActivity(), "Report is successfully downloaded. You can find it in the Downloads folder.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Please select date range.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidToGenerateDateRangeReport() {
        if(startDate.equals("") || endDate.equals("")){
            return false;
        }

        return true;
    }

    private boolean isValidToGenerateMonthlyReport() {
        if(selectedAccommodation==null){
            return false;
        }

        return true;
    }


}
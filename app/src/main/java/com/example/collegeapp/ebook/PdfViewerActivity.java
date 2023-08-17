package com.example.collegeapp.ebook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.collegeapp.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PdfViewerActivity extends AppCompatActivity {

    private String url;
    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        url = getIntent().getStringExtra("pdfUrl");

        pdfView = findViewById(R.id.pdfView);

        new PdfDownload().execute(url);
    }

    private class PdfDownload extends AsyncTask<String,Void, InputStream> {

        private ProgressDialog pd = new ProgressDialog(PdfViewerActivity.this,ProgressDialog.STYLE_SPINNER);

        @Override
        protected void onPreExecute() {
            showProgressDialogWithTitle("Please Wait...");
        }

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                if(urlConnection.getResponseCode() == 200){
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).onLoad(new OnLoadCompleteListener() {
                @Override
                public void loadComplete(int nbPages) {
                    hideProgressDialogWithTitle();
                }
            }).load();

        }

        private void showProgressDialogWithTitle(String substring) {
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //Without this user can hide loader by tapping outside screen
            pd.setCancelable(false);
            pd.setMessage(substring);
            pd.show();
        }

        private void hideProgressDialogWithTitle() {
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.dismiss();
        }
    }
}
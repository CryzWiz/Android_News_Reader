package com.allanarnesen.android_news_reader;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MainFragment extends Fragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private OnItemSelectedListener mListener;

    public static String WEB_STRING;
    public String ERROR_STRING = "Ingen svar mottatt. Oppdatering avbrutt!";
    public static String UPDATE_TOAST_STRING;


    public MainFragment() {
        Log.d("MainFragment", "MainFragment()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainFragment", "onCreate()");
        setRetainInstance(true); //onCreate runs only once when the app launches

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("MainFragment", "onRefresh called from SwipeRefreshLayout");
                initiateRefresh();
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.d("MainFragment", "onAttach()");

        if (context instanceof OnItemSelectedListener) {
            mListener = (OnItemSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d("MainFragment", "onStart()");
        initiateRefresh();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.d("MainFragment", "onDetach()");
        //PreferenceManager.setDefaultValues(getContext(),R.xml.preferences,false);
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnItemSelectedListener {
        void onItemSelected(int position);

    }

    public void initiateRefresh() {
        Log.d(TAG, "initiateRefresh");
        /*
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.refresh_progress_1));
*/
        new DownloadList().execute();
    }

    /**
     * When the AsyncTask finishes, it calls onRefreshComplete(), which updates the data in the
     * ListAdapter and turns off the progress bar.
     */
    private void onRefreshComplete(Boolean bool) {
        Log.i("MainFragment", "onRefreshComplete()");

        mSwipeRefreshLayout.setRefreshing(false);

        UPDATE_TOAST_STRING = "onRefreshComplete : " + bool;
        Toast.makeText(getActivity(), UPDATE_TOAST_STRING, Toast.LENGTH_SHORT).show();
    }

    private class DownloadList extends AsyncTask<Void, Void, Boolean> {
        // Sleep for a small amount of time to simulate a background-task
        static final int TASK_DURATION = 1 * 1000; // 3 seconds

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Thread.sleep(TASK_DURATION);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            Log.d("MainFragment", "onPostExecute()");
            super.onPostExecute(bool);
            onRefreshComplete(bool);
        }

        private void readServerResponse(InputStream is, StringBuilder serverResponse) {
            Log.d("MainFragment", "readServerResponse()");
        }
    }

}

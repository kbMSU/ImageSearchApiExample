package opensource.karthik.imagesearch.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import opensource.karthik.imagesearch.Events.ImageSearchError;
import opensource.karthik.imagesearch.Events.ImageSearchResult;
import opensource.karthik.imagesearch.Modules.EventsModule;

public class SearchTask extends AsyncTask<Context,Void,Void> {

    String searchText;
    String start;
    JSONObject result;
    ImageSearchError error;
    EventsModule eventsModule = EventsModule.getInstance();

    public SearchTask(String input,String index) {
        searchText = input;
        start = index;
    }

    @Override
    protected Void doInBackground(Context... params) {
        try {
            String ip = "192.168.0.1";

            URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="
                              +searchText+"&userip="+ip+"&start="+start+"&rsz="+8);
            URLConnection connection = url.openConnection();
            connection.addRequestProperty("Referer", "karthik.opensource");

            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((line = reader.readLine()) != null) {
                builder.append(line);
            }

            result =  new JSONObject(builder.toString());
        } catch (Exception ex) {
            error = new ImageSearchError(ex);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(result != null) {
            ImageSearchResult event = new ImageSearchResult(result,searchText);
            eventsModule.Post(event);
        } else if(error != null) {
            eventsModule.Post(error);
        } else {
            eventsModule.Post(new ImageSearchError(new Exception("Unknown error")));
        }
    }
}

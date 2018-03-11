package com.example.basma.cardsgame;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


class Lists
{

    ArrayList names;
    ArrayList scores;
    ArrayList dates;

    public Lists()
    {


        names = new ArrayList();
        scores = new ArrayList();
        dates = new ArrayList();
    }

    public ArrayList getDates() {
        return dates;
    }

    public ArrayList getNames() {
        return names;
    }

    public ArrayList getScores() {
        return scores;
    }

    public void setDates(ArrayList dates) {
        this.dates = dates;
    }

    public void setNames(ArrayList names) {
        this.names = names;
    }

    public void setScores(ArrayList scores) {
        this.scores = scores;
    }

    public void sortLists()
    {
        int n = names.size();
        String temp1="";
        String temp2="";
        String temp3="";
        for(int i=0; i < n; i++){
            for(int j=1; j < (n-i); j++){
                if(Double.parseDouble(scores.get(j-1).toString()) < Double.parseDouble(scores.get(j).toString())){
                    //swap elements
                    temp1 = names.get(j-1).toString();
                    temp2 = scores.get(j-1).toString();
                    temp3 = dates.get(j-1).toString();


                    names.set((j-1), names.get(j));
                    names.set((j), temp1);

                    scores.set((j-1), scores.get(j));
                    scores.set((j), temp2);


                    dates.set((j-1), dates.get(j));
                    dates.set((j), temp3);


                }

            }
        }

    }
}


public class scoreActivity extends AppCompatActivity {

    TextView txt;
    Button restart;
    Button exit;

    DatabaseHelper myDb;
    EditText editName,editSurname,editMarks ,editTextId;
    Button btnAddData;
    Button btnviewAll;
    Button btnDelete;

    String username;
    String score;
    String date;

    Button btnviewUpdate;
     Lists lists;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_score);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
         username = extras.getString("userName");
         score = extras.getString("scoreID");
         date = extras.getString("date");
    lists=new Lists();




        myDb = new DatabaseHelper(this);

        AddData();
        viewAll();
    }

    


    public  void AddData() {

                        boolean isInserted = myDb.insertData(username,score,date);
                        if(isInserted == true)
                            Toast.makeText(scoreActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(scoreActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();

    }

    public void viewAll() {

                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id :"+ res.getString(0)+"\n");
                            buffer.append("Name :"+ res.getString(1)+"\n");
                            buffer.append("Score:"+ res.getString(2)+"\n");
                            buffer.append("Date :"+ res.getString(3)+"\n\n");
                            lists.names.add( res.getString(1));
                            lists.scores.add( res.getString(2));
                            lists.dates.add( res.getString(3));
                        }

                        // Show all data
                        showMessage("Data",buffer.toString());


    }

    public void showMessage(String title,String Message){
        String[] all = new String[lists.names.size()];

        if(lists.names.size()==lists.scores.size()&&lists.names.size()==lists.dates.size())
            lists.sortLists();
        for (int i = 0; i < all.length; i++) {
            all[i] = lists.names.get(i) + "   " + lists.scores.get(i)+"     "+lists.dates.get(i);
        }


        ListAdapter myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, all);
        ListView myList = (ListView) findViewById(R.id.listID);
        myList.setAdapter(myAdapter);
    }




}

package com.example.pattern.strategyCache;

import java.util.List;
import java.util.Random;

import com.example.pattern.repository.R;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class MainActivity extends ListActivity {
  
  //private CommentsDataAccessObject datasource;
  private Repository repository;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //datasource = new CommentsDataAccessObject(this);
    //datasource.open();
    
    repository = new Repository(this);
    
    //List<Comment> values = datasource.getAllComments();
    List<Comment> values = repository.getAllComments();
    
    // use the SimpleCursorAdapter to show the
    // elements in a ListView
    ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
        android.R.layout.simple_list_item_1, values);
    setListAdapter(adapter);
  }

  // Will be called via the onClick attribute
  // of the buttons in main.xml
  public void onClick(View view) {
    @SuppressWarnings("unchecked")
    ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
    Comment comment = null;
    switch (view.getId()) {
    case R.id.add:
      String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
      int nextInt = new Random().nextInt(3);
      // save the new comment to the database
      //comment = datasource.createComment(comments[nextInt]);
      comment = repository.createComment(comments[nextInt]);
      adapter.add(comment);
      break;
    case R.id.delete:
      if (getListAdapter().getCount() > 0) {
        comment = (Comment) getListAdapter().getItem(0);
        //datasource.deleteComment(comment);
        repository.deleteComment(comment);
        adapter.remove(comment);
      }
      break;
    case R.id.retrieve:
		EditText commentIdToRetrieve = (EditText) findViewById(R.id.commentId);
		long commentId = Long.valueOf(commentIdToRetrieve.getText().toString());
		System.out.println("retrieving this comment id =="+commentId);
		
		Comment commentRetrieved = repository.getComment(commentId);
		System.out.println("retrieved comment =="+commentRetrieved.getComment());
		
		adapter.add(commentRetrieved);
    break;
    }
    adapter.notifyDataSetChanged();
  }

  @Override
  protected void onResume() {
	repository.open();
    //datasource.open();
    super.onResume();
  }

  @Override
  protected void onPause() {
	 repository.close();
    //datasource.close();
    super.onPause();
  }

} 
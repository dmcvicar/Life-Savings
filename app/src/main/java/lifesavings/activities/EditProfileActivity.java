package lifesavings.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import lifesavings.db.User;
import lifesavings.db.UserDataSource;


public class EditProfileActivity extends ActionBarActivity {

    public static final String PREFS_NAME = "com.example.cs480.lifesavings";
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private EditText userName;
    private EditText userAge;
    private RadioButton userMale;
    private RadioButton userFemale;
    private EditText userWeight;
    private EditText userHeight;
    private UserDataSource oldDB;
    private User primary;
    private String photoPath;
    private ImageView profileThumbNail;

    private static int userNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        oldDB = new UserDataSource(this);
        try {
            oldDB.open();
        }catch(Exception e){}

        userName = (EditText) findViewById(R.id.name_edit_text);
        userAge = (EditText) findViewById(R.id.age_edit_text);
        userMale = (RadioButton) findViewById(R.id.male_gender_radbutton);
        userFemale = (RadioButton) findViewById(R.id.female_gender_radbutton);
        userHeight = (EditText) findViewById(R.id.height_edit_text);
        userWeight = (EditText) findViewById(R.id.weight_edit_text);

        if(getIntent().hasExtra("USER")) {
            primary =  User.fromArrayList(getIntent().getStringArrayListExtra("USER"));
            userName.setText(primary.getName());
            userAge.setText("" + primary.getAge());
            if (primary.getGender().equalsIgnoreCase("male")) {
                userMale.setChecked(true);
                userFemale.setChecked(false);
            } else {
                userMale.setChecked(false);
                userFemale.setChecked(true);
            }
            userWeight.setText("" + primary.getWeight());
            userHeight.setText("" + primary.getHeight());
        }else {
            userName.setText("");
            userAge.setText("");
            userMale.setChecked(false);
            userFemale.setChecked(true);
            userWeight.setText("");
            userHeight.setText("");
        }
    }

    public void infoSave(View view){
        User currentUser = new User();
        Intent intent = new Intent(this,HomeActivity.class);
        if(getIntent().hasExtra("USER")) {
            currentUser = getUserFromInput(User.fromArrayList(getIntent().getStringArrayListExtra("USER")).getUserid());
            oldDB.updateUser(currentUser);
            intent.putStringArrayListExtra("USER",currentUser.toArrayList());
        }else{
            currentUser = getUserFromInput(-1);
            currentUser = oldDB.createUser(currentUser);
            intent.putStringArrayListExtra("USER",currentUser.toArrayList());
        }
        startActivity(intent);
    }

    public void infoCancel(View view){
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(primary != null)
            getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }*/
        if (id == R.id.home) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putStringArrayListExtra("USER",primary.toArrayList());
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private User getUserFromInput(int userId) {
        String gender;
        gender = "female";
        if(userMale.isChecked())
        {
            gender = "male";
        }
        User user = new User(userId,
                userName.getText().toString(),
                gender,
                Integer.parseInt(userWeight.getText().toString()),
                Integer.parseInt(userHeight.getText().toString()),
                Integer.parseInt(userAge.getText().toString()));
        return user;
    }

    //handle taking a picture
    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File profilePic = null;
            try{
                profilePic = createImageFile();
            }
            catch (IOException e){
                Toast toast = Toast.makeText(getApplicationContext(),
                        "File Path not created successfully", Toast.LENGTH_SHORT);
                toast.show();
            }
            if(profilePic != null){
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(profilePic));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                galleryAddPic();
            }
        }
    }
    //handle writing a picture
    private File createImageFile() throws IOException {
        // Create an image file name
        String imageName = "user_" + userNum;
        File storageLoc = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageName,  /* prefix */
                ".jpg",         /* suffix */
                storageLoc      /* directory */
        );
        // Save the file
        photoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    //add a picture to a gallery
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(photoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    //Generate thumbnail the way google suggests on their doc pages
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profileThumbNail.setImageBitmap(imageBitmap);
        }
    }

    }

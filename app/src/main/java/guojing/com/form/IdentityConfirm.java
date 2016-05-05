package guojing.com.form;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class IdentityConfirm extends AppCompatActivity {

    private Button takePhoto, btnVoice;
    private Uri imageUri;
    private ImageView picture;


    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    public static final int RECORD_VOICE=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_confirm);
        takePhoto = (Button) findViewById(R.id.takePicture);
        btnVoice = (Button) findViewById(R.id.recordVideo);
        picture = (ImageView) findViewById(R.id.picture);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        btnVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用Android自带的音频录制应用
                Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                startActivityForResult(intent, RECORD_VOICE);
            }
        });


    }

    private void takePhoto() {
        // 创建File对象，用于存储拍照后的图片
        File outputImage = new File(Environment.getExternalStorageDirectory(), "tempImage.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageUri = Uri.fromFile(outputImage);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO); // 启动相机程序
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO); // 启动裁剪程序
                }
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                        // 将裁剪后的照片显示出来
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case RECORD_VOICE:
            // 获取到刚刚录制的音频的Uri，可以进行播放等操作，这里显示返回的Uri
            if (resultCode == RESULT_OK) {
                Uri audioPath = data.getData();
                Toast.makeText(this, audioPath.toString(), Toast.LENGTH_LONG).show();
            }
                break;
            default:
                break;
        }
    }


}

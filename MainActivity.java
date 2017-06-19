package com.example.administrator.retrofitdemo;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.data;
import static android.os.Build.VERSION_CODES.M;

public class MainActivity extends AppCompatActivity {
    static String username="11111111111";
    static String password="1";
    static String postkey="1503d";
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.Phone);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到系统相册；
                toPic();
            }
        });
        // http://qhb.2dyt.com/Bwei/login?username=11111111111&password=1&postkey=1503d
         myPath();
        // myGet();//GET请求
        //myPost();//Post请求
       // posts();//Post请求
       //myPostBody();
    }

    public static final int IMAGE = 1 ;
    public static final int CAMERA = 2 ;

    public static String photoCacheDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Bwei";

    public void toPic() {

        if (!new File(photoCacheDir).exists()) {
            new File(photoCacheDir).mkdirs();
        }

        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType("image/*");
        startActivityForResult(getAlbum, 1);

    }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {

                switch (requestCode) {

                    case 1:

                        try {
                            // 相册

                            if (data == null)// 如果没有获取到数据
                                return;
                            Uri originalUri = data.getData();
                            //文件大小判断

                            if (originalUri != null) {
                                File file = null;
                                String[] proj = {MediaStore.Images.Media.DATA};
                                Cursor actualimagecursor = managedQuery(originalUri, proj, null, null, null);
                                if (null == actualimagecursor) {
                                    if (originalUri.toString().startsWith("file:")) {
                                        file = new File(originalUri.toString().substring(7, originalUri.toString().length()));
                                        if (!file.exists()) {
                                            //地址包含中文编码的地址做utf-8编码
                                            originalUri = Uri.parse(URLDecoder.decode(originalUri.toString(), "UTF-8"));
                                            file = new File(originalUri.toString().substring(7, originalUri.toString().length()));
                                        }
                                    }
                                } else {
                                    // 系统图库
                                    int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                                    actualimagecursor.moveToFirst();
                                    String img_path = actualimagecursor.getString(actual_image_column_index);
                                    if (img_path == null) {
                                        InputStream inputStrean = getContentResolver().openInputStream(originalUri);
                                        file = new File(photoCacheDir + "/aa.jpg");
                                        if (!file.exists()) {
                                            file.createNewFile();
                                        }
                                        System.out.println(" - " + file.exists());
                                        FileOutputStream outputStream = new FileOutputStream(file);

                                        byte[] buffer = new byte[1024];
                                        int len = 0;
                                        while ((len = inputStrean.read(buffer)) != -1) {
                                            outputStream.write(buffer, 0, len);
                                        }
                                        outputStream.flush();

                                        if (inputStrean != null) {
                                            inputStrean.close();
                                            inputStrean = null;
                                        }

                                        if (outputStream != null) {
                                            outputStream.close();
                                            outputStream = null;
                                        }
                                    } else {
                                        file = new File(img_path);
                                    }
                                }
                                String camerFileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
                                File newfilenew = new File(photoCacheDir, camerFileName);
//                            if (!newfilenew.exists()) {
//                                newfilenew.createNewFile();
//                            }
                                FileInputStream inputStream = new FileInputStream(file);
                                FileOutputStream outStream = new FileOutputStream(newfilenew);

                                try {
                                    byte[] buffer = new byte[1024];
                                    int len = 0;
                                    while ((len = inputStream.read(buffer)) != -1) {
                                        outStream.write(buffer, 0, len);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    try {
                                        inputStream.close();
                                        outStream.close();
                                        inputStream = null;
                                        outStream = null;
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                uploadFile(newfilenew.toString());

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
              }
            }
          }

    public void uploadFile(String str){

        Retrofit fit=new Retrofit.Builder().baseUrl("http://qhb.2dyt.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyHttp myHttp = fit.create(MyHttp.class);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),new File(str));

         String[] filename=str.split("\\|");

        MultipartBody mbody=new MultipartBody.Builder()
                .addFormDataPart("image",filename[filename.length-1],requestBody).build();

        myHttp.upLoadPhone(mbody)
    }


    private void myPath() {
        Retrofit fit=new Retrofit.Builder().baseUrl("http://qhb.2dyt.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyHttp myHttp = fit.create(MyHttp.class);
        Map<String,String>map=new HashMap<>();
        map.put("username","11111111111");
        map.put("password","1");
        map.put("postkey","1503d");
        myHttp.getPath("login",map).enqueue(new Callback<Bean>() {
            @Override
            public void onResponse(Call<Bean> call, Response<Bean> response) {
                Toast.makeText(MainActivity.this,"***"+response.body().getRet_msg(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Bean> call, Throwable t) {

            }
        });
    }

    private void myPostBody() {
        Retrofit fit=new Retrofit.Builder().baseUrl("http://qhb.2dyt.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyHttp myHttp = fit.create(MyHttp.class);

       Mybean bean = new Mybean(username,password,postkey);

        myHttp.postBody(bean).enqueue(new Callback<Bean>() {
            @Override
            public void onResponse(Call<Bean> call, Response<Bean> response) {
                Toast.makeText(MainActivity.this,"***"+response.body().getRet_msg(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Bean> call, Throwable t) {

            }
        });
    }

    private void posts() {
        //创建Retrofit对象；
        Retrofit fit=new Retrofit.Builder().baseUrl("http://qhb.2dyt.com/")
               //添加转换器工厂，用Gson转换成Bean；
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //使用动态代理的方式直接返回接口对象；
        MyHttp myHttp = fit.create(MyHttp.class);
        Map<String,String>map=new HashMap<>();
        map.put("username","11111111111");
        map.put("password","1");
        map.put("postkey","1503d");
        myHttp.myPost(map).enqueue(new Callback<Bean>() {
            @Override
            public void onResponse(Call<Bean> call, Response<Bean> response) {
                Toast.makeText(MainActivity.this,"***"+response.body().getRet_msg(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Bean> call, Throwable t) {

            }
        });
    }

    private void myPost() {
        //创建Retrofit对象；
    Retrofit fit= new Retrofit.Builder().baseUrl("http://qhb.2dyt.com/")
            //添加转换器工厂，用Gson转换成Bean；
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    //使用动态代理的方式直接返回接口对象；
    MyHttp http = fit.create(MyHttp.class);
    http.getPost(username,password,postkey).enqueue(new Callback<Bean>() {
        @Override
        public void onResponse(Call<Bean> call, Response<Bean> response) {
            Toast.makeText(MainActivity.this,"***"+response.body().getRet_msg(),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Call<Bean> call, Throwable t) {

        }
    });
}

    //get请求；
    private void myGet() {
        //创建Retrofit对象；构造者模式；
        Retrofit fit=new Retrofit.Builder().baseUrl("http://qhb.2dyt.com/")
                //添加转换器工厂，用Gson转换成Bean；
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //使用动态代理的方式直接返回接口对象；
        MyHttp myHttp = fit.create(MyHttp.class);
        //直接使用对象调用接口中的方法进行异步请求；
        //方法需要一个集合参数；
        Map<String,String>map=new HashMap<>();
        map.put("username","11111111111");
        map.put("password","1");
        map.put("postkey","1503d");
        myHttp.getData(map).enqueue(new Callback<Bean>() {
            @Override
            public void onResponse(Call<Bean> call, Response<Bean> response) {
                Toast.makeText(MainActivity.this,"***"+response.body().getRet_msg(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Bean> call, Throwable t) {

            }
        });
    }
}

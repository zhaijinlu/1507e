package com.bwie.day.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwie.day.R;
import com.bwie.day.app.MyApp;
import com.bwie.day.bean.Student;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewInject(R.id.btn)
    private Button btn;
    @ViewInject(R.id.imageview)
    private ImageView imageView;
    private String path="http://pic.qjimage.com/pm0005/high/pm0005-7332xe.jpg";
    private String url="http://www.93.gov.cn/93app/data.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }
    //点击事件
    @Event( type = View.OnClickListener.class,value = {R.id.btn,R.id.insert,R.id.select,R.id.updata,R.id.delete,R.id.del_table,R.id.del_db,R.id.up_data,R.id.dawn_data})
    private void onclick(View v){
        DbManager db = MyApp.getdb();
        switch (v.getId()){
            case R.id.btn:
                //x.image().bind(imageView,path);
                ImageOptions options=new ImageOptions.Builder().setCircular(true).setSize(300,300).setFailureDrawableId(R.mipmap.a).build();
                x.image().bind(imageView,path,options);
                break;
            case R.id.insert://添加数据
                List<Student>  list=new ArrayList<>();
                list.add(new Student("张三"));
                list.add(new Student("李四"));
                list.add(new Student("王五"));
                list.add(new Student("王八"));
                try {
                    db.save(list);
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.select://查询数据
                try {
                    List<Student> students = db.findAll(Student.class);
                    for (int i=0;i<students.size();i++){
                        Log.e("Students",students.toString());
                    }
                    List<Student> all = db.selector(Student.class).where("name", "=", "李四").findAll();
                    for (int i=0;i<all.size();i++){
                        Log.e("Student",all.toString());
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.updata://修改数据
                try {
                    Student stu = db.findById(Student.class, "3");
                    stu.setName("钱七");
                    db.update(stu,"name");
                    Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.delete:
                try {
                    Student stu = db.findById(Student.class, "4");
                    db.delete(stu);
                    Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                } catch (DbException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.del_table://删除表
                try {
                    db.dropTable(Student.class);
                    Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.del_db://删除数据库
                try {
                    db.dropDb();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.up_data://上传
                updata();
                break;
            case R.id.dawn_data://下载
                downdata();
                break;
        }

    }
    //下载
    private void downdata() {
        String url = "http://imtt.dd.qq.com/16891/3AFA21F3690FB27C82A6AB6024E56852.apk?fsname=com.tencent.mobileqq_7.1.8_718.apk&csr=97c2";
        RequestParams params = new RequestParams(url);
        params.setSaveFilePath(Environment.getExternalStorageDirectory()+"/apk");
        x.http().get(params, new Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
                //下载成功之后进行安装apk
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(result), "application/vnd.android.package-archive");
                startActivity(intent);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    //上传
    private void updata() {
        String url = "http://169.254.59.150:8080/08web/FileUploadServlet";
        File file=new File(Environment.getExternalStorageDirectory(),"/lengfeng.mp3");
        RequestParams params=new RequestParams(url);
        params.addBodyParameter("file",file);
        params.setMultipart(true);
        x.http().post(params, new Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
                Toast.makeText(MainActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    //长按事件(请求网络数据)
    @Event(type = View.OnLongClickListener.class,value = R.id.btn)
    private  boolean longplay(View v){
        RequestParams params=new RequestParams(url);
        params.addQueryStringParameter("channelId","0");
        params.addQueryStringParameter("startNum","0");
        params.setCacheMaxAge(1000*60);
        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(MainActivity.this, "请求成功"+result.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                Toast.makeText(MainActivity.this, "缓存数据"+result.toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return  true;
    }
}

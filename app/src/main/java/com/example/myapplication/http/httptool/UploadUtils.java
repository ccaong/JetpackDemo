package com.example.myapplication.http.httptool;

import android.text.TextUtils;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by iCong.
 * Time:2017年7月6日.
 * Retrofit文件上传
 */

public class UploadUtils {
  private static final String FILE_NOT_NULL = "文件不能为空";
  private static final String FILE_PATH_NOT_NULL = "文件路径不能为空";

  public static MultipartBody.Part getMultipartBody(String path) {
    if (TextUtils.isEmpty(path)) throw new NullPointerException(FILE_PATH_NOT_NULL);
    File file = new File(path);
    if (file.exists()) {
      RequestBody requestFile =
          RequestBody.create(MediaType.parse("application/octet-stream"), file);
      MultipartBody.Part body =
          MultipartBody.Part.createFormData("imgFile", file.getName(), requestFile);
      return body;
    } else {
//      throw new NullPointerException(FILE_NOT_NULL);
      return null;
    }
  }

  public static MultipartBody.Part getMultipartBody(File file) {
    if (file.exists()) {
      RequestBody requestFile =
          RequestBody.create(MediaType.parse("application/octet-stream"), file);
      MultipartBody.Part body =
          MultipartBody.Part.createFormData("file", file.getName(), requestFile);
      return body;
    } else {
      throw new NullPointerException(FILE_NOT_NULL);
    }
  }

  public static List<MultipartBody.Part> getMultipartBodysForFile(List<File> files) {
    if (files.isEmpty()) throw new NullPointerException(FILE_NOT_NULL);
    MultipartBody.Builder builder = new MultipartBody.Builder();
    for (File file : files) {
      if (file.exists()) {
        RequestBody requestFile =
            RequestBody.create(MediaType.parse("application/octet-stream"), file);
        builder.addFormDataPart("file", file.getName(), requestFile);
      } else {
        throw new NullPointerException(FILE_NOT_NULL);
      }
    }
    return builder.build().parts();
  }

  public static List<MultipartBody.Part> getMultipartBodysForPath(List<String> paths) {
    if (paths.isEmpty()) throw new NullPointerException(FILE_PATH_NOT_NULL);
    MultipartBody.Builder builder = new MultipartBody.Builder();
    for (String path : paths) {
      File file = new File(path);
      if (file.exists()) {
        RequestBody requestFile =
            RequestBody.create(MediaType.parse("application/octet-stream"), file);
        builder.addFormDataPart("file", file.getName(), requestFile);
      } else {
        throw new NullPointerException(FILE_NOT_NULL);
      }
    }
    return builder.build().parts();
  }
}

package com.muzi.repairtime.utils;


import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by muzi on 2017/12/20.
 * 727784430@qq.com
 */

public class ImgPartUtils {

    public static MultipartBody.Part[] string2Part(List<String> paths) {
        if (paths == null || paths.size() == 0) {
            return null;
        }
        MultipartBody.Part[] parts = new MultipartBody.Part[paths.size()];
        for (int i = 0; i < paths.size(); i++) {
            parts[i] = getPart(i, paths.get(i));
        }
        return parts;
    }

    private static MultipartBody.Part getPart(int position, String path) {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        MultipartBody.Part part;
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        part = MultipartBody.Part.createFormData("files", file.getName(), requestFile);
        return part;
    }

    public static MultipartBody.Part[] files2Part(List<File> files) {
        if (files == null || files.size() == 0) {
            return null;
        }
        MultipartBody.Part[] parts = new MultipartBody.Part[files.size()];
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            if (file != null) {
                MultipartBody.Part part;
                RequestBody requestFile = RequestBody.create(MultipartBody.FORM, file);
                part = MultipartBody.Part.createFormData("files", file.getName(), requestFile);
                parts[i] = part;
            }
        }
        return parts;
    }

    public static MultipartBody.Part[] file2Part(File file) {
        if (file == null) {
            return null;
        }
        MultipartBody.Part[] parts = new MultipartBody.Part[1];
        if (file != null) {
            MultipartBody.Part part;
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            part = MultipartBody.Part.createFormData("files", file.getName(), requestFile);
            parts[0] = part;
        }
        return parts;
    }

    public static MultipartBody.Part[] string2Part(String path) {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        return file2Part(new File(path));
    }
}

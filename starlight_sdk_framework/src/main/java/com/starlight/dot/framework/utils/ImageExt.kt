package com.starlight.dot.framework.utils

import android.graphics.*
import android.os.Environment
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

/**
 * 压缩图片
 * create by Eastevil at 2022/11/4 11:53
 * @author Eastevil
 * @param file
 *      需要压缩的图片文件
 * @param replacePath
 *      压缩后的图片文件路径
 * @param width
 *      压缩的图片宽度
 * @return
 *      压缩后的图片
 */
fun compressImage(file : File, replacePath : String = "/starlight/temp/", width : Int = 1080): File? {
    val file = zoomImage(file.path,replacePath);
    /*val width = getScreenSize(BaseApp.app)[0];
    compressImageSize(file.path,width);*/
    val bitmap = rotateImage(file,0);
    bitmap?.let {
        return it.bitmapToFile(file.path);
    }
    return null;
}


/**
 * 质量压缩图片
 * create by Eastevil at 2022/11/4 13:28
 * @author Eastevil
 * @param filePath
 *      图片文件路径
 * @param replacePath
 *      压缩后的图片保存路径
 * @return
 *      压缩后的图片
 */
fun zoomImage(filePath : String,replacePath : String = "/starlight/zoomimage/"): File {
    //质量压缩图片
    var bitmap = BitmapFactory.decodeFile(filePath);
    val baos = ByteArrayOutputStream()
    // 质量压缩
    var isCompress = false
    if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg")) {
        isCompress = true
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    } else if (filePath.endsWith(".png")) {
        isCompress = true
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
    } else {
        isCompress = false
    }
    if(isCompress){
        var options = 100
        var length = baos.toByteArray().size.toLong()
        length /= 1024
        if (length > 200) {
            while (baos.toByteArray().size / 1024 > 200) {
                options -= 5 // 每次都减少5
                baos.reset() // 重置baos即清空baos
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos)
            }
        }
    }
    val dirFile = File(Environment.getExternalStorageDirectory().toString() + replacePath)
    if (!dirFile.exists()) {
        dirFile.mkdirs()
    }
    var child = System.currentTimeMillis().toString()
    child = if (filePath.endsWith(".jpg")) {
        "$child.jpg"
    } else if (filePath.endsWith(".png")) {
        "$child.png"
    } else if (filePath.endsWith(".jpeg")) {
        "$child.jpeg"
    } else {
        "$child.jpg"
    }
    val file = File(dirFile.path, child)
    var fos : FileOutputStream? = null;
    try {
        fos = FileOutputStream(file);
        fos.write(baos.toByteArray())
        fos.flush()
    } catch (e : Exception){
        e.printStackTrace()
    } finally {
        fos?.let {
            it.close();
        }
        if(!bitmap.isRecycled){
            bitmap.recycle();
        }
    }
    return file;
}

/**
 * 旋转图片
 * create by Eastevil at 2022/11/4 13:24
 * @author Eastevil
 * @param file
 *      文件
 * @param orientationDegree
 *      旋转角度
 * @return
 *      旋转后的bitmap
 */
fun rotateImage(file : File,orientationDegree : Int): Bitmap? {
    //方便判断，角度都转换为正值
    var degree = orientationDegree
    if( degree < 0){
        degree = 360 + orientationDegree;
    }
    var bm = BitmapFactory.decodeFile(file.path);
    var srcW: Int = bm.getWidth()
    var srcH: Int = bm.getHeight()

    val m = Matrix()
    m.setRotate(degree.toFloat(), srcW.toFloat() / 2, srcH.toFloat() / 2)
    var targetX: Float
    var targetY: Float

    var destH = srcH
    var destW = srcW

    //根据角度计算偏移量，原理不明
    if (degree == 90 ) {
        targetX = srcH.toFloat();
        targetY = 0F;
        destH = srcW;
        destW = srcH;
    } else if( degree == 270){
        targetX = 0F;
        targetY = srcW.toFloat();
        destH = srcW;
        destW = srcH;
    }else if(degree == 180){
        targetX = srcW.toFloat();
        targetY = srcH.toFloat();
    }else {
        return bm;
    }
    val values = FloatArray(9)
    m.getValues(values)
    val x1 = values[Matrix.MTRANS_X]
    val y1 = values[Matrix.MTRANS_Y]
    m.postTranslate(targetX - x1, targetY - y1);

    //注意destW 与 destH 不同角度会有不同
    val bm1 = Bitmap.createBitmap(destW, destH, Bitmap.Config.ARGB_8888)
    val paint = Paint()
    val canvas = Canvas(bm1)
    canvas.drawBitmap(bm, m, paint)
    return bm1
}


/**
 * 保存Bitmap为本地文件，如果传入的保存路径存在，则会删除
 * create by Administrator at 2022/3/28 0:19
 * @author Administrator
 * @param savePath
 *      保存路径
 * @return
 *      保存后的文件
 */
fun Bitmap.bitmapToFile(savePath : String): File? {
    val file = File(savePath);
    if(file.exists()){
        file.delete();
    }
    var fos : FileOutputStream? = null;
    var bos : BufferedOutputStream? = null;
    try {
        fos = FileOutputStream(file);
        bos = BufferedOutputStream(fos);
        this.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        bos.flush();
        bos.close();
        return file;
    } catch (e : java.lang.Exception){
        e.printStackTrace();
    } finally {
        fos?.let {
            it.close()
        }
        bos?.let {
            it.close();
        }
    }
    return null;
}

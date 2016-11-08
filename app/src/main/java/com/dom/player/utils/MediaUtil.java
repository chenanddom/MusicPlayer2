package com.dom.player.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import com.dom.player.bean.Song;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import player.dom.com.musicplayer.R;

/**
 * Created by chendom on 16-11-8.
 */

/**
 * 怎样获取图片的大小？
 * 首先我们把这个图片转成Bitmap，然后再利用Bitmap的getWidth()和getHeight()方法就可以取到图片的宽高了。
 * 新问题又来了，在通过BitmapFactory.decodeFile(String path)方法将突破转成Bitmap时，遇到大一些的图片，我们经常会遇到OOM(Out Of Memory)的问题。怎么避免它呢？
 * 这就用到了我们上面提到的BitmapFactory.Options这个类。
 * <p>
 * BitmapFactory.Options这个类，有一个字段叫做 inJustDecodeBounds 。SDK中对这个成员的说明是这样的：
 * If set to true, the decoder will return null (no bitmap), but the out…
 * 也就是说，如果我们把它设为true，那么BitmapFactory.decodeFile(String path, Options opt)并不会真的返回一个Bitmap给你，它仅仅会把它的宽，高取回来给你，这样就不会占用太多的内存，也就不会那么频繁的发生OOM了。
 */
public class MediaUtil {
    private static final Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");

    public static ArrayList<Song> getAllSongs(Context context) {
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        ArrayList<Song> mp3Infos = new ArrayList<Song>();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            Song mp3Info = new Song();
            long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));//get music's id
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));//get music's title
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));//get music's artist
            String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            String displayeName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            long albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
            String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
            if (isMusic != 0) {
                mp3Info.setId(id);
                mp3Info.setTitle(title);
                mp3Info.setArtist(artist);
                mp3Info.setAlbum(album);
                mp3Info.setDispalyeName(displayeName);
                mp3Info.setAlbumId(albumId);
                mp3Info.setDuration(duration);
                mp3Info.setSize(size);
                mp3Info.setUrl(url);
                mp3Infos.add(mp3Info);
            }

        }
        return mp3Infos;
    }

    public static String formatTimer(long time) {
        String min = time / (1000 * 60) + "";
        String sec = time % (1000 * 60) + "";
        if (min.length() < 2) {
            min = "0" + time / (1000 * 60) + "";

        } else {
            sec = time / (1000 * 60) + "";
        }
        if (sec.length() == 4) {
            sec = "0" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 3) {
            sec = "00" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 2) {
            sec = "000" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 1) {
            sec = "0000" + (time % (1000 * 60)) + "";
        }
        return min + ":" + sec.trim().substring(0, 2);
    }

    private static final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
    private static final BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();

    public static final Bitmap getArtwork(Context context, long songId, long albumId, boolean allowdefault) {
        if (albumId < 0) {
            if (songId >= 0) {
//            Bitmap bmp = get
                Bitmap bmp = getArtworkFromFile(context, songId, -1);
                if (bmp != null) {
                    return bmp;
                }
            }
            if (allowdefault) {
                return getDefaulArtwork(context);
            }
            return null;
        }
        ContentResolver res = context.getContentResolver();
        Uri uri = ContentUris.withAppendedId(sArtworkUri, albumId);
        if (uri != null) {
            InputStream in = null;
            try {
                in = res.openInputStream(uri);
                Bitmap bm = BitmapFactory.decodeStream(in, null, sBitmapOptions);
                if (bm == null) {
                    bm = getDefaulArtwork(context);
                }
                return bm;
            } catch (Exception e) {
                Bitmap bm = getArtworkFromFile(context, songId, albumId);
                if (bm != null) {
                    if (bm.getConfig() == null) {
                        bm = bm.copy(Bitmap.Config.RGB_565, false);
                        if (bm == null && allowdefault) {
                            return getDefaulArtwork(context);
                        }
                    }
                } else if (allowdefault) {
                    bm = getDefaulArtwork(context);
                }
                return bm;
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    private static Bitmap getArtworkFromFile(Context context, long songId, long albumId) {
        Bitmap bmp = null;
        if (albumId < 0 && songId < 0) {
            throw new IllegalArgumentException("must specify an abum or a song id");
        }
        try {
            if (albumId < 0) {
                Uri uri = Uri.parse("content://media/external/audio/media/" + songId + "/albumart");
                ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
                if (pfd != null) {
                    FileDescriptor fd = pfd.getFileDescriptor();
                    bmp = BitmapFactory.decodeFileDescriptor(fd);

                }
            } else {
                Uri uri = ContentUris.withAppendedId(sArtworkUri, albumId);
                ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
                if (pfd != null) {
                    FileDescriptor fd = pfd.getFileDescriptor();
                    bmp = BitmapFactory.decodeFileDescriptor(fd);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bmp;
    }

    private static Bitmap getDefaulArtwork(Context context) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeStream(context.getResources().openRawResource(R.raw.icon_monkey), null, opts);
    }

}

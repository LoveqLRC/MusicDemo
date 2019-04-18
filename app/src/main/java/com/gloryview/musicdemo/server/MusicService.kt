package com.gloryview.musicdemo.server

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserCompat.MediaItem.FLAG_BROWSABLE
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.media.MediaBrowserServiceCompat

/**
 *
 * @ProjectName:    MusicDemo
 * @Package:        com.gloryview.musicdemo.server
 * @ClassName:      MusicService
 * @Description:     java类作用描述
 * @Author:         Rc
 * @CreateDate:     2019/4/4 2:52 PM
 * @UpdateUser:     更新者
 * @UpdateDate:     2019/4/4 2:52 PM
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class MusicService : MediaBrowserServiceCompat() {

    private var isForegroundService = false

    override fun onCreate() {
        super.onCreate()

        Log.d("music","MusicService onCreate")
        val sessionIntent = packageManager?.getLaunchIntentForPackage(packageName)
        val sessionActivityPendingIntent = PendingIntent.getActivity(this, 0, sessionIntent, 0)

        // Create a new MediaSession.
        val mediaSession = MediaSessionCompat(this, "MusicService")
            .apply {
                setSessionActivity(sessionActivityPendingIntent)
                isActive = true
            }
        sessionToken = mediaSession.sessionToken

        val mediaController = MediaControllerCompat(this, mediaSession).also {
            it.registerCallback(MediaControllerCallback())
        }


    }


    override fun onLoadChildren(parentId: String, result: Result<MutableList<MediaBrowserCompat.MediaItem>>) {
        Log.d("music", "MusicService onLoadChildren  $parentId")
        val children = mutableListOf<MediaBrowserCompat.MediaItem>()
        children.add(MediaBrowserCompat.MediaItem(MediaDescriptionCompat.Builder().setDescription("hello world")
            .setMediaId("mediaId").build(), FLAG_BROWSABLE))
        result.sendResult(children)
    }

    override fun onGetRoot(clientPackageName: String, clientUid: Int, rootHints: Bundle?): BrowserRoot? {
        Log.d("music","MusicService onGetRoot")
        return BrowserRoot("M", null)
    }


    private inner class MediaControllerCallback : MediaControllerCompat.Callback() {
        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            updateNotification()
        }

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            updateNotification()
        }

        private fun updateNotification() {
            if (!isForegroundService) {
                startService(Intent(applicationContext, this@MusicService.javaClass))
                isForegroundService = true
            }
        }
    }
}
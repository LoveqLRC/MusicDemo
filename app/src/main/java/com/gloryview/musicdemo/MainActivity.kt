package com.gloryview.musicdemo

import android.content.ComponentName
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import com.gloryview.musicdemo.server.MusicService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {

            mediaBrowser = MediaBrowserCompat(
                this.applicationContext, ComponentName(this.applicationContext, MusicService::class.java)
                , object : MediaBrowserConnectionCallback(this.applicationContext) {}, null
            ).apply { connect() }

        }

        button2.setOnClickListener {
            mediaBrowser.subscribe("rc", object : MediaBrowserCompat.SubscriptionCallback() {

                override fun onChildrenLoaded(parentId: String, children: MutableList<MediaBrowserCompat.MediaItem>) {
                    Log.d("music", "mediaBrowser onConnected")

                }
            })
        }


        button3.setOnClickListener {
            mediaBrowser.subscribe("new rc", object : MediaBrowserCompat.SubscriptionCallback() {

                override fun onChildrenLoaded(parentId: String, children: MutableList<MediaBrowserCompat.MediaItem>) {
                    Log.d("music", "mediaBrowser onConnected")

                }
            })
        }
    }

//    private lateinit var mediaBrowserConnectionCallback: MediaBrowserConnectionCallback

    lateinit var mediaBrowser: MediaBrowserCompat


    //客户端链接回调
    private open inner class MediaBrowserConnectionCallback(private val context: Context) :
        MediaBrowserCompat.ConnectionCallback() {

        override fun onConnected() {
            Log.d("music", "mediaBrowser onConnected")

            var mediaController = MediaControllerCompat(context, mediaBrowser.sessionToken)
                .apply {
                    registerCallback(MediaControllerCallback())
                }
        }

        override fun onConnectionSuspended() {
            Log.d("music", "mediaBrowser onConnectionSuspended")
        }

        override fun onConnectionFailed() {
            Log.d("music", "mediaBrowser onConnectionFailed")
        }

    }


    //控制器回调
    private inner class MediaControllerCallback : MediaControllerCompat.Callback() {
        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {

            Log.d("music", "mediaBrowser onPlaybackStateChanged")
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            Log.d("music", "mediaBrowser onMetadataChanged")
        }

        override fun onQueueChanged(queue: MutableList<MediaSessionCompat.QueueItem>?) {
            Log.d("music", "mediaBrowser onQueueChanged")
        }

        override fun onSessionDestroyed() {
            Log.d("music", "mediaBrowser onSessionDestroyed")
//            mediaBrowserConnectionCallback.onConnectionSuspended()
        }

    }
}

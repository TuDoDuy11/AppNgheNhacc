package com.example.appnghenhac
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appnghenhac.Music
import com.example.appnghenhac.MusicAdapter
import com.example.appnghenhac.R
class MainActivity : AppCompatActivity(), MusicAdapter.OnItemClickListener {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var musicAdapter: MusicAdapter
    private lateinit var textNowPlaying: TextView
    private lateinit var btnPlayPause: Button
    private var selectedPosition: Int = -1
    private var isPlaying: Boolean = false
    private var currentMusicPosition: Int = -1

    private val musicList = listOf(
        Music("Anh Không Tha Thứ", "Đình Dũng", R.raw.anhkhongthathu),
        Music("Em Đã Thương Người Ta Hơn Anh", "Noo Phước Thịnh", R.raw.emdathuongnguoitahonanh),
        Music("Cô Gái Vàng", "Huy R", R.raw.cogaivang),
        Music("Hạt Cát Bụi Vàng", "Thành Draw", R.raw.hatcatbuivang),
        Music("Ông Bà Già Tao Lo Hết", "Bình Gold", R.raw.ongbagiataolohet),
        Music("Sơn Tinh Thủy Tinh", "RickyStar-Rtee", R.raw.sontinhtuytinh),
        Music("Thiên Đàng", "Wowy", R.raw.thiendang),
        Music("This Way", "CARA", R.raw.thisway),
        Music("021", "Binz", R.raw.binz021),

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mediaPlayer = MediaPlayer()
        textNowPlaying = findViewById(R.id.textNowPlaying)
        btnPlayPause = findViewById(R.id.btnPlayPause)

        setupRecyclerView()

        btnPlayPause.setOnClickListener {
            if (isPlaying) {
                mediaPlayer.pause()
                btnPlayPause.text = "Play"
            } else {
                if (currentMusicPosition != -1) {
                    playMusic(currentMusicPosition)
                }
            }
            isPlaying = !isPlaying
        }
    }

    private fun setupRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        musicAdapter = MusicAdapter(musicList, this)
        recyclerView.adapter = musicAdapter
    }

    override fun onItemClick(position: Int) {
        selectedPosition = position
        if (currentMusicPosition != position) {
            stopMusic()
            playMusic(position)
            isPlaying = true
            btnPlayPause.text = "Pause"
        } else {
            if (isPlaying) {
                mediaPlayer.pause()
                isPlaying = false
                btnPlayPause.text = "Play"
            } else {
                mediaPlayer.start()
                isPlaying = true
                btnPlayPause.text = "Pause"
            }
        }
        currentMusicPosition = position
        updateNowPlayingText(position)
    }

    private fun playMusic(position: Int) {
        mediaPlayer.reset()
        mediaPlayer = MediaPlayer.create(this, musicList[position].resourceId)
        mediaPlayer.start()
    }

    private fun stopMusic() {
        mediaPlayer.stop()
        mediaPlayer.reset()
    }

    private fun updateNowPlayingText(position: Int) {
        textNowPlaying.text = "Now Playing: ${musicList[position].title} - ${musicList[position].artist}"
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
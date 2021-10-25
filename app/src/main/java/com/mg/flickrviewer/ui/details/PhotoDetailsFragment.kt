package com.mg.flickrviewer.ui.details

import android.app.DownloadManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.mg.flickrviewer.R
import com.mg.flickrviewer.databinding.PhotoDetailsFragmentBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class PhotoDetailsFragment : Fragment() {

    private val args: PhotoDetailsFragmentArgs by navArgs()

    private var binding: PhotoDetailsFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PhotoDetailsFragmentBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            toolbar.setupWithNavController(findNavController())

            description.text = args.flickrPhoto.title

            imageProgressBar.isVisible = true
            photoView.setOnLongClickListener {

                ContextCompat.getSystemService(requireContext(), DownloadManager::class.java)
                    ?.let { dm ->
                        val fileUri = Uri.parse(args.flickrPhoto.getUrl())
                        val request = DownloadManager.Request(fileUri)
                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

                        dm.enqueue(request)
                    }

                Snackbar.make(view, R.string.loading_started, Snackbar.LENGTH_SHORT).show()
                true
            }

            Picasso.get()
                .load(args.flickrPhoto.getUrl())
                .error(R.drawable.ic_baseline_error_outline_24)
                .into(photoView, object : Callback {
                    override fun onSuccess() {
                        imageProgressBar.isVisible = false
                    }

                    override fun onError(e: Exception?) {
                        imageProgressBar.isVisible = false
                    }
                })
        }
    }
}
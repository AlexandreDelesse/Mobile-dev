package fr.isen.delesse.androidrestaurant.dishDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fr.isen.delesse.androidrestaurant.R
import fr.isen.delesse.androidrestaurant.databinding.FragmentPhotoBinding


class PhotoFragment : Fragment() {

    private lateinit var binding: FragmentPhotoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = arguments?.getString(URL)
        if(url?.isNotEmpty() == true) {
            Picasso.get().load(url).placeholder(R.drawable.iconentree).into(binding.photo)
        }

    }

    companion object {
        fun newInstance(url: String): PhotoFragment {
            return PhotoFragment().apply {
                arguments = Bundle().apply { putString(URL, url)
                }
            }
        }

        const val URL= "URL"
    }
}
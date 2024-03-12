package com.example.vkeducationtask.presentation

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.vkeducationtask.R
import com.example.vkeducationtask.databinding.CardProductBinding
import com.example.vkeducationtask.databinding.FragmentProductItemBinding
import com.example.vkeducationtask.domain.entity.Product
import com.squareup.picasso.Picasso
import kotlin.math.abs


class ProductItemFragment : Fragment(), GestureDetector.OnGestureListener  {

    private lateinit var binding: FragmentProductItemBinding
    lateinit var gestureDefector: GestureDetector
    private var currentIndexImage = 0
    private var listImage = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val product = getProduct()
        listImage = product.images
        gestureDefector = GestureDetector(requireContext(), this)
        binding.imageView2.setOnTouchListener{ view, motionEvent ->
            gestureDefector.onTouchEvent(motionEvent)
            true
        }
        loadImage(currentIndexImage)
        preloadImages()
        binding.apply {
            title.text = product.title
            desc.text = product.description
            rating.text = product.rating.toString()
            price.text = "$" + product.price.toString()
            discountPercentage.text = product.discountPercentage.toString()
            brand.text = product.brand
            imageButton.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
            nextImage.setOnClickListener {
                nextImage()
            }
            backImage.setOnClickListener {
                prevImage()
            }
        }

    }

    private fun getProduct() : Product {
        return requireArguments().getSerializable(PRODUCT) as Product
    }

    companion object {
        private const val SWIPE_MIN_DISTANCE = 80
        private const val SWIPE_MAX_OFF_PATH = 250
        private const val SWIPE_THRESHOLD_VELOCITY = 200

        const val TAG = "Product"
        const val PRODUCT = "ProductItem"


        fun newInstance(product: Product) : ProductItemFragment{
            val arduments = Bundle().apply {
                putSerializable(PRODUCT, product)
            }
            val fragment = ProductItemFragment()
            fragment.arguments = arduments
            return fragment
        }

    }
    private fun preloadImages() {
        for (index in listImage.indices) {
            Picasso.get().load(listImage[index]).fetch()
        }
    }

    private fun loadImage(index: Int) {
        Picasso.get().load(listImage[index]).into(binding.imageView2)
        binding.imageCounterTextView.text = "${index+1}/${listImage.size}"
    }

    private fun nextImage(){
        if (currentIndexImage < listImage.size - 1) {
            currentIndexImage++
            loadImage(currentIndexImage)
        }
    }

    private fun prevImage(){
        if (currentIndexImage > 0) {
            currentIndexImage--
            loadImage(currentIndexImage)
        }
    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent, p2: Float, p3: Float): Boolean {
        try {
            if (p0 != null) {
                if (abs(p0.y - p1.y) > SWIPE_MAX_OFF_PATH) {
                    return false
                }
            }
            if (p0 != null) {
                if (p0.x - p1.x > SWIPE_MIN_DISTANCE && abs(p2) > SWIPE_THRESHOLD_VELOCITY) {
                    nextImage()
                } else if (p1.x - p0.x > SWIPE_MIN_DISTANCE && abs(p2) > SWIPE_THRESHOLD_VELOCITY) {
                    prevImage()
                }
            }
        } catch (_: Exception) {
        }
        return true
    }

    override fun onDown(p0: MotionEvent): Boolean {
        return false
    }

    override fun onShowPress(p0: MotionEvent) {
    }

    override fun onSingleTapUp(p0: MotionEvent): Boolean {
        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent, p2: Float, p3: Float): Boolean {
        return false
    }

    override fun onLongPress(p0: MotionEvent) {
    }


}
package com.example.vkeducationtask.presentation

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.vkeducationtask.R
import com.example.vkeducationtask.databinding.CardProductBinding
import com.example.vkeducationtask.databinding.FragmentProductItemBinding
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
        listImage = getListImage() ?: arrayListOf()
        gestureDefector = GestureDetector(requireContext(), this)
        binding.imageView2.setOnTouchListener{ view, motionEvent ->
            gestureDefector.onTouchEvent(motionEvent)
            true
        }
        binding.apply {
            title.text = getTitle()
            desc.text = getDesc()
            rating.text = getRating().toString()
            price.text = getPrice().toString()
            discountPercentage.text = getDiscount().toString()
            brand.text = getBrand()
            imageButton.setOnClickListener {
                requireActivity().onBackPressed()
            }
            loadImage(currentIndexImage)
        }

    }

    private fun getTitle(): String? {
        return requireArguments().getString(TITLE)
    }

    private fun getListImage(): ArrayList<String>? {
        return requireArguments().getStringArrayList(LIST_IMAGE)
    }

    private fun getDesc(): String? {
        return requireArguments().getString(DESC)
    }

    private fun getRating(): Double {
        return requireArguments().getDouble(RATING)
    }

    private fun getPrice(): Int {
        return requireArguments().getInt(PRICE)
    }

    private fun getDiscount(): Double {
        return requireArguments().getDouble(DISCOUNT)
    }

    private fun getBrand(): String? {
        return requireArguments().getString(BRAND)
    }

    companion object {
        private const val SWIPE_MIN_DISTANCE = 120
        private const val SWIPE_MAX_OFF_PATH = 250
        private const val SWIPE_THRESHOLD_VELOCITY = 200

        const val TITLE = "Title"
        const val LIST_IMAGE = "ListImage"
        const val DESC = "Desc"
        const val RATING = "Rating"
        const val PRICE = "Price"
        const val DISCOUNT = "Discount"
        const val BRAND = "Brand"

        fun newInstance(title: String, list_image: ArrayList<String>, desc: String, rating: Double, price: Int, discount: Double, brand: String) : ProductItemFragment{
            val arduments = Bundle().apply {
                putString(TITLE, title)
                putStringArrayList(LIST_IMAGE, list_image)
                putString(DESC, desc)
                putDouble(RATING, rating)
                putInt(PRICE, price)
                putDouble(DISCOUNT, discount)
                putString(BRAND, brand)
            }
            val fragment = ProductItemFragment()
            fragment.arguments = arduments
            return fragment
        }

    }

    private fun loadImage(index : Int){
        if(listImage.size == 0){
            //TODO дефолтное изображение
        }
        else{
            Picasso.get().load(listImage[index]).into(binding.imageView2)
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
                    if (currentIndexImage < listImage.size - 1) {
                        currentIndexImage++
                        loadImage(currentIndexImage)
                    }
                } else if (p1.x - p0.x > SWIPE_MIN_DISTANCE && abs(p2) > SWIPE_THRESHOLD_VELOCITY) {
                    if (currentIndexImage > 0) {
                        currentIndexImage--
                        loadImage(currentIndexImage)
                    }
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
package com.example.myapplication

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.CartAdapter
import com.example.myapplication.databinding.ActivityCartBinding
import com.example.myapplication.model.CartModel
import com.example.myapplication.model.ProductModel

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private var cartAdapter: CartAdapter = CartAdapter()
    private var cartList : ArrayList<CartModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    fun init() {
        loadData()
        cartAdapter.CartAdapter(cartList, this@CartActivity, binding)
        binding.apply {
            recyclerCart.apply {
                layoutManager = LinearLayoutManager(this@CartActivity)
                adapter = cartAdapter
            }
        }

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, ProductActivity::class.java)
            intent.putExtra("cartList", cartList)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    fun loadData() {
        val productList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.extras?.getParcelable("productList", ArrayList<ProductModel>()::class.java)
        } else {
            TODO("VERSION.SDK_INT < TIRAMISU")
        }

        if (productList != null) {
            for (product in productList) {
                cartList.add(CartModel(product, 1, product.price!!.toFloat()))
            }
        }
    }
}
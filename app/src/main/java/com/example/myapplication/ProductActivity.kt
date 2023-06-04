package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.adapter.ProductAdapter
import com.example.myapplication.databinding.ActivityProductBinding
import com.example.myapplication.model.CartModel
import com.example.myapplication.model.ProductModel

class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding
    private var productAdapter: ProductAdapter = ProductAdapter()
    private var productList : ArrayList<ProductModel> = ArrayList()
    private var cartList : ArrayList<ProductModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            txtWelcome.text = "BIENVENIDO: ${intent?.extras?.getString("userName")}"
        }

        init()
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                updateCartList(data?.extras?.getParcelable("cartList", ArrayList<CartModel>()::class.java)!!)
            }
        }
    }

    fun updateCartList(cartList: ArrayList<CartModel>) {
        this.cartList.clear()

        for(car in cartList) {
            this.cartList.add(car.product)
        }

        binding.recyclerProduct.adapter!!.notifyDataSetChanged()
        binding.badge.setNumber(this.cartList.size)
    }

    fun init() {
        loadData()
        productAdapter.ProductAdapter(productList, cartList, this@ProductActivity, binding)
        binding.apply {
            recyclerProduct.apply {
                layoutManager = GridLayoutManager(this@ProductActivity, 2)
                adapter = productAdapter
            }
        }

        binding.btnCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            intent.putExtra("productList", cartList)
            resultLauncher.launch(intent)
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    fun loadData() {
        productList.add(
            ProductModel("1",
            "Iphone 13",
            "https://www.tiendaamiga.com.bo/media/catalog/product/cache/deb88dadd509903c96aaa309d3e790dc/e/0/e06641-iphone-13-bolivia_1.jpg",
            "7534")
        )
        productList.add(
            ProductModel(
                "2",
                "Samsung S23",
                "https://cdn.shopify.com/s/files/1/0024/2987/8323/products/nstore_5_746x.png?v=1685021675",
                "7235")
        )
        productList.add(ProductModel(
            "3",
            "MackBook Pro",
            "https://www.macworld.com/wp-content/uploads/2023/03/review-macbook-air-m2-2022.jpg?quality=50&strip=all",
            "13689")
        )
        productList.add(ProductModel(
            "4",
            "Monitor Samsung",
            "https://www.dismac.com.bo/media/catalog/product/l/s/ls24f350fhlxzs.jpg?quality=80&bg-color=255,255,255&fit=bounds&height=700&width=700&canvas=700:700",
            "1500")
        )
    }
}
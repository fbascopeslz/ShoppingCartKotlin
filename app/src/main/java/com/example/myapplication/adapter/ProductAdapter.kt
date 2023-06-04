package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityProductBinding
import com.example.myapplication.model.ProductModel
import com.example.myapplication.databinding.LayoutProductItemBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private lateinit var binding: LayoutProductItemBinding
    var productList: ArrayList<ProductModel> = ArrayList()
    var cartList: ArrayList<ProductModel> = ArrayList()
    lateinit var context:Context
    private lateinit var bindingProductActivity: ActivityProductBinding

    fun ProductAdapter(productList : ArrayList<ProductModel>, cartList : ArrayList<ProductModel>, context: Context, bindingProductActivity: ActivityProductBinding) {
        this.productList = productList
        this.cartList = cartList
        this.context = context
        this.bindingProductActivity = bindingProductActivity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        binding = LayoutProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList.get(position)

        holder.itemView.setOnClickListener{
            addToCart(holder.itemView, product)
        }

        holder.bind(productList[position])
    }

    fun addToCart(view: View, product: ProductModel) {
        if (!cartList.contains(product)) {
            cartList.add(product)
            Snackbar.make(view, "${product.name} AGREGADO CORRECTAMENTE AL CARRITO!", Snackbar.LENGTH_SHORT).show()
            bindingProductActivity.badge.setNumber(cartList.size)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ProductViewHolder(var itemBinding: LayoutProductItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item : ProductModel) {
            binding.apply {
                txtPrice.text = "${item.price} Bs."
                txtName.text = item.name
                Picasso.get().load(item.image).into(imageView)
            }
        }
    }
}